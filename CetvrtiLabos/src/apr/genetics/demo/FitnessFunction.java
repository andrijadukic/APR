package apr.genetics.demo;

import java.util.List;

public interface FitnessFunction {

    double valueAt(List<Double> x);

    default FitnessFunction negate() {
        return x -> -valueAt(x);
    }
}
