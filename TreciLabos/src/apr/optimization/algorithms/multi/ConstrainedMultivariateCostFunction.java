package apr.optimization.algorithms.multi;

import apr.functions.constraints.EqualityConstraint;
import apr.functions.constraints.InequalityConstraint;
import apr.linear.vector.IVector;
import apr.functions.IConstrainedMultivariateFunction;

import java.util.Objects;

/**
 * Implementation of the {@code IMultivariableCostFunction} interface used for constrained optimization problems
 */
public class ConstrainedMultivariateCostFunction implements IMultivariateCostFunction {

    protected final IConstrainedMultivariateFunction function;
    protected int functionEvalCounter;

    public ConstrainedMultivariateCostFunction(IConstrainedMultivariateFunction function) {
        this.function = Objects.requireNonNull(function);
    }

    public double getCoefficient() {
        return function.getCoefficient();
    }

    public void setCoefficient(double coefficient) {
        function.setCoefficient(coefficient);
    }

    public InequalityConstraint[] getInequalityConstraints() {
        return function.getInequalityConstraints();
    }

    public EqualityConstraint[] getEqualityConstraints() {
        return function.getEqualityConstraints();
    }

    @Override
    public int getFunctionEvaluationCount() {
        return functionEvalCounter;
    }

    @Override
    public void reset() {
        functionEvalCounter = 0;
    }

    @Override
    public double valueAt(IVector x) {
        functionEvalCounter++;
        return function.valueAt(x);
    }
}
