package apr.matrix;

public class LUPLinearEquationSolver implements ILinearEquationSolver {

    private final IMatrix L;
    private final IMatrix U;
    private final IVector P;

    public LUPLinearEquationSolver(IMatrix A) {
        IMatrix[] LUP = new SeparativeLUPDecomposer(A).decompose();
        L = LUP[0];
        U = LUP[1];
        P = LUP[2].toRowVectors()[0];
    }

    @Override
    public IVector solve(IVector b) {
        return Operations.backwardSubstitution(U, Operations.forwardSubstitution(L, Operations.permute(b, P)));
    }
}
