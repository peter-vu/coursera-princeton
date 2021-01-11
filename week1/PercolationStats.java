/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_INTERVAL = 1.96;
    // private int n;
    private final int trials;
    private final double[] results;
    // perform independent trials on an n-by-n grid


    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid argument");
        }
        // this.n = n;
        this.trials = trials;
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            // System.out.println("Start trials " + i);
            Percolation perc = new Percolation(n);
            boolean isPerculates = false;
            boolean isStopped = false;
            while (!isPerculates) {
                while (!isStopped) {
                    int row = StdRandom.uniform(1, n + 1);
                    int col = StdRandom.uniform(1, n + 1);
                    // System.out.println(row + ", " + col);
                    if (!perc.isOpen(row, col)) {
                        perc.open(row, col);
                        isStopped = true;
                    }
                }
                isStopped = false;
                isPerculates = perc.percolates();
            }
            results[i] = (double) perc.numberOfOpenSites() / n / n;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double sd = stddev();
        return mean - CONFIDENCE_INTERVAL * sd / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double sd = stddev();
        return mean + CONFIDENCE_INTERVAL * sd / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid argument");
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + "[" + stats.confidenceLo()
                                   + "," + stats.confidenceHi() + "]");
    }
}
