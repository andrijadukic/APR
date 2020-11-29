package apr.optimization.algorithms.multi;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.uni.GoldenSectionSearch;
import apr.optimization.algorithms.uni.IUnivariateOptimizer;
import apr.optimization.functions.IMultivariateFunction;

import static apr.linear.util.linalg.LinearAlgebra.add;
import static apr.linear.util.linalg.LinearAlgebra.multiply;
import static apr.linear.util.linalg.OperationMutability.IMMUTABLE;
import static apr.linear.util.linalg.OperationMutability.MUTABLE;

/**
 * Implementation of the line search algorithm
 */
public class LineSearch implements IUnivariateOptimizer {

    private final IMultivariateFunction function;
    private final IVector x;
    private final IVector direction;

    public LineSearch(IMultivariateFunction function, IVector x, IVector direction) {
        this.function = function;
        this.x = x;
        this.direction = direction;
    }

    @Override
    public double search(double l0) {
        return new GoldenSectionSearch(
                lambda -> function.valueAt(add(multiply(direction, lambda, IMMUTABLE), x, MUTABLE)))
                .search(l0);
    }

    @Override
    public String getName() {
        return "Line search";
    }
}
