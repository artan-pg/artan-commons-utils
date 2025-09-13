//package ir.artanpg.commons.utils;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.NullAndEmptySource;
//import org.junit.jupiter.params.provider.NullSource;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.nio.MappedByteBuffer;
//import java.nio.channels.FileChannel;
//import java.nio.file.InvalidPathException;
//import java.nio.file.Path;
//import java.nio.file.StandardOpenOption;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.assertj.core.api.BDDAssertions.then;
//
///**
// * Unit tests for the {@link FileUtils} class.
// *
// * @author Mohammad Yazdian
// */
//@SuppressWarnings("DataFlowIssue")
//class FileUtilsTests {
//
//    @ParameterizedTest
//    @NullAndEmptySource
//    @ValueSource(strings = " ")
//    void getFilename_ShouldReturnEmptyPath_WhenTheStringPathIsNullOrEmptyOrBlank(String input) {
//        // When
//        Path actual = FileUtils.getFilename(input);
//
//        // Then
//        then(actual.toString()).isEmpty();
//        then(actual.isAbsolute()).isFalse();
//    }
//
//    @ParameterizedTest
//    @NullSource
//    void getFilename_ShouldReturnEmptyPath_WhenThePathIsNull(Path input) {
//        // When
//        Path actual = FileUtils.getFilename(input);
//
//        // Then
//        then(actual.toString()).isEmpty();
//        then(actual.isAbsolute()).isFalse();
//    }
//
//    @Test
//    void getFilename_ShouldReturnEmptyPath_WhenThePathWithEmptyString() {
//        // Given
//        Path input = Path.of("");
//
//        // When
//        Path actual = FileUtils.getFilename(input);
//
//        // Then
//        then(actual.toString()).isEmpty();
//        then(actual.isAbsolute()).isFalse();
//    }
//
//    @Test
//    void getFilename_ShouldThrowInvalidPathException_WhenOnlySpace() {
//        // Given
//        String input = " ";
//
//        // Then
//        assertThatThrownBy(() -> Path.of(input))
//                .isInstanceOf(InvalidPathException.class)
//                .hasMessageContaining("Trailing char");
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"   /path/to/file.txt", "/path/to/file.txt   "})
//    void getFilename_ShouldThrowInvalidPathException_WhenTheStringPathHasWhitespace(String path) {
//        // Then
//        assertThatThrownBy(() -> Path.of(path))
//                .isInstanceOf(InvalidPathException.class)
//                .hasMessageContaining("Trailing char");
//    }
//
//    @Test
//    void getFilename_ShouldThrowsInvalidPathException_WhenUNCPathIsMissingHostname() {
//        // Given
//        String input = "//";
//
//        // Then
//        assertThatThrownBy(() -> Path.of(input))
//                .isInstanceOf(InvalidPathException.class)
//                .hasMessageContaining("UNC path is missing hostname");
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"/path/to/file.txt", "/ẞüñ/file.txt"})
//    void getFilename_ShouldReturnPath_WhenTheStringUnixPathProvided(String input) {
//        // When
//        Path actualStringPath = FileUtils.getFilename(input);
//        Path actualObjectPath = FileUtils.getFilename(Path.of(input));
//
//        // Then
//        then(actualStringPath.toString()).isEqualTo("file.txt");
//        then(actualStringPath.isAbsolute()).isFalse();
//
//        then(actualObjectPath.toString()).isEqualTo("file.txt");
//        then(actualObjectPath.isAbsolute()).isFalse();
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"\\path\\to\\file.txt", "\\ẞüñ\\file.txt"})
//    void getFilename_ShouldReturnPath_WhenTheStringWindowsPathProvided(String input) {
//        // When
//        Path actualStringPath = FileUtils.getFilename(input);
//        Path actualObjectPath = FileUtils.getFilename(Path.of(input));
//
//        // Then
//        then(actualStringPath.toString()).isEqualTo("file.txt");
//        then(actualStringPath.isAbsolute()).isFalse();
//
//        then(actualObjectPath.toString()).isEqualTo("file.txt");
//        then(actualObjectPath.isAbsolute()).isFalse();
//    }
//
//    @Test
//    void getFilename_ShouldReturnPath_WhenTheStringPathHasMixedSeparators() {
//        // Given
//        String input = "/path\\to/file.txt";
//
//        //When
//        Path actualStringPath = FileUtils.getFilename(input);
//        Path actualObjectPath = FileUtils.getFilename(Path.of(input));
//
//        // Then
//        then(actualStringPath.toString()).isEqualTo("file.txt");
//        then(actualStringPath.isAbsolute()).isFalse();
//
//        then(actualObjectPath.toString()).isEqualTo("file.txt");
//        then(actualObjectPath.isAbsolute()).isFalse();
//    }
//
//    @Test
//    void getFilename_ShouldReturnPath_WhenTheStringPathNoSeparatorsPresent() {
//        // Given
//        String input = "file.txt";
//
//        // When
//        Path actualStringPath = FileUtils.getFilename(input);
//        Path actualObjectPath = FileUtils.getFilename(Path.of(input));
//
//        // Then
//        then(actualStringPath.toString()).isEqualTo("file.txt");
//        then(actualStringPath.isAbsolute()).isFalse();
//
//        then(actualObjectPath.toString()).isEqualTo("file.txt");
//        then(actualObjectPath.isAbsolute()).isFalse();
//    }
//
//    @Test
//    void getFilename_ShouldReturnEmptyPath_WhenThePathEndsWithSeparator() {
//        // Given
//        String input = "/path/to/";
//
//        // When
//        Path actualStringPath = FileUtils.getFilename(input);
//        Path actualObjectPath = FileUtils.getFilename(Path.of(input));
//
//        // Then
//        then(actualStringPath.toString()).isEmpty();
//        then(actualStringPath.isAbsolute()).isFalse();
//
//        then(actualObjectPath.toString()).isEmpty();
//        then(actualObjectPath.isAbsolute()).isFalse();
//    }
//
//    @Test
//    void getFilename_ShouldReturnEmptyPath_WhenThePathIsOnlySeparators() {
//        // Given
//        String input = "//";
//
//        // When
//        Path actual = FileUtils.getFilename(input);
//
//        // Then
//        then(actual.toString()).isEmpty();
//        then(actual.isAbsolute()).isFalse();
//    }
//
//    @Test
//    void mapFileUsingMappedByteBuffers_ShouldReturnEmptyList_WhenFileIsEmpty() throws IOException, URISyntaxException {
//        // Given
//        List<MappedByteBuffer> actual;
//        Path file = Path.of(getClass().getClassLoader().getResource("files/empty.txt").toURI());
//
//        // When
//        try (FileChannel channel = FileChannel.open(file, StandardOpenOption.READ)) {
//            actual = FileUtils.mapFileUsingMappedByteBuffers(channel, FileChannel.MapMode.READ_ONLY, 1000);
//        }
//
//        // Then
//        then(actual).isEmpty();
//    }
//
//    @Test
//    void mapFileUsingMappedByteBuffers_ShouldReturnSingleBuffer_WhenFileSizeLessThanPageSize()
//            throws IOException, URISyntaxException {
//        // Given
//        List<MappedByteBuffer> actual;
//        Path file = Path.of(getClass().getClassLoader().getResource("files/small.txt").toURI());
//
//        // When
//        try (FileChannel channel = FileChannel.open(file, StandardOpenOption.READ)) {
//            actual = FileUtils.mapFileUsingMappedByteBuffers(channel, FileChannel.MapMode.READ_ONLY, 1000);
//        }
//
//        // Then
//        then(actual)
//                .hasSize(1)
//                .allSatisfy(buffer -> then(buffer.capacity()).isEqualTo(4));
//    }
//
//    @Test
//    void mapFileUsingMappedByteBuffers_ShouldReturnMultipleBuffers_WhenFileSizeExceedsPageSize()
//            throws IOException, URISyntaxException {
//        // Given
//        List<MappedByteBuffer> actual;
//        Path file = Path.of(getClass().getClassLoader().getResource("files/large.txt").toURI());
//
//        // When
//        try (FileChannel channel = FileChannel.open(file, StandardOpenOption.READ)) {
//            actual = FileUtils.mapFileUsingMappedByteBuffers(channel, FileChannel.MapMode.READ_ONLY, 1000);
//        }
//
//        // Then
//        then(actual).hasSize(3)
//                .satisfies(buffer -> then(buffer.get(0).capacity()).isEqualTo(1000))
//                .satisfies(buffer -> then(buffer.get(1).capacity()).isEqualTo(1000))
//                .satisfies(buffer -> then(buffer.get(2).capacity()).isEqualTo(500));
//    }
//
//    @Test
//    void mapFileUsingMappedByteBuffers_ShouldThrowNullPointerException_WhenChannelIsNull() {
//        // Then
//        assertThatThrownBy(() -> FileUtils.mapFileUsingMappedByteBuffers(null, FileChannel.MapMode.READ_ONLY, 1000))
//                .isInstanceOf(NullPointerException.class);
//    }
//
//    @Test
//    void mapFileUsingMappedByteBuffers_ShouldThrowNullPointerException_WhenModeIsNull()
//            throws IOException, URISyntaxException {
//        // Given
//        Path file = Path.of(getClass().getClassLoader().getResource("files/small.txt").toURI());
//
//        // Then
//        try (FileChannel channel = FileChannel.open(file, StandardOpenOption.READ)) {
//            assertThatThrownBy(() -> FileUtils.mapFileUsingMappedByteBuffers(channel, null, 1000))
//                    .isInstanceOf(NullPointerException.class);
//        }
//    }
//
//    @Test
//    void mapFileUsingMappedByteBuffers_ShouldThrowIllegalArgumentException_WhenPageSizeIsNonPositive()
//            throws IOException, URISyntaxException {
//        // Given
//        Path file = Path.of(getClass().getClassLoader().getResource("files/small.txt").toURI());
//
//        // Then
//        try (FileChannel channel = FileChannel.open(file, StandardOpenOption.READ)) {
//            assertThatThrownBy(() -> FileUtils.mapFileUsingMappedByteBuffers(channel, FileChannel.MapMode.READ_ONLY, 0))
//                    .isInstanceOf(IllegalArgumentException.class);
//        }
//    }
//
//    @ParameterizedTest
//    @NullAndEmptySource
//    @ValueSource(strings = " ")
//    void getFilenameExtension_ShouldReturnsEmptyString_WhenPathIsNullOrEmptyOrBlank(String input) {
//        // When
//        String actual = FileUtils.getFilenameExtension(input);
//
//        // Then
//        then(actual).isEmpty();
//    }
//
//    @Test
//    void getFilenameExtension_ShouldReturnsEmpty_WhenNoDotExists() {
//        // Given
//        String input = "filename";
//
//        // When
//        String actual = FileUtils.getFilenameExtension(input);
//
//        // Then
//        then(actual).isEmpty();
//    }
//
//    @Test
//    void getFilenameExtension_ShouldReturnsEmpty_WhenFolderSeparatorAfterDot() {
//        // Given
//        String input = "folder.ext/file";
//
//        // When
//        String actual = FileUtils.getFilenameExtension(input);
//
//        // Then
//        then(actual).isEmpty();
//    }
//
//    @Test
//    void getFilenameExtension_ShouldReturnsExtension_WhenSimpleFilenameHasExtension() {
//        // Given
//        String input = "readme.txt";
//
//        // When
//        String actual = FileUtils.getFilenameExtension(input);
//
//        // Then
//        then(actual).isEqualTo("txt");
//    }
//
//    @Test
//    void getFilenameExtension_ShouldReturnsLastExtension_WhenMultipleDotsExist() {
//        // Given
//        String input = "archive.tar.gz";
//
//        // When
//        String actual = FileUtils.getFilenameExtension(input);
//
//        //Then
//        then(actual).isEqualTo("gz");
//    }
//
//    @Test
//    void getFilenameExtension_ShouldReturnsExtension_WhenPathContainsUnixFolders() {
//        // Given
//        String input = "/home/user/document.pdf";
//
//        // When
//        String actual = FileUtils.getFilenameExtension(input);
//
//        //Then
//        then(actual).isEqualTo("pdf");
//    }
//
//    @Test
//    void getFilenameExtension_ShouldReturnsExtension_WhenPathContainsWindowsFolders() {
//        // Given
//        String input = "\\home\\user\\document.pdf";
//
//        // When
//        String actual = FileUtils.getFilenameExtension(input);
//
//        // Then
//        then(actual).isEqualTo("pdf");
//    }
//
//    @Test
//    void getFilenameExtension_ShouldReturnExtension_WhenPathHasUnicode() {
//        // Given
//        String input = "/ẞüñ/file.txt";
//
//        // When
//        String actual = FileUtils.getFilenameExtension(input);
//
//        // Then
//        then(actual).isEqualTo("txt");
//    }
//}
