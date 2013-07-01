package nl.salp.util.bit;

/**
 * Base implementation for data type specific bitwise operation helper functions.
 *
 * @param <K> The type to work on.
 */
public abstract class BitHelper<K extends Number> {
    /**
     * The number of bits.
     */
    private final int bitLength;

    /**
     * Create a new BitHelper.
     *
     * @param bitLength The number of bits of the original value.
     */
    public BitHelper(int bitLength) {
        this.bitLength = bitLength;
    }

    /**
     * Check if the bit index is a valid index for the implementation (<code>-1 &lt; index &gt; bitLength</code>).
     *
     * @param index The index to check.
     *
     * @throws IllegalArgumentException When the index is not valid.
     */
    protected final void checkBitIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Bit index " + index + " is not a valid index, the minimum is 0");
        } else if (index > bitLength - 1) {
            throw new IllegalArgumentException("Bit index " + index + " is not a valid index, the maximum is " + (bitLength - 1));
        }
    }

    /**
     * Get the size of the original value type in bytes.
     *
     * @return The size in bytes.
     */
    public final int getNumberOfBytes() {
        return bitLength / 8;
    }

    /**
     * Get the size of the original value type in bits.
     *
     * @return The size in bits.
     */
    public final int getNumberOfBits() {
        return bitLength;
    }

    /**
     * Check if a bit at a certain position is set, counting from LSB, starting with 0.
     *
     * @param value The value to check the bit on.
     * @param bit   The indexes of the bits to check.
     *
     * @return <code>true</code> if the bit is set.
     *
     * @throws IllegalArgumentException When a bit index is not valid.
     */
    public final boolean isBitSet(K value, int... bit) {
        return isSetByMask(value, createMask(bit));
    }

    /**
     * Set the bits at certain positions to 1, counting from LSB, starting with 0.
     *
     * @param value The value to set the bit on.
     * @param bits  The bit indexes to set.
     *
     * @return The new value.
     *
     * @throws IllegalArgumentException When a bit index is not valid.
     */
    public final K setBit(K value, int... bits) {
        return setByMask(value, createMask(bits));
    }

    /**
     * Set the bits at certain positions to 0, counting from LSB, starting with 0.
     *
     * @param value The value to unset the bit on.
     * @param bits  The bit indexes to unset.
     *
     * @return The new value.
     *
     * @throws IllegalArgumentException When a bit index is not valid.
     */
    public final K unsetBit(K value, int... bits) {
        return unsetByMask(value, createMask(bits));
    }

    /**
     * Check if the LSB is set on the value.
     *
     * @return <code>true</code> if the LSB is set.
     */
    public final boolean isLsbSet(K value) {
        return isSetByMask(value, getLsbMask());
    }

    /**
     * Set the LSB to 1 on the value.
     *
     * @param value The value.
     *
     * @return The value with the LSB set to 1.
     */
    public final K setLsb(K value) {
        return setByMask(value, getLsbMask());
    }

    /**
     * Set the LSB to 0.
     *
     * @return The value with the LSB set to 0.
     */
    public final K unsetLsb(K value) {
        return unsetByMask(value, getLsbMask());
    }

    /**
     * Check if the MSB is set to 1 on a value.
     *
     * @param value The value.
     *
     * @return <code>true</code> if the MSB is set.
     */
    public final boolean isMsbSet(K value) {
        return isSetByMask(value, getMsbMask());
    }

    /**
     * Set the MSB to 1 on a value.
     *
     * @param value The value.
     *
     * @return The value with the MSB set to 1.
     */
    public final K setMsb(K value) {
        return setByMask(value, getMsbMask());
    }

    /**
     * Set the MSB to 0 on a value.
     *
     * @param value The value.
     *
     * @return The value with the MSB set to 0.
     */
    public final K unsetMsb(K value) {
        return unsetByMask(value, getMsbMask());
    }

    /**
     * Show the value as a binary string with the exact length of the bits in the original value type, left-padded with 0's.
     *
     * @return The binary string.
     */
    public final String toBinaryString(K value) {
        String str = String.format("%" + bitLength + "s", Long.toBinaryString(value.longValue())).replace(' ', '0');
        if (str.length() > bitLength) {
            str = str.substring(str.length() - bitLength);
        }
        return str;
    }

    /**
     * Get the value of the bit-pattern with only the LSB set.
     *
     * @return The LSB mask.
     */
    protected abstract K getLsbMask();

    /**
     * Get the value of the bit-pattern with only the MSB set.
     *
     * @return The MSB mask.
     */
    protected abstract K getMsbMask();

    /**
     * Set the bits that are set on the mask.
     *
     * @param value The value to set the bits on.
     * @param mask  The mask.
     *
     * @return The result.
     */
    protected abstract K setByMask(K value, K mask);

    /**
     * Unset the bits that are set on the mask.
     *
     * @param value The value to unset the bits on.
     * @param mask  The mask.
     *
     * @return The result.
     */
    protected abstract K unsetByMask(K value, K mask);

    /**
     * Create number with the bits on the provided positions set.
     *
     * @param bitIndexes The indexes of the bits set.
     *
     * @return The mask.
     *
     * @throws IllegalArgumentException When indexes are used that are outside the range of bits.
     * @see BitHelper#getNumberOfBits()
     */
    protected abstract K createMask(int... bitIndexes);

    /**
     * Check if the bits set in the mask are all set on the value.
     *
     * @param value The value.
     * @param mask  The mask.
     *
     * @return <code>true</code> if all bits are set.
     */
    protected abstract boolean isSetByMask(K value, K mask);

    @Override
    public boolean equals(Object obj) {
        boolean eq = false;
        if (obj != null && obj instanceof BitHelper) {
            eq = this.bitLength == ((BitHelper) obj).bitLength;
        }
        return eq;
    }

    @Override
    public int hashCode() {
        return bitLength;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BitHelper [ ");
        sb.append("bits: ").append(bitLength);
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * {@link BitHelper} implementation for 8-bit bytes.
     */
    public static final class ByteBitHelper extends BitHelper<Byte> {
        /**
         * Create a new ByteBitHelper.
         */
        public ByteBitHelper() {
            super(8);
        }

        @Override
        protected Byte getLsbMask() {
            return 1;
        }

        @Override
        protected Byte getMsbMask() {
            return Byte.MIN_VALUE;
        }

        @Override
        protected boolean isSetByMask(Byte value, Byte mask) {
            return (value & mask) == mask;
        }

        @Override
        protected Byte unsetByMask(Byte value, Byte mask) {
            return (byte) (value & ~mask);
        }

        @Override
        protected Byte createMask(int... bitIndexes) {
            byte result = 0;
            for (int idx : bitIndexes) {
                checkBitIndex(idx);
                result = (byte) (result | (1 << idx));
            }
            return result;
        }

        @Override
        protected Byte setByMask(Byte value, Byte mask) {
            return (byte) (value | mask);
        }
    }

    /**
     * {@link BitHelper} implementation for 16-bit shorts.
     */
    public static final class ShortBitHelper extends BitHelper<Short> {
        /**
         * Create a new ShortBitHelper.
         */
        public ShortBitHelper() {
            super(16);
        }

        @Override
        protected Short getLsbMask() {
            return 1;
        }

        @Override
        protected Short getMsbMask() {
            return Short.MIN_VALUE;
        }

        @Override
        protected boolean isSetByMask(Short value, Short mask) {
            return (value & mask) == mask;
        }

        @Override
        protected Short unsetByMask(Short value, Short mask) {
            return (short) (value & ~mask);
        }

        @Override
        protected Short createMask(int... bitIndexes) {
            short result = 0;
            for (int idx : bitIndexes) {
                checkBitIndex(idx);
                result = (short) (result | (1 << idx));
            }
            return result;
        }

        @Override
        protected Short setByMask(Short value, Short mask) {
            return (short) (value | mask);
        }
    }

    /**
     * {@link BitHelper} implementation for 32-bit integers.
     */
    public static final class IntegerBitHelper extends BitHelper<Integer> {
        /**
         * Create a new IntegerBitHelper.
         */
        public IntegerBitHelper() {
            super(32);
        }

        @Override
        protected Integer getLsbMask() {
            return 1;
        }

        @Override
        protected Integer getMsbMask() {
            return Integer.MIN_VALUE;
        }

        @Override
        protected boolean isSetByMask(Integer value, Integer mask) {
            return (value & mask) == mask;
        }

        @Override
        protected Integer unsetByMask(Integer value, Integer mask) {
            return value & ~mask;
        }

        @Override
        protected Integer createMask(int... bitIndexes) {
            int result = 0;
            for (int idx : bitIndexes) {
                checkBitIndex(idx);
                result = result | (1 << idx);
            }
            return result;
        }

        @Override
        protected Integer setByMask(Integer value, Integer mask) {
            return value | mask;
        }
    }

    /**
     * {@link BitHelper} implementation for 64-bit longs.
     */
    public static final class LongBitHelper extends BitHelper<Long> {
        /**
         * Create a new LongBitHelper.
         */
        public LongBitHelper() {
            super(32);
        }

        @Override
        protected Long getLsbMask() {
            return 1L;
        }

        @Override
        protected Long getMsbMask() {
            return Long.MIN_VALUE;
        }

        @Override
        protected boolean isSetByMask(Long value, Long mask) {
            return (value & mask) == mask;
        }

        @Override
        protected Long unsetByMask(Long value, Long mask) {
            return value & ~mask;
        }

        @Override
        protected Long createMask(int... bitIndexes) {
            long result = 0;
            for (int idx : bitIndexes) {
                checkBitIndex(idx);
                result = result | (1 << idx);
            }
            return result;
        }

        @Override
        protected Long setByMask(Long value, Long mask) {
            return value | mask;
        }
    }
}
