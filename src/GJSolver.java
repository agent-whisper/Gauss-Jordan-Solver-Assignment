public class GJSolver {
	private static GJSolver instance;

	public static synchronized GJSolver getInstance() {
		if (instance == null) {
			instance = new GJSolver();
		}

		return instance;
	}

	public Matrix pivot(Matrix mx) {
		Matrix tempMatrix = new Matrix(mx);

		int currRow = 0;

		for (int i = 0; i < tempMatrix.getCol(); i++) {
			for (int j = currRow + 1; j < tempMatrix.getRow(); j++) {

				if (tempMatrix.getElement(currRow, i) < tempMatrix.getElement(j, i)) {
					OBE.getInstance().swapRow(tempMatrix, currRow + 1, j + 1);
				}

			}
			currRow++;
		}

		return tempMatrix;
	}

	public Matrix getEchelon(Matrix mx) {
		Matrix tempMatrix = new Matrix(pivot(mx));

		int leadingOne = 0;

		while ((leadingOne < tempMatrix.getRow()) && (leadingOne < tempMatrix.getCol() - 1)) {
			OBE.getInstance().divideRow(tempMatrix, leadingOne + 1, tempMatrix.getElement(leadingOne, leadingOne));

			for (int j = leadingOne + 1; j < tempMatrix.getRow(); j++) {
				OBE.getInstance().substractRow(tempMatrix, j + 1, leadingOne + 1, tempMatrix.getElement(j, leadingOne));
			}

			leadingOne++;
		}

		return tempMatrix;
	}

	private Matrix getReducedEchelon(Matrix mx) {
		Matrix tempMatrix = new Matrix(getEchelon(mx));


		return tempMatrix;
	}

	public Matrix GaussElim(Matrix mx) {
		Matrix tempMatrix = new Matrix(pivot(mx));
		return tempMatrix;
	}

	public Matrix GaussJordan(Matrix mx) {
		Matrix tempMatrix = new Matrix(pivot(mx));
		return tempMatrix;
	}

	public static void main (String[] args) {
		Matrix mx = new Matrix("not_found.txt");
		System.out.println("Before:");
		mx.toString();

		System.out.println("After:");
		GJSolver.getInstance().getEchelon(mx).toString();
	}
}