package com.generator;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class FunctionTypeGenerator {
    static String generate(int arity) {
        return """
          package com.pivovarit.forc;

          import java.util.Objects;

          /**
           * Represents a function with %d argument%s.
           *
           * %s
           * @param <R> the type of the result
           */
          @FunctionalInterface
          public interface Function%d<%s> {

              /**
               * Applies this function to the given argument%s.
               *
               * %s
               * @return the function result
               */
              R apply(%s);

              /**
               * Returns a composed function that first applies this function to its input,
               * and then applies the {@code after} function to the result.
               *
               * @param after the function to apply after this function is applied
               * @param <V> the type of the output of the {@code after} function
               * @return a composed function
               * @throws NullPointerException if after is {@code null}
               */
              default <V> Function%d<%s> andThen(Function1<? super R, ? extends V> after) {
                  Objects.requireNonNull(after);
                  return (%s) -> after.apply(apply(%s));
              }
          }
          """.formatted(
          arity,
          arity > 1 ? "s" : "",
          typeParamDocs(arity),
          arity,
          typeParams(arity, "R"),
          arity > 1 ? "s" : "",
          paramDocs(arity),
          argList(arity),
          arity,
          typeParams(arity, "V"),
          argNames(arity),
          argNames(arity)
        );
    }

    private static String typeParamDocs(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "@param <T%d> the type of the %s argument".formatted(i, Generator.ordinal(i)))
          .collect(Collectors.joining("\n * "));
    }

    private static String paramDocs(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "@param t%d the %s argument".formatted(i, Generator.ordinal(i)))
          .collect(Collectors.joining("\n     * "));
    }

    private static String typeParams(int arity, String last) {
        var params = IntStream.rangeClosed(1, arity)
          .mapToObj("T%d"::formatted)
          .collect(Collectors.joining(", "));
        return params.isEmpty() ? last : params + ", " + last;
    }

    private static String argList(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "T%d t%d".formatted(i, i))
          .collect(Collectors.joining(", "));
    }

    private static String argNames(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj("t%d"::formatted)
          .collect(Collectors.joining(", "));
    }
}
