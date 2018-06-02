package algorithms.search;

import algorithms.mazeGenerators.Maze;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BreadthFirstSearchTest {
    BreadthFirstSearch bfs = new BreadthFirstSearch();
    Maze temp = new Maze(50,50);
    ISearchable MazeSerach = new SearchableMaze(temp);
    Solution tempSolve = new Solution();
    @Test
    void solve() {
        assertNotEquals(bfs.solve(MazeSerach),null);
        assertNotEquals(bfs.solve(MazeSerach),0);

    }

    @Test
    void getName() {
        assertEquals(bfs.getName(),"BreadthFirstSearch");
    }

    @Test
    void finalSolution() {
        assertNotEquals(tempSolve.getSolutionPath(),null);
    }
}