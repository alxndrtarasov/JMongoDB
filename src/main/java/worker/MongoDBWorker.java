package worker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDBWorker implements BDWorker {

	private MongoDatabase database;
	public static final String defDbName = "postgres";
	public static final String login = "postgres";
	public static final String password = "1337";
	public static String defName = "obj";

	@Override
	public MongoDatabase connect(String dbName, String login, String password) {
		MongoClientURI connectionString = new MongoClientURI("mongodb://localhost");
		MongoClient mongoClient = new MongoClient(connectionString);

		MongoDatabase database = mongoClient.getDatabase("mydb");
		this.database = database;
		return database;
	}

	@Override
	public void createDatabase(String name) {
		if (database != null) {
			database.createCollection(name);
			setDefName(name);
		} else {
			System.err.println("establish connection first");
		}

	}

	public void setDefName(String defName) {
		MongoDBWorker.defName = defName;
	}

	@Override
	public void deleteDatabase(String name) throws SQLException {
		if (database != null) {
			database.getCollection(name).drop();
		} else {
			System.err.println("establish connection first");
		}

	}

	@Override
	public void cleanTable(String name) {
		if (database != null) {
			database.getCollection(name).drop();
			database.createCollection(name);
		} else {
			System.err.println("establish connection first");
		}
	}

	@Override
	public void insert(String table, int id, String[] keys, String[] values) {
		if (database != null) {
			Document doc = new Document("id", id);
			for (int i = 0; i < keys.length; i++) {
				doc.append(keys[i], values[i]);
			}
			database.getCollection(table).insertOne(doc);
		} else {
			System.err.println("establish connection first");
		}
	}

	@Override
	public void update(String table, String[] keys, String[] values, int id) {
		if (database != null) {
			Document doc = new Document();
			for (int i = 0; i < keys.length; i++) {
				doc.append(keys[i], values[i]);
			}
			Document update = new Document("$set", doc);
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", id);
			FindIterable<Document> cursor = database.getCollection(table).find(whereQuery);
			MongoCursor<Document> iterator = cursor.iterator();
			while (iterator.hasNext()) {
				System.err.println("Check");
				database.getCollection(table).updateOne(iterator.next(), update);
			}

		} else {
			System.err.println("establish connection first");
		}
	}

	@Override
	public List<Document> find(String table, String field, String value) {
		if (database != null) {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put(field, value);
			FindIterable<Document> cursor = database.getCollection(table).find(whereQuery);
			MongoCursor<Document> iterator = cursor.iterator();
			List<Document> result = new ArrayList<Document>();
			while (iterator.hasNext()) {
				result.add(iterator.next());
			}
			return result;

		} else {
			System.err.println("establish connection first");
		}
		return null;
	}

	@Override
	public void deleteByFieldValue(String table, String field, String value) {
		if (database != null) {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put(field, value);
			database.getCollection(table).deleteMany(whereQuery);
		} else {
			System.err.println("establish connection first");
		}
	}

	@Override
	public void deleteById(String table, String id) {
		if (database != null) {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", id);
			database.getCollection(table).deleteOne(whereQuery);
		} else {
			System.err.println("establish connection first");
		}

	}

	@Override
	public List<Document> getAllObjects() {
		if (database != null) {
			FindIterable<Document> cursor = database.getCollection(defName).find();
			MongoCursor<Document> iterator = cursor.iterator();
			List<Document> result = new ArrayList<Document>();
			while (iterator.hasNext()) {
				result.add(iterator.next());
			}
			return result;
		} else {
			System.err.println("establish connection first");
		}
		return null;
	}

	@Override
	public Document find(String table, int id) {
		if (database != null) {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", id);
			FindIterable<Document> cursor = database.getCollection(table).find(whereQuery);
			MongoCursor<Document> iterator = cursor.iterator();
			while (iterator.hasNext()) {
				return iterator.next();
			}
			return null;

		} else {
			System.err.println("establish connection first");
		}
		return null;
	}

}
