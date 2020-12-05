package apr.linear.vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Vector implementation that serves as a view of a list
 */
public class ListViewVector extends AbstractVector {

    private final List<Double> view;

    public ListViewVector(List<Double> view) {
        this.view = Objects.requireNonNull(view);
    }

    @Override
    public Vector newInstance(int dimension) {
        return new ListViewVector(new ArrayList<>(dimension));
    }

    @Override
    public Vector copy() {
        return new ListViewVector(new ArrayList<>(view));
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
    public Vector set(int i, double value) {
        view.set(i, value);
        return this;
    }
}
