package apr.optimization.functions.constraints;

import apr.optimization.functions.IMultivariateFunction;

public abstract class ImplicitConstraint implements IConstraint {

    protected final IMultivariateFunction f;

    public ImplicitConstraint(IMultivariateFunction f) {
        this.f = f;
    }
}
