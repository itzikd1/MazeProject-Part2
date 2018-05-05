package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {
    MyMazeGenerator test = new MyMazeGenerator();
    BestFirstSearch bestFS = new BestFirstSearch();
    Maze testMaze= test.generate(15,15);
    @Test
    void solve() {
        Solution Soul = bestFS.solve(new SearchableMaze(testMaze)); //solve maze with bfs
        assertNotEquals( null,Soul); //make sure its not equal null
    }

    @Test
    void getName() {
        assertEquals("BestFirstSearch", bestFS.getName()); // make sure name is BestFirstSearch
    }

    @Test
    void getNumberOfNodesEvaluated() {
        assertEquals(0, bestFS.getNumberOfNodesEvaluated()); //make sure before run its 0
    }
    
}