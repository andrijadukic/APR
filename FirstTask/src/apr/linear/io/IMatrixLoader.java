package apr.linear.io;

import apr.linear.matrix.IMatrix;

import java.io.IOException;

public interface IMatrixLoader {

    IMatrix load(String path) throws IOException;
}
