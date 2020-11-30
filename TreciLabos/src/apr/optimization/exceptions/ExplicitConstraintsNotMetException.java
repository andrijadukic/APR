package apr.optimization.exceptions;

public class ExplicitConstraintsNotMetException extends ConstraintsNotMetException {

    public ExplicitConstraintsNotMetException() {
        super("Explicit constraints were not met");
    }
}
