package apr.linear.util;

import apr.linear.matrix.IMatrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

public class FileMatrixLoader implements IMatrixLoader {

    private final IMatrixBuilder builder;

    public FileMatrixLoader(IMatrixBuilder builder) {
        this.builder = builder;
    }

    @Override
    public IMatrix load(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

        int rowDimension = lines.size();
        double[][] array = new double[rowDimension][];
        for (int i = 0; i < rowDimension; i++) {
            array[i] = Arrays.stream(lines.get(i).split("\\s+")).mapToDouble(Double::parseDouble).toArray();
        }

        if (!Matrices.isMatrixArray(array)) throw new InvalidParameterException();

        int columnDimension = array[0].length;

        IMatrix matrix = builder.build(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int j = 0; j < columnDimension; j++) {
                matrix.set(i, j, array[i][j]);
            }
        }

        return matrix;
    }
}
