package apr.linear.exceptions;

public class SingularMatrixException extends RuntimeException{

    public SingularMatrixException() {
        super("Matrix is singular!");
    }
}
