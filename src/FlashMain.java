import java.sql.ResultSet;
import java.util.*;

public class FlashMain {
	public static void main(String[] args) {
		MigrationManager.performPendingMigrations();
		ArrayList<Flashcard> testDeck = DeckGenerator.generateTestDeck();
		Deck.create("Multiplication Tables", "", testDeck);
		
		/*
		ResultSet rs = statement.executeQuery("select * from person");
		while(rs.next()) {
			// read the result set
			System.out.println("name = " + rs.getString("name"));
			System.out.println("id = " + rs.getInt("id"));
		} 
		*/
		
		Scanner console = new Scanner(System.in);

		FlashcardSequencer practice = new FlashcardSequencer();

		while (practice.hasNext()) {
			Flashcard flashcard = practice.nextFlashcard();
			System.out.println(flashcard.getQuestion());
			String response = console.next();
			while (flashcard.validateAnswer(response) == false) {
				System.out.println("Incorrect. Try again.");
				response = console.next();
			}
			System.out.println("Correct!");
		}
	}
}
