package apr.optimization.algorithms.fminsearch;

import apr.linear.util.Matrices;
import apr.linear.util.builders.IVectorBuilder;
import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.functions.IMultivariableCostFunction;

import static apr.linear.util.linalg.LinearAlgebra.add;
import static apr.linear.util.linalg.LinearAlgebra.multiply;
import static apr.linear.util.linalg.OperationMutability.MUTABLE;

public abstract class SimplexMethod extends AbstractMultivariableOptimizationAlgorithm {

    protected SimplexMethod(IMultivariableCostFunction f) {
        super(f);
    }

    protected SimplexMethod(IMultivariableCostFunction f, double epsilon) {
        super(f, epsilon);
    }

    protected Pair argMaxMin(double[] array) {
        double maxValue, minValue;
        int maxIndex, minIndex;

        maxValue = minValue = array[0];
        maxIndex = minIndex = 0;
        for (int i = 1, n = array.length; i < n; i++) {
            double temp = array[i];
            if (temp > maxValue) {
                maxValue = temp;
                maxIndex = i;
            } else if (temp < minValue) {
                minValue = temp;
                minIndex = i;
            }
        }
        return new Pair(maxIndex, minIndex);
    }

    protected IVector centroid(IVector[] simplex, int h) {
        int n = simplex.length - 1;
        IVector centroid = Matrices.zeroes(n, (IVectorBuilder) Vector::new);
        for (int i = 0, length = n + 1; i < length; i++) {
            if (i == h) continue;
            add(centroid, simplex[i], MUTABLE);
        }
        return multiply(centroid, 1. / n, MUTABLE);
    }

    protected IVector centroid(IVector[] simplex) {
        int n = simplex[0].getDimension();
        IVector centroid = Matrices.zeroes(n, (IVectorBuilder) Vector::new);
        for (int i = 0; i < n; i++) {
            add(centroid, simplex[i], MUTABLE);
        }
        return multiply(centroid, 1. / n, MUTABLE);
    }

    protected int argMin(double[] array) {
        int minIndex = 0;
        double minValue = array[0];
        for (int i = 1, n = array.length; i < n; i++) {
            double temp = array[i];
            if (temp < minValue) {
                minValue = temp;
                minIndex = i;
            }
        }
        return minIndex;
    }

    protected boolean isStopCriteriaMet(double[] fX, IVector centroid) {
        double val = 0.;
        double fxc = f.valueAt(centroid);
        for (double fx : fX) {
            val += Math.pow(fx - fxc, 2);
        }
        return Math.sqrt(val / (fX.length - 1)) <= epsilon;
    }

    public record Pair(int first, int second) {
    }
}
