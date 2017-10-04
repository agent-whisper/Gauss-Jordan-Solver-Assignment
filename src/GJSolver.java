import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Objects;

public class GJSolver {
	private static GJSolver instance;

	//Fungsi inisiasi singleton
	public static synchronized GJSolver getInstance() {
		if (instance == null) {
			instance = new GJSolver();
		}

		return instance;
	}

	//Fungsi mengecek keberadaan solusi
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

	//Cek apakah sebuah SPL unik
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

	private ArrayList<OperandPair> multiSolutionBuilder(double[] aRow) {
		int currCol = 0;
		ArrayList<OperandPair> result = new ArrayList<>();

		while ((aRow[currCol] == 0) && (currCol < aRow.length - 1)) {
			currCol++;
		}

		// if ((currCol == aRow.length - 1) && (aRow[currCol] == 0)) {
		// 	return ("0");
		// }

		for (int i = aRow.length - 1; i > currCol; i--) {
			double num = ((i < aRow.length - 1) ? (-1) : 1) * (aRow[i] / aRow[currCol]);
			if (num != 0) {
				result.add(new OperandPair(num, ((i < aRow.length - 1 ) ? "" + ((char) (97 + i) + " ") : "")));
				//tempStr = tempStr + String.format("%.2f", num) + ((i < aRow.length - 1 ) ? " " + ((char) (97 + i) + " * ") : "") + ((i > currCol + 1) ? " + ": "");
			}

		}		

		return result;
	}

	private Matrix uniqueSolutionBuilder(Matrix aMatrix) {
		int currRow = aMatrix.getCol() - 2;
		int currCol;
		Matrix solution = new Matrix(aMatrix.getCol() - 1, 1);

		while (currRow >= 0) {
			currCol = currRow + 1;
			solution.setElement(currRow, 0, (aMatrix.getElement(currRow, aMatrix.getCol() - 1)));

			while (currCol < aMatrix.getCol() - 1) {
				//System.out.println("Current row: " + currRow + " Current col: " + currCol + " Current constant: " + aMatrix.getElement(currRow, currCol) + " Variable value: "+ solution.getElement(currCol, 0));
				solution.setElement(currRow, 0, ((-1) * aMatrix.getElement(currRow, currCol) * solution.getElement(currCol, 0) + solution.getElement(currRow, 0)));
				//System.out.println("Current variable value: " + solution.getElement(currRow, 0));
				currCol++;
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

		while ((currRow < tempMatrix.getCol() - 1) && (leadingOne < tempMatrix.getCol() - 1) && (currRow < tempMatrix.getRow())) {
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
			
			while (tempMatrix.getElement(currRow, leadingOne) == 0) {
				leadingOne++;
			}
			
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
                
                int rowPengurang = tempMatrix.getRow()-1; //row yg d jadikan pengurang (diambil row 3 dlu, krn row 3 leading one ada d paling ujung kanan).
                int colPengurang = 0;
                                        
                while(rowPengurang != 0)
                {
                    while(tempMatrix.getElement(rowPengurang,colPengurang) != 1 && colPengurang <= tempMatrix.getCol()-2)
                    {colPengurang++;}
                    
                    if(tempMatrix.getElement(rowPengurang,colPengurang) == 1)
                    {
                        for(int i = 0; i < rowPengurang; i++)
                        {
                            
                                if(tempMatrix.getElement(i,colPengurang) != 0)
                                {
                                    OBE.getInstance().substractRow(tempMatrix, i+1, rowPengurang+1, tempMatrix.getElement(i,colPengurang));
                                }
                            
                        }
                    }
                        
                    rowPengurang--;
                    colPengurang = 0;
                }
                
                
		return tempMatrix;
	}

	public Matrix GaussElim(Matrix mx) {
		Matrix tempMatrix = new Matrix(getEchelon(mx));

		if (!solutionExists(tempMatrix)) {
			System.out.println("Tidak ada solusi");
			return (new LESSolution(1));
		}

		if (!checkIfUnique(tempMatrix)) {
			LESSolution tempResult = new LESSolution(mx.getCol() - 1);

			System.out.println("Memulai multisolution procedure");
			for (int i = 0; i < tempMatrix.getRow(); i++) {
				tempResult.setElement(i, multiSolutionBuilder(tempMatrix.getRowSet(i)));
			}

			// System.out.println("Solusi sebelum diproses: ");
			// tempResult.toString();

			for (int i = tempResult.getRow() - 2; i >= 0; i--) {
				int bufferSize = tempResult.getElement(i).size();
				ArrayList<OperandPair> loc = new ArrayList<>();

				for (int j = 0; j < bufferSize; j++) {
					if (!Objects.equals(tempResult.getElement(i).get(j).getVar(), "")) {
						loc.add(new OperandPair(tempResult.getElement(i).get(j)));
						
						ArrayList<OperandPair> subHolder = new ArrayList<>();
						for (int l = 0; l < tempResult.getElement(((int) tempResult.getElement(i).get(j).getVar().charAt(0)) - 97).size(); l++) {
							subHolder.add(tempResult.getElement(i).get(j).subtitute(tempResult.getElement(((int) tempResult.getElement(i).get(j).getVar().charAt(0)) - 97)).get(l));
						} 

						for (int k = 0; k < subHolder.size(); k++) {
							tempResult.getElement(i).add(new OperandPair(subHolder.get(k)));
						}
					}
				}

				for (int j = 0; j < loc.size(); j++) {
					// System.out.println(loc.get(j);
					tempResult.getElement(i).remove(loc.get(j));
				}

				// System.out.println();
			}
			// System.out.println("Solusi setelah disubstitusi: ");
			// tempResult.toString();

			tempResult.addSameVar();
			// System.out.println("Solusi setelah dijumlah: ");
			// tempResult.toString();

			return tempResult;
		} else {
			System.out.println("Memulai uniquesolution procedure");
			return uniqueSolutionBuilder(tempMatrix);

		}
	}

	public Matrix GaussJordan(Matrix mx) {
		Matrix tempMatrix = new Matrix(getReducedEchelon(mx));
		return tempMatrix;
	}

	public static void main (String[] args) {
		Matrix mx = new Matrix("not_found.txt");

		System.out.println("Original:");
		mx.toString();
		System.out.println();

		System.out.println("Echelon form:");

		System.out.println();
		GJSolver.getInstance().GaussElim(mx).toString();
                
		// System.out.println("Echelon reduced form:");
		// System.out.println(GJSolver.getInstance().GaussJordan(mx).toString());
		// System.out.println(GJSolver.getInstance().GaussElim(mx).getRow());
		// GJSolver.getInstance().GaussElim(mx).getRow();
		// GJSolver.getInstance().multiSolutionSubstituter(4, "(0.71)d + (0.53)c + (0.53)b");
	}
}