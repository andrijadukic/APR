package apr.linear.util;

public interface ICopyable<T> {

    /**
     * Creates a deep copy of this object
     *
     * @return deep copy
     */
    T copy();
}
