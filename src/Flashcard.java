public class Flashcard {
	private int flashcardID;
	private String question;
	private String answer;
	
	public Flashcard(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}
	
	public Flashcard(int flashcardID, String question, String answer) {
		this.flashcardID = flashcardID;
		this.question = question;
		this.answer = answer;
	}
	
	public String getQuestion() {
		return this.question;
	}	
	
	public String getAnswer() {
		return this.answer;
	}	
	
	public boolean validateAnswer(String response) {
		return response.equals(this.answer);
	}	
}
