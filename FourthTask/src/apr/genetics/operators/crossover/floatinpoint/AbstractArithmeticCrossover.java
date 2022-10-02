package apr.genetics.operators.crossover.floatinpoint;

abstract class AbstractArithmeticCrossover extends AbstractFloatingPointCrossover {

    private static final double DEFAULT_ALPHA = 0.5;

    protected double alpha = DEFAULT_ALPHA;

    protected AbstractArithmeticCrossover() {
    }

    protected AbstractArithmeticCrossover(double alpha) {
        this.alpha = alpha;
    }
}
