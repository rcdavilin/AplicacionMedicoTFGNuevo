package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.MedicoController;
import javax.swing.UIManager;

public class VerEnfermedades extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldEnfermedad;
    private JTextField textFieldFechaBaja;
    private JTextField textFieldTratamiento;
    private JTextField textFieldInforme;
    static String dni, selectedDni;
    JLabel lblEnfermedad;
    JLabel lblFecha;
    JLabel lblTratamiento;
    JLabel lblDetalles;
    JLabel lblMedicamentosParaEl;
    JLabel lblInforme;
    JLabel lblTitulo;
    JScrollPane scrollPaneMedicamentosTratamiento;
    JTextArea textAreaMedicamentosTratamiento;
    MedicoController controllerMedico = new MedicoController();
    ArrayList<String> enfermedad, fechaBaja, fechaAlta, tratamiento, informe;
    ArrayList<ArrayList<String>> medicamentos;
    private static int posicionEnfermedad = 0;
    JButton btnSiguienteEnfermedad;
    JButton btnAnteriorEnfermedad;
    JButton btnVolver;
    VentanaPrincipalMedico principal;
    VerHistorialMedico historial;
    private JLabel lblFechaAlta;
    private JTextField textFieldAlta;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VerEnfermedades frame = new VerEnfermedades(dni, selectedDni);
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
    public VerEnfermedades(String dni, String selectedDni) {
        VerEnfermedades.dni = dni;
        VerEnfermedades.selectedDni = selectedDni;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 562, 558);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(230, 230, 250));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        
        enfermedad = controllerMedico.findEnfermedad(selectedDni);
        fechaBaja = controllerMedico.findFechaBaja(selectedDni);
        fechaAlta = controllerMedico.findFechaAlta(selectedDni);
        tratamiento = controllerMedico.findTratamiento(selectedDni);
        medicamentos = controllerMedico.findMedicamentosTratamiento(selectedDni);
        informe = controllerMedico.findInformeHistorialMedico(selectedDni);

        lblEnfermedad = new JLabel("Enfermedad:");
        lblEnfermedad.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEnfermedad.setBounds(121, 86, 97, 22);
        contentPane.add(lblEnfermedad);

        textFieldEnfermedad = new JTextField();
        textFieldEnfermedad.setEditable(false);
        textFieldEnfermedad.setColumns(10);
        textFieldEnfermedad.setBounds(269, 86, 180, 22);
        contentPane.add(textFieldEnfermedad);

        textFieldFechaBaja = new JTextField();
        textFieldFechaBaja.setEditable(false);
        textFieldFechaBaja.setColumns(10);
        textFieldFechaBaja.setBounds(269, 133, 180, 22);
        contentPane.add(textFieldFechaBaja);

        lblFecha = new JLabel(" Fecha baja:");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFecha.setBounds(121, 131, 97, 22);
        contentPane.add(lblFecha);

        lblTratamiento = new JLabel("Tratamiento:");
        lblTratamiento.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblTratamiento.setBounds(121, 236, 97, 22);
        contentPane.add(lblTratamiento);

        textFieldTratamiento = new JTextField();
        textFieldTratamiento.setEditable(false);
        textFieldTratamiento.setColumns(10);
        textFieldTratamiento.setBounds(269, 236, 180, 22);
        contentPane.add(textFieldTratamiento);

        lblDetalles = new JLabel("Detalles:");
        lblDetalles.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDetalles.setBounds(121, 202, 85, 22);
        contentPane.add(lblDetalles);

        lblMedicamentosParaEl = new JLabel("Medicamentos");
        lblMedicamentosParaEl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblMedicamentosParaEl.setBounds(121, 283, 120, 22);
        contentPane.add(lblMedicamentosParaEl);

        scrollPaneMedicamentosTratamiento = new JScrollPane();
        scrollPaneMedicamentosTratamiento.setBounds(269, 282, 180, 70);
        contentPane.add(scrollPaneMedicamentosTratamiento);

        textAreaMedicamentosTratamiento = new JTextArea();
        textAreaMedicamentosTratamiento.setEditable(false);
        scrollPaneMedicamentosTratamiento.setViewportView(textAreaMedicamentosTratamiento);

        lblInforme = new JLabel("Informe:");
        lblInforme.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblInforme.setBounds(121, 360, 97, 22);
        contentPane.add(lblInforme);

        textFieldInforme = new JTextField();
        textFieldInforme.setEditable(false);
        textFieldInforme.setColumns(10);
        textFieldInforme.setBounds(269, 362, 180, 22);
        contentPane.add(textFieldInforme);

        lblTitulo = new JLabel("Informacion de las enfermedades del paciente");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTitulo.setBounds(150, 22, 328, 32);
        contentPane.add(lblTitulo);

        btnSiguienteEnfermedad = new JButton("Siguiente Enfermedad");
        btnSiguienteEnfermedad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (posicionEnfermedad < enfermedad.size() - 1) {
                    posicionEnfermedad++;
                    mostrarDatosEnfermedad();
                    actualizarEstadoBotones();
                }
                
            }
        });

        btnSiguienteEnfermedad.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnSiguienteEnfermedad.setBounds(299, 435, 179, 32);
        contentPane.add(btnSiguienteEnfermedad);

        btnAnteriorEnfermedad = new JButton("Anterior Enfermedad");
        btnAnteriorEnfermedad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (posicionEnfermedad > 0) {
                    posicionEnfermedad--;
                    mostrarDatosEnfermedad();
                    actualizarEstadoBotones();
                }
                
            }
        });
        btnAnteriorEnfermedad.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnAnteriorEnfermedad.setBounds(88, 435, 153, 32);
        contentPane.add(btnAnteriorEnfermedad);

        

        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                principal = new VentanaPrincipalMedico(dni);
                principal.setVisible(true);
                dispose();
            }
        });
        btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnVolver.setBackground(UIManager.getColor("Button.background"));
        btnVolver.setBounds(35, 29, 85, 21);
        contentPane.add(btnVolver);
        
        lblFechaAlta = new JLabel(" Fecha alta:");
        lblFechaAlta.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFechaAlta.setBounds(121, 170, 97, 22);
        contentPane.add(lblFechaAlta);
        
        textFieldAlta = new JTextField();
        textFieldAlta.setEditable(false);
        textFieldAlta.setColumns(10);
        textFieldAlta.setBounds(269, 172, 180, 22);
        contentPane.add(textFieldAlta);

        if (!enfermedad.isEmpty()) {
            mostrarDatosEnfermedad();
        }
        actualizarEstadoBotones();
    }

    private void mostrarDatosEnfermedad() {
        textFieldEnfermedad.setText(enfermedad.get(posicionEnfermedad));
        textFieldFechaBaja.setText(fechaBaja.get(posicionEnfermedad));
        textFieldAlta.setText(fechaAlta.get(posicionEnfermedad));
        textFieldTratamiento.setText(tratamiento.get(posicionEnfermedad));
        textFieldInforme.setText(informe.get(posicionEnfermedad));
        textAreaMedicamentosTratamiento.setText(String.join("\n", medicamentos.get(posicionEnfermedad)));
    }
    
    private void actualizarEstadoBotones() {
        btnAnteriorEnfermedad.setEnabled(posicionEnfermedad > 0);
        btnSiguienteEnfermedad.setEnabled(posicionEnfermedad < enfermedad.size() - 1);
    }
}
