package apr.optimization.algorithms;

import apr.linear.vector.IVector;

public interface IOptimizationAlgorithm {

    IVector search(IVector x0);

    String getName();
}
