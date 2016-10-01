import java.util.*;

public class DeckGenerator {
	public static ArrayList<Flashcard> generateTestDeck() {
		ArrayList<Flashcard> testDeck = new ArrayList<Flashcard>();
		
		// generate the times tables, and return them
		for (int i = 1; i <= 10; i++) {
			for (int j = 10; j >= i; j--) {
				int left = i;
				int right = j;
				int answer = i * j;
				testDeck.add(new Flashcard(Integer.toString(left) + " * " + Integer.toString(right), 
											Integer.toString(answer)));	
			}
		}
		return testDeck;
	}
}