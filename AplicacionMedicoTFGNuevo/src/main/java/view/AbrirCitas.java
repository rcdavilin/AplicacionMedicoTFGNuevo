package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.bson.Document;

import com.toedter.calendar.JDateChooser;

import controller.MedicoController;
import miComponente.ComponenteHoras;

public class AbrirCitas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	MedicoController controllerMedico = new MedicoController();
	String selectedDni;
	JLabel lblTitulo;
	JDateChooser dateChooserFecha;
	private JTextField textFieldFechaSeleccionada;
	JLabel lblFechaSeleccionada;
	JRadioButton rdbtnSeleccionarFecha;
	JButton btnAceptar;
	JButton btnCancelar;
	VentanaPrincipalMedico principal;
	JLabel lblMensaje;
	DateFormat formateador;
	ComponenteHoras componenteHoras;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AbrirCitas frame = new AbrirCitas(dni);
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
	public AbrirCitas(String dni) {
		try {
			AbrirCitas.dni = dni;


			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 665, 489);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(230, 230, 250));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			lblTitulo = new JLabel("Abrir citas para los pacientes");
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblTitulo.setBounds(190, 28, 218, 27);
			contentPane.add(lblTitulo);

			dateChooserFecha = new JDateChooser();
			dateChooserFecha.setMinSelectableDate(new Date());
			dateChooserFecha.setBounds(30, 143, 233, 19);
			contentPane.add(dateChooserFecha);

			rdbtnSeleccionarFecha = new JRadioButton("Seleccionar fecha");
			rdbtnSeleccionarFecha.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnSeleccionarFecha.isSelected()) {

						formateador = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' YYYY");
						String fechaFormateada = formateador.format(dateChooserFecha.getDate());
						textFieldFechaSeleccionada.setText(fechaFormateada);
					}
				}
			});
			rdbtnSeleccionarFecha.setBackground(new Color(230, 230, 250));
			rdbtnSeleccionarFecha.setBounds(30, 168, 179, 21);
			contentPane.add(rdbtnSeleccionarFecha);

			lblFechaSeleccionada = new JLabel("Fecha seleccionada");
			lblFechaSeleccionada.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblFechaSeleccionada.setBounds(30, 212, 155, 27);
			contentPane.add(lblFechaSeleccionada);

			textFieldFechaSeleccionada = new JTextField();
			textFieldFechaSeleccionada.setBounds(30, 249, 203, 19);
			contentPane.add(textFieldFechaSeleccionada);
			textFieldFechaSeleccionada.setColumns(10);

			btnAceptar = new JButton("Aceptar");
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					Optional<Document> medico = controllerMedico.findByDni(dni);
					if (medico.isPresent()) {
						String citas = textFieldFechaSeleccionada.getText() + " "
								+ componenteHoras.getFormato();
						ArrayList<String> listaCitas = new ArrayList<String>();
						listaCitas.add(citas);

						Boolean anadido = controllerMedico.abrirCitasPaciente(medico, listaCitas);
						if (anadido == true) {
							lblMensaje.setText("Se han abierto las citas correctamente");
							lblMensaje.setForeground(Color.GREEN);
						} else {
							lblMensaje.setText("No se han abierto las citas correctamente");
							lblMensaje.setForeground(Color.RED);
						}
					} else {
						lblMensaje.setText("No existe el paciente con el DNI " + selectedDni);
						lblMensaje.setForeground(Color.RED);
					}

				}
			});
			btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnAceptar.setBounds(367, 351, 85, 27);
			contentPane.add(btnAceptar);

			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					principal = new VentanaPrincipalMedico(dni);
					principal.setVisible(true);
					dispose();
				}
			});
			btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnCancelar.setBounds(178, 351, 85, 27);
			contentPane.add(btnCancelar);

			lblMensaje = new JLabel("");
			lblMensaje.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblMensaje.setBounds(200, 406, 296, 21);
			contentPane.add(lblMensaje);
			
			componenteHoras = new ComponenteHoras();
			componenteHoras.setBackground(new Color(230, 230, 250));
			componenteHoras.setBounds(316, 88, 325, 226);
			contentPane.add(componenteHoras);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(AbrirCitas.this, "El DNI " + dni + " no pacientes a cargo");
		}

	}
}
