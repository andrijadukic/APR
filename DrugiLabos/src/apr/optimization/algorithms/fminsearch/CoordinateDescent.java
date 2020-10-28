package apr.optimization.algorithms.fminsearch;

import apr.linear.vector.IVector;
import apr.optimization.algorithms.fminbnd.GoldenSectionSearch;
import apr.optimization.function.IMultivariableFunction;

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
        IVector xPrev = x.copy();
        while (true) {
            for (int i = 0; i < dimension; i++) {
                final int ei = i;
                double lambdaMin = new GoldenSectionSearch(lambda -> f.valueAt(x.set(ei, lambda))).search(x.get(i));
                x.set(i, lambdaMin);
            }
            if (isStopCriteriaMet(xPrev, x)) break;
            xPrev = x.copy();
        }
        return x;
    }

    private boolean isStopCriteriaMet(IVector prev, IVector current) {
        for (int i = 0, n = prev.getDimension(); i < n; i++) {
            if (Math.abs(prev.get(i) - current.get(i)) > epsilon) {
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
