package apr.optimization.algorithms.fminsearch;

import apr.linear.util.Matrices;
import apr.linear.vector.IVector;
import apr.optimization.function.IMultivariableFunction;

import java.util.Arrays;


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

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
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
        IVector[] X = initialSimplex(x0);
        while (true) {
            double[] fX = Arrays.stream(X).mapToDouble(f::valueAt).toArray();
            Pair argMaxMin = argMaxMin(fX);
            int h = argMaxMin.first;
            int l = argMaxMin.second;
            IVector xh = X[h];
            IVector xl = X[l];
            double max = fX[h];
            double min = fX[l];

            IVector xc = centroid(X, xh);
            IVector xr = reflection(xc, xh);

            double fxr = f.valueAt(xr);
            if (fxr < min) {
                IVector xe = expansion(xc, xr);
                X[h] = f.valueAt(xe) < min ? xe : xr;
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
                    if (fxr < max) {
                        xh = X[h] = xr;
                    }
                    IVector xk = contraction(xc, xh);
                    if (f.valueAt(xk) < f.valueAt(xh)) {
                        X[h] = xk;
                    } else {
                        for (int i = 0, n = X.length; i < n; i++) {
                            if (i == l) continue;
                            X[i] = shrink(X[i], xl);
                        }
                    }
                } else {
                    X[h] = xr;
                }
            }

            if (isStopCriteriaMet(fX, xc)) return xl;
        }
    }

    private IVector[] initialSimplex(IVector x0) {
        int n = x0.getDimension() + 1;
        IVector[] simplex = new IVector[n];
        simplex[0] = x0.copy();
        for (int i = 1; i < n; i++) {
            simplex[i] = x0.copy().set(i - 1, x0.get(i - 1) + step);
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

    private static IVector centroid(IVector[] simplex, IVector xh) {
        int n = xh.getDimension();
        IVector centroid = Matrices.zeroes(n, xh::newInstance);
        for (IVector point : simplex) {
            if (point.equals(xh)) continue;
            centroid = centroid.add(point);
        }
        return centroid.multiply((double) 1 / n);
    }

    private IVector reflection(IVector xc, IVector xh) {
        return xc.multiply(1 + alpha).subtract(xh.multiply(alpha));
    }

    private IVector expansion(IVector xc, IVector xr) {
        return xc.multiply(1 - gamma).add(xr.multiply(gamma));
    }

    private IVector contraction(IVector xc, IVector xh) {
        return xc.multiply(1 - beta).add(xh.multiply(beta));
    }

    private IVector shrink(IVector xi, IVector xl) {
        return xl.add(xi.subtract(xl).multiply(sigma));
    }

    private boolean isStopCriteriaMet(double[] fX, IVector centroid) {
        double fxc = f.valueAt(centroid);
        return Math.sqrt(Arrays.stream(fX).map(x -> Math.pow(x - fxc, 2)).average().orElseThrow(IllegalStateException::new)) <= epsilon;
    }

    @Override
    public String getName() {
        return "Nelder Mead";
    }

    private static record Pair(int first, int second) {
    }
}
