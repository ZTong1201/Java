package bearmaps.proj2ab;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> items;
    private int size;
    private HashMap<T, Integer> index;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        items.add(null);
        index = new HashMap<>();
    }

    @Override
    public T getSmallest() {
        if(size == 0) throw new NoSuchElementException();
        return items.get(1).item;
    }

    @Override
    public T removeSmallest() {
        if(size == 0) throw new NoSuchElementException();
        PriorityNode returnNode = items.get(1);
        swap(1, size);
        items.remove(size);
        size -= 1;
        index.remove(returnNode.item);
        sinkDown(1);
        return returnNode.item;
    }

    private void sinkDown(int k) {
        while(leftChild(k) <= size && rightChild(k) <= size) {
            int tmpLeft = items.get(k).compareTo(items.get(leftChild(k)));
            int tmpRight = items.get(k).compareTo(items.get(rightChild(k)));
            if(tmpLeft <= 0 && tmpRight <= 0) break;
            else if (tmpLeft > 0 && tmpRight > 0 && tmpLeft >= tmpRight) {
                swap(k, leftChild(k));
                k = leftChild(k);
            }
            else if(tmpLeft > 0 && tmpRight > 0 && tmpLeft < tmpRight) {
                swap(k, rightChild(k));
                k = rightChild(k);
            } else if(tmpLeft > 0) {
                swap(k, leftChild(k));
                k = leftChild(k);
            } else {
                swap(k, rightChild(k));
                k = rightChild(k);
            }
        }
    }

    @Override
    public void add(T item, double priority) {
        if(contains(item)) throw new IllegalArgumentException();
        items.add(new PriorityNode(item, priority));
        size += 1;
        index.put(item, size);
        swimUp(size);
    }

    private void swimUp(int k) {
        while(parent(k) >= 1) {
            int tmp = items.get(k).compareTo(items.get(parent(k)));
            if(tmp < 0) {
                swap(k, parent(k));
                k = parent(k);
            }  else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        index.put(items.get(i).getItem(), j);
        index.put(items.get(j).getItem(), i);
        PriorityNode tmp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, tmp);
    }

    private int parent(int k) {
        return k / 2;
    }

    private int leftChild(int k) {
        return k * 2;
    }

    private int rightChild(int k) {
        return k * 2 + 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T item) {
        if(!index.containsKey(item)) return false;
        return true;
    }

    @Override
    public void changePriority(T item, double priority) {
        if(!contains(item)) throw new NoSuchElementException();
        int k = index.get(item);
        items.get(k).setPriority(priority);
        swimUp(k);
        sinkDown(k);
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        public PriorityNode(T e, double p) {
            item = e;
            priority = p;
        }

        T getItem() { return this.item; }

        double getPriority() { return this.priority; }

        void setPriority(double p) { this.priority = p; }

        @Override
        public int compareTo(PriorityNode o) {
            if(o == null) {
                return -1;
            }
            return (int) (this.getPriority() - o.getPriority());
        }

        @Override
        public boolean equals(Object o) {
            if(o == this) {
                return true;
            }
            if(o == null) {
                return false;
            }
            if(o.getClass() != this.getClass()) {
                return false;
            }
            else {
                return this.getItem().equals(((PriorityNode) o).getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
