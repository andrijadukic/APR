package apr.optimization.exceptions;

public class MaximumIterationCountExceededException extends RuntimeException {

    public MaximumIterationCountExceededException(int maxIter) {
        super("Maximum iteration count (" + maxIter + ") exceeded");
    }

    public MaximumIterationCountExceededException(int maxIter, String message) {
        super("Maximum iteration count (" + maxIter + ") exceeded, " + message);
    }
}
