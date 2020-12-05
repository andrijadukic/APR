package apr.optimization.algorithms.multi.deriv;

import apr.linear.vector.Vector;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.*;

/**
 * Implementation of the gradient descent algorithm
 */
public final class GradientDescent extends AbstractDifferentiableMultivariateOptimizer {

    public GradientDescent(DifferentiableMultivariateCostFunction function) {
        super(function);
    }

    public GradientDescent(DifferentiableMultivariateCostFunction function, double epsilon, int maxIter, boolean computeOptimalStep) {
        super(function, epsilon, maxIter, computeOptimalStep);
    }

    @Override
    protected Vector computeDirection(Vector x, Vector gradient) {
        return multiply(gradient, -1., IMMUTABLE);
    }

    @Override
    public String getName() {
        return "Gradient descent";
    }
}
