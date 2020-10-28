package apr.optimization.algorithms;

import apr.linear.util.Matrices;
import apr.linear.vector.IVector;
import apr.optimization.function.IFunction;


public class NelderMeadMethod extends AbstractOptimizationAlgorithm {

    private double alpha = 1.0;
    private double beta = 0.5;
    private double gamma = 2.0;

    private double sigma = 0.5;
    private double step = 1;

    public NelderMeadMethod(IFunction f) {
        super(f);
    }

    public NelderMeadMethod(IFunction f, double epsilon, double alpha, double beta, double gamma, double sigma, double step) {
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
        IVector[] simplex = initialSimplex(x0);
        while (true) {
            Pair argMaxMin = argMaxMin(f, simplex);
            int h = argMaxMin.first;
            int l = argMaxMin.second;
            IVector xh = simplex[h];
            IVector xl = simplex[l];
            double min = f.valueAt(xl);
            double max = f.valueAt(xh);

            IVector xc = centroid(simplex, xh);
            IVector xr = reflection(xc, xh);

            double fxr = f.valueAt(xr);
            if (fxr < min) {
                IVector xe = expansion(xc, xr);
                simplex[h] = f.valueAt(xe) < min ? xe : xr;
            } else {
                boolean isConditionMet = true;
                for (int i = 0, n = simplex.length; i < n; i++) {
                    if (i == h) continue;
                    if (fxr < f.valueAt(simplex[i])) {
                        isConditionMet = false;
                        break;
                    }
                }
                if (isConditionMet) {
                    if (fxr < max) {
                        simplex[h] = xr;
                    }
                    IVector xk = contraction(xc, simplex[h]);
                    if (f.valueAt(xk) < f.valueAt(simplex[h])) {
                        simplex[h] = xk;
                    } else {
                        for (int i = 0, n = simplex.length; i < n; i++) {
//                            if (i == l) continue;
                            simplex[i] = simplex[i].add(xl).multiply(sigma);
                        }
                    }
                } else {
                    simplex[h] = xr;
                }
            }

            if (isStopCriteriaMet(f, simplex, xc)) return xl;
        }
    }

    private IVector[] initialSimplex(IVector x0) {
        int dimension = x0.getDimension();
        IVector[] simplex = new IVector[dimension];
        for (int i = 0; i < dimension; i++) {
            simplex[i] = x0.copy().set(i, x0.get(i) + step);
        }
        return simplex;
    }

    private static Pair argMaxMin(IFunction f, IVector[] simplex) {
        double maxValue, minValue;
        int maxIndex, minIndex;
        maxValue = minValue = f.valueAt(simplex[0]);
        maxIndex = minIndex = 0;

        for (int i = 1, n = simplex.length; i < n; i++) {
            double temp = f.valueAt(simplex[i]);
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

        return n == 1 ? centroid : centroid.multiply((double) 1 / (n - 1));
    }

    private IVector reflection(IVector xc, IVector xh) {
        return xc.multiply(1 + alpha).subtract(xh.multiply(alpha));
    }

    private IVector contraction(IVector xc, IVector xh) {
        return xc.multiply(1 - beta).add(xh.multiply(beta));
    }

    private IVector expansion(IVector xc, IVector xr) {
        return xc.multiply(1 - gamma).add(xr.multiply(gamma));
    }

    private boolean isStopCriteriaMet(IFunction f, IVector[] simplex, IVector centroid) {
        double val = 0;
        double fxc = f.valueAt(centroid);
        for (IVector point : simplex) {
            val += Math.pow(f.valueAt(point) - fxc, 2);
        }
        return Math.sqrt(val / simplex.length) <= epsilon;
    }

    @Override
    public String getName() {
        return "Nelder Mead";
    }

    private static record Pair(int first, int second) {
    }
}
