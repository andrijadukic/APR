package apr.optimization.algorithms.multi.noderiv;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.uni.GoldenSectionSearch;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;

/**
 * Implementation of the coordinate descent algorithm
 */
public class CoordinateDescent extends AbstractMultivariateOptimizer {

    public CoordinateDescent(IMultivariateCostFunction function) {
        super(function);
    }

    public CoordinateDescent(IMultivariateCostFunction function, double epsilon) {
        super(function, epsilon);
    }

    @Override
    public IVector search(IVector x0) {
        IVector x = x0.copy();
        int dimension = x0.getDimension();
        while (true) {
            IVector snapshot = x.copy();
            for (int i = 0; i < dimension; i++) {
                final int nthDimension = i;
                final double xi = x.get(nthDimension);
                x.set(nthDimension, xi + new GoldenSectionSearch(lambda -> function.valueAt(x.set(nthDimension, xi + lambda))).search(xi));
            }
            if (testConvergence(snapshot, x)) break;
        }
        return x;
    }

    protected boolean testConvergence(IVector previous, IVector current) {
        for (int i = 0, n = previous.getDimension(); i < n; i++) {
            if (Math.abs(previous.get(i) - current.get(i)) > epsilon) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "Coordinate descent";
    }
}
