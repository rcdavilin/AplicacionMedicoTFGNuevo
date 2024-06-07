package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.MedicoController;

public class VerCitasConLosPacientes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	String[] dniPaciente;
	ArrayList<String> citas, dniMedico;
	String selectedDni;
	MedicoController controllerMedico = new MedicoController();
	JLabel lblVerCitasCon;
	JComboBox<String> comboBoxDniPacientes;
	JLabel lblNewLabel;
	JTextArea textAreaMostrar;
	JScrollPane scrollPane;
	private JButton btnCancelar;
	VentanaPrincipalMedico principal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerCitasConLosPacientes frame = new VerCitasConLosPacientes(dni);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VerCitasConLosPacientes(String dni) {

		AsignarCitaPaciente.dni = dni;
		dniPaciente = controllerMedico.dniPacientes(dni);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 502, 376);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblVerCitasCon = new JLabel("Ver citas con los pacientes a cargo");
		lblVerCitasCon.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVerCitasCon.setBounds(21, 32, 233, 27);
		contentPane.add(lblVerCitasCon);

		comboBoxDniPacientes = new JComboBox<String>();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("");
		for (int i = 0; i < dniPaciente.length; i++) {
			model.addElement(dniPaciente[i]);
		}
		comboBoxDniPacientes.setModel(model);
		comboBoxDniPacientes.setBounds(289, 36, 179, 21);
		comboBoxDniPacientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
					citas = controllerMedico.findbyCitasPaciente(selectedDni);
					dniMedico = controllerMedico.findDniMedicobyCitasPaciente(selectedDni);
					String nombreMedico = "";
					String apellidoMedico = "";
					String especialidad = "";
					StringBuilder todasLasCitas = new StringBuilder();
					for (int i = 0; i < citas.size(); i++) {
						nombreMedico = controllerMedico.findNombreMedicoPorDni(dniMedico.get(i));
						apellidoMedico = controllerMedico.findApellidosMedicoPorDni(dniMedico.get(i));
						especialidad = controllerMedico.findEspecialidadPorDni(dniMedico.get(i));
						todasLasCitas.append(citas.get(i)).append(" - ").append(nombreMedico).append(" ").append(apellidoMedico).append(" - ")
						.append(especialidad).append("\n");
					}
					textAreaMostrar.setText(todasLasCitas.toString());
					
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(VerCitasConLosPacientes.this,
							"El DNI " + selectedDni + " no tiene citas a cargo");
				}catch (ClassCastException e2) {
					JOptionPane.showMessageDialog(VerCitasConLosPacientes.this,
							"El DNI " + selectedDni + " no tiene citas a cargo");
				}
			}
		});

		contentPane.add(comboBoxDniPacientes);

		lblNewLabel = new JLabel("Citas con los pacientes");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(21, 69, 157, 37);
		contentPane.add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 116, 447, 156);
		contentPane.add(scrollPane);

		textAreaMostrar = new JTextArea();
		textAreaMostrar.setEditable(false);
		scrollPane.setViewportView(textAreaMostrar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal = new VentanaPrincipalMedico(dni);
				principal.setVisible(true);
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelar.setBounds(186, 282, 85, 27);
		contentPane.add(btnCancelar);

	}
}
