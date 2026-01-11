package com.generator;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ForComprehensionGenerator {

    static String generate(int arity) {
        return """
          package com.pivovarit.forc;
          
          import java.util.Objects;
          import java.util.Optional;
          
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
          }
          """.formatted(indent(IntStream.rangeClosed(2, arity)
            .mapToObj(ForComprehensionGenerator::generateEagerOptional)
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
            """, 2));
    }

    private static String generateEagerOptional(int arity) {
        var tparams = typeParams(arity);
        var methodParams = optionalParams(arity);
        var ctorArgs = argNamesPrefixed(arity);
        var fields = optionalFields(arity);
        var ctorParams = optionalCtorParams(arity);
        var nnp = nullChecks(arity);
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
          javadocParams(arity),
          arity, "s",
          typeParams(arity),
          arity, tparams,
          methodParams,
          indent(nnp, 4),
          arity, ctorArgs,
          arity, "s",
          javadocTypeParams(arity),
          arity, tparams,
          indent(fields, 4),
          arity, ctorParams,
          indent(assignFields(arity), 8),
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

    private static String assignFields(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "this.o%d = o%d;".formatted(i, i))
          .collect(Collectors.joining("\n"));
    }

    private static String nullChecks(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "Objects.requireNonNull(%s%d, \"%s\");".formatted("o", i, "o%d is null".formatted(i)))
          .collect(Collectors.joining("\n"));
    }

    private static String argNamesPrefixed(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "o" + i)
          .collect(Collectors.joining(", "));
    }

    private static String javadocParams(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "@param %s%d %s".formatted("o", i, "the %s optional value".formatted(ordinal(i))))
          .collect(Collectors.joining("\n * "));
    }

    private static String javadocTypeParams(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "@param <T%d> the type of the %s optional value".formatted(i, ordinal(i)))
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
