package apr.linear.util;

import apr.linear.matrix.IMatrix;

public interface IMatrixBuilder {

    IMatrix build(int rowDimension, int columnDimension);
}
