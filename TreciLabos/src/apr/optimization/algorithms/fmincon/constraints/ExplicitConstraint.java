package apr.optimization.algorithms.fmincon.constraints;

import apr.linear.vector.IVector;

public record ExplicitConstraint(double lowerBound, double upperBound) implements IConstraint {

    @Override
    public boolean test(IVector x) {
        return x.allMatch(xi -> xi >= lowerBound && xi <= upperBound);
    }
}
