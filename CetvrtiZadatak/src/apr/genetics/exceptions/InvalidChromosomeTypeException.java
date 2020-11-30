package apr.genetics.exceptions;

public class InvalidChromosomeTypeException extends RuntimeException {

    public InvalidChromosomeTypeException(Class<?> expected, Class<?> received) {
        super("Operation does not support this chromosome type. Expected " + expected.getName() + ", received: " + received.getName());
    }
}
