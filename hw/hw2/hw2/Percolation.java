package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.awt.desktop.SystemSleepEvent;

public class Percolation {

    private int size;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufWithoutBottom;
    private boolean[][] grid;
    private int VirtualTopSite;
    private int VirtualBottomSite;

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
        grid = new boolean[N][N];
        size = 0;
    }

    /**
     * helper function to compute desired index in Union-Find
     * @param i
     * @param j
     * @return desired index
     */
    private int index(int i, int j) {
        return i * grid.length + j + 1;
    }

    /**
     * open the site (row, col) if it is not open already
     * meanwhile, connect with its open neighbors
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            if (row - 1 >= 0 && grid[row - 1][col]) {
                uf.union(index(row, col), index(row - 1, col));
                ufWithoutBottom.union(index(row, col), index(row - 1, col));
            }
            if (row + 1 < grid.length && grid[row + 1][col]) {
                uf.union(index(row, col), index(row + 1, col));
                ufWithoutBottom.union(index(row, col), index(row + 1, col));
            }
            if (col - 1 >= 0 && grid[row][col - 1]) {
                uf.union(index(row, col), index(row, col - 1));
                ufWithoutBottom.union(index(row, col), index(row, col - 1));
            }
            if (col + 1 < grid.length && grid[row][col + 1]) {
                uf.union(index(row, col), index(row, col + 1));
                ufWithoutBottom.union(index(row, col), index(row, col + 1));
            }
            grid[row][col] = true;
            size += 1;
        }
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
        validate(row, col);
        return grid[row][col];
    }

    /**
     * is the site (row, col) full?
     * @param row
     * @param col
     * @return true if the site is connected to top-row sites, false otherwise
     */
    public boolean isFull(int row, int col) {
        validate(row, col);
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

    private void validate(int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
            throw new IndexOutOfBoundsException();
        }
    }
}
