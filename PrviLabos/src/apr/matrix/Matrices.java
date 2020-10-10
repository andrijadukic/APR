package apr.matrix;

import java.security.InvalidParameterException;
import java.util.Random;

public class Matrices {

    public static final double EPSILON = 1e-9;

    public static IMatrix blank(int rows, int columns) {
        return new Matrix(rows, columns, new double[rows][columns]);
    }

    public static IMatrix blankSquare(int dimension) {
        return blank(dimension, dimension);
    }

    public static IMatrix identity(int dimension) {
        return diagonal(dimension, 1.);
    }

    public static IMatrix diagonal(int dimension, double value) {
        double[][] array = new double[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            array[i][i] = value;
        }

        return new Matrix(dimension, dimension, array);
    }

    public static IMatrix random(int rows, int columns, Random random) {
        double[][] array = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[i][j] = random.nextDouble();
            }
        }

        return new Matrix(rows, columns, array);
    }

    public static IMatrix columnVectorsToMatrix(IVector[] vectors) {
        int columns = vectors.length;
        int rows = vectors[0].getDimension();
        for (int i = 1; i < columns; i++) {
            if (rows != vectors[i].getDimension()) throw new InvalidParameterException();
        }

        double[][] array = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[i][j] = vectors[j].get(i);
            }
        }

        return new Matrix(rows, columns, array);
    }

    public static IMatrix rowVectorsToMatrix(IVector[] vectors) {
        int rows = vectors.length;
        int columns = vectors[0].getDimension();
        for (int i = 1; i < rows; i++) {
            if (columns != vectors[i].getDimension()) throw new InvalidParameterException();
        }

        double[][] array = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[i][j] = vectors[i].get(j);
            }
        }

        return new Matrix(rows, columns, array);
    }

    public static IMatrix permute(IMatrix matrix, IVector permutationVector) {
        if (matrix.rows() != permutationVector.getDimension()) throw new InvalidParameterException();

        IMatrix result = matrix.copy();

        for (int i = 0, n = permutationVector.getDimension(); i < n; i++) {
            if (permutationVector.get(i) == i) continue;

            result.swapRows(i, (int) permutationVector.get(i));
        }
        return result;
    }

    public static IVector permute(IVector vector, IVector permutationVector) {
        int dimension = vector.getDimension();

        if (dimension != permutationVector.getDimension()) throw new InvalidParameterException();

        IVector result = vector.copy();

        for (int i = 0; i < dimension; i++) {
            result.swap(i, (int) permutationVector.get(i));
        }
        return result;
    }

    public static IVector permutationMatrixToVector(IMatrix matrix) {
        IVector vector = new Vector(matrix.columns());
        for (int i = 0, n = vector.getDimension(); i < n; i++) {
            int j = 0;
            while (matrix.get(i, j) != 1) {
                j++;
            }
            vector.set(i, j);
        }
        return vector;
    }

    public static int countSwaps(IVector vector) {
        int count = 0;
        for (int i = 0, n = vector.getDimension(); i < n; i++) {
            for (; i != vector.get(i); count++) {
                vector.swap(i, (int) vector.get(i));
            }
        }
        return count;
    }

    public static int countSwaps(IMatrix matrix) {
        return countSwaps(permutationMatrixToVector(matrix));
    }

    public static IMatrix squareRandom(int dimension, Random random) {
        return random(dimension, dimension, random);
    }

    public static boolean areDimensionsSame(IMatrix m1, IMatrix m2) {
        return m1.rows() == m2.rows() && m1.columns() == m2.columns();
    }

    public static boolean areDimensionsSame(IVector v1, IVector v2) {
        return v1.getDimension() == v2.getDimension();
    }

    public static boolean isSquareMatrix(IMatrix matrix) {
        return matrix.rows() == matrix.columns();
    }

    public static boolean isMultiplicationApplicable(IMatrix m1, IMatrix m2) {
        return m1.columns() != m2.rows();
    }

    public static boolean isMultiplicationApplicable(IMatrix matrix, IVector vector) {
        return matrix.columns() != vector.getDimension();
    }

    public static boolean isMultiplicationApplicable(IVector v1, IVector v2) {
        return areDimensionsSame(v1, v2);
    }

    public static boolean isSubstitutionApplicable(IMatrix matrix, IVector vector) {
        return vector.getDimension() == matrix.rows();
    }

    public static boolean isMatrixArray(double[][] array) {
        int columns = array[0].length;

        for (int i = 1, n = array.length; i < n; i++) {
            if (array[i].length != columns) return false;
        }

        return true;
    }
}
