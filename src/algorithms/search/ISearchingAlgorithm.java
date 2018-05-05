package algorithms.search;

/**
 * Interface to solve problem with different algorithms.
 */

public interface ISearchingAlgorithm {

    Solution solve(ISearchable domain);

    String getName();

    int getNumberOfNodesEvaluated();

    Solution finalSolution(AState state);
}