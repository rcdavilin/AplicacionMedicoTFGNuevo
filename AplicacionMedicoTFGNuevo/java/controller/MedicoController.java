package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.Document;

import model.Informe;
import repository.informe.InformeRepositoryImpl;
import repository.medico.MedicoRepositoryImpl;
import repository.paciente.PacienteRepositoryImpl;

public class MedicoController {

	private final MedicoRepositoryImpl medicoRepositoryImpl = new MedicoRepositoryImpl();
	private final PacienteRepositoryImpl pacienteRepositoryImpl = new PacienteRepositoryImpl();
	private final InformeRepositoryImpl informeRespositoryImpl = new InformeRepositoryImpl();

	public Optional<Document> findByDni(String dni) {
		Optional<Document> medico = medicoRepositoryImpl.findById(dni);

		return medico;

	}
	
	public Boolean authenticateUser(String username, String password) {
		Optional<Document> user = medicoRepositoryImpl.findByUsernameAndPassword(username, password);
		return user.isPresent();
	}

	public Boolean anadirVariables(Optional<Document> pacientes, Document enfermedades) {
		Document contenido = new Document();
		contenido.append("Enfermedades", enfermedades);
		return pacienteRepositoryImpl.updateHistorialMedico(pacientes, contenido);
	}
	
	public Document crearDocumentoEnfermedades(Optional<Document> pacientes, String enfermedad, String fecha_baja,
			String fecha_alta, String[] historialMedicoMedicamentos, String tratamiento, String informe) {
		Document enfermedades = new Document();
		Document detalles = new Document();

		enfermedades.append("Enfermedad", enfermedad);
		enfermedades.append("Fecha_Baja", fecha_baja);
		enfermedades.append("Fecha_Alta", fecha_alta);

		List<String> medicamentosList = Arrays.asList(historialMedicoMedicamentos);

		detalles.append("Tratamiento", tratamiento);
		detalles.append("Medicamentos", medicamentosList);
		detalles.append("Informe", informe);
		enfermedades.append("Detalles", detalles);
		return enfermedades;
	}

	public Document anadirDniPaciente(String dni) {
		Document informe;
		informe = new Informe().append("Dni_Paciente", dni);

		return informe;
	}

	public Optional<Document> comprobarDniPaciente(String dni) {

		Optional<Document> informe = informeRespositoryImpl.findById(dni);
		return informe;
	}

	public Boolean salvarDniMedico(Document paciente) {
		return informeRespositoryImpl.save(paciente);
	}

	public String[] findAlergenosPaciente(String dni) {
		String[] medico = pacienteRepositoryImpl.findAlergenos(dni);
		return medico;
	}

	public String[] findMedicamentosPaciente(String dni) {
		String[] medico = pacienteRepositoryImpl.findMedicamentos(dni);
		return medico;
	}

	public ArrayList<ArrayList<String>> findMedicamentosTratamiento(String dni) {
		return pacienteRepositoryImpl.findMedicamentosTratamiento(dni);
	}

	public ArrayList<String> findEnfermedad(String nombre) {
		ArrayList<String> medico = pacienteRepositoryImpl.findEnfermedad(nombre);
		return medico;
	}

	public ArrayList<byte[]> findInformeJaspersoft(String nombre) {
		ArrayList<byte[]> medico = informeRespositoryImpl.verInformes(nombre);
		return medico;
	}

	public ArrayList<String> findInformeHistorialMedico(String nombre) {
		ArrayList<String> medico = pacienteRepositoryImpl.findInformeHistorialMedico(nombre);
		return medico;
	}

	public ArrayList<String> findFechaBaja(String nombre) {
		ArrayList<String> medico = pacienteRepositoryImpl.findFechaBaja(nombre);
		return medico;
	}

