package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.text.ParseException;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import org.bson.Document;

import controller.MedicoController;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldApellidos, textFieldNombre, textFieldSexo;
	private JLabel labelPaswd, Title, name, surname, dob, gender, dni, lblMensaje;
	private MaskFormatter mask;
	private JPasswordField passwordField;
	JButton btnAceptar, btnCancelar;
	JFormattedTextField formattedDni, formattedFecha_Nacimiento;
	MedicoController medicoController = new MedicoController();
	InicioSesion inicio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registro frame = new Registro();
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
	public Registro() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 420);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setForeground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		Title = new JLabel("Crea una cuenta Medica");
		Title.setFont(new Font("Tahoma", Font.BOLD, 17));
		Title.setBounds(10, 10, 211, 62);
		contentPane.add(Title);

		name = new JLabel("Nombre:");
		name.setFont(new Font("Tahoma", Font.PLAIN, 12));
		name.setBounds(73, 132, 56, 21);
		contentPane.add(name);

		surname = new JLabel("Apellidos:");
		surname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		surname.setBounds(310, 127, 56, 31);
		contentPane.add(surname);

		dob = new JLabel("Fecha de nacimiento:");
		dob.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dob.setBounds(7, 180, 145, 21);
		contentPane.add(dob);

		gender = new JLabel("Sexo:");
		gender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gender.setBounds(329, 182, 30, 17);
		contentPane.add(gender);

		dni = new JLabel("DNI:");
		dni.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dni.setBounds(87, 93, 45, 13);
		contentPane.add(dni);

		textFieldApellidos = new JTextField();
		textFieldApellidos.setBounds(376, 134, 135, 19);
		contentPane.add(textFieldApellidos);
		textFieldApellidos.setColumns(10);

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(139, 134, 135, 19);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);

		textFieldSexo = new JTextField();
		textFieldSexo.setColumns(10);
		textFieldSexo.setBounds(376, 182, 135, 19);
		contentPane.add(textFieldSexo);

		labelPaswd = new JLabel("Contraseña:");
		labelPaswd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPaswd.setBounds(300, 94, 79, 13);
		contentPane.add(labelPaswd);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dni = formattedDni.getText();
				char[] password = passwordField.getPassword();
				String passwordString = new String(password);
				Optional<Document> dniMedico = medicoController.comprobarDni(dni);
				if (dniMedico.isPresent()) {
					Boolean anadido = medicoController.anadirContraseña(dni, "Contraseña", passwordString);
					if (anadido == true) {
						lblMensaje.setText("El registro ha sido realizado con exito");
						lblMensaje.setForeground(Color.GREEN);
					} else {
						lblMensaje.setText("El registro no ha sido realizado con exito");
						lblMensaje.setForeground(Color.RED);
					}
					inicio = new InicioSesion();
					inicio.setVisible(true);
					dispose();
				}

			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnAceptar.setBounds(327, 262, 120, 44);
		contentPane.add(btnAceptar);

		try {
			mask = new MaskFormatter("########?");
			mask.setValidCharacters("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			formattedDni = new JFormattedTextField(mask);
			formattedDni.setBounds(136, 92, 138, 19);
			contentPane.add(formattedDni);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		passwordField = new JPasswordField();
		passwordField.setBounds(376, 92, 135, 19);
		contentPane.add(passwordField);

		try {
			mask = new MaskFormatter("##/##/####");
			mask.setValidCharacters("0123456789");
			formattedFecha_Nacimiento = new JFormattedTextField(mask);
			formattedFecha_Nacimiento.setBounds(139, 182, 135, 19);
			contentPane.add(formattedFecha_Nacimiento);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		lblMensaje = new JLabel("");
		lblMensaje.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMensaje.setBounds(76, 334, 425, 37);
		contentPane.add(lblMensaje);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inicio = new InicioSesion();
				inicio.setVisible(true);
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCancelar.setBounds(126, 262, 120, 44);
		contentPane.add(btnCancelar);
	}
}
