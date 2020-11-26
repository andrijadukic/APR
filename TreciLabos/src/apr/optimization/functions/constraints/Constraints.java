package apr.optimization.functions.constraints;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.fminbnd.Interval;
import apr.optimization.functions.IMultivariateFunction;

public class Constraints {

    public static ExplicitConstraint explicit(Interval... bounds) {
        return new ExplicitConstraint(bounds);
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
}