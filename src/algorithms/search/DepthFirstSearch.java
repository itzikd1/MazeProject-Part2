package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {

    /**
     * constructor
     */

    public DepthFirstSearch() {
        super(); //get all info from aSearch
        this.name = "DepthFirstSearch"; //set name to DFS
        this.numberOfNodes=0;
    }

    /**
     * Solve with DFS
     *
     * @param domain - get a searchable state and solve it
     * @return - solution to domain problem
     */

    public Solution solve(ISearchable domain) {
        //reset all visited states
        if (domain == null)
            return null;
        domain.ResetVisit();
        Stack<AState> checkPath = new Stack<AState>(); // create a new stack
        checkPath.add(domain.getStartState()); // add the 1st state to queue
        ArrayList<AState> allN;//TODO FIX NAMES
        Solution Solu; //new solution
        while (checkPath.size() != 0) { //while we didn't go over all possible states
            AState temp = checkPath.pop(); //get a state from stack
            if (temp.equals(domain.getGoalState())) {
                domain.setGoalState(temp); //set end state
                Solu = finalSolution(temp); //function to add the path inside solu Solution
                domain.ResetVisit(); //reset visited fields
                return Solu; //return solution
            }
            allN = domain.getAllPossibleStates(temp); //get all possible states from current state
            for (int i = 0; i < allN.size(); i++) { //go over states
                if (!domain.isVisited(allN.get(i))) { //if state not visited
                    numberOfNodes++;
                    domain.changeVisitTrue(allN.get(i)); //change visit to true
                    allN.get(i).cameFrom = temp; //update where state came from
                    checkPath.push(allN.get(i)); //add state to stack
                }
                if (domain.getGoalState().equals(allN.get(i))) { //if this is end state
                    allN.get(i).cameFrom = temp; //update where it came from

                    Solu = finalSolution(allN.get(i)); //get solution
                    domain.ResetVisit(); //reset visited fields
                    return Solu; //return solution to user
                }
            }
        }
        return null;
    }
}

