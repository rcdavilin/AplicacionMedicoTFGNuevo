package repository.paciente;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import db.MongoDB;

public class PacienteRepositoryImpl implements PacienteRepository {

	MongoClient mongoClient = MongoDB.getClient();
	MongoDatabase database = mongoClient.getDatabase("TrabajoMongo");
	MongoCollection<Document> collection = database.getCollection("Pacientes");
	String dni = "Dni", nombre = "Nombre", apellidos = "Apellidos", fechaNacimiento = "Fecha_Nacimiento", sexo = "Sexo",
			lugarNacimiento = "Lugar_Nacimiento", altura = "Altura", peso = "Peso", grupo_Sanguineo = "Grupo_Sanguineo",
			enfermedad = "Enfermedad", tipo = "Tipo";

	@Override
	public List<Document> findAll() {
		Bson projectionFields = Projections.fields(Projections.include(dni, nombre, enfermedad, tipo),
				Projections.excludeId());

		MongoCursor<Document> cursor = collection.find().projection(projectionFields).iterator();

		List<Document> documentList = new ArrayList<>();
		try {
			while (cursor.hasNext()) {
				Document document = cursor.next();
				documentList.add(document);
			}
		} finally {
			cursor.close();
		}

		return documentList;
	}

	public Boolean updateHistorialMedico(Optional<Document> paciente, Document historial) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
				Document update = new Document("$push", new Document(historial));
				collection.updateOne(filter, update);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String[] findAlergenos(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		List<String> alergenos = (List<String>) result.get("Alergenos");
		return alergenos.toArray(new String[0]);

	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findEnfermedad(String paciente) {
		Bson filter = eq(dni, paciente);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedadesLista = (ArrayList<Document>) document.get("Enfermedades");
		ArrayList<String> enfermedades = new ArrayList<>();

		for (Document obj : enfermedadesLista) {
			if (obj.containsKey("Enfermedad")) {
				enfermedades.add(obj.getString("Enfermedad"));
			}
		}

		return enfermedades;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findFechaBaja(String paciente) {
		Bson filter = eq(dni, paciente);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Enfermedades");
		ArrayList<String> fecha = new ArrayList<>();

		for (Document obj : enfermedades) {
			if (obj.containsKey("Fecha_Baja")) {
				fecha.add(obj.getString("Fecha_Baja"));
			}
		}

		return fecha;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findFechaAlta(String paciente) {
		Bson filter = eq(dni, paciente);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Enfermedades");
		ArrayList<String> fecha = new ArrayList<>();

		for (Document obj : enfermedades) {
			if (obj.containsKey("Fecha_Alta")) {
				fecha.add(obj.getString("Fecha_Alta"));
			}
		}

		return fecha;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> findTratamiento(String paciente) {
		Bson filter = eq(dni, paciente);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Enfermedades");
		ArrayList<String> tratamiento = new ArrayList<>();

		for (Document obj1 : enfermedades) {
			if (obj1.containsKey("Detalles")) {
				Document obj2 = (Document) obj1.get("Detalles");
				if (obj2.containsKey("Tratamiento")) {
					tratamiento.add(obj2.getString("Tratamiento"));
				}
			}
		}

		return tratamiento;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findInformeHistorialMedico(String paciente) {
		Bson filter = eq(dni, paciente);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Enfermedades");
		ArrayList<String> informe = new ArrayList<>();

		for (Document obj1 : enfermedades) {
			if (obj1.containsKey("Detalles")) {
				Document obj2 = (Document) obj1.get("Detalles");
				if (obj2.containsKey("Informe")) {
					informe.add(obj2.getString("Informe"));
				}
			}
		}

		return informe;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> findMedicamentosTratamiento(String paciente) {
		Bson filter = eq(dni, paciente);
		Document document = collection.find(filter).first();

		if (document == null) {
			return new ArrayList<>();
		}

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Enfermedades");
		ArrayList<ArrayList<String>> medicamentos = new ArrayList<>();

		for (Document obj1 : enfermedades) {
			if (obj1.containsKey("Detalles")) {
				Document obj2 = (Document) obj1.get("Detalles");
				if (obj2.containsKey("Medicamentos")) {
					ArrayList<String> medicamento = (ArrayList<String>) obj2.get("Medicamentos");
					medicamentos.add(new ArrayList<>(medicamento));
				} else {
					medicamentos.add(new ArrayList<>());
				}
			} else {
				medicamentos.add(new ArrayList<>());
			}
		}

		return medicamentos;
	}

	@SuppressWarnings("unchecked")
	public String[] findMedicamentos(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		List<String> medicamentos = (List<String>) result.get("Medicamentos");
		return medicamentos.toArray(new String[0]);
	}

	public String findEnfermedadIngreso(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object dniList = result.get(enfermedad);
		return (String) dniList;

	}

	public String findTipoEnfermedad(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object dniList = result.get(tipo);
		return (String) dniList;

	}

	public String findFechaIngreso(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object dniList = result.get("Fecha_Ingreso");
		return (String) dniList;

	}

	public String findDniPorDni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object dniList = result.get(dni);
		return (String) dniList;

	}

	public String findNombrePordni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object nombreDni = result.get(nombre);
		return (String) nombreDni;

	}

	public String findApellidosPordni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object apellidosDni = result.get(apellidos);
		return (String) apellidosDni;

	}

	public String findFechaNacimientoPordni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object fechaNacimientoList = result.get(fechaNacimiento);
		return (String) fechaNacimientoList;

	}

	public String findSexoPordni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object sexoDni = result.get(sexo);
		return (String) sexoDni;

	}

	public String findLugarNacimientoPordni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object lugarNacimientoDni = result.get(lugarNacimiento);
		return (String) lugarNacimientoDni;

	}

