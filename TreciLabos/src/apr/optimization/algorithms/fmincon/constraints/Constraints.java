package apr.optimization.algorithms.fmincon.constraints;

import apr.linear.vector.IVector;
import apr.optimization.functions.IMultivariableFunction;

public class Constraints {

    public static ExplicitConstraint explicit(double lb, double ub) {
        return new ExplicitConstraint(lb, ub);
    }

    public static ImplicitConstraint implicit(IMultivariableFunction f) {
        return new ImplicitConstraint(f);
    }

    public static boolean test(IConstraint[] constraints, IVector x) {
        for (IConstraint constraint : constraints) {
            if (!constraint.test(x)) return false;
        }
        return true;
    }
}