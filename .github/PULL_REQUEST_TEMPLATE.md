## Summary

<!-- Briefly describe what this PR does and why. -->

## Type of Change

- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Refactoring
- [ ] Test improvement
- [ ] Chore / build / CI

## Related Issue

<!-- Link the related issue if any. Example: Fixes #123 -->

Closes #

## Checklist

### General
- [ ] I have read and followed the [Contributing Guide](../CONTRIBUTING.md)
- [ ] My code follows the project's coding standards (Checkstyle)
- [ ] I have performed a self-review of my own code
- [ ] I have added/updated documentation where appropriate
- [ ] All new and existing tests pass locally with:
  ```bash
  ./mvnw clean verify -Pexecute-tests,format
  ```

### Tests (if applicable)
- [ ] I have added tests that prove my fix/feature works
- [ ] New tests follow the project test conventions:
  - [ ] Test class name ends with `Tests`
  - [ ] Proper Javadoc on the test class (`@author YOUR_FULL_NAME`)
  - [ ] One `@Nested` class per method under test (ordered as in the source class)
  - [ ] Method names follow: `<MethodUnderTest>_Should<ExpectedBehavior>_When<Condition>`
  - [ ] Exception tests include the exception name after `ShouldThrow`
  - [ ] Every test method has a short English `@DisplayName`
  - [ ] Assertions use AssertJ `BDDAssertions` with `.as("...")`
  - [ ] Exception assertions use `.hasMessage(...)`
  - [ ] Sections separated with `// given`, `// when`, `// then` (or `// when & then`)
  - [ ] String emptiness checks cover `null`, `""`, and `"   "`
  - [ ] Helpers / test doubles are placed at the end after the separator comment
  - [ ] No unnecessary Reflection
  - [ ] `@SuppressWarnings("DataFlowIssue")` used where required for null arguments

## Additional Notes

<!-- Any extra information reviewers should know. -->
