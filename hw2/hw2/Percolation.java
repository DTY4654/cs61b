package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridSize;
    private boolean [][] isGridOpened;
    private WeightedQuickUnionUF uf;




    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if( N <= 0){
            throw new IllegalArgumentException();
        }
        this.isGridOpened = new boolean[N][N];
        this.gridSize = N;
        for( int i = 0; i < N; i++){
            this.isGridOpened [i][i] = false;
        }
        this.uf = new WeightedQuickUnionUF( N * N );

    }


    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if(row < 0 || row > gridSize - 1 || col < 0 || col > gridSize - 1){
            throw new IndexOutOfBoundsException();
        }
        if(this.isGridOpened[row][col]){
            return;
        } else{
            this.isGridOpened [row][col] = true;
        }

        //if left,right,top,bottom block is open, connect them.

        while( col + 1< gridSize - 1 ) {
            if (isOpen(row, col + 1)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }

        while(col - 1 > 0 ) {
            if (isOpen(row, col - 1)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
        }

        while( row + 1 < gridSize - 1 ){
            if(isOpen(row + 1, col)){
                uf.union(xyTo1D(row,col),xyTo1D(row + 1, col));
            }
        }

        while(row - 1 > 0) {
            if (isOpen(row - 1, col)) {
                uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
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
    public boolean isFull(int row, int col){
        if(row < 0 || row > gridSize - 1 || col < 0 || col > gridSize - 1){
            throw new IndexOutOfBoundsException();
        }

        for(int i = 0; i < gridSize; i++ ){
            if(uf.connected(xyTo1D(row, col), i)){
                return true;
            }
        }
        return false;
    }


    // all methods should take constant time plus a constant number of calls to
    // the union-find methods union(), find(), connected(), and count().
    // Meeting these requirements is somewhat tricky! You might consider creating a solution that simply works,
    // before figuring out a way to make it faster. For tips on meeting the speed requirements,
    // see the video at the beginning of this spec. Your numberOfOpenSites() method must take constant time.


    // number of open sites
    // Your numberOfOpenSites() method must take constant time.
    public int numberOfOpenSites(){
        int count = 0;
        for(int i = 0; i < gridSize; i++){
            for( int j = 0; j < gridSize; j++){
                if (isGridOpened[i][j]){
                    count ++;
                }
            }
        }
        return count;
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

    // unit testing (not required)
    public static void main(String[] args) {


    }

}                       
