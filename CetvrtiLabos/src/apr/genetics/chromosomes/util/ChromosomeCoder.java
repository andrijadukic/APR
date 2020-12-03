package apr.genetics.chromosomes.util;

import java.util.List;
import java.util.stream.Collectors;

public interface ChromosomeCoder<R, V> {

    R encode(V value);

    V decode(R value);

    default List<R> encode(List<V> representation) {
        return representation.stream().map(this::encode).collect(Collectors.toUnmodifiableList());
    }

    default List<V> decode(List<R> representation) {
        return representation.stream().map(this::decode).collect(Collectors.toUnmodifiableList());
    }
}
