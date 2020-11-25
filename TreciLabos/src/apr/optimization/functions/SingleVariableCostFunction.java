package apr.optimization.functions;

/**
 * Implementation of the {@code ISingleVariableCostFunction} interface
 */
public class SingleVariableCostFunction implements ISingleVariableCostFunction {

    protected final ISingleVariableFunction function;
    protected ISingleVariableFunction gradient;

    protected int functionEvalCounter;
    protected int gradientEvalCounter;

    public SingleVariableCostFunction(ISingleVariableFunction function) {
        this.function = function;
    }

    public SingleVariableCostFunction(ISingleVariableFunction function, ISingleVariableFunction gradient) {
        this(function);
        this.gradient = gradient;
    }

    @Override
    public int getFunctionEvaluationCount() {
        return functionEvalCounter;
    }

    public int getGradientEvaluationCount() {
        return gradientEvalCounter;
    }

    @Override
    public void reset() {
        gradientEvalCounter = functionEvalCounter = 0;
    }

    @Override
    public double valueAt(double x) {
        functionEvalCounter++;
        return function.valueAt(x);
    }

    @Override
    public double gradient(double x) {
        gradientEvalCounter++;
        return gradient.valueAt(x);
    }
}