package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private final double[] res;
    private final double confidenceLevel;
    private final double mu;
    private final double sigma;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("You grid size or simulation time can be less than or equal to 0!");
        }
        confidenceLevel = 1.96;
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
        mu = StdStats.mean(res);
        sigma = StdStats.stddev(res);

    }
    // sample mean of percolation threshold
    public double mean() {
        return mu;
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return sigma;
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mu - 1.96 * sigma / Math.sqrt(res.length);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh(){
        return mu + 1.96 * sigma / Math.sqrt(res.length);
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats system = new PercolationStats(200,1000, pf);
        System.out.println("The mean probability is: "+ system.mean());
        System.out.println("The standard deviation is: "+ system.stddev());
        System.out.println("The confidence interval is : ( " + system.confidenceLow() + " , " + system.confidenceHigh() + " )");
    }

}
