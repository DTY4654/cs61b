/**
 * Created by GritShiva on 2017/5/22 0022.
 */
package hw4.puzzle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;


public class Solver {

    private final Board initialBoard;
    private MinPQ<SearchNode> searchNodes;
    private List<Board> solutionBoards = new ArrayList<>();



    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */

    public Solver(Board initial){
        this.initialBoard = initial;
        this.searchNodes = new MinPQ<>();




    }

    public class SearchNode{

        private Board currentState;
        private int movesSofar;
        private SearchNode previousNode;
        private int priority;

        public SearchNode(Board b, int movesMade, SearchNode prevNode){
            this.currentState = b;
            this.movesSofar = movesMade;
            this.previousNode = prevNode;
            this.priority = this.currentState.manhattan() + movesSofar;
        }

        public int getMoves(){
            return movesSofar;
        }

        public int getPriority(){
            return priority;
        }

        public Board getState(){
            return currentState;
        }

        public SearchNode getPreviousNode(){
            return previousNode;
        }
    }



    /**
     * Returns the minimum number of moves to solve the
     * initial board.
     *
     * To solve the puzzle from a given search node on the priority queue,
     * the total number of moves we need to make (including those already made)
     * is at least its priority, using either the Hamming or Manhattan priority function.
     *
     */
    public int moves(){
        return initialBoard.manhattan();
    }


    /**
     * Returns the sequence of Boards from the initial board
     * to the solution.
     */
    public Iterable<Board> solution(){



    }






    // DO NOT MODIFY MAIN METHOD

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution()) {
            StdOut.println(board);
       }
    }

}