import java.util.ArrayList;
import java.util.Scanner;



public class LESSolution extends Matrix {
		
	private ArrayList<OperandPair>[] expression;
	private int row;

	public LESSolution(int row) {
		this.row = row;
		expression = (ArrayList<OperandPair>[])new ArrayList[row];

		for (int i = 0; i < row; i++) {
			expression[i] = new ArrayList<OperandPair>();
			expression[i].add(new OperandPair (String.format("%c", (char)(97 + i))));
		}
	}

	public LESSolution(LESSolution copy) {
		this.row = copy.row;
		expression = (ArrayList<OperandPair>[])new ArrayList[this.row];

		for (int i = 0; i < row; i++) {
			expression[i] = new ArrayList<OperandPair>();
			for (int j = 0; j < copy.expression.length; j++) {
				expression[i].add(new OperandPair(copy.expression[i].get(j)));
			}
		}
	}

	@Override
	public int getRow() {
		return this.row;
	}

	public ArrayList<OperandPair> getElement(int row) {
		return expression[row];
	}

	public void setElement(int row, ArrayList<OperandPair> val) {
		expression[row].clear();

		for (int i = 0; i < val.size(); i++) {
			expression[row].add(val.get(i));
		}
	}

	public String toString() {
		String tempStr = "";

		System.out.println("Hasil:");
        for (int i = 0; i < row; i++) {
            System.out.format("%3c = ", (char)(97 + i));
            
        	for (int j = 0; j < expression[i].size(); j++) {
            	System.out.format("%.4f %s %s", expression[i].get(j).getCoefficient(), expression[i].get(j).getVar(), (j == expression[i].size() - 1 ? "" : "+ "));
        	}
        	System.out.println();
        }

        return tempStr;
	}

	public void addSameVar() {
		for (int i = 0; i < expression.length; i++) {
			for (int j = 0; j < expression[i].size(); j++) {
				String currVar = expression[i].get(j).getVar();
				for (int k = j + 1; k < expression[i].size(); k++) {
					if (expression[i].get(k).getVar() == currVar) {
						expression[i].get(j).setCoefficient(expression[i].get(j).getCoefficient() + expression[i].get(k).getCoefficient());
						expression[i].remove(k--);
					}
				}
			}
		}
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