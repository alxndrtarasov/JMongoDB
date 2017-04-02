import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.BeforeClass;
import org.junit.Test;

import worker.BDWorker;
import worker.MongoDBWorker;

/**
 * Created by alta0816 on 20.02.2017.
 */
public class BDWorkerTest {
	private static final String defDbName = "postgres";
	private static BDWorker worker;
	private static final String login = "postgres";
	private static final String password = "1337";

	@BeforeClass
	public static void setUpBeforeClass() {
		worker = new MongoDBWorker();
	}

	@Test
	public void testConnect() throws SQLException {
		Connection result = worker.connect(defDbName, login, password);
		PreparedStatement statement = result.prepareStatement("select * from testconnect");
		ResultSet set = statement.executeQuery();
		if (set.next()) {
			assertEquals(set.getString("id"), "1");
		}
		result.close();
	}

	@Test
	public void testCreateAndDeleteDatabase() throws SQLException {
		Connection connectionToDef = worker.connect(defDbName, login, password);
		String dbName = "new_DB";
		worker.createDatabase(dbName);
		Connection connectionToNew = worker.connect(dbName, login, password);
		connectionToNew.close();
		connectionToDef = worker.connect(defDbName, login, password);
		worker.deleteDatabase(dbName);
		connectionToDef.close();
	}

	@Test
	public void testCleanTable() throws SQLException {
		String dbName = "new_DB";
		Connection connectionToDef = worker.connect(defDbName, login, password);
		worker.createDatabase(dbName);
		connectionToDef.close();
		Connection connectionToNew = worker.connect(dbName, login, password);
		Statement statement = connectionToNew.createStatement();
		statement.executeUpdate("create table test (id integer);");
		statement.executeUpdate("insert into test values(13)");
		worker.cleanTable("test");
		ResultSet set = statement.executeQuery("select * from test");
		assertEquals(false, set.next());
		connectionToNew.close();
		worker.connect(defDbName, login, password);
		worker.deleteDatabase(dbName);
	}
	//
	// @Test
	// public void testConnect() {
	// worker.connect("localhost", "1337");
	// }
	//
	// @Test
	// public void testConnect() {
	// worker.connect("localhost", "1337");
	// }
	//
	// @Test
	// public void testConnect() {
	// worker.connect("localhost", "1337");
	// }
	//
	// @Test
	// public void testConnect() {
	// worker.connect("localhost", "1337");
	// }
	//
	// @Test
	// public void testConnect() {
	// worker.connect("localhost", "1337");
	// }
	//
	// @Test
	// public void testConnect() {
	// worker.connect("localhost", "1337");
	// }
}
