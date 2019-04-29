public class ArrayDeque2<T> {

    /* Another implementation of array-based deque for better get(), add() and remove()
    * by taking remainder. However, resizing procedure can be slower since arraycopy() method is not utilized. */

    private int size;
    private int nextFirst, nextLast;
    private T[] items;

    /* Create and empty array-based deque. */
    public ArrayDeque2() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = items.length/2;
        nextLast = items.length/2+1;
    }

    /* Deep-copy of another deque */
    public ArrayDeque2(ArrayDeque2 other) {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = items.length/2;
        nextLast = items.length/2+1;

        for (int i=0; i<other.size(); i++) {
            addFirst((T) other.get(i));
        }
    }

    /* Return the number of elements in the deque */
    public int size() {
        return size;
    }

    /* Return true if the deque is empty, false otherwise */
    public boolean isEmpty() {
        return size==0;
    }

    /* Some helper functions to compute next index for insertion */
    private int minusOne(int index){
        if (index==0){
            return items.length - 1;
        }
        else {
            return index - 1;
        }
    }

    private int plusOne(int index) {
        return index = (index + 1) % items.length;
    }

    /* Resize the deque when necessary */
    private void resize(int newSize) {
        T[] a = (T[]) new Object[newSize];
        int j = 0;
        for (int i = plusOne(nextFirst);i!=minusOne(nextLast);i = plusOne(i)){
            a[j] = items[i];
            j = plusOne(j);
        }
        a[j] = items[minusOne(nextLast)];
        items = a;
        nextFirst = items.length - 1;
        nextLast = j+1;
    }

    /* Add item at the front of the deque */
    public void addFirst(T i) {
        items[nextFirst] = i;
        size += 1;
        nextFirst = minusOne(nextFirst);
        if (size==items.length) {
            resize(size*2);
        }
    }

    /* Add item at the end of the deque */
    public void addLast(T i) {
        items[nextLast] = i;
        size += 1;
        nextLast = plusOne(nextLast);
        if (size==items.length) {
            resize(size*2);
        }
    }

    /* Remove item at the front of the deque,
    resize the deque if "usage ratio" is less than 0.25 when the length of deque is 16 or more */
    public T removeFirst() {
        nextFirst = plusOne(nextFirst);
        T x = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        if (((double) size/items.length<0.25) && (items.length >= 16)){
            resize((int)(items.length*0.5));
        }
        return x;
    }

    /* Remove item at the end of the deque,
    resize the deque if "usage ratio" is less than 0.25 when the length of deque is 16 or more */
    public T removeLast() {
        nextLast = minusOne(nextLast);
        T x = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        if (((double) size/items.length<0.25) && (items.length >= 16)){
            System.out.print((double)(size/items.length));
            resize((int)(items.length*0.5));
        }
        return x;
    }

    /* Get i-th item from the deque, if item does not exist, return null */
    public T get(int index){
        if (size==0) {
            return null;
        }
        return items[(index + plusOne(nextFirst)) % items.length];
    }

    /* Print every item in the deque, print out a new line when finished */
    public void printDeque() {
        int i = 0;
        while (i < size){
            System.out.print(get(i));
            System.out.print(" ");
            i += 1;
        }
        System.out.println("\n");
    }
}
