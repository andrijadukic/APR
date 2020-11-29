package apr.optimization.functions.constraints;

import apr.linear.vector.IVector;
import apr.optimization.functions.IMultivariateFunction;

/**
 * Implementation of the {@code IConstraint} interface for constraints in the form of f(x) >= 0
 */
public final class InequalityConstraint extends ImplicitConstraint {

    public InequalityConstraint(IMultivariateFunction f) {
        super(f);
    }

    @Override
    public boolean test(IVector x) {
        return f.valueAt(x) >= 0.;
    }
}
