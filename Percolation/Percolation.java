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

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF wqu;
    private final WeightedQuickUnionUF wquCopy;
    private boolean[][] grid;
    private int siteOpen;
    
    private int dimMining(int row, int col) {
        // change a row, column location into an int
        return ((row-1) * n) + col;
    }
    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            // check the value of n
            throw new IllegalArgumentException("n should be a postive integer.");  
        }
        this.n = n;
        wqu = new WeightedQuickUnionUF(n * n + 2);
        wquCopy = new WeightedQuickUnionUF(n * n + 1);
        siteOpen = 0;
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false; // the sites are initialized to blocked
            }
        }
    }
    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        if (row <= 0 || col <= 0 || row > n || col > n) {
            // check the value of row and col
            throw new IllegalArgumentException("Invalid row or col for Open()."); 
        }
        if (!grid[row-1][col-1]) {
            grid[row-1][col-1] = true; // assign the site as open
            siteOpen++;
            if (row == 1) {
                wqu.union(0, dimMining(row, col));
                wquCopy.union(0, dimMining(row, col));
                if (n > 1 && isOpen(row+1, col)) {
                    wqu.union(dimMining(row, col), dimMining(row+1, col));
                    wquCopy.union(dimMining(row, col), dimMining(row+1, col));
                } else if (n == 1) {
                    wqu.union(1, 2);
                }
            } else if (row == n) {
                wqu.union(n*n+1, dimMining(row, col));
                if (isOpen(row-1, col)) {
                    wqu.union(dimMining(row-1, col), dimMining(row, col));
                    wquCopy.union(dimMining(row-1, col), dimMining(row, col));
                }
            } else {
                if (isOpen(row-1, col)) {
                    wqu.union(dimMining(row-1, col), dimMining(row, col));
                    wquCopy.union(dimMining(row-1, col), dimMining(row, col));
                }
                if (isOpen(row+1, col)) {
                    wqu.union(dimMining(row, col), dimMining(row+1, col));
                    wquCopy.union(dimMining(row, col), dimMining(row+1, col));
                }
            }
            if (col > 1) {
                if (isOpen(row, col-1)) {
                    wqu.union(dimMining(row, col-1), dimMining(row, col));
                    wquCopy.union(dimMining(row, col-1), dimMining(row, col));
                }
            }
            if (col < n) {
                if (n > 1 && isOpen(row, col+1)) {
                   wqu.union(dimMining(row, col), dimMining(row, col+1));
                   wquCopy.union(dimMining(row, col), dimMining(row, col+1));
                }
            }
        }
    }
    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        if (row <= 0 || col <= 0 || row > n || col > n) {
            // check the value of row and col
            throw new IllegalArgumentException("Invalid row or col for isOpen()."); 
        }
        return grid[row-1][col-1];
    }
    public boolean isFull(int row, int col) {
        // is site (row, col) full?
        if (row <= 0 || col <= 0 || row > n || col > n) {
            // check the value of row and col
            throw new IllegalArgumentException("Invalid row or col for isFull()."); 
        }
        return wquCopy.connected(0, dimMining(row, col)); 
    }
    public int numberOfOpenSites() {
        // number of open sites
        return siteOpen;
    }
    public boolean percolates() {
        // does the system percolate?
        return wqu.connected(0, n*n + 1);
    }
  
    public static void main(String[] args) {
        // test client (optional)
        // create 5 by 5 grid with all sites blocked
        Percolation p = new Percolation(5);
        // check the system percolate or not
        System.out.println(p.percolates());
        // manually open serveral sites 
        p.open(1, 1);
        p.open(1, 2);
        p.open(2, 2);
        p.open(3, 2);
        p.open(4, 2);
        p.open(4, 3);
        p.open(5, 3);
        // check the system percolate or not
        System.out.println(p.percolates());
    }
}
