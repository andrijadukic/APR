package apr.optimization.algorithms.fminbnd;

import apr.optimization.functions.IUnivariateFunction;

/**
 * Implementation of the golden section search algorithm
 */
public class GoldenSectionSearch extends AbstractUnivariateOptimizer {

    private final double K = 0.5 * (Math.sqrt(5) - 1);
    private double h = 1;

    public GoldenSectionSearch(IUnivariateFunction f) {
        super(f);
    }

    public GoldenSectionSearch(IUnivariateFunction f, double epsilon, double h) {
        super(f, epsilon);
        this.h = h;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    @Override
    public double search(double x0) {
        Interval interval = findInterval(UnimodalInterval.findInterval(f, h, x0));
        return (interval.lowerbound() + interval.upperbound()) / 2.;
    }

    public Interval findInterval(Interval interval) {
        double a = interval.lowerbound();
        double b = interval.upperbound();

        double c = b - K * (b - a);
        double d = a + K * (b - a);

        double fc = f.valueAt(c);
        double fd = f.valueAt(d);

        while ((b - a) > epsilon) {
            if (fc < fd) {
                b = d;
                d = c;
                c = b - K * (b - a);
                fd = fc;
                fc = f.valueAt(c);
            } else {
                a = c;
                c = d;
                d = a + K * (b - a);
                fc = fd;
                fd = f.valueAt(d);
            }
        }

        return new Interval(a, b);
    }

    @Override
    public String getName() {
        return "Golden section search";
    }
}
