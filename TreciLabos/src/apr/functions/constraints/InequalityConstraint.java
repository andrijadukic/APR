package apr.functions.constraints;

import apr.linear.vector.Vector;
import apr.functions.MultivariateFunction;

/**
 * Implementation of the {@code IConstraint} interface for constraints in the form of f(x) >= 0
 */
public final class InequalityConstraint extends ImplicitConstraint {

    public InequalityConstraint(MultivariateFunction function) {
        super(function);
    }

    @Override
    public boolean test(Vector x) {
        return function.valueAt(x) >= 0.;
    }
}
