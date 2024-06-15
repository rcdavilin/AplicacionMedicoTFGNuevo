package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import controller.MedicoController;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;

public class GenerarInforme extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static String dni;
	private String[] dniPaciente;
	private JComboBox<String> comboBoxDniPacientes;
	private JLabel lblGenerarInforme;
	private JLabel lblIntroduzcaNombre;
	private MedicoController controllerMedico = new MedicoController();
	private JButton btnCancelar;
	private VentanaPrincipalMedico principal;
	private JButton btnGenerar;
	private String selectedDni, nombrePaciente, apellidosPaciente, nombreMedico, apellidosMedico, fechaNaciemiento, sexo,
			especialidad, enfermedad, tipo, fechaIngreso, dniMedico;
	private String[] alergenos;
	private String[] medicamentos;
	private String filePath = "C:\\Users\\mamj2\\JaspersoftWorkspace\\MyReports\\InformePrueba";
	private String fileJRXML = filePath + ".jrxml";
	private HashMap<String, Object> parametros = new HashMap<>();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenerarInforme frame = new GenerarInforme(dni);
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
	public GenerarInforme(String dni) {
		setResizable(false);

		GenerarInforme.dni = dni;
		dniPaciente = controllerMedico.dniPacientes(dni);
		setBackground(new Color(230, 230, 250));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 483, 357);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblIntroduzcaNombre = new JLabel("Introduzca el DNI del paciente");
		lblIntroduzcaNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIntroduzcaNombre.setBounds(47, 81, 210, 22);
		contentPane.add(lblIntroduzcaNombre);

		comboBoxDniPacientes = new JComboBox<String>();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("");
		for (int i = 0; i < dniPaciente.length; i++) {
			model.addElement(dniPaciente[i]);
		}
		comboBoxDniPacientes.setModel(model);
		comboBoxDniPacientes.setBounds(272, 83, 179, 21);
		contentPane.add(comboBoxDniPacientes);

		lblGenerarInforme = new JLabel("Generar informe");
		lblGenerarInforme.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGenerarInforme.setBounds(193, 23, 148, 21);
		contentPane.add(lblGenerarInforme);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> {
			principal = new VentanaPrincipalMedico(dni);
			principal.setVisible(true);
			dispose();
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelar.setBounds(120, 213, 85, 33);
		contentPane.add(btnCancelar);

		btnGenerar = new JButton("Generar");
		btnGenerar.addActionListener(e -> {
			String filepdf = filePath + ".pdf";
			selectedDni = comboBoxDniPacientes.getSelectedItem().toString();
			int option = JOptionPane.showConfirmDialog(null,
					"¿Desea generar el informe al paciente con DNI " + selectedDni + "?", "Generar informe",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				try {
					Optional<Document> paciente = controllerMedico.findByDniPaciente(selectedDni);
					alergenos = controllerMedico.findAlergenosPaciente(selectedDni);
					medicamentos = controllerMedico.findMedicamentosPaciente(selectedDni);
					nombrePaciente = controllerMedico.findNombrePacientePorDni(selectedDni);
					apellidosPaciente = controllerMedico.findApellidosPacientePorDni(selectedDni);
					fechaNaciemiento = controllerMedico.findFechaNacimientoPorDni(selectedDni);
					sexo = controllerMedico.findSexoPorDni(selectedDni);
					enfermedad = controllerMedico.findEnfermedadIngreso(selectedDni);
					tipo = controllerMedico.findTipo(selectedDni);
					fechaIngreso = controllerMedico.findFechaIngreso(selectedDni);
					dniMedico = controllerMedico.findDniMedico(selectedDni);
					nombreMedico = controllerMedico.findNombreMedicoPorDni(dniMedico);
					apellidosMedico = controllerMedico.findApellidosMedicoPorDni(dniMedico);
					especialidad = controllerMedico.findEspecialidadPorDni(dniMedico);

					parametros.put("Dni", selectedDni);
					parametros.put("Nombre", nombrePaciente);
					parametros.put("Apellidos", apellidosPaciente);
					parametros.put("Fecha_Nacimiento", fechaNaciemiento);
					parametros.put("Sexo", sexo);
					parametros.put("Alergenos", String.join(", ", alergenos));
					parametros.put("Medicamentos", String.join(", ", medicamentos));
					parametros.put("Enfermedad", enfermedad);
					parametros.put("Tipo", tipo);
					parametros.put("Fecha_Ingreso", fechaIngreso);
					parametros.put("Dni_Medico", dniMedico);
					parametros.put("Nombre_Medico", nombreMedico);
					parametros.put("Apellidos_Medico", apellidosMedico);
					parametros.put("Especialidad_Medico", especialidad);

					List<Document> documents = new ArrayList<>();
					paciente.ifPresent(documents::add);

					ObjectMapper mapper = new ObjectMapper();
					ArrayNode arrayNode = mapper.createArrayNode();

					for (Document doc : documents) {
						ObjectNode objectNode = mapper.readValue(doc.toJson(), ObjectNode.class);
						arrayNode.add(objectNode);
					}

					String json = arrayNode.toString();

					ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(json.getBytes());
					JsonDataSource dataSource = new JsonDataSource(jsonDataStream);

					JasperReport informeEXE = JasperCompileManager.compileReport(fileJRXML);
					JasperPrint informeGenerado = JasperFillManager.fillReport(informeEXE, parametros, dataSource);

					JDialog progressDialog = new JDialog();
					progressDialog.setTitle("Generando Informe");
					progressDialog.setModal(true);
					progressDialog.setLayout(new BorderLayout());
					progressDialog.setSize(300, 100);
					progressDialog.setLocationRelativeTo(null);

					JProgressBar progressBar = new JProgressBar();
					progressBar.setIndeterminate(true);
					progressDialog.add(progressBar, BorderLayout.CENTER);

					JLabel progressLabel = new JLabel("Generando informe...");
					progressDialog.add(progressLabel, BorderLayout.SOUTH);

					Timer timer = new Timer(7000, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							progressDialog.dispose();
						}
					});
					timer.setRepeats(false);
					timer.start();

					progressDialog.setVisible(true);

					Thread.sleep(10);
					progressDialog.dispose();
					JasperExportManager.exportReportToPdfFile(informeGenerado, filepdf);
					JOptionPane.showMessageDialog(null, "Informe generado con éxito", "Generación de Informe",
							JOptionPane.INFORMATION_MESSAGE);

					Optional<Document> pacienteDni = controllerMedico.comprobarDniPaciente(selectedDni);
					if (pacienteDni.isPresent()) {
						File pdfFile = new File(filepdf);
						byte[] pdfData = new byte[(int) pdfFile.length()];
						try (FileInputStream fis = new FileInputStream(pdfFile)) {
							fis.read(pdfData);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						DateTimeFormatter formateador = DateTimeFormatter
								.ofPattern("EEEE, d 'de' MMMM 'de' uuuu HH:mm");
						String fechaFormateada = LocalDateTime.now().format(formateador);
						Boolean guardado = controllerMedico.anadirInforme(pacienteDni,dni, pdfData, fechaFormateada);
						if (guardado) {
							JOptionPane.showMessageDialog(GenerarInforme.this, "Informe guardado correctamente");
							Timer timer1 = new Timer(1500, new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									principal = new VentanaPrincipalMedico(dni);
									principal.setVisible(true);
									dispose();
								}
							});
							timer1.setRepeats(false);
							timer1.start();

						} else {
							JOptionPane.showMessageDialog(GenerarInforme.this, "Informe no guardado correctamente");

						}
					} else {
						Document informe = controllerMedico.anadirDniPaciente(selectedDni);
						Boolean anadido = controllerMedico.salvarDniMedico(informe);
						if (anadido) {
							File pdfFile = new File(filepdf);

							byte[] pdfData = new byte[(int) pdfFile.length()];
							try (FileInputStream fis = new FileInputStream(pdfFile)) {
								fis.read(pdfData);
							} catch (FileNotFoundException e1) {

								e1.printStackTrace();
							} catch (IOException e1) {

								e1.printStackTrace();
							}
							Optional<Document> pacienteDni1 = controllerMedico.comprobarDniPaciente(selectedDni);
							if (pacienteDni1.isPresent()) {
								DateTimeFormatter formateador = DateTimeFormatter
										.ofPattern("EEEE, d 'de' MMMM 'de' uuuu HH:mm");
								String fechaFormateada = LocalDateTime.now().format(formateador);
								Boolean guardado = controllerMedico.anadirInforme(pacienteDni1,dni, pdfData, fechaFormateada);
								if (guardado) {
									JOptionPane.showMessageDialog(GenerarInforme.this, "Informe guardado correctamente");
									
								} else {
									JOptionPane.showMessageDialog(GenerarInforme.this, "Informe no guardado correctamente");
									
								}

							}else {
								JOptionPane.showMessageDialog(GenerarInforme.this,
										"No se ha encontrado el DNI del paciente correctamente");

							}

						} else {
							JOptionPane.showMessageDialog(GenerarInforme.this,
									"No se ha encontrado el DNI del paciente correctamente");

						}

					}

				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(GenerarInforme.this,
							"El paciente no tiene o un diagnostico hecho o alergenos, medicamentos en su historial");
				} catch (JRException e2) {

					e2.printStackTrace();
				} catch (InterruptedException e2) {

					e2.printStackTrace();
				} catch (JsonMappingException e1) {

					e1.printStackTrace();
				} catch (JsonProcessingException e1) {

					e1.printStackTrace();
				}

			}
		});
		btnGenerar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGenerar.setBounds(276, 213, 85, 33);
		contentPane.add(btnGenerar);
	}
}
