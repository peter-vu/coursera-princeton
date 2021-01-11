/* *****************************************************************************
 *  Name:              Peter Vu
 *  Coursera User ID:  123
 *  Last modified:     9/1/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] isOpen;
    private final int n, source, sink;
    private int noOpenSites;
    private final WeightedQuickUnionUF uf, topFillUf;
    // private final int[][] direction = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        assertValidArgument(n > 0);
        this.n = n;
        this.isOpen = new boolean[n * n + 2];
        this.noOpenSites = 0;
        this.source = n * n;
        this.sink = n * n + 1;
        this.isOpen[source] = true;
        this.isOpen[sink] = true;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.topFillUf = new WeightedQuickUnionUF(n * n + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        assertValidArgument(isValid(row, col));
        int p1 = getIndex(row, col);
        if (!isOpen[p1]) {
            isOpen[p1] = true;
            noOpenSites++;
            unionTop(row, col);
            unionBottom(row, col);
            unionLeft(row, col);
            unionRight(row, col);

            // check for source and sink union
            if (row == 1) {
                uf.union(source, p1);
                topFillUf.union(source, p1);
            }
            if (row == n) {
                uf.union(p1, sink);
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        assertValidArgument(isValid(row, col));
        return isOpen[getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        assertValidArgument(isValid(row, col));
        int p = getIndex(row, col);
        return topFillUf.find(source) == topFillUf.find(p);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return noOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(source) == uf.find(sink);
    }

    private boolean isAvailable(int row, int col) {
        // assertValidArgument(isValid(row, col));
        if (isValid(row, col)) {
            return isOpen[getIndex(row, col)];
        }
        return false;

    }

    private boolean isValid(int row, int col) {
        return 0 < row && row <= n
                && 0 < col && col <= n;
    }

    private void assertValidArgument(boolean valid) {
        if (!valid) {
            throw new IllegalArgumentException("Invalid argument");

        }
    }

    private int getIndex(int row, int col) {
        return n * (row - 1) + (col - 1);
    }

    private void unionTop(int row, int col) {
        if (isAvailable(row - 1, col)) {
            int p1 = getIndex(row, col);
            int p2 = getIndex(row - 1, col);
            union(p1, p2);
        }
    }

    private void unionBottom(int row, int col) {
        if (isAvailable(row + 1, col)) {
            int p1 = getIndex(row, col);
            int p2 = getIndex(row + 1, col);
            union(p1, p2);
        }
    }

    private void unionLeft(int row, int col) {
        if (isAvailable(row, col - 1)) {
            int p1 = getIndex(row, col);
            int p2 = getIndex(row, col - 1);
            union(p1, p2);
        }
    }

    private void unionRight(int row, int col) {
        if (isAvailable(row, col + 1)) {
            int p1 = getIndex(row, col);
            int p2 = getIndex(row, col + 1);
            union(p1, p2);
        }
    }

    private void union(int p1, int p2) {
        uf.union(p1, p2);
        topFillUf.union(p1, p2);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 6;
        int[][] array = { { 1, 2 }, { 2, 2 }, { 2, 2 } };
        Percolation perc = new Percolation(n);
        System.out.println(perc.isOpen(1, 2));
        for (int[] input : array) {
            int row = input[0];
            int col = input[1];
            perc.open(row, col);
        }
        System.out.println(perc.isOpen(1, 2));
        System.out.println("noOpenSites: " + perc.numberOfOpenSites());
        System.out.println(perc.percolates());
        System.out.println(perc.isFull(1, 1));
    }
}
