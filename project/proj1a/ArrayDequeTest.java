public class ArrayDequeTest {
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

    public static boolean checkItem(int expected, int actual) {
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

        ArrayDeque1<Integer> lld1 = new ArrayDeque1<Integer>();

        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(5);

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        passed = checkSize(1, lld1.size()) && passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;
        passed = checkItem(5, lld1.get(0)) && passed;

        lld1.addLast(6);
        passed = checkSize(2, lld1.size()) && passed;
        passed = checkItem(6, lld1.get(1)) && passed;

        lld1.addLast(7);
        passed = checkSize(3, lld1.size()) && passed;
        passed = checkItem(7, lld1.get(2)) && passed;
        //passed = checkItem(null, lld1.get(3)) && passed;

        lld1.addFirst(4);
        passed = checkItem(4, lld1.get(0)) && passed;
        passed = checkItem(7, lld1.get(3)) && passed;

        lld1.addFirst(3);
        passed = checkItem(3, lld1.get(0)) && passed;
        passed = checkItem(4, lld1.get(1)) && passed;

        lld1.addLast(8);
        passed = checkItem(3, lld1.get(0)) && passed;
        passed = checkItem(8, lld1.get(5)) && passed;

        lld1.addLast(9);
        passed = checkItem(3, lld1.get(0)) && passed;
        passed = checkItem(9, lld1.get(6)) && passed;

        lld1.removeFirst();
        passed = checkItem(4, lld1.get(0)) && passed;
        passed = checkSize(6, lld1.size()) && passed;

        lld1.removeLast();
        passed = checkItem(4, lld1.get(0)) && passed;
        passed = checkItem(8, lld1.get(4)) && passed;
        passed = checkSize(5, lld1.size()) && passed;


        System.out.println("Printing out deque: ");
        lld1.printDeque();

        printTestStatus(passed);

    }

    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public static void addRemoveTest() {

        System.out.println("Running add/remove test.");

        ArrayDeque1<Integer> lld1 = new ArrayDeque1<>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(4);
        lld1.addFirst(3);
        lld1.addFirst(2);
        lld1.addLast(5);
        lld1.addLast(6);
        lld1.addLast(7);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addFirst(1);
        lld1.addFirst(0);
        lld1.addFirst(-1);
        lld1.addFirst(-2);
        lld1.addFirst(-3);
        passed = checkItem(-3,lld1.get(0)) && passed;
        passed = checkItem(5,lld1.get(8)) && passed;
        ArrayDeque1<Integer> lld2 = new ArrayDeque1<>();
        // should be empty
        boolean passed2 = checkEmpty(true, lld2.isEmpty());
        lld2.addFirst(3);
        lld2.addFirst(3);
        lld2.addFirst(3);
        lld2.addFirst(3);
        lld2.addLast(7);
        lld2.addLast(8);
        lld2.addLast(9);
        lld2.addLast(6);
        lld2.addLast(0);
        lld2.addLast(5);
        lld2.addFirst(3);
        lld2.addFirst(3);
        lld2.addFirst(3);
        lld2.addFirst(3);
        lld2.addFirst(3);
        lld2.addFirst(4);
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeLast();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeFirst();
        System.out.println("Printing out deque: ");
        lld2.printDeque();
        lld2.removeFirst();
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
