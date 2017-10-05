import java.util.Scanner;

public class Menu {
	private static Menu instance;

	//Fungsi inisiasi singleton
	public static synchronized Menu getInstance() {
		if (instance == null) {
			instance = new Menu();
		}
		return instance;
	}

	public String mainMenu() {
		Scanner input = new Scanner(System.in);
		System.out.format(
			"Linier Equation Solver\n" +
			"By: 13515020, 13515050, 13515104\n\n" +

			"What would you like to do? (Enter the option number)\n" +
			"1. Perform Gauss Elimination\n" +
			"2. Perform Gauss-Jordan Elimination\n" +
			"3. Perform an interpolation\n\n" +

			"> "
		);

		String currInput = input.next();
		
		while (!currInput.equals("1") && !currInput.equals("2") && !currInput.equals("3")) {
			currInput = badInput("menu");
		}

		switch (currInput) {
			case "1":	
				gaussElimMenu();
				break;
			case "2":	
				break;
			case "3":	
				break;
		}

		return "success!";
	}

	public String badInput(String type) {
		Scanner input = new Scanner(System.in);
		String currInput = "";
		if (type.equals("menu")) {
			System.out.format(
				"\nBad input, please reenter:\n" +
				"1. Perform Gauss Elimination\n" +
				"2. Perform Gauss-Jordan Elimination\n" +
				"3. Perform an interpolation\n\n" +

				"> "
			);

			currInput = input.next();
		} else if (type.equals("gaussMenu")) {
			System.out.format(
				"\nBad input, please reenter:\n" +
				"1. Fill in through keyboard\n" +
				"2. Insert text file\n\n" +

				"> "
			);

			currInput = input.next();
		}

		return currInput;
	}

	public String gaussElimMenu() {
		Scanner input = new Scanner(System.in);
		String currInput = "";

		System.out.format(
			"\nHow would you like to enter the data?\n" +
			"1. Fill in through keyboard\n" +
			"2. Insert text file\n\n" +

			"> "
		);
		currInput = input.next();

		while (!(currInput.equals("1") || currInput.equals("2") || currInput.equals("3"))) {
			currInput = badInput("gaussMenu");
		}

		return currInput;
	}



	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println(Menu.getInstance().mainMenu());
	}
}