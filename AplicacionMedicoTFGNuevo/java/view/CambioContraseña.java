package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import org.bson.Document;

import controller.MedicoController;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.text.ParseException;
import java.util.Optional;

import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class CambioContraseña extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	InicioSesion inicio;
	private JLabel lblMensaje, lblDni, lblIntroduzcaNuevaContrasea, lblIntroduzcaDeNuevo;
	JFormattedTextField formattedDni;
	JButton btnCancelar, btnAceptar;
	private MaskFormatter mascara;
	MedicoController medicoController = new MedicoController();
	private JPasswordField passwordFieldContraseña, passwordFieldConfirmarContraseña;
	private JRadioButton rdbtnMostrarContraseña, rdbtnMostrarContraseña_1;
	private JLabel lblTitulo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CambioContraseña frame = new CambioContraseña();
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
	public CambioContraseña() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 563, 394);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblDni = new JLabel("Introduzca el DNI\r\n");
		lblDni.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDni.setBounds(10, 76, 141, 21);
		contentPane.add(lblDni);

		lblIntroduzcaNuevaContrasea = new JLabel("Introduzca nueva contraseña\r\n\r\n");
		lblIntroduzcaNuevaContrasea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIntroduzcaNuevaContrasea.setBounds(10, 124, 180, 21);
		contentPane.add(lblIntroduzcaNuevaContrasea);

		lblIntroduzcaDeNuevo = new JLabel("Introduzca de nuevo la contraseña\r\n\r\n");
		lblIntroduzcaDeNuevo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIntroduzcaDeNuevo.setBounds(10, 168, 205, 21);
		contentPane.add(lblIntroduzcaDeNuevo);

		try {
			mascara = new MaskFormatter("########?");
			mascara.setValidCharacters("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			formattedDni = new JFormattedTextField(mascara);
			formattedDni.setBounds(232, 77, 155, 21);
			contentPane.add(formattedDni);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		passwordFieldContraseña = new JPasswordField();
		passwordFieldContraseña.setBounds(232, 126, 155, 19);
		contentPane.add(passwordFieldContraseña);

		passwordFieldConfirmarContraseña = new JPasswordField();
		passwordFieldConfirmarContraseña.setBounds(232, 170, 155, 19);
		contentPane.add(passwordFieldConfirmarContraseña);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inicio = new InicioSesion();
				inicio.setVisible(true);
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelar.setBounds(120, 229, 98, 31);
		contentPane.add(btnCancelar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dni = formattedDni.getText();
				String password = new String(passwordFieldContraseña.getPassword());
				String confirmarPassword = new String(passwordFieldConfirmarContraseña.getPassword());

				Optional<Document> dniMedico = medicoController.comprobarDni(dni);
				if (dniMedico.isPresent()) {
					if (password.equals(confirmarPassword)) {
						Boolean anadido = medicoController.actualizarContraseña(dniMedico, "Contraseña", password);
						if (anadido == true) {
							lblMensaje.setText("El cambio de contraseña ha sido realizado con exito");
							lblMensaje.setForeground(Color.GREEN);
						} else {
							lblMensaje.setText("El cambio de contraseña no ha sido realizado con exito");
							lblMensaje.setForeground(Color.RED);
						}
						inicio = new InicioSesion();
						inicio.setVisible(true);
						dispose();
					} else {
						lblMensaje.setText("Las contraseñas no son iguales");
						lblMensaje.setForeground(Color.RED);
					}

				}
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAceptar.setBounds(276, 229, 93, 31);
		contentPane.add(btnAceptar);

		lblMensaje = new JLabel("");
		lblMensaje.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMensaje.setBounds(53, 304, 426, 29);
		contentPane.add(lblMensaje);

		rdbtnMostrarContraseña = new JRadioButton("Mostrar contraseña");
		rdbtnMostrarContraseña.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnMostrarContraseña.setBackground(new Color(230, 230, 250));
		rdbtnMostrarContraseña.setBounds(393, 124, 145, 21);
		contentPane.add(rdbtnMostrarContraseña);
		rdbtnMostrarContraseña.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnMostrarContraseña.isSelected()) {
					passwordFieldContraseña.setEchoChar((char) 0);
				} else {
					passwordFieldContraseña.setEchoChar('\u2022');
				}
			}
		});

		rdbtnMostrarContraseña_1 = new JRadioButton("Mostrar contraseña");
		rdbtnMostrarContraseña_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnMostrarContraseña_1.setBackground(new Color(230, 230, 250));
		rdbtnMostrarContraseña_1.setBounds(393, 168, 145, 21);
		contentPane.add(rdbtnMostrarContraseña_1);
		
		lblTitulo = new JLabel("Cambio de contraseña");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitulo.setBounds(175, 26, 217, 21);
		contentPane.add(lblTitulo);
		rdbtnMostrarContraseña_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnMostrarContraseña_1.isSelected()) {
					passwordFieldConfirmarContraseña.setEchoChar((char) 0);
				} else {
					passwordFieldConfirmarContraseña.setEchoChar('\u2022');
				}
			}
		});

	}

}
