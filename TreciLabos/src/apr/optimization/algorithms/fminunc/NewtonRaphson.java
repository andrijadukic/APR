package apr.optimization.algorithms.fminunc;

import apr.linear.decompose.LUPDecomposer;
import apr.linear.vector.IVector;
import apr.optimization.functions.IDifferentiableMultivariableCostFunction;

import static apr.linear.util.linalg.LinearAlgebra.multiply;
import static apr.linear.util.linalg.OperationMutability.MUTABLE;

public class NewtonRaphson extends AbstractDifferentiableMultivariableOptimizationAlgorithm {

    public NewtonRaphson(IDifferentiableMultivariableCostFunction f) {
        super(f);
    }

    public NewtonRaphson(IDifferentiableMultivariableCostFunction f, double epsilon, int maxIter, boolean computeOptimalStep) {
        super(f, epsilon, maxIter, computeOptimalStep);
    }

    @Override
    protected IVector computeDirection(IVector x, IVector gradient) {
        return new LUPDecomposer(f.hessian(x)).solver().solve(multiply(gradient, -1, MUTABLE));
    }

    @Override
    public String getName() {
        return "Newton Raphson";
    }

}
