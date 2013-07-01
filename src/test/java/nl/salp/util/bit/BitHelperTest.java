package nl.salp.util.bit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.internal.matchers.VarargMatcher;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Unit tests for {@link BitHelper} non-abstract methods.
 *
 * @see BitHelper
 */
public class BitHelperTest {
    /**
     * Default number, also used as default mask.
     */
    private final Integer DEFAULT_NUMBER = -1;
    /**
     * Default number of bits for the helper.
     */
    private final int DEFAULT_BITS = 16;

    /**
     * The class under test as a Mockito spied class with only the abstract methods being mocked.
     */
    private BitHelper<Number> helper;

    /**
     * Set-up, being called before every test.
     */
    @Before
    public void setUp() {
        helper = createBitHelper(DEFAULT_BITS);
    }

    /**
     * Ensure that nothing happens with a valid index.
     *
     * @see BitHelper#checkBitIndex(int)
     */
    @Test
    public void shouldDoNothingWhenCheckingTheBitIndexForCorrectNumbers() {
        helper.checkBitIndex(DEFAULT_BITS - 1);
    }

    /**
     * Check if an IllegalArgumentException is thrown with an index that is too low.
     *
     * @see BitHelper#checkBitIndex(int)
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCheckingTheBitIndexForTooSmallNumbers() {
        helper.checkBitIndex(-1);
    }

    /**
     * Check if an IllegalArgumentException is thrown with an index that is too high.
     *
     * @see BitHelper#checkBitIndex(int)
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCheckingTheBitIndexForTooBigNumbers() {
        helper.checkBitIndex(DEFAULT_BITS);
    }

    /**
     * Check if the correct number of bytes are reported.
     *
     * @see BitHelper#getNumberOfBytes()
     */
    @Test
    public void shouldReportNumberOfBytes() {
        assertEquals(4, createBitHelper(32).getNumberOfBytes());
        assertEquals(1, createBitHelper(8).getNumberOfBytes());
    }

    /**
     * Check if the number of bits are reported.
     *
     * @see BitHelper#getNumberOfBits()
     */
    @Test
    public void shouldReportNumberOfBits() {
        assertEquals(DEFAULT_BITS, helper.getNumberOfBits());
    }

    /**
     * Check if checked bits that are set on the value are reported.
     *
     * @see BitHelper#isBitSet(Number, int...)
     */
    @Test
    public void shouldCheckIfBitIsSet() {
        helper.isBitSet(DEFAULT_NUMBER, 1, 3);

        verify(helper, times(1)).createMask(1, 3);
        verify(helper, times(1)).isSetByMask(DEFAULT_NUMBER, DEFAULT_NUMBER);
        verifyNoMoreInteractions(helper);
    }

    /**
     * Check if checked bits on the value are set.
     *
     * @see BitHelper#setBit(Number, int...)
     */
    @Test
    public void shouldSetBit() {
        helper.setBit(DEFAULT_NUMBER, 1, 3);

        verify(helper, times(1)).createMask(1, 3);
        verify(helper, times(1)).setByMask(DEFAULT_NUMBER, DEFAULT_NUMBER);
        verifyNoMoreInteractions(helper);
    }

    /**
     * Check if checked bits on the value are unset.
     *
     * @see BitHelper#unsetBit(Number, int...)
     */
    @Test
    public void shouldUnsetBit() {
        helper.unsetBit(DEFAULT_NUMBER, 1, 3);

        verify(helper, times(1)).createMask(1, 3);
        verify(helper, times(1)).unsetByMask(DEFAULT_NUMBER, DEFAULT_NUMBER);
        verifyNoMoreInteractions(helper);
    }

    /**
     * Check if the LSB is reported for set values.
     *
     * @see BitHelper#isLsbSet(Number)
     */
    @Test
    public void shouldCheckIfLsbIsSet() {
        helper.isLsbSet(DEFAULT_NUMBER);

        verify(helper, times(1)).getLsbMask();
        verify(helper, times(1)).isSetByMask(DEFAULT_NUMBER, DEFAULT_NUMBER);
        verifyNoMoreInteractions(helper);
    }

    /**
     * Check if the LSB is set for values.
     *
     * @see BitHelper#setLsb(Number)
     */
    @Test
    public void shouldSetLsb() {
        helper.setLsb(DEFAULT_NUMBER);

        verify(helper, times(1)).getLsbMask();
        verify(helper, times(1)).setByMask(DEFAULT_NUMBER, DEFAULT_NUMBER);
        verifyNoMoreInteractions(helper);
    }

