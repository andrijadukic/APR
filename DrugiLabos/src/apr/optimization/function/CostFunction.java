package apr.optimization.function;

import apr.linear.vector.IVector;

import java.util.HashMap;
import java.util.Map;

public class CostFunction implements ICostFunction {

    protected final IFunction function;

    protected final Map<String, Double> values;

    protected long counter;

    protected IVector x0;
    protected IVector xMin;

    public CostFunction(IVector x0, IVector xMin, IFunction function) {
        this.function = function;
        values = new HashMap<>();
        this.x0 = x0;
        this.xMin = xMin;
    }

    @Override
    public long getCounter() {
        return counter;
    }

    @Override
    public void setX0(IVector x0) {
        this.x0 = x0;
    }

    @Override
    public void setXMin(IVector xMin) {
        this.xMin = xMin;
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
