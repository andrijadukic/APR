package apr.linear.util;

import apr.linear.matrix.IMatrix;

import java.io.IOException;

public interface IMatrixLoader {

    IMatrix load(String path) throws IOException;
}
