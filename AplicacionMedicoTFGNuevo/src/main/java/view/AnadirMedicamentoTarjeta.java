package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.bson.Document;

import controller.MedicoController;

public class AnadirMedicamentoTarjeta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	String[] dniPaciente;
	MedicoController controllerMedico = new MedicoController();
	String selectedDni;
	private JLabel lblTitulo, lblElijaElDni, lblMensaje, lblIntroduzcaMedicamentos;
	JButton btnCancelar, btnAceptar;
	JComboBox<String> comboBoxDniPacientes;
	VentanaPrincipalMedico principal;
	static String dni;
	private JButton btnAnadirCampo;
	private JButton btnEliminarCampo;
	private int textFieldXPosition = 384;
	private int textFieldYPosition = 159;
	private final int textFieldHeight = 26;
	private final int textFieldSpacing = 10;
	private List<JTextField> textFields = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnadirMedicamentoTarjeta frame = new AnadirMedicamentoTarjeta(dni);
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
	public AnadirMedicamentoTarjeta(String dni) {
		try {
			AnadirMedicamentoTarjeta.dni = dni;
			dniPaciente = controllerMedico.dniPacientes(dni);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 646, 483);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(230, 230, 250));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			comboBoxDniPacientes = new JComboBox<String>();
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			model.addElement("");
			for (int i = 0; i < dniPaciente.length; i++) {
				model.addElement(dniPaciente[i]);
			}
			comboBoxDniPacientes.setModel(model);
			comboBoxDniPacientes.setBounds(394, 89, 212, 21);
			comboBoxDniPacientes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
					lblIntroduzcaMedicamentos.setVisible(true);
					btnAnadirCampo.setVisible(true);
					btnEliminarCampo.setVisible(true);
				}
			});
			contentPane.add(comboBoxDniPacientes);

			lblIntroduzcaMedicamentos = new JLabel("Introduzca los medicamentos a la tarjeta sanitaria del paciente");
			lblIntroduzcaMedicamentos.setVisible(false);
			lblIntroduzcaMedicamentos.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblIntroduzcaMedicamentos.setBounds(10, 155, 381, 31);
			contentPane.add(lblIntroduzcaMedicamentos);

			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					principal = new VentanaPrincipalMedico(dni);
					principal.setVisible(true);
					dispose();
				}
			});
			btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnCancelar.setBounds(147, 341, 95, 31);
			contentPane.add(btnCancelar);

			btnAceptar = new JButton("Aceptar");
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Optional<Document> paciente = controllerMedico.findByDniPaciente(selectedDni);
					if (paciente.isPresent()) {
						List<String> medicamentosList = new ArrayList<>();
						for (JTextField tf : textFields) {
							if (!tf.getText().trim().isEmpty()) {
			                    medicamentosList.add(tf.getText().trim());
			                }
						}
						String[] listaMedicamentos = medicamentosList.toArray(new String[0]);
						Boolean anadido = controllerMedico.anadirTarjeta(paciente, listaMedicamentos);
						if (anadido == true) {
							lblMensaje.setText("Medicamentos añadidos a la tarjeta medica del paciente con éxito");
							lblMensaje.setForeground(Color.GREEN);
						} else {
							lblMensaje.setText(
									"Medicamentos no han sido añadidos a la tarjeta medica del paciente con éxito");
							lblMensaje.setForeground(Color.RED);
						}
					} else {
						lblMensaje.setText("No existe el paciente con el DNI " + selectedDni);
						lblMensaje.setForeground(Color.RED);
					}

				}
			});
			btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnAceptar.setBounds(372, 341, 90, 31);
			contentPane.add(btnAceptar);

			lblTitulo = new JLabel("Añadir medicamentos a la tarjeta sanitaria");
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblTitulo.setBounds(151, 25, 359, 25);
			contentPane.add(lblTitulo);

			lblMensaje = new JLabel("");
			lblMensaje.setBounds(131, 395, 348, 21);
			contentPane.add(lblMensaje);

			lblElijaElDni = new JLabel("Elija el DNI del paciente al que quiera añadirle medicamentos");
			lblElijaElDni.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblElijaElDni.setBounds(10, 86, 374, 25);
			contentPane.add(lblElijaElDni);

			btnAnadirCampo = new JButton("Nuevo medicamento");
			btnAnadirCampo.setVisible(false);
			btnAnadirCampo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					anadirCampo();
				}
			});
			btnAnadirCampo.setBounds(21, 216, 198, 21);
			contentPane.add(btnAnadirCampo);

			btnEliminarCampo = new JButton("Eliminar ultimo medicamento");
			btnEliminarCampo.setVisible(false);
			btnEliminarCampo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eliminarCampo();
				}
			});
			btnEliminarCampo.setBounds(21, 247, 198, 21);
			contentPane.add(btnEliminarCampo);

		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(AnadirMedicamentoTarjeta.this, "El DNI " + dni + " no pacientes a cargo");
		}
	}

	private void anadirCampo() {
		JTextField campoNuevo = new JTextField();
		campoNuevo.setBounds(textFieldXPosition, textFieldYPosition, 222, textFieldHeight);
		contentPane.add(campoNuevo);
		textFields.add(campoNuevo);

		textFieldYPosition += textFieldHeight + textFieldSpacing;

		contentPane.setPreferredSize(new Dimension(400, textFieldYPosition + textFieldHeight));
		contentPane.revalidate();
		contentPane.repaint();
	}

	private void eliminarCampo() {
		if (!textFields.isEmpty()) {
			JTextField lastField = textFields.remove(textFields.size() - 1);
			contentPane.remove(lastField);

			textFieldYPosition -= textFieldHeight + textFieldSpacing;

			contentPane.setPreferredSize(new Dimension(400, textFieldYPosition + textFieldHeight));
			contentPane.revalidate();
			contentPane.repaint();
		}
	}
}
