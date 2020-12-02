package apr.functions.constraints;

import apr.linear.vector.IVector;
import apr.functions.IMultivariateFunction;

/**
 * Implementation of the {@code IConstraint} interface for constraints in the form of f(x) == 0
 */
public final class EqualityConstraint extends ImplicitConstraint {

    public EqualityConstraint(IMultivariateFunction function) {
        super(function);
    }

    @Override
    public boolean test(IVector x) {
        return function.valueAt(x) == 0.;
    }
}
