public class GJSolver {
	private static GJSolver instance;

	public static synchronized GJSolver getInstance() {
		if (instance == null) {
			instance = new GJSolver();
		}

		return instance;
	}

	private boolean solutionExists(Matrix mx) {
		for (int i = 0; i < mx.getRow(); i++) {
			int j = 0;
			int zeroCount = 0;

			while ((j < mx.getCol() - 1) && (mx.getElement(i, j) == 0)) {
				zeroCount++;
				j++;
			}

			if ((zeroCount == (mx.getCol() - 1)) && (mx.getElement(i, mx.getCol() - 1) != 0)) {
				return false;
			}
		}

		return true;
	}

	private boolean checkIfUnique(Matrix mx) {
		if (mx.getRow() < mx.getCol() - 1) {
			return false;
		} else {
			//jumlah row = jumlah variabel
			for (int i = 0; i < mx.getCol() - 1; i++) {
				if (mx.getElement(i, i) != 1) {
					return false;
				}
			}
		}

		return true;
	}

	private String multiSolutionBuilder(double[] aRow) {
		int currCol = 0;
		String tempStr = "";

		while ((aRow[currCol] == 0) && (currCol < aRow.length - 1)) {
			currCol++;
		}

		if ((currCol == aRow.length - 1) && (aRow[currCol] == 0)) {
			return ("0");
		}

		for (int i = aRow.length - 1; i > currCol; i--) {
			double num = ((i < aRow.length - 1) ? (-1) : 1) * (aRow[i] / aRow[currCol]);
			if (num != 0) {
				tempStr = tempStr + "(" + String.format("%.2f", num) + ")" + ((i < aRow.length - 1 ) ? (char) (97 + i) : "") + ((i > currCol + 1) ? " + ": "");
			}

		}		

		return tempStr;
	}

	private Matrix uniqueSolutionBuilder(Matrix aMatrix) {
		int currRow = aMatrix.getCol() - 2;
		int currCol;
		Matrix solution = new Matrix(aMatrix.getCol() - 1, 1);

		while (currRow >= 0) {
			currCol = currRow;
			solution.setElement(currRow, 1, (aMatrix.getElement(currRow, aMatrix.getCol() - 1)));

			while (++currCol < aMatrix.getCol() - 1) {
				solution.setElement(currRow, 1, (aMatrix.getElement(currRow, aMatrix.getCol() - 1)));
				solution.setElement(currRow, 1, (-1) * aMatrix.getElement(currRow, currCol) * solution.getElement(currCol, 1));
			}

			currRow--;
		}

		return solution;
	}

	public Matrix getEchelon(Matrix mx) {
		Matrix tempMatrix = new Matrix(mx);

		int leadingOne = 0;
		int currRow = 0;
		int currCol = 0;

		while ((currRow < tempMatrix.getCol() - 1) && (leadingOne < tempMatrix.getCol() - 1)) {
			while ((tempMatrix.getElement(currRow, currCol) == 0) && (currCol < tempMatrix.getCol() - 1)) {
				currCol++;
			}

			for (int j = currRow + 1; j < tempMatrix.getRow(); j++) {
				if (Math.abs(tempMatrix.getElement(currRow, currCol)) < Math.abs(tempMatrix.getElement(j, currCol))) {
					OBE.getInstance().swapRow(tempMatrix, currRow + 1, j + 1);
				}
			}
			System.out.println ("Iteration " + currRow + ", pivoting");
			tempMatrix.toString();

			currCol++;
			
			while (tempMatrix.getElement(leadingOne, leadingOne) == 0) {
				leadingOne++;
			}
			
			//System.out.println("Current leading element: " + tempMatrix.getElement(leadingOne, leadingOne));
			OBE.getInstance().divideRow(tempMatrix, currRow + 1, tempMatrix.getElement(currRow, leadingOne));

			for (int j = currRow + 1; j < tempMatrix.getRow(); j++) {
				OBE.getInstance().substractRow(tempMatrix, j + 1, currRow + 1, tempMatrix.getElement(j, leadingOne));
			}

			System.out.println ("Iteration " + currRow + ", partial echelon");
			tempMatrix.toString();
			leadingOne++;
			currRow++;
		}

		return tempMatrix;
	}

	private Matrix getReducedEchelon(Matrix mx) {
		Matrix tempMatrix = new Matrix(getEchelon(mx));


		return tempMatrix;
	}

	public Matrix GaussElim(Matrix mx) {
		Matrix tempMatrix = new Matrix(getEchelon(mx));

		if (!solutionExists(tempMatrix)) {
			System.out.println("Tidak ada solusi");
			return (new LESSolution(1));
		}

		if (!checkIfUnique(tempMatrix)) {
			LESSolution tempResult = new LESSolution(mx.getRow());

			for (int i = 0; i < mx.getRow(); i++) {
				tempResult.setElement(i, multiSolutionBuilder(tempMatrix.getRowSet(i)));
			}

			return tempResult;
		} else {
			return uniqueSolutionBuilder(tempMatrix);

		}

		// LESSolution result = new LESSolution(tempMatrix.getCol() - 1);
		// for (int i = result.getRow() - 1; i >= 0; i--) {
		// 	result.setElement(i, );
		// }
	}

	public Matrix GaussJordan(Matrix mx) {
		Matrix tempMatrix = new Matrix(getReducedEchelon(mx));
		return tempMatrix;
	}

	public static void main (String[] args) {
		Matrix mx = new Matrix("not_found.txt");
		System.out.println("Original:");
		mx.toString();

		System.out.println("Echelon form:");
		GJSolver.getInstance().GaussElim(mx).toString();
	}
}