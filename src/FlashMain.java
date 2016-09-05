import java.util.*;

public class FlashMain {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		
		String question = "What is 7 + 3?";
		int answer = 10;
		
		System.out.println(question);
		int response = console.nextInt();
		
		while (response != answer)	{
			System.out.println("Incorrect. Try again.");
			response = console.nextInt();
		}
		System.out.println("Correct!");
	}
}
