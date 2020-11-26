package apr.optimization.algorithms.fminunc;

import apr.linear.util.linalg.LinearAlgebra;
import apr.linear.util.linalg.OperationMutability;
import apr.linear.vector.IVector;
import apr.optimization.functions.IDifferentiableMultivariateCostFunction;

public class GradientDescent extends AbstractDifferentiableMultivariateOptimizer {

    public GradientDescent(IDifferentiableMultivariateCostFunction f) {
        super(f);
    }

    public GradientDescent(IDifferentiableMultivariateCostFunction f, double epsilon, int maxIter, boolean computeOptimalStep) {
        super(f, epsilon, maxIter, computeOptimalStep);
    }

    @Override
    protected IVector computeDirection(IVector x, IVector gradient) {
        return LinearAlgebra.multiply(gradient, -1., OperationMutability.IMMUTABLE);
    }

    @Override
    public String getName() {
        return "Gradient descent";
    }
}
