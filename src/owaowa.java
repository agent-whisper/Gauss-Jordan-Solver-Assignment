import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class owaowa {
	public static void main(String[] args) {
		String test = "0.71 d * + 0.53 c * + 0.53 b";
		Scanner tgtScanner = new Scanner(test);
		Stack<String> holder = new Stack<>();
		Pattern aChar = Pattern.compile("[a-z]*");
		Pattern anOperator = Pattern.compile("[^a-z[//+//*]]");
		
		while (tgtScanner.hasNext()) {
			// if (!tgtScanner.hasNextChar()) {
			// 	tgtScanner.next();
			// } else {
			// 	System.out.println(tgtScanner.nextDouble());
			// }
			String temp = tgtScanner.next();
			Matcher matcher = aChar.matcher(temp);
			Matcher matcher2 = anOperator.matcher(temp);

			System.out.println("--> " + temp);
			if (matcher.matches()) {
				System.out.println("var " + temp);
			}

			if (matcher2.matches()) {
				System.out.println("operator " + temp);
			}
		}

		String apalah = "a";
		char test1 = apalah.charAt(0);
		System.out.println(((int) test1));
	}
}