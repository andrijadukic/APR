package apr.optimization.functions;

import apr.linear.vector.IVector;
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
                - inequalityConstraintsPenalty(x, coefficient, inequalityConstraints)
                + equalityConstraintsPenalty(x, coefficient, equalityConstraints);
    }

    protected double inequalityConstraintsPenalty(IVector x, double coefficient, InequalityConstraint[] inequalityConstraints) {
        if (inequalityConstraints == null) return 0.;

        coefficient = 1. / coefficient;
        double penalty = 0.;
        for (InequalityConstraint constraint : inequalityConstraints) {
            double constraintFunctionValue = constraint.getFunction().valueAt(x);

            if (constraintFunctionValue <= 0) return Double.NEGATIVE_INFINITY;

            penalty += coefficient * Math.log(constraintFunctionValue);

        }
        return penalty;
    }

    protected double equalityConstraintsPenalty(IVector x, double coefficient, EqualityConstraint[] equalityConstraints) {
        if (equalityConstraints == null) return 0.;

        double penalty = 0.;
        for (EqualityConstraint constraint : equalityConstraints) {
            double constraintFunctionValue = constraint.getFunction().valueAt(x);
            penalty += coefficient * constraintFunctionValue * constraintFunctionValue;
        }
        return penalty;
    }
}
