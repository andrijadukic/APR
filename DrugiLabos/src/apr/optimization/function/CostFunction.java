package apr.optimization.function;

import apr.linear.vector.IVector;

import java.util.HashMap;
import java.util.Map;

public class CostFunction implements ICostFunction {

    protected final IFunction function;
    protected final Map<String, Double> values;
    protected long counter;

    public CostFunction(IFunction function) {
        this.function = function;
        values = new HashMap<>();
    }

    @Override
    public long getCounter() {
        return counter;
    }

    @Override
    public void increment() {
        counter++;
    }

    @Override
    public void reset() {
        counter = 0;
        values.clear();
    }

    @Override
    public double valueAt(IVector x) {
        increment();

        String key = x.toString();
        if (values.containsKey(key)) return values.get(key);

        double value = function.valueAt(x);
        values.put(key, value);
        return value;
    }
}
