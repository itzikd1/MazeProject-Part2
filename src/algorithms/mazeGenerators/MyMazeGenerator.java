package algorithms.mazeGenerators;

import java.util.*;

public class MyMazeGenerator extends AMazeGenerator {
    private Maze myMaze;
    private Position[][] cells;
    private List<Position> candidates;

    /**
     * constructor
     */

    public MyMazeGenerator() {
        candidates = new ArrayList<Position>();
    }

    /**
     * generate a maze with all 1's
     *
     * @param columns - columns of maze
     * @param rows    - rows of maze
     */

    public Maze generate(int rows, int columns) {
        if (rows < 2)
            rows = 10;
        if (columns < 2)
            columns = 10;
        myMaze = new Maze(rows, columns);
        cells = new Position[rows][columns];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                cells[i][j] = new Position(i, j); // double array with new position in each cell
        //change all values to 1
        for (int i = 0; i < myMaze.numOfRows(); i++)
            for (int j = 0; j < myMaze.numOfColumns(); j++)
                myMaze.changeCellValue(i, j, 1);
        candidates.add(myMaze.getStartPosition()); //add start position to array
        Position currentPosition;
        while (candidates.size() > 0) { //while array not empty
            currentPosition = getRandomPos();//from candidates list
            if (isChangeAble(currentPosition)) {
                addToPath(currentPosition);//change value to 0
                addCandidates(currentPosition);//neighbours are candidates
            }
            candidates.remove(currentPosition);
        }
        makeGoalPosition();
        return myMaze;
    }

    /**
     * returns array list of 2-4 neighbours of a position
     *
     * @param p - position from user
     */

    private List<Position> myNeighbours(Position p) {
        if (p == null)
            return null;
        List<Position> neighbours;
        neighbours = new ArrayList<Position>();
        //check if can go up down left or right if so, add to list
        if (isLegal(p.getRowIndex() - 1, p.getColumnIndex()))
            neighbours.add(cells[p.getRowIndex() - 1][p.getColumnIndex()]);
        if (isLegal(p.getRowIndex() + 1, p.getColumnIndex()))
            neighbours.add(cells[p.getRowIndex() + 1][p.getColumnIndex()]);
        if (isLegal(p.getRowIndex(), p.getColumnIndex() - 1))
            neighbours.add(cells[p.getRowIndex()][p.getColumnIndex() - 1]);
        if (isLegal(p.getRowIndex(), p.getColumnIndex() + 1))
            neighbours.add(cells[p.getRowIndex()][p.getColumnIndex() + 1]);
        return neighbours; //return list
    }

    /**
     * add to candidates<position> list all position's neighbours who might become a path.
     *
     * @param p - position from user
     */

    private void addCandidates(Position p) {
        if (p != null) {
            List<Position> neighbours;
            neighbours = myNeighbours(p);
            for (int i = 0; i < neighbours.size(); i++)
                if (myMaze.getCellValue(neighbours.get(i).getRowIndex(), neighbours.get(i).getColumnIndex()) == 1 && isChangeAble(neighbours.get(i)))
                    candidates.add(neighbours.get(i)); //only legal neighbours and that have 1
        }
    }

    /**
     * changeable cell (from 1 to 0) is a cell that has maximum of one 0 neighbour,
     *
     * @param p - position from user
     */

    private void addToPath(Position p) {
        if (p != null && isChangeAble(p))
            myMaze.changeCellValue(p.getRowIndex(), p.getColumnIndex(), 0);
    }

    /**
     * checks if currently position can be changed to 0, if so, then do it
     *
     * @param p - position from user
     */

    private boolean isChangeAble(Position p) {
        if (p == null)
            return false;
        if (myMaze.getCellValue(p.getRowIndex(), p.getColumnIndex()) == 0)
            return false;
        return (numOfPathNeighbours(p) <= 1);
    }

    /**
     * returns number of neighbours which are already on the path
     *
     * @param p - position from user
     */

    private int numOfPathNeighbours(Position p) {
        if (p == null)
            return 0;
        int count = 0;
        //if neighbour legal and has 0 count++;
        if (isLegal(p.getRowIndex(), p.getColumnIndex() + 1) &&
                myMaze.getCellValue(p.getRowIndex(), p.getColumnIndex() + 1) == 0)
            count++;
        if (isLegal(p.getRowIndex(), p.getColumnIndex() - 1) &&
                myMaze.getCellValue(p.getRowIndex(), p.getColumnIndex() - 1) == 0)
            count++;
        if (isLegal(p.getRowIndex() + 1, p.getColumnIndex()) &&
                myMaze.getCellValue(p.getRowIndex() + 1, p.getColumnIndex()) == 0)
            count++;
        if (isLegal(p.getRowIndex() - 1, p.getColumnIndex()) &&
                myMaze.getCellValue(p.getRowIndex() - 1, p.getColumnIndex()) == 0)
            count++;
        return count;
    }

    /**
     * checks if the position is on maze's bounds
     *
     * @param column - column of maze
     * @param row    - row of maze
     */

    private boolean isLegal(int row, int column) {
        if (row < 0 || row >= myMaze.numOfRows()) // check if out of bound
            return false;
        return column >= 0 && column < myMaze.numOfColumns();
    }


    /**
     * returns a random position from candidates list.
     *
     * @return - random position
     */
    private Position getRandomPos() {
        Random random = new Random();
        int index = random.nextInt(candidates.size()); // get a random candidate.
        return candidates.get(index);
    }

    /**
     * set a random goal position
     */

    private void makeGoalPosition() {
        int x;
        boolean found = false;
        int rows = myMaze.numOfRows();
        int columns = myMaze.numOfColumns();
        Random random = new Random();
        while (!found) {
            x = random.nextInt(2) + 1;//get value of 1 or 2
            if (x == 1) {
                x = random.nextInt(columns); // get a random column
                if (myMaze.getCellValue(rows - 1, x) == 0) {//legal goal position in last row.
                    myMaze.setGoalPosition(new Position(rows - 1, x));
                    found = true;
                }
            } else {
                x = random.nextInt(myMaze.numOfRows());// get a random row
                if (myMaze.getCellValue(x, columns - 1) == 0) {//legal goal position in last column
                    myMaze.setGoalPosition(new Position(x, columns - 1));
                    found = true;
                }
            }
        }
    }
}