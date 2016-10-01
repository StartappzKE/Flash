import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Deck {	
	private ArrayList<Flashcard> cards;
	
	public static Deck load() {
		return null;
	}
	
	// inserts cards and deck into database
	public static Deck create(String name, String descr, ArrayList<Flashcard> cards) {
		Connection connection = DBManager.getDatabaseConnection();

		int deckID = 0;
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.execute("INSERT INTO DECK (DeckName, DeckDescr) "
							  + "VALUES ('" + name + "', '" + descr + "');");
			
			ResultSet rs = statement.executeQuery("SELECT DeckID FROM DECK WHERE DeckName = '" + name + "'");
			while(rs.next()) {
				deckID = rs.getInt("DeckID");
			}
			
			for (int i = 0; i < cards.size(); i++) {
				String insert = "INSERT INTO FLASHCARD (DeckID, Question, Answer) "
						+ "VALUES (" + deckID + ", '" + cards.get(i).getQuestion() + "', '" + cards.get(i).getAnswer() + "')";
					statement.execute(insert);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			DBManager.closeDatabaseConnection(connection);
		}
		
		return null;
	}
}
