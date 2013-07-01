package nl.salp.util.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Stream reader for reading the content of a stream into a byte[] using specified sized chunks of data.
 */
public class ChunkedByteStreamReader {
    /**
     * The default number of bytes in a chunk.
     */
    private static final int DEFAULT_CHUNK_BYTES = 128;

    /**
     * The size of a chunk in bytes.
     */
    private final int chunkBytes;

    /**
     * Create a new ChunkedByteStreamReader with the default size (128 bytes).
     */
    public ChunkedByteStreamReader() {
        this(DEFAULT_CHUNK_BYTES);
    }

    /**
     * Create a new ChunkedByteStreamReader with a specified chunk size.
     *
     * @param chunkBytes The size of the chunks to read.
     */
    public ChunkedByteStreamReader(int chunkBytes) {
        this.chunkBytes = chunkBytes;
    }

    /**
     * Read all the data from a stream.
     *
     * @param stream The stream to read.
     *
     * @return The data from the stream as byte[].
     *
     * @throws IOException              When reading failed.
     * @throws IllegalArgumentException When a null stream or a stream without data was provided.
     */
    public byte[] read(InputStream stream) throws IOException {
        if (stream == null) {
            throw new IllegalArgumentException("Tried to read from a null stream.");
        }
        if (stream.available() == 0) {
            throw new IllegalArgumentException("No data available while trying to read the stream.");
        }

        byte[] result = new byte[0];
        byte[] chunk = new byte[this.chunkBytes];
        int k;
        while ((k = stream.read(chunk, 0, chunk.length)) > -1) {
            byte[] readResult = new byte[result.length + k];
            System.arraycopy(result, 0, readResult, 0, result.length);
            System.arraycopy(chunk, 0, readResult, result.length, k);
            result = readResult;
        }
        return result;
    }
}