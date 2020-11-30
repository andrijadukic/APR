package apr.functions.constraints;

import apr.functions.IMultivariateFunction;

import java.util.Objects;

/**
 * Abstract class representing equality or inequality constraints
 */
public abstract class ImplicitConstraint implements IConstraint {

    protected final IMultivariateFunction function;

    public ImplicitConstraint(IMultivariateFunction function) {
        this.function = Objects.requireNonNull(function);
    }

    public IMultivariateFunction getFunction() {
        return function;
    }
}
