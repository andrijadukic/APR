package apr.linear.util;

import apr.linear.matrix.IMatrix;
import apr.linear.matrix.Matrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class TextFileMatrixLoader implements IMatrixLoader {

    @Override
    public IMatrix load(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

        int rows = lines.size();
        double[][] array = new double[rows][];
        for (int i = 0; i < rows; i++) {
            array[i] = Arrays.stream(lines.get(i).split("\\s+")).mapToDouble(Double::parseDouble).toArray();
        }

        return new Matrix(array);
    }
}
