package ir.artanpg.commons.utils;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static ir.artanpg.commons.utils.StringUtils.EMPTY;
import static ir.artanpg.commons.utils.StringUtils.INDEX_NOT_FOUND;

/**
 * Provides utility methods for {@link File} or {@link Path} instances.
 *
 * @author Mohammad Yazdian
 */
public abstract class FileUtils {

    /**
     * The character used as a folder separator in file paths.
     */
    public static final char FOLDER_SEPARATOR_CHAR = '/';

    /**
     * The character used as a folder separator in Windows file paths.
     */
    public static final char WINDOWS_FOLDER_SEPARATOR_CHAR = '\\';

    /**
     * The character that separates the file extension from the file name.
     */
    public static final char DOT_CHAR = '.';

    /**
     * The string used as a folder separator in file paths.
     */
    public static final String FOLDER_SEPARATOR = "/";

    /**
     * The string used as a folder separator in Windows file paths.
     */
    public static final String WINDOWS_FOLDER_SEPARATOR = "\\";

    /**
     * The string representing the parent directory in file paths.
     */
    public static final String TOP_PATH = "..";

    /**
     * The string representing the current directory in file paths
     */
    public static final String CURRENT_PATH = ".";

    private FileUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Extracts just the filename from the given path by finding the substring
     * after the last folder separator.
     *
     * <p><strong>Note:</strong>This method handles both {@code Unix} and
     * {@code Windows} folder separators and returns the last segment of the
     * path as a {@link Path} object.
     *
     * <p>If the path string is {@code null}, {@code empty}, or {@code blank},
     * returns a {@code Path.of("")} object.
     *
     * <p>If the path string ends with a separator, returns {@code Path.of("")}
     * object.
     *
     * <p>If no separator is found, the entire path is returned as the
     * filename.
     *
     * <p>Examples:
     * <pre>
     *  FileUtils.getFilename(null);                   = Path.of("")
     *  FileUtils.getFilename("");                     = Path.of("")
     *  FileUtils.getFilename(" ");                    = Path.of("")
     *  FileUtils.getFilename("/path/to/");            = Path.of("")
     *  FileUtils.getFilename("file.txt");             = Path.of("file.txt")
     *  FileUtils.getFilename("/path/to/file.txt");    = Path.of("file.txt")
     *  FileUtils.getFilename("\\path\\to\\file.txt"); = Path.of("file.txt")
     * </pre>
     *
     * @param path the input path string
     * @return a {@link Path} object representing the filename, never {@code null}
     */
    public static Path getFilename(String path) {
        if (!StringUtils.hasText(path)) return getEmptyPath();

        path = path.trim();

        int separatorIndex = getFolderSeparatorIndex(path);

        String filename = separatorIndex != INDEX_NOT_FOUND ? path.substring(separatorIndex + 1) : path;
        return Path.of(filename);
    }

    /**
     * Extracts just the filename from the given path by finding the substring
     * after the last folder separator.
     *
     * <p><strong>Note:</strong>This method handles both {@code Unix} and
     * {@code Windows} folder separators and returns the last segment of the
     * path as a {@link Path} object.
     *
     * <p>If the path object is {@code null}, returns a {@code Path.of("")}
     * object.
     *
     * <p>Examples:
     * <pre>
     *  FileUtils.getFilename(null);                            = Path.of("")
     *  FileUtils.getFilename(Path.of(""));                     = Path.of("")
     *  FileUtils.getFilename(Path.of("/path/to/"));            = Path.of("")
     *  FileUtils.getFilename(Path.of("file.txt"));             = Path.of("file.txt")
     *  FileUtils.getFilename(Path.of("/path/to/file.txt"));    = Path.of("file.txt")
     *  FileUtils.getFilename(Path.of("\\path\\to\\file.txt")); = Path.of("file.txt")
     *  FileUtils.getFilename(Path.of(" "));                    = InvalidPathException
     *  FileUtils.getFilename(Path.of("//"));                   = InvalidPathException
     *  FileUtils.getFilename(Path.of(" file.txt"));            = InvalidPathException
     *  FileUtils.getFilename(Path.of("file.txt "));            = InvalidPathException
     * </pre>
     *
     * @param path the input path object
     * @return a {@link Path} object representing the filename, never {@code null}
     * @throws InvalidPathException if the {@code UNC} input path is incomplete, or
     */
    public static Path getFilename(Path path) {
        if (path == null) return getEmptyPath();

        String filename = path.getFileName().toString();
        return (!StringUtils.hasText(filename) || isDirectory(path)) ? getEmptyPath() : path.getFileName();
    }

