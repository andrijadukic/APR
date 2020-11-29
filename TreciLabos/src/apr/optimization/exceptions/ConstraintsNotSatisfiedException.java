package apr.optimization.exceptions;

/**
 * Exception class used when constraints are not met
 */
public class ConstraintsNotSatisfiedException extends RuntimeException {

    public ConstraintsNotSatisfiedException() {
        super("Constraints were not met");
    }
}
