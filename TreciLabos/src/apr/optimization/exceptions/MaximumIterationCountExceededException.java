package apr.optimization.exceptions;

/**
 * Exception class used when iteration count of a method exceeds the given limit
 */
public class MaximumIterationCountExceededException extends RuntimeException {

    public MaximumIterationCountExceededException(int maxIter) {
        super("Maximum iteration count (" + maxIter + ") exceeded");
    }

    public MaximumIterationCountExceededException(int maxIter, String message) {
        super("Maximum iteration count (" + maxIter + ") exceeded, " + message);
    }
}
