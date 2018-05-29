
package test;

import algorithms.mazeGenerators.*;
import IO.*;

import java.io.IOException;
import java.io.OutputStream;

public class MyTests {

    public static void main(String[] args) {


        testMazeGenerator(new MyMazeGenerator());

    }

    @SuppressWarnings("Duplicates")
    private static void testMazeGenerator(IMazeGenerator mazeGenerator) {

// prints the time it takes the algorithm to run
//        System.out.println(String.format("Maze generation time(ms): %s",
//                mazeGenerator.measureAlgorithmTimeMillis(3/*rows*/,6/*columns*/)));
// generate another maze
        Maze maze = mazeGenerator.generate(5/*rows*/, 5/*columns*/);
// prints the maze
        maze.print();
        // test for maze to byte[]
        byte[] temp;
        temp = maze.toByteArray();//this is original maze generated normally
        Maze maze2 = new Maze(temp);//this maze is generated by byte[] constructor
        maze2.print();


// get the maze entrance
        Position startPosition = maze.getStartPosition();
// print the position
        System.out.println(String.format("Start Position: %s",
                startPosition)); // format "{row,column}"
// prints the maze exit position
        System.out.println(String.format("Goal Position: %s",
                maze.getGoalPosition()));
    }

}