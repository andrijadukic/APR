package apr.optimization.algorithms.fminbnd;

public interface ISingleVariableOptimizationAlgorithm {

    double search(double x0);

    String getName();
}
