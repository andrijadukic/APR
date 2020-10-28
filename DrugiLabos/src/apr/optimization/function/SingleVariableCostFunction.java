package apr.optimization.function;

/**
 * Class implementing ISingleVariableCostFunction interface
 */
public class SingleVariableCostFunction implements ISingleVariableCostFunction {

    protected final ISingleVariableFunction function;
    protected long counter;

    public SingleVariableCostFunction(ISingleVariableFunction function) {
        this.function = function;
    }

    @Override
    public long getCounter() {
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