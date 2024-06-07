package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.text.MaskFormatter;

import org.bson.Document;

import controller.MedicoController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InicioSesion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPasswordField passwordField;
	private Registro registro;
	JPanel contentPane;
	JLabel usernameLabel, passwordLabel;
	JButton loginButton, registerButton;
	JFormattedTextField formattedDni;
	private MaskFormatter mascara;
	MedicoController medicoController = new MedicoController();
	VentanaPrincipalMedico vpm;
	JRadioButton rdbtnMostrarContraseña;
	CambioContraseña cambio;
	String username;
	JLabel cambiarContrasenaLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InicioSesion frame = new InicioSesion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InicioSesion() {

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 456);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		usernameLabel = new JLabel("DNI:");
		usernameLabel.setBounds(75, 96, 100, 30);
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(usernameLabel);

		passwordLabel = new JLabel("Contraseña:");
		passwordLabel.setBounds(75, 147, 89, 30);
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(164, 150, 200, 30);
		contentPane.add(passwordField);

		rdbtnMostrarContraseña = new JRadioButton("Mostrar contraseña");
		rdbtnMostrarContraseña.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnMostrarContraseña.setBackground(new Color(230, 230, 250));
		rdbtnMostrarContraseña.setBounds(380, 153, 145, 21);
		contentPane.add(rdbtnMostrarContraseña);
		rdbtnMostrarContraseña.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnMostrarContraseña.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('\u2022');
				}
			}
		});

		loginButton = new JButton("Iniciar Sesión");
		loginButton.setBounds(292, 234, 150, 40);
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(loginButton);

		registerButton = new JButton("Registrarse");
		registerButton.setBounds(133, 234, 121, 40);
		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(registerButton);
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registro = new Registro();
				registro.setVisible(true);
				dispose();
			}
		});

		try {
			mascara = new MaskFormatter("########?");
			mascara.setValidCharacters("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			formattedDni = new JFormattedTextField(mascara);
			formattedDni.setBounds(164, 100, 200, 27);
			contentPane.add(formattedDni);
			
			

		} catch (ParseException e) {
			e.printStackTrace();
		}
		cambiarContrasenaLabel = new JLabel("Cambiar Contraseña");
		cambiarContrasenaLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cambio = new CambioContraseña();
				cambio.setVisible(true);
				dispose();
			}
		});
		cambiarContrasenaLabel.setBackground(new Color(230, 230, 250));
		cambiarContrasenaLabel.setForeground(Color.BLUE);
		cambiarContrasenaLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cambiarContrasenaLabel.setBounds(164, 187, 200, 21);
		contentPane.add(cambiarContrasenaLabel);

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				username = formattedDni.getText();
				String password = new String(passwordField.getPassword());

				Optional<Document> dni = medicoController.comprobarDni(username);
				if (dni.isPresent() && medicoController.authenticateUser(username, password)) {
					vpm = new VentanaPrincipalMedico(username);
					vpm.setVisible(true);
					dispose();
				} else if (dni.isPresent()) {
					JOptionPane.showMessageDialog(InicioSesion.this,
							"El usuario " + username + " existe pero la contraseña es incorrecta");
				} else {
					JOptionPane.showMessageDialog(InicioSesion.this, "El usuario " + username + " no existe");
				}
			}
		});

	}
}
