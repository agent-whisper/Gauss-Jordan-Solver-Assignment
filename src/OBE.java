public class OBE {
	private static OBE instance;

	private OBE() {

	}

	public static synchronized OBE getInstance() {
		if (instance == null) {
			instance = new OBE();
		}

		return instance;
	}

	public void swapRow(Matrix mx, int row1, int row2) {
		for (int i = 0; i < mx.getCol(); i++) {
			double tempInt = mx.getElement(row1 - 1, i);
			mx.setElement(row1 - 1, i, mx.getElement(row2 - 1, i));
			mx.setElement(row2 -1, i, tempInt);
		}
	}

	public void multiplyRow(Matrix mx, int row1, double k) {
		for (int i = 0; i < mx.getCol(); i++) {
			mx.setElement(row1 - 1, i, mx.getElement(row1 - 1, i) * k);
	}

	public void divideRow(Matrix mx, int row1, double k) {
		for (int i = 0; i < mx.getCol(); i++) {
			mx.setElement(row1 - 1, i, mx.getElement(row1 - 1, i) / k);
		}
	}

	public void substractRow(Matrix mx, int rowTgt, int rowSrc, double k) {
		for (int i = 0; i < mx.getCol(); i++) {
			mx.setElement(rowTgt - 1, i, mx.getElement(rowTgt - 1, i) - (k * mx.getElement(rowSrc - 1, i)));
		}
	}
}