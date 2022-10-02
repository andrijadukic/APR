package apr.optimization.algorithms.multi;

import apr.linear.vector.Vector;
import apr.functions.MultivariateFunction;

import java.util.Objects;

/**
 * Implementation of the {@code IMultivariableCostFunction} interface
 */
public class MultivariateCostFunction implements MultivariateFunction {

    protected final MultivariateFunction function;
    protected int functionEvalCounter;

    public MultivariateCostFunction(MultivariateFunction function) {
        this.function = Objects.requireNonNull(function);
    }

    public int getFunctionEvaluationCount() {
        return functionEvalCounter;
    }

    public void reset() {
        functionEvalCounter = 0;
    }

    @Override
    public double valueAt(Vector x) {
        functionEvalCounter++;
        return function.valueAt(x);
    }
}
