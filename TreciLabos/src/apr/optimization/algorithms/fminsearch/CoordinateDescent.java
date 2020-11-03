package apr.optimization.algorithms.fminsearch;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.fminbnd.GoldenSectionSearch;
import apr.optimization.functions.IMultivariableFunction;

/**
 * Implementation of the coordinate descent algorithm
 */
public class CoordinateDescent extends AbstractMultivariableOptimizationAlgorithm {

    public CoordinateDescent(IMultivariableFunction function) {
        super(function);
    }

    public CoordinateDescent(IMultivariableFunction f, double epsilon) {
        super(f, epsilon);
    }

    @Override
    public IVector search(IVector x0) {
        int dimension = x0.getDimension();

        IVector x = x0.copy();
        IVector snapshot = x.copy();
        while (true) {
            for (int i = 0; i < dimension; i++) {
                final int nthDimension = i;
                final double xi = x.get(nthDimension);
                x.set(nthDimension, xi + new GoldenSectionSearch(lambda -> f.valueAt(x.set(nthDimension, xi + lambda))).search(xi));
            }
            if (isStopCriteriaMet(snapshot, x)) break;
            snapshot = x.copy();
        }
        return x;
    }

    private boolean isStopCriteriaMet(IVector previous, IVector current) {
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
