package apr.optimization.function;

/**
 * Implementation of the {@code ISingleVariableCostFunction} interface
 */
public class SingleVariableCostFunction implements ISingleVariableCostFunction {

    protected final ISingleVariableFunction function;
    protected int counter;

    public SingleVariableCostFunction(ISingleVariableFunction function) {
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
    public double valueAt(double x) {
        counter++;
        return function.valueAt(x);
    }
}