package apr.optimization.algorithms.fminunc;

import apr.linear.vector.IVector;
import apr.optimization.functions.IDifferentiableMultivariableCostFunction;

public class GradientDescent extends AbstractDifferentiableMultivariableOptimizationAlgorithm {

    public GradientDescent(IDifferentiableMultivariableCostFunction f) {
        super(f);
    }

    public GradientDescent(IDifferentiableMultivariableCostFunction f, double epsilon, int maxIter, boolean computeOptimalStep) {
        super(f, epsilon, maxIter, computeOptimalStep);
    }

    @Override
    protected IVector computeDirection(IVector x, IVector gradient) {
        return gradient;
    }

    @Override
    public String getName() {
        return "Gradient descent";
    }
}
