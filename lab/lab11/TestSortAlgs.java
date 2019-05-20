import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> test1 = new Queue<>();
        for(int i = 0; i < 100; i++) {
            test1.enqueue(StdRandom.uniform(50));
        }
        Queue<Integer> sortedTest1 = QuickSort.quickSort(test1);
        for(int i = 0; i < 99; i++) {
            Integer smaller = sortedTest1.dequeue();
            Integer greater = sortedTest1.peek();
            assertTrue(greater.compareTo(smaller) >= 0);
        }

    }

    @Test
    public void testMergeSort() {
        Queue<Integer> test1 = new Queue<>();
        for(int i = 0; i < 100; i++) {
            test1.enqueue(StdRandom.uniform(50));
        }
        Queue<Integer> sortedTest1 = MergeSort.mergeSort(test1);
        for(int i = 0; i < 99; i++) {
            Integer smaller = sortedTest1.dequeue();
            Integer greater = sortedTest1.peek();
            assertTrue(greater.compareTo(smaller) >= 0);
        }

    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
