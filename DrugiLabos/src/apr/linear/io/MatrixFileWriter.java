package apr.linear.io;

import apr.linear.matrix.IMatrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MatrixFileWriter implements IMatrixWriter {

    @Override
    public void write(IMatrix matrix, String path) {
        try (var writer = Files.newBufferedWriter(Paths.get(path))) {
            writer.write(matrix.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
