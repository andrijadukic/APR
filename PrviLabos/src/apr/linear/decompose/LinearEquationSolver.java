package apr.linear.decompose;

import apr.linear.matrix.IMatrix;
import apr.linear.vector.IVector;

public interface LinearEquationSolver {

    IVector solve(IVector b);

    IMatrix solve(IMatrix b);

    IMatrix invert();
}
