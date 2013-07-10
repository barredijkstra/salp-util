package nl.salp.util.collection;

import java.util.HashMap;

/**
 * Basic immutable pair (2-tuple).
 *
 * <p>
 * Only created since I dislike using things like {@link HashMap.SimpleImmutableEntry}.
 * </p>
 *
 * @param <L> The type of the left-hand value.
 * @param <R> The type of the right-hand value.
 */
public class ImmutablePair<L, R> {
    /**
     * The left-hand value.
     */
    private final L leftValue;
    /**
     * The right-hand value.
     */
    private final R rightValue;

    /**
     * Create a new ImmutablePair.
     *
     * @param leftValue  The left-hand value.
     * @param rightValue The right-hand value.
     *
     * @throws IllegalArgumentException When one of the provided values is null.
     */
    public ImmutablePair(L leftValue, R rightValue) throws IllegalArgumentException {
        if (leftValue == null) {
            throw new IllegalArgumentException("No left rightValue provided");
        }
        if (rightValue == null) {
            throw new IllegalArgumentException("No right rightValue provided");
        }
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    /**
     * Get the left-hand value.
     *
     * @return The value.
     */
    public L getLeftValue() {
        return leftValue;
    }

    /**
     * Get the right-hand value.
     *
     * @return The value.
     */
    public R getRightValue() {
        return rightValue;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (obj != null && obj instanceof ImmutablePair) {
            ImmutablePair other = (ImmutablePair) obj;
            equals = leftValue.equals(other.leftValue) && rightValue.equals(other.rightValue);
        }
        return equals;
    }

    @Override
    public int hashCode() {
        int bits = leftValue.hashCode();
        bits ^= rightValue.hashCode() * 31;
        return bits;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ImmutablePair [");
        sb.append("left: ").append(leftValue.toString()).append(", ");
        sb.append("right: ").append(rightValue.toString());
        sb.append("]");
        return sb.toString();
    }
}
