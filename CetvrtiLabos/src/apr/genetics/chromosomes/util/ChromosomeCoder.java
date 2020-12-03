package apr.genetics.chromosomes.util;

public interface ChromosomeCoder<R, V> {

    R encode(V value);

    V decode(R value);
}
