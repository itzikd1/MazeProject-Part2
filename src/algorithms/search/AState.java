package algorithms.search;
//TODO change names to normal names instead of x and stuff like that Shift+R in BFS\DFS\BFS2

/**
 * abstract class to describe a state in current problem
 */

public abstract class AState {
    private int id;
    private double cost;
    AState cameFrom;
    private static int counter = 0;

    /**
     * constructor
     */

    AState() {
        this.id = counter;
        counter++;
        cameFrom = null;
        this.cost = 0;
    }

    /**
     * @return cost of aState
     */

    public double getCost() {
        return this.cost;
    }


    /**
     * @param costI set cost
     */

    public void setCost(double costI) {
        this.cost=costI;
    }

    /**
     * to string - abstract
     */

    public abstract String toString();

    public int hashCode() {
        return id;
    }

}