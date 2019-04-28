/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
	
	/* Utility method for printing out empty checks. */
	public static boolean checkEmpty(boolean expected, boolean actual) {
		if (expected != actual) {
			System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Utility method for printing out empty checks. */
	public static boolean checkSize(int expected, int actual) {
		if (expected != actual) {
			System.out.println("size() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	public static boolean checkItem(String expected, String actual) {
		if (expected != actual) {
			System.out.println("get() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Prints a nice message based on whether a test passed. 
	 * The \n means newline. */
	public static void printTestStatus(boolean passed) {
		if (passed) {
			System.out.println("Test passed!\n");
		} else {
			System.out.println("Test failed!\n");
		}
	}

	/** Adds a few things to the list, checking isEmpty() and size() are correct, 
	  * finally printing the results. 
	  *
	  * && is the "and" operation. */
	public static void addIsEmptySizeTest() {
		System.out.println("Running add/isEmpty/Size test.");

		LinkedListDeque1<String> lld1 = new LinkedListDeque1<String>();

		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst("front");
		
		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
		passed = checkSize(1, lld1.size()) && passed;
		passed = checkEmpty(false, lld1.isEmpty()) && passed;
		passed = checkItem("front", lld1.get(0)) && passed;
		passed = checkItem("front", lld1.getRecursive(0)) && passed;

		lld1.addLast("middle");
		passed = checkSize(2, lld1.size()) && passed;
		passed = checkItem("middle", lld1.get(1)) && passed;
		passed = checkItem("middle", lld1.getRecursive(1)) && passed;

		lld1.addLast("back");
		passed = checkSize(3, lld1.size()) && passed;
		passed = checkItem("back", lld1.get(2)) && passed;
		passed = checkItem("back", lld1.getRecursive(2)) && passed;
		passed = checkItem(null, lld1.getRecursive(3)) && passed;

		System.out.println("Printing out deque: ");
		lld1.printDeque();

		LinkedListDeque2<String> lld2 = new LinkedListDeque2<String>();

		boolean passed2 = checkEmpty(true, lld2.isEmpty());

		lld2.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
		passed2 = checkSize(1, lld2.size()) && passed2;
		passed2 = checkEmpty(false, lld2.isEmpty()) && passed2;
		passed2 = checkItem("front", lld2.get(0)) && passed2;
		passed2 = checkItem("front", lld2.getRecursive(0)) && passed2;

		lld2.addLast("middle");
		passed2 = checkSize(2, lld2.size()) && passed2;
		passed2 = checkItem("middle", lld2.get(1)) && passed2;
		passed2 = checkItem("middle", lld2.getRecursive(1)) && passed2;

		lld2.addLast("back");
		passed2 = checkSize(3, lld2.size()) && passed2;
		passed2 = checkItem("back", lld2.get(2)) && passed2;
		passed2 = checkItem("back", lld2.getRecursive(2)) && passed2;
		passed2 = checkItem(null, lld2.getRecursive(3)) && passed2;

		System.out.println("Printing out deque: ");
		lld2.printDeque();

		printTestStatus(passed);
		printTestStatus(passed2);

	}

	/** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
	public static void addRemoveTest() {

		System.out.println("Running add/remove test.");

		LinkedListDeque1<Integer> lld1 = new LinkedListDeque1<Integer>();
		// should be empty 
		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		System.out.println("Printing out deque: ");
		lld1.printDeque();
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.removeFirst();
		// should be empty 
		passed = checkEmpty(true, lld1.isEmpty()) && passed;

		LinkedListDeque2<Integer> lld2 = new LinkedListDeque2<Integer>();
		// should be empty
		boolean passed2 = checkEmpty(true, lld2.isEmpty());

		lld2.addFirst(10);
		// should not be empty
		System.out.println("Printing out deque: ");
		lld2.printDeque();
		passed2 = checkEmpty(false, lld2.isEmpty()) && passed2;

		lld2.removeFirst();
		// should be empty
		passed2 = checkEmpty(true, lld2.isEmpty()) && passed2;


		printTestStatus(passed);
		printTestStatus(passed2);
	}

	public static void main(String[] args) {
		System.out.println("Running tests.\n");
		addIsEmptySizeTest();
		addRemoveTest();
	}
} 