package apr.genetics.exceptions;

public class ChromosomeLengthMismatchException extends RuntimeException {

    public ChromosomeLengthMismatchException(int length1, int length2) {
        super("Chromosomes not of equal length (" + length1 + " != " + length2 + ")");
    }
}
