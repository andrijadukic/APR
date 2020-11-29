package apr.optimization.algorithms.uni;

import apr.optimization.functions.IUnivariateFunction;

import java.util.Objects;

/**
 * Implementation of the {@code ISingleVariableCostFunction} interface
 */
public class UnivariateCostFunction implements IUnivariateCostFunction {

    protected final IUnivariateFunction function;
    protected int functionEvalCounter;

    public UnivariateCostFunction(IUnivariateFunction function) {
        this.function = Objects.requireNonNull(function);
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
    public double valueAt(double x) {
        functionEvalCounter++;
        return function.valueAt(x);
    }
}