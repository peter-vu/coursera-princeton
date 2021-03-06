/* *****************************************************************************
 *  Name:              Peter Vu
 *  Coursera User ID:  vututuananh.vn.sg@gmail.com
 *  Last modified:     9/1/2020
 * Solved with best timing + best memory
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // private static final byte IS_CLOSED = (byte) 0;
    private static final byte IS_OPEN = (byte) 1;
    private static final byte IS_TOP = (byte) 2;
    private static final byte IS_BOTTOM = (byte) 4;
    private static final byte IS_PERCOLATE = (byte) 7;

    private byte[] states;
    private final int n;
    private int noOpenSites;
    private final WeightedQuickUnionUF uf;
    private boolean isPercolated;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        assertValidArgument(n > 0);
        this.n = n;
        this.states = new byte[n * n];
        this.noOpenSites = 0;
        this.uf = new WeightedQuickUnionUF(n * n);
        isPercolated = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        assertValidArgument(isValid(row, col));
        int p = getIndex(row, col);
        if ((states[p] & IS_OPEN) != IS_OPEN) {
            states[p] = (byte) (states[p] | IS_OPEN);
            noOpenSites++;

            int root = uf.find(p);
            if (row == 1) {
                states[root] = (byte) (states[root] | IS_TOP);
            }
            if (row == n) {
                states[root] = (byte) (states[root] | IS_BOTTOM);
            }
        }

        unionTop(row, col);
        unionBottom(row, col);
        unionLeft(row, col);
        unionRight(row, col);

        checkPercolates(p);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        assertValidArgument(isValid(row, col));
        return (states[getIndex(row, col)] & IS_OPEN) == IS_OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        assertValidArgument(isValid(row, col));
        int root = uf.find(getIndex(row, col));
        return (states[root] & IS_TOP) == IS_TOP;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return noOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolated;
    }

    private boolean isAvailable(int row, int col) {
        // assertValidArgument(isValid(row, col));
        if (isValid(row, col)) {
            return (states[getIndex(row, col)] & IS_OPEN) == IS_OPEN;
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
        int root1 = uf.find(p1);
        int root2 = uf.find(p2);
        states[root1] = (byte) (states[root1] | states[root2]);
        states[root2] = states[root1];
        uf.union(p1, p2);
        // additional check
        int root = uf.find(p1);
        states[root] = (byte) (states[root] | states[root1] | states[root2]);
        checkPercolates(root);
    }

    private void checkPercolates(int p) {
        int root = uf.find(p);
        if ((states[root] & IS_PERCOLATE) == IS_PERCOLATE) {
            isPercolated = true;
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 3;
        int[][] array = { { 1, 1 }, { 2, 1 }, { 3, 1 } };
        Percolation perc = new Percolation(n);
        // System.out.println("isOpen(1,5): " + perc.isOpen(1, 5));
        for (int[] input : array) {
            int row = input[0];
            int col = input[1];
            perc.open(row, col);
            System.out.println("isFull: " + "[" + row + "," + col + "]: " + perc.isFull(row, col));
        }
        System.out.println("isOpen(1,2): " + perc.isOpen(1, 2));
        System.out.println("noOpenSites: " + perc.numberOfOpenSites());
        System.out.println("percolate: " + perc.percolates());
        System.out.println("isFull: " + perc.isFull(1, 1));
    }
}
