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
        
        public void interpolasi_linier()
        {
             Scanner in = new Scanner(System.in);
          System.out.print("Banyak data : ");
          int N = in.nextInt();
          double dataX[] = new double[N];
          double dataY[] = new double[N];
          //input data x & y
          for(int i=0; i<N; i++){
               System.out.print("X"+(i+1)+" = ");
               dataX[i]=in.nextDouble();
               System.out.print("Y"+(i+1)+" = ");
               dataY[i]=in.nextDouble();
          }
          //mencetak data x & y
          System.out.print("\tX |");
          for(int i=0; i<N; i++){
               System.out.print(" "+dataX[i]+" |");
          }
          System.out.print("\n\tY |");
               for(int i=0; i<N; i++){
               System.out.print(" "+dataY[i]+" |");
          }
          //yang dicari
          System.out.println();
          System.out.print("\nTentukan Nilai X = ");
          double x=in.nextDouble();
          double x1=0,x2=0,y1=0,y2=0;
          //penyeleksian
          for(int i=0; i<N-1; i++){
               if(x>dataX[i] && x<dataX[i+1]){
               x1=dataX[i];
               x2=dataX[i+1];
               y1=dataY[i];
               y2=dataY[i+1];
               }
          }
          System.out.println("x1 = "+x1);
          System.out.println("x2 = "+x2);
          System.out.println("y1 = "+y1);
          System.out.println("y2 = "+y2);
          double y=y1+((y2-y1)/(x2-x1))*(x-x1);
          System.out.println("Titik terbaru adalah P3("+x+","+y+")");
        }
        
                
	public static void main (String[] args) {
		Matrix mx = new Matrix("myMatrix3.txt");
                
                
		System.out.println(new LESSolution(10).getRow());
		System.out.println("Original:" + (mx.getCol() - 1));
		mx.toString();

		System.out.println("Echelon form:");
		System.out.println(GJSolver.getInstance().GaussElim(mx).toString());
                
                System.out.println("Echelon reduced form:");
		System.out.println(GJSolver.getInstance().GaussJordan(mx).toString());
		// System.out.println(GJSolver.getInstance().GaussElim(mx).getRow());
		// GJSolver.getInstance().GaussElim(mx).getRow();
		// GJSolver.getInstance().multiSolutionSubstituter(4, "(0.71)d + (0.53)c + (0.53)b");
	}
}