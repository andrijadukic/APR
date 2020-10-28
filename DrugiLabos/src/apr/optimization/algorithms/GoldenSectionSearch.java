package apr.optimization.algorithms;

import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.function.IFunction;
import apr.optimization.util.Interval;

public class GoldenSectionSearch extends AbstractOptimizationAlgorithm {

    private final double K = 0.5 * (Math.sqrt(5) - 1);
    private double h = 1;

    public GoldenSectionSearch(IFunction f) {
        super(f);
    }

    public GoldenSectionSearch(IFunction f, double epsilon, double h) {
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
    public IVector search(IVector x0) {
        Interval interval = search(x0.get(0));
        return x0.newInstance(1).set(0, (interval.end() - interval.start()) / 2);
    }

    public Interval search(double x0) {
        return search(new UnimodalInterval(f, epsilon, h).search(x0));
    }

    public Interval search(Interval interval) {
        double a = interval.start();
        double b = interval.end();

        double c = b - K * (b - a);
        double d = a + K * (b - a);

        double fc = f.valueAt(new Vector(c));
        double fd = f.valueAt(new Vector(d));

        while ((b - a) > epsilon) {
            if (fc < fd) {
                b = d;
                d = c;
                c = b - K * (b - a);
                fd = fc;
                fc = f.valueAt(new Vector(c));
            } else {
                a = c;
                c = d;
                d = a + K * (b - a);
                fc = fd;
                fd = f.valueAt(new Vector(d));
            }
        }

        return new Interval(a, b);
    }

    @Override
    public String getName() {
        return "Golden section search";
    }
}
