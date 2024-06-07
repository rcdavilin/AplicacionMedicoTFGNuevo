package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class AsignarCitaPaciente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	String[] dniPaciente;
	MedicoController controllerMedico = new MedicoController();
	JComboBox<String> comboBoxDniPacientes;
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
					AsignarCitaPaciente frame = new AsignarCitaPaciente(dni);
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
	public AsignarCitaPaciente(String dni) {
		try {
			AsignarCitaPaciente.dni = dni;
			dniPaciente = controllerMedico.dniPacientes(dni);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 665, 489);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(230, 230, 250));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			comboBoxDniPacientes = new JComboBox<String>();
			comboBoxDniPacientes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedDni = (String) comboBoxDniPacientes.getSelectedItem();

				}
			});
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			model.addElement("");
			for (int i = 0; i < dniPaciente.length; i++) {
				model.addElement(dniPaciente[i]);
			}
			comboBoxDniPacientes.setModel(model);
			comboBoxDniPacientes.setBounds(346, 46, 179, 21);
			contentPane.add(comboBoxDniPacientes);

			lblTitulo = new JLabel("Asignar cita a los pacientes");
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblTitulo.setBounds(98, 42, 218, 27);
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

					Optional<Document> paciente = controllerMedico.findByDniPaciente(selectedDni);
					if (paciente.isPresent()) {
						String citas = textFieldFechaSeleccionada.getText() + " " + componenteHoras.getFormato();
						;
						Document listaCitas = new Document();
						
						listaCitas.append("DniMedico", dni).append("Fecha", citas);

                        Boolean anadido = controllerMedico.addCitasPaciente(paciente, listaCitas);
						if (anadido == true) {
							lblMensaje.setText("Cita asignada al paceinte con exito");
							lblMensaje.setForeground(Color.GREEN);
						} else {
							lblMensaje.setText("Cita no ha sido asignada al paceinte con exito");
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
			componenteHoras.setBounds(295, 105, 346, 218);
			contentPane.add(componenteHoras);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(AsignarCitaPaciente.this, "El DNI " + dni + " no pacientes a cargo");
		}

	}
}
