package apr.optimization.functions.constraints;

import apr.linear.vector.IVector;
import apr.optimization.functions.IMultivariateFunction;

public final class EqualityConstraint extends ImplicitConstraint {

    public EqualityConstraint(IMultivariateFunction f) {
        super(f);
    }

    @Override
    public boolean test(IVector x) {
        return f.valueAt(x) == 0.;
    }
}
