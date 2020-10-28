package apr.optimization.function;

import apr.linear.vector.IVector;

import java.util.HashMap;
import java.util.Map;

/**
 * Class implementing ICostFunction interface, using a Map<String, Double> object to cache calculated values
 */
public class MultivariableCostFunction implements IMultivariableCostFunction {

    protected final IMultivariableFunction function;
    protected final Map<String, Double> values;
    protected long counter;

    public MultivariableCostFunction(IMultivariableFunction function) {
        this.function = function;
        values = new HashMap<>();
    }

    @Override
    public long getCounter() {
        return counter;
    }

    @Override
    public void reset() {
        counter = 0;
        values.clear();
    }

    @Override
    public double valueAt(IVector x) {
        String key = x.toString();
        if (values.containsKey(key)) return values.get(key);

        counter++;

        double value = function.valueAt(x);
        values.put(key, value);
        return value;
    }
}
