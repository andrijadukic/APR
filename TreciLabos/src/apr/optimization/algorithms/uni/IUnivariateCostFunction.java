package apr.optimization.algorithms.uni;

import apr.optimization.functions.IUnivariateFunction;

/**
 * Represents a single cost function with a counter feature
 */
public interface IUnivariateCostFunction extends IUnivariateFunction {

    double gradient(double x);

    int getFunctionEvaluationCount();

    int getGradientEvaluationCount();

    void reset();
}
