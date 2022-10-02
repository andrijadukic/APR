package apr.linear.io;

import apr.linear.matrix.IMatrix;

public interface IMatrixWriter {

    void write(IMatrix matrix, String path);
}
