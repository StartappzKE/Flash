import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	public static Connection getDatabaseConnection() {
		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:MemoryFlashDB.db");
		}
		catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
		
		return connection;
	} 
	
	public static void closeDatabaseConnection(Connection connection) {
		try {
			if(connection != null)
				connection.close();
		}
		catch(SQLException e) {
			// connection close failed.
			System.err.println(e);
		}
	}
}
