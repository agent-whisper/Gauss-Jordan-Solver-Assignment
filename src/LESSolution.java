import java.util.ArrayList;
import java.util.Scanner;



public class LESSolution extends Matrix {
		
	private String[] element;
	private int row;

	public LESSolution(int row) {
		this.row = row;
		element = new String[row];

		for (int i = 0; i < row; i++) {
			element[i] = "" + (char)(97 + i);
		}
	}

	@Override
	public int getRow() {
		return this.row;
	}

	public String getElement(int row) {
		return element[row];
	}

	public void setElement(int row, String val) {
		element[row] = (val);
	}

	public String toString() {
		String tempStr = "";

        for (int i = 0; i < row; i++) {
            System.out.format("%3c = %3s\n", (char)(97 + i), element[i]);
        }

        return tempStr;
	}

	// public String toString() {
 //        String tempStr = "";

 //        for (int i = 0; i < row; i++) {
 //            System.out.println(element[i]);
 //        }

 //        return tempStr;
 //    }

	public static void main(String[] args) {
		LESSolution test = new LESSolution(4);
		test.toString();
	}
}