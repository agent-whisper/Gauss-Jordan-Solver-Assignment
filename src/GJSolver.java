public class GJSolver {
	private static GJSolver instance;

	public static synchronized GJSolver getInstance() {
		if (instance == null) {
			instance = new GJSolver();
		}

		return instance;
	}

	private Matrix pivot(Matrix mx) {

	}

	private Matrix getEchelon(Matrix mx) {

	}

	private Matrix getReducedEchelon(Matrix mx) {

	}

	public Matrix GaussElim(Matrix mx) {

	}

	public Matrix GaussJordan(Matrix mx) {

	}


}