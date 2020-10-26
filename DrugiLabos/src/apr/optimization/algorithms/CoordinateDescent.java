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
        IVector ei = Matrices.zeroes(dimension, x0::newInstance);

        while (true) {
            IVector xs = x.copy();
            for (int i = 0; i < dimension; i++) {
                ei.set(i, 1);
                GoldenSectionSearch.epsilon = e.get(i);
                Interval min = GoldenSectionSearch.goldenRatio(l -> f.valueAt(x.add(l.multiply(ei))), 1, x.get(i));
                x.set(i, x.get(i) + (min.end() - min.start()) / 2);
                ei.set(i, 0);
            }

            boolean isConditionMet = true;
            for (int i = 0; i < dimension; i++) {
                if (Math.abs(xs.get(i) - x.get(i)) > e.get(i)) {
                    isConditionMet = false;
                    break;
                }
            }
            if (isConditionMet) break;
        }

        return x;
    }
}
