import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.Preferences;

public class MigrationManager {
	private static final String migrationsKey = "Migration number";
	
	public static void performPendingMigrations() {
		// check preference to see how many migrations have been performed
		Preferences prefs = Preferences.userNodeForPackage(MigrationManager.class);
		int performedMigrations = prefs.getInt(migrationsKey, 0);
		switch (performedMigrations) {
		case 0: 
			MigrationManager.performFirstMigration();
		}
	}
	
	private static void performFirstMigration() {
		// create db tables
		String decks = "CREATE TABLE DECK ("
                     + "DeckID INT IDENTITY(1,1) PRIMARY KEY NOT NULL, "
                     + "DeckName VARCHAR(100) NOT NULL, "
                     + "DeckDescr VARCHAR(500))";
		String flashcards = "CREATE TABLE FLASHCARD ("
                + "FlashcardID INT IDENTITY(1,1) PRIMARY KEY NOT NULL, "
                + "DeckID INT NOT NULL, "
                // + "FOREIGN KEY(DeckID) REFERENCES DECK(DeckId), "
                + "Question VARCHAR(100) NOT NULL, " 
                + "Answer VARCHAR(100) NOT NULL)";
		String sessions = "CREATE TABLE SESSION ("
                + "SessionID INT IDENTITY(1,1) PRIMARY KEY NOT NULL, "
                + "DeckID INT NOT NULL, "
                // + "FOREIGN KEY(DeckID) REFERENCES DECK(DeckId), "
                + "Size INT NOT NULL, " 
                + "BeginDate DATETIME NOT NULL, "
                + "EndDate DATETIME)";
		String answerHistory = "CREATE TABLE ANSWER_HISTORY ("
                + "Answer_historyID INT IDENTITY(1,1) PRIMARY KEY NOT NULL, "
                + "SessionID INT NOT NULL, "
                // + "FOREIGN KEY(SessionID) REFERENCES SESSION(SessionId), "
                + "FlashcardID INT NOT NULL, "
                // + "FOREIGN KEY(FlashcardID) REFERENCES FLASHCARD(FlashcardId), "
                + "TimeToCorrect DOUBLE NOT NULL, "
                + "FirstAttemptCorrect BOOLEAN NOT NULL, "
                + "AnsweredAt DATETIME NOT NULL)";
		
		Connection connection = MigrationManager.getDatabaseConnection();
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			statement.execute(decks);
			statement.execute(flashcards);
			statement.execute(sessions);
			statement.execute(answerHistory);
			
			MigrationManager.updateMigrationsCompletedTo(1);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			MigrationManager.closeDatabaseConnection(connection);
		}
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
	
	private static void closeDatabaseConnection(Connection connection) {
		try {
			if(connection != null)
				connection.close();
		}
		catch(SQLException e) {
			// connection close failed.
			System.err.println(e);
		}
	}
	
	private static void updateMigrationsCompletedTo(int value) {
		// set preference that you have completed the first migration
		// http://stackoverflow.com/questions/11380812/does-java-have-something-similar-to-cocoas-nsuserdefaults
		Preferences prefs = Preferences.userNodeForPackage(MigrationManager.class);
		prefs.putInt(migrationsKey, value);
	}
}
