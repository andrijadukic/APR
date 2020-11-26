package apr.optimization.algorithms.multi.gradient;

import apr.linear.decompose.LUPDecomposer;
import apr.linear.vector.IVector;
import apr.optimization.functions.IDifferentiableMultivariateCostFunction;

import static apr.linear.util.linalg.LinearAlgebra.multiply;
import static apr.linear.util.linalg.OperationMutability.IMMUTABLE;
import static apr.linear.util.linalg.OperationMutability.MUTABLE;

public class NewtonRaphson extends AbstractDifferentiableMultivariateOptimizer {

    public NewtonRaphson(IDifferentiableMultivariateCostFunction f) {
        super(f);
    }

    public NewtonRaphson(IDifferentiableMultivariateCostFunction f, double epsilon, int maxIter, boolean computeOptimalStep) {
        super(f, epsilon, maxIter, computeOptimalStep);
    }

    @Override
    protected IVector computeDirection(IVector x, IVector gradient) {
        return multiply(
                new LUPDecomposer(f.hessian(x)).solver().solve(multiply(gradient, -1, IMMUTABLE)),
                -1,
                MUTABLE);
    }

    @Override
    public String getName() {
        return "Newton Raphson";
    }

}
