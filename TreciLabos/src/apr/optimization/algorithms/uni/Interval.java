package apr.optimization.algorithms.uni;

import java.security.InvalidParameterException;

/**
 * Record class for storing intervals
 */
public record Interval(double lowerbound, double upperbound) {

    public Interval {
        if (lowerbound > upperbound) throw new InvalidParameterException();
    }

    @Override
    public String toString() {
        return "[" + lowerbound + ", " + upperbound + "]";
    }
}
