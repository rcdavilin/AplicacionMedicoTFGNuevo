package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

public class InicioSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPasswordField passwordField;
	private Registro registro;
	private JPanel contentPane;
	private JLabel usernameLabel, passwordLabel;
	private JButton loginButton, registerButton;
	private JFormattedTextField formattedDni;
	private MaskFormatter mascara;
	private MedicoController medicoController = new MedicoController();
	private VentanaPrincipalMedico vpm;
	private JRadioButton rdbtnMostrarContraseña;
	private CambioContraseña cambio;
	private String username;
	private JLabel cambiarContrasenaLabel;
	private JRadioButton rdGuardarUsuario;

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
		setBounds(100, 100, 586, 446);

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
		loginButton.setBounds(292, 258, 150, 40);
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(loginButton);

		registerButton = new JButton("Registrarse");
		registerButton.setBounds(133, 258, 121, 40);
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
		
		rdGuardarUsuario = new JRadioButton("Guardar usuario");
		rdGuardarUsuario.setBackground(new Color(230, 230, 250));
		rdGuardarUsuario.setBounds(164, 214, 200, 21);
		contentPane.add(rdGuardarUsuario);
		
		JLabel lblTitulo = new JLabel("Medicos Hospital la Paloma\r\n");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitulo.setBounds(172, 39, 248, 21);
		contentPane.add(lblTitulo);

		ActionListener loginAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				performLogin();
			}
		};

		loginButton.addActionListener(loginAction);

		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					performLogin();
				}
			}
		});

		formattedDni.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					performLogin();
				}
			}
		});
		loadUserAndPassword();
	}

	private void performLogin() {
		username = formattedDni.getText();
		String password = new String(passwordField.getPassword());

		Optional<Document> dni = medicoController.comprobarDni(username);
		if (dni.isPresent() && medicoController.authenticateUser(username, password)) {
			if(rdGuardarUsuario.isSelected()) {
				saveUserAndPassword(username, password);
			}
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
	private void saveUserAndPassword(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("src/main/resources/user_credentials.txt", false))) {
            writer.write("Usuario: " + username + ", Contraseña: " + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserAndPassword() {
        File file = new File("src/main/resources/user_credentials.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                if (line != null && !line.trim().isEmpty()) {
                    String[] parts = line.split(", ");
                    if (parts.length == 2) {
                        String username = parts[0].split(": ")[1];
                        String password = parts[1].split(": ")[1];
                        formattedDni.setText(username);
                        passwordField.setText(password);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
