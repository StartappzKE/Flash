import java.sql.Connection;
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
		String decks = "CREATE TABLE DECK ("
                     + "DeckID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                     + "DeckName VARCHAR(100) NOT NULL, "
                     + "DeckDescr VARCHAR(500))";
		String flashcards = "CREATE TABLE FLASHCARD ("
                + "FlashcardID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "DeckID INTEGER NOT NULL, "
                // + "FOREIGN KEY(DeckID) REFERENCES DECK(DeckId), "
                + "Question VARCHAR(100) NOT NULL, " 
                + "Answer VARCHAR(100) NOT NULL)";
		String sessions = "CREATE TABLE SESSION ("
                + "SessionID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "DeckID INTEGER NOT NULL, "
                // + "FOREIGN KEY(DeckID) REFERENCES DECK(DeckId), "
                + "Size INTEGER NOT NULL, " 
                + "BeginDate DATETIME NOT NULL, "
                + "EndDate DATETIME)";
		String answerHistory = "CREATE TABLE ANSWER_HISTORY ("
                + "Answer_historyID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "SessionID INTEGER NOT NULL, "
                // + "FOREIGN KEY(SessionID) REFERENCES SESSION(SessionId), "
                + "FlashcardID INTEGER NOT NULL, "
                // + "FOREIGN KEY(FlashcardID) REFERENCES FLASHCARD(FlashcardId), "
                + "TimeToCorrect DOUBLE NOT NULL, "
                + "FirstAttemptCorrect BOOLEAN NOT NULL, "
                + "AnsweredAt DATETIME NOT NULL)";
		
		Connection connection = DBManager.getDatabaseConnection();
		
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
			DBManager.closeDatabaseConnection(connection);
		}
	}
	
	private static void updateMigrationsCompletedTo(int value) {
		// set preference that you have completed the first migration
		// http://stackoverflow.com/questions/11380812/does-java-have-something-similar-to-cocoas-nsuserdefaults
		Preferences prefs = Preferences.userNodeForPackage(MigrationManager.class);
		prefs.putInt(migrationsKey, value);
	}
}
