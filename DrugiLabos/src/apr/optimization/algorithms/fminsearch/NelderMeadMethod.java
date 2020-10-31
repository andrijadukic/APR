package apr.optimization.algorithms.fminsearch;

import apr.linear.util.Matrices;
import apr.linear.vector.IVector;
import apr.linear.util.builders.IVectorBuilder;
import apr.linear.vector.Vector;
import apr.optimization.function.IMultivariableFunction;

import java.util.Arrays;

import static apr.linear.util.linalg.LinearAlgebra.*;
import static apr.linear.util.linalg.OperationMutability.*;

/**
 * Implementation of the Nelder-Mead simplex method
 */
public class NelderMeadMethod extends AbstractMultivariableOptimizationAlgorithm {

    private double alpha = 1.0;
    private double beta = 0.5;
    private double gamma = 2.0;

    private double sigma = 0.5;
    private double step = 1;

    public NelderMeadMethod(IMultivariableFunction f) {
        super(f);
    }

    public NelderMeadMethod(IMultivariableFunction f, double epsilon, double alpha, double beta, double gamma, double sigma, double step) {
        super(f, epsilon);
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.sigma = sigma;
        this.step = step;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    @Override
    public IVector search(IVector x0) {
        final IVector[] X = initialSimplex(x0);
        final double[] fX = Arrays.stream(X).mapToDouble(f::valueAt).toArray();
        while (true) {
            Pair argMaxMin = argMaxMin(fX);
            int h = argMaxMin.first;
            int l = argMaxMin.second;
            IVector xh = X[h];
            IVector xl = X[l];

            IVector xc = centroid(X, h);
            IVector xr = reflection(xc, xh);

            double fxr = f.valueAt(xr);
            if (fxr < fX[l]) {
                IVector xe = expansion(xc, xr);
                double fxe = f.valueAt(xe);
                if (fxe < fX[l]) {
                    X[h] = xe;
                    fX[h] = fxe;
                } else {
                    X[h] = xr;
                    fX[h] = fxr;
                }
            } else {
                boolean isConditionMet = true;
                for (int i = 0, n = fX.length; i < n; i++) {
                    if (i == h) continue;
                    if (fxr < fX[i]) {
                        isConditionMet = false;
                        break;
                    }
                }
                if (isConditionMet) {
                    if (fxr < fX[h]) {
                        xh = X[h] = xr;
                        fX[h] = fxr;
                    }
                    IVector xk = contraction(xc, xh);
                    double fxk = f.valueAt(xk);
                    if (fxk < fX[h]) {
                        X[h] = xk;
                        fX[h] = fxk;
                    } else {
                        for (int i = 0, n = X.length; i < n; i++) {
                            if (i == l) continue;
                            X[i] = shrink(X[i], xl);
                            fX[i] = f.valueAt(X[i]);
                        }
                    }
                } else {
                    X[h] = xr;
                    fX[h] = fxr;
                }
            }

            if (isStopCriteriaMet(fX, xc)) return X[argMin(fX)];
        }
    }

    private IVector[] initialSimplex(IVector x0) {
        int n = x0.getDimension() + 1;
        IVector[] simplex = new IVector[n];
        simplex[0] = x0.copy();
        for (int i = 1; i < n; i++) {
            int nthDimension = i - 1;
            simplex[i] = x0.copy().set(nthDimension, x0.get(nthDimension) + step);
        }
        return simplex;
    }

    private static Pair argMaxMin(double[] array) {
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

    private static IVector centroid(IVector[] simplex, int h) {
        int n = simplex.length - 1;
        IVector centroid = Matrices.zeroes(n, (IVectorBuilder) Vector::new);
        for (int i = 0, length = n + 1; i < length; i++) {
            if (i == h) continue;
            add(centroid, simplex[i], MUTABLE);
        }
        return multiply(centroid, 1. / n, MUTABLE);
    }

    private IVector reflection(IVector xc, IVector xh) {
        return subtract(
                multiply(xc, 1 + alpha, IMMUTABLE),
                multiply(xh, alpha, IMMUTABLE),
                MUTABLE);
    }

    private IVector expansion(IVector xc, IVector xr) {
        return add(
                multiply(xc, 1 - gamma, IMMUTABLE),
                multiply(xr, gamma, IMMUTABLE),
                MUTABLE);
    }

    private IVector contraction(IVector xc, IVector xh) {
        return add(
                multiply(xc, 1 - beta, IMMUTABLE),
                multiply(xh, beta, IMMUTABLE),
                MUTABLE);
    }

    private IVector shrink(IVector xi, IVector xl) {
        return multiply(
                add(xi, xl, MUTABLE),
                sigma,
                MUTABLE);
    }

    private boolean isStopCriteriaMet(double[] fX, IVector centroid) {
        double val = 0.;
        double fxc = f.valueAt(centroid);
        for (double x : fX) {
            val += Math.pow(x - fxc, 2);
        }
        return Math.sqrt(val / (fX.length - 1)) <= epsilon;
    }

    private int argMin(double[] array) {
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

    @Override
    public String getName() {
        return "Nelder Mead";
    }

    private static record Pair(int first, int second) {
    }
}
