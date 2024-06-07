package repository.medico;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;

import db.MongoDB;

public class MedicoRepositoryImpl implements MedicoRepository {

	MongoClient mongoClient = MongoDB.getClient();
	MongoDatabase database = mongoClient.getDatabase("TrabajoMongo");
	MongoCollection<Document> collection = database.getCollection("Medicos");
	String dni = "Dni", nombre = "Nombre", apellidos = "Apellidos", especialidad = "Especialidad",
			fecha_incorporacion = "Fecha_Incorporacion";

	@Override
	public List<Document> findAll() {
		Bson projectionFields = Projections.excludeId();

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

	public String findDniPorDni(String medico) {
		Bson filter = eq(dni, medico);
		Document result = collection.find(filter).first();
		Object dniList = result.get(dni);
		return (String) dniList;

	}

	public String findNombrePordni(String medico) {
		Bson filter = eq(dni, medico);
		Document result = collection.find(filter).first();
		Object dniList = result.get(nombre);
		return (String) dniList;

	}

	public String findApellidosPordni(String medico) {
		Bson filter = eq(dni, medico);
		Document result = collection.find(filter).first();
		Object dniList = result.get(apellidos);
		return (String) dniList;

	}

	public String findEspecialidadPordni(String medico) {
		Bson filter = eq(dni, medico);
		Document result = collection.find(filter).first();
		Object dniList = result.get(especialidad);
		return (String) dniList;

	}

	public String findFechaIncorporacionPordni(String medico) {
		Bson filter = eq(dni, medico);
		Document result = collection.find(filter).first();
		Object dniList = result.get(fecha_incorporacion);
		return (String) dniList;

	}
	
	public Boolean abrirCitasMedicas(Optional<Document> medico, String atributo, List<String> citas) {
		try {
			if (medico.isPresent()) {
				Document filter = medico.get();
				collection.updateOne(eq("Dni", filter.getString("Dni")), Updates.pushEach(atributo, citas));
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
	public String[] guardarDniPacientes(String medico) {
		Bson filter = eq(dni, medico);
		Document document = collection.find(filter).first();
		List<String> dniList = (List<String>) document.get("Pacientes_Cargo");
		return dniList.toArray(new String[0]);
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

	public Optional<Document> findByUsernameAndPassword(String username, String password) {
		Bson filter = Filters.and(Filters.eq("Dni", username), Filters.eq("Contraseña", password));
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

	public Boolean update(Optional<Document> medico, String atributo, List<String> valores) {
		try {
			if (medico.isPresent()) {
				Document filter = medico.get();
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

	public Boolean update(Optional<Document> medico, String atributo, String valor) {
		try {

			if (medico.isPresent()) {
				Document filter = medico.get(); // filtro para seleccionar el documento a actualizar
				Document update = new Document("$set", new Document(atributo, valor));
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

	public Boolean update(Optional<Document> medico, String atributo, Document valores) {
		try {
			if (medico.isPresent()) {
				Document filter = medico.get();
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



	public List<Document> findByAttribute(String atributo, String valor) {
		Bson filter = eq(atributo, valor);
		Bson projectionFields = Projections.excludeId();
		List<Document> results = collection.find(filter).projection(projectionFields).into(new ArrayList<>());
		return results;
	}

}
