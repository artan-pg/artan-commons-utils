# Artan Commons Utils

[![CI](https://github.com/artan-pg/artan-commons-utils/actions/workflows/ci.yaml/badge.svg)](https://github.com/artan-pg/artan-commons-utils/actions/workflows/ci.yaml)
[![Coverage](https://img.shields.io/badge/coverage-pending-lightgrey)](https://github.com/artan-pg/artan-commons-utils)
[![Maven Central](https://img.shields.io/badge/maven%20central-pending-lightgrey)](https://central.sonatype.com/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java](https://img.shields.io/badge/Java-21%2B-orange)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9%2B-blue)](https://maven.apache.org/)
[![SemVer](https://img.shields.io/badge/SemVer-2.0.0-blue)](https://semver.org/)
[![Maintenance](https://img.shields.io/badge/Maintenance-Actively%20Maintained-brightgreen)](https://github.com/artan-pg/artan-commons-utils)

Miscellaneous utility classes for everyday Java development — focused on strings, arrays, collections, files and related helpers.

## Features

- Utility methods for common string operations
- Array and collection helpers
- File-related utilities
- Designed for Java 21+
- Strict code quality enforcement (Checkstyle + JaCoCo)
- Fully tested with JUnit 5 and AssertJ

## Requirements

- **JDK 21** or higher (CI also verifies against newer JDK versions)
- Maven 3.9+ (or use the included Maven Wrapper)

## Installation

The artifacts are published to the public ArtanPG Nexus repository.

### Release version

```xml
<dependency>
  <groupId>ir.artanpg.commons</groupId>
  <artifactId>commons-utils</artifactId>
  <version>1.0.0</version> <!-- replace with the latest release -->
</dependency>
```

### Snapshot version

```xml
<dependency>
  <groupId>ir.artanpg.commons</groupId>
  <artifactId>commons-utils</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Make sure the following repositories are available in your `pom.xml` or `settings.xml`:

```xml
<repository>
  <id>maven-releases</id>
  <url>https://repository.artanpg.ir/repository/maven-releases/</url>
  <releases><enabled>true</enabled></releases>
  <snapshots><enabled>false</enabled></snapshots>
</repository>
<repository>
  <id>maven-snapshots</id>
  <url>https://repository.artanpg.ir/repository/maven-snapshots/</url>
  <releases><enabled>false</enabled></releases>
  <snapshots><enabled>true</enabled></snapshots>
</repository>
```

> **Note:** Publication to Maven Central is planned. Until then, use the Nexus repository above.

## Building from Source

```bash
./mvnw clean install
```

To also run tests and enforce code style:

```bash
./mvnw clean install -Pexecute-tests,format
```

## Maven Profiles

| Profile         | Purpose                                                   | When to use                                |
|-----------------|-----------------------------------------------------------|--------------------------------------------|
| `execute-tests` | Enables unit/integration tests and JaCoCo coverage checks | Local development, CI, before opening a PR |
| `format`        | Runs Checkstyle and fails the build on style violations   | Local development, CI, before opening a PR |
| `documentation` | Attaches source and Javadoc jars                          | When publishing a release or snapshot      |

### Recommended combinations

```bash
# Development / verification
./mvnw clean verify -Pexecute-tests,format

# Publishing (used by release & snapshot workflows)
./mvnw clean deploy -Pdocumentation
```

## Running Tests & Quality Checks

```bash
# Run tests + coverage
./mvnw test -Pexecute-tests

# Check code style only
./mvnw validate -Pformat
```

The project uses:
 - Coding standards
 - Test conventions
 - Pull request process

## License

This project is licensed under the Apache License 2.0.
