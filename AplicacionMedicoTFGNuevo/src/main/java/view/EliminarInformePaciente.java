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

public class EliminarInformePaciente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JLabel lblTitulo, lblEligaElMedicamento, lblMedicamentoElegido, lblMensaje, lblEligaElDni;
	String[]  dniPaciente,informes;
	String selectedDni, selectedInforme;
	static String dni;
	private JTextField textFieldInforme;
	JButton btnCancelar, btnEliminar;
	VentanaPrincipalMedico principal;
	MedicoController controllerMedico = new MedicoController();
	private JComboBox<String> comboBoxInforme;
	private JComboBox<String> comboBoxDniPacientes;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EliminarInformePaciente frame = new EliminarInformePaciente(dni);
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
	public EliminarInformePaciente(String dni) {
		try {
			AnadirMedicamentoTarjeta.dni = dni;
			dniPaciente = controllerMedico.dniPacientes(dni);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 590, 389);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(230, 230, 250));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			lblTitulo = new JLabel("Elimina informe del paciente\r\n");
			lblTitulo.setBounds(165, 23, 225, 29);
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
			contentPane.add(lblTitulo);
			
			comboBoxDniPacientes = new JComboBox<String>();
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			model.addElement("");
			for (int i = 0; i < dniPaciente.length; i++) {
				model.addElement(dniPaciente[i]);
			}
			comboBoxDniPacientes.setModel(model);
			comboBoxDniPacientes.setBounds(300, 82, 192, 21);
			comboBoxDniPacientes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
						lblEligaElMedicamento.setVisible(true);
						comboBoxInforme.setVisible(true);
						informes = controllerMedico.informes(selectedDni);
						DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<String>();
						model1.addElement("");
						for (int i = 0; i < informes.length; i++) {
							model1.addElement(informes[i]);
						}
						comboBoxInforme.setModel(model1);
					} catch (NullPointerException e1) {
						JOptionPane.showMessageDialog(EliminarInformePaciente.this,
								"El DNI " + selectedDni + " no tiene informes");
						lblEligaElMedicamento.setVisible(false);
						comboBoxInforme.setVisible(false);

					}
				}
			});
			contentPane.add(comboBoxDniPacientes);

		
			
			
			comboBoxInforme = new JComboBox<String>();
			comboBoxInforme.setVisible(false);
			comboBoxInforme.setBounds(300, 132, 264, 21);
			comboBoxInforme.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedInforme = comboBoxInforme.getSelectedItem().toString();
					lblMedicamentoElegido.setVisible(true);
					textFieldInforme.setVisible(true);
					textFieldInforme.setText(selectedInforme);
				}
			});
			contentPane.add(comboBoxInforme);

			lblEligaElMedicamento = new JLabel("Eliga el informe que quiera eliminar por fecha\r\n");
			lblEligaElMedicamento.setVisible(false);
			lblEligaElMedicamento.setBounds(10, 127, 283, 29);
			lblEligaElMedicamento.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPane.add(lblEligaElMedicamento);

			lblMedicamentoElegido = new JLabel("El informe eligido para eliminar ");
			lblMedicamentoElegido.setBounds(24, 184, 230, 21);
			lblMedicamentoElegido.setVisible(false);
			lblMedicamentoElegido.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPane.add(lblMedicamentoElegido);

			textFieldInforme = new JTextField();
			textFieldInforme.setEditable(false);
			textFieldInforme.setBounds(300, 185, 264, 19);
			textFieldInforme.setVisible(false);
			contentPane.add(textFieldInforme);
			textFieldInforme.setColumns(10);

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
						selectedInforme = textFieldInforme.getText();
						Boolean actualizado = controllerMedico.eliminarInforme(selectedDni,selectedInforme);
						if (actualizado == true) {
							lblMensaje.setText("Informe eliminado con exito");
							lblMensaje.setForeground(Color.GREEN);
						} else {
							lblMensaje.setText("Informe no eliminado con exito");
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
			lblMensaje.setBounds(174, 321, 294, 21);
			lblMensaje.setFont(new Font("Tahoma", Font.PLAIN, 12));
			contentPane.add(lblMensaje);

			lblEligaElDni = new JLabel("Eliga el DNI del paciente\r\n");
			lblEligaElDni.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblEligaElDni.setBounds(24, 77, 188, 29);
			contentPane.add(lblEligaElDni);
			
			
			
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(EliminarInformePaciente.this, "El DNI " + dni + " no pacientes a cargo");
		}

	}
}
