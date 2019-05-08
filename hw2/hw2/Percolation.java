package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.awt.desktop.SystemSleepEvent;

public class Percolation {

    private int size;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufWithoutBottom;
    private String[][] grid;
    private int VirtualTopSite;
    private int VirtualBottomSite;
    private int[] dr;
    private int[] dc;

    /**
     * constructor of percolation system
     * create N-by-N grid, with all sites initially blocked
     * @param N if N <= 0, throw an IllegalArgumentException
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Grid size cannot be less than 0!");
        }
        uf = new WeightedQuickUnionUF(N*N+2);
        ufWithoutBottom = new WeightedQuickUnionUF(N*N+1);
        VirtualTopSite = 0;
        VirtualBottomSite = N*N + 1;
        grid = new String[N][N];
        for (int  i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = "blocked";
            }
        }
        size = 0;
        dr = new int[]{0, 0, -1, 1};
        dc = new int[]{1, -1, 0, 0};
    }

    /**
     * helper function to compute desired index in Union-Find
     * @param i
     * @param j
     * @return desired index
     */
    private int index (int i, int j) {
        return i * grid.length + j + 1;
    }

    /**
     * open the site (row, col) if it is not open already
     * meanwhile, connect with its open neighbors
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        if (row < 0 || row > grid.length) {
            throw new IndexOutOfBoundsException("Index" + row + "is out of bound between 0" + (grid.length - 1));
        }
        if (col < 0 || col > grid.length) {
            throw new IndexOutOfBoundsException("Index" + col + "is out of bound between 0" + (grid.length - 1));
        }
        int pos = index(row, col);
        if (isOpen(row, col)) {
            return;
        }
        for (int k = 0; k < 4; k++) {
            int newX = dr[k] + row;
            int newY = dc[k] + col;
            if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid.length) {
                if (grid[newX][newY] == "open") {
                    uf.union(index(row, col), index(newX, newY));
                    ufWithoutBottom.union(index(row, col), index(newX, newY));
                }
            }
        }
        grid[row][col] = "open";
        size += 1;
        if (row == 0) {
            uf.union(VirtualTopSite, index(row, col));
            ufWithoutBottom.union(VirtualTopSite, index(row, col));
        }
        if (row == grid.length - 1) {
            uf.union(VirtualBottomSite, index(row, col));
        }
    }

    /**
     * is the site (row, col) open?
     * @param row
     * @param col
     * @return true if the site is already open, false otherwise
     */
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > grid.length) {
            throw new IndexOutOfBoundsException("Index" + row + "is out of bound between 0" + (grid.length - 1));
        }
        if (col < 0 || col > grid.length) {
            throw new IndexOutOfBoundsException("Index" + col + "is out of bound between 0" + (grid.length - 1));
        }
        return grid[row][col] == "open";
    }

    /**
     * is the site (row, col) full?
     * @param row
     * @param col
     * @return true if the site is connected to top-row sites, false otherwise
     */
    public boolean isFull(int row, int col) {
        if (row < 0 || row > grid.length) {
            throw new IndexOutOfBoundsException("Index" + row + "is out of bound between 0" + (grid.length - 1));
        }
        if (col < 0 || col > grid.length) {
            throw new IndexOutOfBoundsException("Index" + col + "is out of bound between 0" + (grid.length - 1));
        }
        return uf.connected(VirtualTopSite, index(row, col)) && ufWithoutBottom.connected(VirtualTopSite, index(row, col));
    }

    /**
     * does the system percolate?
     * @return true if the system percolates from the top to the bottom, false otherwise
     */
    public boolean percolates() {
        return uf.connected(VirtualTopSite, VirtualBottomSite);
    }

    /**
     * number of open sites
     * @return the number of current open sites
     */
    public int numberOfOpenSites() {
        return size;
    }

    public static void main(String[] args) {
        Percolation system = new Percolation(20);
        system.open(1,3);
        system.isOpen(1, 3);
    }
}
