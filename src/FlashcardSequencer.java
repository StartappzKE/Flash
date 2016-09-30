import java.util.*;

public class FlashcardSequencer {
	private Random r;
	private ArrayList<Flashcard> flashcardDeck;
	
	public FlashcardSequencer() {	
		this.r = new Random();
		this.flashcardDeck = new ArrayList<Flashcard>();
		this.flashcardDeck = this.populateDeck();
	}
	
	private ArrayList<Flashcard> populateDeck() {
		// remove
		this.flashcardDeck.add(new Flashcard("What is 7 + 1?", "8"));
		this.flashcardDeck.add(new Flashcard("What is 10 * 4?", "40"));
		this.flashcardDeck.add(new Flashcard("What is 8 + 2?", "10"));
		this.flashcardDeck.add(new Flashcard("What is 50 / 10?", "5"));
		return this.flashcardDeck;
	}
	
	public Flashcard nextFlashcard() {
		// select random flashcard from database
		int index = this.r.nextInt(this.flashcardDeck.size());
		return this.flashcardDeck.get(index);
	}
	
	public boolean hasNext() {
		return true;
	}
	
}
