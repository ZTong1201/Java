import java.util.Iterator;

public class UnionFind {

    private int[] Items;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        Items = new int[n];
        for (int i = 0; i < Items.length; i++) {
            Items[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0 || vertex > Items.length - 1) {
            throw new IllegalArgumentException(String.format("%d is not a valid index!", vertex));
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        while (parent(v1) >= 0) {
            v1 = Items[v1];
        }
        return (-1)*Items[v1];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return Items[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        int root1 = find(v1);
        int root2 = find(v2);
        int size1 = sizeOf(v1);
        int size2 = sizeOf(v2);
        if (root1 == root2) {
            Items[v1] = root1;
            Items[v2] = Items[v1];
        } else if (size1 <= size2) {
            Items[root2] -= size1;
            Items[v1] = root2;
            Items[root1] = root2;
            if (Items[v2] >= 0){
                Items[v2] = Items[v1];
            }
        } else {
            Items[root1] -= size2;
            Items[v2] = root1;
            Items[root2] = root1;
            if (Items[v1] >= 0){
                Items[v1] = Items[v2];
            }
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        while (parent(vertex) >= 0) {
            vertex = parent(vertex);
            if (parent(vertex) < 0) {
                return vertex;
            }
        }
        return vertex;
    }
}
