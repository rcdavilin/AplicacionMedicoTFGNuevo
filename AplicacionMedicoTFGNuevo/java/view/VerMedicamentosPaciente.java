package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.MedicoController;
import javax.swing.JButton;

public class VerMedicamentosPaciente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	String[] dniPaciente;
	String[] citas;
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
					VerMedicamentosPaciente frame = new VerMedicamentosPaciente(dni);
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
	public VerMedicamentosPaciente(String dni) {

		AsignarCitaPaciente.dni = dni;
		dniPaciente = controllerMedico.dniPacientes(dni);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 534, 376);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblVerCitasCon = new JLabel("Ver medicamentos con los pacientes a cargo");
		lblVerCitasCon.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVerCitasCon.setBounds(10, 32, 306, 27);
		contentPane.add(lblVerCitasCon);

		comboBoxDniPacientes = new JComboBox<String>();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("");
		for (int i = 0; i < dniPaciente.length; i++) {
			model.addElement(dniPaciente[i]);
		}
		comboBoxDniPacientes.setModel(model);
		comboBoxDniPacientes.setBounds(331, 36, 179, 21);
		comboBoxDniPacientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
					citas = controllerMedico.medicamentos(selectedDni);
					
					String todasLosMedicamentos = "";
					for (int i = 0; i < citas.length; i++) {
						todasLosMedicamentos += citas[i] + "\n";
					}
					textAreaMostrar.setText(todasLosMedicamentos);
					
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(VerMedicamentosPaciente.this,
							"El DNI " + selectedDni + " no tiene medicamentos");
				}
			}
		});

		contentPane.add(comboBoxDniPacientes);

		lblNewLabel = new JLabel("Medicamentos de los pacientes");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(24, 113, 202, 37);
		contentPane.add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(236, 119, 274, 121);
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
