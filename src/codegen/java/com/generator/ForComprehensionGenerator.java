package com.generator;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ForComprehensionGenerator {

    static String generate(int arity) {
        return """
          package com.pivovarit.forc;

          import java.util.ArrayList;
          import java.util.List;
          import java.util.Objects;
          import java.util.Optional;
          import java.util.stream.Collectors;
          import java.util.stream.Stream;

          /**
           * Provides a fluent for-comprehension API for Java, inspired by Scala's for-expressions.
           * <p>
           * This package allows composition of multiple monadic or collection-like types in a readable
           * and type-safe manner.
           */
          @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
          public final class ForComprehension {

              private ForComprehension() {
              }

          %s

          %s

          %s

          %s

          %s

          %s
          }
          """.formatted(indent(IntStream.rangeClosed(2, arity)
            .mapToObj(ForComprehensionGenerator::generateEagerOptional)
            .collect(Collectors.joining("\n")), 4),
          indent(IntStream.rangeClosed(2, arity)
            .mapToObj(ForComprehensionGenerator::generateEagerStream)
            .collect(Collectors.joining("\n")), 4),
          indent(IntStream.rangeClosed(2, arity)
            .mapToObj(ForComprehensionGenerator::generateEagerIterable)
            .collect(Collectors.joining("\n")), 4),
          indent("""
              /**
               * Creates a lazy for-comprehension over two {@link Optional} values.
               * <p>
               * The second optional is computed lazily and depends on the value of the first.
               * This enables dependent sequencing similar to Scala's for-expressions.
               *
               * @param o1 the first optional value
               * @param o2 a function producing the second optional value based on the first
               * @param <T1> the type of the first optional value
               * @param <T2> the type of the second optional value
               * @return a lazy for-comprehension over two optional values
               * @throws NullPointerException if any argument is {@code null}
               */
              public static <T1, T2> ForLazy2Optional<T1, T2> forc(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2) {
                  Objects.requireNonNull(o1, "o1 is null");
                  Objects.requireNonNull(o2, "o2 is null");
                  return new ForLazy2Optional<>(o1, o2);
              }

              /**
               * Represents a for-comprehension where the second {@link Optional} value
               * is computed lazily based on the first value.
               *
               * @param <T1> the type of the first optional value
               * @param <T2> the type of the second optional value
               */
              public static final class ForLazy2Optional<T1, T2> {

                  private final Optional<T1> o1;
                  private final Function1<? super T1, Optional<T2>> o2;

                  private ForLazy2Optional(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2) {
                      this.o1 = o1;
                      this.o2 = o2;
                  }

                  /**
                   * Produces the result of the for-comprehension by applying the given function
                   * to the contained values.
                   * <p>
                   * The second optional is evaluated only if the first optional is present.
                   *
                   * @param f the combining function
                   * @param <R> the result type
                   * @return an optional containing the result of the function application,
                   *         or {@link Optional#empty()} if any step yields an empty optional
                   * @throws NullPointerException if the function is {@code null}
                   */
                  public <R> Optional<R> yield(Function2<? super T1, ? super T2, ? extends R> f) {
                      Objects.requireNonNull(f, "f is null");

                      return o1.flatMap(t1 -> o2.apply(t1).map(t2 -> f.apply(t1, t2)));
                  }
              }
            """, 2),
          indent("""
              /**
               * Creates a lazy for-comprehension over two {@link Stream} values.
               * <p>
               * The second stream is computed lazily and depends on the elements of the first.
               * This enables dependent sequencing similar to Scala's for-expressions.
               *
               * @param s1 the first stream
               * @param s2 a function producing the second stream based on elements of the first
               * @param <T1> the element type of the first stream
               * @param <T2> the element type of the second stream
               * @return a lazy for-comprehension over two streams
               * @throws NullPointerException if any argument is {@code null}
               */
              public static <T1, T2> ForLazy2Stream<T1, T2> forc(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2) {
                  Objects.requireNonNull(s1, "s1 is null");
                  Objects.requireNonNull(s2, "s2 is null");
                  return new ForLazy2Stream<>(s1, s2);
              }

              /**
               * Represents a for-comprehension where the second {@link Stream}
               * is computed lazily based on elements of the first stream.
               *
               * @param <T1> the element type of the first stream
               * @param <T2> the element type of the second stream
               */
              public static final class ForLazy2Stream<T1, T2> {

                  private final Stream<T1> s1;
                  private final Function1<? super T1, Stream<T2>> s2;

                  private ForLazy2Stream(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2) {
                      this.s1 = s1;
                      this.s2 = s2;
                  }

                  /**
                   * Produces the result of the for-comprehension by applying the given function
                   * to the combined stream elements.
                   * <p>
                   * The second stream is evaluated for each element of the first stream.
                   *
                   * @param f the combining function
                   * @param <R> the result type
                   * @return a stream containing the results of applying the function to all combinations
                   * @throws NullPointerException if the function is {@code null}
                   */
                  public <R> Stream<R> yield(Function2<? super T1, ? super T2, ? extends R> f) {
                      Objects.requireNonNull(f, "f is null");

                      return s1.flatMap(t1 -> s2.apply(t1).map(t2 -> f.apply(t1, t2)));
                  }
              }
            """, 2),
          indent("""
              /**
               * Creates a lazy for-comprehension over two {@link Iterable} values.
               * <p>
               * The second iterable is computed lazily and depends on the elements of the first.
               * This enables dependent sequencing similar to Scala's for-expressions.
               *
               * @param i1 the first iterable
               * @param i2 a function producing the second iterable based on elements of the first
               * @param <T1> the element type of the first iterable
               * @param <T2> the element type of the second iterable
               * @return a lazy for-comprehension over two iterables
               * @throws NullPointerException if any argument is {@code null}
               */
              public static <T1, T2> ForLazy2Iterable<T1, T2> forc(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2) {
                  Objects.requireNonNull(i1, "i1 is null");
                  Objects.requireNonNull(i2, "i2 is null");
                  return new ForLazy2Iterable<>(i1, i2);
              }

              /**
               * Represents a for-comprehension where the second {@link Iterable}
               * is computed lazily based on elements of the first iterable.
               *
               * @param <T1> the element type of the first iterable
               * @param <T2> the element type of the second iterable
               */
              public static final class ForLazy2Iterable<T1, T2> {

                  private final Iterable<T1> i1;
                  private final Function1<? super T1, Iterable<T2>> i2;

                  private ForLazy2Iterable(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2) {
                      this.i1 = i1;
                      this.i2 = i2;
                  }

                  /**
                   * Produces the result of the for-comprehension by applying the given function
                   * to the combined iterable elements.
                   * <p>
                   * The second iterable is evaluated for each element of the first iterable.
                   *
                   * @param f the combining function
                   * @param <R> the result type
                   * @return a list containing the results of applying the function to all combinations
                   * @throws NullPointerException if the function is {@code null}
                   */
                  public <R> List<R> yield(Function2<? super T1, ? super T2, ? extends R> f) {
                      Objects.requireNonNull(f, "f is null");

                      List<R> result = new ArrayList<>();
                      for (T1 t1 : i1) {
                          for (T2 t2 : i2.apply(t1)) {
                              result.add(f.apply(t1, t2));
                          }
                      }
                      return result;
                  }
              }
            """, 2));
    }

    private static String generateEagerOptional(int arity) {
        var tparams = typeParams(arity);
        var methodParams = optionalParams(arity);
        var ctorArgs = argNamesPrefixed(arity, "o");
        var fields = optionalFields(arity);
        var ctorParams = optionalCtorParams(arity);
        var nnp = nullChecks(arity, "o");
        var yield = yieldOptionalChain(arity);

        return """
          /**
           * Creates a strict (eager) for-comprehension over %d {@link Optional} value%s.
           * <p>
           * All optionals are evaluated eagerly, and the resulting comprehension
           * yields a value only if all optionals are present.
           *
           * %s
           * @return a for-comprehension over %d optional value%s
           * @throws NullPointerException if any argument is {@code null}
           */
          public static <%s> For%dOptional<%s> forc(%s) {
          %s
              return new For%dOptional<>(%s);
          }

          /**
           * Represents a for-comprehension over %d eagerly evaluated {@link Optional} value%s.
           *
           * %s
           */
          public static final class For%dOptional<%s> {

          %s

              private For%dOptional(%s) {
          %s
              }

              /**
               * Produces the result of the for-comprehension by applying the given function
               * to the contained values.
               * <p>
               * The function is invoked only if all optionals are present.
               *
               * @param f the combining function
               * @param <R> the result type
               * @return an optional containing the result of the function application,
               *         or {@link Optional#empty()} if any input optional is empty
               * @throws NullPointerException if the function is {@code null}
               */
              public <R> Optional<R> yield(Function%d<%s> f) {
                  Objects.requireNonNull(f, "f is null");

          %s
              }
          }
          """.formatted(
          arity, "s",
          javadocParams(arity, "o", "optional value"),
          arity, "s",
          typeParams(arity),
          arity, tparams,
          methodParams,
          indent(nnp, 4),
          arity, ctorArgs,
          arity, "s",
          javadocTypeParams(arity, "optional value"),
          arity, tparams,
          indent(fields, 4),
          arity, ctorParams,
          indent(assignFields(arity, "o"), 8),
          arity, tparamsWithRWildcard(arity),
          indent(yield, 8)
        );
    }

    private static String generateEagerStream(int arity) {
        var tparams = typeParams(arity);
        var methodParams = streamParams(arity);
        var ctorArgs = argNamesPrefixed(arity, "s");
        var fields = streamFields(arity);
        var ctorParams = streamParams(arity);
        var nnp = nullChecks(arity, "s");
        var yield = yieldStreamChain(arity);

        return """
          /**
           * Creates a strict (eager) for-comprehension over %d {@link Stream} value%s.
           * <p>
           * All streams are evaluated eagerly. The resulting comprehension yields
           * the cartesian product of all streams, transformed by the yield function.
           *
           * %s
           * @return a for-comprehension over %d stream value%s
           * @throws NullPointerException if any argument is {@code null}
           */
          public static <%s> For%dStream<%s> forc(%s) {
          %s
              return new For%dStream<>(%s);
          }

          /**
           * Represents a for-comprehension over %d eagerly evaluated {@link Stream} value%s.
           *
           * %s
           */
          public static final class For%dStream<%s> {

          %s

              private For%dStream(%s) {
          %s
              }

              /**
               * Produces the result of the for-comprehension by applying the given function
               * to the cartesian product of the stream elements.
               *
               * @param f the combining function
               * @param <R> the result type
               * @return a stream containing the results of applying the function to all combinations
               * @throws NullPointerException if the function is {@code null}
               */
              public <R> Stream<R> yield(Function%d<%s> f) {
                  Objects.requireNonNull(f, "f is null");

          %s
              }
          }
          """.formatted(
          arity, "s",
          javadocParams(arity, "s", "stream"),
          arity, "s",
          typeParams(arity),
          arity, tparams,
          methodParams,
          indent(nnp, 4),
          arity, ctorArgs,
          arity, "s",
          javadocTypeParams(arity, "stream"),
          arity, tparams,
          indent(fields, 4),
          arity, ctorParams,
          indent(assignFields(arity, "s"), 8),
          arity, tparamsWithRWildcard(arity),
          indent(yield, 8)
        );
    }

    private static String generateEagerIterable(int arity) {
        var tparams = typeParams(arity);
        var methodParams = iterableParams(arity);
        var ctorArgs = argNamesPrefixed(arity, "i");
        var fields = iterableFields(arity);
        var ctorParams = iterableParams(arity);
        var nnp = nullChecks(arity, "i");
        var yield = yieldIterableChain(arity);

        return """
          /**
           * Creates a strict (eager) for-comprehension over %d {@link Iterable} value%s.
           * <p>
           * All iterables are evaluated eagerly. The resulting comprehension yields
           * the cartesian product of all iterables, transformed by the yield function.
           *
           * %s
           * @return a for-comprehension over %d iterable value%s
           * @throws NullPointerException if any argument is {@code null}
           */
          public static <%s> For%dIterable<%s> forc(%s) {
          %s
              return new For%dIterable<>(%s);
          }

          /**
           * Represents a for-comprehension over %d eagerly evaluated {@link Iterable} value%s.
           *
           * %s
           */
          public static final class For%dIterable<%s> {

          %s

              private For%dIterable(%s) {
          %s
              }

              /**
               * Produces the result of the for-comprehension by applying the given function
               * to the cartesian product of the iterable elements.
               *
               * @param f the combining function
               * @param <R> the result type
               * @return a list containing the results of applying the function to all combinations
               * @throws NullPointerException if the function is {@code null}
               */
              public <R> List<R> yield(Function%d<%s> f) {
                  Objects.requireNonNull(f, "f is null");

          %s
              }
          }
          """.formatted(
          arity, "s",
          javadocParams(arity, "i", "iterable"),
          arity, "s",
          typeParams(arity),
          arity, tparams,
          methodParams,
          indent(nnp, 4),
          arity, ctorArgs,
          arity, "s",
          javadocTypeParams(arity, "iterable"),
          arity, tparams,
          indent(fields, 4),
          arity, ctorParams,
          indent(assignFields(arity, "i"), 8),
          arity, tparamsWithRWildcard(arity),
          indent(yield, 8)
        );
    }

    private static String yieldOptionalChain(int arity) {
        if (arity == 1) {
            return "return o1.map(t1 -> f.apply(t1));";
        }

        var args = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", "));

        var chain = IntStream.range(1, arity)
          .map(i -> arity - i)
          .mapToObj(i -> "o%d.flatMap(t%d -> %%s)".formatted(i, i))
          .reduce("o%d.map(t%d -> f.apply(%s))".formatted(arity, arity, args), (i, w) -> w.formatted(i));

        return "return " + chain + ";";
    }

    private static String yieldStreamChain(int arity) {
        if (arity == 1) {
            return "return s1.map(t1 -> f.apply(t1));";
        }

        var args = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", "));

        var collections = IntStream.rangeClosed(2, arity)
          .mapToObj(i -> "List<T%d> l%d = s%d.collect(Collectors.toList());".formatted(i, i, i))
          .collect(Collectors.joining("\n"));

        var chain = IntStream.range(1, arity)
          .map(i -> arity - i)
          .mapToObj(i -> i == 1
              ? "s1.flatMap(t1 -> %s)"
              : "l%d.stream().flatMap(t%d -> %%s)".formatted(i, i))
          .reduce("l%d.stream().map(t%d -> f.apply(%s))".formatted(arity, arity, args), (i, w) -> w.formatted(i));

        return collections + "\n\nreturn " + chain + ";";
    }

    private static String yieldIterableChain(int arity) {
        var args = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", "));

        var collections = IntStream.rangeClosed(2, arity)
          .mapToObj(i -> "List<T%d> l%d = new ArrayList<>();\ni%d.forEach(l%d::add);".formatted(i, i, i, i))
          .collect(Collectors.joining("\n"));

        var loops = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> i == 1
            ? "for (T1 t1 : i1) {"
            : "for (T%d t%d : l%d) {".formatted(i, i, i))
          .collect(Collectors.joining("\n"));

        var closes = "}".repeat(arity);

        return collections + "\nList<R> result = new ArrayList<>();\n" + loops + "\n    result.add(f.apply(" + args + "));\n" + closes + "\nreturn result;";
    }

    private static String typeParams(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj("T%d"::formatted)
          .collect(Collectors.joining(", "));
    }

    private static String tparamsWithRWildcard(int arity) {
        var args = IntStream.rangeClosed(1, arity)
          .mapToObj("? super T%d"::formatted)
          .collect(Collectors.joining(", "));
        return (args.isEmpty() ? "" : args + ", ") + "? extends R";
    }

    private static String optionalParams(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "Optional<T%d> o%d".formatted(i, i))
          .collect(Collectors.joining(", "));
    }

    private static String optionalCtorParams(int arity) {
        return optionalParams(arity);
    }

    private static String optionalFields(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "private final Optional<T%d> o%d;".formatted(i, i))
          .collect(Collectors.joining("\n"));
    }

    private static String iterableParams(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "Iterable<T%d> i%d".formatted(i, i))
          .collect(Collectors.joining(", "));
    }

    private static String iterableFields(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "private final Iterable<T%d> i%d;".formatted(i, i))
          .collect(Collectors.joining("\n"));
    }

    private static String streamParams(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "Stream<T%d> s%d".formatted(i, i))
          .collect(Collectors.joining(", "));
    }

    private static String streamFields(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "private final Stream<T%d> s%d;".formatted(i, i))
          .collect(Collectors.joining("\n"));
    }

    private static String assignFields(int arity, String prefix) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "this.%s%d = %s%d;".formatted(prefix, i, prefix, i))
          .collect(Collectors.joining("\n"));
    }

    private static String nullChecks(int arity, String prefix) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "Objects.requireNonNull(%s%d, \"%s%d is null\");".formatted(prefix, i, prefix, i))
          .collect(Collectors.joining("\n"));
    }

    private static String argNamesPrefixed(int arity, String prefix) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> prefix + i)
          .collect(Collectors.joining(", "));
    }

    private static String javadocParams(int arity, String prefix, String description) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "@param %s%d the %s %s".formatted(prefix, i, ordinal(i), description))
          .collect(Collectors.joining("\n * "));
    }

    private static String javadocTypeParams(int arity, String description) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "@param <T%d> the type of the %s %s".formatted(i, ordinal(i), description))
          .collect(Collectors.joining("\n * "));
    }

    private static String ordinal(int i) {
        return switch (i) {
            case 1 -> "first";
            case 2 -> "second";
            case 3 -> "third";
            default -> i + "th";
        };
    }

    private static String indent(String s, int spaces) {
        return s.lines().map(l -> l.isEmpty() ? l : " ".repeat(spaces) + l).collect(Collectors.joining("\n"));
    }
}
