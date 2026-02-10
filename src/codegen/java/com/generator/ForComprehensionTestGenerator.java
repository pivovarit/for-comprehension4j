package com.generator;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ForComprehensionTestGenerator {

    static String generate(int arity) {
        var eagerOptionalTests = new StringBuilder();
        for (int i = 2; i <= arity; i++) {
            eagerOptionalTests.append(generateEagerOptionalTest(i));
        }

        var eagerStreamTests = new StringBuilder();
        for (int i = 2; i <= arity; i++) {
            eagerStreamTests.append(generateEagerStreamTest(i));
            eagerStreamTests.append(generateEagerStreamEmptyTest(i));
        }

        var eagerIterableTests = new StringBuilder();
        for (int i = 2; i <= arity; i++) {
            eagerIterableTests.append(generateEagerIterableTest(i));
            eagerIterableTests.append(generateEagerIterableEmptyTest(i));
        }

        String lazyTests = generateLazyOptionalTest()
                           + generateLazyStreamTest()
                           + generateLazyStreamEmptyTest()
                           + generateLazyIterableTest()
                           + generateLazyIterableEmptyTest();

        return """
          package com.pivovarit.forc;

          import org.junit.jupiter.api.Nested;
          import org.junit.jupiter.api.Test;

          import java.util.Collections;
          import java.util.List;
          import java.util.Optional;
          import java.util.stream.Stream;

          import static org.assertj.core.api.Assertions.assertThat;

          class ForComprehensionTest {

              @Nested
              class EagerOptional {
          %s
              }

              @Nested
              class EagerStream {
          %s
              }

              @Nested
              class EagerIterable {
          %s
              }

              @Nested
              class Lazy {
          %s
              }
          }
          """.formatted(
          indent(eagerOptionalTests.toString(), 4),
          indent(eagerStreamTests.toString(), 4),
          indent(eagerIterableTests.toString(), 4),
          indent(lazyTests, 4));
    }

    private static String indent(String text, int spaces) {
        var prefix = " ".repeat(spaces);
        return text.lines()
          .map(line -> line.isEmpty() ? line : prefix + line)
          .collect(Collectors.joining("\n"));
    }

    private static String generateEagerOptionalTest(int arity) {
        var declarations = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> indent("Optional<Integer> o%d = Optional.of(%d);".formatted(i, i), 8))
          .collect(Collectors.joining("\n"));
        var allArgs = forcArgs(arity, "o");
        var lambda = yieldLambda(arity);
        var sum = arity * (arity + 1) / 2;

        var firstEmptyArgs = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> i == 1 ? "empty" : "o" + i)
          .collect(Collectors.joining(", "));

        var lastEmptyArgs = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> i == arity ? "empty" : "o" + i)
          .collect(Collectors.joining(", "));

        return """

              @Test
              void shouldTestEagerOptional%d() {
          %s
                  Optional<Integer> empty = Optional.empty();

                  assertThat(ForComprehension.forc(%s).yield(%s)).hasValue(%d);
                  assertThat(ForComprehension.forc(%s).yield(%s)).isEmpty();
                  assertThat(ForComprehension.forc(%s).yield(%s)).isEmpty();
              }
          """.formatted(arity, declarations, allArgs, lambda, sum,
          firstEmptyArgs, lambda, lastEmptyArgs, lambda);
    }

    private static String generateEagerStreamTest(int arity) {
        return """

              @Test
              void shouldTestEagerStream%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .containsExactly(%s);
              }
          """.formatted(arity, streamCreationArgs(arity), yieldLambda(arity), expectedCartesianValues(arity));
    }

    private static String generateEagerStreamEmptyTest(int arity) {
        var lambda = yieldLambda(arity);

        var firstEmptyArgs = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> i == 1 ? "Stream.<Integer>empty()" : streamOf(i))
          .collect(Collectors.joining(", "));

        var lastEmptyArgs = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> i == arity ? "Stream.<Integer>empty()" : streamOf(i))
          .collect(Collectors.joining(", "));

        return """

              @Test
              void shouldTestEagerStreamWithEmptyStream%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .isEmpty();
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .isEmpty();
              }
          """.formatted(arity, firstEmptyArgs, lambda, lastEmptyArgs, lambda);
    }

    private static String generateEagerIterableTest(int arity) {
        return """

              @Test
              void shouldTestEagerIterable%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .containsExactly(%s);
              }
          """.formatted(arity, iterableCreationArgs(arity), yieldLambda(arity), expectedCartesianValues(arity));
    }

    private static String generateEagerIterableEmptyTest(int arity) {
        var lambda = yieldLambda(arity);

        var firstEmptyArgs = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> i == 1 ? "Collections.<Integer>emptyList()" : iterableOf(i))
          .collect(Collectors.joining(", "));

        var lastEmptyArgs = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> i == arity ? "Collections.<Integer>emptyList()" : iterableOf(i))
          .collect(Collectors.joining(", "));

        return """

              @Test
              void shouldTestEagerIterableWithEmptyIterable%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s)).isEmpty();
                  assertThat(ForComprehension.forc(%s).yield(%s)).isEmpty();
              }
          """.formatted(arity, firstEmptyArgs, lambda, lastEmptyArgs, lambda);
    }

    private static String generateLazyOptionalTest() {
        return """

              @Test
              void shouldTestLazyOptional() {
                  Optional<Integer> o1 = Optional.of(1);
                  Optional<Integer> empty = Optional.empty();

                  assertThat(ForComprehension.forc(o1, v1 -> Optional.of(v1)).yield((t1, t2) -> t1 + t2)).hasValue(2);
                  assertThat(ForComprehension.forc(o1, v1 -> empty).yield((t1, t2) -> t1 + t2)).isEmpty();
                  assertThat(ForComprehension.forc(empty, v1 -> o1).yield((t1, t2) -> t1 + t2)).isEmpty();
                  assertThat(ForComprehension.forc(empty, v1 -> empty).yield((t1, t2) -> t1 + t2)).isEmpty();
              }
          """;
    }

    private static String generateLazyStreamTest() {
        return """

              @Test
              void shouldTestLazyStream() {
                  assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(v1 * 10, v1 * 20)).yield((t1, t2) -> t1 + t2))
                      .containsExactly(11, 21, 22, 42);
              }
          """;
    }

    private static String generateLazyStreamEmptyTest() {
        return """

              @Test
              void shouldTestLazyStreamWithEmptyStream() {
                  assertThat(ForComprehension.forc(Stream.<Integer>empty(), v1 -> Stream.of(v1)).yield((t1, t2) -> t1 + t2)).isEmpty();
                  assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.<Integer>empty()).yield((t1, t2) -> t1 + t2)).isEmpty();
              }
          """;
    }

    private static String generateLazyIterableTest() {
        return """

              @Test
              void shouldTestLazyIterable() {
                  assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(v1 * 10, v1 * 20)).yield((t1, t2) -> t1 + t2))
                      .containsExactly(11, 21, 22, 42);
              }
          """;
    }

    private static String generateLazyIterableEmptyTest() {
        return """

              @Test
              void shouldTestLazyIterableWithEmptyIterable() {
                  assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), v1 -> List.of(v1)).yield((t1, t2) -> t1 + t2)).isEmpty();
                  assertThat(ForComprehension.forc(List.of(1, 2), v1 -> Collections.<Integer>emptyList()).yield((t1, t2) -> t1 + t2)).isEmpty();
              }
          """;
    }

    private static String yieldLambda(int arity) {
        return "(%s) -> %s".formatted(IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", ")), IntStream.rangeClosed(1, arity)
            .mapToObj(i1 -> "t" + i1)
            .collect(Collectors.joining(" + ")));
    }

    private static String forcArgs(int arity, String prefix) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> prefix + i)
          .collect(Collectors.joining(", "));
    }

    private static String streamOf(int position) {
        return switch (position) {
            case 1 -> "Stream.of(1, 2)";
            case 2 -> "Stream.of(10, 20)";
            default -> "Stream.of(%d)".formatted((long) Math.pow(10, position - 1));
        };
    }

    private static String streamCreationArgs(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(ForComprehensionTestGenerator::streamOf)
          .collect(Collectors.joining(", "));
    }

    private static String iterableOf(int position) {
        return switch (position) {
            case 1 -> "List.of(1, 2)";
            case 2 -> "List.of(10, 20)";
            default -> "List.of(%d)".formatted((long) Math.pow(10, position - 1));
        };
    }

    private static String iterableCreationArgs(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(ForComprehensionTestGenerator::iterableOf)
          .collect(Collectors.joining(", "));
    }

    private static String expectedCartesianValues(int arity) {
        long base = IntStream.rangeClosed(3, arity).mapToLong(i -> (long) Math.pow(10, i - 1)).sum();
        return "%d, %d, %d, %d".formatted(1 + 10 + base, 1 + 20 + base, 2 + 10 + base, 2 + 20 + base);
    }
}
