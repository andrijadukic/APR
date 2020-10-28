package apr.optimization.algorithms.fminbnd;

/**
 * Interface defining a single variable function minimization algorithm
 */
public interface ISingleVariableOptimizationAlgorithm {

    /**
     * Computes the minimum of function from a starting point
     *
     * @param x0 starting point
     * @return argmin
     */
    double search(double x0);

    /**
     * Gets the name of this algorithm
     *
     * @return name of algorithm
     */
    String getName();
}
