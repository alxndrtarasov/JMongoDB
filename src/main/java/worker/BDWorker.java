package worker;

import java.sql.SQLException;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;

/**
 * Created by alta0816 on 20.02.2017.
 */
public interface BDWorker {
	public MongoDatabase connect(String dbName, String login, String password);

	public void createDatabase(String name);

	public void deleteDatabase(String name) throws SQLException;

	public void cleanTable(String name);

	public java.util.List<Document> find(String table, String field, String value);

	public void deleteByFieldValue(String table, String field, String value);

	public void deleteById(String table, String id);

	public Document find(String table, int id);

	public List<Document> getAllObjects();

	void insert(String table, int id, String[] key, String[] values);

	void update(String table, String[] keys, String[] values, int id);
}