    private static boolean isDirectory(Path path) {
        String filename = path.toString();

        return (filename.endsWith(FOLDER_SEPARATOR) || filename.endsWith(WINDOWS_FOLDER_SEPARATOR)) ||
                filename.lastIndexOf(DOT_CHAR) == INDEX_NOT_FOUND ||
                Files.isDirectory(path);
    }

    /**
     * Extracts the extension from a given file path. The method looks for the
     * last dot character in the path and returns the substring after it as the
     * file extension.
     *
     * <p>If the path is {@code null}, {@code empty}, or {@code blank}, an
     * {@code empty} string is returned.
     *
     * <p>If no dot exists in the path, returns an {@code empty} string.
     *
     * <p>If the last folder separator comes after the last dot, returns an
     * {@code empty} string.
     *
     * <p>Examples:
     * <pre>
     *  FileUtils.getFilenameExtension(null);                   = ""
     *  FileUtils.getFilenameExtension("");                     = ""
     *  FileUtils.getFilenameExtension(" ");                    = ""
     *  FileUtils.getFilenameExtension("document.pdf");         = "pdf"
     *  FileUtils.getFilenameExtension("archive.tar.gz");       = "gz"
     *  FileUtils.getFilenameExtension("/path/to/file.txt");    = "txt"
     *  FileUtils.getFilenameExtension("\\path\\to\\file.txt"); = "txt"
     *  FileUtils.getFilenameExtension("/home/user/file");      = ""
     * </pre>
     *
     * @param path the input path string
     * @return the file extension without the dot, or an {@code empty} string if not applicable
     */
    public static String getFilenameExtension(String path) {
        if (!StringUtils.hasText(path)) return EMPTY;

        int lastIndex = path.lastIndexOf(DOT_CHAR);
        if (lastIndex == INDEX_NOT_FOUND) return EMPTY;

        if (getFolderSeparatorIndex(path) > lastIndex) return EMPTY;

        return path.substring(lastIndex + 1);
    }

    /**
     * Maps a file to memory using multiple {@link MappedByteBuffer} instances,
     * each representing a segment of the file with the specified page size.
     *
     * <p>This method is useful for handling large files that exceed the size
     * limitations of a single memory mapping or require segmented processing.
     *
     * <p>The file is divided into segments of the specified {@code pageSize},
     * with the last segment potentially smaller to cover the remaining bytes.
     *
     * <p>The mapping mode determines whether the buffers are {@code read-only}
     * , {@code read-write} or {@code private}.
     *
     * <p>Examples:
     * <ul>
     *   <li>For an {@code empty} file, returns an {@code empty} list.</li>
     *   <li>For a {@code 2500-byte} file with {@code pageSize=1000} and
     *   {@code mode=READ_ONLY}, returns a list of three
     *   {@link MappedByteBuffer} objects: two of size {@code 1000} and one of
     *   size {@code 500}.</li>
     * </ul>
     *
     * @param channel  the {@link FileChannel} representing the file to map
     * @param mode     the mapping mode
     * @param pageSize the size of each mapped segment in bytes
     * @return a list of {@code MappedByteBuffer} instances representing the file segments
     * @throws IOException              if an I/O error occurs during mapping
     * @throws NullPointerException     if {@code channel} or {@code mode} is {@code null}
     * @throws IllegalArgumentException if {@code pageSize} is not positive
     */
    public static List<MappedByteBuffer> mapFileUsingMappedByteBuffers(FileChannel channel,
                                                                       FileChannel.MapMode mode,
                                                                       int pageSize) throws IOException {
        if (pageSize <= 0) throw new IllegalArgumentException("Page size must be positive");

        List<MappedByteBuffer> bufferList = new ArrayList<>();
        long channelSize = channel.size();
        long mappingStart = 0;
        int mappingSize = 0;

        for (long i = 0; mappingStart + mappingSize < channelSize; i++) {
            mappingSize = ((channelSize / pageSize) == i) ? (int) (channelSize - i * pageSize) : pageSize;
            mappingStart = i * pageSize;
            bufferList.add(channel.map(mode, mappingStart, mappingSize));
        }

        return bufferList;
    }

    private static Path getEmptyPath() {
        return Path.of(EMPTY);
    }

    private static int getFolderSeparatorIndex(String path) {
        int unixSeparator = path.lastIndexOf(FOLDER_SEPARATOR_CHAR);
        int windowsSeparator = path.lastIndexOf(WINDOWS_FOLDER_SEPARATOR_CHAR);

        return Math.max(unixSeparator, windowsSeparator);
    }
}
