package apr.optimization.exceptions;

public class ImplicitConstraintsNotMetException extends ConstraintsNotMetException {

    public ImplicitConstraintsNotMetException() {
        super("Implicit constraints were not met");
    }
}
