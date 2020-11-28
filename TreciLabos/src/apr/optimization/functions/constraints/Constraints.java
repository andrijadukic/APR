package apr.optimization.functions.constraints;

import apr.linear.exceptions.DimensionMismatchException;
import apr.linear.vector.IVector;
import apr.optimization.algorithms.uni.Interval;
import apr.optimization.functions.IMultivariateFunction;

import java.util.Arrays;

public class Constraints {

    public static ExplicitConstraint[] explicit(Interval... bounds) {
        return Arrays.stream(bounds)
                .map(ExplicitConstraint::new)
                .toArray(ExplicitConstraint[]::new);
    }

    public static ImplicitConstraint equality(IMultivariateFunction f) {
        return new EqualityConstraint(f);
    }

    public static ImplicitConstraint inequality(IMultivariateFunction f) {
        return new InequalityConstraint(f);
    }

    public static boolean test(IVector x, IConstraint... constraints) {
        for (IConstraint constraint : constraints) {
            if (!constraint.test(x)) return false;
        }
        return true;
    }

    public static boolean test(IVector x, ExplicitConstraint... constraints) {
        int n = x.getDimension();
        int numConstraints = constraints.length;

        if (n != numConstraints) throw new DimensionMismatchException(n,numConstraints);

        for (int i = 0; i < n; i++) {
            if (!constraints[i].test(x.get(i))) return false;
        }
        return true;
    }
}