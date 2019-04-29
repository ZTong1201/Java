public class ArrayDeque1<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /* Create and empty array-based deque. */
    public ArrayDeque1() {
        items = (T[]) new Object[8];
        nextFirst = items.length/2;
        nextLast = items.length/2+1;
        size = 0;
    }

    /* Deep-copy of another deque */
    public ArrayDeque1(ArrayDeque1 other){
        items = (T[]) new Object[8];
        nextFirst = items.length/2;
        nextLast = items.length/2+1;
        size = 0;

        for (int i=0; i<other.size(); i++){
            addFirst((T) other.get(i));
        }
    }


    /* Resize the deque when necessary */
    public void resize(boolean enlarge) {
        if (enlarge) {
            T[] a = (T[]) new Object[size*2];
            if (nextFirst<=nextLast){
                System.arraycopy(items,nextFirst+1,a,nextFirst+1,nextLast-nextFirst-1);
                items = a;
            }
            else{
                System.arraycopy(items,0,a,0,nextLast);
                System.arraycopy(items,nextFirst+1,a,a.length-items.length+nextFirst+1,items.length-nextFirst-1);
                nextFirst = a.length-items.length+nextFirst;
                items = a;
            }
        }
        else {
            T[] a = (T[]) new Object[(int)(items.length*0.5)];
            if (nextFirst<=nextLast){
                System.arraycopy(items,nextFirst+1,a,nextFirst+1,nextLast-nextFirst-1);
                items = a;
            }
            else{
                System.arraycopy(items,0,a,0,nextLast);
                System.arraycopy(items,nextFirst+1,a,a.length-items.length+nextFirst+1,items.length-nextFirst-1);
                nextFirst = a.length-items.length+nextFirst;
                items = a;
            }
        }
    }

    /* Add item at the front of the deque */
    public void addFirst(T i) {
        items[nextFirst] = i;
        nextFirst -= 1;
        size += 1;
        if ((double) size/items.length >= 0.75){
            resize(true);
        }
        if ((size>=16) && ((double) size/items.length < 0.25)){
            resize(false);
        }
        if ((nextFirst<0) && (nextLast != items.length-1)){
            nextFirst = items.length - 1;
        }
    }

    /* Add item at the end of the deque */
    public void addLast(T i) {
        items[nextLast] = i;
        nextLast += 1;
        size += 1;
        if ((double) size/items.length >= 0.75){
            resize(true);
        }
        if ((nextLast>items.length-1) && (nextFirst!=0)) {
            nextLast = 0;
        }
    }

    /* Remove item at the front of the deque */
    public T removeFirst() {
        if ((items.length>=16) && ((double) size/items.length < 0.25)){
            resize(false);
        }
        if (nextFirst+1>items.length-1) {
            T x = items[0];
            items[0] = null;
            nextFirst = 0;
            size -= 1;
            return x;
        }
        T x = items[nextFirst+1];
        items[nextFirst+1] = null;
        nextFirst += 1;
        size -= 1;
        return x;
    }

    /* Remove item at the end of the deque */
    public T removeLast() {
        if ((items.length>=16) && ((double) size/items.length < 0.25)){
            resize(false);
        }
        if (nextLast-1<0) {
            T x = items[items.length-1];
            items[items.length-1] = null;
            nextLast = items.length - 1;
            size -= 1;
            return x;
        }
        T x = items[nextLast - 1];
        size -= 1;
        items[nextLast - 1] = null;
        nextLast -= 1;
        return x;
    }


    /* Get i-th item from the deque, if item does not exist, return null */
    public T get(int index){
        if (index+1>size) {
            return null;
        }
        if (nextFirst+1+index>items.length-1){
            return items[nextFirst+1+index-items.length];
        }
        return items[nextFirst+1+index];
    }

    /* Return the size of the deque */
    public int size() {
        return size;
    }

    /* Return true if the deque is empty, false otherwise */
    public boolean isEmpty() {
        return size==0;
    }

    /* Print every item in the deque, print out a new line when finished */
    public void printDeque() {
        int i = 0;
        while (this.get(i)!=null) {
            System.out.print(this.get(i));
            System.out.print(" ");
            i += 1;
        }
        System.out.println("\n");
    }


}
