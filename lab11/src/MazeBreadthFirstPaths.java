/**
 * Created by GritShiva on 2017/5/20 0020.
 */
import java.util.*;

/**
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
    }

    /** Conducts a breadth first search of the maze starting at vertex x. */
    private void bfs(int s) {
        /* Your code here. */
        
        Queue<Integer> q = new PriorityQueue<>();
        distTo[s] = 0;
        marked[s] = true;
        announce();
        q.add(s);

        while(!q.isEmpty()) {
            int v = q.poll();
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    announce();
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    announce();
                    q.add(w);
                    if(marked[t]){
                        return;
                    }
                }
            }
        }

    }




    @Override
    public void solve() {
        bfs(s);
    }
}