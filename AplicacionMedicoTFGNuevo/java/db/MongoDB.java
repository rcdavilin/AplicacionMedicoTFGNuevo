package db;

import java.io.FileInputStream;
import java.util.Properties;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoDB {
	private final String FILE_PROPS = "./src/main/resources/app.conf";
	private static MongoClient db = null;

	private MongoDB() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(FILE_PROPS));
			db = MongoClients.create(props.getProperty("URI"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MongoClient getClient() {
		if (db == null) {
			new MongoDB();
		}
		return db;
	}
}
