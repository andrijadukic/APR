package apr.optimization.algorithms.multi.noderiv;

import apr.linear.util.Matrices;
import apr.linear.util.builders.IVectorBuilder;
import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.algorithms.multi.IMultivariateCostFunction;

import java.util.Arrays;

import static apr.linear.util.linalg.LinearAlgebra.add;
import static apr.linear.util.linalg.LinearAlgebra.multiply;
import static apr.linear.util.linalg.OperationMutability.MUTABLE;

/**
 * Abstract implementation of a simplex optimization method
 */
public abstract class AbstractSimplexMethod extends AbstractMultivariateOptimizer {

    protected AbstractSimplexMethod(IMultivariateCostFunction function) {
        super(function);
    }

    protected AbstractSimplexMethod(IMultivariateCostFunction function, double epsilon) {
        super(function, epsilon);
    }

    @Override
    public IVector search(IVector x0) {
        validate(x0);

        final IVector[] X = initialSimplex(x0);
        final double[] fX = Arrays.stream(X).mapToDouble(function::valueAt).toArray();
        while (true) {
            boolean convergence = iterate(X, fX);
            if (convergence) break;
        }

        return X[argMin(fX)];
    }

    protected abstract void validate(IVector x0);

    protected abstract IVector[] initialSimplex(IVector x0);

    protected abstract boolean iterate(IVector[] X, double[] fX);

    protected IVector centroid(IVector[] simplex, int h) {
        int n = simplex.length;
        IVector centroid = Matrices.zeroes(simplex[0].getDimension(), (IVectorBuilder) Vector::new);
        for (int i = 0; i < n; i++) {
            if (i == h) continue;
            add(centroid, simplex[i], MUTABLE);
        }
        return multiply(centroid, 1. / (n - 1), MUTABLE);
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
        double fxc = function.valueAt(centroid);
        for (double fx : fX) {
            val += Math.pow(fx - fxc, 2);
        }
        return Math.sqrt(val / (fX.length - 1)) <= epsilon;
    }
}
