package com.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Generator {

    private static final String PACKAGE_NAME = "com/pivovarit/forc";
    private static final int ARITY = 2;

    public static void main(String[] args) throws IOException {
        var dir = Files.createDirectories(Path.of(args[0], PACKAGE_NAME));

        for (int i = 1; i <= ARITY; i++) {
            Files.writeString(dir.resolve("Function%d.java".formatted(i)), FunctionTypeGenerator.generate(i));
        }

        Files.writeString(dir.resolve("ForComprehension.java"),
          licenseHeader() +
          """
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
            
                /**
                 * Creates a strict (eager) for-comprehension over two {@link Optional} values.
                 * <p>
                 * Both optionals are evaluated eagerly, and the resulting comprehension
                 * yields a value only if both optionals are present.
                 *
                 * @param o1 the first optional value
                 * @param o2 the second optional value
                 * @param <T1> the type of the first optional value
                 * @param <T2> the type of the second optional value
                 * @return a for-comprehension over two optional values
                 * @throws NullPointerException if any argument is {@code null}
                 */
                public static <T1, T2> For2Optional<T1, T2> forc(Optional<T1> o1, Optional<T2> o2) {
                    Objects.requireNonNull(o1, "o1 is null");
                    Objects.requireNonNull(o2, "o2 is null");
                    return new For2Optional<>(o1, o2);
                }
            
                /**
                 * Represents a for-comprehension over two eagerly evaluated {@link Optional} values.
                 *
                 * @param <T1> the type of the first optional value
                 * @param <T2> the type of the second optional value
                 */
                public static final class For2Optional<T1, T2> {
            
                    private final Optional<T1> o1;
                    private final Optional<T2> o2;
            
                    private For2Optional(Optional<T1> o1, Optional<T2> o2) {
                        this.o1 = o1;
                        this.o2 = o2;
                    }
            
                    /**
                     * Produces the result of the for-comprehension by applying the given function
                     * to the contained values.
                     * <p>
                     * The function is invoked only if both optionals are present.
                     *
                     * @param f the combining function
                     * @param <R> the result type
                     * @return an optional containing the result of the function application,
                     *         or {@link Optional#empty()} if any input optional is empty
                     * @throws NullPointerException if the function is {@code null}
                     */
                    public <R> Optional<R> yield(Function2<? super T1, ? super T2, ? extends R> f) {
                        Objects.requireNonNull(f, "f is null");
            
                        return o1.flatMap(t1 -> o2.map(t2 -> f.apply(t1, t2)));
                    }
                }
            
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
            }
            """);
    }

    private static String licenseHeader() {
        return """
          /*
           * Copyright 2014-2026 Grzegorz Piwowarek, https://4comprehension.com/
           *
           * Licensed under the Apache License, Version 2.0 (the "License");
           * you may not use this file except in compliance with the License.
           * You may obtain a copy of the License at
           *
           * https://www.apache.org/licenses/LICENSE-2.0
           *
           * Unless required by applicable law or agreed to in writing, software
           * distributed under the License is distributed on an "AS IS" BASIS,
           * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
           * See the License for the specific language governing permissions and
           * limitations under the License.
           */
          """;
    }

    static class FunctionTypeGenerator {
        static String generate(int arity) {
            return """
              %spackage com.pivovarit.forc;
              
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
              """.formatted(licenseHeader(), arity, arity > 1 ? "s" : "", arity, typeParams(arity, "R"), argList(arity), arity, typeParams(arity, "V"), argNames(arity), argNames(arity));
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
}
