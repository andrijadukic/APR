package apr.functions;

import apr.linear.vector.IVector;
import apr.functions.constraints.EqualityConstraint;
import apr.functions.constraints.InequalityConstraint;

import java.util.Objects;

/**
 * Implementation of the {@code IConstrainedMultivariateFunction} interface
 */
public class ConstrainedMultivariateFunction implements IConstrainedMultivariateFunction {

    protected final IMultivariateFunction unconstrainedFunction;

    private final EqualityConstraint[] equalityConstraints;
    private final InequalityConstraint[] inequalityConstraints;

    private double coefficient;

    private static final double DEFAULT_COEFFICIENT = 1.;

    private static final EqualityConstraint[] EQUALITY_CONSTRAINTS_PLACEHOLDER = new EqualityConstraint[]{};
    private static final InequalityConstraint[] INEQUALITY_CONSTRAINTS_PLACEHOLDER = new InequalityConstraint[]{};

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction,
                                           EqualityConstraint[] equalityConstraints, InequalityConstraint[] inequalityConstraints,
                                           double coefficient) {
        this.unconstrainedFunction = Objects.requireNonNull(unconstrainedFunction);
        this.equalityConstraints = Objects.requireNonNull(equalityConstraints);
        this.inequalityConstraints = Objects.requireNonNull(inequalityConstraints);
        this.coefficient = coefficient;
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction,
                                           EqualityConstraint[] equalityConstraints, InequalityConstraint[] inequalityConstraints) {
        this(unconstrainedFunction, equalityConstraints, inequalityConstraints, DEFAULT_COEFFICIENT);
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction, InequalityConstraint... inequalityConstraints) {
        this(unconstrainedFunction, EQUALITY_CONSTRAINTS_PLACEHOLDER, inequalityConstraints);
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction, EqualityConstraint... equalityConstraints) {
        this(unconstrainedFunction, equalityConstraints, INEQUALITY_CONSTRAINTS_PLACEHOLDER);
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction, InequalityConstraint[] inequalityConstraints, double coefficient) {
        this(unconstrainedFunction, EQUALITY_CONSTRAINTS_PLACEHOLDER, inequalityConstraints, coefficient);
    }

    public ConstrainedMultivariateFunction(IMultivariateFunction unconstrainedFunction, EqualityConstraint[] equalityConstraints, double coefficient) {
        this(unconstrainedFunction, equalityConstraints, INEQUALITY_CONSTRAINTS_PLACEHOLDER, coefficient);
    }

    @Override
    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public EqualityConstraint[] getEqualityConstraints() {
        return equalityConstraints;
    }

    public InequalityConstraint[] getInequalityConstraints() {
        return inequalityConstraints;
    }

    @Override
    public double valueAt(IVector x) {
        double value = unconstrainedFunction.valueAt(x);

        double inequalityCoefficient = 1. / coefficient;
        for (InequalityConstraint constraint : inequalityConstraints) {
            double constraintFunctionValue = constraint.getFunction().valueAt(x);

            if (constraintFunctionValue <= 0) return Double.POSITIVE_INFINITY;

            value -= inequalityCoefficient * Math.log(constraintFunctionValue);
        }
        for (EqualityConstraint constraint : equalityConstraints) {
            double constraintFunctionValue = constraint.getFunction().valueAt(x);
            value += coefficient * constraintFunctionValue * constraintFunctionValue;
        }

        return value;
    }
}
