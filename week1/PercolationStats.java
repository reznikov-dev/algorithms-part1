import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private static final double PERCENTILE = 1.96;
    private static final String FORMAT = "mean\t\t\t\t\t= %f \nstddev\t\t\t\t\t= %f \n95%% confidence interval = [%f, %f]\n";

    private final double[] trialsThreshold;
    private double mean;
    private double stddev;

    /**
     * Constructor
     * @param n int - grid row and col number (grid is n * n)
     * @param trials int - independent experiments numbers
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid n or trials argument");
        }

        trialsThreshold = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }

            trialsThreshold[i] = p.numberOfOpenSites() * 1.0 / (n * n);
        }
    }

    /**
     * Returns mean of percolation threshold
     * @return double
     */
    public double mean() {
        if (mean == 0.0) {
            mean = StdStats.mean(trialsThreshold);
        }

        return mean;
    }

    /**
     * Returns the sample standard deviation
     * @return double
     */
    public double stddev() {
        if (stddev == 0.0) {
            stddev = StdStats.stddev(trialsThreshold);
        }

        return stddev;
    }

    /**
     * Returns low endpoint of 95% confidence interval
     * @return double
     */
    public double confidenceLo() {
        return mean() - percentileStdDev();
    }

    /**
     * Returns high endpoint of 95% confidence interval
     * @return double
     */
    public double confidenceHi() {
        return mean() + percentileStdDev();
    }

    /**
     * Returns stddev 95% confidence
     * @return double
     */
    private double percentileStdDev() {
        return PERCENTILE * stddev() / Math.sqrt(trialsThreshold.length);
    }   

    /**
     * @param args Strings[]
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);

        StdOut.printf(FORMAT,  ps.mean(), ps.stddev(), ps.confidenceLo(), ps.confidenceHi());
    }
}