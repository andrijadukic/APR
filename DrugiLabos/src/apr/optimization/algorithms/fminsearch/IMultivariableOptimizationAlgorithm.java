package apr.optimization.algorithms.fminsearch;

import apr.linear.vector.IVector;

public interface IMultivariableOptimizationAlgorithm {

    IVector search(IVector x0);

    String getName();
}
