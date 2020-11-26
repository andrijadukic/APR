package apr.optimization.algorithms.multi;

import apr.optimization.functions.IMultivariateFunction;

/**
 * Represents a multivariable cost function with a counter feature
 */
public interface IMultivariateCostFunction extends IMultivariateFunction {

    int getFunctionEvaluationCount();

    void reset();
}
