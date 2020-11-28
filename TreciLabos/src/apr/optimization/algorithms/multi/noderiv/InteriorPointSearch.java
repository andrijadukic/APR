package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;
import apr.optimization.algorithms.multi.MultivariateCostFunction;
import apr.optimization.functions.constraints.InequalityConstraint;

import static apr.linear.util.linalg.LinearAlgebra.norm;
import static apr.linear.util.linalg.LinearAlgebra.subtract;
import static apr.linear.util.linalg.OperationMutability.IMMUTABLE;

public abstract class InteriorPointSearch extends AbstractMultivariateOptimizer {

    public InteriorPointSearch(InequalityConstraint[] inequalityConstraints) {
        super(buildConstrainedFunction(inequalityConstraints));
    }

    public InteriorPointSearch(InequalityConstraint[] inequalityConstraints, double epsilon) {
        super(buildConstrainedFunction(inequalityConstraints), epsilon);
    }

    private static IMultivariateCostFunction buildConstrainedFunction(InequalityConstraint[] inequalityConstraints) {
        return new MultivariateCostFunction(x -> {
            double penalty = 0.;
            for (InequalityConstraint constraint : inequalityConstraints) {
                double constraintValue = constraint.getFunction().valueAt(x);
                if (constraintValue < 0) {
                    penalty -= constraintValue;
                }
            }
            return penalty;
        });
    }

    @Override
    public IVector search(IVector x0) {
        IVector x = x0.copy();
        while (true) {
            IVector snapshot = x;
            x = argMin(x);
            if (norm(subtract(x, snapshot, IMMUTABLE)) < epsilon) break;
        }
        return x;
    }

    protected abstract IVector argMin(IVector x0);
}
