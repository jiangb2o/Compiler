
/**
 * Pair class storage two elements.
 * @param <A> first element type.
 * @param <B> second element type.
 */
public class Pair<A, B> {
    public A first;
    public B second;

    /**
     * Constructor
     * @param first to initialize first element
     * @param second to initialize second element
     */
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
}
