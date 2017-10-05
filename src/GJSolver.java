import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
        
public class GJSolver {
	private static GJSolver instance;

	private class operandPair {
		public double coefficient;
		public String var;

		public operandPair(double coefficient, String var) {
			this.coefficient = coefficient;
			this.var = var;
		}

		public ArrayList<operandPair> subtitute(ArrayList<operandPair> expression) {
			ArrayList<operandPair> result = new ArrayList<>();

			for (int i = 0; i < expression.size(); i++) {
				result.add(new operandPair(this.coefficient * expression.get(i).coefficient, expression.get(i).var));
			}

			return result;
		}
	}

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
				tempStr = tempStr + String.format("%.2f", num) + ((i < aRow.length - 1 ) ? " " + ((char) (97 + i) + " * ") : "") + ((i > currCol + 1) ? " + ": "");
			}

		}		

		return tempStr;
	}

	// public void multiSolutionSubstituter(LESSolution solution) {
	// 	Pattern aChar = Pattern.compile("[a-z]*");
	// 	Pattern anOperator = Pattern.compile("[^a-z[//+//*]]");

	// 	for (int rowIndex = solution.getRow() - 1; rowIndex >= 0; rowIndex--) {
	// 		System.out.println("Sekarang di variabel ke " + (rowIndex + 1));
	// 		Scanner strReader = new Scanner(solution.getElement(rowIndex));
	// 		Stack<String> expression = new Stack<>();
	// 		String buffer = "";

	// 		while (strReader.hasNext()) {
	// 			String holder = strReader.next();

	// 			Matcher charMatcher = aChar.matcher(holder);
	// 			Matcher opMatcher = anOperator.matcher(holder);

	// 			if (!charMatcher.matches() && !opMatcher.matches()) {
	// 				expression.push(holder);
	// 			} else {
	// 				if (charMatcher.matches()) {
	// 					System.out.println("Ketemu sebuah variabel " + holder);
	// 					if (((int) holder.charAt(0)) - 97 != rowIndex) {
	// 						System.out.println("Variabel akan disubstitusi dengan: " + solution.getElement(((int) holder.charAt(0)) - 97));
	// 						Scanner subsReader = new Scanner(solution.getElement(((int) holder.charAt(0)) - 97));
	// 						// subsReader.useDelimiter("[^a-z]");
	// 						// subsReader.useDelimiter("//+");
	// 						while (subsReader.hasNext()) {
	// 							String subHolder = subsReader.next();

	// 							if (aChar.matcher(subHolder).matches()) {
	// 								System.out.println(rowIndex + " --> " + subHolder);
	// 							}
	// 						}
	// 					}
	// 				} else {
	// 					if (holder == "+") {
							
	// 					}
	// 				}
	// 			}
	// 		}

	// 		System.out.println(buffer);
	// 	}
	// }

	// public void multiSolutionSubstituter(LESSolution solution) {
	// 	Pattern aChar = Pattern.compile("[a-z]*");
	// 	Pattern anOperator = Pattern.compile("[^a-z[//+//*]]");

	// 	for (int rowIndex = solution.getRow() - 1; rowIndex >= 0; rowIndex--) {
	// 		Scanner strReader = new Scanner(solution.getElement(rowIndex));
	// 		ArrayList<String> vary = new ArrayList<>();
	// 		ArrayList<Double> constants = new ArrayList<>();
	// 		ArrayList<String> operator = new ArrayList<>();
	// 		String buffer = "";

	// 		while (strReader.hasNext()) {
	// 			String holder = strReader.next();
	// 			boolean wasConst = false;
	// 			Matcher charMatcher = aChar.matcher(holder);
	// 			Matcher opMatcher = anOperator.matcher(holder);

	// 			if (!charMatcher.matches() && !opMatcher.matches()) {
	// 				constants.add(holder);
	// 				wasConst = true;
	// 			} else {
	// 				if (charMatcher.matches()) {
	// 					vary.add(holder);
	// 				} else {
	// 					if (wasConst) {
	// 						vary.add("");
	// 					}
	// 					operator.add(holder);
	// 				}
	// 				wasConst = false;
	// 			}
	// 		}

	// 		for (String v : vary) {
	// 			if (((int) v.charAt(0)) - 97 != rowIndex) {
					
	// 			}
	// 		}

	// 		System.out.println(buffer);
	// 	}
	// }

	private Matrix uniqueSolutionBuilder(Matrix aMatrix) {
		int currRow = aMatrix.getCol() - 2;
		int currCol;
		Matrix solution = new Matrix(aMatrix.getCol() - 1, 1);

		while (currRow >= 0) {
			currCol = currRow + 1;
			solution.setElement(currRow, 0, (aMatrix.getElement(currRow, aMatrix.getCol() - 1)));

			while (currCol < aMatrix.getCol() - 1) {
				// solution.setElement(currRow, 0, (aMatrix.getElement(currRow, aMatrix.getCol() - 1)));
				System.out.println("Current row: " + currRow + " Current col: " + currCol + " Current constant: " + aMatrix.getElement(currRow, currCol) + " Variable value: "+ solution.getElement(currCol, 0));
				solution.setElement(currRow, 0, ((-1) * aMatrix.getElement(currRow, currCol) * solution.getElement(currCol, 0) + solution.getElement(currRow, 0)));
				System.out.println("Current variable value: " + solution.getElement(currRow, 0));
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
			
			while (tempMatrix.getElement(leadingOne, leadingOne) == 0) {
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

			for (int i = 0; i < tempMatrix.getRow(); i++) {
				tempResult.setElement(i, multiSolutionBuilder(tempMatrix.getRowSet(i)));
			}

			//multiSolutionSubstituter(tempResult);
			return tempResult;
		} else {
			return uniqueSolutionBuilder(tempMatrix);

		}
	}

	public Matrix GaussJordan(Matrix mx) {
		Matrix tempMatrix = new Matrix(getReducedEchelon(mx));
		return tempMatrix;
	}
        
        public static void lagrangeInterpolasi()
        {
            Scanner in = new Scanner(System.in);
            System.out.print("Banyak data : ");
            int N = in.nextInt();
            Double[] nilaiX = new Double[N];
            Double[] nilaiY = new Double[N];
            
            for(int i=0; i<N; i++)
            {
               System.out.print("X"+(i+1)+" = ");
               nilaiX[i]=in.nextDouble();
               System.out.print("Y"+(i+1)+" = ");
               nilaiY[i]=in.nextDouble();
            }
            
            System.out.println("Nilai x dan y yang diketahui:");
            for (int i = 0; i < nilaiX.length; i++) 
            {
                System.out.println("x = " + nilaiX[i] + " ,\t y = " + nilaiY[i]);
            }
            int input = 1;
            while(input != 0)
            {
                System.out.println("Masukan nilai x yang ingin dicari f(x)nya:");
                double x = in.nextDouble();
                BigDecimal titikX = BigDecimal.valueOf(x);
                int derajatPolinom = nilaiX.length - 1;
                int tingkatKetelitian = 1; //kalau 1, 10^-9. kalau 2, 10^-12. kalau 3, 10^-15
            
                BigDecimal lagrange = new BigDecimal("0");
                for (int i = 0; i <= derajatPolinom; i++) 
                {
                    BigDecimal pi = new BigDecimal("1");
                    for (int j = 0; j <= derajatPolinom; j++) 
                    {
                        if (i != j) 
                        {
                            pi = pi.multiply((titikX.subtract(BigDecimal
                                    .valueOf(nilaiX[j]))).divide(
                                BigDecimal.valueOf(nilaiX[i]).subtract(
                                        BigDecimal.valueOf(nilaiX[j])),
                                tingkatKetelitian, RoundingMode.HALF_EVEN));
                        }
                    }
                    lagrange = lagrange.add(pi.multiply(BigDecimal.valueOf(nilaiY[i])));
                }
            // Tamplkan hasil Interpolasi
            System.out.println("Derajat polinom = " + derajatPolinom);
            System.out.println("Hitung nilai y pada titik x = " + titikX + "!");
            System.out.println("------------------------------------------------\n");
            System.out.println("Hasil Interpolasi pada titik x = " + titikX + " adalah " + lagrange);
        
            System.out.println("Masih ada data? (0/1)");
                    input = in.nextInt();
        }}
               
	public static void main (String[] args) {
		/*Matrix mx = new Matrix("myMatrix3.txt");
                
                
		System.out.println(new LESSolution(10).getRow());
		System.out.println("Original:" + (mx.getCol() - 1));
		mx.toString();

		System.out.println("Echelon form:");
		System.out.println(GJSolver.getInstance().GaussElim(mx).toString());
                
                System.out.println("Echelon reduced form:");
		System.out.println(GJSolver.getInstance().GaussJordan(mx).toString());
		// System.out.println(GJSolver.getInstance().GaussElim(mx).getRow());
		// GJSolver.getInstance().GaussElim(mx).getRow();
		// GJSolver.getInstance().multiSolutionSubstituter(4, "(0.71)d + (0.53)c + (0.53)b");*/
                lagrangeInterpolasi();
                
	}
}