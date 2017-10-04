import java.util.ArrayList;

public class OperandPair {
		private double coefficient;
		private String var;

		@Override
		public boolean equals(Object o) {
		    if (!(o instanceof OperandPair)) {
		      return false;
		    }
		    OperandPair other = (OperandPair) o;
		    return coefficient == (other.coefficient) && var.equals(other.var);
		}

		public OperandPair(OperandPair copy) {
			this.coefficient = copy.coefficient;
			this.var = copy.var;
		}

		public OperandPair(double coefficient, String var) {
			this.coefficient = coefficient;
			this.var = var;
		}

		public OperandPair(String var) {
			this.coefficient = 1;
			this.var = var;
		}

		public OperandPair() {
			this.coefficient = 0;
			this.var = "nil";
		}

		public ArrayList<OperandPair> subtitute(ArrayList<OperandPair> expression) {
			ArrayList<OperandPair> result = new ArrayList<>();

			for (int i = 0; i < expression.size(); i++) {
				result.add(new OperandPair(this.coefficient * expression.get(i).coefficient, expression.get(i).var));
			}

			return result;
		}

		public void setCoefficient(double newVal) {
			this.coefficient = newVal;
		}

		public void setVar(String newVal) {
			this.var = newVal;
		}

		public double getCoefficient() {
			return this.coefficient;
		}

		public String getVar() {
			return this.var;
		}

		public String toString() {
			return (coefficient + var);
		}
	}