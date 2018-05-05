package algorithms.search;

import java.util.ArrayList;

/**
 * interface to control different kind of problems in a general way
 */

public interface ISearchable {
    AState getStartState();

    AState getGoalState();

    void setGoalState(AState x);

    ArrayList<AState> getAllPossibleStates(AState s);

    boolean isVisited(AState visit);

    void changeVisitTrue(AState visit);

    void ResetVisit();
}