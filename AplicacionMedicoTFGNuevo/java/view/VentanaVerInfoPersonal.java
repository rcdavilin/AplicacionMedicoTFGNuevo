package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controller.MedicoController;

public class VentanaVerInfoPersonal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldDni, textFieldNombre, textFieldApellidos, textFieldEspecialidad,
			textFieldFechaIncorporacion;
	JLabel lblFechaIncorporacion, lblEspecialidad, lblApellidos, lblNombre, lblDNI, lblIntroduzcaNombre;
	private MedicoController controllerInterfaz = new MedicoController();
	private MaskFormatter mascara;
	JButton btnVolver;
	VentanaPrincipalMedico principal;
	static String dni;
	private JLabel lblVerInformacionPersonal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaVerInfoPersonal frame = new VentanaVerInfoPersonal(dni);
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
	public VentanaVerInfoPersonal(String dni) {
		VentanaVerInfoPersonal.dni = dni;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 468);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblIntroduzcaNombre = new JLabel("Informacion del medico: ");
		lblIntroduzcaNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIntroduzcaNombre.setBounds(171, 98, 210, 22);
		contentPane.add(lblIntroduzcaNombre);

		lblDNI = new JLabel("DNI:");
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDNI.setBounds(96, 166, 50, 22);
		contentPane.add(lblDNI);

		lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombre.setBounds(96, 203, 50, 22);
		contentPane.add(lblNombre);

		lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblApellidos.setBounds(96, 235, 62, 22);
		contentPane.add(lblApellidos);

		lblEspecialidad = new JLabel("Especialdad:");
		lblEspecialidad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEspecialidad.setBounds(96, 267, 119, 22);
		contentPane.add(lblEspecialidad);

		lblFechaIncorporacion = new JLabel("Fecha incorporacion:");
		lblFechaIncorporacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFechaIncorporacion.setBounds(96, 299, 119, 19);
		contentPane.add(lblFechaIncorporacion);

		try {
			mascara = new MaskFormatter("########?");
			mascara.setValidCharacters("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");

		} catch (ParseException e) {
			e.printStackTrace();
		}

		textFieldDni = new JTextField();
		textFieldDni.setEditable(false);
		textFieldDni.setBounds(234, 169, 180, 19);
		contentPane.add(textFieldDni);
		textFieldDni.setText(controllerInterfaz.findDniMedicoPorDni(dni));
		textFieldDni.setColumns(10);

		textFieldNombre = new JTextField();
		textFieldNombre.setEditable(false);
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(234, 203, 180, 19);
		textFieldNombre.setText(controllerInterfaz.findNombreMedicoPorDni(dni));
		contentPane.add(textFieldNombre);

		textFieldApellidos = new JTextField();
		textFieldApellidos.setEditable(false);
		textFieldApellidos.setColumns(10);
		textFieldApellidos.setBounds(234, 238, 180, 19);
		textFieldApellidos.setText(controllerInterfaz.findApellidosMedicoPorDni(dni));
		contentPane.add(textFieldApellidos);

		textFieldEspecialidad = new JTextField();
		textFieldEspecialidad.setEditable(false);
		textFieldEspecialidad.setColumns(10);
		textFieldEspecialidad.setBounds(234, 270, 180, 19);
		textFieldEspecialidad.setText(controllerInterfaz.findEspecialidadPorDni(dni));
		contentPane.add(textFieldEspecialidad);

		textFieldFechaIncorporacion = new JTextField();
		textFieldFechaIncorporacion.setEditable(false);
		textFieldFechaIncorporacion.setColumns(10);
		textFieldFechaIncorporacion.setBounds(234, 300, 180, 19);
		textFieldFechaIncorporacion.setText(controllerInterfaz.findFechaIncoporacionPorDni(dni));
		contentPane.add(textFieldFechaIncorporacion);

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
		btnVolver.setBounds(29, 99, 85, 21);
		contentPane.add(btnVolver);
		
		lblVerInformacionPersonal = new JLabel("Ver informacion personal");
		lblVerInformacionPersonal.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblVerInformacionPersonal.setBounds(150, 26, 217, 21);
		contentPane.add(lblVerInformacionPersonal);

	}
}
