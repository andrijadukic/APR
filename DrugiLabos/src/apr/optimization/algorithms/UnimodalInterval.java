package apr.optimization.algorithms;

import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.function.IFunction;
import apr.optimization.util.Interval;

public class UnimodalInterval extends AbstractOptimizationAlgorithm {

    private double h = 1;

    public UnimodalInterval(IFunction f) {
        super(f);
    }

    public UnimodalInterval(IFunction f, double epsilon, double h) {
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
        return x0.newInstance(2).set(0, interval.start()).set(1, interval.end());
    }

    public Interval search(double x0) {
        double l = x0 - h;
        double m = x0;
        double r = x0 + h;
        double fl, fm, fr;
        int step = 1;

        fl = f.valueAt(new Vector(l));
        fm = f.valueAt(new Vector(x0));
        fr = f.valueAt(new Vector(r));

        if (fm < fr && fm < fl) return new Interval(l, r);

        if (fm > fr) {
            do {
                l = m;
                m = r;
                fm = fr;
                r = x0 + h * (step *= 2);
                fr = f.valueAt(new Vector(r));
            } while (fm > fr);
        } else {
            do {
                r = m;
                m = l;
                fm = fl;
                l = x0 - h * (step *= 2);
                fl = f.valueAt(new Vector(l));
            } while (fm > fl);
        }

        return new Interval(l, r);
    }

    @Override
    public String getName() {
        return "Unimodal interval";
    }
}
