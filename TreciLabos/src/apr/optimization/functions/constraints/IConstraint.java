package apr.optimization.functions.constraints;

import apr.linear.vector.IVector;

/**
 * Represents a constraint on a function
 */
@FunctionalInterface
public interface IConstraint {

    /**
     * Tests if given point fits this constraint
     *
     * @param x point
     * @return true if point fits constraint, false otherwise
     */
    boolean test(IVector x);

    /**
     * Combines two constraints with logical and operator
     *
     * @param next constraint to be added to this constraint
     * @return new constraint
     */
    default IConstraint and(IConstraint next) {
        return x -> test(x) && next.test(x);
    }

    /**
     * Combines two constraints with logical or operator
     *
     * @param next constraint to be added to this constraint
     * @return new constraint
     */
    default IConstraint or(IConstraint next) {
        return x -> test(x) || next.test(x);
    }

    /**
     * Creates an inverse constraint (returns true if orginal constraint returned false and vice versa)
     *
     * @return new constraint
     */
    default IConstraint not() {
        return x -> !test(x);
    }
}
