package apr.optimization.algorithms.multi.gradient;

import apr.linear.vector.IVector;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.*;

public class GradientDescent extends AbstractDifferentiableMultivariateOptimizer {

    public GradientDescent(IDifferentiableMultivariateCostFunction f) {
        super(f);
    }

    public GradientDescent(IDifferentiableMultivariateCostFunction f, double epsilon, int maxIter, boolean computeOptimalStep) {
        super(f, epsilon, maxIter, computeOptimalStep);
    }

    @Override
    protected IVector computeDirection(IVector x, IVector gradient) {
        return multiply(gradient, -1., IMMUTABLE);
    }

    @Override
    public String getName() {
        return "Gradient descent";
    }
}
