public class GJSolver {
	private static GJSolver instance;

	private GJSolver() {

	}

	public static synchronized GJSolver getInstance() {
		if (instance == null)
			instance = new GJSolver();

		return instance;
	}


}