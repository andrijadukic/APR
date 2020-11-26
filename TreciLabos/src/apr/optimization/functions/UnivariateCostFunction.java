package apr.optimization.functions;

/**
 * Implementation of the {@code ISingleVariableCostFunction} interface
 */
public class UnivariateCostFunction implements IUnivariateCostFunction {

    protected final IUnivariateFunction function;
    protected IUnivariateFunction gradient;

    protected int functionEvalCounter;
    protected int gradientEvalCounter;

    public UnivariateCostFunction(IUnivariateFunction function) {
        this.function = function;
    }

    public UnivariateCostFunction(IUnivariateFunction function, IUnivariateFunction gradient) {
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