package apr.optimization.demo;

import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.algorithms.fminbnd.ISingleVariableOptimizationAlgorithm;
import apr.optimization.algorithms.fminsearch.IMultivariableOptimizationAlgorithm;
import apr.optimization.function.IMultivariableCostFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tester {

    private final Map<String, List<TestResult>> results;

    public Tester() {
        results = new HashMap<>();
    }

    public void test(IMultivariableCostFunction f, IMultivariableOptimizationAlgorithm algorithm, IVector x0) {
        f.reset();

        String name = algorithm.getName();
        List<TestResult> algorithmResults = results.getOrDefault(name, new ArrayList<>());
        IVector min = algorithm.search(x0);
        algorithmResults.add(new TestResult(x0, min, f.getCounter()));
        results.put(name, algorithmResults);

        f.reset();
    }

    public void test(IMultivariableCostFunction f, ISingleVariableOptimizationAlgorithm algorithm, double x0) {
        f.reset();

        String name = algorithm.getName();
        List<TestResult> algorithmResults = results.getOrDefault(name, new ArrayList<>());
        double min = algorithm.search(x0);
        algorithmResults.add(new TestResult(new Vector(x0), new Vector(min), f.getCounter()));
        results.put(name, algorithmResults);

        f.reset();
    }


    public void printResults() {
        results.forEach((algorithm, tests) -> {
            System.out.println(algorithm);
            tests.forEach(System.out::println);
            System.out.println();
            System.out.println();
        });
    }

    public void reset() {
        results.clear();
    }

    public record TestResult(IVector x0, IVector min, int evaluationCount) {

        @Override
        public String toString() {
            return "Calls: %-5d \t|\t Minimum: [%s]".formatted(evaluationCount, min);
        }
    }
}
