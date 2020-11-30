package apr.optimization.algorithms.multi.deriv;

import apr.linear.decompose.LUPDecomposer;
import apr.linear.vector.IVector;

import static apr.linear.util.linalg.LinearAlgebra.multiply;
import static apr.linear.util.linalg.OperationMutability.IMMUTABLE;

/**
 * Implementation of the Newton-Raphson algorithm
 */
public final class NewtonRaphson extends AbstractDifferentiableMultivariateOptimizer {

    public NewtonRaphson(IDifferentiableMultivariateCostFunction function) {
        super(function);
    }

    public NewtonRaphson(IDifferentiableMultivariateCostFunction function, double epsilon, int maxIter, boolean computeOptimalStep) {
        super(function, epsilon, maxIter, computeOptimalStep);
    }

    @Override
    protected IVector computeDirection(IVector x, IVector gradient) {
        return new LUPDecomposer(function.hessian(x)).solver().solve(multiply(gradient, -1, IMMUTABLE));
    }

    @Override
    public String getName() {
        return "Newton Raphson";
    }

}
