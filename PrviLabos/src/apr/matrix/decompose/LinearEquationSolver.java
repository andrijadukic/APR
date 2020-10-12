package apr.matrix.decompose;

import apr.matrix.RealMatrix;
import apr.matrix.RealVector;
import apr.matrix.Vector;

public interface LinearEquationSolver {

    RealVector solve(RealVector b);

    RealMatrix solve(RealMatrix b);

    RealMatrix invert();
}
