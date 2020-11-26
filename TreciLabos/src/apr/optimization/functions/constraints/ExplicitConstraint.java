package apr.optimization.functions.constraints;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.uni.Interval;

public class ExplicitConstraint implements IConstraint {

    private final Interval[] bounds;

    public ExplicitConstraint(Interval... bounds) {
        this.bounds = bounds;
    }

    public Interval getBound(int index) {
        return bounds[index];
    }

    @Override
    public boolean test(IVector x) {
        int n = x.getDimension();

        if (n > bounds.length) throw new IllegalArgumentException();

        for (int i = 0; i < n; i++) {
            double xi = x.get(i);
            Interval bound = bounds[i];
            if (!(xi >= bound.lowerbound() && xi <= bound.upperbound())) return false;
        }

        return true;
    }
}
