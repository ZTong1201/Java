public class LinkedListDeque2<T> {

    private class DoublyNode {
        public T item;
        public DoublyNode prev;
        public DoublyNode next;

        public DoublyNode(DoublyNode p, T i, DoublyNode n){
            item = i;
            prev = p;
            next = n;
        }
    }

    private int size = 0;
    private DoublyNode sentinel = new DoublyNode(null,null,null);;

    public LinkedListDeque2() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque2(LinkedListDeque2 other) {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

        for (int i=0; i<other.size(); i++){
            addFirst((T) other.get(i));
        }
    }

    public void addFirst(T i) {
        size += 1;
        DoublyNode newNode = new DoublyNode(null,i,sentinel.next);
        sentinel.next = newNode;
        newNode.prev = sentinel;
        sentinel.prev = newNode;
    }

    public void addLast(T i) {
        size += 1;
        DoublyNode newNode = new DoublyNode(sentinel.prev,i,null);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        newNode.next = sentinel;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public void printDeque() {
        DoublyNode currentNode = sentinel.next;
        while (currentNode != sentinel) {
            System.out.print(currentNode.item + " ");
            currentNode = currentNode.next;
        }
        System.out.println("\n");
    }

    public T removeFirst() {
        DoublyNode currentFirst = sentinel.next;
        if(currentFirst==sentinel){
            return null;
        }
        size -= 1;
        sentinel.next = currentFirst.next;
        currentFirst.next.prev = sentinel;
        return currentFirst.item;
    }

    public T removeLast() {
        DoublyNode currentLast = sentinel.prev;
        if (currentLast==sentinel) {
            return null;
        }
        size -= 1;
        sentinel.prev = currentLast.prev;
        currentLast.prev.next = sentinel;
        return currentLast.item;
    }

    public T get(int index) {
        if (index+1>size) {
            return null;
        }
        DoublyNode ptr = sentinel.next;
        int i = 0;
        while (i < index){
            ptr = ptr.next;
            i += 1;
        }
        return ptr.item;
    }

    public DoublyNode getRecursive(DoublyNode currentNode, int index) {
        if (index==0){
            return currentNode;
        }
        return getRecursive(currentNode.next, index - 1);
    }

    public T getRecursive(int index) {
        DoublyNode node = getRecursive(sentinel.next, index);
        if (node!=sentinel) {
            return node.item;
        }
        return null;

    }
}
