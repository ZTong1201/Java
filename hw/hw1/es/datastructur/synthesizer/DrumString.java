package es.datastructur.synthesizer;

import edu.princeton.cs.algs4.StdAudio;
import java.util.HashSet;


public class DrumString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = 1.0; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public DrumString(double frequency) {
        int capacity = (int) Math.round((double) SR/frequency);
        buffer = new ArrayRingBuffer<Double>(capacity);
        for (int i = 0 ; i < capacity; i++) {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        //       Make sure that your random numbers are different from each
        //       other.
        for (int i = 0; i < buffer.capacity(); i++) {
            double r = Math.random() - 0.5;
            java.util.Set<Double> randomSet = new HashSet<>();
            if (!randomSet.contains(r)) {
                buffer.dequeue();
                randomSet.add(r);
                buffer.enqueue(r);
            }
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double front = buffer.dequeue();
        double second = buffer.peek();
        double newDouble = 0.0;
        double flag = Math.random();
        if (flag <= 0.5) {
            newDouble = (-1)*(front + second)*0.5*DECAY;
        } else {
            newDouble = (front + second)*0.5*DECAY;
        }
        buffer.enqueue(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        double front = buffer.peek();
        return front;
    }
}
