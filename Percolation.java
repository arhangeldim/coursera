
public class Percolation {

    private int N;
    private WeightedQuickUnionUF uf;
    private boolean[] isopen;

    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        this.N = N;
        // Two additiional sites for top and bottom
        uf = new WeightedQuickUnionUF(N * N + 2);
        isopen = new boolean[N * N];
        for (int i = 0; i < N * N; i++) {
            isopen[i] = false;
        }
    }

    public void open(int i, int j) {
        validate(i, j);
        isopen[xyTo1D(i, j)] = true;
        if (i > 1) {
            if (isOpen(i - 1, j)) {
                uf.union(xyTo1D(i, j), xyTo1D(i - 1, j));
            }
        }
        if (i < N) {
            if (isOpen(i + 1, j)) {
                uf.union(xyTo1D(i, j), xyTo1D(i + 1, j));
            }
        }
        if (j > 1) {
            if (isOpen(i, j - 1)) {
                uf.union(xyTo1D(i, j), xyTo1D(i, j - 1));
            }
        }
        if (j < N) {
            if (isOpen(i, j + 1)) {
                uf.union(xyTo1D(i, j), xyTo1D(i, j + 1));
            }
        }
        // link to the top site
        if (i == 1) {
            uf.union(N * N, xyTo1D(i, j));
        }
//        if (i == N) {
//            uf.union(N * N + 1, xyTo1D(i, j));
//        }
    }

    public boolean isOpen(int i, int j) {
        validate(i, j);
        return isopen[xyTo1D(i, j)];
    }

    public boolean isFull(int i, int j) {
        validate(i, j);
        return uf.connected(N * N, xyTo1D(i, j));
    }

    public boolean percolates() {
    //    return uf.connected(N * N, N * N + 1);

        for (int i = N * N - 1; i >= N * N - N; i--) {
            if (isopen[i] && uf.connected(N * N, i)) return true;
        }
        return false;
    }

    private int xyTo1D(int x, int y) {
        validate(x, y);
        int idx = (x - 1) * N + (y - 1);
        return idx;
    }

    private void validate(int i, int j) {
        if (i <= 0 || i > N || j <= 0 || j > N)
            throw new IndexOutOfBoundsException();
    }


}
