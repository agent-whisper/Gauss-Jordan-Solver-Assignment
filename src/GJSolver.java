import java.math.BigDecimal; 
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

		int currRow = 0;
		int currCol = 0;

		while ((currRow < tempMatrix.getCol() - 1) && (currRow < tempMatrix.getRow())) {
			while ((tempMatrix.getElement(currRow, currCol) == 0) && (currCol < tempMatrix.getCol() - 1)) {
				currCol++;
			}

			if (currCol < tempMatrix.getCol() - 1) {
				for (int j = currRow + 1; j < tempMatrix.getRow(); j++) {
					if (Math.abs(tempMatrix.getElement(currRow, currCol)) < Math.abs(tempMatrix.getElement(j, currCol))) {
						OBE.getInstance().swapRow(tempMatrix, currRow + 1, j + 1);
					}
				}
				
				System.out.println ("Iteration " + currRow + ", pivoting");
				tempMatrix.toString();

				OBE.getInstance().divideRow(tempMatrix, currRow + 1, tempMatrix.getElement(currRow, currCol));

				for (int j = currRow + 1; j < tempMatrix.getRow(); j++) {
					OBE.getInstance().substractRow(tempMatrix, j + 1, currRow + 1, tempMatrix.getElement(j, currCol));
				}
			} else {
				System.out.println("Tak bisa diproses");
			}

			System.out.println ("Iteration " + currRow + ", partial echelon");
			tempMatrix.toString();
			currRow++;
			System.out.println();
		}

		return tempMatrix;
	}

	public Matrix getReducedEchelon(Matrix mx) {
		Matrix tempMatrix = new Matrix(mx);

		int currRow = 0;
		int currCol = 0;

		while ((currRow < tempMatrix.getCol() - 1) && (currRow < tempMatrix.getRow())) {
			while ((tempMatrix.getElement(currRow, currCol) == 0) && (currCol < tempMatrix.getCol() - 1)) {
				currCol++;
			}

			if (currCol < tempMatrix.getCol() - 1) {
				for (int j = currRow + 1; j < tempMatrix.getRow(); j++) {
					if (Math.abs(tempMatrix.getElement(currRow, currCol)) < Math.abs(tempMatrix.getElement(j, currCol))) {
						OBE.getInstance().swapRow(tempMatrix, currRow + 1, j + 1);
					}
				}
				
				System.out.println ("Iteration " + currRow + ", pivoting");
				tempMatrix.toString();

				OBE.getInstance().divideRow(tempMatrix, currRow + 1, tempMatrix.getElement(currRow, currCol));

				for (int j = 0; j < tempMatrix.getRow(); j++) {
					if (j != currRow){
						OBE.getInstance().substractRow(tempMatrix, j + 1, currRow + 1, tempMatrix.getElement(j, currCol));
					}
				}
			} else {
				System.out.println("Tak bisa diproses");
			}

			System.out.println ("Iteration " + currRow + ", partial echelon");
			tempMatrix.toString();
			currRow++;
			System.out.println();
		}

		return tempMatrix;
	}

	private Matrix backSub(Matrix mx) {
		if (!solutionExists(mx)) {
			System.out.println();
			System.out.println("Tidak ada solusi");
			System.out.println();
			return (new LESSolution(1));
		}

		if (!checkIfUnique(mx)) {
			LESSolution tempResult = new LESSolution(mx.getCol() - 1);

			System.out.println();
			System.out.println("Memulai multisolution procedure");
			System.out.println();
			for (int i = 0; i < mx.getRow(); i++) {
				tempResult.setElement(i, multiSolutionBuilder(mx.getRowSet(i)));
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
			System.out.println();
			System.out.println("Memulai uniquesolution procedure");
			System.out.println();
			return uniqueSolutionBuilder(mx);

		}
	}

	public Matrix GaussElim(Matrix mx) {
		Matrix tempMatrix = new Matrix(getEchelon(mx));
		return backSub(tempMatrix);		
	}

	public Matrix GaussJordan(Matrix mx) {
		Matrix tempMatrix = new Matrix(getReducedEchelon(mx));
		return backSub(tempMatrix);
	}
<<<<<<< HEAD

	public Matrix interpolate(Matrix data) {
		if (data.getCol() != 2) {
			System.out.println("Wrong data format");
			return (new Matrix());
		} else {
			Matrix interpolation = new Matrix (data.getRow(), data.getRow() + 1);
			for (int i = 0; i < interpolation.getRow(); i++) {
				for (int j = 0; j < interpolation.getCol(); j++) {
					interpolation.setElement(i, j, Math.pow(data.getElement(i,0), j));

					if (j == interpolation.getCol() - 1) {
						interpolation.setElement(i, j, data.getElement(i,1));
						
					}
				}

			}

			return GaussElim(interpolation);
		}
	}

	public double estimate(double x, Matrix mx) {
		Matrix interpolation = new Matrix(interpolate(mx));
		double y = 0;

		for (int i = 0; i < interpolation.getCol(); i++) {
			for (int j = 0; j < interpolation.getRow(); j++) {
				y += Math.pow(x, j) * interpolation.getElement(j, i);
			}
		}

		return y;
	}

	public static void main (String[] args) {
		Matrix mx = new Matrix("soal_f.txt");
        
 //        public static void lagrangeInterpolasi()
 //        {
            
 //           Scanner myScanner = new Scanner(System.in);
 //           int n; //Banyak angkanya
 //           int count, count2; //penghitung loop
 //           float numerator; //The numerator
 //           float denominator;  //The denominator
    
 //           //Promt a user to enter a value
 //            System.out.print("Banyak data: ");
 //            n = myScanner.nextInt(); //Store the value in n
 //            float [] arrayx = new float[n]; 
 //            float [] arrayy = new float[n]; 
            
 //           //Promt user to enter the array for X
 //            System.out.println("Isi nilai dalam X[i] : ");
            
 //            for(count = 0; count<n; count++) //Start the loop for X
 //            {
 //                 //Promp the user to enter the sequel for xi
 //                System.out.print("X[" + (count+1) + "]: ");
 //                //Store the sequel in the Array, arrayx
 //                arrayx[count] = myScanner.nextFloat();
 //            }
 //            //Promt user to enter the array for Y
 //            System.out.println("Isi nilai dalam Y[i] : ");
 //            for(count = 0; count<n; count++) // loop for Y
 //            {
 //                //Promp the user to enter the sequel for yi
 //                System.out.print("Y[" + (count+1) + "]: ");
 //                //Store the sequel in the Array, arrayy
 //                arrayy[count] = myScanner.nextFloat();
 //            }
 //            int derajatPolinom = arrayx.length - 1;
 //            //Promp the user to enter any (the arbitray)
 //            //value x to get the corresponding value of y
 //            int input = 1;
 //            while(input != 0)
 //            {
 //            float x = 0;
 //            float y = 0;
 //            System.out.print("Isi nilai x yang ingin dicari f(x)nya: ");
 //            x = myScanner.nextFloat();  //Store the value in x
             
 //            //first Loop for the polynomial calculation
 //            for(count = 0; count<n; count++)
 //            {
 //                 //Initialisation of variable
 //                numerator = 1; denominator = 1;
                 
 //                //second Loop for the polynomial calculation
 //                for(count2 = 0; count2<n; count2++)
 //                {
 //                    if (count2 != count)
 //                    {
 //                      numerator = numerator * (x - arrayx[count2]);
 //                      denominator = denominator * (arrayx[count] - arrayx[count2]);
 //                    } 
 //                }
 //                y = y + (numerator/denominator) * arrayy[count];
 //            }
 //            System.out.println("Derajat polinom = " + derajatPolinom);
 //            System.out.println("XHasil Interpolasi pada titik x = " + x + " adalah " +  y);
 //            System.out.println("Input lagi? (0/1)");
 //            input = myScanner.nextInt();
 //            }
 //        }
               
	// public static void main (String[] args) {
	// 	/*Matrix mx = new Matrix("myMatrix3.txt");
                
                
	// 	System.out.println(new LESSolution(10).getRow());
	// 	System.out.println("Original:" + (mx.getCol() - 1));
	// 	Matrix mx = new Matrix("myMatrix2.txt");

		System.out.println("Original:");
		mx.toString();
		System.out.println();

		System.out.println("Echelon form:");

		System.out.println();
		System.out.println(GJSolver.getInstance().estimate(1, mx));
		// GJSolver.getInstance().GaussElim(mx).toString();
		// GJSolver.getInstance().GaussJordan(mx).toString();
		// GJSolver.getInstance().interpolate(mx).toString();
                
		// System.out.println("Echelon reduced form:");
		// System.out.println(GJSolver.getInstance().GaussJordan(mx).toString());
		// System.out.println(GJSolver.getInstance().GaussElim(mx).getRow());
		// GJSolver.getInstance().GaussElim(mx).getRow();
		// GJSolver.getInstance().multiSolutionSubstituter(4, "(0.71)d + (0.53)c + (0.53)b");*/
                lagrangeInterpolasi();
                
	}
}