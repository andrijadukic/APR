package apr.linear.util;

import java.util.function.DoublePredicate;

public interface IMatchable {

    /**
     * Tests if any element matches predicate
     *
     * @param predicate predicate to be tested
     * @return true if any element matches, false otherwise
     */
    boolean anyMatch(DoublePredicate predicate);


    /**
     * Tests if all elements match predicate
     *
     * @param predicate predicate to be tested
     * @return true if all elements match, false otherwise
     */
    boolean allMatch(DoublePredicate predicate);
}
