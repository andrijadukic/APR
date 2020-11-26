package apr.optimization.functions.constraints;

import apr.linear.vector.IVector;

@FunctionalInterface
public interface IConstraint {

    boolean test(IVector x);

    default IConstraint and(IConstraint next) {
        return x -> test(x) && next.test(x);
    }

    default IConstraint or(IConstraint next) {
        return x -> test(x) || next.test(x);
    }

    default IConstraint not() {
        return x -> !test(x);
    }
}
