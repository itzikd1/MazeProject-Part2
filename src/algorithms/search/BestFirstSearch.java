package algorithms.search;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class BestFirstSearch extends ASearchingAlgorithm {

    /**
     * constructor
     */

    public BestFirstSearch() {
        super();
        this.name = "BestFirstSearch";
        this.numberOfNodes = 0;

    }

    /**
     * Compare between to aState according to cost
     *
     * @param o1,o2 - objects to compare
     */

    private int compareA(AState o1, AState o2) {
        if (o1.getCost() > o2.getCost())
            return 1;
        if (o2.getCost() > o1.getCost())
            return -1;
        else
            return 0;

    }

    /**
     * Solve with Best Algorithm
     *
     * @param domain - get a searchable state and solve it
     * @return - solution to domain problem
     */

    @Override
    //TODO make sure comparator works
    public Solution solve(ISearchable domain) {
        if (domain == null)
            return null;
        domain.ResetVisit(); // set all visit to false
        PriorityQueue<AState> StepsGo = new PriorityQueue<AState>(this::compareA); // new link list to keep steps
        StepsGo.add(domain.getStartState()); // add the 1st state to queue
        domain.changeVisitTrue(domain.getStartState());
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

    private Solution FindSol(PriorityQueue<AState> StepsGo, ISearchable domain) {
        if (StepsGo == null)
            return null;
        Solution Solu; //new solution
        ArrayList<AState> MyNeighbours;
        while (StepsGo.size() != 0) { //as long as there are steps to do
            AState temp = StepsGo.poll(); //get a state from queue
            if (domain.getGoalState().equals(temp)) { //if state equal to end state
                domain.setGoalState(temp); //set end state
                Solu = finalSolution(domain.getGoalState());
                domain.ResetVisit(); //reset visited fields
                return Solu; //return solution
            }
            MyNeighbours = domain.getAllPossibleStates(temp);
            for (int i = 0; i < MyNeighbours.size(); i++) {
                if (!domain.isVisited(MyNeighbours.get(i))) {// new state found
                    MyNeighbours.get(i).cameFrom = temp; //updates its parent
                    numberOfNodes++;
                    if (MyNeighbours.get(i).equals(domain.getGoalState())) {
                        domain.setGoalState(MyNeighbours.get(i)); //set end state
                        Solu = finalSolution(domain.getGoalState()); //function to add the path inside solu Solution

                        domain.ResetVisit(); //reset visited fields
                        return Solu; //return solution
                    }
                    domain.changeVisitTrue(MyNeighbours.get(i));
                    StepsGo.add(MyNeighbours.get(i));

                }
            }
        }
        return null;
    }
}