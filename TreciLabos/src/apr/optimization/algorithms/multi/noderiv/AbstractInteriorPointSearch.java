package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;
import apr.optimization.algorithms.multi.MultivariateCostFunction;
import apr.optimization.functions.constraints.InequalityConstraint;

import static apr.linear.util.linalg.LinearAlgebra.norm;
import static apr.linear.util.linalg.LinearAlgebra.subtract;
import static apr.linear.util.linalg.OperationMutability.MUTABLE;

/**
 * Abstract implementation of an interior point search algorithm
 */
public abstract class AbstractInteriorPointSearch extends AbstractMultivariateOptimizer {

    public AbstractInteriorPointSearch(InequalityConstraint[] inequalityConstraints) {
        super(buildConstrainedFunction(inequalityConstraints));
    }

    public AbstractInteriorPointSearch(InequalityConstraint[] inequalityConstraints, double epsilon) {
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
            IVector snapshot = x.copy();
            x = argMin(function, x);
            if (norm(subtract(snapshot, x, MUTABLE)) < epsilon) break;
        }
        return x;
    }

    protected abstract IVector argMin(IMultivariateCostFunction function, IVector x0);
}
