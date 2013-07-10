package nl.salp.util.collection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ImmutablePairTest {
    @Test
    public void shouldSetValues() {
        ImmutablePair<Boolean, String> pair = new ImmutablePair<Boolean, String>(true, "foobar");
        assertEquals(true, pair.getLeftValue());
        assertEquals("foobar", pair.getRightValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWithANullLeftValue() {
        new ImmutablePair<Boolean, String>(null, "foobar");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWithANullRightValue() {
        new ImmutablePair<Boolean, String>(true, null);
    }

    @Test
    public void shouldEqualSameValues() {
        ImmutablePair<Boolean, String> pairOne = new ImmutablePair<Boolean, String>(true, "foobar");
        ImmutablePair<Boolean, String> pairTwo = new ImmutablePair<Boolean, String>(true, "foobar");

        assertTrue(pairOne.equals(pairTwo));
        assertTrue(pairTwo.equals(pairOne));
    }

    @Test
    public void shouldNotEqualDifferentValues() {
        ImmutablePair<Boolean, String> pairOne = new ImmutablePair<Boolean, String>(true, "foobar");
        ImmutablePair<Boolean, String> pairTwo = new ImmutablePair<Boolean, String>(false, "foobar");

        assertFalse(pairOne.equals(pairTwo));
        assertFalse(pairTwo.equals(pairOne));
    }

    @Test
    public void shouldNotEqualDifferentTypes() {
        ImmutablePair<Boolean, String> pairOne = new ImmutablePair<Boolean, String>(true, "foobar");
        ImmutablePair<String, String> pairTwo = new ImmutablePair<String, String>("true", "foobar");

        assertFalse(pairOne.equals(pairTwo));
    }

    @Test
    public void shouldCalculateUniqueHashCode() {
        ImmutablePair<String, String> pair = new ImmutablePair<String, String>("true", "foobar");
        ImmutablePair<String, String> pairSwitched = new ImmutablePair<String, String>("foobar", "true");
        ImmutablePair<Boolean, String> pairDifferentType = new ImmutablePair<Boolean, String>(true, "foobar");
        ImmutablePair<String, String> pairEqual = new ImmutablePair<String, String>("true", "foobar");

        assertNotEquals(pair.hashCode(), pairSwitched.hashCode());
        assertNotEquals(pair.hashCode(), pairDifferentType.hashCode());
        assertEquals(pair.hashCode(), pairEqual.hashCode());
    }

    @Test
    public void shouldGenerateString() {
        ImmutablePair<Boolean, String> pair = new ImmutablePair<Boolean, String>(true, "foobar");
        assertEquals("ImmutablePair [left: true, right: foobar]", pair.toString());
    }
}