    /**
     * Check if the LSB is unset for values.
     *
     * @see BitHelper#unsetLsb(Number)
     */
    @Test
    public void shouldUnsetLsb() {
        helper.unsetLsb(DEFAULT_NUMBER);

        verify(helper, times(1)).getLsbMask();
        verify(helper, times(1)).unsetByMask(DEFAULT_NUMBER, DEFAULT_NUMBER);
        verifyNoMoreInteractions(helper);
    }

    /**
     * Check if the MSB is reported for values with the MSB set.
     *
     * @see BitHelper#isMsbSet(Number)
     */
    @Test
    public void shouldCheckIfMsbIsSet() {
        helper.isMsbSet(DEFAULT_NUMBER);

        verify(helper, times(1)).getMsbMask();
        verify(helper, times(1)).isSetByMask(DEFAULT_NUMBER, DEFAULT_NUMBER);
        verifyNoMoreInteractions(helper);
    }

    /**
     * Check if the MSB is set for values.
     *
     * @see BitHelper#setMsb(Number)
     */
    @Test
    public void shouldSetMsb() {
        helper.setMsb(DEFAULT_NUMBER);

        verify(helper, times(1)).getMsbMask();
        verify(helper, times(1)).setByMask(DEFAULT_NUMBER, DEFAULT_NUMBER);
        verifyNoMoreInteractions(helper);
    }

    /**
     * Check if the MSB is unset for values.
     *
     * @see BitHelper#unsetMsb(Number)
     */
    @Test
    public void shouldUnsetMsb() {
        helper.unsetMsb(DEFAULT_NUMBER);

        verify(helper, times(1)).getMsbMask();
        verify(helper, times(1)).unsetByMask(DEFAULT_NUMBER, DEFAULT_NUMBER);
        verifyNoMoreInteractions(helper);
    }

    /**
     * Check if binary strings are created from values.
     *
     * @see BitHelper#toBinaryString(Number)
     */
    @Test
    public void shouldCreateBinaryString() {
        assertEquals("1111111111111111", helper.toBinaryString(DEFAULT_NUMBER));
        assertEquals("0000000000000001", helper.toBinaryString(1));
    }

    /**
     * Create a Mockito spy implementation of a new BitHelper with the provided number of bits.
     * All calls invoke the real methods except for the abstract ones which call mock methods.
     *
     * @param bits The number of bits.
     *
     * @return The spy BitHelper.
     */
    private BitHelper<Number> createBitHelper(int bits) {
        BitHelper<Number> original = new BitHelper<Number>(bits) {
            @Override
            protected Number getLsbMask() {
                System.out.println("original getLsbMask...");
                return null;
            }

            @Override
            protected Number getMsbMask() {
                System.out.println("original getMsbMask...");
                return null;
            }

            @Override
            protected Number setByMask(Number value, Number mask) {
                System.out.println("original setByMask...");
                return null;
            }

            @Override
            protected Number unsetByMask(Number value, Number mask) {
                System.out.println("original unsetByMask...");
                return null;
            }

            @Override
            protected Number createMask(int... bitIndexes) {
                System.out.println("original createMask...");
                return null;
            }

            @Override
            protected boolean isSetByMask(Number value, Number mask) {
                System.out.println("original isSetByMask...");
                return false;
            }
        };
        BitHelper<Number> spy = spy(original);

        // Default behaviour.
        doReturn(false).when(spy).isSetByMask(any(Integer.class), any(Integer.class));
        doReturn(DEFAULT_NUMBER).when(spy).createMask(argThat(new IntVarargMatcher()));
        doReturn(DEFAULT_NUMBER).when(spy).unsetByMask(any(Number.class), any(Number.class));
        doReturn(DEFAULT_NUMBER).when(spy).setByMask(any(Number.class), any(Number.class));
        doReturn(DEFAULT_NUMBER).when(spy).getMsbMask();
        doReturn(DEFAULT_NUMBER).when(spy).getLsbMask();

        return spy;
    }

    /**
     * Matcher for int varargs.
     */
    private static class IntVarargMatcher extends ArgumentMatcher<int[]> implements VarargMatcher {

        @Override
        public boolean matches(Object argument) {
            return true;
        }
    }
}
