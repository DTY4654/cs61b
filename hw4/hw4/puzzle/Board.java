package hw4.puzzle;


import edu.princeton.cs.algs4.Queue;

public class Board {

    private final int [][] board;
    private final int N;




    /** Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     * NOTE that Board class must be immutable*/

    public Board(int[][] tiles){
        if(tiles == null){
            throw new NullPointerException();
        }
        this.N = tiles.length;
        this.board = new int[N][N];
        for(int i = 0 ; i < N; i++){
            for(int j = 0; j < N; j++){
                this.board[i][j] = tiles[i][j];
            }
        }
    }


    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     */
    public int tileAt(int i, int j){
        if( i > N - 1 || i < 0 || j > N - 1 || j < 0 ){
            throw new java.lang.IndexOutOfBoundsException();
        }

        return board[i][j];
    }


    /**
     * Returns the board size N
     */

    public int size(){
        return this.N;
    }


    /**
     * Hamming priority function: The number of tiles in the wrong position, plus the number of moves made so far
     * to get to the search node.
     */
    public int hamming() {
        int hamming = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.board[i][j] != N * i + j + 1 && this.board[i][j]!= 0) {
                    hamming += 1;
                }
            }
        }

        return hamming;

    }


    /**
     * Manhattan priority function: The sum of the Manhattan distances (sum of the vertical and horizontal distance)
     * from the tiles to their goal positions, plus the number of moves made so far to get to the search node.
     */
    public int manhattan(){
        int manhanttan = 0;

        for(int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                int currentNumInThePosition = this.board[i][j];
                if(currentNumInThePosition == 0){
                    continue;
                }else{
                    int expectedRow = (currentNumInThePosition - 1) / N;
                    int expectedCol = (currentNumInThePosition - 1) % N;
                    manhanttan += (Math.abs(i - expectedRow) + Math.abs(j - expectedCol));
                }
            }
        }
        return manhanttan;
    }


    /**
     * Returns true if is this board the goal board
     */
    public boolean isGoal(){
        return manhattan() == 0;
    }




    public static Iterable<Board> neighbors(Board var0) {
        Queue var1 = new Queue();
        int var2 = var0.size();
        int var3 = -1;
        int var4 = -1;

        int var6;
        for(int var5 = 0; var5 < var2; ++var5) {
            for(var6 = 0; var6 < var2; ++var6) {
                if(var0.tileAt(var5, var6) == 0) {
                    var3 = var5;
                    var4 = var6;
                }
            }
        }

        int[][] var9 = new int[var2][var2];

        int var7;
        for(var6 = 0; var6 < var2; ++var6) {
            for(var7 = 0; var7 < var2; ++var7) {
                var9[var6][var7] = var0.tileAt(var6, var7);
            }
        }

        for(var6 = 0; var6 < var2; ++var6) {
            for(var7 = 0; var7 < var2; ++var7) {
                if(Math.abs(var6 - var3) + Math.abs(var7 - var4) == 1) {
                    var9[var3][var4] = var9[var6][var7];
                    var9[var6][var7] = 0;
                    Board var8 = new Board(var9);
                    var1.enqueue(var8);
                    var9[var6][var7] = var9[var3][var4];
                    var9[var3][var4] = 0;
                }
            }
        }

        return var1;
    }
    /**
     * Returns true if this board's tile values are the same position as y's
     */
    public boolean equals(Object y){
        if(y == this){
            return true;
        }

        if(y == null){
            return false;
        }

        if(y.getClass() != this.getClass()){
            return false;
        }

        Board that = (Board) y;

        if(this.N != that.N){
            return false;
        }

        for(int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }

        return true;

    }



    /** Returns the string representation of the board. 
     * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}