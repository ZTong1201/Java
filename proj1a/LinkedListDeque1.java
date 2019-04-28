public class LinkedListDeque1<T> {

    /* Implement a generic type deque use linked list with double sentinels in both front and back. */

    /* Doubly linked list class */
    private class DoublyNode {
        public T item;
        public DoublyNode next;
        public DoublyNode prev;

        public DoublyNode(DoublyNode p, T x, DoublyNode n){
            prev = p;
            item = x;
            next = n;
        }
    }

    private int size;
    private DoublyNode sentFront;
    private DoublyNode sentBack;

    /* Create an empty linked list deque. */
    public LinkedListDeque1() {
        size = 0;
        sentFront = new DoublyNode(null,null,null);
        sentBack = new DoublyNode(null,null,null);
        sentFront.next = sentBack;
        sentBack.prev = sentFront;
    }

    /* Deep copy of another linked list deque. */
    public LinkedListDeque1(LinkedListDeque1 other){
        size = 0;
        sentFront = new DoublyNode(null,null,null);
        sentBack = new DoublyNode(null,null,null);
        sentFront.next = sentBack;
        sentBack.prev = sentFront;

        for (int i=0; i<other.size();i++){
            addFirst((T) other.get(i));
        }
    }

    /* Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /* Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size==0;
    }

    /* Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        size += 1;
        DoublyNode currentNext = sentFront.next;
        DoublyNode newNode = new DoublyNode(sentFront, item, currentNext);
        sentFront.next = newNode;
        currentNext.prev = newNode;
    }

    /* Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        size += 1;
        DoublyNode currentPrev = sentBack.prev;
        DoublyNode newNode = new DoublyNode(currentPrev, item, sentBack);
        sentBack.prev = newNode;
        currentPrev.next = newNode;
    }

    /* Prints the items in the deque from first to last, separated by a space.
    Once all the items have been printed, print out a new line. */
    public void printDeque() {
        DoublyNode ptr = sentFront.next;
        while (ptr != sentBack){
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
        System.out.println('\n');
    }

    /* Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst() {
        if (size==0){
            return null;
        }
        size -= 1;
        DoublyNode currentNext = sentFront.next;
        sentFront.next = currentNext.next;
        return currentNext.item;
    }

    /* Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast() {
        if (size==0){
            return null;
        }
        size -= 1;
        DoublyNode currentPrev = sentBack.prev;
        sentBack.prev = currentPrev.prev;
        return currentPrev.item;
    }

    /* Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    If no such item exists, returns null. */
    public T get(int index) {
        if (index+1>size) {
            return null;
        }
        DoublyNode ptr = sentFront.next;
        int i = 0;
        while (i < index) {
            i += 1;
            ptr = ptr.next;
        }
        return ptr.item;
    }

    private DoublyNode getNode(DoublyNode current, int index){
        if (index==0){
            return current.next;
        }
        current = getNode(current.next, index-1);
        return current;
    }

    /* Same as get, but uses recursion/ */
    public T getRecursive(int index){
        if (index+1>size){
            return null;
        }
        return getNode(sentFront, index).item;


    }

}