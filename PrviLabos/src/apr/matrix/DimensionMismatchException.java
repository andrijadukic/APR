package apr.matrix;

public class DimensionMismatchException extends RuntimeException {

    public DimensionMismatchException(int real, int expected) {
        super("Expected dimension: " + expected + ", Received: " + real);
    }
}
