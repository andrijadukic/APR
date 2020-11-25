package apr.optimization.algorithms.fmincon.constraints;

import apr.linear.vector.IVector;
import apr.optimization.functions.IMultivariableFunction;

public record ImplicitConstraint(IMultivariableFunction f) implements IConstraint {

    @Override
    public boolean test(IVector x) {
        return f.valueAt(x) >= 0;
    }
}
