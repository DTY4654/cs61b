package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridSize;
    private boolean [][] isGridOpened;
    private int numOfOpenSites;
    private WeightedQuickUnionUF uf;



    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if( N <= 0){
            throw new IllegalArgumentException();
        }
        this.isGridOpened = new boolean[N][N];
        this.gridSize = N;
        for( int i = 0; i < N; i++){
            for(int j = 0; j < N; j++) {
                this.isGridOpened[i][j] = false;
            }
        }
        this.numOfOpenSites = 0;
        this.uf = new WeightedQuickUnionUF( N * N );
    }


    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if(row < 0 || row > gridSize - 1 || col < 0 || col > gridSize - 1){
            throw new IndexOutOfBoundsException();
        }
        if(isGridOpened[row][col]){
            return;
        } else{
            isGridOpened [row][col] = true;
            numOfOpenSites += 1;
        }

        //if left,right,top,bottom block is open, connect them.
        if (col + 1< gridSize  && isOpen(row, col + 1)) {
            uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }

        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }

        if( row + 1 < gridSize  && isOpen(row + 1, col)){
            uf.union(xyTo1D(row,col),xyTo1D(row + 1, col));
        }

        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
    }



    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(row < 0 || row > gridSize - 1 || col < 0 || col > gridSize - 1){
            throw new IndexOutOfBoundsException();
        }
        return this.isGridOpened[row][col];
    }



    private int xyTo1D (int row, int col){
        if(row < 0 || row > gridSize - 1 || col < 0 || col > gridSize - 1){
            throw new IndexOutOfBoundsException();
        }
        return row * gridSize + col;
    }


    // is the site (row, col) full?  is connected to top
    public boolean isFull(int row, int col) {
        if (row < 0 || row > gridSize - 1 || col < 0 || col > gridSize - 1) {
            throw new IndexOutOfBoundsException();
        } else if (row == 0 && isOpen(row, col)) {
            return true;
        } else if (row != 0) {
            for(int i = 0; i < gridSize; i++) {
                if (isOpen(row, col) && isOpen(0, i) && uf.connected(xyTo1D(row,col), i)) {
                    return true;
                }
            }
        }
        return false;
    }


    // Performance requirements: all methods should take constant time plus a constant number of calls to
    // the union-find methods union(), find(), connected(), and count().
    // Meeting these requirements is somewhat tricky! You might consider creating a solution that simply works,
    // before figuring out a way to make it faster. For tips on meeting the speed requirements,
    // see the video at the beginning of this spec. Your numberOfOpenSites() method must take constant time.


    // number of open sites
    // Your numberOfOpenSites() method must take constant time.
    public int numberOfOpenSites(){
        return numOfOpenSites;
    }


    // does the system percolate?
    public boolean percolates(){
        for(int i = gridSize  * (gridSize - 1) ; i < gridSize * gridSize - 1; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (uf.connected(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
//3
//        0 2
//        1 2
//        2 2
//        2 0
//        1 0
//        0 0



    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(0,2);
        p.open(1,2);
        System.out.println(p.numberOfOpenSites());
        p.open(2,2);

        p.open(2,0);
        p.open(1,0);
        System.out.println(p.numberOfOpenSites());
        p.open(0,0);


        System.out.println(" is ( 0,2) fulled?" + p.isFull(0,2));
        System.out.println(" is ( 1,2) fulled?" + p.isFull(1,2));
        System.out.println(" is ( 1,2) and (0,2) connected>" + p.uf.connected(p.xyTo1D(0,2), p.xyTo1D(1,2)));

    }

}                       
