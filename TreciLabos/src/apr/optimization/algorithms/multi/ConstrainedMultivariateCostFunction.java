package apr.optimization.algorithms.multi;

import apr.linear.vector.IVector;
import apr.optimization.functions.IConstrainedMultivariateFunction;

/**
 * Implementation of the {@code IMultivariableCostFunction} interface used for constrained optimization problems
 */
public class ConstrainedMultivariateCostFunction implements IMultivariateCostFunction {

    protected final IConstrainedMultivariateFunction function;
    protected int functionEvalCounter;

    public ConstrainedMultivariateCostFunction(IConstrainedMultivariateFunction function) {
        this.function = function;
    }

    public double getCoefficient() {
        return function.getCoefficient();
    }

    public void setCoefficient(double coefficient) {
        function.setCoefficient(coefficient);
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
