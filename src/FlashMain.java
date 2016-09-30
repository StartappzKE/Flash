import java.util.*;

public class FlashMain {
	public static void main(String[] args) {
		MigrationManager.performPendingMigrations();
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
