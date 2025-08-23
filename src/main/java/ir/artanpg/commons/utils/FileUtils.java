package ir.artanpg.commons.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

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
        if (!StringUtils.hasText(path)) return Path.of(EMPTY);

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

    private static Path getEmptyPath() {
        return Path.of(EMPTY);
    }

    private static int getFolderSeparatorIndex(String path) {
        int unixSeparator = path.lastIndexOf(FOLDER_SEPARATOR_CHAR);
        int windowsSeparator = path.lastIndexOf(WINDOWS_FOLDER_SEPARATOR_CHAR);

        return Math.max(unixSeparator, windowsSeparator);
    }
}
