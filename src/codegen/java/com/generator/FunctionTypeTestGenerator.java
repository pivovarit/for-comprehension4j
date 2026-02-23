package com.generator;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class FunctionTypeTestGenerator {

    static String generate(int maxArity) {
        var tests = new StringBuilder();
        for (int i = 1; i <= maxArity; i++) {
            tests.append(generateAndThenTest(i));
            tests.append(generateAndThenNullTest(i));
        }

        return """
          package com.pivovarit.forc;

          import org.junit.jupiter.api.Test;

          import static org.assertj.core.api.Assertions.assertThat;
          import static org.assertj.core.api.Assertions.assertThatThrownBy;

          class FunctionTypeTest {
          %s
          }
          """.formatted(Generator.indent(tests.toString(), 4));
    }

    private static String generateAndThenTest(int arity) {
        var typeParams = typeParams(arity);
        var lambda = functionLambda(arity);
        var applyArgs = applyArgs(arity);
        var expectedSum = arity * (arity + 1) / 2;

        return """

          @Test
          void shouldAndThen%d() {
              Function%d<%s> f = %s;
              assertThat(f.andThen(r -> r * 2).apply(%s)).isEqualTo(%d);
          }
        """.formatted(arity, arity, typeParams, lambda, applyArgs, expectedSum * 2);
    }

    private static String generateAndThenNullTest(int arity) {
        var typeParams = typeParams(arity);
        var lambda = functionLambda(arity);

        return """

          @Test
          void shouldAndThenThrowOnNull%d() {
              Function%d<%s> f = %s;
              assertThatThrownBy(() -> f.andThen(null)).isInstanceOf(NullPointerException.class);
          }
        """.formatted(arity, arity, typeParams, lambda);
    }

    private static String typeParams(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "Integer")
          .collect(Collectors.joining(", ")) + ", Integer";
    }

    private static String functionLambda(int arity) {
        var params = IntStream.rangeClosed(1, arity).mapToObj(i -> "t" + i).collect(Collectors.joining(", "));
        var body = IntStream.rangeClosed(1, arity).mapToObj(i -> "t" + i).collect(Collectors.joining(" + "));
        var paramList = arity == 1 ? params : "(" + params + ")";
        return "%s -> %s".formatted(paramList, body);
    }

    private static String applyArgs(int arity) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(Integer::toString)
          .collect(Collectors.joining(", "));
    }
}
