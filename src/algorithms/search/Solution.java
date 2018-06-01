package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * solution class
 * returns array with all solution steps
 */

public class Solution implements Serializable {

    private ArrayList<AState> solution;

    /**
     * constructor of solution
     */

    public Solution() {
        solution = new ArrayList<AState>();
    }

    /**
     * add a state to solution array
     */

    public void addState(AState newState) {
        if (newState != null)
            solution.add(newState);
    }

    /**
     * return solution array
     *
     * @return solution - list with all solution steps
     */

    public ArrayList<AState> getSolutionPath() {
        return solution;
    }

    @Override
    public String toString() {
        return "";
    }
}