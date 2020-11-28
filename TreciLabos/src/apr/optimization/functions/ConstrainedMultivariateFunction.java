package apr.optimization.functions;

import apr.linear.vector.IVector;
import apr.optimization.functions.constraints.EqualityConstraint;
import apr.optimization.functions.constraints.InequalityConstraint;

public class ConstrainedMultivariateFunction implements IConstrainedMultivariateFunction {

    private final IMultivariateFunction unconstrainedFunction;
    private EqualityConstraint[] equalityConstraints;
    private InequalityConstraint[] inequalityConstraints;

    private double coefficient;

    private static final double DEFAULT_COEFFICIENT = 1.;

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction, InequalityConstraint[] inequalityConstraints, double coefficient) {
        this.unconstrainedFunction = unconstrainedFunction;
        this.inequalityConstraints = inequalityConstraints;
        this.coefficient = coefficient;
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction, EqualityConstraint[] equalityConstraints, double coefficient) {
        this.unconstrainedFunction = unconstrainedFunction;
        this.equalityConstraints = equalityConstraints;
        this.coefficient = coefficient;
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction,
                                           EqualityConstraint[] equalityConstraints, InequalityConstraint[] inequalityConstraints1,
                                           double coefficient) {
        this.unconstrainedFunction = unconstrainedFunction;
        this.equalityConstraints = equalityConstraints;
        this.inequalityConstraints = inequalityConstraints1;
        this.coefficient = coefficient;
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction, InequalityConstraint[] inequalityConstraints) {
        this(unconstrainedFunction, inequalityConstraints, DEFAULT_COEFFICIENT);
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction, EqualityConstraint[] equalityConstraints) {
        this(unconstrainedFunction, equalityConstraints, DEFAULT_COEFFICIENT);
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction,
                                           EqualityConstraint[] equalityConstraints, InequalityConstraint[] inequalityConstraints) {
        this(unconstrainedFunction, equalityConstraints, inequalityConstraints, DEFAULT_COEFFICIENT);
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
        return unconstrainedFunction.valueAt(x) -
                (1. / coefficient) * inequalityConstraintsPenalty(x, inequalityConstraints) +
                coefficient * equalityConstraintsPenalty(x, equalityConstraints);
    }

    private static double inequalityConstraintsPenalty(IVector x, InequalityConstraint[] inequalityConstraints) {
        double penalty = 0.;
        for (InequalityConstraint constraint : inequalityConstraints) {
            double constraintFunctionValue = constraint.getFunction().valueAt(x);

            if (constraintFunctionValue < 0) return Double.POSITIVE_INFINITY;

            penalty += Math.log(constraintFunctionValue);

        }
        return penalty;
    }

    private static double equalityConstraintsPenalty(IVector x, EqualityConstraint[] equalityConstraints) {
        double penalty = 0.;
        for (EqualityConstraint constraint : equalityConstraints) {
            double constraintFunctionValue = constraint.getFunction().valueAt(x);
            penalty += constraintFunctionValue * constraintFunctionValue;
        }
        return penalty;
    }
}
