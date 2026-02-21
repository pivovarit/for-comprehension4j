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
        var allFields = fields(arity, ct.typeName, ct.prefix) + "\n" + guardField(arity);
        var ctorParams = params(arity, ct.typeName, ct.prefix);
        var nnp = nullChecks(arity, ct.prefix);
        var assignFirst = assignFields(arity, ct.prefix) + "\nthis.guard = null;";
        var className = "For%d%s".formatted(arity, ct.typeName);
        var secondCtor = guardConstructor(arity, ct.typeName, ct.prefix);
        var filterMethodStr = filterMethod(arity, className, ct.prefix);
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

          %s

          %s

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
          Generator.indent(allFields, 4),
          arity, ct.typeName, ctorParams,
          Generator.indent(assignFirst, 8),
          Generator.indent(secondCtor, 4),
          Generator.indent(filterMethodStr, 4),
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
            case OPTIONAL -> guardedYieldFlatMapChain(arity, "o");
            case STREAM -> guardedYieldStreamChain(arity);
            case ITERABLE -> guardedYieldForLoopChain(arity, "i");
        };
    }

    private static String generateLazy(int arity, ContainerType ct) {
        var tparams = typeParams(arity);
        var methodParams = lazyParams(arity, ct.typeName, ct.prefix);
        var ctorArgs = argNamesPrefixed(arity, ct.prefix);
        var allFields = lazyFields(arity, ct.typeName, ct.prefix) + "\n" + guardField(arity);
        var nnp = nullChecks(arity, ct.prefix);
        var assignFirst = assignFields(arity, ct.prefix) + "\nthis.guard = null;";
        var className = "ForLazy%d%s".formatted(arity, ct.typeName);
        var secondCtor = lazyGuardConstructor(arity, ct.typeName, ct.prefix);
        var filterMethodStr = filterMethod(arity, className, ct.prefix);
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

          %s

          %s

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
          Generator.indent(allFields, 4),
          arity, ct.typeName, methodParams,
          Generator.indent(assignFirst, 8),
          Generator.indent(secondCtor, 4),
          Generator.indent(filterMethodStr, 4),
          ct.returnType, arity, tparamsWithRWildcard(arity),
          Generator.indent(yield, 8)
        );
    }

    private static String lazyYieldChain(int arity, ContainerType ct) {
        return switch (ct) {
            case OPTIONAL -> guardedLazyYieldOptionalChain(arity);
            case STREAM -> guardedLazyYieldStreamChain(arity);
            case ITERABLE -> guardedLazyYieldIterableChain(arity);
        };
    }

    private static String guardField(int arity) {
        return "private final Function%d<%s, Boolean> guard;".formatted(arity, typeParams(arity));
    }

    private static String guardConstructor(int arity, String typeName, String prefix) {
        var funcType = "Function%d<%s, Boolean>".formatted(arity, typeParams(arity));
        var ctorParams = params(arity, typeName, prefix) + ", " + funcType + " guard";
        var assignBody = assignFields(arity, prefix) + "\nthis.guard = guard;";
        return """
          private For%d%s(%s) {
          %s
          }
          """.formatted(arity, typeName, ctorParams, Generator.indent(assignBody, 4));
    }

    private static String lazyGuardConstructor(int arity, String typeName, String prefix) {
        var funcType = "Function%d<%s, Boolean>".formatted(arity, typeParams(arity));
        var ctorParams = lazyParams(arity, typeName, prefix) + ", " + funcType + " guard";
        var assignBody = assignFields(arity, prefix) + "\nthis.guard = guard;";
        return """
          private ForLazy%d%s(%s) {
          %s
          }
          """.formatted(arity, typeName, ctorParams, Generator.indent(assignBody, 4));
    }

    private static String filterMethod(int arity, String className, String prefix) {
        var tparams = typeParams(arity);
        var funcType = "Function%d<%s, Boolean>".formatted(arity, tparams);
        var lambdaParams = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", "));
        var andLambda = "(%s) -> existingGuard.apply(%s) && predicate.apply(%s)"
          .formatted(lambdaParams, lambdaParams, lambdaParams);
        var ctorArgs = argNamesPrefixed(arity, prefix) + ", newGuard";
        return """
          public %s<%s> filter(%s predicate) {
              Objects.requireNonNull(predicate, "predicate is null");
              %s existingGuard = this.guard;
              %s newGuard = existingGuard == null ? predicate : %s;
              return new %s<>(%s);
          }
          """.formatted(
          className, tparams,
          funcType,
          funcType,
          funcType,
          andLambda,
          className, ctorArgs
        );
    }

    private static String guardedYieldFlatMapChain(int arity, String prefix) {
        var args = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", "));

        var sb = new StringBuilder("return %s1.flatMap(t1 ->\n".formatted(prefix));
        for (int i = 2; i <= arity; i++) {
            sb.append(" ".repeat((i - 1) * 4));
            sb.append("%s%d.flatMap(t%d ->\n".formatted(prefix, i, i));
        }
        sb.append(" ".repeat(arity * 4));
        sb.append("(guard != null && !guard.apply(%s)) ? Optional.empty() : Optional.of(f.apply(%s))"
          .formatted(args, args));
        sb.append(")".repeat(arity));
        sb.append(";");
        return sb.toString();
    }

    private static String guardedYieldStreamChain(int arity) {
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
        sb.append("l%d.stream().filter(t%d -> guard == null || guard.apply(%s)).map(t%d -> f.apply(%s))"
          .formatted(arity, arity, args, arity, args));
        sb.append(")".repeat(arity - 1));
        sb.append(";");
        return collections + "\n\n" + sb.toString();
    }

    private static String guardedYieldForLoopChain(int arity, String prefix) {
        var args = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", "));

        var loops = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> " ".repeat((i - 1) * 4) + "for (T%d t%d : %s%d) {".formatted(i, i, prefix, i))
          .collect(Collectors.joining("\n"));

        var body = " ".repeat(arity * 4) + "if (guard == null || guard.apply(" + args + ")) {\n"
          + " ".repeat((arity + 1) * 4) + "result.add(f.apply(" + args + "));\n"
          + " ".repeat(arity * 4) + "}";

        var closes = IntStream.range(0, arity)
          .map(i -> arity - 1 - i)
          .mapToObj(i -> " ".repeat(i * 4) + "}")
          .collect(Collectors.joining("\n"));

        return "List<R> result = new ArrayList<>();\n" + loops + "\n" + body + "\n" + closes + "\nreturn result;";
    }

    private static String guardedLazyYieldOptionalChain(int arity) {
        var args = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", "));

        var sb = new StringBuilder("return o1.flatMap(t1 ->\n");
        for (int i = 2; i <= arity; i++) {
            var applyArgs = IntStream.rangeClosed(1, i - 1)
              .mapToObj(j -> "t" + j)
              .collect(Collectors.joining(", "));
            sb.append(" ".repeat((i - 1) * 4));
            sb.append("o%d.apply(%s).flatMap(t%d ->\n".formatted(i, applyArgs, i));
        }
        sb.append(" ".repeat(arity * 4));
        sb.append("(guard != null && !guard.apply(%s)) ? Optional.empty() : Optional.of(f.apply(%s))"
          .formatted(args, args));
        sb.append(")".repeat(arity));
        sb.append(";");
        return sb.toString();
    }

    private static String guardedLazyYieldStreamChain(int arity) {
        var args = IntStream.rangeClosed(1, arity)
          .mapToObj(i -> "t" + i)
          .collect(Collectors.joining(", "));

        var sb = new StringBuilder("return s1.flatMap(t1 ->\n");
        for (int i = 2; i < arity; i++) {
            var applyArgs = IntStream.rangeClosed(1, i - 1)
              .mapToObj(j -> "t" + j)
              .collect(Collectors.joining(", "));
            sb.append(" ".repeat((i - 1) * 4));
            sb.append("s%d.apply(%s).flatMap(t%d ->\n".formatted(i, applyArgs, i));
        }
        var lastApplyArgs = IntStream.rangeClosed(1, arity - 1)
          .mapToObj(j -> "t" + j)
          .collect(Collectors.joining(", "));
        sb.append(" ".repeat((arity - 1) * 4));
        sb.append("s%d.apply(%s).filter(t%d -> guard == null || guard.apply(%s)).map(t%d -> f.apply(%s))"
          .formatted(arity, lastApplyArgs, arity, args, arity, args));
        sb.append(")".repeat(arity - 1));
        sb.append(";");
        return sb.toString();
    }

    private static String guardedLazyYieldIterableChain(int arity) {
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
        sb.append("if (guard == null || guard.apply(%s)) {\n".formatted(args));
        sb.append(" ".repeat((arity + 1) * 4));
        sb.append("result.add(f.apply(%s));\n".formatted(args));
        sb.append(" ".repeat(arity * 4));
        sb.append("}\n");
        for (int i = arity; i >= 1; i--) {
            sb.append(" ".repeat((i - 1) * 4));
            sb.append("}\n");
        }
        sb.append("return Collections.unmodifiableList(result);");
        return sb.toString();
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
}
