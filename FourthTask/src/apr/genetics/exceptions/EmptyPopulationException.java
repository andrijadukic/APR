package apr.genetics.exceptions;

public class EmptyPopulationException extends RuntimeException {

    public EmptyPopulationException() {
        super("Population contains zero chromosomes");
    }
}
