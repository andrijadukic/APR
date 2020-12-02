package apr.genetics.chromosomes.util;

public interface ChromosomeEncoder<R, E> {

    R encode(E value);
}
