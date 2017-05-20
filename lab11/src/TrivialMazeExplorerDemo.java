/**
 * Created by GritShiva on 2017/5/20 0020.
 */
/**
 *  @author Josh Hug
 */

public class TrivialMazeExplorerDemo {
    public static void main(String[] args) {
        Maze maze = new Maze("maze.config");
        TrivialMazeExplorer tme = new TrivialMazeExplorer(maze);
        tme.solve();
    }
}