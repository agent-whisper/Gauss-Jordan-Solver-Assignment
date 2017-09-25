import java.io.*;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) throws IOException {
		BufferedReader br = null;
		String line = "";
		String countLine = "";
		int count = 0;
		try {
			br = new BufferedReader(new FileReader("not_found.txt"));
			line = br.readLine();

			{
				countLine = line;
				Scanner scanner = new Scanner(countLine);

				while (scanner.hasNext()) {
					if (scanner.hasNextInt()) {
						count++;
						scanner.nextInt();
					}
				}
			}

			while (line != null) {
				Scanner scanner = new Scanner(line);

				while (scanner.hasNext()) {
					if (scanner.hasNextInt()) {
						System.out.println(scanner.nextInt());
					}
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("too bad");
		} finally {
			if (br != null)
				br.close();
		}

		System.out.println("count: " + count);
	}
}