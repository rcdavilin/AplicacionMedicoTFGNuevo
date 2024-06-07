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

public class ModificarCitaPaciente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	String[] dniPaciente;
	ArrayList<String> citasMedicas;
	MedicoController controllerMedico = new MedicoController();
	JComboBox<String> comboBoxDniPacientes;
	String selectedDni, selectedCita;
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
	JLabel lblNewLabelCitaAntigua;
	JComboBox<String> comboBoxCitasMedico;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModificarCitaPaciente frame = new ModificarCitaPaciente(dni);
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
	public ModificarCitaPaciente(String dni) {
		try {
			ModificarCitaPaciente.dni = dni;
			dniPaciente = controllerMedico.dniPacientes(dni);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 665, 528);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(230, 230, 250));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			comboBoxDniPacientes = new JComboBox<String>();
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
			model.addElement("");
			for (int i = 0; i < dniPaciente.length; i++) {
				model.addElement(dniPaciente[i]);
			}
			comboBoxDniPacientes.setModel(model);
			comboBoxDniPacientes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
					Optional<Document> paciente = controllerMedico.comprobarDniPaciente(selectedDni);
					if (paciente.isPresent()) {
						lblNewLabelCitaAntigua.setVisible(true);
						comboBoxCitasMedico.setVisible(true);
						citasMedicas = controllerMedico.findbyCitasPaciente(selectedDni);
						DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
						model.addElement("");
						for (int i = 0; i < citasMedicas.size(); i++) {
							model.addElement(citasMedicas.get(i));
						}
						comboBoxCitasMedico.setModel(model);

					}

				}
			});
			comboBoxDniPacientes.setBounds(346, 46, 179, 21);
			contentPane.add(comboBoxDniPacientes);

			lblTitulo = new JLabel("Asignar cita a los pacientes");
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblTitulo.setBounds(98, 42, 218, 27);
			contentPane.add(lblTitulo);

			dateChooserFecha = new JDateChooser();
			dateChooserFecha.setMinSelectableDate(new Date());
			dateChooserFecha.setBounds(30, 215, 233, 19);
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
			rdbtnSeleccionarFecha.setBounds(30, 240, 179, 21);
			contentPane.add(rdbtnSeleccionarFecha);

			lblFechaSeleccionada = new JLabel("Fecha seleccionada");
			lblFechaSeleccionada.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblFechaSeleccionada.setBounds(30, 284, 155, 27);
			contentPane.add(lblFechaSeleccionada);

			textFieldFechaSeleccionada = new JTextField();
			textFieldFechaSeleccionada.setBounds(30, 321, 203, 19);
			contentPane.add(textFieldFechaSeleccionada);
			textFieldFechaSeleccionada.setColumns(10);

			btnAceptar = new JButton("Aceptar");
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
					selectedCita = comboBoxCitasMedico.getSelectedItem().toString();
					String citas = textFieldFechaSeleccionada.getText() + " " + componenteHoras.getFormato();
					try {
						Boolean actualizado = controllerMedico.modificarCita(selectedDni, dni, selectedCita, citas);

						if (actualizado == true) {
							lblMensaje.setText("Cita modificada con exito");
							lblMensaje.setForeground(Color.GREEN);

						} else {
							lblMensaje.setText("Cita no modificada con exito");
							lblMensaje.setForeground(Color.RED);
						}
					} catch (NullPointerException e1) {
						JOptionPane.showMessageDialog(ModificarCitaPaciente.this,
								"El DNI " + dni + " no citas abiertas");
					}
				}
			});
			btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnAceptar.setBounds(367, 423, 85, 27);
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
			btnCancelar.setBounds(178, 423, 85, 27);
			contentPane.add(btnCancelar);

			lblMensaje = new JLabel("");
			lblMensaje.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblMensaje.setBounds(199, 460, 296, 21);
			contentPane.add(lblMensaje);

			componenteHoras = new ComponenteHoras();
			componenteHoras.setBackground(new Color(230, 230, 250));
			componenteHoras.setBounds(295, 184, 346, 211);
			contentPane.add(componenteHoras);

			lblNewLabelCitaAntigua = new JLabel("Cita antigua");
			lblNewLabelCitaAntigua.setVisible(false);
			lblNewLabelCitaAntigua.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblNewLabelCitaAntigua.setBounds(122, 137, 122, 19);
			contentPane.add(lblNewLabelCitaAntigua);

			comboBoxCitasMedico = new JComboBox<String>();
			comboBoxCitasMedico.setVisible(false);
			comboBoxCitasMedico.setBounds(243, 137, 282, 21);
			contentPane.add(comboBoxCitasMedico);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(ModificarCitaPaciente.this, "El DNI " + dni + " no pacientes a cargo");
		}

	}
}
