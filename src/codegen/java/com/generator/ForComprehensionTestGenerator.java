package com.generator;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ForComprehensionTestGenerator {

    static String generate(int arity) {
        var eagerOptionalTests = new StringBuilder();
        for (int i = 2; i <= arity; i++) {
            eagerOptionalTests.append(wrapInArityNested(i, generateEagerOptionalTest(i)));
        }

        var eagerStreamTests = new StringBuilder();
        for (int i = 2; i <= arity; i++) {
            eagerStreamTests.append(wrapInArityNested(i, generateEagerStreamTest(i) + generateEagerStreamEmptyTest(i)));
        }

        var eagerIterableTests = new StringBuilder();
        for (int i = 2; i <= arity; i++) {
            eagerIterableTests.append(wrapInArityNested(i, generateEagerIterableTest(i) + generateEagerIterableEmptyTest(i)));
        }

        var lazyOptionalTests = new StringBuilder();
        for (int i = 2; i <= arity; i++) {
            lazyOptionalTests.append(wrapInArityNested(i, generateLazyOptionalTest(i)));
        }

        var lazyStreamTests = new StringBuilder();
        for (int i = 2; i <= arity; i++) {
            lazyStreamTests.append(wrapInArityNested(i, generateLazyStreamTest(i) + generateLazyStreamEmptyTest(i)));
        }

        var lazyIterableTests = new StringBuilder();
        for (int i = 2; i <= arity; i++) {
            lazyIterableTests.append(wrapInArityNested(i, generateLazyIterableTest(i) + generateLazyIterableEmptyTest(i)));
        }

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
              class LazyOptional {
          %s
              }

              @Nested
              class LazyStream {
          %s
              }

              @Nested
              class LazyIterable {
          %s
              }
          }
          """.formatted(
          indent(eagerOptionalTests.toString(), 4),
          indent(eagerStreamTests.toString(), 4),
          indent(eagerIterableTests.toString(), 4),
          indent(lazyOptionalTests.toString(), 4),
          indent(lazyStreamTests.toString(), 4),
          indent(lazyIterableTests.toString(), 4));
    }

    private static String indent(String text, int spaces) {
        var prefix = " ".repeat(spaces);
        return text.lines()
          .map(line -> line.isEmpty() ? line : prefix + line)
          .collect(Collectors.joining("\n"));
    }

    private static String wrapInArityNested(int arity, String tests) {
        return "\n    @Nested\n    class Arity" + arity + " {" + indent(tests, 4) + "\n    }\n";
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

    private static String generateLazyOptionalTest(int arity) {
        var lambda = yieldLambda(arity);
        var sum = arity * (arity + 1) / 2;
        var allArgs = lazyOptionalArgs(arity, 0);
        var firstEmptyArgs = lazyOptionalArgs(arity, 1);
        var lastEmptyArgs = lazyOptionalArgs(arity, arity);

        return """

              @Test
              void shouldTestLazyOptional%d() {
                  Optional<Integer> o1 = Optional.of(1);
                  Optional<Integer> empty = Optional.empty();

                  assertThat(ForComprehension.forc(%s).yield(%s)).hasValue(%d);
                  assertThat(ForComprehension.forc(%s).yield(%s)).isEmpty();
                  assertThat(ForComprehension.forc(%s).yield(%s)).isEmpty();
              }
          """.formatted(arity, allArgs, lambda, sum,
          firstEmptyArgs, lambda, lastEmptyArgs, lambda);
    }

    private static String generateLazyStreamTest(int arity) {
        return """

              @Test
              void shouldTestLazyStream%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .containsExactly(%s);
              }
          """.formatted(arity, lazyStreamArgs(arity, 0), yieldLambda(arity), expectedCartesianValues(arity));
    }

    private static String generateLazyStreamEmptyTest(int arity) {
        var lambda = yieldLambda(arity);
        return """

              @Test
              void shouldTestLazyStreamWithEmptyStream%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .isEmpty();
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .isEmpty();
              }
          """.formatted(arity, lazyStreamArgs(arity, 1), lambda, lazyStreamArgs(arity, arity), lambda);
    }

    private static String generateLazyIterableTest(int arity) {
        return """

              @Test
              void shouldTestLazyIterable%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .containsExactly(%s);
              }
          """.formatted(arity, lazyIterableArgs(arity, 0), yieldLambda(arity), expectedCartesianValues(arity));
    }

    private static String generateLazyIterableEmptyTest(int arity) {
        var lambda = yieldLambda(arity);
        return """

              @Test
              void shouldTestLazyIterableWithEmptyIterable%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s)).isEmpty();
                  assertThat(ForComprehension.forc(%s).yield(%s)).isEmpty();
              }
          """.formatted(arity, lazyIterableArgs(arity, 1), lambda, lazyIterableArgs(arity, arity), lambda);
    }

    private static String lazyLambdaParams(int position) {
        var params = IntStream.rangeClosed(1, position - 1)
          .mapToObj(j -> "v" + j)
          .collect(Collectors.joining(", "));
        return position - 1 == 1 ? params : "(" + params + ")";
    }

    private static String lazyOptionalArgs(int arity, int emptyPos) {
        var sb = new StringBuilder(emptyPos == 1 ? "empty" : "o1");
        for (int i = 2; i <= arity; i++) {
            sb.append(", ");
            sb.append(lazyLambdaParams(i));
            if (i == emptyPos) {
                sb.append(" -> empty");
            } else {
                sb.append(" -> Optional.of(%d)".formatted(i));
            }
        }
        return sb.toString();
    }

    private static String lazyStreamArgs(int arity, int emptyPos) {
        var sb = new StringBuilder(emptyPos == 1 ? "Stream.<Integer>empty()" : streamOf(1));
        for (int i = 2; i <= arity; i++) {
            sb.append(", ");
            sb.append(lazyLambdaParams(i));
            if (i == emptyPos) {
                sb.append(" -> Stream.<Integer>empty()");
            } else {
                sb.append(" -> " + streamOf(i));
            }
        }
        return sb.toString();
    }

    private static String lazyIterableArgs(int arity, int emptyPos) {
        var sb = new StringBuilder(emptyPos == 1 ? "Collections.<Integer>emptyList()" : iterableOf(1));
        for (int i = 2; i <= arity; i++) {
            sb.append(", ");
            sb.append(lazyLambdaParams(i));
            if (i == emptyPos) {
                sb.append(" -> Collections.<Integer>emptyList()");
            } else {
                sb.append(" -> " + iterableOf(i));
            }
        }
        return sb.toString();
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