	public ArrayList<String> findFechaAlta(String nombre) {
		ArrayList<String> medico = pacienteRepositoryImpl.findFechaAlta(nombre);
		return medico;
	}
	public ArrayList<String> findTratamiento(String nombre) {
		ArrayList<String> medico = pacienteRepositoryImpl.findTratamiento(nombre);
		return medico;
	}

	public String findDniMedicoPorDni(String nombre) {
		String medico = medicoRepositoryImpl.findDniPorDni(nombre);
		return medico;
	}

	public String findNombreMedicoPorDni(String nombre) {
		String medico = medicoRepositoryImpl.findNombrePordni(nombre);
		return medico;
	}

	public String findApellidosMedicoPorDni(String nombre) {
		String medico = medicoRepositoryImpl.findApellidosPordni(nombre);
		return medico;
	}

	public String findEspecialidadPorDni(String nombre) {
		String medico = medicoRepositoryImpl.findEspecialidadPordni(nombre);
		return medico;
	}

	public String findFechaIncoporacionPorDni(String nombre) {
		String medico = medicoRepositoryImpl.findFechaIncorporacionPordni(nombre);
		return medico;
	}

	public String findDniPacientePorDni(String nombre) {
		String medico = pacienteRepositoryImpl.findDniPorDni(nombre);
		return medico;
	}

	public String findNombrePacientePorDni(String nombre) {
		String medico = pacienteRepositoryImpl.findNombrePordni(nombre);
		return medico;
	}

	public String findApellidosPacientePorDni(String nombre) {
		String medico = pacienteRepositoryImpl.findApellidosPordni(nombre);
		return medico;
	}

	public String findFechaNacimientoPorDni(String nombre) {
		String medico = pacienteRepositoryImpl.findFechaNacimientoPordni(nombre);
		return medico;
	}

	public String findSexoPorDni(String nombre) {
		String medico = pacienteRepositoryImpl.findSexoPordni(nombre);
		return medico;
	}

	public String findLugarNacimientoPorDni(String nombre) {
		String medico = pacienteRepositoryImpl.findLugarNacimientoPordni(nombre);
		return medico;
	}

	public Integer findAlturaPorDni(String nombre) {
		Integer medico = pacienteRepositoryImpl.findAlturaPordni(nombre);
		return medico;
	}

	public Integer findPesoPorDni(String nombre) {
		Integer medico = pacienteRepositoryImpl.findPesoPordni(nombre);
		return medico;
	}

	public String findGrupoSanguineoPorDni(String nombre) {
		String medico = pacienteRepositoryImpl.findGrupoSanguineoPordni(nombre);
		return medico;
	}

	public String findEnfermedadIngreso(String nombre) {
		String medico = pacienteRepositoryImpl.findEnfermedadIngreso(nombre);
		return medico;
	}

	public String findTipo(String nombre) {
		String medico = pacienteRepositoryImpl.findTipoEnfermedad(nombre);
		return medico;
	}

	public String findFechaIngreso(String nombre) {
		String medico = pacienteRepositoryImpl.findFechaIngreso(nombre);
		return medico;
	}

	public String findDniMedico(String nombre) {
		String medico = pacienteRepositoryImpl.findDniMedico(nombre);
		return medico;
	}

	public String findTelefono(String nombre) {
		String medico = pacienteRepositoryImpl.findTelefono(nombre);
		return medico;
	}

	public ArrayList<String> findHoraCreacion(String nombre) {
		ArrayList<String> medico = informeRespositoryImpl.findFechaCreacion(nombre);
		return medico;
	}
	
	public ArrayList<String> findDniMedicoInforme(String nombre) {
		ArrayList<String> medico = informeRespositoryImpl.findDniMedicoInforme(nombre);
		return medico;
	}

	public Boolean addCitasPaciente(Optional<Document> dni, Document cita) {
		Boolean actualizado = pacienteRepositoryImpl.updateCitas(dni, "Citas_Paciente", cita);

		return actualizado;
	}

