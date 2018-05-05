package algorithms.search;

public class MazeState extends AState {

    private int row;
    private int col;

    /**
     * constructor of MazeState
     * @param row and column of maze
     */

    public MazeState(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }


    /**
     * toString - print the MazeState
     * @return string describes position in maze
     */

    @Override
    public String toString() {
        String x;
        x = row + "," + col;
        return x;
    }

    /**
     * getter and setter of rows and columns
     * @return current state's row and column
     */

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * equable function to compare between mazeState's
     * @param  o - object t compare
     */

    public boolean equals(Object o) {
        if (!(o instanceof MazeState)) //check that object is MazeState type
            return false;
        return ((MazeState) o).row == row && ((MazeState) o).col == col;
    }

    public int hashCode() {
        return 3 * (row * 5 + col * 3) + row;
    }
}