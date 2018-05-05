package algorithms.mazeGenerators;

/**
 * Interface for maze generator
 */

public interface IMazeGenerator {

    /**
     * @param rows    - number of rows
     * @param columns - number of columns
     * @return generated maze
     */
    Maze generate(int rows, int columns);

    /**
     * measure time to create
     *
     * @param rows, columns - maze's dimensions
     * @return generation time
     */
    long measureAlgorithmTimeMillis(int rows, int columns);
}