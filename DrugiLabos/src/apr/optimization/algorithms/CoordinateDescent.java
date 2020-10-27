package apr.optimization.algorithms;

import apr.linear.util.Matrices;
import apr.linear.vector.IVector;
import apr.optimization.function.IFunction;
import apr.optimization.util.Interval;

public class CoordinateDescent {

    public static double epsilon = 10e-6;

    public static IVector search(IFunction f, IVector x0, IVector e) {
        int dimension = x0.getDimension();

        if (e == null) {
            e = Matrices.ones(dimension, x0::newInstance).multiply(epsilon);
        }

        IVector x = x0.copy();
        IVector xPrev = x.copy();
        while (true) {
            IVector ei = Matrices.zeroes(dimension, x0::newInstance);
            for (int i = 0; i < dimension; i++) {
                ei.set(i, 1);
                GoldenSectionSearch.epsilon = e.get(i);
                Interval min = GoldenSectionSearch.goldenRatio(lambda -> f.valueAt(x.add(lambda.multiply(ei))), 1, x.get(i));
                x.set(i, x.get(i) + (min.end() - min.start()) / 2);
                ei.set(i, 0);
            }

            if (isStopCriteriaMet(xPrev, x, e)) break;

            xPrev = x.copy();
        }
        return x;
    }

    private static boolean isStopCriteriaMet(IVector prev, IVector current, IVector e) {
        for (int i = 0, n = prev.getDimension(); i < n; i++) {
            if (Math.abs(prev.get(i) - current.get(i)) > e.get(i)) {
                return false;
            }
        }
        return true;
    }
}