	public Integer findAlturaPordni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object alturaDni = result.get(altura);
		return (Integer) alturaDni;

	}

	public Integer findPesoPordni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object pesoDni = result.get(peso);
		return (Integer) pesoDni;

	}

	public String findGrupoSanguineoPordni(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object grupoSanguineoDni = result.get(grupo_Sanguineo);
		return (String) grupoSanguineoDni;

	}

	public String findDniMedico(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object grupoSanguineoDni = result.get("Dni_Medico");
		return (String) grupoSanguineoDni;

	}
	public String findTelefono(String paciente) {
		Bson filter = eq(dni, paciente);
		Document result = collection.find(filter).first();
		Object grupoSanguineoDni = result.get("Telefono");
		return (String) grupoSanguineoDni;

	}

	@Override
	public Boolean save(Document entity) {
		try {
			collection.insertOne(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Optional<Document> findById(String id) {
		Bson filter = eq(dni, id);
		Bson projectionFields = Projections.excludeId();
		Document result = collection.find(filter).projection(projectionFields).first();
		return Optional.ofNullable(result);
	}

	public List<Document> findByNombre(String nombre) {

		Bson filter = eq("Nombre", nombre);
		Bson projectionFields = Projections.excludeId();

		List<Document> results = collection.find(filter).projection(projectionFields).into(new ArrayList<>());
		return results;
	}

	@Override
	public DeleteResult delete(String dni) {
		DeleteResult resultado = null;
		try {
			Bson filter = eq("Dni", dni);
			resultado = collection.deleteOne(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findCitasPacientes(String medico) {
		Bson filter = eq(dni, medico);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Citas_Paciente");
		ArrayList<String> fecha = new ArrayList<>();

		for (Document obj : enfermedades) {
			if (obj.containsKey("Fecha")) {
				fecha.add(obj.getString("Fecha"));
			}
		}

		return fecha;

	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> findDniMedicoDeCitasPacientes(String medico) {
		Bson filter = eq(dni, medico);
		Document document = collection.find(filter).first();

		ArrayList<Document> enfermedades = (ArrayList<Document>) document.get("Citas_Paciente");
		ArrayList<String> fecha = new ArrayList<>();

		for (Document obj : enfermedades) {
			if (obj.containsKey("DniMedico")) {
				fecha.add(obj.getString("DniMedico"));
			}
		}

		return fecha;

	}

	@SuppressWarnings("unchecked")
	public String[] guardarMedicamentos(String paciente) {
		Bson filter = eq(dni, paciente);
		Document document = collection.find(filter).first();
		List<String> medicamentos = (List<String>) document.get("Tarjeta_Medica");
		return medicamentos.toArray(new String[0]);
	}

	public String guardarMedicamentosString(String paciente) {
		Bson filter = eq(dni, paciente);
		Document document = collection.find(filter).first();
		Object medicamentosTarjeta = document.get("Tarjeta_Medica");
		return (String) medicamentosTarjeta;
	}

	public Boolean eliminarEnfermedadIngreso(Optional<Document> paciente, String enfermedad, String tipo,
			String fecha) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
				collection.updateOne(eq(dni, filter.getString("Dni")),
						Updates.combine(Updates.unset(enfermedad), Updates.unset(tipo), Updates.unset(fecha)));
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean updateCitas(Optional<Document> paciente, String atributo, Document cita) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
				collection.updateOne(eq("Dni", filter.getString("Dni")), Updates.push(atributo, cita));
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean updateMedicamentosTarjeta(Optional<Document> paciente, String atributo, List<String> valor) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
				collection.updateOne(eq(dni, filter.getString("Dni")), Updates.addEachToSet(atributo, valor));
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean eliminarValorDeArray(Optional<Document> paciente, String atributo, String valor) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
				collection.updateOne(eq("Dni", filter.getString("Dni")), Updates.pull(atributo, valor));
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean update(Optional<Document> paciente, String atributo, List<String> valores) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
				Document update = new Document("$set", new Document(atributo, valores));
				collection.updateOne(filter, update);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean updateEnfermedadYTipo(Optional<Document> paciente, String enfermedad, String tipo,
			String fechaIngreso) {
		try {
			if (paciente.isPresent()) {
				Document filter = new Document(dni, paciente.get().getString(dni));
				Document update = new Document("$set", new Document("Enfermedad", enfermedad).append("Tipo", tipo)
						.append("Fecha_Ingreso", fechaIngreso));
				collection.updateOne(filter, update);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean modificarCita(String dni, String dniMedico, String fechaOriginal, String nuevaFecha) {
	    try {
	        Document filter = new Document("Dni", dni).append("Citas_Paciente", new Document("$elemMatch",
	                new Document("DniMedico", dniMedico).append("Fecha", fechaOriginal)));

	        Document update = new Document("$set", new Document("Citas_Paciente.$[e].Fecha", nuevaFecha));

	        Document arrayFilter = new Document("e.DniMedico", dniMedico).append("e.Fecha", fechaOriginal);

	        UpdateOptions options = new UpdateOptions().arrayFilters(Arrays.asList(arrayFilter));

	        UpdateResult result = collection.updateOne(filter, update, options);
	        return result.getModifiedCount() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public Boolean update(Optional<Document> paciente, String atributo, Document valores) {
		try {
			if (paciente.isPresent()) {
				Document filter = paciente.get();
				Document update = new Document("$set", new Document(atributo, valores));
				collection.updateOne(filter, update);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
