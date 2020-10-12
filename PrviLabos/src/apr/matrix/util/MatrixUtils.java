package apr.matrix.util;

import apr.matrix.RealMatrix;
import apr.matrix.Vector;
import apr.matrix.exceptions.DimensionMismatchException;
import apr.matrix.exceptions.MatrixDimensionMismatchException;

public class MatrixUtils {

    public static final double EPSILON = 1e-9;

    public static void checkDimensionsSame(RealMatrix m1, RealMatrix m2) {
        int r1 = m1.getRowDimension();
        int c1 = m1.getColumnDimension();
        int r2 = m2.getRowDimension();
        int c2 = m2.getColumnDimension();

        if (!(r1 == r2 && c1 == c2)) throw new MatrixDimensionMismatchException(r1, c1, r2, c2);
    }

    public static void checkDimensionsSame(Vector v1, Vector v2) {
        int d1 = v1.getDimension();
        int d2 = v2.getDimension();

        if (d1 == d2) throw new DimensionMismatchException(d1, d2);
    }

    public static void checkAdditionApplicable(RealMatrix m1, RealMatrix m2) {
        checkDimensionsSame(m1, m2);
    }

    public static void checkAdditionApplicable(Vector v1, Vector v2) {
        checkDimensionsSame(v1, v2);
    }

    public static void checkMultiplicationApplicable(RealMatrix m1, RealMatrix m2) {
        int columnDimension = m1.getColumnDimension();
        int rowDimension = m2.getRowDimension();

        if (columnDimension != rowDimension) throw new DimensionMismatchException(columnDimension, rowDimension);
    }

    public static void checkMultiplicationApplicable(RealMatrix matrix, Vector vector) {
        int columnDimension = matrix.getColumnDimension();
        int vectorDimension = vector.getDimension();

        if (columnDimension != vectorDimension) throw new DimensionMismatchException(columnDimension, vectorDimension);
    }

    public static void checkMultiplicationApplicable(Vector v1, Vector v2) {
        checkDimensionsSame(v1, v2);
    }
}
