package repository.informe;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;

import db.MongoDB;

public class InformeRepositoryImpl implements InformeRepository {

	MongoClient mongoClient = MongoDB.getClient();
	MongoDatabase database = mongoClient.getDatabase("TrabajoMongo");
	MongoCollection<Document> collection = database.getCollection("Informes");

	@Override
	public List<Document> findAll() {
		Bson projectionFields = Projections.fields(Projections.excludeId());

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
		Bson filter = eq("Dni_Paciente", id);
		Bson projectionFields = Projections.excludeId();
		Document result = collection.find(filter).projection(projectionFields).first();
		return Optional.ofNullable(result);
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

	public Boolean guardarInforme(Optional<Document> paciente,String dniMedico, byte[] pdfBytes, String hora) {
		try {
			if (paciente.isPresent()) {
				Document pdfInforme = new Document("Dni_Medico",dniMedico).append("pdf", new Binary(pdfBytes)).append("Hora_Creacion", hora);
				Document filter = new Document("Dni_Paciente", paciente.get().getString("Dni_Paciente"));
				Document update = new Document("$push", new Document("Informes", pdfInforme));

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
	public ArrayList<byte[]> verInformes(String medico) {
		Bson filter = Filters.eq("Dni_Paciente", medico);
		Document document = collection.find(filter).first();
		ArrayList<byte[]> informes = new ArrayList<>();

		if (document != null && document.containsKey("Informes")) {
			ArrayList<Document> informesLista = (ArrayList<Document>) document.get("Informes");

			for (Document obj : informesLista) {
				if (obj.containsKey("pdf")) {
					Binary pdfBinary = (Binary) obj.get("pdf");
					informes.add(pdfBinary.getData());
				}
			}
		}

		return informes;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> findFechaCreacion(String medico) {
	    Bson filter = eq("Dni_Paciente", medico);
	    Document document = collection.find(filter).first();

	    if (document == null) {
	        return new ArrayList<>();
	    }

	    ArrayList<Document> informes = (ArrayList<Document>) document.get("Informes");
	    ArrayList<String> fechas = new ArrayList<>();

	    if (informes != null) {
	        for (Document informe : informes) {
	            if (informe.containsKey("Hora_Creacion")) {
	                fechas.add(informe.getString("Hora_Creacion"));
	            }
	        }
	    }

	    return fechas;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> findDniMedicoInforme(String medico) {
	    Bson filter = eq("Dni_Paciente", medico);
	    Document document = collection.find(filter).first();

	    if (document == null) {
	        return new ArrayList<>();
	    }

	    ArrayList<Document> informes = (ArrayList<Document>) document.get("Informes");
	    ArrayList<String> fechas = new ArrayList<>();

	    if (informes != null) {
	        for (Document informe : informes) {
	            if (informe.containsKey("Dni_Medico")) {
	                fechas.add(informe.getString("Dni_Medico"));
	            }
	        }
	    }

	    return fechas;
	}

	@SuppressWarnings("unchecked")
	public String[] guardaInformes(String paciente) {
        Bson filter = eq("Dni_Paciente", paciente);
        Document document = collection.find(filter).first();

        List<Document> informes = (List<Document>) document.get("Informes");
        List<String> informesListas = informes.stream()
                                               .map(doc -> doc.getString("Hora_Creacion"))
                                               .collect(Collectors.toList());

        return informesListas.toArray(new String[0]);
    }
	
	public Boolean eliminarInformePaciente(String paciente,  String valor) {
		try {
          
            Document filter = new Document("Dni_Paciente", paciente);
    
            Document pullFilter = new Document("Hora_Creacion", valor);
       
            collection.updateOne(filter, Updates.pull("Informes", pullFilter));
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
  

}
