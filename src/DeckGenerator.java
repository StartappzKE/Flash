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
	
	public static String[] intervalNames = {
		"minor second",
		"major second",
		"minor third",
		"major third",
		"perfect fourth",
		"tritone",
		"perfect fifth",
		"minor sixth",
		"major sixth",
		"minor seventh",
		"major seventh",
	};

	public static String[] noteNames = {
		"C",
		"C#",
		"D",
		"D#",
		"E",
		"F",
		"F#",
		"G",
		"G#",
		"A",
		"A#",
		"B"
	};

	public static final Map<String, String> enharmonicEquivalents;
	static {
		enharmonicEquivalents = new HashMap<>();
		enharmonicEquivalents.put("C#", "Db");
		enharmonicEquivalents.put("D#", "Eb");
		enharmonicEquivalents.put("F#", "Gb");
		enharmonicEquivalents.put("G#", "Ab");
		enharmonicEquivalents.put("A#", "Bb");
	}

	public static ArrayList<Flashcard> generateIntervalDeck() {
		ArrayList<Flashcard> cards = new ArrayList<Flashcard>();

		for (int i = 0; i < noteNames.length; i++) {
			for (int j = 0; j < intervalNames.length; j++) {
				int ascendingAnswerIndex = (i + j + 1) % noteNames.length;
				int descendingAnswerIndex = ((noteNames.length + (i - j - 1)) % noteNames.length);
				String ascendingAnswer = noteNames[ascendingAnswerIndex];
				String descendingAnswer = noteNames[descendingAnswerIndex];

				DeckGenerator.intervalsAddCards(
					cards,
					noteNames[i],
					ascendingAnswer,
					descendingAnswer,
					intervalNames[j]
				);

				String enharmonicEquivalent = enharmonicEquivalents.get(noteNames[i]);
				if (enharmonicEquivalent != null) {
					String ascendingAnswerEquivalent = enharmonicEquivalents.get(ascendingAnswer);
					String descendingAnswerEquivalent = enharmonicEquivalents.get(descendingAnswer);

					DeckGenerator.intervalsAddCards(
						cards,
						enharmonicEquivalent,
						ascendingAnswerEquivalent != null ? ascendingAnswerEquivalent : ascendingAnswer,
						descendingAnswerEquivalent != null ? descendingAnswerEquivalent : descendingAnswer,
						intervalNames[j]
					);
				}
			}
		}

		return cards;
	}

	public static void intervalsAddCards(
		ArrayList<Flashcard> cards, 
		String questionNote, 
		String ascendingAnswer, 
		String descendingAnswer, 
		String interval) 
	{
		String ascendingQuestion = "What is an ascending " + interval + " of " + questionNote + "?";
		String descendingQuestion = "What is a descending " + interval + " of " + questionNote + "?";

		Flashcard ascending = new Flashcard(ascendingQuestion, ascendingAnswer);
		Flashcard descending = new Flashcard(descendingQuestion, descendingAnswer);

		cards.add(ascending);
		cards.add(descending);
	}
}