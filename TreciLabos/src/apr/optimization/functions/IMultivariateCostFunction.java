package apr.optimization.functions;

/**
 * Represents a multivariable cost function with a counter feature
 */
public interface IMultivariateCostFunction extends IMultivariateFunction {

    int getFunctionEvaluationCount();

    void reset();
}
