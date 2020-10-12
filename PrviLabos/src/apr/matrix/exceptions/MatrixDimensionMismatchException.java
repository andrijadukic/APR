package apr.matrix.exceptions;

public class MatrixDimensionMismatchException extends RuntimeException {

    public MatrixDimensionMismatchException(int r1, int c1, int r2, int c2) {
        super("Expected dimensions: " + r1 + "x" + c1 + ", Received: " + r2 + "x" + c2);
    }
}
