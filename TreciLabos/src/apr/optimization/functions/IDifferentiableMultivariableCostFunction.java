package apr.optimization.functions;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;

public interface IDifferentiableMultivariableCostFunction extends IMultivariableCostFunction {

    IVector gradient(IVector x);

    IMatrix hessian(IVector x);

    int getGradientEvaluationCount();

    int getHessianEvaluationCount();
}
