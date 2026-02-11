package com.generator;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ForComprehensionGenerator {

    enum ContainerType {
        OPTIONAL("Optional", "o", "Optional<R>", "optional value"),
        STREAM("Stream", "s", "Stream<R>", "stream"),
        ITERABLE("Iterable", "i", "List<R>", "iterable");

        final String typeName;
        final String prefix;
        final String returnType;
        final String javadocNoun;

        ContainerType(String typeName, String prefix, String returnType, String javadocNoun) {
            this.typeName = typeName;
            this.prefix = prefix;
            this.returnType = returnType;
            this.javadocNoun = javadocNoun;
        }
    }

    static String generate(int arity) {
        var sections = Arrays.stream(ContainerType.values())
          .map(ct -> Generator.indent(IntStream.rangeClosed(2, arity)
            .mapToObj(a -> generateEager(a, ct))
            .collect(Collectors.joining("\n")), 4))
          .collect(Collectors.toList());

        var lazySections = Arrays.stream(ContainerType.values())
          .map(ct -> Generator.indent(IntStream.rangeClosed(2, arity)
            .mapToObj(a -> generateLazy(a, ct))
            .collect(Collectors.joining("\n")), 4))
          .collect(Collectors.toList());

        return """
          package com.pivovarit.forc;

          import java.util.ArrayList;
          import java.util.Collections;
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
          """.formatted(
          sections.get(0),
          sections.get(1),
          sections.get(2),
          lazySections.get(0),
          lazySections.get(1),
          lazySections.get(2));
    }

    private static String generateEager(int arity, ContainerType ct) {
        var tparams = typeParams(arity);
        var methodParams = params(arity, ct.typeName, ct.prefix);
        var ctorArgs = argNamesPrefixed(arity, ct.prefix);
        var fields = fields(arity, ct.typeName, ct.prefix);
        var ctorParams = params(arity, ct.typeName, ct.prefix);
        var nnp = nullChecks(arity, ct.prefix);
        var yield = eagerYieldChain(arity, ct);

        return """
          /**
           * Creates a strict (eager) for-comprehension over %d {@link %s} value%s.
           * <p>
           * %s
           *
           * %s
           * %s
           * @return a for-comprehension over %d %s value%s
           * @throws NullPointerException if any argument is {@code null}
           */
          public static <%s> For%d%s<%s> forc(%s) {
          %s
              return new For%d%s<>(%s);
          }

          /**
           * Represents a for-comprehension over %d eagerly evaluated {@link %s} value%s.
           *
           * %s
           */
          public static final class For%d%s<%s> {

          %s

              private For%d%s(%s) {
          %s
              }

              /**
               * Produces the result of the for-comprehension by applying the given function
               * to the %s.
               *%s
               * @param f the combining function
               * @param <R> the result type
               * @return %s
               * @throws NullPointerException if the function is {@code null}
               */
              public <R> %s yield(Function%d<%s> f) {
                  Objects.requireNonNull(f, "f is null");

          %s
              }
          }
          """.formatted(
          arity, ct.typeName, "s",
          eagerJavadocDescription(ct),
          javadocParams(arity, ct.prefix, ct.javadocNoun),
          javadocTypeParams(arity, ct.javadocNoun),
          arity, ct.typeName.toLowerCase(), "s",
          typeParams(arity),
          arity, ct.typeName, tparams,
          methodParams,
          Generator.indent(nnp, 4),
          arity, ct.typeName, ctorArgs,
          arity, ct.typeName, "s",
          javadocTypeParams(arity, ct.javadocNoun),
          arity, ct.typeName, tparams,
          Generator.indent(fields, 4),
          arity, ct.typeName, ctorParams,
          Generator.indent(assignFields(arity, ct.prefix), 8),
          eagerYieldJavadocSubject(ct),
          eagerYieldJavadocExtra(ct),
          eagerYieldJavadocReturn(ct),
          ct.returnType, arity, tparamsWithRWildcard(arity),
          Generator.indent(yield, 8)
        );
    }

    private static String eagerJavadocDescription(ContainerType ct) {
        return switch (ct) {
            case OPTIONAL -> "All optionals are evaluated eagerly, and the resulting comprehension\n * yields a value only if all optionals are present.";
            case STREAM -> "All streams are evaluated eagerly. The resulting comprehension yields\n * the cartesian product of all streams, transformed by the yield function.";
            case ITERABLE -> "All iterables are evaluated eagerly. The resulting comprehension yields\n * the cartesian product of all iterables, transformed by the yield function.";
        };
    }

    private static String eagerYieldJavadocSubject(ContainerType ct) {
        return switch (ct) {
            case OPTIONAL -> "contained values";
            case STREAM -> "cartesian product of the stream elements";
            case ITERABLE -> "cartesian product of the iterable elements";
        };
    }

    private static String eagerYieldJavadocExtra(ContainerType ct) {
        return switch (ct) {
            case OPTIONAL -> " <p>\n     * The function is invoked only if all optionals are present.\n     *";
            case STREAM, ITERABLE -> "";
        };
    }

    private static String eagerYieldJavadocReturn(ContainerType ct) {
        return switch (ct) {
            case OPTIONAL -> "an optional containing the result of the function application,\n     *         or {@link Optional#empty()} if any input optional is empty";
            case STREAM -> "a stream containing the results of applying the function to all combinations";
            case ITERABLE -> "a list containing the results of applying the function to all combinations";
        };
    }

    private static String eagerYieldChain(int arity, ContainerType ct) {
        return switch (ct) {
            case OPTIONAL -> yieldFlatMapChain(arity, "o");
            case STREAM -> yieldStreamChain(arity);
            case ITERABLE -> yieldForLoopChain(arity, "i");
        };
    }

    private static String generateLazy(int arity, ContainerType ct) {
        var tparams = typeParams(arity);
        var methodParams = lazyParams(arity, ct.typeName, ct.prefix);
        var ctorArgs = argNamesPrefixed(arity, ct.prefix);
        var fields = lazyFields(arity, ct.typeName, ct.prefix);
        var nnp = nullChecks(arity, ct.prefix);
        var yield = lazyYieldChain(arity, ct);

        return """
          public static <%s> ForLazy%d%s<%s> forc(%s) {
          %s
              return new ForLazy%d%s<>(%s);
          }

          public static final class ForLazy%d%s<%s> {

          %s

              private ForLazy%d%s(%s) {
          %s
              }

              public <R> %s yield(Function%d<%s> f) {
                  Objects.requireNonNull(f, "f is null");

          %s
              }
          }
          """.formatted(
          tparams,
          arity, ct.typeName, tparams,
          methodParams,
          Generator.indent(nnp, 4),
          arity, ct.typeName, ctorArgs,
          arity, ct.typeName, tparams,
          Generator.indent(fields, 4),
          arity, ct.typeName, methodParams,
          Generator.indent(assignFields(arity, ct.prefix), 8),
          ct.returnType, arity, tparamsWithRWildcard(arity),
          Generator.indent(yield, 8)
        );
    }

    private static String lazyYieldChain(int arity, ContainerType ct) {
        return switch (ct) {
            case OPTIONAL, STREAM -> yieldLazyFlatMapChain(arity, ct.prefix);
            case ITERABLE -> yieldLazyIterableChain(arity);
        };
    }

    private static String yieldFlatMapChain(int arity, String prefix) {
        if (arity == 1) {
            return "return %s1.map(t1 -> f.apply(t1));".formatted(prefix);
        }

        var args = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", "));

        var sb = new StringBuilder("return %s1.flatMap(t1 ->\n".formatted(prefix));
        for (int i = 2; i < arity; i++) {
            sb.append(" ".repeat((i - 1) * 4));
            sb.append("%s%d.flatMap(t%d ->\n".formatted(prefix, i, i));
        }
        sb.append(" ".repeat((arity - 1) * 4));
        sb.append("%s%d.map(t%d -> f.apply(%s))".formatted(prefix, arity, arity, args));
        sb.append(")".repeat(arity - 1));
        sb.append(";");

        return sb.toString();
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

        var sb = new StringBuilder("return s1.flatMap(t1 ->\n");
        for (int i = 2; i < arity; i++) {
            sb.append(" ".repeat((i - 1) * 4));
            sb.append("l%d.stream().flatMap(t%d ->\n".formatted(i, i));
        }
        sb.append(" ".repeat((arity - 1) * 4));
        sb.append("l%d.stream().map(t%d -> f.apply(%s))".formatted(arity, arity, args));
        sb.append(")".repeat(arity - 1));
        sb.append(";");

        return collections + "\n\n" + sb.toString();
    }

    private static String yieldForLoopChain(int arity, String prefix) {
        var args = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", "));

        var loops = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> " ".repeat((i - 1) * 4) + "for (T%d t%d : %s%d) {".formatted(i, i, prefix, i))
          .collect(Collectors.joining("\n"));

        var body = " ".repeat(arity * 4) + "result.add(f.apply(" + args + "));";

        var closes = IntStream.range(0, arity)
          .map(i -> arity - 1 - i)
          .mapToObj(i -> " ".repeat(i * 4) + "}")
          .collect(Collectors.joining("\n"));

        return "List<R> result = new ArrayList<>();\n" + loops + "\n" + body + "\n" + closes + "\nreturn result;";
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

    private static String params(int arity, String typeName, String prefix) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "%s<T%d> %s%d".formatted(typeName, i, prefix, i))
          .collect(Collectors.joining(", "));
    }

    private static String fields(int arity, String typeName, String prefix) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "private final %s<T%d> %s%d;".formatted(typeName, i, prefix, i))
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
          .mapToObj(i -> "@param %s%d the %s %s".formatted(prefix, i, Generator.ordinal(i), description))
          .collect(Collectors.joining("\n * "));
    }

    private static String javadocTypeParams(int arity, String description) {
        return IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "@param <T%d> the type of the %s %s".formatted(i, Generator.ordinal(i), description))
          .collect(Collectors.joining("\n * "));
    }

    private static String lazyParams(int arity, String typeName, String prefix) {
        var sb = new StringBuilder("%s<T1> %s1".formatted(typeName, prefix));
        for (int i = 2; i <= arity; i++) {
            var funcParams = IntStream.rangeClosed(1, i - 1)
              .mapToObj("? super T%d"::formatted)
              .collect(Collectors.joining(", "));
            sb.append(", Function%d<%s, %s<T%d>> %s%d".formatted(i - 1, funcParams, typeName, i, prefix, i));
        }
        return sb.toString();
    }

    private static String lazyFields(int arity, String typeName, String prefix) {
        var sb = new StringBuilder("private final %s<T1> %s1;".formatted(typeName, prefix));
        for (int i = 2; i <= arity; i++) {
            var funcParams = IntStream.rangeClosed(1, i - 1)
              .mapToObj("? super T%d"::formatted)
              .collect(Collectors.joining(", "));
            sb.append("\nprivate final Function%d<%s, %s<T%d>> %s%d;".formatted(i - 1, funcParams, typeName, i, prefix, i));
        }
        return sb.toString();
    }

    private static String yieldLazyFlatMapChain(int arity, String prefix) {
        var args = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", "));

        var sb = new StringBuilder("return %s1.flatMap(t1 ->\n".formatted(prefix));
        for (int i = 2; i < arity; i++) {
            var applyArgs = IntStream.rangeClosed(1, i - 1)
              .mapToObj(j -> "t" + j)
              .collect(Collectors.joining(", "));
            sb.append(" ".repeat((i - 1) * 4));
            sb.append("%s%d.apply(%s).flatMap(t%d ->\n".formatted(prefix, i, applyArgs, i));
        }
        var lastApplyArgs = IntStream.rangeClosed(1, arity - 1)
          .mapToObj(j -> "t" + j)
          .collect(Collectors.joining(", "));
        sb.append(" ".repeat((arity - 1) * 4));
        sb.append("%s%d.apply(%s).map(t%d -> f.apply(%s))".formatted(prefix, arity, lastApplyArgs, arity, args));
        sb.append(")".repeat(arity - 1));
        sb.append(";");
        return sb.toString();
    }

    private static String yieldLazyIterableChain(int arity) {
        var args = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", "));

        var sb = new StringBuilder("List<R> result = new ArrayList<>();\n");
        sb.append("for (T1 t1 : i1) {\n");
        for (int i = 2; i <= arity; i++) {
            var applyArgs = IntStream.rangeClosed(1, i - 1)
              .mapToObj(j -> "t" + j)
              .collect(Collectors.joining(", "));
            sb.append(" ".repeat((i - 1) * 4));
            sb.append("for (T%d t%d : i%d.apply(%s)) {\n".formatted(i, i, i, applyArgs));
        }
        sb.append(" ".repeat(arity * 4));
        sb.append("result.add(f.apply(%s));\n".formatted(args));
        for (int i = arity; i >= 1; i--) {
            sb.append(" ".repeat((i - 1) * 4));
            sb.append("}\n");
        }
        sb.append("return Collections.unmodifiableList(result);");
        return sb.toString();
    }
}
