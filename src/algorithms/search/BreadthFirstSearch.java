package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    /**
     * constructor
     */

    public BreadthFirstSearch() {
        super();
        this.name = "BreadthFirstSearch"; //name = BFS
        this.numberOfNodes = 0;

    }

    /**
     * Solve with BFS
     *
     * @param domain - get a searchable state and solve it
     * @return - solution to domain problem
     */

    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null)
            return null;
        domain.ResetVisit(); // set all visit to false
        Queue<AState> StepsGo = new LinkedList<AState>(); // new link list to keep steps
        StepsGo.add(domain.getStartState()); // add the 1st state to queue
        domain.changeVisitTrue(domain.getStartState());
        numberOfNodes++;
        Solution Solu; //new solution
        Solu = FindSol(StepsGo, domain);
        return Solu;
    }

    /**
     * FindSol function - to get solution
     *
     * @param StepsGo - get queue with start state and start going over it
     * @return - solution to domain problem
     */

    private Solution FindSol(Queue<AState> StepsGo, ISearchable domain) {
        if (StepsGo == null)
            return null;
        Solution Solu; //new solution
        ArrayList<AState> MyNeighbours;
        while (StepsGo.size() != 0) { //as long as there are steps to do
            AState temp = StepsGo.poll(); //get a state from queue
            if (domain.getGoalState().equals(temp)) { //if state equal to end state
                domain.setGoalState(temp); //set end state
                Solu = finalSolution(temp);
                domain.ResetVisit(); //reset visited fields
                return Solu; //return solution
            }
            MyNeighbours = domain.getAllPossibleStates(temp);
            for (int i = 0; i < MyNeighbours.size(); i++) {
                if (!domain.isVisited(MyNeighbours.get(i))) {// new state found
                    numberOfNodes++;
                    domain.changeVisitTrue(MyNeighbours.get(i));
                    MyNeighbours.get(i).cameFrom = temp; //updates its parent
                    StepsGo.add(MyNeighbours.get(i));
                }
                if (MyNeighbours.get(i).equals(domain.getGoalState())) {
                    MyNeighbours.get(i).cameFrom = temp;
                    //domain.setGoalState(allN.get(i)); //set end state
                    Solu = finalSolution(MyNeighbours.get(i)); //function to add the path inside solu Solution
                    domain.ResetVisit(); //reset visited fields
                    return Solu; //return solution
                }
            }
        }
        return null;
    }
}