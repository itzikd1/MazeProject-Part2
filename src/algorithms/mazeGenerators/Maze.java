package algorithms.mazeGenerators;


public class Maze {
    private int[][] myMaze;
    private Position startPosition;
    private Position goalPosition;

    /**
     * constructor of maze
     */

    public Maze(int row, int column) {
        if (row < 2)
            row = 10;
        if (column < 2)
            column = 10;
        myMaze = new int[row][column];
        startPosition = new Position(0, 0); //default start = (0,0)

    }

    /**
     * @return start position of maze
     */

    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * @return goal position of maze
     */

    public Position getGoalPosition() {
        return goalPosition;
    }

    /**
     * @param startPosition - start position of maze
     */

    public void setStartPosition(Position startPosition) {
        if (startPosition.getRowIndex() < this.numOfRows() && startPosition.getColumnIndex() < this.numOfColumns())
            if (startPosition.getColumnIndex() >= 0 && startPosition.getRowIndex() >= 0)
                this.startPosition = startPosition;
    }

    /**
     * @param goalPosition - goal position of maze
     */

    public void setGoalPosition(Position goalPosition) {
        if (goalPosition.getRowIndex() < this.numOfRows() && goalPosition.getColumnIndex() < this.numOfColumns())
            if (goalPosition.getColumnIndex() >= 0 && goalPosition.getRowIndex() >= 0)
                this.goalPosition = goalPosition;
    }

    /**
     * @return number of rows in maze
     */

    public int numOfRows() {
        return myMaze.length;
    }

    /**
     * @return number of columns in maze
     */

    public int numOfColumns() {
        return myMaze[0].length;
    }

    /**
     * change value of cell in maze
     * @param row - row of maze
     * @param column - column of maze
     * @param value - value to change to
     */

    public void changeCellValue(int row, int column, int value) {
        if (value == 0 || value == 1)
            if (row < numOfRows() && column < numOfColumns() && row >= 0 && column >= 0)
                myMaze[row][column] = value;
    }

    /**
     * @return value of a cell in maze
     */

    public int getCellValue(int row, int column) {
        return myMaze[row][column];
    }

    /**
     * print maze
     */

    public void print() {
        for (int i = 0; i < myMaze.length; i++) {
            System.out.println(" "); //go down at end of line of array
            for (int j = 0; j < myMaze[0].length; j++)
                if (i == startPosition.getRowIndex() && j == startPosition.getColumnIndex())
                    System.out.print(" S"); //start position
                else if (i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex())
                    System.out.print(" E"); //end position
                else
                    System.out.print(" " + myMaze[i][j]); //print if 0 or 1 and add space
        }
        System.out.println(" "); //just so it looks better
    }
}