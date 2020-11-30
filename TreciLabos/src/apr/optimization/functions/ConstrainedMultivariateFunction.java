package apr.optimization.functions;

import apr.linear.vector.IVector;
import apr.optimization.functions.constraints.Constraints;
import apr.optimization.functions.constraints.EqualityConstraint;
import apr.optimization.functions.constraints.InequalityConstraint;

/**
 * Implementation of the {@code IConstrainedMultivariateFunction} interface
 */
public class ConstrainedMultivariateFunction implements IConstrainedMultivariateFunction {

    protected final IMultivariateFunction unconstrainedFunction;

    private EqualityConstraint[] equalityConstraints;
    private InequalityConstraint[] inequalityConstraints;

    private double coefficient = DEFAULT_COEFFICIENT;

    private static final double DEFAULT_COEFFICIENT = 1.;

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction, InequalityConstraint... inequalityConstraints) {
        this.unconstrainedFunction = unconstrainedFunction;
        this.inequalityConstraints = inequalityConstraints;
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction, EqualityConstraint... equalityConstraints) {
        this.unconstrainedFunction = unconstrainedFunction;
        this.equalityConstraints = equalityConstraints;
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction,
                                           EqualityConstraint[] equalityConstraints, InequalityConstraint[] inequalityConstraints) {
        this(unconstrainedFunction, equalityConstraints);
        this.inequalityConstraints = inequalityConstraints;
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction, InequalityConstraint[] inequalityConstraints, double coefficient) {
        this(unconstrainedFunction, inequalityConstraints);
        this.coefficient = coefficient;
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction, EqualityConstraint[] equalityConstraints, double coefficient) {
        this(unconstrainedFunction, equalityConstraints);
        this.coefficient = coefficient;
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction,
                                           EqualityConstraint[] equalityConstraints, InequalityConstraint[] inequalityConstraints,
                                           double coefficient) {
        this(unconstrainedFunction, equalityConstraints, inequalityConstraints);
        this.coefficient = coefficient;
    }

    @Override
    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public double valueAt(IVector x) {
        return unconstrainedFunction.valueAt(x)
                - (inequalityConstraints == null ? 0. : Constraints.barrier(x, inequalityConstraints, 1. / coefficient))
                + (equalityConstraints == null ? 0. : Constraints.penalty(x, equalityConstraints, coefficient));
    }
}
