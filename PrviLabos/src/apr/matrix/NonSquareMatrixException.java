package apr.matrix;

public class NonSquareMatrixException extends RuntimeException {

    public NonSquareMatrixException(int rowDimension, int columnDimension) {
        super("Matrix of dimensions " + rowDimension + "x" + columnDimension + " is not a square matrix!");
    }
}
