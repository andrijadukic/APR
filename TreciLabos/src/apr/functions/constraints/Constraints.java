package apr.functions.constraints;

import apr.linear.exceptions.DimensionMismatchException;
import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.MultivariateCostFunction;
import apr.optimization.algorithms.util.Interval;
import apr.functions.IMultivariateFunction;

/**
 * Utility class for creating and testing function constraints
 */
public class Constraints {

    /**
     * Creates new explicit constraint
     *
     * @param lb lowerbound, inclusive
     * @param ub upperbound, inclusive
     * @return new explicit constraint
     */
    public static ExplicitConstraint explicit(double lb, double ub) {
        return new ExplicitConstraint(new Interval(lb, ub));
    }

    /**
     * Creates new equality constraint in form of f(x) == 0
     *
     * @param function constraint function
     * @return new equality constraint
     */
    public static EqualityConstraint equality(IMultivariateFunction function) {
        return new EqualityConstraint(function);
    }

    /**
     * Creates new inequality constraint in form of f(x) >= 0
     *
     * @param function constraint function
     * @return new inequality constraint
     */
    public static InequalityConstraint inequality(IMultivariateFunction function) {
        return new InequalityConstraint(function);
    }

    /**
     * Tests a number of constraints on a given point
     *
     * @param x           point
     * @param constraints constraints
     * @return true if point fits all constraints, false otherwise
     */
    public static boolean test(IVector x, IConstraint... constraints) {
        for (IConstraint constraint : constraints) {
            if (!constraint.test(x)) return false;
        }
        return true;
    }

    /**
     * Tests a number of explicit constraints on a given point
     *
     * @param x           point
     * @param constraints constraints
     * @return true if point fits all constraints, false otherwise
     */
    public static boolean test(IVector x, ExplicitConstraint... constraints) {
        int n = x.getDimension();
        int numConstraints = constraints.length;

        if (n != numConstraints) throw new DimensionMismatchException(n, numConstraints);

        for (int i = 0; i < n; i++) {
            if (!constraints[i].test(x.get(i))) return false;
        }
        return true;
    }

    public static IMultivariateFunction sum(InequalityConstraint[] inequalityConstraints) {
        return new MultivariateCostFunction(x -> {
            double penalty = 0.;
            for (InequalityConstraint constraint : inequalityConstraints) {
                double constraintValue = constraint.getFunction().valueAt(x);
                if (constraintValue < 0) {
                    penalty -= constraintValue;
                }
            }
            return penalty;
        });
    }

    public static double penalty(IVector x, EqualityConstraint[] equalityConstraints, double coefficient) {
        double penalty = 0.;
        for (EqualityConstraint constraint : equalityConstraints) {
            double constraintFunctionValue = constraint.getFunction().valueAt(x);
            penalty += coefficient * constraintFunctionValue * constraintFunctionValue;
        }
        return penalty;
    }

    public static double barrier(IVector x, InequalityConstraint[] inequalityConstraints, double coefficient) {
        double penalty = 0.;
        for (InequalityConstraint constraint : inequalityConstraints) {
            double constraintFunctionValue = constraint.getFunction().valueAt(x);

            if (constraintFunctionValue <= 0) return Double.NEGATIVE_INFINITY;

            penalty += coefficient * Math.log(constraintFunctionValue);

        }
        return penalty;
    }
}