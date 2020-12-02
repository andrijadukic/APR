package apr.functions.constraints;

import apr.linear.exceptions.DimensionMismatchException;
import apr.linear.vector.IVector;
import apr.optimization.algorithms.util.Interval;
import apr.functions.IMultivariateFunction;

import java.util.Objects;

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
        return new EqualityConstraint(Objects.requireNonNull(function));
    }

    /**
     * Creates new inequality constraint in form of f(x) >= 0
     *
     * @param function constraint function
     * @return new inequality constraint
     */
    public static InequalityConstraint inequality(IMultivariateFunction function) {
        return new InequalityConstraint(Objects.requireNonNull(function));
    }

    /**
     * Tests a number of constraints on a given point
     *
     * @param x           point
     * @param constraints constraints
     * @return true if point fits all constraints, false otherwise
     */
    public static boolean test(IVector x, IConstraint... constraints) {
        for (IConstraint constraint : Objects.requireNonNull(constraints)) {
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
        Objects.requireNonNull(constraints);

        int n = x.getDimension();
        int numConstraints = constraints.length;

        if (n != numConstraints) throw new DimensionMismatchException(n, numConstraints);

        for (int i = 0; i < n; i++) {
            if (!constraints[i].test(x.get(i))) return false;
        }
        return true;
    }
}