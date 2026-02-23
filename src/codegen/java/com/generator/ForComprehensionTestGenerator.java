package com.generator;

import com.generator.ForComprehensionGenerator.ContainerType;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ForComprehensionTestGenerator {

    static String generate(int arity) {
        var eagerSections = new ArrayList<String>();
        var lazySections = new ArrayList<String>();

        for (var ct : ContainerType.values()) {
            var eagerTests = new StringBuilder();
            var lazyTests = new StringBuilder();
            for (int i = 2; i <= arity; i++) {
                eagerTests.append(wrapInArityNested(i, generateEagerTest(i, ct) + generateEagerEmptyTest(i, ct) + generateEagerFilterTest(i, ct) + generateEagerChainedFilterTest(i, ct)));
                lazyTests.append(wrapInArityNested(i, generateLazyTest(i, ct) + generateLazyEmptyTest(i, ct) + generateLazyFilterTest(i, ct) + generateLazyChainedFilterTest(i, ct)));
            }
            eagerSections.add(eagerTests.toString());
            lazySections.add(lazyTests.toString());
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
          Generator.indent(eagerSections.get(0), 4),
          Generator.indent(eagerSections.get(1), 4),
          Generator.indent(eagerSections.get(2), 4),
          Generator.indent(lazySections.get(0), 4),
          Generator.indent(lazySections.get(1), 4),
          Generator.indent(lazySections.get(2), 4));
    }

    private static String wrapInArityNested(int arity, String tests) {
        return "\n    @Nested\n    class Arity" + arity + " {" + Generator.indent(tests, 4) + "\n    }\n";
    }

    private static String generateEagerTest(int arity, ContainerType ct) {
        var lambda = yieldLambda(arity);
        return switch (ct) {
            case OPTIONAL -> {
                var declarations = IntStream.rangeClosed(1, arity)
                  .mapToObj(i -> Generator.indent("Optional<Integer> o%d = Optional.of(%d);".formatted(i, i), 8))
                  .collect(Collectors.joining("\n"));
                var allArgs = forcArgs(arity, "o");
                var sum = arity * (arity + 1) / 2;
                var firstEmptyArgs = IntStream.rangeClosed(1, arity)
                  .mapToObj(i -> i == 1 ? "empty" : "o" + i)
                  .collect(Collectors.joining(", "));
                var lastEmptyArgs = IntStream.rangeClosed(1, arity)
                  .mapToObj(i -> i == arity ? "empty" : "o" + i)
                  .collect(Collectors.joining(", "));

                yield """

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
            case STREAM, ITERABLE -> """

              @Test
              void shouldTestEager%s%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .containsExactly(%s);
              }
          """.formatted(ct.typeName, arity, creationArgs(arity, ct), lambda, expectedCartesianValues(arity));
        };
    }

    private static String generateEagerEmptyTest(int arity, ContainerType ct) {
        if (ct == ContainerType.OPTIONAL) return "";
        var lambda = yieldLambda(arity);
        var firstEmptyArgs = eagerArgsWithEmpty(arity, 1, ct);
        var lastEmptyArgs = eagerArgsWithEmpty(arity, arity, ct);

        return switch (ct) {
            case STREAM -> """

              @Test
              void shouldTestEagerStreamWithEmptyStream%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .isEmpty();
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .isEmpty();
              }
          """.formatted(arity, firstEmptyArgs, lambda, lastEmptyArgs, lambda);
            case ITERABLE -> """

              @Test
              void shouldTestEagerIterableWithEmptyIterable%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s)).isEmpty();
                  assertThat(ForComprehension.forc(%s).yield(%s)).isEmpty();
              }
          """.formatted(arity, firstEmptyArgs, lambda, lastEmptyArgs, lambda);
            default -> "";
        };
    }

    private static String generateLazyTest(int arity, ContainerType ct) {
        var lambda = yieldLambda(arity);
        return switch (ct) {
            case OPTIONAL -> {
                var sum = arity * (arity + 1) / 2;
                var allArgs = lazyArgs(arity, 0, ct);
                var firstEmptyArgs = lazyArgs(arity, 1, ct);
                var lastEmptyArgs = lazyArgs(arity, arity, ct);

                yield """

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
            case STREAM, ITERABLE -> """

              @Test
              void shouldTestLazy%s%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .containsExactly(%s);
              }
          """.formatted(ct.typeName, arity, lazyArgs(arity, 0, ct), lambda, expectedCartesianValues(arity));
        };
    }

    private static String generateLazyEmptyTest(int arity, ContainerType ct) {
        if (ct == ContainerType.OPTIONAL) return "";
        var lambda = yieldLambda(arity);

        return switch (ct) {
            case STREAM -> """

              @Test
              void shouldTestLazyStreamWithEmptyStream%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .isEmpty();
                  assertThat(ForComprehension.forc(%s).yield(%s))
                      .isEmpty();
              }
          """.formatted(arity, lazyArgs(arity, 1, ct), lambda, lazyArgs(arity, arity, ct), lambda);
            case ITERABLE -> """

              @Test
              void shouldTestLazyIterableWithEmptyIterable%d() {
                  assertThat(ForComprehension.forc(%s).yield(%s)).isEmpty();
                  assertThat(ForComprehension.forc(%s).yield(%s)).isEmpty();
              }
          """.formatted(arity, lazyArgs(arity, 1, ct), lambda, lazyArgs(arity, arity, ct), lambda);
            default -> "";
        };
    }

    private static String generateEagerFilterTest(int arity, ContainerType ct) {
        var lambda = yieldLambda(arity);
        return switch (ct) {
            case OPTIONAL -> {
                var declarations = IntStream.rangeClosed(1, arity)
                  .mapToObj(i -> Generator.indent("Optional<Integer> o%d = Optional.of(%d);".formatted(i, i), 8))
                  .collect(Collectors.joining("\n"));
                var allArgs = forcArgs(arity, "o");
                var sum = arity * (arity + 1) / 2;
                yield """

              @Test
              void shouldTestEagerOptionalFilter%d() {
          %s
                  assertThat(ForComprehension.forc(%s).filter(%s).yield(%s)).hasValue(%d);
                  assertThat(ForComprehension.forc(%s).filter(%s).yield(%s)).isEmpty();
              }
          """.formatted(arity, declarations, allArgs, filterLambdaSum(arity), lambda, sum,
                        allArgs, filterLambdaAlwaysFalse(arity), lambda);
            }
            case STREAM -> """

              @Test
              void shouldTestEagerStreamFilter%d() {
                  assertThat(ForComprehension.forc(%s).filter(%s).yield(%s))
                      .containsExactly(%s);
                  assertThat(ForComprehension.forc(%s).filter(%s).yield(%s))
                      .isEmpty();
              }
          """.formatted(arity, creationArgs(arity, ct), filterLambdaT1Eq1(arity), lambda, expectedCartesianValuesFilteredByT1(arity),
                        creationArgs(arity, ct), filterLambdaAlwaysFalse(arity), lambda);
            case ITERABLE -> """

              @Test
              void shouldTestEagerIterableFilter%d() {
                  assertThat(ForComprehension.forc(%s).filter(%s).yield(%s))
                      .containsExactly(%s);
                  assertThat(ForComprehension.forc(%s).filter(%s).yield(%s))
                      .isEmpty();
              }
          """.formatted(arity, creationArgs(arity, ct), filterLambdaT1Eq1(arity), lambda, expectedCartesianValuesFilteredByT1(arity),
                        creationArgs(arity, ct), filterLambdaAlwaysFalse(arity), lambda);
        };
    }

    private static String generateLazyFilterTest(int arity, ContainerType ct) {
        var lambda = yieldLambda(arity);
        return switch (ct) {
            case OPTIONAL -> {
                var sum = arity * (arity + 1) / 2;
                var allArgs = lazyArgs(arity, 0, ct);
                yield """

              @Test
              void shouldTestLazyOptionalFilter%d() {
                  Optional<Integer> o1 = Optional.of(1);
                  assertThat(ForComprehension.forc(%s).filter(%s).yield(%s)).hasValue(%d);
                  assertThat(ForComprehension.forc(%s).filter(%s).yield(%s)).isEmpty();
              }
          """.formatted(arity, allArgs, filterLambdaSum(arity), lambda, sum,
                        allArgs, filterLambdaAlwaysFalse(arity), lambda);
            }
            case STREAM -> """

              @Test
              void shouldTestLazyStreamFilter%d() {
                  assertThat(ForComprehension.forc(%s).filter(%s).yield(%s))
                      .containsExactly(%s);
                  assertThat(ForComprehension.forc(%s).filter(%s).yield(%s))
                      .isEmpty();
              }
          """.formatted(arity, lazyArgs(arity, 0, ct), filterLambdaT1Eq1(arity), lambda, expectedCartesianValuesFilteredByT1(arity),
                        lazyArgs(arity, 0, ct), filterLambdaAlwaysFalse(arity), lambda);
            case ITERABLE -> """

              @Test
              void shouldTestLazyIterableFilter%d() {
                  assertThat(ForComprehension.forc(%s).filter(%s).yield(%s))
                      .containsExactly(%s);
                  assertThat(ForComprehension.forc(%s).filter(%s).yield(%s))
                      .isEmpty();
              }
          """.formatted(arity, lazyArgs(arity, 0, ct), filterLambdaT1Eq1(arity), lambda, expectedCartesianValuesFilteredByT1(arity),
                        lazyArgs(arity, 0, ct), filterLambdaAlwaysFalse(arity), lambda);
        };
    }

    private static String generateEagerChainedFilterTest(int arity, ContainerType ct) {
        var lambda = yieldLambda(arity);
        return switch (ct) {
            case OPTIONAL -> {
                var declarations = IntStream.rangeClosed(1, arity)
                  .mapToObj(i -> Generator.indent("Optional<Integer> o%d = Optional.of(%d);".formatted(i, i), 8))
                  .collect(Collectors.joining("\n"));
                var allArgs = forcArgs(arity, "o");
                var sum = arity * (arity + 1) / 2;
                yield """

              @Test
              void shouldTestEagerOptionalChainedFilter%d() {
          %s
                  assertThat(ForComprehension.forc(%s).filter(%s).filter(%s).yield(%s)).hasValue(%d);
                  assertThat(ForComprehension.forc(%s).filter(%s).filter(%s).yield(%s)).isEmpty();
              }
          """.formatted(arity, declarations,
                        allArgs, filterLambdaSum(arity), filterLambdaT1Eq1(arity), lambda, sum,
                        allArgs, filterLambdaSum(arity), filterLambdaAlwaysFalse(arity), lambda);
            }
            case STREAM -> """

              @Test
              void shouldTestEagerStreamChainedFilter%d() {
                  assertThat(ForComprehension.forc(%s).filter(%s).filter(%s).yield(%s))
                      .containsExactly(%s);
                  assertThat(ForComprehension.forc(%s).filter(%s).filter(%s).yield(%s))
                      .isEmpty();
              }
          """.formatted(arity, creationArgs(arity, ct), filterLambdaT1Eq1(arity), filterLambdaT2Eq10(arity), lambda, expectedCartesianValuesFilteredByT1AndT2(arity),
                        creationArgs(arity, ct), filterLambdaT1Eq1(arity), filterLambdaAlwaysFalse(arity), lambda);
            case ITERABLE -> """

              @Test
              void shouldTestEagerIterableChainedFilter%d() {
                  assertThat(ForComprehension.forc(%s).filter(%s).filter(%s).yield(%s))
                      .containsExactly(%s);
                  assertThat(ForComprehension.forc(%s).filter(%s).filter(%s).yield(%s))
                      .isEmpty();
              }
          """.formatted(arity, creationArgs(arity, ct), filterLambdaT1Eq1(arity), filterLambdaT2Eq10(arity), lambda, expectedCartesianValuesFilteredByT1AndT2(arity),
                        creationArgs(arity, ct), filterLambdaT1Eq1(arity), filterLambdaAlwaysFalse(arity), lambda);
        };
    }

    private static String generateLazyChainedFilterTest(int arity, ContainerType ct) {
        var lambda = yieldLambda(arity);
        return switch (ct) {
            case OPTIONAL -> {
                var sum = arity * (arity + 1) / 2;
                var allArgs = lazyArgs(arity, 0, ct);
                yield """

              @Test
              void shouldTestLazyOptionalChainedFilter%d() {
                  Optional<Integer> o1 = Optional.of(1);
                  assertThat(ForComprehension.forc(%s).filter(%s).filter(%s).yield(%s)).hasValue(%d);
                  assertThat(ForComprehension.forc(%s).filter(%s).filter(%s).yield(%s)).isEmpty();
              }
          """.formatted(arity, allArgs, filterLambdaSum(arity), filterLambdaT1Eq1(arity), lambda, sum,
                        allArgs, filterLambdaSum(arity), filterLambdaAlwaysFalse(arity), lambda);
            }
            case STREAM -> """

              @Test
              void shouldTestLazyStreamChainedFilter%d() {
                  assertThat(ForComprehension.forc(%s).filter(%s).filter(%s).yield(%s))
                      .containsExactly(%s);
                  assertThat(ForComprehension.forc(%s).filter(%s).filter(%s).yield(%s))
                      .isEmpty();
              }
          """.formatted(arity, lazyArgs(arity, 0, ct), filterLambdaT1Eq1(arity), filterLambdaT2Eq10(arity), lambda, expectedCartesianValuesFilteredByT1AndT2(arity),
                        lazyArgs(arity, 0, ct), filterLambdaT1Eq1(arity), filterLambdaAlwaysFalse(arity), lambda);
            case ITERABLE -> """

              @Test
              void shouldTestLazyIterableChainedFilter%d() {
                  assertThat(ForComprehension.forc(%s).filter(%s).filter(%s).yield(%s))
                      .containsExactly(%s);
                  assertThat(ForComprehension.forc(%s).filter(%s).filter(%s).yield(%s))
                      .isEmpty();
              }
          """.formatted(arity, lazyArgs(arity, 0, ct), filterLambdaT1Eq1(arity), filterLambdaT2Eq10(arity), lambda, expectedCartesianValuesFilteredByT1AndT2(arity),
                        lazyArgs(arity, 0, ct), filterLambdaT1Eq1(arity), filterLambdaAlwaysFalse(arity), lambda);
        };
    }

    private static String filterLambdaT2Eq10(int arity) {
        String params = IntStream.rangeClosed(1, arity).mapToObj(i -> "t" + i).collect(Collectors.joining(", "));
        return "(%s) -> t2 == 10".formatted(params);
    }

    private static String expectedCartesianValuesFilteredByT1AndT2(int arity) {
        long base = IntStream.rangeClosed(3, arity).mapToLong(i -> (long) Math.pow(10, i - 1)).sum();
        return "%d".formatted(1 + 10 + base);
    }

    private static String filterLambdaSum(int arity) {
        int sum = arity * (arity + 1) / 2;
        String params = IntStream.rangeClosed(1, arity).mapToObj(i -> "t" + i).collect(Collectors.joining(", "));
        String sumExpr = IntStream.rangeClosed(1, arity).mapToObj(i -> "t" + i).collect(Collectors.joining(" + "));
        return "(%s) -> %s == %d".formatted(params, sumExpr, sum);
    }

    private static String filterLambdaAlwaysFalse(int arity) {
        String params = IntStream.rangeClosed(1, arity).mapToObj(i -> "t" + i).collect(Collectors.joining(", "));
        return "(%s) -> false".formatted(params);
    }

    private static String filterLambdaT1Eq1(int arity) {
        String params = IntStream.rangeClosed(1, arity).mapToObj(i -> "t" + i).collect(Collectors.joining(", "));
        return "(%s) -> t1 == 1".formatted(params);
    }

    private static String expectedCartesianValuesFilteredByT1(int arity) {
        long base = IntStream.rangeClosed(3, arity).mapToLong(i -> (long) Math.pow(10, i - 1)).sum();
        return "%d, %d".formatted(1 + 10 + base, 1 + 20 + base);
    }

    private static String lazyLambdaParams(int position) {
        var params = IntStream.rangeClosed(1, position - 1)
          .mapToObj(j -> "v" + j)
          .collect(Collectors.joining(", "));
        return position - 1 == 1 ? params : "(" + params + ")";
    }

    private static String lazyArgs(int arity, int emptyPos, ContainerType ct) {
        var sb = new StringBuilder(emptyPos == 1 ? emptyExpr(ct) : lazyFirstArg(ct));
        for (int i = 2; i <= arity; i++) {
            sb.append(", ");
            sb.append(lazyLambdaParams(i));
            if (i == emptyPos) {
                sb.append(" -> " + emptyExpr(ct));
            } else {
                sb.append(" -> " + lazyValueExpr(i, ct));
            }
        }
        return sb.toString();
    }

    private static String emptyExpr(ContainerType ct) {
        return switch (ct) {
            case OPTIONAL -> "empty";
            case STREAM -> "Stream.<Integer>empty()";
            case ITERABLE -> "Collections.<Integer>emptyList()";
        };
    }

    private static String lazyFirstArg(ContainerType ct) {
        return ct == ContainerType.OPTIONAL ? "o1" : valueOf(1, ct);
    }

    private static String lazyValueExpr(int i, ContainerType ct) {
        return ct == ContainerType.OPTIONAL ? "Optional.of(%d)".formatted(i) : valueOf(i, ct);
    }

    private static String eagerArgsWithEmpty(int arity, int emptyPos, ContainerType ct) {
        var empty = eagerEmptyExpr(ct);
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> i == emptyPos ? empty : valueOf(i, ct))
          .collect(Collectors.joining(", "));
    }

    private static String eagerEmptyExpr(ContainerType ct) {
        return switch (ct) {
            case STREAM -> "Stream.<Integer>empty()";
            case ITERABLE -> "Collections.<Integer>emptyList()";
            case OPTIONAL -> throw new IllegalArgumentException();
        };
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

    private static String valueOf(int position, ContainerType ct) {
        var factoryPrefix = ct == ContainerType.STREAM ? "Stream" : "List";
        return switch (position) {
            case 1 -> "%s.of(1, 2)".formatted(factoryPrefix);
            case 2 -> "%s.of(10, 20)".formatted(factoryPrefix);
            default -> "%s.of(%d)".formatted(factoryPrefix, (long) Math.pow(10, position - 1));
        };
    }

    private static String creationArgs(int arity, ContainerType ct) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> valueOf(i, ct))
          .collect(Collectors.joining(", "));
    }

    private static String expectedCartesianValues(int arity) {
        long base = IntStream.rangeClosed(3, arity).mapToLong(i -> (long) Math.pow(10, i - 1)).sum();
        return "%d, %d, %d, %d".formatted(1 + 10 + base, 1 + 20 + base, 2 + 10 + base, 2 + 20 + base);
    }
}
