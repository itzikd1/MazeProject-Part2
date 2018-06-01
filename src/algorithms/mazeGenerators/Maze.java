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
        goalPosition = new Position(row-1, column-1); //default start = (row,col)
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
     *
     * @param row    - row of maze
     * @param column - column of maze
     * @param value  - value to change to
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
        int x = startPosition.getRowIndex();
        int y = startPosition.getColumnIndex();
        int x2 = goalPosition.getRowIndex();
        int y2 = goalPosition.getColumnIndex();
        for (int i = 0; i < myMaze.length; i++) {
            System.out.println(" "); //go down at end of line of array
            for (int j = 0; j < myMaze[0].length; j++)
                if (i == x && j == y)
                    System.out.print(" S"); //start position
                else if (i == x2 && j == y2)
                    System.out.print(" E"); //end position
                else
                    System.out.print(" " + myMaze[i][j]); //print if 0 or 1 and add space
        }
        System.out.println(" "); //just so it looks better
    }


    /**
     @@@@@@@@@@@@@@@@@@@@@@              PART B            @@@@@@@@@@@@@@
     */


    /**
     *
     * @param calculateMe - int to calculate
     * @return number of multipications needed to represent parameter +1
     */
    public int DivideBy256(int calculateMe){
        int ans = 0;
        while (calculateMe > 0) {
            calculateMe = calculateMe - 255;
            ans++;
        }
        return ans;
    }

    /**
     * Converts this maze to byte array.
     * @return byte array represents maze
     */

    public byte[] toByteArray() {

        int rows = numOfRows();
        int rowsNeeded = rows/256;
        int rowsModulo = rows%256;

        int cols = numOfColumns();
        int colsNeeded = cols/256;
        int colsModulo = cols%256;

        int goalPosRow = getGoalPosition().getRowIndex();
        int goalRowsNeeded = goalPosRow/256;
        int goalRowsModulo = goalPosRow%256;

        int goalPosCol = getGoalPosition().getColumnIndex();
        int goalColsNeeded = goalPosCol/256;
        int goalColsModulo = goalPosCol%256;

        byte [] mazeAsByteArray = new byte [rows*cols+8];
        mazeAsByteArray[0]=(byte)rowsNeeded;
        mazeAsByteArray[1]=(byte)rowsModulo;
        mazeAsByteArray[2]=(byte)colsNeeded;
        mazeAsByteArray[3]=(byte)colsModulo;
        mazeAsByteArray[4]=(byte)goalRowsNeeded;
        mazeAsByteArray[5]=(byte)goalRowsModulo;
        mazeAsByteArray[6]=(byte)goalColsNeeded;
        mazeAsByteArray[7]=(byte)goalColsModulo;
        int k = 8;
        for(int i = 0; i < myMaze.length;i++)
            for(int j = 0; j < myMaze[0].length; j++) {
                mazeAsByteArray[k] = (byte)myMaze[i][j];
                k++;
            }
        return mazeAsByteArray;
    }

    public Maze(byte[] byteMaze){


        int rows = (int)(byteMaze[0]*256 + byteMaze[1]);
        int cols = (int)(byteMaze[2]*256 + byteMaze[3]);
        myMaze = new int[rows][cols];
        int k=0;
        for(;k<8;k++)
            if(byteMaze[k]<0)
                byteMaze[k]+=256;

        for ( int i = 0; i < rows;i++) {
            for (int j = 0; j < cols; j++) {
                myMaze[i][j] = (int)byteMaze[k];
                k++;
            }
        }
        setStartPosition(new Position(0,0));
        int actualGoalPosRow = byteMaze[4]*256+byteMaze[5];
        int actualGoalPosCol = byteMaze[6]*256+byteMaze[7];
        setGoalPosition(new Position(actualGoalPosRow,actualGoalPosCol));

        if (byteMaze.length < 7)//TODO THINK
        {
        }
//TODO avoid boring mazes such as we did before
        /*if (row < 2)
            row = 10;
        if (column < 2)
            column = 10;
        */
    }
}