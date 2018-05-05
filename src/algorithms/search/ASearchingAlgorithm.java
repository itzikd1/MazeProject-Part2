package algorithms.search;

import java.util.Stack;

/**
 * abstract class for a specific type of algorithms
 */

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    // ArrayList <AState> theSolution;
    String name;
    int numberOfNodes = 0;

    /**
     * @return name - get name of algorithm
     */

    public String getName() {
        return name;
    }

    /**
     * @return numberOfNodes get number of nodes we went threw to solve problem
     */

    public int getNumberOfNodesEvaluated() {
        return numberOfNodes;
    }

    /**
     * @return final solution
     */

    public Solution finalSolution(AState state) {
        Solution Sol = new Solution();
        Stack<AState> FSol = new Stack<AState>();
        if (state != null) {
            while (state.cameFrom != null) {
                FSol.push(state);
                state = state.cameFrom;
            }
            FSol.push(state);
        }
        while (FSol.size() != 0) {
            Sol.addState(FSol.pop());
        }
        return Sol;
    }
}