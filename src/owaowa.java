import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class owaowa {
	public static void main(String[] args) {
		ArrayList<Double> bla = new ArrayList<>();
		for (double i = 0; i < 10; i++) {
			bla.add(i);
		}

		for (double x : bla) {
			System.out.println(x);
		}

		System.out.println();

		bla.add(4, 10.0);
		for (double x : bla) {
			System.out.println(x);
		}
	}
}