package apr.optimization.functions;

/**
 * Represents a multivariable cost function with a counter feature
 */
public interface IMultivariableCostFunction extends IMultivariableFunction {

    int getFunctionEvaluationCount();

    void reset();
}
