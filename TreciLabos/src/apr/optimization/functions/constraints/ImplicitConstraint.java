package apr.optimization.functions.constraints;

import apr.optimization.functions.IMultivariateFunction;

/**
 * Abstract class representing equality or inequality constraints
 */
public abstract class ImplicitConstraint implements IConstraint {

    protected final IMultivariateFunction f;

    public ImplicitConstraint(IMultivariateFunction f) {
        this.f = f;
    }

    public IMultivariateFunction getFunction() {
        return f;
    }
}
