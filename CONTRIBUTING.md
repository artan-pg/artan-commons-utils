# Contributing to Artan Commons Utils

Thank you for your interest in contributing!
We welcome bug reports, feature requests, documentation improvements, and code contributions.

By participating in this project, you agree to abide by our [Code of Conduct](CODE_OF_CONDUCT.md).

## Ways to Contribute

- **Report bugs** – Open an issue and describe the problem clearly.
- **Suggest features** – Open an issue explaining the use case and expected behavior.
- **Improve documentation** – Fix typos, clarify explanations, or add examples.
- **Submit code** – Fix bugs or implement new features via Pull Requests.

## Development Setup

### Prerequisites

- JDK 21 or higher
- Maven Wrapper is included (`./mvnw`)

### Getting Started

```bash
git clone https://github.com/artan-pg/artan-commons-utils.git
cd artan-commons-utils
./mvnw clean verify -Pexecute-tests,format
```

## Coding Standards

- Follow the project's Checkstyle rules `(src/checkstyle/checkstyle.xml)`.
- The build fails on Checkstyle violations when the `format` profile is active.
- Prefer clear, readable code over overly clever solutions.
- Avoid reflection unless absolutely necessary.

## Test Writing Guidelines

All unit tests must follow these rules strictly.

Pull Requests that do not comply will be requested to change.

### 1. General Rules

- Use the **BDD** style.
- Test class name must end with `Tests`.
- Every test class must have this Javadoc:

```java
/**
 * Unit tests for {@link ClassName}.
 *
 * @author {YOUR_FULL_NAME}
 */
```

- Aim for 100% code coverage (enforced by JaCoCo).
- Follow all rules defined in `src/checkstyle/checkstyle.xml`.

### 2. Structure – Nested Classes

- Create one `@Nested` class per method of the class under test.
- Order the nested classes according to the order of methods in the original class.
- Do not add comments to separate nested classes.

### 3. Test Method Naming

Use this exact pattern:

```text
<MethodUnderTest>_Should<ExpectedBehavior>_When<Condition>
```

Examples:

- `isEmpty_ShouldReturnTrue_WhenInputIsNull`
- `isEmpty_ShouldReturnTrue_WhenInputIsEmptyString`
- `isEmpty_ShouldReturnFalse_WhenInputHasContent`
- `parse_ShouldThrowIllegalArgumentException_WhenInputIsInvalid`

For exception scenarios, include the exception class name after `ShouldThrow`:

```text
..._ShouldThrowIllegalArgumentException_When...
```

### 4. Display Name

Every test method must have a short English `@DisplayName`:

```java
@DisplayName("Should return true when the input is null")
```

### 5. Assertions

- Use AssertJ, specifically static imports from `org.assertj.core.api.BDDAssertions`.
- Always use `.as("description")` with a clear but concise message.
- For exceptions, always verify the message with `.hasMessage(...)`.

Example:

```java
then(result)
    .

as("Result should be empty for null input")
    .

isEmpty();
```

### 6. Test Method Structure

Separate the sections with comments:

```java
// given
...

// when
...

// then
...
```

Rules:

- If there is no given part, still write an empty `// given` comment.
- If when and then are combined, use `// when & then`.

### 7. Special Cases

- When a method checks that a `String` is not blank/empty, you must write scenarios for:
  - `null`
  - `""`
  - `"    "` (blank)

- Prefer `Awaitility` instead of `Thread.sleep`.
- Avoid Reflection as much as possible.

### 8. Helper Methods and Test Doubles

Place all helper methods and test doubles at the end of the test class, after all nested classes, separated by this comment:

```java
// -------------------------------------------------------------------------
// Helpers and test doubles
// -------------------------------------------------------------------------
```

- Do not add Javadoc or comments to helper methods/classes.
- Define variables in the `// given` section on a single line whenever possible.

### 9. Example Skeleton

```java
/**
 * Unit tests for {@link StringUtils}.
 *
 * @author Mohammad Yazdian
 */
class StringUtilsTests {

    @Nested
    class IsEmpty {

        @Test
        @DisplayName("Should return true when the input is null")
        @SuppressWarnings("DataFlowIssue")
        void isEmpty_ShouldReturnTrue_WhenInputIsNull() {
            // given
            String input = null;

            // when
            boolean result = StringUtils.isEmpty(input);

            // then
            then(result)
                .as("isEmpty should return true for null")
                .isTrue();
        }

        // more tests...
    }

    // other nested classes...

    // -------------------------------------------------------------------------
    // Helpers and test doubles
    // -------------------------------------------------------------------------
}
```

## Pull Request Process

1. Create a feature/bugfix branch from `main`.
2. Write tests first (or together with the code) following the rules above.
3. Make sure the following command passes:
    ```bash
   ./mvnw clean verify -Pexecute-tests,format
    ```
4. Push your branch and open a Pull Request against `main`.
5. Fill in the PR template and link related issues if any.
6. Wait for review. Address all feedback.

## Commit Messages
We follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).

Format:

```text
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

### Allowed types

- `feat` – new feature
- `fix` – bug fix
- `docs` – documentation only
- `style` – formatting, missing semicolons, etc. (no code change)
- `refactor` – code change that neither fixes a bug nor adds a feature
- `test` – adding or correcting tests
- `chore` – maintenance tasks, build configuration, etc.
- `ci` – CI related changes
- `perf` – performance improvements

### Examples

```text
feat(string): add isBlank method

fix(array): correct null handling in contains

test(collection): add edge cases for empty lists

docs: improve installation section in README
```

## Questions?

Feel free to open an issue if anything is unclear.

Thank you for helping improve Artan Commons Utils!
