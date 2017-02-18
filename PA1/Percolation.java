import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private final WeightedQuickUnionUF w1, w2;
    private final boolean[] grid;

    public Percolation(int n) {
        if (n <= 0) throw new java.lang.IllegalArgumentException();
        this.size = n;
        grid = new boolean[n*n];
        w1 = new WeightedQuickUnionUF(n*n+2);
        w2 = new WeightedQuickUnionUF(n*n+1);

        for (int i = 0; i < n * n; i++) {
            grid[i] = false;
        }

        for (int i = 0; i < n; i++) {
            w1.union(i, n*n);
            w1.union(n*n-n+i, n*n+1);
            w2.union(i, n*n);
        }
    }


    private boolean validatePos(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size) return false;
        return true;
    }

    private void union(int i, int j, int k, int m) {
        if (validatePos(k, m) && isOpen(k+1, m+1)) {
            int p = i * size + j;
            int q = k * size + m;
            w1.union(p, q);
            w2.union(p, q);
        }
    }
    
    public void open(int i0, int j0) {
        // convesion
        int i = i0 - 1;
        int j = j0 - 1;
       if (!validatePos(i, j)) throw new java.lang.IndexOutOfBoundsException();
        grid[i*size+j] = true;
        // union process
        union(i, j, i-1, j);
        union(i, j, i+1, j);
        union(i, j, i, j-1);
        union(i, j, i, j+1);
    }

    public boolean isOpen(int i0, int j0) {
        int i = i0 - 1;
        int j = j0 - 1;
        if (!validatePos(i, j)) throw new java.lang.IndexOutOfBoundsException();
        return grid[i*size+j];
    }

    public boolean isFull(int i, int j) {
        return isOpen(i, j) && w2.connected((i-1)*size+j-1, size*size);
    }
    
    public boolean percolates() {
        if (size == 1) return isOpen(1, 1);
        return w1.connected(size*size, size*size+1);
    }

    public static void main(String[] args) {

    }
}
