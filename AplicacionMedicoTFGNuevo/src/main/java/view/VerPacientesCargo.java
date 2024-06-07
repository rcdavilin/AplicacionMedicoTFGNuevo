package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.MedicoController;

public class VerPacientesCargo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	JLabel lblNewLabel;
	MedicoController controllerMedico = new MedicoController();
	String[] dniPaciente;
	JComboBox<String> comboBoxDniPacientes;
	String pacientes, selectedDni;
	VentanaPrincipalMedico vpm;
	private JTextField textFieldDni, textFieldNombre, textFieldApellidos;
	JLabel lblFechaIncorporacion, lblEspecialidad, lblApellidos, lblNombre, lblDNI, lblIntroduzcaNombre;
	JFormattedTextField formattedTextFieldDni;
	JButton btnVolver;
	VentanaPrincipalMedico principal;

	JTextField textFieldFechaNacimiento, textFieldSexo;
	JLabel lblSexo, lblFechaNacimiento;

	JLabel lblLugarDeNacimiento;
	JTextField textFieldLugarNacimiento;
	JLabel lblAltura;
	JTextField textFieldAltura;
	JLabel lblPeso;
	JTextField textFieldPeso;
	JLabel lblGrupoSanguineo;
	JTextField textFieldGrupoSanguineo;
	private JLabel lblVerInformacionDe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerPacientesCargo frame = new VerPacientesCargo(dni);
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
	public VerPacientesCargo(String dni) {
		try {
			VerPacientesCargo.dni = dni;
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
			lblIntroduzcaNombre.setBounds(115, 63, 210, 22);
			contentPane.add(lblIntroduzcaNombre);

			lblDNI = new JLabel("DNI:");
			lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblDNI.setBounds(93, 147, 50, 22);
			contentPane.add(lblDNI);

			lblNombre = new JLabel("Nombre:");
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNombre.setBounds(93, 184, 50, 22);
			contentPane.add(lblNombre);

			lblApellidos = new JLabel("Apellidos:");
			lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblApellidos.setBounds(93, 216, 62, 22);
			contentPane.add(lblApellidos);

			lblFechaNacimiento = new JLabel("Fecha Nacimiento:");
			lblFechaNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblFechaNacimiento.setBounds(93, 248, 119, 22);
			contentPane.add(lblFechaNacimiento);

			lblSexo = new JLabel("Sexo:");
			lblSexo.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblSexo.setBounds(93, 280, 119, 19);
			contentPane.add(lblSexo);

			textFieldDni = new JTextField();
			textFieldDni.setEditable(false);
			textFieldDni.setBounds(231, 150, 180, 19);
			contentPane.add(textFieldDni);
			textFieldDni.setColumns(10);

			textFieldNombre = new JTextField();
			textFieldNombre.setEditable(false);
			textFieldNombre.setColumns(10);
			textFieldNombre.setBounds(231, 184, 180, 19);

			contentPane.add(textFieldNombre);

			textFieldApellidos = new JTextField();
			textFieldApellidos.setEditable(false);
			textFieldApellidos.setColumns(10);
			textFieldApellidos.setBounds(231, 219, 180, 19);

			contentPane.add(textFieldApellidos);

			textFieldFechaNacimiento = new JTextField();
			textFieldFechaNacimiento.setEditable(false);
			textFieldFechaNacimiento.setColumns(10);
			textFieldFechaNacimiento.setBounds(231, 251, 180, 19);

			contentPane.add(textFieldFechaNacimiento);

			textFieldSexo = new JTextField();
			textFieldSexo.setEditable(false);
			textFieldSexo.setColumns(10);
			textFieldSexo.setBounds(231, 281, 180, 19);

			contentPane.add(textFieldSexo);

			lblLugarDeNacimiento = new JLabel("Lugar de Nacimiento:");
			lblLugarDeNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblLugarDeNacimiento.setBounds(93, 309, 119, 19);
			contentPane.add(lblLugarDeNacimiento);

			textFieldLugarNacimiento = new JTextField();
			textFieldLugarNacimiento.setEditable(false);
			textFieldLugarNacimiento.setColumns(10);
			textFieldLugarNacimiento.setBounds(231, 310, 180, 19);
			contentPane.add(textFieldLugarNacimiento);

			lblAltura = new JLabel("Altura:");
			lblAltura.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblAltura.setBounds(93, 344, 119, 19);
			contentPane.add(lblAltura);

			textFieldAltura = new JTextField();
			textFieldAltura.setEditable(false);
			textFieldAltura.setColumns(10);
			textFieldAltura.setBounds(231, 345, 180, 19);
			contentPane.add(textFieldAltura);

			lblPeso = new JLabel("Peso:");
			lblPeso.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblPeso.setBounds(93, 373, 119, 19);
			contentPane.add(lblPeso);

			textFieldPeso = new JTextField();
			textFieldPeso.setEditable(false);
			textFieldPeso.setColumns(10);
			textFieldPeso.setBounds(231, 374, 180, 19);
			contentPane.add(textFieldPeso);

			lblGrupoSanguineo = new JLabel("Grupo sanguineo:");
			lblGrupoSanguineo.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblGrupoSanguineo.setBounds(93, 404, 119, 19);
			contentPane.add(lblGrupoSanguineo);

			textFieldGrupoSanguineo = new JTextField();
			textFieldGrupoSanguineo.setEditable(false);
			textFieldGrupoSanguineo.setColumns(10);
			textFieldGrupoSanguineo.setBounds(231, 405, 180, 19);
			contentPane.add(textFieldGrupoSanguineo);

			comboBoxDniPacientes = new JComboBox<String>();
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			model.addElement("");
			for (int i = 0; i < dniPaciente.length; i++) {
				model.addElement(dniPaciente[i]);
			}
			comboBoxDniPacientes.setModel(model);
			comboBoxDniPacientes.setBounds(336, 65, 179, 21);
			comboBoxDniPacientes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
					selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
					textFieldDni.setText(controllerMedico.findDniPacientePorDni(selectedDni));
					textFieldNombre.setText(controllerMedico.findNombrePacientePorDni(selectedDni));
					textFieldApellidos.setText(controllerMedico.findApellidosPacientePorDni(selectedDni));
					textFieldFechaNacimiento.setText(controllerMedico.findFechaNacimientoPorDni(selectedDni));
					textFieldSexo.setText(controllerMedico.findSexoPorDni(selectedDni));
					textFieldLugarNacimiento.setText(controllerMedico.findLugarNacimientoPorDni(selectedDni));
					textFieldAltura.setText(controllerMedico.findAlturaPorDni(selectedDni) + "");
					textFieldPeso.setText(controllerMedico.findPesoPorDni(selectedDni) + "");
					textFieldGrupoSanguineo.setText(controllerMedico.findGrupoSanguineoPorDni(selectedDni));
					}catch (NullPointerException e1) {
						JOptionPane.showMessageDialog(VerPacientesCargo.this,
							"Escoga un dni");
					}

				}
			});
			contentPane.add(comboBoxDniPacientes);

			textFieldDni = new JTextField();
			textFieldDni.setEditable(false);
			textFieldDni.setBounds(231, 150, 180, 19);
			contentPane.add(textFieldDni);
			textFieldDni.setColumns(10);

			textFieldNombre = new JTextField();
			textFieldNombre.setEditable(false);
			textFieldNombre.setColumns(10);
			textFieldNombre.setBounds(231, 184, 180, 19);

			contentPane.add(textFieldNombre);

			textFieldApellidos = new JTextField();
			textFieldApellidos.setEditable(false);
			textFieldApellidos.setColumns(10);
			textFieldApellidos.setBounds(231, 219, 180, 19);

			contentPane.add(textFieldApellidos);

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
			btnVolver.setBounds(10, 64, 85, 21);
			contentPane.add(btnVolver);
			
			lblVerInformacionDe = new JLabel("Ver informacion de los pacientes a cargo");
			lblVerInformacionDe.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblVerInformacionDe.setBounds(115, 20, 327, 21);
			contentPane.add(lblVerInformacionDe);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(VerPacientesCargo.this, "El DNI " + dni + " no pacientes a cargo");
		}

	}
}
