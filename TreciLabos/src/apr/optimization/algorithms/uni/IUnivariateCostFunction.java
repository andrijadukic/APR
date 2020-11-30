package apr.optimization.algorithms.uni;

import apr.functions.IUnivariateFunction;

/**
 * Represents a single cost function with a counter feature
 */
public interface IUnivariateCostFunction extends IUnivariateFunction {

    int getFunctionEvaluationCount();

    void reset();
}
