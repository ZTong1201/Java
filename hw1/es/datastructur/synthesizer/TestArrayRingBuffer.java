package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {

    @Test
    public void IterationTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.dequeue();
        arb.enqueue(4);
        for (int i : arb) {
            System.out.println(i);
        }
    }

    @Test
    public void EqualTest() {
        ArrayRingBuffer<Integer> arb1 = new ArrayRingBuffer<Integer>(10);
        arb1.enqueue(1);
        arb1.enqueue(2);
        arb1.enqueue(3);
        ArrayRingBuffer<Integer> arb2 = new ArrayRingBuffer<Integer>(10);
        arb2.enqueue(1);
        arb2.enqueue(2);
        arb2.enqueue(3);
        ArrayRingBuffer<Integer> arb3 = new ArrayRingBuffer<Integer>(10);
        arb3.enqueue(1);
        arb3.enqueue(2);
        arb3.enqueue(4);
        ArrayRingBuffer<Integer> arb4 = new ArrayRingBuffer<Integer>(10);
        arb4.enqueue(1);
        arb4.enqueue(2);
        arb4.enqueue(3);
        assertTrue(arb1.equals(arb2));
        assertFalse(arb1.equals(arb3));
        assertTrue(arb2.equals(arb4));
    }
}
