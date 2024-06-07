package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bson.Document;

import controller.MedicoController;

import javax.swing.JTextField;
import javax.swing.JButton;

public class EliminarMedicamentoTarjeta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JLabel lblTitulo, lblEligaElMedicamento, lblMedicamentoElegido, lblMensaje, lblEligaElDni;
	String[] medicamento, dniPaciente;
	String selectedDni, selectedMedicamento;
	static String dni;
	private JTextField textFieldMedicamento;
	JButton btnCancelar, btnEliminar;
	VentanaPrincipalMedico principal;
	MedicoController controllerMedico = new MedicoController();
	private JComboBox<String> comboBoxDniPacientes, comboBoxMedicamentos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EliminarMedicamentoTarjeta frame = new EliminarMedicamentoTarjeta(dni);
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
	public EliminarMedicamentoTarjeta(String dni) {
		try {
			AnadirMedicamentoTarjeta.dni = dni;
			dniPaciente = controllerMedico.dniPacientes(dni);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 556, 389);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(230, 230, 250));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			lblTitulo = new JLabel("Elimina medicamentos de la tarjeta sanitaria\r\n");
			lblTitulo.setBounds(106, 24, 387, 29);
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
			contentPane.add(lblTitulo);

			comboBoxDniPacientes = new JComboBox<String>();
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			model.addElement("");
			for (int i = 0; i < dniPaciente.length; i++) {
				model.addElement(dniPaciente[i]);
			}
			comboBoxDniPacientes.setModel(model);
			comboBoxDniPacientes.setBounds(303, 82, 212, 21);
			comboBoxDniPacientes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
						lblEligaElMedicamento.setVisible(true);
						comboBoxMedicamentos.setVisible(true);
						medicamento = controllerMedico.medicamentos(selectedDni);
						DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<String>();
						model1.addElement("");
						for (int i = 0; i < medicamento.length; i++) {
							model1.addElement(medicamento[i]);
						}
						comboBoxMedicamentos.setModel(model1);
					} catch (NullPointerException e1) {
						JOptionPane.showMessageDialog(EliminarMedicamentoTarjeta.this,
								"El DNI " + selectedDni + " no tiene medicamentos");
						lblEligaElMedicamento.setVisible(false);
						comboBoxMedicamentos.setVisible(false);

					}
				}
			});
			comboBoxDniPacientes.setBounds(303, 88, 190, 21);
			contentPane.add(comboBoxDniPacientes);
			comboBoxMedicamentos = new JComboBox<String>();
			comboBoxMedicamentos.setVisible(false);
			comboBoxMedicamentos.setBounds(303, 132, 190, 21);
			comboBoxMedicamentos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedMedicamento = comboBoxMedicamentos.getSelectedItem().toString();
					lblMedicamentoElegido.setVisible(true);
					textFieldMedicamento.setVisible(true);
					textFieldMedicamento.setText(selectedMedicamento);
				}
			});
			contentPane.add(comboBoxMedicamentos);

			lblEligaElMedicamento = new JLabel("Eliga el medicamento que quiera eliminar");
			lblEligaElMedicamento.setVisible(false);
			lblEligaElMedicamento.setBounds(22, 127, 271, 29);
			lblEligaElMedicamento.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPane.add(lblEligaElMedicamento);

			lblMedicamentoElegido = new JLabel("El medicamento eligido para eliminar ");
			lblMedicamentoElegido.setBounds(22, 184, 271, 21);
			lblMedicamentoElegido.setVisible(false);
			lblMedicamentoElegido.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPane.add(lblMedicamentoElegido);

			textFieldMedicamento = new JTextField();
			textFieldMedicamento.setEditable(false);
			textFieldMedicamento.setBounds(303, 186, 190, 19);
			textFieldMedicamento.setVisible(false);
			contentPane.add(textFieldMedicamento);
			textFieldMedicamento.setColumns(10);

			btnCancelar = new JButton("Cancelar");
			btnCancelar.setBounds(127, 256, 85, 29);
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					principal = new VentanaPrincipalMedico(dni);
					principal.setVisible(true);
					dispose();

				}
			});
			btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
			contentPane.add(btnCancelar);

			btnEliminar = new JButton("Eliminar");
			btnEliminar.setBounds(321, 256, 85, 29);
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
					Optional<Document> paciente = controllerMedico.findByDniPaciente(selectedDni);
					if (paciente.isPresent()) {
						selectedMedicamento = comboBoxMedicamentos.getSelectedItem().toString();
						Boolean actualizado = controllerMedico.eliminarMedicamentoTarjeta(paciente,
								selectedMedicamento);
						if (actualizado == true) {
							lblMensaje.setText("Medicamentos eliminado de la tarjeta con exito");
							lblMensaje.setForeground(Color.GREEN);
						} else {
							lblMensaje.setText("Medicamentos no eliminado de la tarjeta");
							lblMensaje.setForeground(Color.RED);
						}
					} else {
						lblMensaje.setText("No existe el paciente con el DNI " + dni);
						lblMensaje.setForeground(Color.RED);
					}
				}
			});
			btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
			contentPane.add(btnEliminar);

			lblMensaje = new JLabel("");
			lblMensaje.setBounds(111, 306, 357, 36);
			lblMensaje.setFont(new Font("Tahoma", Font.PLAIN, 12));
			contentPane.add(lblMensaje);

			lblEligaElDni = new JLabel("Eliga el DNI del paciente\r\n");
			lblEligaElDni.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblEligaElDni.setBounds(22, 77, 271, 29);
			contentPane.add(lblEligaElDni);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(EliminarMedicamentoTarjeta.this, "El DNI " + dni + " no pacientes a cargo");
		}

	}
}
