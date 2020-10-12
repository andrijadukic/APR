package apr.matrix.util;

import apr.matrix.RealMatrix;
import apr.matrix.ArrayRealMatrix;
import apr.matrix.RealVector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Matrices {

    public static final NumberFormat FORMATTER = new DecimalFormat("#.###");

    public static RealMatrix blank(int rows, int columns) {
        return new ArrayRealMatrix(rows, columns, new double[rows][columns]);
    }

    public static RealMatrix blankSquare(int dimension) {
        return blank(dimension, dimension);
    }

    public static RealMatrix identity(int dimension) {
        return diagonal(dimension, 1.);
    }

    public static RealMatrix diagonal(int dimension, double value) {
        double[][] array = new double[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            array[i][i] = value;
        }

        return new ArrayRealMatrix(dimension, dimension, array);
    }

    public static RealMatrix random(int rows, int columns, Random random) {
        double[][] array = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[i][j] = random.nextDouble();
            }
        }

        return new ArrayRealMatrix(rows, columns, array);
    }

    public static RealMatrix squareRandom(int dimension, Random random) {
        return random(dimension, dimension, random);
    }

    public static RealMatrix columnRealVectorsToMatrix(RealVector[] vectors) {
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

        return new ArrayRealMatrix(rows, columns, array);
    }

    public static RealMatrix rowRealVectorsToMatrix(RealVector[] vectors) {
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

        return new ArrayRealMatrix(rows, columns, array);
    }

    public static ArrayRealMatrix load(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        int rows = lines.size();
        double[][] array = new double[rows][];
        for (int i = 0; i < rows; i++) {
            array[i] = Arrays.stream(lines.get(i).split("\\s+")).mapToDouble(Double::parseDouble).toArray();
        }

        return new ArrayRealMatrix(array);
    }

    public static boolean isSquareMatrix(RealMatrix matrix) {
        return matrix.getRowDimension() == matrix.getColumnDimension();
    }

    public static boolean isLowerTriangleMatrix(RealMatrix matrix) {
        if (!isSquareMatrix(matrix)) return false;

        for (int i = 0, n = matrix.getRowDimension(); i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (matrix.get(i, j) > MatrixUtils.EPSILON) return false;
            }
        }

        return true;
    }

    public static boolean isUpperTriangleMatrix(RealMatrix matrix) {
        if (!isSquareMatrix(matrix)) return false;

        for (int i = 0, n = matrix.getRowDimension(); i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix.get(i, j) > MatrixUtils.EPSILON) return false;
            }
        }

        return true;
    }

    public static boolean isMatrixArray(double[][] array) {
        int columns = array[0].length;

        for (int i = 1, n = array.length; i < n; i++) {
            if (array[i].length != columns) return false;
        }

        return true;
    }
}
