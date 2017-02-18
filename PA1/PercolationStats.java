import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
// import java.lang.Math;

public class PercolationStats {
    private final double[] stats;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException();
        stats = new double[trials];

        for (int i = 0; i < trials; i++) {
            stats[i] = test(n);
        }
    }

    private double test(int gridSize) {
        Percolation p = new Percolation(gridSize);
        int openSites = 0;
        while (!p.percolates()) {
            int row = StdRandom.uniform(1, gridSize+1);
            int col = StdRandom.uniform(1, gridSize+1);
            if (!p.isOpen(row, col)) {
                openSites++;
                p.open(row, col);
            }
        }
        return (openSites * 1.0) / (gridSize*gridSize);
    }


    public double mean() {
        return StdStats.mean(stats);
    }

    public double stddev() {
        return StdStats.stddev(stats);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(stats.length));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(stats.length));
    }

    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(size, trials);
        StdOut.printf("mean\t\t= %f\n", ps.mean());
        StdOut.printf("stddev\t\t= %f\n", ps.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", ps.confidenceLo(), ps.confidenceHi());
    }
}