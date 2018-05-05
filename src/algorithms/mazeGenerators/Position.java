package algorithms.mazeGenerators;

public class Position {
    private int row;
    private int column;

    /**
     * constructor of position
     */

    public Position(int row, int column) {
        if (row < 0)
            row = 0;
        if (column < 0)
            column = 0;
        this.row = row;
        this.column = column;
    }

    /**
     * @return index of row
     */

    public int getRowIndex() {
        return row;
    }

    /**
     * @return index of column
     */

    public int getColumnIndex() {
        return column;
    }

    /**
     * toString - print position to user
     *
     * @return Position printed nicely
     */

    public String toString() {
        return "{" + row + "," + column + "}";
    }
}