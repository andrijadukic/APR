package apr.optimization.algorithms.multi;

import apr.functions.ConstrainedMultivariateFunction;
import apr.functions.constraints.EqualityConstraint;
import apr.functions.constraints.InequalityConstraint;

/**
 * Implementation of the {@code IMultivariableCostFunction} interface used for constrained optimization problems
 */
public class ConstrainedMultivariateCostFunction extends MultivariateCostFunction {

    protected final ConstrainedMultivariateFunction constrainedFunction;

    public ConstrainedMultivariateCostFunction(ConstrainedMultivariateFunction constrainedFunction) {
        super(constrainedFunction);
        this.constrainedFunction = constrainedFunction;
    }

    public double getCoefficient() {
        return constrainedFunction.getCoefficient();
    }

    public void setCoefficient(double coefficient) {
        constrainedFunction.setCoefficient(coefficient);
    }

    public InequalityConstraint[] getInequalityConstraints() {
        return constrainedFunction.getInequalityConstraints();
    }

    public EqualityConstraint[] getEqualityConstraints() {
        return constrainedFunction.getEqualityConstraints();
    }
}
