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
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.MedicoController;

public class VerHistorialMedico extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	MedicoController controllerMedico = new MedicoController();
	String[] dniPaciente;
	JComboBox<String> comboBoxDniPacientes;
	String pacientes;
	static String selectedDni;
	VentanaPrincipalMedico vpm;
	JLabel lblMedicamentos, lblAlergenos, lblIntroduzcaNombre;
	JFormattedTextField formattedTextFieldDni;
	JButton btnVolver;
	VentanaPrincipalMedico principal;
	String[] alergenos;
	String[] medicamentos;
	JScrollPane scrollPaneAlergenos;
	JTextArea textAreaAlergenos;
	JScrollPane scrollPaneMedicamentos;
	JTextArea textAreaMedicamentos;
	JButton btnNewButton;
	VerEnfermedades enfermedades;
	JLabel lblEnfermedades;
	JScrollPane scrollPaneEnfermedades;
	JTextArea textAreaEnfermedades;
	ArrayList<String> enfermedad, fechaBaja, fechaAlta;
	private JLabel lblVerHistorialMedico;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerHistorialMedico frame = new VerHistorialMedico(dni);
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
	public VerHistorialMedico(String dni) {
		try {
			VerHistorialMedico.dni = dni;
			dniPaciente = controllerMedico.dniPacientes(dni);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 539, 524);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(230, 230, 250));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			lblIntroduzcaNombre = new JLabel("Introduzca el DNI del paciente");
			lblIntroduzcaNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblIntroduzcaNombre.setBounds(104, 75, 210, 22);
			contentPane.add(lblIntroduzcaNombre);

			lblAlergenos = new JLabel("Alérgenos:");
			lblAlergenos.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblAlergenos.setBounds(94, 145, 85, 22);
			contentPane.add(lblAlergenos);

			lblMedicamentos = new JLabel("Medicamentos actuales:");
			lblMedicamentos.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblMedicamentos.setBounds(48, 253, 131, 22);
			contentPane.add(lblMedicamentos);

			comboBoxDniPacientes = new JComboBox<String>();
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			model.addElement("");
			for (int i = 0; i < dniPaciente.length; i++) {
				model.addElement(dniPaciente[i]);
			}
			comboBoxDniPacientes.setModel(model);
			comboBoxDniPacientes.setBounds(325, 77, 179, 21);
			comboBoxDniPacientes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
						alergenos = controllerMedico.findAlergenosPaciente(selectedDni);
						medicamentos = controllerMedico.findMedicamentosPaciente(selectedDni);
						enfermedad = controllerMedico.findEnfermedad(selectedDni);
						fechaBaja = controllerMedico.findFechaBaja(selectedDni);
						fechaAlta = controllerMedico.findFechaAlta(selectedDni);

						String todasLosAlergenos = "";
						todasLosAlergenos = buscarDentroDeArray(todasLosAlergenos, alergenos);
						String todosLosMedicamentos = "";
						todosLosMedicamentos = buscarDentroDeArray(todosLosMedicamentos, medicamentos);

						StringBuilder enfermedadesYFechas = new StringBuilder();
						for (int i = 0; i < enfermedad.size(); i++) {
							enfermedadesYFechas.append(enfermedad.get(i)).append(" - ").append(fechaBaja.get(i)).append("\n");
						}

						textAreaAlergenos.setText(todasLosAlergenos);
						textAreaMedicamentos.setText(todosLosMedicamentos);
						textAreaEnfermedades.setText(enfermedadesYFechas.toString());

					} catch (NullPointerException e1) {
						textAreaAlergenos.setText("");
						textAreaMedicamentos.setText("");
						textAreaEnfermedades.setText("");
						JOptionPane.showMessageDialog(VerHistorialMedico.this,
								"El DNI " + selectedDni + " no tiene enfermedades, alergenos y medicamentos en su historial médico");
					}
				}

				private String buscarDentroDeArray(String todasLosAlergenos, String[] array) {
					for (int i = 0; i < array.length; i++) {
						todasLosAlergenos += array[i] + "\n";
					}
					return todasLosAlergenos;
				}
			});
			contentPane.add(comboBoxDniPacientes);

			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					principal = new VentanaPrincipalMedico(dni);
					principal.setVisible(true);
					dispose();
				}
			});
			btnVolver.setBackground(new Color(240, 240, 240));
			btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnVolver.setBounds(10, 76, 85, 21);
			contentPane.add(btnVolver);

			scrollPaneAlergenos = new JScrollPane();
			scrollPaneAlergenos.setBounds(228, 125, 180, 91);
			contentPane.add(scrollPaneAlergenos);

			textAreaAlergenos = new JTextArea();
			textAreaAlergenos.setEditable(false);
			scrollPaneAlergenos.setViewportView(textAreaAlergenos);

			scrollPaneMedicamentos = new JScrollPane();
			scrollPaneMedicamentos.setBounds(228, 226, 180, 86);
			contentPane.add(scrollPaneMedicamentos);

			textAreaMedicamentos = new JTextArea();
			textAreaMedicamentos.setEditable(false);
			scrollPaneMedicamentos.setViewportView(textAreaMedicamentos);

			btnNewButton = new JButton("Ver enfermedades");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
					selectedDni = (String) comboBoxDniPacientes.getSelectedItem();

					enfermedades = new VerEnfermedades(dni, selectedDni);
					enfermedades.setVisible(true);
					dispose();
					}catch (NullPointerException e1) {
						JOptionPane.showMessageDialog(VerHistorialMedico.this,
								"El DNI " + selectedDni + " no tiene enfermedades en su historial médico");
					}
						
					
				}
			});
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnNewButton.setBounds(191, 433, 131, 30);
			contentPane.add(btnNewButton);

			lblEnfermedades = new JLabel("Enfermedades:");
			lblEnfermedades.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblEnfermedades.setBounds(76, 348, 103, 22);
			contentPane.add(lblEnfermedades);

			scrollPaneEnfermedades = new JScrollPane();
			scrollPaneEnfermedades.setBounds(228, 330, 180, 86);
			contentPane.add(scrollPaneEnfermedades);

			textAreaEnfermedades = new JTextArea();
			scrollPaneEnfermedades.setViewportView(textAreaEnfermedades);
			
			lblVerHistorialMedico = new JLabel("Ver historial medico");
			lblVerHistorialMedico.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblVerHistorialMedico.setBounds(166, 25, 192, 21);
			contentPane.add(lblVerHistorialMedico);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(VerHistorialMedico.this, "El DNI " + dni + " no tiene pacientes a cargo");
		}
	}
}
