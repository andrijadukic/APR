package apr.matrix;

public class SingularMatrixException extends RuntimeException{

    public SingularMatrixException() {
        super("Matrix is singular!");
    }
}
