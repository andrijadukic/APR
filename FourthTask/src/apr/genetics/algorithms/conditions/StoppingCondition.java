package apr.genetics.algorithms.conditions;

import apr.genetics.algorithms.util.IntermediateResult;

@FunctionalInterface
public interface StoppingCondition {

    boolean isMet(IntermediateResult intermediateResult);

    default StoppingCondition and(StoppingCondition other) {
        return intermediateResult -> isMet(intermediateResult) && other.isMet(intermediateResult);
    }

    default StoppingCondition or(StoppingCondition other) {
        return intermediateResult -> isMet(intermediateResult) || other.isMet(intermediateResult);
    }

    default StoppingCondition not() {
        return intermediateResult -> !isMet(intermediateResult);
    }
}
