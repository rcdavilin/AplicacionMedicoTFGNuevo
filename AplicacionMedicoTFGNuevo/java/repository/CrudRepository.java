package repository;

import java.util.List;
import java.util.Optional;

import org.bson.Document;

import com.mongodb.client.result.DeleteResult;

public interface CrudRepository<T, id> {
	List<Document> findAll();

	Boolean save(Document entity);

	Optional<Document> findById(String id);

	DeleteResult delete(String dni);

}
