package nl.salp.util.bit;

import nl.salp.util.bit.BitHelper.ByteBitHelper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link ByteBitHelper}, also testing the underlying logic of {@link BitHelper} in combination with the 8-bit implementation.
 *
 * @see ByteBitHelper
 */
public class ByteBitHelperTest {

    /**
     * The class under test.
     */
    private BitHelper<Byte> helper;

    @Before
    public void setUp() {
        helper = new ByteBitHelper();
    }

    /**
     * Check if the correct number of bits are reported for a byte implementation.
     *
     * @see BitHelper#getNumberOfBits()
     */
    @Test
    public void shouldGetNumberOfBits() {
        assertEquals(8, helper.getNumberOfBits());
    }

    /**
     * Check if the correct number of bytes are reported for a byte implementation.
     *
     * @see BitHelper#getNumberOfBytes()
     */
    @Test
    public void shouldGetNumberOfBytes() {
        assertEquals(1, helper.getNumberOfBytes());
    }

    /**
     * Check if a mask is returned with only the LSB set.
     *
     * @see BitHelper.ByteBitHelper#getLsbMask()
     */
    @Test
    public void shouldReturnLsbMask() {
        byte expected = 1; // byte 1 = 0000 0001

        byte result = helper.getLsbMask();

        assertEquals(expected, result);
    }

    /**
     * Check if a mask is returned with only the MSB set.
     *
     * @see BitHelper.ByteBitHelper#getMsbMask()
     */
    @Test
    public void shouldReturnMsbMask() {
        byte expected = -128; // byte -128 = 1000 0000

        byte result = helper.getMsbMask();

        assertEquals(expected, result);
    }

    /**
     * Check if a mask is reported as set when all bits are set on the value.
     *
     * @see BitHelper.ByteBitHelper#isSetByMask(Byte, Byte)
     */
    @Test
    public void shouldReportMaskIsSet() {
        byte value = 127;   // byte 127 = 0111 1111
        byte mask = 125;    // byte 125 = 0111 1101

        assertTrue(helper.isSetByMask(value, mask));
    }

    /**
     * Check if a mask is reported as not set when not all bits are set on the value.
     *
     * @see BitHelper.ByteBitHelper#isSetByMask(Byte, Byte)
     */
    @Test
    public void shouldReportMaskIsNotSetForMissingBit() {
        byte value = 125;   // byte 125 = 0111 1101
        byte mask = 127;    // byte 127 = 0111 1111

        assertFalse(helper.isSetByMask(value, mask));
    }

    /**
     * Check if sets bits on the value are unset when they are set on the mask.
     *
     * @see ByteBitHelper#unsetByMask(Byte, Byte)
     */
    @Test
    public void shouldUnsetByMask() {
        byte value = 127;       // byte 127 = 0111 1111
        byte mask = 2;          // byte   2 = 0000 0010
        byte expected = 125;    // byte 125 = 0111 1101

        byte result = helper.unsetByMask(value, mask);

        assertEquals(expected, result);
    }

    /**
     * Check if unset bits on the value are still unset when they are set on the mask.
     *
     * @see ByteBitHelper#unsetByMask(Byte, Byte)
     */
    @Test
    public void shouldLeaveUnsetBitsUnsetByMask() {
        byte value = 125;       // byte 125 = 0111 1101
        byte mask = 2;          // byte   2 = 0000 0010
        byte expected = 125;    // byte 125 = 0111 1101

        byte result = helper.unsetByMask(value, mask);

        assertEquals(expected, result);
    }

    /**
     * Check if the bits on the value are set when they are set on the mask.
     *
     * @see ByteBitHelper#setByMask(Byte, Byte)
     */
    @Test
    public void shouldSetByMask() {
        byte value = 125;       // byte 125 = 0111 1101
        byte mask = 2;          // byte   2 = 0000 0010
        byte expected = 127;    // byte 125 = 0111 1111

        byte result = helper.setByMask(value, mask);

        assertEquals(expected, result);
    }

    /**
     * Check if set bits on the value are still set when they are set on the mask.
     *
     * @see ByteBitHelper#unsetByMask(Byte, Byte)
     */
    @Test
    public void shouldLeaveSetBitsSetByMask() {
        byte value = 125;       // byte 125 = 0111 1101
        byte mask = 1;          // byte   1 = 0000 0001
        byte expected = 125;    // byte 125 = 0111 1101

        byte result = helper.setByMask(value, mask);

        assertEquals(expected, result);
    }

    /**
     * Check if a bit mask is created.
     *
     * @see ByteBitHelper#createMask(int...)
     */
    @Test
    public void shouldCreateMask() {
        byte expected = 125;    // byte 125 = 0111 1101

        byte result = helper.createMask(0, 2, 3, 4, 5, 6);

        assertEquals(expected, result);
    }

    /**
     * Check that invalid indexes while creating a mask result in an IllegalArgumentException.
     *
     * @see ByteBitHelper#createMask(int...)
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldCheckForInvalidIndexesWhileCreatingMask() {
        helper.createMask(1, 3, 16);
    }
}
