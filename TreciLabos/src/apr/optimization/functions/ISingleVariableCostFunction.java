package apr.optimization.functions;

/**
 * Represents a single cost function with a counter feature
 */
public interface ISingleVariableCostFunction extends ISingleVariableFunction {

    double gradient(double x);

    int getFunctionEvaluationCount();

    int getGradientEvaluationCount();

    void reset();
}
