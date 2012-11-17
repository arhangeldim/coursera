
public class PercolationStats {

    private int N;
    private int T;
    private double[] x;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("Incorrect arguments");
        this.N = N;
        this.T = T;
        x = new double[T];
    }

    public double mean() {
        Percolation p;
        for (int i = 0; i < T; i++) {
            p = new Percolation(N);
            x[i] = doPercolate(p);
        }
        return StdStats.mean(x);
    }

    public double stddev() {
        Percolation p;
        for (int i = 0; i < T; i++) {
            p = new Percolation(N);
            x[i] = doPercolate(p);
        }
        return StdStats.stddev(x);
    }

    private double doPercolate(Percolation p) {
        int opened = 0;
        while (!p.percolates()) {
            int i = StdRandom.uniform(N) + 1;
            int j = StdRandom.uniform(N) + 1;
            if (!p.isOpen(i, j)) {
                p.open(i, j);
                opened++;
            }
        }
        return (double) opened / ((double) N * N);
    }


    public static void main(String[] args) {
        if (args.length != 2)
            throw new IllegalArgumentException();
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        double m = stats.mean();
        double dev = stats.stddev();
        double k = 1.96 * dev / Math.sqrt(T);
        StdOut.println("mean\t\t\t= " + m);
        StdOut.println("stddev\t\t\t= " + dev);
        StdOut.println("95% confidence interval\t= " + (m - k) + ", " + (m + k));
    }
}
