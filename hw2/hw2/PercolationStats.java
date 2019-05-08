package hw2;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private double[] res;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("You grid size or simulation time can be less than or equal to 0!");
        }
        res = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation system = pf.make(N);
            while (!system.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!system.isOpen(row, col)) {
                    system.open(row, col);
                }
            }
            res[i] = ((double) system.numberOfOpenSites())/ ((double) N*N);
        }

    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(res);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(res);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(res.length);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh(){
        return mean() + 1.96 * stddev() / Math.sqrt(res.length);
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats system = new PercolationStats(20,10000, pf);
        System.out.println("The mean probability is: "+ system.mean());
        System.out.println("The standard deviation is: "+ system.stddev());
        System.out.println("The confidence interval is : ( " + system.confidenceLow() + " , " + system.confidenceHigh() + " )");
    }

}
