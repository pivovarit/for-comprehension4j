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
           */
          @FunctionalInterface
          public interface Function%d<%s> {
          
              R apply(%s);
          
              default <V> Function%d<%s> andThen(Function1<? super R, ? extends V> after) {
                  Objects.requireNonNull(after);
                  return (%s) -> after.apply(apply(%s));
              }
          }
          """.formatted(
          arity,
          arity > 1 ? "s" : "",
          arity,
          typeParams(arity, "R"),
          argList(arity),
          arity,
          typeParams(arity, "V"),
          argNames(arity),
          argNames(arity)
        );
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
