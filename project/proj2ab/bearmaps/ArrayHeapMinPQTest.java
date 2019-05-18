package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;

public class ArrayHeapMinPQTest {

    @Test
    public void sanityAddTest()  {
        ArrayHeapMinPQ<String> myPQ = new ArrayHeapMinPQ<>();
        myPQ.add("job", 1);
        myPQ.add("work", 5);
        myPQ.add("zzz", 4);
        myPQ.add("dog", 3);
        myPQ.add("ton", 11);
        myPQ.add("apple", 6);
        myPQ.add("peach", 2);
        myPQ.add("banana", 7);
        myPQ.add("panda", 9);
        assertEquals("job", myPQ.getSmallest());
        assertEquals("job", myPQ.removeSmallest());
        assertEquals("peach", myPQ.getSmallest());
        assertNotEquals("dog", myPQ.getSmallest());
    }

    @Test
    public void sanityContainsTest() {
        ArrayHeapMinPQ<String> myPQ = new ArrayHeapMinPQ<>();
        for(int i = 0; i < 10; i++) {
            myPQ.add("check" + i, i);
        }
        assertTrue(myPQ.contains("check4"));
        assertFalse(myPQ.contains("check10"));
    }

    @Test
    public void sanityChangePriorityTest() {
        ArrayHeapMinPQ<String> myPQ = new ArrayHeapMinPQ<>();
        for(int i = 0; i < 10; i++) {
            myPQ.add("check" + i, i + 1);
        }
        myPQ.changePriority("check4", 0);
        assertTrue(myPQ.contains("check4"));
        assertEquals("check4", myPQ.getSmallest());
    }

    @Test
    public void timeSpendTest() {
        NaiveMinPQ<Integer> naiveMinPQ = new NaiveMinPQ<>();
        long start1 = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++) {
            naiveMinPQ.add(i, i + 1);
        }
        for(int j = 0; j < 3000; j++) {
            int randomNum = StdRandom.uniform(1000000);
            naiveMinPQ.contains(randomNum);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("Time Elapsed: " + (end1 - start1));

        ArrayHeapMinPQ<Integer> myMinPQ = new ArrayHeapMinPQ<>();
        long start2 = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++) {
            myMinPQ.add(i, i + 1);
        }
        for(int j = 0; j < 3000; j++) {
            int randomNum = StdRandom.uniform(1000000);
            myMinPQ.contains(randomNum);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Time Elapsed: " + (end2 - start2));
    }
}
