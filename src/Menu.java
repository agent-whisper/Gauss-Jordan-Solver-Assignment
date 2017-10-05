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

	public int mainMenu() {
		Scanner input = new Scanner(System.in);
		int currInput;
		int status = 0;
		boolean running = true;

		do {
			do {
				System.out.format(
					"\n\n\n\n\nLinier Equation Solver\n" +
					"By: 13515020, 13515050, 13515104\n\n" +

					"What would you like to do? (Enter the option number)\n" +
					"1. Perform Gauss Elimination\n" +
					"2. Perform Gauss-Jordan Elimination\n" +
					"3. Perform an interpolation\n" +
					"4. Exit the program\n\n" +

					"> "
				);

				while (!input.hasNextInt()) {
					System.out.format("\nInvalid input!\n> ");
					input.next();
				}

				currInput = input.nextInt();
			} while (currInput < 1 && currInput > 4);

			switch (currInput) {
				case 1:	
					status = gaussElimMenu();
					break;
				case 2:	
					status = gaussJordanMenu();
					break;
				case 3:	
					status = interpolationMenu();
					break;
				case 4:
					running = false;
					break;
			}

		} while (running);

		return status;
	}

	public int gaussElimMenu() {
		Scanner input = new Scanner(System.in);
		int currInput;

		do {
			System.out.format(
				"\nHow would you like to enter the data?\n" +
				"1. Fill in through keyboard\n" +
				"2. Insert text file\n\n" +

				"> "
			);

			while (!input.hasNextInt()) {
				System.out.format("\nInvalid input!\n> ");
				input.next();
			}

			currInput = input.nextInt();
		} while (currInput < 1 && currInput > 2);

		switch (currInput) {
			case 1 :
				return manualGaussElim();

			case 2 :
				System.out.print("Enter your filename: ");
				String filename = input.next();
				return fileGaussElim(filename);
		}

		return 0;
	}

	public Matrix manualMatrix() {
		Scanner input = new Scanner(System.in);
		int row, col;
		do {
			System.out.format(
				"\nEnter the row size (max 10 for safety):\n" +
				"> "
			);

			while (!input.hasNextInt()) {
				System.out.format("\nInvalid input!\n> ");
				input.next();
			}

			row = input.nextInt();
		} while (row < 0 && row > 10);

		do {
			System.out.format(
				"\nEnter the column size (max 10 for safety):\n" +
				"> "
			);

			while (!input.hasNextInt()) {
				System.out.format("\nInvalid input!\n> ");
				input.next();
			}

			col = input.nextInt();
		} while (col < 0 && col > 10);

		Matrix tempMatrix = new Matrix(row, col);
		tempMatrix.fill();

		return tempMatrix;
	}

	public int manualGaussElim() {
		Matrix mx = new Matrix(manualMatrix());
		System.out.format("\nOriginal Matrix:\n\n");
		mx.toString();

		Matrix result = (GJSolver.getInstance().GaussElim(mx));
		showResult(result);

		return 0; 
	}

	public int fileGaussElim(String fileDir) {
		Matrix mx = new Matrix(fileDir);
		System.out.format("\nOriginal Matrix:\n\n");
		mx.toString();

		Matrix result = (GJSolver.getInstance().GaussElim(mx));
		showResult(result);

		return 0; 
	}

	public int gaussJordanMenu() {
		Scanner input = new Scanner(System.in);
		int currInput;

		do {
			System.out.format(
				"\nHow would you like to enter the data?\n" +
				"1. Fill in through keyboard\n" +
				"2. Insert text file\n\n" +

				"> "
			);

			while (!input.hasNextInt()) {
				System.out.format("\nInvalid input!\n> ");
				input.next();
			}

			currInput = input.nextInt();
		} while (currInput < 1 && currInput > 2);

		switch (currInput) {
			case 1 :
				return manualGaussJordan();

			case 2 :
				System.out.print("Enter your filename: ");
				String filename = input.next();
				return fileGaussJordan(filename);
		}

		return 0;
	}

	public int manualGaussJordan() {
		Matrix mx = new Matrix(manualMatrix());
		System.out.format("\nOriginal Matrix:\n\n");
		mx.toString();

		Matrix result = (GJSolver.getInstance().GaussJordan(mx));
		showResult(result);

		return 0; 
	}

	public int fileGaussJordan(String fileDir) {
		Matrix mx = new Matrix(fileDir);
		System.out.format("\nOriginal Matrix:\n\n");
		mx.toString();

		Matrix result = (GJSolver.getInstance().GaussJordan(mx));
		showResult(result);

		return 0; 
	}

	public int interpolationMenu() {
		Scanner input = new Scanner(System.in);
		int currInput;
		int opType;

		do {
			System.out.format(
				"\nHow would you like to enter the data?\n" +
				"1. Fill in through keyboard\n" +
				"2. Insert text file\n\n" +

				"> "
			);

			while (!input.hasNextInt()) {
				System.out.format("\nInvalid input!\n> ");
				input.next();
			}

			currInput = input.nextInt();
		} while (currInput < 1 && currInput > 2);

		do {
			System.out.format(
				"\nWhat output do you want?\n" +
				"1. Estimation for an input\n" +
				"2. The polynomial\n\n" +

				"> "
			);

			while (!input.hasNextInt()) {
				System.out.format("\nInvalid input!\n> ");
				input.next();
			}

			opType = input.nextInt();
		} while (opType < 1 && opType > 2);

		if (currInput == 1) {
			switch (opType) {
				case 1: 
					return manualInterpolation(1);
				case 2:
					return manualInterpolation(2);
			}
		} else if (currInput == 2) {
			System.out.print("Enter your filename: ");
			String filename = input.next();
			switch (opType) {
				case 1:
					return fileInterpolation(1, filename);
				case 2:
					return fileInterpolation(2, filename);
			}
		}

		return 0;
	}

	public int manualInterpolation(int opType) {
		Matrix mx = new Matrix(manualData());
		System.out.format("\nData:\n\n");
		mx.toString();

		if (opType == 1) { //estimasi
			int currInput;
			Scanner input = new Scanner(System.in);
			boolean keepGoing = false;
			boolean repeat = true;
			double x;

			do {
				do {
					System.out.format(
						" x = "
					);

					while (!input.hasNextDouble()) {
						System.out.format("\nInvalid input!\n> ");
						input.next();
						keepGoing = true;
					}

					x = input.nextDouble();
				} while (keepGoing);
				System.out.format("f(%.2f) = %.4f\n\n", x, GJSolver.getInstance().estimate(x, mx));

				do {
					System.out.format(
						"Again?\n" +
						"1. yes\n" +
						"2. no\n\n" +
						"> "
					);

					while (!input.hasNextInt()) {
						System.out.format("\nInvalid input!\n> ");
						input.next();
					}

					currInput = input.nextInt();
				} while (currInput < 1 && currInput > 2);

				if (currInput == 2) {
					repeat = false;
				}
			} while (repeat);
		} else if (opType == 2) {
			showResult(GJSolver.getInstance().interpolate(mx));
		}

		return 0; 
	}

	public Matrix manualData() {
		Scanner input = new Scanner(System.in);
		int row, col;
		do {
			System.out.format(
				"\nEnter the row size (max 10 for safety):\n" +
				"> "
			);

			while (!input.hasNextInt()) {
				System.out.format("\nInvalid input!\n> ");
				input.next();
			}

			row = input.nextInt();
		} while (row < 0 && row > 10);

		Matrix tempMatrix = new Matrix(row, 2);
		tempMatrix.fill();

		return tempMatrix;
	}

	public int fileInterpolation(int opType, String fileDir) {
		Matrix mx = new Matrix(fileDir);
		System.out.format("\nData:\n\n");
		mx.toString();

		if (opType == 1) {
			int currInput;
			Scanner input = new Scanner(System.in);
			boolean keepGoing = false;
			boolean repeat = true;
			double x;

			do {
				do {
					System.out.format(
						" x = "
					);

					while (!input.hasNextDouble()) {
						System.out.format("\nInvalid input!\n> ");
						input.next();
						keepGoing = true;
					}

					x = input.nextDouble();
				} while (keepGoing);

				System.out.format("f(%.2f) = %.4f\n\n", x, GJSolver.getInstance().estimate(x, mx));

				do {
					System.out.format(
						"Again?\n" +
						"1. yes\n" +
						"2. no\n\n" +
						"> "
					);

					while (!input.hasNextInt()) {
						System.out.format("\nInvalid input!\n> ");
						input.next();
					}

					currInput = input.nextInt();
				} while (currInput < 1 && currInput > 2);

				if (currInput == 2) {
					repeat = false;
				}
			} while (repeat);
		} else if (opType == 2) {
			System.out.format("\nNilai koefisien dari a0 ke a%d\n", mx.getRow() - 1);
			showResult(GJSolver.getInstance().interpolate(mx));
		}

		return 0; 
	}

	public void showResult(Matrix result) {
		Scanner input = new Scanner(System.in);
		System.out.format("\n\nResult:\n");
		result.toString();

		int save;
		do {
			System.out.format(
				"\nWould you like to save?\n" +
				"1. yes\n" +
				"2. no\n\n" +
				"> "
			);

			while (!input.hasNextInt()) {
				System.out.format("\nInvalid input!\n> ");
				input.next();
			}

			save = input.nextInt();
		} while (save < 1 && save > 2);

		if (save == 1) {
			System.out.print("Enter file name: ");
			String name = input.next();
			result.save(name);
		}
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println(Menu.getInstance().mainMenu());
	}
}