public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = items.length/2;
        nextLast = items.length/2+1;
        size = 0;
    }

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

    public ArrayDeque(ArrayDeque other){
        items = (T[]) new Object[8];
        nextFirst = items.length/2;
        nextLast = items.length/2+1;
        size = 0;

        for (int i=0; i<other.size(); i++){
            addFirst((T) other.get(i));
        }
    }

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


    public T get(int index){
        if (index+1>size) {
            return null;
        }
        if (nextFirst+1+index>items.length-1){
            return items[nextFirst+1+index-items.length];
        }
        return items[nextFirst+1+index];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public void printDeque() {
        int i = 0;
        while (this.get(i)!=null) {
            System.out.print(this.get(i));
            System.out.println(" ");
            i += 1;
        }
        System.out.println("\n");
    }


}
