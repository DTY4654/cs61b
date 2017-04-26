package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.algs4.StdStats;




public class PercolationStats {

    private double [] threshold;
    private int numOfExperiment;


    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if( N <= 0 || T <= 0){
            throw new java.lang.IllegalArgumentException();
        }
        Percolation p = new Percolation(N);
        this.threshold = new double [T];
        this.numOfExperiment = T;
        for(int i = 0; i < T; i++){
            //uniform(int n) returns a random integer uniformly in [0, n).
            while(!p.percolates()){
                p.open(StdRandom.uniform(N),StdRandom.uniform(N));
            }
            this.threshold[i] = (double) p.numberOfOpenSites() / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * Math.sqrt(stddev()) / Math.sqrt(numOfExperiment);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * Math.sqrt(stddev()) / Math.sqrt(numOfExperiment);
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(20,30);
        for(int i = 0; i < ps.threshold.length; i++){
            System.out.println(ps.threshold[i]);
        }
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLow());
        System.out.println(ps.confidenceHigh());
    }
}                       