	public Boolean anadirInforme(Optional<Document> dni, String dniMedico,byte[] pdfBytes, String hora) {
		Boolean actualizado = informeRespositoryImpl.guardarInforme(dni, dniMedico,pdfBytes, hora);

		return actualizado;
	}

	public Boolean eliminarDiagnostico(Optional<Document> dni, String enfermedad, String tipo, String fecha) {
		Boolean actualizado = pacienteRepositoryImpl.eliminarEnfermedadIngreso(dni, enfermedad, tipo, fecha);

		return actualizado;
	}

	public Boolean modificarCita(String dni, String dniMedico, String fechaOriginal, String fechaNueva) {
		Boolean actualizado = pacienteRepositoryImpl.modificarCita(dni, dniMedico, fechaOriginal, fechaNueva);

		return actualizado;
	}

	public Boolean actualizarEnfermedadYTipo(Optional<Document> paciente, String enfermedad, String tipo,
			String fecha) {
		return pacienteRepositoryImpl.updateEnfermedadYTipo(paciente, enfermedad, tipo, fecha);
	}

	public Boolean abrirCitasPaciente(Optional<Document> dni, List<String> citas) {
		Boolean actualizado = medicoRepositoryImpl.abrirCitasMedicas(dni, "Citas_Abiertas", citas);

		return actualizado;
	}

	public Optional<Document> findByDniPaciente(String dni) {
		Optional<Document> paciente = pacienteRepositoryImpl.findById(dni);

		return paciente;
	}

	public ArrayList<String> findbyCitasPaciente(String dni) {
		ArrayList<String> medico = pacienteRepositoryImpl.findCitasPacientes(dni);
		return medico;
	}

	public ArrayList<String> findDniMedicobyCitasPaciente(String dni) {
		ArrayList<String> medico = pacienteRepositoryImpl.findDniMedicoDeCitasPacientes(dni);
		return medico;
	}

	public String[] dniPacientes(String dni) {
		String[] dniPacientes = medicoRepositoryImpl.guardarDniPacientes(dni);
		return dniPacientes;
	}

	public String[] medicamentos(String dni) {
		String[] dniPacientes = pacienteRepositoryImpl.guardarMedicamentos(dni);
		return dniPacientes;
	}

	public String[] informes(String dni) {
		String[] dniPacientes = informeRespositoryImpl.guardaInformes(dni);
		return dniPacientes;
	}

	public String medicamentosString(String dni) {
		String dniPacientes = pacienteRepositoryImpl.guardarMedicamentosString(dni);
		return dniPacientes;
	}

	public Optional<Document> comprobarDni(String dni) {
		Optional<Document> medicos = medicoRepositoryImpl.findById(dni);
		return medicos;

	}


	public Boolean anadirTarjeta(Optional<Document> medicos, String[] valor) {
		List<String> list = Arrays.asList(valor);

		Boolean actualizado = pacienteRepositoryImpl.updateMedicamentosTarjeta(medicos, "Tarjeta_Medica", list);

		return actualizado;
	}

	public Boolean eliminarMedicamentoTarjeta(Optional<Document> medicos, String valor) {
		Boolean actualizado = pacienteRepositoryImpl.eliminarValorDeArray(medicos, "Tarjeta_Medica", valor);
		return actualizado;
	}

	public Boolean eliminarInforme(String medicos, String valor) {
		Boolean actualizado = informeRespositoryImpl.eliminarInformePaciente(medicos, valor);
		return actualizado;
	}

	public Boolean anadirContraseña(String dni, String atributo, String valor) {
		Optional<Document> medicos;

		medicos = medicoRepositoryImpl.findById(dni);
		Boolean actualizado = medicoRepositoryImpl.update(medicos, atributo, valor);

		return actualizado;
	}

	public Boolean actualizarContraseña(Optional<Document> medicos, String atributo, String contraseña) {
		return medicoRepositoryImpl.update(medicos, atributo, contraseña);
	}

	public String mostrar(String mensaje) {
		return mensaje;
	}

}
