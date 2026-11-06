# Artan Commons Utils

[![CI](https://github.com/artan-pg/artan-commons-utils/actions/workflows/ci.yaml/badge.svg)](https://github.com/artan-pg/artan-commons-utils/actions/workflows/ci.yaml)
[![Coverage](https://img.shields.io/badge/coverage-pending-lightgrey)](https://github.com/artan-pg/artan-commons-utils)
[![Maven Central](https://img.shields.io/badge/maven%20central-pending-lightgrey)](https://central.sonatype.com/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java](https://img.shields.io/badge/Java-21%2B-orange)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9%2B-blue)](https://maven.apache.org/)
[![SemVer](https://img.shields.io/badge/SemVer-2.0.0-blue)](https://semver.org/)
[![Maintenance](https://img.shields.io/badge/Maintenance-Actively%20Maintained-brightgreen)](https://github.com/artan-pg/artan-commons-utils)

Miscellaneous utility classes for everyday Java development — focused on strings, arrays, collections, files and related
helpers.

## Features

- Utility methods for common string operations
- Array and collection helpers
- File-related utilities
- Designed for Java 21+
- Strict code quality enforcement (Checkstyle + JaCoCo)
- Fully tested with JUnit 5 and AssertJ

## Requirements

- **JDK 21** (required for compilation because of preview features)
- Maven 3.9+ (or use the included Maven Wrapper)
- A Maven toolchain pointing to JDK 21 (see below)

### Local toolchain setup

Create or edit `~/.m2/toolchains.xml`:

```xml

<?xml version="1.0" encoding="UTF-8"?>
<toolchains>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>21</version>
      <vendor>temurin</vendor>
    </provides>
    <configuration>
      <jdkHome>/path/to/your/jdk-21</jdkHome>
    </configuration>
  </toolchain>
</toolchains>
```

Then build as usual:

```bash
./mvnw clean install
```

To also run tests and enforce code style:

```bash
./mvnw clean install -Pexecute-tests,format
```

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
  <releases>
    <enabled>true</enabled>
  </releases>
  <snapshots>
    <enabled>false</enabled>
  </snapshots>
</repository>
<repository>
<id>maven-snapshots</id>
<url>https://repository.artanpg.ir/repository/maven-snapshots/</url>
<releases>
  <enabled>false</enabled>
</releases>
<snapshots>
  <enabled>true</enabled>
</snapshots>
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

All profiles are inherited from the parent POM (`artan-parent`). For the full description of each profile (activation
rules, coverage thresholds, release automation, etc.) see the parent project’s
README: https://github.com/artan-pg/artan-parent

| Profile         | Purpose                                                                                    | Example command                             |
|-----------------|--------------------------------------------------------------------------------------------|---------------------------------------------|
| `documentation` | Build the sources jar and the javadoc jar                                                  | `./mvnw -Pdocumentation package`            |
| `format`        | Check code style with Checkstyle                                                           | `./mvnw -Pformat validate`                  |
| `execute-tests` | Run tests and compute JaCoCo coverage                                                      | `./mvnw -Pexecute-tests verify`             |
| `major`         | Cut the next **major** release with an automatically computed version                      | `./mvnw -Dmajor initialize release:prepare` |
| `minor`         | Cut the next **minor** release with an automatically computed version                      | `./mvnw -Dminor initialize release:prepare` |
| `patch`         | Cut the next **patch** release with an automatically computed version                      | `./mvnw -Dpatch initialize release:prepare` |
| `sign`          | Sign artifacts with GPG and deploy the `.asc` signatures alongside                         | `./mvnw -Psign deploy`                      |
| `snapshot-repo` | Guard and prepare a **SNAPSHOT** deploy to the group Nexus (enforcer + publishing plugins) | `./mvnw -Psnapshot-repo deploy`             |
| `release-repo`  | Guard and prepare a **release** deploy to Maven Central (enforcer + publishing plugins)    | `./mvnw -Prelease-repo deploy`              |

### Recommended combinations

```bash
# Development / verification
./mvnw clean verify -Pexecute-tests,format

# Publishing a snapshot (used by the Deploy Snapshot workflow)
./mvnw clean deploy -Pdocumentation

# Publishing a release (used by the Deploy Release workflow)
./mvnw clean deploy -Pdocumentation
```

> **Note**: Tests are disabled by default `(maven.test.skip=true)`.
>
> Always activate the `execute-tests` profile when you want to run tests and coverage checks.

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
