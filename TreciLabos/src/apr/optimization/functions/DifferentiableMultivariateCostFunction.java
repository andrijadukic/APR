package apr.optimization.functions;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.MultivariateCostFunction;

public class DifferentiableMultivariateCostFunction extends MultivariateCostFunction implements IDifferentiableMultivariateCostFunction {

    protected final IMultivariableVectorFunction gradient;
    protected final IMultivariableMatrixFunction hessian;

    protected int gradientEvalCounter;
    protected int hessianEvalCounter;

    public DifferentiableMultivariateCostFunction(IMultivariateFunction function, IMultivariableVectorFunction gradient, IMultivariableMatrixFunction hessian) {
        super(function);
        this.gradient = gradient;
        this.hessian = hessian;
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
        hessianEvalCounter = gradientEvalCounter = 0;
    }

    @Override
    public IVector gradient(IVector x) {
        gradientEvalCounter++;
        return gradient.valueAt(x);
    }

    @Override
    public IMatrix hessian(IVector x) {
        return hessian.valueAt(x);
    }
}
