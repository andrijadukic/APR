package apr.genetics.algorithms.conditions;

public class StoppingConditions {

    public static StoppingCondition maxIter(final int maxIter) {
        return intermediateResult -> intermediateResult.iteration() == maxIter;
    }

    public static StoppingCondition precision(final double target, final double epsilon) {
        return intermediateResult -> Math.abs(intermediateResult.fittest().getFitness() - target) < epsilon;
    }

    public static StoppingCondition infiniteLoop() {
        return intermediateResult -> false;
    }
}
