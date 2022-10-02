package apr.optimization.functions;

import apr.linear.vector.IVector;

/**
 * Implementation of the {@code IMultivariableCostFunction} interface
 */
public class MultivariableCostFunction implements IMultivariableCostFunction {

    protected final IMultivariableFunction function;
    protected int counter;

    public MultivariableCostFunction(IMultivariableFunction function) {
        this.function = function;
    }

    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public void reset() {
        counter = 0;
    }

    @Override
    public double valueAt(IVector x) {
        counter++;
        return function.valueAt(x);
    }
}
