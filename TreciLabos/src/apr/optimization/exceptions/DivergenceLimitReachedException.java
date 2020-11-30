package apr.optimization.exceptions;

/**
 * Exception class used when iteration count of a method exceeds the given limit
 */
public class DivergenceLimitReachedException extends RuntimeException {

    public DivergenceLimitReachedException(int maxIter) {
        super("Divergence limit reached (" + maxIter + ")");
    }

    public DivergenceLimitReachedException(int maxIter, String message) {
        super("Divergence limit reached (" + maxIter + "), " + message);
    }
}
