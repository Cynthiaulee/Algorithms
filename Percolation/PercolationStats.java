/******************************************************************************
 *  Name:    Wenyin San
 *  NetID:   N/A
 *  Precept: N/A
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Model an n-by-n percolation system using the union-find
 *                data structure.
 ******************************************************************************/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
//    private int n; 
    private final double CONFIDENCE_95;
    private double mean;
    private double stddev;
    private final int trials;  
    private final double[] count;
    
    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) {
            // check the arguments
            throw new IllegalArgumentException("n and trials"
                                     +"should be postive integers"); 
        }
//       this.n = n;
        CONFIDENCE_95 = 1.96;
        mean = 0;
        stddev = 0;
        this.trials = trials;
        count = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation PS = new Percolation(n);
            while (!PS.percolates()) {
                 int rowRand = StdRandom.uniform(1, n+1);
                 int colRand = StdRandom.uniform(1, n+1);
                 if (!PS.isOpen(rowRand, colRand)) {
                     PS.open(rowRand, colRand);
                 }
            }
            count[i] = PS.numberOfOpenSites()*1.0 / (n*n*1.0);
        }     
    }
    public double mean() {
        // sample mean of percolation threshold
        mean = StdStats.mean(count);
        return mean;
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        stddev = StdStats.stddev(count);
        return stddev;
    }
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        if (mean == 0) {
            mean = mean();
        }
        if (stddev == 0) {
            stddev = stddev();
        }
        double confidenceLo = mean - CONFIDENCE_95*stddev/Math.sqrt(trials*1.0);
        return confidenceLo;
    }
    public double confidenceHi() {
        if (mean == 0) {
            mean = mean();
        }
        if (stddev == 0) {
            stddev = stddev();
        }
        // high endpoint of 95% confidence interval
        double confidenceHi = mean + CONFIDENCE_95*stddev/Math.sqrt(trials*1.0);
        return confidenceHi;
    }
    public static void main(String[] args) {
        // test client (described below)
        int n = 2;
        int trials = 10000;
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("n               = " + n);
        System.out.println("trials          = " + trials);
        System.out.println("mean                 = " + stats.mean());
        System.out.println("stdev                = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo()
                               + ", " + stats.confidenceHi()+"]");
    }
}


