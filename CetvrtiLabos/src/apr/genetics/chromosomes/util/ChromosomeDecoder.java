package apr.genetics.chromosomes.util;

public interface ChromosomeDecoder<R, E> {

    R decode(E value);
}
