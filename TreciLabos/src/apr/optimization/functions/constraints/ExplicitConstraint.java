package apr.optimization.functions.constraints;

import apr.optimization.algorithms.uni.Interval;

public record ExplicitConstraint(double lowerbound, double upperbound) {

    public ExplicitConstraint(Interval interval) {
        this(interval.start(), interval.end());
    }

    public boolean test(double x) {
        return x >= lowerbound && x <= upperbound;
    }
}
