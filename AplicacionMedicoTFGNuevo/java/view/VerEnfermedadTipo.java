package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.MedicoController;

public class VerEnfermedadTipo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	MedicoController controllerMedico = new MedicoController();
	String[] dniPaciente;
	JComboBox<String> comboBoxDniPacientes;
	String selectedDni, enfermedad, tipo, fecha;
	JLabel lblTipo, lblEnfermedad, lblIntroduzcaNombre;
	JButton btnVolver;
	VentanaPrincipalMedico principal;

	private JTextField textFieldEnfermedad;
	private JTextField textFieldTipo;
	private JLabel lblVerEnfermedadIngreso;
	private JLabel lblFechaIngreso;
	private JTextField textFieldFechaIngreso;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerEnfermedadTipo frame = new VerEnfermedadTipo(dni);
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
	public VerEnfermedadTipo(String dni) {
		setResizable(false);
		try {
			VerEnfermedadTipo.dni = dni;
			dniPaciente = controllerMedico.dniPacientes(dni);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 539, 472);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(230, 230, 250));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			lblIntroduzcaNombre = new JLabel("Introduzca el DNI del paciente");
			lblIntroduzcaNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblIntroduzcaNombre.setBounds(115, 91, 210, 22);
			contentPane.add(lblIntroduzcaNombre);

			lblEnfermedad = new JLabel("Enfermedad inrgreso:");
			lblEnfermedad.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblEnfermedad.setBounds(74, 183, 169, 22);
			contentPane.add(lblEnfermedad);

			lblTipo = new JLabel("Tipo enfermedad:");
			lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblTipo.setBounds(74, 251, 131, 22);
			contentPane.add(lblTipo);

			comboBoxDniPacientes = new JComboBox<String>();
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			model.addElement("");
			for (int i = 0; i < dniPaciente.length; i++) {
				model.addElement(dniPaciente[i]);
			}
			comboBoxDniPacientes.setModel(model);
			comboBoxDniPacientes.setBounds(336, 93, 179, 21);
			comboBoxDniPacientes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
						enfermedad = controllerMedico.findEnfermedadIngreso(selectedDni);
						tipo = controllerMedico.findTipo(selectedDni);
						fecha = controllerMedico.findFechaIngreso(selectedDni);

						if (enfermedad != null && tipo != null && fecha != null) {
							textFieldEnfermedad.setText(enfermedad);
							textFieldTipo.setText(tipo);
							textFieldFechaIngreso.setText(fecha);

						} else {
							JOptionPane.showMessageDialog(VerEnfermedadTipo.this,
									"El DNI " + selectedDni + " no tiene un diagnostico");
						}

					} catch (NullPointerException e1) {
						JOptionPane.showMessageDialog(VerEnfermedadTipo.this,
								"El DNI " + selectedDni + " no tiene un diagnostico");
					}
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
			btnVolver.setBounds(10, 92, 85, 21);
			contentPane.add(btnVolver);

			textFieldEnfermedad = new JTextField();
			textFieldEnfermedad.setEditable(false);
			textFieldEnfermedad.setBounds(276, 186, 210, 19);
			contentPane.add(textFieldEnfermedad);
			textFieldEnfermedad.setColumns(10);

			textFieldTipo = new JTextField();
			textFieldTipo.setEditable(false);
			textFieldTipo.setColumns(10);
			textFieldTipo.setBounds(276, 254, 210, 19);
			contentPane.add(textFieldTipo);

			lblVerEnfermedadIngreso = new JLabel("Ver enfermedad ingreso del paciente");
			lblVerEnfermedadIngreso.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblVerEnfermedadIngreso.setBounds(115, 29, 303, 21);
			contentPane.add(lblVerEnfermedadIngreso);

			lblFechaIngreso = new JLabel("Fecha ingreso:");
			lblFechaIngreso.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblFechaIngreso.setBounds(74, 314, 131, 22);
			contentPane.add(lblFechaIngreso);

			textFieldFechaIngreso = new JTextField();
			textFieldFechaIngreso.setEditable(false);
			textFieldFechaIngreso.setColumns(10);
			textFieldFechaIngreso.setBounds(276, 317, 210, 19);
			contentPane.add(textFieldFechaIngreso);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(VerEnfermedadTipo.this, "El DNI " + dni + " no tiene pacientes a cargo");
		}
	}
}
