import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Deck {	
	private int deckID;
	private String name;
	private String descr;
	private ArrayList<Flashcard> cards;
	private int flashcardID;
	
	public Deck(String name) {
		this.loadDeck(name);
		this.loadCards();
	}
	
	private void loadDeck(String name) {
		DBManager db = new DBManager();	
		ResultSet rs = db.query("SELECT * FROM DECK WHERE DeckName = '" + name + "'");
		try {
			while(rs.next()) {
				this.deckID = rs.getInt("DeckID");
				this.name = rs.getString("DeckName");
				this.descr = rs.getString("DeckDescr");
			}
		} catch(SQLException e) {
			System.out.println("error in loadDeck");
		}

		db.closeDatabaseConnection();
	}
	
	private void loadCards() {
		DBManager db = new DBManager();
		this.cards = new ArrayList<Flashcard>();
		
		ResultSet rs = db.query("SELECT * FROM FLASHCARD WHERE DeckID = " + this.deckID);
		try {
			while(rs.next()) {
				this.cards.add(new Flashcard(rs.getInt("FlashcardID"), rs.getString("Question"), rs.getString("Answer")));
			}
		} catch(SQLException e) {
			System.out.println("error in loadDeck");
		}
	}
	
	// inserts cards and deck into database
	public static Deck create(String name, String descr, ArrayList<Flashcard> cards) {
		DBManager db = new DBManager();
		
		db.execute("INSERT INTO DECK (DeckName, DeckDescr) "
				   + "VALUES ('" + name + "', '" + descr + "');");
		
		Deck newDeck = new Deck(name);
			
		for (int i = 0; i < cards.size(); i++) {
			db.execute("INSERT INTO FLASHCARD (DeckID, Question, Answer) "
					   + "VALUES (" + newDeck.deckID + ", '" + cards.get(i).getQuestion() + "', '" 
					   + cards.get(i).getAnswer() + "')");
		}
		
		newDeck.cards = cards;
		db.closeDatabaseConnection();
		
		return newDeck;
	}
}
