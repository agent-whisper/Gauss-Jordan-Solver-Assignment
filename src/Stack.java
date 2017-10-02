import java.util.ArrayList;

public class Stack<T> {
	private int top = -1;
	private ArrayList<T> elements;

	public Stack() {
		elements = new ArrayList<>();
	}

	public Stack(Stack<T> aCopy) {
		elements = new ArrayList<>();

		for (T el : aCopy.elements) {
			elements.add(el);
		}
	}

	public void push(T newVal) {
		elements.add(newVal);
	}

	public T pop() {
		if (!isEmpty()) {
			T temp = elements.get(elements.size() - 1);
			elements.remove(elements.size() - 1);

			return temp;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	public int size() {
		return elements.size();
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public static void main(String[] args) {
		Stack<String> st = new Stack<String>();

		st.push("test 0");
		st.pop();
		System.out.println(st.pop());
		// try {
		// } catch (IndexOutOfBoundsException e) {
		// 	System.out.println("does it work?");
		// }


	}
}