package algorithms.mazeGenerators;

/**
 * abstract class to generates a maze, and returns how long it took to create
 */

public abstract class AMazeGenerator implements IMazeGenerator {

    @Override
    public abstract Maze generate(int rows, int columns);


    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long StartTime = System.currentTimeMillis(); //get start time
        if (rows < 5)
            rows = 10;
        if (columns < 5)
            columns = 10;
        generate(rows, columns); //generate maze
        long StopTime = System.currentTimeMillis(); //get end time
        return StopTime - StartTime; //return the difference
    }
}