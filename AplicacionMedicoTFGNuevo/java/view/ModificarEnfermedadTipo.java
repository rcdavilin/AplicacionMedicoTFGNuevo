package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

public class ModificarEnfermedadTipo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	MedicoController controllerMedico = new MedicoController();
	String[] dniPaciente;
	JComboBox<String> comboBoxDniPacientes;
	String selectedDni, enfermedad, tipo, fecha;
	JLabel lblTipo, lblEnfermedad, lblIntroduzcaNombre;
	VentanaPrincipalMedico principal;
	JButton btnGuardar;
	private JTextField textFieldEnfermedad;
	private JTextField textFieldTipo;
	JLabel lblMensaje;
	private JLabel lblModificarDiagnostico;
	private JLabel lblFechaIngreso;
	private JTextField textFieldFechaIngreso;
	private JButton btnVolver;
	private JButton btnDarDeAlts;
	AnadirEnfermedadAHistorial anadirHistorial;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModificarEnfermedadTipo frame = new ModificarEnfermedadTipo(dni);
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
	public ModificarEnfermedadTipo(String dni) {
		setResizable(false);
		try {
			ModificarEnfermedadTipo.dni = dni;
			dniPaciente = controllerMedico.dniPacientes(dni);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 570, 464);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(230, 230, 250));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			lblIntroduzcaNombre = new JLabel("Introduzca el DNI del paciente");
			lblIntroduzcaNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblIntroduzcaNombre.setBounds(45, 85, 210, 22);
			contentPane.add(lblIntroduzcaNombre);

			lblEnfermedad = new JLabel("Enfermedad inrgreso:");
			lblEnfermedad.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblEnfermedad.setBounds(89, 155, 169, 22);
			contentPane.add(lblEnfermedad);

			lblTipo = new JLabel("Tipo enfermedad");
			lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblTipo.setBounds(89, 213, 131, 22);
			contentPane.add(lblTipo);

			comboBoxDniPacientes = new JComboBox<String>();
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			model.addElement("");
			for (int i = 0; i < dniPaciente.length; i++) {
				model.addElement(dniPaciente[i]);
			}
			comboBoxDniPacientes.setModel(model);
			comboBoxDniPacientes.setBounds(270, 87, 179, 21);
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
							JOptionPane.showMessageDialog(ModificarEnfermedadTipo.this,
									"El DNI " + selectedDni + " no tiene un diagnostico");
						}
					} catch (NullPointerException e1) {
						JOptionPane.showMessageDialog(ModificarEnfermedadTipo.this,
								"El DNI " + selectedDni + " no tiene asignada una enferemedad y su tipo");
					}
				}

			});
			contentPane.add(comboBoxDniPacientes);

			textFieldEnfermedad = new JTextField();
			textFieldEnfermedad.setBounds(266, 158, 210, 19);
			contentPane.add(textFieldEnfermedad);
			textFieldEnfermedad.setColumns(10);

			textFieldTipo = new JTextField();
			textFieldTipo.setColumns(10);
			textFieldTipo.setBounds(266, 216, 210, 19);
			contentPane.add(textFieldTipo);
			
			btnGuardar = new JButton("Guardar datos");
			btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnGuardar.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
			        Optional<Document> paciente = controllerMedico.findByDniPaciente(selectedDni);
			        enfermedad = textFieldEnfermedad.getText();
			        tipo = textFieldTipo.getText();
			        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' uuuu HH:mm");
			        fecha = LocalDateTime.now().format(formateador);
			        
			        Boolean actualizado = controllerMedico.actualizarEnfermedadYTipo(paciente, enfermedad, tipo,fecha);
			        if (actualizado) {
			            lblMensaje.setText("Enfermedad ingreso y tipo modificada con éxito");
			            lblMensaje.setForeground(Color.GREEN);
			        } else {
			            lblMensaje.setText("Enfermedad ingreso y tipo no han sido modificadas con éxito");
			            lblMensaje.setForeground(Color.RED);
			        }
			    }
			});

			btnGuardar.setBounds(207, 330, 131, 35);
			contentPane.add(btnGuardar);
			
			lblMensaje = new JLabel("");
			lblMensaje.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblMensaje.setBounds(98, 376, 319, 28);
			contentPane.add(lblMensaje);
			
			lblModificarDiagnostico = new JLabel("Modificar diagnostico");
			lblModificarDiagnostico.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblModificarDiagnostico.setBounds(171, 27, 217, 21);
			contentPane.add(lblModificarDiagnostico);
			
			lblFechaIngreso = new JLabel("Fecha ingreso:");
			lblFechaIngreso.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblFechaIngreso.setBounds(89, 264, 131, 22);
			contentPane.add(lblFechaIngreso);
			
			textFieldFechaIngreso = new JTextField();
			textFieldFechaIngreso.setColumns(10);
			textFieldFechaIngreso.setBounds(266, 267, 210, 19);
			contentPane.add(textFieldFechaIngreso);
			
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					principal = new VentanaPrincipalMedico(dni);
					principal.setVisible(true);
					dispose();
				}
			});
			btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnVolver.setBounds(45, 330, 111, 35);
			contentPane.add(btnVolver);
			
			btnDarDeAlts = new JButton("Dar de alta");
			btnDarDeAlts.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int option = JOptionPane.showConfirmDialog(null, "¿Desea dar de alta al paciente?", "Confirmacion", JOptionPane.YES_NO_OPTION);
					if(option == JOptionPane.YES_OPTION) {
						int option1 = JOptionPane.showConfirmDialog(null, "¿Desea añadir la enfermedad al historial medico?", "Confirmacion", JOptionPane.YES_NO_OPTION);
						if(option1== JOptionPane.YES_OPTION) {
							selectedDni = comboBoxDniPacientes.getSelectedItem().toString();
							anadirHistorial = new AnadirEnfermedadAHistorial(dni, selectedDni);
							anadirHistorial.setVisible(true);
							dispose();
							Optional<Document> paciente = controllerMedico.findByDniPaciente(selectedDni);
							if(paciente.isPresent()) {
								Boolean actualizado = controllerMedico.eliminarDiagnostico(paciente, "Enfermedad", "Tipo", "Fecha_Ingreso");
								if (actualizado) {
									lblMensaje.setText("Paciente dado de alta");
									lblMensaje.setForeground(Color.GREEN);
									textFieldEnfermedad.setText("");
									textFieldFechaIngreso.setText("");
									textFieldTipo.setText("");
								} else {
									lblMensaje.setText("Paciente no ha sido dado de alta");
									lblMensaje.setForeground(Color.RED);
								}
							}
						}else {
							
							selectedDni = comboBoxDniPacientes.getSelectedItem().toString();
							Optional<Document> paciente = controllerMedico.findByDniPaciente(selectedDni);
							if(paciente.isPresent()) {
								Boolean actualizado = controllerMedico.eliminarDiagnostico(paciente, "Enfermedad", "Tipo", "Fecha_Ingreso");
								if (actualizado) {
									lblMensaje.setText("Paciente dado de alta");
									lblMensaje.setForeground(Color.GREEN);
									textFieldEnfermedad.setText("");
									textFieldFechaIngreso.setText("");
									textFieldTipo.setText("");
								} else {
									lblMensaje.setText("Paciente no ha sido dado de alta");
									lblMensaje.setForeground(Color.RED);
								}
							}
						}
					}else{
						
					}
				}
			});
			btnDarDeAlts.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnDarDeAlts.setBounds(384, 330, 131, 35);
			contentPane.add(btnDarDeAlts);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(ModificarEnfermedadTipo.this, "El DNI " + dni + " no tiene pacientes a cargo");
		}
	}
}
