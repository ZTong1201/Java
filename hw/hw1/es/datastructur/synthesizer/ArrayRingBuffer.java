package es.datastructur.synthesizer;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T>  {

    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (fillCount == rb.length) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        last += 1;
        if (last == rb.length) {
            last = 0;
        }
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T returnItem = rb[first];
        rb[first] = null;
        first += 1;
        if (first == rb.length) {
            first = 0;
        }
        fillCount -= 1;
        return returnItem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T returnItem = rb[first];
        return returnItem;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    private class ArrayBufferIterator implements Iterator<T> {
        private int wizPos;
        private int nextCount;

        public ArrayBufferIterator() {
            wizPos = first;
            nextCount = 0;
        }

        public boolean hasNext() {
            return nextCount < fillCount;
        }

        public T next() {
            T returnItem = rb[wizPos];
            wizPos += 1;
            nextCount += 1;
            if (wizPos == rb.length) {
                wizPos = 0;
            }
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayBufferIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
        boolean equal = true;
        if (other.fillCount() != this.fillCount()) {
            return false;
        }
        for (int i = 0; i< this.fillCount(); i++) {
            T item1 = this.dequeue();
            T item2 = other.dequeue();
            if (item1 != item2) {
                equal = false;
            }
            this.enqueue(item1);
            other.enqueue(item2);
        }
        return equal;
    }

}
