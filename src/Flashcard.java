public class Flashcard {
	private String question;
	private String answer;
	
	public Flashcard(String question, String answer) {
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
