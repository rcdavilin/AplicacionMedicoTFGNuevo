package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

public class AsignarEnfemerdadYTipo extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    JLabel lblTipo;
    JComboBox<String> comboBoxTipo;
    JLabel lblEnfermedad;
    JComboBox<String> comboBoxEnfermedad;
    JComboBox<String> comboBoxDniPacientes;
    JLabel lblAsignarDiagnosticoA;
    String[] dniPaciente;
    static String dni;
    String selectedDni;
    MedicoController controllerMedico = new MedicoController();
    JButton btnAceptar;
    JButton btnCancelar;
    JLabel lblMensaje;
    VentanaPrincipalMedico principal;
    String enfermedad, tipo, fecha;
    private JLabel lblFechaDiagnostico;
    private JTextField textFieldFechaDiagnostico;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AsignarEnfemerdadYTipo frame = new AsignarEnfemerdadYTipo(dni);
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
    public AsignarEnfemerdadYTipo(String dni) {
        AsignarCitaPaciente.dni = dni;
        dniPaciente = controllerMedico.dniPacientes(dni);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 522, 427);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(230, 230, 250));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblTipo = new JLabel("Eliga el tipo");
        lblTipo.setVisible(false);
        lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblTipo.setBounds(95, 170, 119, 21);
        contentPane.add(lblTipo);

        comboBoxTipo = new JComboBox<String>();
        comboBoxTipo.setVisible(false);
        comboBoxTipo.setBounds(259, 171, 222, 21);
        contentPane.add(comboBoxTipo);

        lblEnfermedad = new JLabel("Eliga la enfermedad");
        lblEnfermedad.setVisible(false);
        lblEnfermedad.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblEnfermedad.setBounds(98, 104, 136, 21);
        contentPane.add(lblEnfermedad);

        comboBoxEnfermedad = new JComboBox<String>();
        comboBoxEnfermedad.setModel(new DefaultComboBoxModel<String>(new String[] { "", "Mental", "Hormonal",
                "Respiratoria", "Cancer", "Dermatologica", "Digestiva", "Oftalmologica", "Cardiocirculatoria" }));
        comboBoxEnfermedad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enfermedad = comboBoxEnfermedad.getSelectedItem().toString();
                lblTipo.setVisible(true);
                comboBoxTipo.setVisible(true);
                
                switch (enfermedad) {
                case "Mental":
                    comboBoxTipo.setModel(new DefaultComboBoxModel<String>(new String[] { "Depresion", "Ansiedad",
                            "Tourette", "Estres postraumatico", "Trastorno obsesivo-compulsivo", "Ictus" }));
                    break;
                case "Hormonal":
                    comboBoxTipo.setModel(new DefaultComboBoxModel<String>(new String[] { "Diabetes", "Hipertiroidismo",
                            "Hipotiroidismo", "Síndrome del ovario poliquístico", "Enfermedad de Addison",
                            "Enfermedad de Graves" }));
                    break;
                case "Respiratoria":
                    comboBoxTipo.setModel(new DefaultComboBoxModel<String>(new String[] { "Tuberculosis", "Asma",
                            "Asbestosis", "Bronquiolitis", "Bronquitis", "Enfisema", "Empiema" }));
                    break;
                case "Cancer":
                    comboBoxTipo.setModel(new DefaultComboBoxModel<String>(new String[] { "Pulmon", "Piel", "Prostata",
                            "Higado", "Pancreas", "Mama", "Celebral", "Oseo" }));
                    break;
                case "Dermatologica":
                    comboBoxTipo.setModel(new DefaultComboBoxModel<String>(new String[] { "Dermatitis atopica",
                            "Psoriasis", "Acne", "Onicomicosis", "Vitíligo", "Queratosis actínica" }));
                    break;
                case "Digestiva":
                    comboBoxTipo.setModel(new DefaultComboBoxModel<String>(
                            new String[] { "Dispepsia", "Enfermedad por reflujo gastroesofágico", "Enfermedad celiaca",
                                    "Síndrome del intestino irritable", "Colitis ulcerosa", "Gastritis", "Ulceras" }));
                    break;
                case "Oftalmologica":
                    comboBoxTipo.setModel(
                            new DefaultComboBoxModel<String>(new String[] { "Ambliopía", "Astigmatismo", "Cataratas",
                                    "Daltonismo", "Retinopatía diabética", "Síndrome del ojo seco", "Miodesopsias" }));
                    break;
                case "Cardiocirculatoria":
                    comboBoxTipo.setModel(new DefaultComboBoxModel<String>(
                            new String[] { "Anemia", "Hemofilia", "Leucemia", "Infarto de miocardio", "Angina de pecho",
                                    "Insuficiencia cardiaca", "Trastornos del ritmo cardiaco" }));
                    break;
                default:
                    break;

                }
            }
        });
        comboBoxEnfermedad.setVisible(false);
        comboBoxEnfermedad.setBounds(259, 105, 222, 21);
        contentPane.add(comboBoxEnfermedad);

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
                lblEnfermedad.setVisible(true);
                comboBoxEnfermedad.setVisible(true);
                lblFechaDiagnostico.setVisible(true);
                textFieldFechaDiagnostico.setVisible(true);
            }
        });
        comboBoxDniPacientes.setBounds(302, 47, 179, 21);
        contentPane.add(comboBoxDniPacientes);

        lblAsignarDiagnosticoA = new JLabel("Asignar diagnostico a paciente");
        lblAsignarDiagnosticoA.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblAsignarDiagnosticoA.setBounds(39, 43, 233, 27);
        contentPane.add(lblAsignarDiagnosticoA);
        
        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedDni = (String) comboBoxDniPacientes.getSelectedItem();
                Optional<Document> paciente = controllerMedico.findByDniPaciente(selectedDni);
                if(paciente.isPresent()) {
                    enfermedad = comboBoxEnfermedad.getSelectedItem().toString();
                    tipo = comboBoxTipo.getSelectedItem().toString();
                    fecha = textFieldFechaDiagnostico.getText();
                    Boolean actualizado = controllerMedico.actualizarEnfermedadYTipo(paciente, enfermedad, tipo, fecha);
                    if (actualizado) {
                        lblMensaje.setText("Enfermedad y tipo asignada al paceinte con exito");
                        lblMensaje.setForeground(Color.GREEN);
                    } else {
                        lblMensaje.setText("Enfermedad y tipo no ha sido asignada al paceinte con exito");
                        lblMensaje.setForeground(Color.RED);
                    }
                    
                }else {
                    lblMensaje.setText("No existe el paciente con el DNI " + selectedDni);
                    lblMensaje.setForeground(Color.RED);
                }
                
                
            }
        });
        btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnAceptar.setBounds(271, 285, 89, 34);
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
        btnCancelar.setBounds(105, 285, 89, 34);
        contentPane.add(btnCancelar);
        
        lblMensaje = new JLabel("");
        lblMensaje.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblMensaje.setBounds(95, 359, 283, 21);
        contentPane.add(lblMensaje);
        
        lblFechaDiagnostico = new JLabel("Fecha diagnostico");
        lblFechaDiagnostico.setVisible(false);
        lblFechaDiagnostico.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblFechaDiagnostico.setBounds(95, 225, 119, 21);
        contentPane.add(lblFechaDiagnostico);
        
        textFieldFechaDiagnostico = new JTextField();
        textFieldFechaDiagnostico.setVisible(false);
        textFieldFechaDiagnostico.setEditable(false);
        textFieldFechaDiagnostico.setBounds(264, 227, 217, 19);
        contentPane.add(textFieldFechaDiagnostico);
        textFieldFechaDiagnostico.setColumns(10);

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' uuuu HH:mm");
        String fechaFormateada = LocalDateTime.now().format(formateador);
        textFieldFechaDiagnostico.setText(fechaFormateada);

    }
}
