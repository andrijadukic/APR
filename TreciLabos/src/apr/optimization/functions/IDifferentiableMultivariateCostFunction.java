package apr.optimization.functions;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;

public interface IDifferentiableMultivariateCostFunction extends IMultivariateCostFunction {

    IVector gradient(IVector x);

    IMatrix hessian(IVector x);

    int getGradientEvaluationCount();

    int getHessianEvaluationCount();
}
