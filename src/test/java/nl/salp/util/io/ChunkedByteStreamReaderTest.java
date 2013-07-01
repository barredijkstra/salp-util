package nl.salp.util.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ChunkedByteStreamReaderTest {
    /**
     * The stream to use for the tests.
     * Can be set via {@link ChunkedByteStreamReaderTest#generateDataAndProvideForStream(int)}.
     */
    private InputStream stream;

    @Test
    public void shouldReadSingleCompleteChunk() throws Exception {
        byte[] data = generateDataAndProvideForStream(128);

        ChunkedByteStreamReader reader = new ChunkedByteStreamReader(128);
        byte[] output = reader.read(stream);

        assertArrayEquals(data, output);
    }

    @Test
    public void shouldReadIncompleteChunk() throws Exception {
        byte[] data = generateDataAndProvideForStream(64);

        ChunkedByteStreamReader reader = new ChunkedByteStreamReader(128);
        byte[] output = reader.read(stream);

        assertArrayEquals(data, output);
    }

    @Test
    public void shouldReadMultipleCompleteChunks() throws Exception {
        byte[] data = generateDataAndProvideForStream(256);

        ChunkedByteStreamReader reader = new ChunkedByteStreamReader(128);
        byte[] output = reader.read(stream);

        assertArrayEquals(data, output);
    }

    @Test
    public void shouldReadMultipleChunksWithIncompleteChunk() throws Exception {
        byte[] data = generateDataAndProvideForStream(300);

        ChunkedByteStreamReader reader = new ChunkedByteStreamReader(128);
        byte[] output = reader.read(stream);

        assertArrayEquals(data, output);
    }

    /**
     * Generate random data and provided it as the stream ({@link ChunkedByteStreamReaderTest#stream}) data.
     *
     * @param bytes The number of bytes.
     *
     * @return The generated data.
     *
     * @throws Exception When setting data for the stream failed.
     */
    private byte[] generateDataAndProvideForStream(int bytes) throws Exception {
        final byte[] data = generateData(bytes);
        stream = new ByteArrayInputStream(data);
        return data;
    }

    /**
     * Generated a byte[] of a defined length with random data.
     *
     * @param bytes The number of bytes to generate.
     *
     * @return The byte[] with random data.
     */
    private static byte[] generateData(int bytes) {
        byte[] data = new byte[bytes];
        Random rng = new Random();
        rng.nextBytes(data);
        return data;
    }
}
