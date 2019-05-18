import edu.princeton.cs.algs4.SymbolDigraph;

import java.security.Key;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class BSTMap<K extends Comparable<K>, V extends Comparable<V>> implements Map61B<K, V> {
    int size = 0;
    Set<K> KeySet = new HashSet<>();

    @Override
    public void clear() {
        size = 0;
        BSTree = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(K k) {
        if (BSTree == null) {
            return null;
        }
        BST lookup = BSTree.get(k);
        if (lookup == null) {
            return null;
        }
        return lookup.val;
    }

    @Override
    public void put(K key, V val) {
        if (BSTree != null) {
            BST lookup = BSTree.get(key);
            if (lookup == null) {
                BSTree.insert(BSTree, key, val);
                size += 1;
            } else {
                BSTree.val = val;
            }
        } else {
            BSTree = new BST(key, val, null, null);
            size += 1;
        }
    }

    @Override
    public boolean containsKey(K k) {
        if (BSTree == null) {
            return false;
        }
        return BSTree.get(k) != null;
    }

    private void inOrder(BST T) {
        if (T == null) {
            return;
        }
        inOrder(T.left);
        System.out.println(T.key);
        inOrder(T.right);
    }

    public void printInOrder() {
        inOrder(BSTree);
    }


    private class BST{
        K key;
        V val;
        BST left;
        BST right;



        public BST(K k, V v, BST l, BST r) {
            key = k;
            val = v;
            left = l;
            right = r;
        }

        public BST get(K k) {
            int cmp = k.compareTo(key);
            if (k != null && cmp == 0) {
                return this;
            }
            if (cmp < 0) {
                if (left == null) {
                    return null;
                }
                return left.get(k);
            } else {
                if (right == null) {
                    return null;
                }
                return right.get(k);
            }
        }

        public BST insert(BST T, K k, V v) {
            if (T == null) {
                return new BST(k, v, T, T);
            }
            if (k.compareTo(T.key) < 0) {
                T.left = insert(T.left, k, v);
            } else {
                T.right = insert(T.right, k , v);
            }
            return T;
        }

        private BST swapSmallest(BST T, BST R) {
            if (T.left == null) {
                R.key = T.key;
                return T.right;
            } else {
                T.left = swapSmallest(T.left, R);
                return T;
            }
        }

        public BST remove(BST T, K k) {
            if (T == null) {
                return null;
            }
            if (k.compareTo(T.key) < 0) {
                T.left = remove(T.left, k);
            } else if (k.compareTo(T.key) > 0) {
                T.right = remove(T.right, k);
            } else if (T.left == null) {
                return T.right;
            } else if (T.right == null) {
                return T.left;
            } else {
                T.right = swapSmallest(T.right, T);
            }
            return T;
        }
    }

    private BST BSTree;

    @Override
    public V remove(K key) {
        V returnValue = get(key);
        BSTree = BSTree.remove(BSTree, key);
        size -= 1;
        return returnValue;
    }

    @Override
    public V remove(K key, V value) {
        V returnValue = get(key);
        if (returnValue.compareTo(value) == 0) {
            remove(key);
            return returnValue;
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        preOrder(BSTree);
        return KeySet;
    }


    private Set<K> preOrder(BST T) {
        if (T == null) {
            return null;
        }
        //K returnValue = T.key;
        KeySet.add(T.key);
        preOrder(T.left);
        preOrder(T.right);
        return KeySet;
    }



    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {

        int i = 0;
        @Override
        public boolean hasNext() {
            return i < KeySet.size();
        }

        @Override
        public K next() {
            K[] keyArray = (K[]) KeySet.toArray();
            K returnKey = keyArray[i];
            i += 1;
            return returnKey;
        }
    }
}
