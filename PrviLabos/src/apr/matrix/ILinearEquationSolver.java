package apr.matrix;

public interface ILinearEquationSolver {

    IVector solve(IVector b);

    IMatrix solve(IMatrix b);

    IMatrix invert();
}
