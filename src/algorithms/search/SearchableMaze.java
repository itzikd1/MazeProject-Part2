package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

/**
 * Class to get info of maze
 */

public class SearchableMaze implements ISearchable {

    private Maze maze;
    private MazeState start;
    private MazeState end;
    private boolean[][] visitedMap;

    /**
     * constructor
     *
     * @param m - maze we get from user
     */

    public SearchableMaze(Maze m) {
        if (m != null) {
            maze = m;
            start = new MazeState(m.getStartPosition().getRowIndex(), m.getStartPosition().getColumnIndex());
            end = new MazeState(m.getGoalPosition().getRowIndex(), m.getGoalPosition().getColumnIndex());
            visitedMap = new boolean[m.numOfRows()][m.numOfColumns()];
        }
    }

    /**
     * get start position of maze
     *
     * @return - start position
     */

    @Override
    public AState getStartState() {
        return start;
    }

    /**
     * get end position of maze
     *
     * @return - end position
     */

    @Override
    public AState getGoalState() {
        return end;
    }

    /**
     * set end position of maze
     */

    @Override
    public void setGoalState(AState x) {
        if (x != null && x instanceof MazeState) //make sure aState is a MazeState
            end = (MazeState) x;
    }

    /**
     * check if row and column are not out of array
     *
     * @param row    from user
     * @param column from user
     */

    private boolean isLegal(int row, int column) {
        if (row < 0 || column < 0 || row >= maze.numOfRows() || column >= maze.numOfColumns())
            return false;
        if (visitedMap[row][column] == false)
            return true;
        return false;
    }

    private ArrayList<AState> getAllDiagonal(int x, int y) {
        ArrayList<AState> temp = new ArrayList<AState>();
        MazeState tempM;
        if (isLegal(x - 1, y - 1) && visitedMap[x - 1][y - 1] == false && maze.getCellValue(x - 1, y - 1) == 0)
            if (visitedMap[x - 1][y] == false || visitedMap[x][y - 1] == false)
                if (maze.getCellValue(x - 1, y) == 0 || maze.getCellValue(x, y - 1) == 0) {
                    tempM = new MazeState(x - 1, y - 1);
                    tempM.setCost(1.5);
                    temp.add(tempM);
                }
        if (isLegal(x + 1, y + 1) && visitedMap[x + 1][y + 1] == false && maze.getCellValue(x + 1, y + 1) == 0)
            if (visitedMap[x + 1][y] == false || visitedMap[x][y + 1] == false)
                if (maze.getCellValue(x, y + 1) == 0 || maze.getCellValue(x + 1, y) == 0) {
                    tempM = new MazeState(x + 1, y + 1);
                    tempM.setCost(1.5);
                    temp.add(tempM);
                }
        if (isLegal(x + 1, y - 1) && visitedMap[x + 1][y - 1] == false && maze.getCellValue(x + 1, y - 1) == 0)
            if (visitedMap[x][y - 1] == false || visitedMap[x + 1][y] == false)
                if (maze.getCellValue(x, y - 1) == 0 || maze.getCellValue(x + 1, y) == 0) {
                    tempM = new MazeState(x + 1, y - 1);
                    tempM.setCost(1.5);
                    temp.add(tempM);
                }
        if (isLegal(x - 1, y + 1) && visitedMap[x - 1][y + 1] == false && maze.getCellValue(x - 1, y + 1) == 0)
            if (visitedMap[x - 1][y] == false || visitedMap[x - 1][y] == false)
                if (maze.getCellValue(x - 1, y) == 0 || maze.getCellValue(x, y + 1) == 0) {
                    tempM = new MazeState(x - 1, y - 1);
                    tempM.setCost(1.5);
                    temp.add(tempM);
                }
        return temp;
    }

    /**
     * get all location AState can move to in maze (up\down\left\right)
     *
     * @param s - current state from user
     * @return list with all possible states
     */
//TODO Need to take care of cost

    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        ArrayList<AState> temp = new ArrayList<AState>(); //array to keep possible states
        ArrayList<AState> tempD; //array to keep Diagonal states
        MazeState mazestate;
        if (s != null && s instanceof MazeState) //make sure State is a MazeState
        {
            mazestate = ((MazeState) s);
            int x = mazestate.getRow();
            int y = mazestate.getCol();
            MazeState TempAdd; //temp mazestate
            TempAdd = CheckLegal(x - 1, y); //check if legal to add
            if (TempAdd != null) //if it is
                temp.add(TempAdd); //add to arraylist
            TempAdd = CheckLegal(x + 1, y);
            if (TempAdd != null)
                temp.add(TempAdd);
            TempAdd = CheckLegal(x, y - 1);
            if (TempAdd != null)
                temp.add(TempAdd);
            TempAdd = CheckLegal(x, y + 1);
            if (TempAdd != null)
                temp.add(TempAdd);
            tempD = getAllDiagonal(x, y); //get all diagonal states
            for (int i = 0; i < tempD.size(); i++)
                temp.add(tempD.get(i)); //add them to array
        }
        return temp;
    }

    /**
     * check if state is legal or not
     *
     * @param x,y - current x,y location from user
     * @return mazestate if legal, else null
     */

    private MazeState CheckLegal(int x, int y) {
        MazeState tempM;
        if (isLegal(x, y))
            if (maze.getCellValue(x, y) == 0) {
                tempM = new MazeState(x, y);
                tempM.setCost(1);
                return tempM;
            }
        return null;
    }

    /**
     * check if aState has been visitedMap in the past
     *
     * @param visit - get a Astate from user
     * @return true or false if Astate has been visitedMap
     */

    //TODO error here for some reason with big mazes
    @Override
    public boolean isVisited(AState visit) {
        if (visit != null && ((MazeState) visit).getRow() < maze.numOfRows()-1 && ((MazeState) visit).getCol() < maze.numOfColumns()-1)
            return visitedMap[((MazeState) visit).getRow()][((MazeState) visit).getCol()];
        else
            return false;
    }

    /**
     * change visit to positive
     *
     * @param visit - update to true
     */

    @Override
    public void changeVisitTrue(AState visit) {
        if (visit != null)
            visitedMap[((MazeState) visit).getRow()][((MazeState) visit).getCol()] = true;
    }

    /**
     * reset all visitedMap field to false
     */

    @Override
    public void ResetVisit() {
        for (int i = 0; i < maze.numOfColumns(); i++)
            for (int j = 0; j < maze.numOfRows(); j++)
                visitedMap[j][i] = false;
    }
}