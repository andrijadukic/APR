package apr.optimization.algorithms.multi.deriv;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;

/**
 * Represents a differentiable {@code IMultivariableCostFunction} object
 */
public interface IDifferentiableMultivariateCostFunction extends IMultivariateCostFunction {

    IVector gradient(IVector x);

    IMatrix hessian(IVector x);

    int getGradientEvaluationCount();

    int getHessianEvaluationCount();
}
