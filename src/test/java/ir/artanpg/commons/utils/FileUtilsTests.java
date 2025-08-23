package ir.artanpg.commons.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import static ir.artanpg.commons.utils.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link FileUtils} class.
 */
public class FileUtilsTests {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void getFilename_ShouldReturnEmptyPath_WhenTheStringPathIsNullOrEmptyOrBlank(String path) {
        // When
        Path result = FileUtils.getFilename(path);

        // Then
        then(result.toString()).isEqualTo(EMPTY);
        then(result.isAbsolute()).isFalse();
    }

    @ParameterizedTest
    @NullSource
    void getFilename_ShouldReturnEmptyPath_WhenThePathIsNull(Path path) {
        // When
        Path result = FileUtils.getFilename(path);

        // Then
        then(result.toString()).isEqualTo(EMPTY);
        then(result.isAbsolute()).isFalse();
    }

    @Test
    void getFilename_ShouldReturnEmptyPath_WhenThePathWithEmptyString() {
        // Given
        Path objectPath = Path.of(EMPTY);

        // When
        Path result = FileUtils.getFilename(objectPath);

        // Then
        then(result.toString()).isEqualTo(EMPTY);
        then(result.isAbsolute()).isFalse();
    }

    @Test
    void getFilename_ShouldThrowInvalidPathException_WhenOnlySpace() {
        // Given
        String invalidPath = " ";

        // Then
        assertThatThrownBy(() -> Path.of(invalidPath))
                .isInstanceOf(InvalidPathException.class)
                .hasMessageContaining("Trailing char");
    }

    @ParameterizedTest
    @ValueSource(strings = {"   /path/to/file.txt", "/path/to/file.txt   "})
    void getFilename_ShouldThrowInvalidPathException_WhenTheStringPathHasWhitespace(String path) {
        // Then
        assertThatThrownBy(() -> Path.of(path))
                .isInstanceOf(InvalidPathException.class)
                .hasMessageContaining("Trailing char");
    }

    @Test
    void getFilename_ShouldThrowsInvalidPathException_WhenUNCPathIsMissingHostname() {
        // Given
        String invalidUNC = "//";

        // Then
        assertThatThrownBy(() -> Path.of(invalidUNC))
                .isInstanceOf(InvalidPathException.class)
                .hasMessageContaining("UNC path is missing hostname");
    }

    @ParameterizedTest
    @ValueSource(strings = {"/path/to/file.txt", "/ẞüñ/file.txt"})
    void getFilename_ShouldReturnPath_WhenTheStringUnixPathProvided(String path) {
        // Given
        Path objectPath = Path.of(path);

        // When
        Path resultStringPath = FileUtils.getFilename(path);
        Path resultObjectPath = FileUtils.getFilename(objectPath);

        // Then
        then(resultStringPath.toString()).isEqualTo("file.txt");
        then(resultStringPath.isAbsolute()).isFalse();

        then(resultObjectPath.toString()).isEqualTo("file.txt");
        then(resultObjectPath.isAbsolute()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"\\path\\to\\file.txt", "\\ẞüñ\\file.txt"})
    void getFilename_ShouldReturnPath_WhenTheStringWindowsPathProvided(String path) {
        // Given
        Path objectPath = Path.of(path);

        // When
        Path resultStringPath = FileUtils.getFilename(path);
        Path resultObjectPath = FileUtils.getFilename(objectPath);

        // Then
        then(resultStringPath.toString()).isEqualTo("file.txt");
        then(resultStringPath.isAbsolute()).isFalse();

        then(resultObjectPath.toString()).isEqualTo("file.txt");
        then(resultObjectPath.isAbsolute()).isFalse();
    }

    @Test
    void getFilename_ShouldReturnPath_WhenTheStringPathHasMixedSeparators() {
        // Given
        String stringPath = "/path\\to/file.txt";
        Path objectPath = Path.of(stringPath);

        //When
        Path resultStringPath = FileUtils.getFilename(stringPath);
        Path resultObjectPath = FileUtils.getFilename(objectPath);

        // Then
        then(resultStringPath.toString()).isEqualTo("file.txt");
        then(resultStringPath.isAbsolute()).isFalse();

        then(resultObjectPath.toString()).isEqualTo("file.txt");
        then(resultObjectPath.isAbsolute()).isFalse();
    }

    @Test
    void getFilename_ShouldReturnPath_WhenTheStringPathNoSeparatorsPresent() {
        // Given
        String stringPath = "file.txt";
        Path objectPath = Path.of(stringPath);

        // When
        Path resultStringPath = FileUtils.getFilename(stringPath);
        Path resultObjectPath = FileUtils.getFilename(objectPath);

        // Then
        then(resultStringPath.toString()).isEqualTo("file.txt");
        then(resultStringPath.isAbsolute()).isFalse();

        then(resultObjectPath.toString()).isEqualTo("file.txt");
        then(resultObjectPath.isAbsolute()).isFalse();
    }

    @Test
    void getFilename_ShouldReturnEmptyPath_WhenThePathEndsWithSeparator() {
        // Given
        String stringPath = "/path/to/";
        Path objectPath = Path.of(stringPath);

        // When
        Path resultStringPath = FileUtils.getFilename(stringPath);
        Path resultObjectPath = FileUtils.getFilename(objectPath);

        // Then
        then(resultStringPath.toString()).isEqualTo(EMPTY);
        then(resultStringPath.isAbsolute()).isFalse();

        then(resultObjectPath.toString()).isEqualTo(EMPTY);
        then(resultObjectPath.isAbsolute()).isFalse();
    }

    @Test
    void getFilename_ShouldReturnEmptyPath_WhenThePathIsOnlySeparators() {
        // Given
        String stringPath = "//";

        // When
        Path resultStringPath = FileUtils.getFilename(stringPath);

        // Then
        then(resultStringPath.toString()).isEqualTo(EMPTY);
        then(resultStringPath.isAbsolute()).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void getFilenameExtension_ShouldReturnsEmptyString_WhenPathIsNullOrEmptyOrBlank(String path) {
        assertEquals(EMPTY, FileUtils.getFilenameExtension(path));
    }

    @Test
    void getFilenameExtension_ShouldReturnsEmpty_WhenNoDotExists() {
        assertEquals(EMPTY, FileUtils.getFilenameExtension("filename"));
    }

    @Test
    void getFilenameExtension_ShouldReturnsEmpty_WhenFolderSeparatorAfterDot() {
        assertEquals(EMPTY, FileUtils.getFilenameExtension("folder.ext/file"));
    }

    @Test
    void getFilenameExtension_ShouldReturnsExtension_WhenSimpleFilenameHasExtension() {
        assertEquals("txt", FileUtils.getFilenameExtension("readme.txt"));
    }

    @Test
    void getFilenameExtension_ShouldReturnsLastExtension_WhenMultipleDotsExist() {
        assertEquals("gz", FileUtils.getFilenameExtension("archive.tar.gz"));
    }

    @Test
    void getFilenameExtension_ShouldReturnsExtension_WhenPathContainsUnixFolders() {
        assertEquals("pdf", FileUtils.getFilenameExtension("/home/user/document.pdf"));
    }

    @Test
    void getFilenameExtension_ShouldReturnsExtension_WhenPathContainsWindowsFolders() {
        assertEquals("pdf", FileUtils.getFilenameExtension("\\home\\user\\document.pdf"));
    }

    @Test
    void getFilenameExtension_ShouldReturnExtension_WhenPathHasUnicode() {
        assertEquals("txt", FileUtils.getFilenameExtension("/ẞüñ/file.txt"));
    }
}
