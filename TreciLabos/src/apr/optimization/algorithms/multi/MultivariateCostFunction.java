package apr.optimization.algorithms.multi;

import apr.linear.vector.IVector;
import apr.optimization.functions.IMultivariateFunction;

/**
 * Implementation of the {@code IMultivariableCostFunction} interface
 */
public class MultivariateCostFunction implements IMultivariateCostFunction {

    protected final IMultivariateFunction function;
    protected int functionEvalCounter;

    public MultivariateCostFunction(IMultivariateFunction function) {
        this.function = function;
    }

    @Override
    public int getFunctionEvaluationCount() {
        return functionEvalCounter;
    }

    @Override
    public void reset() {
        functionEvalCounter = 0;
    }

    @Override
    public double valueAt(IVector x) {
        functionEvalCounter++;
        return function.valueAt(x);
    }
}