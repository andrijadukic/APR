package apr.genetics.operators.crossover.floatinpoint;

abstract class AbstractArithmeticCrossover extends AbstractFloatingPointCrossover {

    protected double alpha = DEFAULT_ALPHA;

    private static final double DEFAULT_ALPHA = 0.5;

    protected AbstractArithmeticCrossover() {
    }

    protected AbstractArithmeticCrossover(double alpha) {
        this.alpha = alpha;
    }
}
