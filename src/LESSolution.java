import java.util.ArrayList;
import java.util.Scanner;
import java.text.NumberFormat;
import java.io.*;



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
			for (int j = 0; j < copy.expression[i].size(); j++) {
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

	@Override
	public void save(String fileDir) {
		BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(fileDir));
            
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < expression[i].size(); j++) {
                    writer.write((expression[i].get(j).toString()));

                    if ((j == expression[i].size() - 1) && (i != this.row - 1))
                        writer.newLine();
                    else 
                        writer.write(" ");
                }
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error in saving file!");
        }
	}

	public String toString() {
		for (int i = 0; i < this.row; i++) {
			System.out.format("%3c =", (char) (97 + i));
            for (int j = 0; j < expression[i].size(); j++) {
            	System.out.format(" %.4f %s ", expression[i].get(j).getCoefficient(), expression[i].get(j).getVar());
            	if (j >= expression[i].size() - 1) {
                    System.out.format("\n");
            	}
                else {
                    System.out.format("+");
                }
            }
        }
        return "";
	}

	public static void main(String[] args) {
		LESSolution test = new LESSolution(4);
		test.toString();
	}
}