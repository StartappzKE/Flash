import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	private Connection connection;
	
	public DBManager() {
		this.connection = DBManager.getDatabaseConnection();
	}
	
	private static Connection getDatabaseConnection() {
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
	
	public void closeDatabaseConnection() {
		try {
			if (this.connection != null) {
				this.connection.close();
			}
		}
		catch(SQLException e) {
			// connection close failed.
			System.err.println(e);
		}
	}
	
	public void execute(String query) {
		if (this.checkIfNull()) {
			return;
		}
		
		try {
			Statement statement = this.connection.createStatement();
			statement.setQueryTimeout(30);
			statement.execute(query);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} 
	}
	
	public ResultSet query(String query) {
		if (this.checkIfNull()) {
			return null;
		}
		
		ResultSet rs = null;
		try {
			Statement statement = this.connection.createStatement();
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return rs;
	}
	
	private boolean checkIfNull() {
		boolean isNull = false;
		if (this.connection == null) { 
			System.out.println("No database connection");
			isNull = true;
		}
		
		return isNull;
	}
}
