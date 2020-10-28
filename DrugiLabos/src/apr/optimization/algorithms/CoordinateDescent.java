package apr.optimization.algorithms;

import apr.linear.vector.IVector;
import apr.optimization.function.IFunction;
import apr.optimization.util.Interval;

public class CoordinateDescent extends AbstractOptimizationAlgorithm {

    public CoordinateDescent(IFunction function) {
        super(function);
    }

    public CoordinateDescent(IFunction f, double epsilon) {
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
                Interval min = new GoldenSectionSearch(lambda -> f.valueAt(x.set(ei, lambda.get(0)))).search(x.get(i));
                x.set(i, x.get(i) + (min.end() - min.start()) / 2);
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
