package apr.optimization.util;

import java.security.InvalidParameterException;

public record Interval(double start, double end) {

    public Interval {
        if (start > end) throw new InvalidParameterException();
    }
}
