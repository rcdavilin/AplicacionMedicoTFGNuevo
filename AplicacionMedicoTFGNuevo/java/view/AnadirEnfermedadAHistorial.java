package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.bson.Document;

import controller.MedicoController;

public class AnadirEnfermedadAHistorial extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> comboBoxDetalles;
	private JButton btnCancelar, btnAcepatr;
	private JLabel lblEnfermedad, lblFechaBaja, lblDetalles, lblTratamiento, lblMedicamentos, lblInforme;
	private JTextField textFieldEnfermedad, textFieldTratamiento, textFieldMedicamentos, textFieldinforme;
	private final MedicoController controllerInterfaz = new MedicoController();
	VentanaPrincipalMedico medico;
	private JLabel lblMensaje;
	private JLabel lblAadirEnfermedadesAl;
	JLabel lblFechaAlta;
	static String dni, selectedDni;
	String enfermedad, fecha_baja, fecha_alta, fechaBajaModificada;
	private JTextField textFieldFecha_Alta;
	private JTextField textFieldFechaBaja;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnadirEnfermedadAHistorial frame = new AnadirEnfermedadAHistorial(dni, selectedDni);
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
	public AnadirEnfermedadAHistorial(String dni, String selectedDni) {
		AnadirEnfermedadAHistorial.dni = dni;
		AnadirEnfermedadAHistorial.selectedDni = selectedDni;

		enfermedad = controllerInterfaz.findEnfermedadIngreso(selectedDni);
		fecha_baja = controllerInterfaz.findFechaIngreso(selectedDni);

		DateTimeFormatter formatoOriginal = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' uuuu HH:mm", new Locale("es", "ES"));
		DateTimeFormatter nuevoFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDateTime fechaIngreso = LocalDateTime.parse(fecha_baja, formatoOriginal);

		fechaBajaModificada = fechaIngreso.format(nuevoFormato);

		

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 504, 502);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblEnfermedad = new JLabel("Enfermedad\r\n");
		lblEnfermedad.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEnfermedad.setBounds(78, 107, 112, 20);
		contentPane.add(lblEnfermedad);

		textFieldEnfermedad = new JTextField();
		textFieldEnfermedad.setColumns(10);
		textFieldEnfermedad.setBounds(200, 110, 143, 20);
		textFieldEnfermedad.setText(enfermedad);
		contentPane.add(textFieldEnfermedad);

		lblFechaBaja = new JLabel("Fecha baja");
		lblFechaBaja.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaBaja.setBounds(78, 141, 112, 20);
		contentPane.add(lblFechaBaja);

		lblDetalles = new JLabel("Detalles");
		lblDetalles.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDetalles.setBounds(78, 230, 77, 14);
		contentPane.add(lblDetalles);

		comboBoxDetalles = new JComboBox<String>();
		comboBoxDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String opcion = comboBoxDetalles.getSelectedItem().toString();
				switch (opcion) {
				case "Si":
					lblTratamiento.setVisible(true);
					textFieldTratamiento.setVisible(true);
					lblMedicamentos.setVisible(true);
					textFieldMedicamentos.setVisible(true);
					textFieldinforme.setVisible(true);
					lblInforme.setVisible(true);

					break;
				case "No":
					break;
				}
			}
		});
		comboBoxDetalles.setModel(new DefaultComboBoxModel<String>(new String[] { "No", "Si" }));
		comboBoxDetalles.setBounds(194, 228, 53, 22);
		contentPane.add(comboBoxDetalles);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				medico = new VentanaPrincipalMedico(dni);
				medico.setVisible(true);
				dispose();
			}
		});
		btnCancelar.setBounds(129, 388, 89, 23);
		contentPane.add(btnCancelar);

		btnAcepatr = new JButton("Aceptar");
		btnAcepatr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnAcepatr == e.getSource()) {
					Optional<Document> pacientes = controllerInterfaz.findByDniPaciente(selectedDni);
					Document enfermedades = crearDocumentosEnfermedades();
					Boolean anadido = controllerInterfaz.anadirVariables(pacientes, enfermedades);
					if (anadido == true) {
						lblMensaje.setText("Enfermedades añadidas con exito");
						lblMensaje.setForeground(Color.GREEN);
					} else {
						lblMensaje.setText("Enfermedades no añadidas con exito");
						lblMensaje.setForeground(Color.RED);
					}

				}
			}

			public Document crearDocumentosEnfermedades() {
				Optional<Document> pacientes = controllerInterfaz.findByDniPaciente(selectedDni);

				String enfermedad = textFieldEnfermedad.getText();
				String fecha_baja = textFieldFechaBaja.getText().toString();
				String fecha_alta = textFieldFecha_Alta.getText();
				String medicamentos = textFieldMedicamentos.getText();
				String tratamiento = textFieldTratamiento.getText();
				String informe = textFieldinforme.getText();
				String[] historialMedicoMedicamentos = medicamentos.split(" ");
				Document enfermedades = controllerInterfaz.crearDocumentoEnfermedades(pacientes, enfermedad, fecha_baja,
						fecha_alta, historialMedicoMedicamentos, tratamiento, informe);
				return enfermedades;
			}

		});
		btnAcepatr.setBounds(296, 388, 89, 23);
		contentPane.add(btnAcepatr);

		lblTratamiento = new JLabel("Tratamiento");
		lblTratamiento.setVisible(false);
		lblTratamiento.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTratamiento.setBounds(78, 267, 112, 14);
		contentPane.add(lblTratamiento);

		textFieldTratamiento = new JTextField();
		textFieldTratamiento.setVisible(false);
		textFieldTratamiento.setColumns(10);
		textFieldTratamiento.setBounds(200, 266, 134, 20);
		contentPane.add(textFieldTratamiento);

		lblMedicamentos = new JLabel("Medicamentos");
		lblMedicamentos.setVisible(false);
		lblMedicamentos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMedicamentos.setBounds(78, 298, 112, 14);
		contentPane.add(lblMedicamentos);

		textFieldMedicamentos = new JTextField();
		textFieldMedicamentos.setVisible(false);
		textFieldMedicamentos.setColumns(10);
		textFieldMedicamentos.setBounds(200, 297, 134, 20);
		contentPane.add(textFieldMedicamentos);

		textFieldinforme = new JTextField();
		textFieldinforme.setVisible(false);
		textFieldinforme.setColumns(10);
		textFieldinforme.setBounds(200, 328, 134, 20);
		contentPane.add(textFieldinforme);

		lblInforme = new JLabel("Informe");
		lblInforme.setVisible(false);
		lblInforme.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblInforme.setBounds(78, 331, 94, 14);
		contentPane.add(lblInforme);

		lblMensaje = new JLabel("");
		lblMensaje.setBounds(129, 435, 241, 20);
		contentPane.add(lblMensaje);

		lblAadirEnfermedadesAl = new JLabel("Añadir enfermedades al historial medico");
		lblAadirEnfermedadesAl.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAadirEnfermedadesAl.setBounds(78, 42, 343, 22);
		contentPane.add(lblAadirEnfermedadesAl);

		lblFechaAlta = new JLabel("Fecha alta");
		lblFechaAlta.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaAlta.setBounds(78, 175, 112, 20);
		contentPane.add(lblFechaAlta);

		textFieldFecha_Alta = new JTextField();
		textFieldFecha_Alta.setBounds(200, 178, 143, 19);
		DateTimeFormatter formateador1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		fecha_alta = LocalDateTime.now().format(formateador1);
		textFieldFecha_Alta.setText(fecha_alta);
		contentPane.add(textFieldFecha_Alta);

		textFieldFechaBaja = new JTextField();
		textFieldFechaBaja.setText("04/06/2024");
		textFieldFechaBaja.setBounds(200, 140, 143, 19);
		textFieldFechaBaja.setText(fechaBajaModificada);
		contentPane.add(textFieldFechaBaja);

	}
}
