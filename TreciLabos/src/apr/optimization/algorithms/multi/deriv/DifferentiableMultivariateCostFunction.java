package apr.optimization.algorithms.multi.deriv;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.MultivariateCostFunction;
import apr.optimization.functions.IMultivariableMatrixFunction;
import apr.optimization.functions.IMultivariableVectorFunction;
import apr.optimization.functions.IMultivariateFunction;

import java.util.Objects;

/**
 * Implementation of {@code IDifferentiableMultivariableCostFunction} interface
 */
public class DifferentiableMultivariateCostFunction extends MultivariateCostFunction implements IDifferentiableMultivariateCostFunction {

    protected final IMultivariableVectorFunction gradient;
    protected final IMultivariableMatrixFunction hessian;

    protected int gradientEvalCounter;
    protected int hessianEvalCounter;

    public DifferentiableMultivariateCostFunction(IMultivariateFunction function, IMultivariableVectorFunction gradient, IMultivariableMatrixFunction hessian) {
        super(function);
        this.gradient = Objects.requireNonNull(gradient);
        this.hessian = Objects.requireNonNull(hessian);
    }

    @Override
    public int getGradientEvaluationCount() {
        return gradientEvalCounter;
    }

    @Override
    public int getHessianEvaluationCount() {
        return hessianEvalCounter;
    }

    @Override
    public void reset() {
        super.reset();
        gradientEvalCounter = hessianEvalCounter = 0;
    }

    @Override
    public IVector gradient(IVector x) {
        gradientEvalCounter++;
        return gradient.valueAt(x);
    }

    @Override
    public IMatrix hessian(IVector x) {
        hessianEvalCounter++;
        return hessian.valueAt(x);
    }
}
