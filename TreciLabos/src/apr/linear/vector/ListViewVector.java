package apr.linear.vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Vector implementation that serves as a view of a list
 */
public class ListViewVector extends AbstractVector {

    private final List<Double> view;

    public ListViewVector(List<Double> view) {
        this.view = view;
    }

    @Override
    public IVector newInstance(int dimension) {
        return new ListViewVector(new ArrayList<>(dimension));
    }

    @Override
    public int getDimension() {
        return view.size();
    }

    @Override
    public double get(int i) {
        return view.get(i);
    }

    @Override
    public IVector set(int i, double value) {
        view.set(i, value);
        return this;
    }

    @Override
    public IVector copy() {
        return new ListViewVector(new ArrayList<>(view));
    }
}
