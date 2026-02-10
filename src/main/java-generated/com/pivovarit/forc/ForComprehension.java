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

    /**
     * Creates a strict (eager) for-comprehension over 2 {@link Optional} values.
     * <p>
     * All optionals are evaluated eagerly, and the resulting comprehension
     * yields a value only if all optionals are present.
     *
     * @param o1 the first optional value
     * @param o2 the second optional value
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @return a for-comprehension over 2 optional values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2> For2Optional<T1, T2> forc(Optional<T1> o1, Optional<T2> o2) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        return new For2Optional<>(o1, o2);
    }

    /**
     * Represents a for-comprehension over 2 eagerly evaluated {@link Optional} values.
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
         * The function is invoked only if all optionals are present.
         *
         * @param f the combining function
         * @param <R> the result type
         * @return an optional containing the result of the function application,
         *         or {@link Optional#empty()} if any input optional is empty
         * @throws NullPointerException if the function is {@code null}
         */
        public <R> Optional<R> yield(Function2<? super T1, ? super T2, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.map(t2 -> f.apply(t1, t2)));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 3 {@link Optional} values.
     * <p>
     * All optionals are evaluated eagerly, and the resulting comprehension
     * yields a value only if all optionals are present.
     *
     * @param o1 the first optional value
     * @param o2 the second optional value
     * @param o3 the third optional value
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @param <T3> the type of the third optional value
     * @return a for-comprehension over 3 optional values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3> For3Optional<T1, T2, T3> forc(Optional<T1> o1, Optional<T2> o2, Optional<T3> o3) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        Objects.requireNonNull(o3, "o3 is null");
        return new For3Optional<>(o1, o2, o3);
    }

    /**
     * Represents a for-comprehension over 3 eagerly evaluated {@link Optional} values.
     *
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @param <T3> the type of the third optional value
     */
    public static final class For3Optional<T1, T2, T3> {

        private final Optional<T1> o1;
        private final Optional<T2> o2;
        private final Optional<T3> o3;

        private For3Optional(Optional<T1> o1, Optional<T2> o2, Optional<T3> o3) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
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
        public <R> Optional<R> yield(Function3<? super T1, ? super T2, ? super T3, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.flatMap(t2 ->
                    o3.map(t3 -> f.apply(t1, t2, t3))));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 4 {@link Optional} values.
     * <p>
     * All optionals are evaluated eagerly, and the resulting comprehension
     * yields a value only if all optionals are present.
     *
     * @param o1 the first optional value
     * @param o2 the second optional value
     * @param o3 the third optional value
     * @param o4 the 4th optional value
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @param <T3> the type of the third optional value
     * @param <T4> the type of the 4th optional value
     * @return a for-comprehension over 4 optional values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4> For4Optional<T1, T2, T3, T4> forc(Optional<T1> o1, Optional<T2> o2, Optional<T3> o3, Optional<T4> o4) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        Objects.requireNonNull(o3, "o3 is null");
        Objects.requireNonNull(o4, "o4 is null");
        return new For4Optional<>(o1, o2, o3, o4);
    }

    /**
     * Represents a for-comprehension over 4 eagerly evaluated {@link Optional} values.
     *
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @param <T3> the type of the third optional value
     * @param <T4> the type of the 4th optional value
     */
    public static final class For4Optional<T1, T2, T3, T4> {

        private final Optional<T1> o1;
        private final Optional<T2> o2;
        private final Optional<T3> o3;
        private final Optional<T4> o4;

        private For4Optional(Optional<T1> o1, Optional<T2> o2, Optional<T3> o3, Optional<T4> o4) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
            this.o4 = o4;
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
        public <R> Optional<R> yield(Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.flatMap(t2 ->
                    o3.flatMap(t3 ->
                        o4.map(t4 -> f.apply(t1, t2, t3, t4)))));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 5 {@link Optional} values.
     * <p>
     * All optionals are evaluated eagerly, and the resulting comprehension
     * yields a value only if all optionals are present.
     *
     * @param o1 the first optional value
     * @param o2 the second optional value
     * @param o3 the third optional value
     * @param o4 the 4th optional value
     * @param o5 the 5th optional value
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @param <T3> the type of the third optional value
     * @param <T4> the type of the 4th optional value
     * @param <T5> the type of the 5th optional value
     * @return a for-comprehension over 5 optional values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4, T5> For5Optional<T1, T2, T3, T4, T5> forc(Optional<T1> o1, Optional<T2> o2, Optional<T3> o3, Optional<T4> o4, Optional<T5> o5) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        Objects.requireNonNull(o3, "o3 is null");
        Objects.requireNonNull(o4, "o4 is null");
        Objects.requireNonNull(o5, "o5 is null");
        return new For5Optional<>(o1, o2, o3, o4, o5);
    }

    /**
     * Represents a for-comprehension over 5 eagerly evaluated {@link Optional} values.
     *
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @param <T3> the type of the third optional value
     * @param <T4> the type of the 4th optional value
     * @param <T5> the type of the 5th optional value
     */
    public static final class For5Optional<T1, T2, T3, T4, T5> {

        private final Optional<T1> o1;
        private final Optional<T2> o2;
        private final Optional<T3> o3;
        private final Optional<T4> o4;
        private final Optional<T5> o5;

        private For5Optional(Optional<T1> o1, Optional<T2> o2, Optional<T3> o3, Optional<T4> o4, Optional<T5> o5) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
            this.o4 = o4;
            this.o5 = o5;
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
        public <R> Optional<R> yield(Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.flatMap(t2 ->
                    o3.flatMap(t3 ->
                        o4.flatMap(t4 ->
                            o5.map(t5 -> f.apply(t1, t2, t3, t4, t5))))));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 6 {@link Optional} values.
     * <p>
     * All optionals are evaluated eagerly, and the resulting comprehension
     * yields a value only if all optionals are present.
     *
     * @param o1 the first optional value
     * @param o2 the second optional value
     * @param o3 the third optional value
     * @param o4 the 4th optional value
     * @param o5 the 5th optional value
     * @param o6 the 6th optional value
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @param <T3> the type of the third optional value
     * @param <T4> the type of the 4th optional value
     * @param <T5> the type of the 5th optional value
     * @param <T6> the type of the 6th optional value
     * @return a for-comprehension over 6 optional values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4, T5, T6> For6Optional<T1, T2, T3, T4, T5, T6> forc(Optional<T1> o1, Optional<T2> o2, Optional<T3> o3, Optional<T4> o4, Optional<T5> o5, Optional<T6> o6) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        Objects.requireNonNull(o3, "o3 is null");
        Objects.requireNonNull(o4, "o4 is null");
        Objects.requireNonNull(o5, "o5 is null");
        Objects.requireNonNull(o6, "o6 is null");
        return new For6Optional<>(o1, o2, o3, o4, o5, o6);
    }

    /**
     * Represents a for-comprehension over 6 eagerly evaluated {@link Optional} values.
     *
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @param <T3> the type of the third optional value
     * @param <T4> the type of the 4th optional value
     * @param <T5> the type of the 5th optional value
     * @param <T6> the type of the 6th optional value
     */
    public static final class For6Optional<T1, T2, T3, T4, T5, T6> {

        private final Optional<T1> o1;
        private final Optional<T2> o2;
        private final Optional<T3> o3;
        private final Optional<T4> o4;
        private final Optional<T5> o5;
        private final Optional<T6> o6;

        private For6Optional(Optional<T1> o1, Optional<T2> o2, Optional<T3> o3, Optional<T4> o4, Optional<T5> o5, Optional<T6> o6) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
            this.o4 = o4;
            this.o5 = o5;
            this.o6 = o6;
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
        public <R> Optional<R> yield(Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.flatMap(t2 ->
                    o3.flatMap(t3 ->
                        o4.flatMap(t4 ->
                            o5.flatMap(t5 ->
                                o6.map(t6 -> f.apply(t1, t2, t3, t4, t5, t6)))))));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 7 {@link Optional} values.
     * <p>
     * All optionals are evaluated eagerly, and the resulting comprehension
     * yields a value only if all optionals are present.
     *
     * @param o1 the first optional value
     * @param o2 the second optional value
     * @param o3 the third optional value
     * @param o4 the 4th optional value
     * @param o5 the 5th optional value
     * @param o6 the 6th optional value
     * @param o7 the 7th optional value
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @param <T3> the type of the third optional value
     * @param <T4> the type of the 4th optional value
     * @param <T5> the type of the 5th optional value
     * @param <T6> the type of the 6th optional value
     * @param <T7> the type of the 7th optional value
     * @return a for-comprehension over 7 optional values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4, T5, T6, T7> For7Optional<T1, T2, T3, T4, T5, T6, T7> forc(Optional<T1> o1, Optional<T2> o2, Optional<T3> o3, Optional<T4> o4, Optional<T5> o5, Optional<T6> o6, Optional<T7> o7) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        Objects.requireNonNull(o3, "o3 is null");
        Objects.requireNonNull(o4, "o4 is null");
        Objects.requireNonNull(o5, "o5 is null");
        Objects.requireNonNull(o6, "o6 is null");
        Objects.requireNonNull(o7, "o7 is null");
        return new For7Optional<>(o1, o2, o3, o4, o5, o6, o7);
    }

    /**
     * Represents a for-comprehension over 7 eagerly evaluated {@link Optional} values.
     *
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @param <T3> the type of the third optional value
     * @param <T4> the type of the 4th optional value
     * @param <T5> the type of the 5th optional value
     * @param <T6> the type of the 6th optional value
     * @param <T7> the type of the 7th optional value
     */
    public static final class For7Optional<T1, T2, T3, T4, T5, T6, T7> {

        private final Optional<T1> o1;
        private final Optional<T2> o2;
        private final Optional<T3> o3;
        private final Optional<T4> o4;
        private final Optional<T5> o5;
        private final Optional<T6> o6;
        private final Optional<T7> o7;

        private For7Optional(Optional<T1> o1, Optional<T2> o2, Optional<T3> o3, Optional<T4> o4, Optional<T5> o5, Optional<T6> o6, Optional<T7> o7) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
            this.o4 = o4;
            this.o5 = o5;
            this.o6 = o6;
            this.o7 = o7;
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
        public <R> Optional<R> yield(Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.flatMap(t2 ->
                    o3.flatMap(t3 ->
                        o4.flatMap(t4 ->
                            o5.flatMap(t5 ->
                                o6.flatMap(t6 ->
                                    o7.map(t7 -> f.apply(t1, t2, t3, t4, t5, t6, t7))))))));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 8 {@link Optional} values.
     * <p>
     * All optionals are evaluated eagerly, and the resulting comprehension
     * yields a value only if all optionals are present.
     *
     * @param o1 the first optional value
     * @param o2 the second optional value
     * @param o3 the third optional value
     * @param o4 the 4th optional value
     * @param o5 the 5th optional value
     * @param o6 the 6th optional value
     * @param o7 the 7th optional value
     * @param o8 the 8th optional value
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @param <T3> the type of the third optional value
     * @param <T4> the type of the 4th optional value
     * @param <T5> the type of the 5th optional value
     * @param <T6> the type of the 6th optional value
     * @param <T7> the type of the 7th optional value
     * @param <T8> the type of the 8th optional value
     * @return a for-comprehension over 8 optional values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> For8Optional<T1, T2, T3, T4, T5, T6, T7, T8> forc(Optional<T1> o1, Optional<T2> o2, Optional<T3> o3, Optional<T4> o4, Optional<T5> o5, Optional<T6> o6, Optional<T7> o7, Optional<T8> o8) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        Objects.requireNonNull(o3, "o3 is null");
        Objects.requireNonNull(o4, "o4 is null");
        Objects.requireNonNull(o5, "o5 is null");
        Objects.requireNonNull(o6, "o6 is null");
        Objects.requireNonNull(o7, "o7 is null");
        Objects.requireNonNull(o8, "o8 is null");
        return new For8Optional<>(o1, o2, o3, o4, o5, o6, o7, o8);
    }

    /**
     * Represents a for-comprehension over 8 eagerly evaluated {@link Optional} values.
     *
     * @param <T1> the type of the first optional value
     * @param <T2> the type of the second optional value
     * @param <T3> the type of the third optional value
     * @param <T4> the type of the 4th optional value
     * @param <T5> the type of the 5th optional value
     * @param <T6> the type of the 6th optional value
     * @param <T7> the type of the 7th optional value
     * @param <T8> the type of the 8th optional value
     */
    public static final class For8Optional<T1, T2, T3, T4, T5, T6, T7, T8> {

        private final Optional<T1> o1;
        private final Optional<T2> o2;
        private final Optional<T3> o3;
        private final Optional<T4> o4;
        private final Optional<T5> o5;
        private final Optional<T6> o6;
        private final Optional<T7> o7;
        private final Optional<T8> o8;

        private For8Optional(Optional<T1> o1, Optional<T2> o2, Optional<T3> o3, Optional<T4> o4, Optional<T5> o5, Optional<T6> o6, Optional<T7> o7, Optional<T8> o8) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
            this.o4 = o4;
            this.o5 = o5;
            this.o6 = o6;
            this.o7 = o7;
            this.o8 = o8;
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
        public <R> Optional<R> yield(Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.flatMap(t2 ->
                    o3.flatMap(t3 ->
                        o4.flatMap(t4 ->
                            o5.flatMap(t5 ->
                                o6.flatMap(t6 ->
                                    o7.flatMap(t7 ->
                                        o8.map(t8 -> f.apply(t1, t2, t3, t4, t5, t6, t7, t8)))))))));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 2 {@link Stream} values.
     * <p>
     * All streams are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all streams, transformed by the yield function.
     *
     * @param s1 the first stream
     * @param s2 the second stream
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @return a for-comprehension over 2 stream values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2> For2Stream<T1, T2> forc(Stream<T1> s1, Stream<T2> s2) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        return new For2Stream<>(s1, s2);
    }

    /**
     * Represents a for-comprehension over 2 eagerly evaluated {@link Stream} values.
     *
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     */
    public static final class For2Stream<T1, T2> {

        private final Stream<T1> s1;
        private final Stream<T2> s2;

        private For2Stream(Stream<T1> s1, Stream<T2> s2) {
            this.s1 = s1;
            this.s2 = s2;
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
        public <R> Stream<R> yield(Function2<? super T1, ? super T2, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<T2> l2 = s2.collect(Collectors.toList());

            return s1.flatMap(t1 ->
                l2.stream().map(t2 -> f.apply(t1, t2)));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 3 {@link Stream} values.
     * <p>
     * All streams are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all streams, transformed by the yield function.
     *
     * @param s1 the first stream
     * @param s2 the second stream
     * @param s3 the third stream
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @param <T3> the type of the third stream
     * @return a for-comprehension over 3 stream values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3> For3Stream<T1, T2, T3> forc(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        Objects.requireNonNull(s3, "s3 is null");
        return new For3Stream<>(s1, s2, s3);
    }

    /**
     * Represents a for-comprehension over 3 eagerly evaluated {@link Stream} values.
     *
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @param <T3> the type of the third stream
     */
    public static final class For3Stream<T1, T2, T3> {

        private final Stream<T1> s1;
        private final Stream<T2> s2;
        private final Stream<T3> s3;

        private For3Stream(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
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
        public <R> Stream<R> yield(Function3<? super T1, ? super T2, ? super T3, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<T2> l2 = s2.collect(Collectors.toList());
            List<T3> l3 = s3.collect(Collectors.toList());

            return s1.flatMap(t1 ->
                l2.stream().flatMap(t2 ->
                    l3.stream().map(t3 -> f.apply(t1, t2, t3))));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 4 {@link Stream} values.
     * <p>
     * All streams are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all streams, transformed by the yield function.
     *
     * @param s1 the first stream
     * @param s2 the second stream
     * @param s3 the third stream
     * @param s4 the 4th stream
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @param <T3> the type of the third stream
     * @param <T4> the type of the 4th stream
     * @return a for-comprehension over 4 stream values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4> For4Stream<T1, T2, T3, T4> forc(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3, Stream<T4> s4) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        Objects.requireNonNull(s3, "s3 is null");
        Objects.requireNonNull(s4, "s4 is null");
        return new For4Stream<>(s1, s2, s3, s4);
    }

    /**
     * Represents a for-comprehension over 4 eagerly evaluated {@link Stream} values.
     *
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @param <T3> the type of the third stream
     * @param <T4> the type of the 4th stream
     */
    public static final class For4Stream<T1, T2, T3, T4> {

        private final Stream<T1> s1;
        private final Stream<T2> s2;
        private final Stream<T3> s3;
        private final Stream<T4> s4;

        private For4Stream(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3, Stream<T4> s4) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
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
        public <R> Stream<R> yield(Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<T2> l2 = s2.collect(Collectors.toList());
            List<T3> l3 = s3.collect(Collectors.toList());
            List<T4> l4 = s4.collect(Collectors.toList());

            return s1.flatMap(t1 ->
                l2.stream().flatMap(t2 ->
                    l3.stream().flatMap(t3 ->
                        l4.stream().map(t4 -> f.apply(t1, t2, t3, t4)))));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 5 {@link Stream} values.
     * <p>
     * All streams are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all streams, transformed by the yield function.
     *
     * @param s1 the first stream
     * @param s2 the second stream
     * @param s3 the third stream
     * @param s4 the 4th stream
     * @param s5 the 5th stream
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @param <T3> the type of the third stream
     * @param <T4> the type of the 4th stream
     * @param <T5> the type of the 5th stream
     * @return a for-comprehension over 5 stream values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4, T5> For5Stream<T1, T2, T3, T4, T5> forc(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3, Stream<T4> s4, Stream<T5> s5) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        Objects.requireNonNull(s3, "s3 is null");
        Objects.requireNonNull(s4, "s4 is null");
        Objects.requireNonNull(s5, "s5 is null");
        return new For5Stream<>(s1, s2, s3, s4, s5);
    }

    /**
     * Represents a for-comprehension over 5 eagerly evaluated {@link Stream} values.
     *
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @param <T3> the type of the third stream
     * @param <T4> the type of the 4th stream
     * @param <T5> the type of the 5th stream
     */
    public static final class For5Stream<T1, T2, T3, T4, T5> {

        private final Stream<T1> s1;
        private final Stream<T2> s2;
        private final Stream<T3> s3;
        private final Stream<T4> s4;
        private final Stream<T5> s5;

        private For5Stream(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3, Stream<T4> s4, Stream<T5> s5) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
            this.s5 = s5;
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
        public <R> Stream<R> yield(Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<T2> l2 = s2.collect(Collectors.toList());
            List<T3> l3 = s3.collect(Collectors.toList());
            List<T4> l4 = s4.collect(Collectors.toList());
            List<T5> l5 = s5.collect(Collectors.toList());

            return s1.flatMap(t1 ->
                l2.stream().flatMap(t2 ->
                    l3.stream().flatMap(t3 ->
                        l4.stream().flatMap(t4 ->
                            l5.stream().map(t5 -> f.apply(t1, t2, t3, t4, t5))))));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 6 {@link Stream} values.
     * <p>
     * All streams are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all streams, transformed by the yield function.
     *
     * @param s1 the first stream
     * @param s2 the second stream
     * @param s3 the third stream
     * @param s4 the 4th stream
     * @param s5 the 5th stream
     * @param s6 the 6th stream
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @param <T3> the type of the third stream
     * @param <T4> the type of the 4th stream
     * @param <T5> the type of the 5th stream
     * @param <T6> the type of the 6th stream
     * @return a for-comprehension over 6 stream values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4, T5, T6> For6Stream<T1, T2, T3, T4, T5, T6> forc(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3, Stream<T4> s4, Stream<T5> s5, Stream<T6> s6) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        Objects.requireNonNull(s3, "s3 is null");
        Objects.requireNonNull(s4, "s4 is null");
        Objects.requireNonNull(s5, "s5 is null");
        Objects.requireNonNull(s6, "s6 is null");
        return new For6Stream<>(s1, s2, s3, s4, s5, s6);
    }

    /**
     * Represents a for-comprehension over 6 eagerly evaluated {@link Stream} values.
     *
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @param <T3> the type of the third stream
     * @param <T4> the type of the 4th stream
     * @param <T5> the type of the 5th stream
     * @param <T6> the type of the 6th stream
     */
    public static final class For6Stream<T1, T2, T3, T4, T5, T6> {

        private final Stream<T1> s1;
        private final Stream<T2> s2;
        private final Stream<T3> s3;
        private final Stream<T4> s4;
        private final Stream<T5> s5;
        private final Stream<T6> s6;

        private For6Stream(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3, Stream<T4> s4, Stream<T5> s5, Stream<T6> s6) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
            this.s5 = s5;
            this.s6 = s6;
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
        public <R> Stream<R> yield(Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<T2> l2 = s2.collect(Collectors.toList());
            List<T3> l3 = s3.collect(Collectors.toList());
            List<T4> l4 = s4.collect(Collectors.toList());
            List<T5> l5 = s5.collect(Collectors.toList());
            List<T6> l6 = s6.collect(Collectors.toList());

            return s1.flatMap(t1 ->
                l2.stream().flatMap(t2 ->
                    l3.stream().flatMap(t3 ->
                        l4.stream().flatMap(t4 ->
                            l5.stream().flatMap(t5 ->
                                l6.stream().map(t6 -> f.apply(t1, t2, t3, t4, t5, t6)))))));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 7 {@link Stream} values.
     * <p>
     * All streams are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all streams, transformed by the yield function.
     *
     * @param s1 the first stream
     * @param s2 the second stream
     * @param s3 the third stream
     * @param s4 the 4th stream
     * @param s5 the 5th stream
     * @param s6 the 6th stream
     * @param s7 the 7th stream
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @param <T3> the type of the third stream
     * @param <T4> the type of the 4th stream
     * @param <T5> the type of the 5th stream
     * @param <T6> the type of the 6th stream
     * @param <T7> the type of the 7th stream
     * @return a for-comprehension over 7 stream values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4, T5, T6, T7> For7Stream<T1, T2, T3, T4, T5, T6, T7> forc(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3, Stream<T4> s4, Stream<T5> s5, Stream<T6> s6, Stream<T7> s7) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        Objects.requireNonNull(s3, "s3 is null");
        Objects.requireNonNull(s4, "s4 is null");
        Objects.requireNonNull(s5, "s5 is null");
        Objects.requireNonNull(s6, "s6 is null");
        Objects.requireNonNull(s7, "s7 is null");
        return new For7Stream<>(s1, s2, s3, s4, s5, s6, s7);
    }

    /**
     * Represents a for-comprehension over 7 eagerly evaluated {@link Stream} values.
     *
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @param <T3> the type of the third stream
     * @param <T4> the type of the 4th stream
     * @param <T5> the type of the 5th stream
     * @param <T6> the type of the 6th stream
     * @param <T7> the type of the 7th stream
     */
    public static final class For7Stream<T1, T2, T3, T4, T5, T6, T7> {

        private final Stream<T1> s1;
        private final Stream<T2> s2;
        private final Stream<T3> s3;
        private final Stream<T4> s4;
        private final Stream<T5> s5;
        private final Stream<T6> s6;
        private final Stream<T7> s7;

        private For7Stream(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3, Stream<T4> s4, Stream<T5> s5, Stream<T6> s6, Stream<T7> s7) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
            this.s5 = s5;
            this.s6 = s6;
            this.s7 = s7;
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
        public <R> Stream<R> yield(Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<T2> l2 = s2.collect(Collectors.toList());
            List<T3> l3 = s3.collect(Collectors.toList());
            List<T4> l4 = s4.collect(Collectors.toList());
            List<T5> l5 = s5.collect(Collectors.toList());
            List<T6> l6 = s6.collect(Collectors.toList());
            List<T7> l7 = s7.collect(Collectors.toList());

            return s1.flatMap(t1 ->
                l2.stream().flatMap(t2 ->
                    l3.stream().flatMap(t3 ->
                        l4.stream().flatMap(t4 ->
                            l5.stream().flatMap(t5 ->
                                l6.stream().flatMap(t6 ->
                                    l7.stream().map(t7 -> f.apply(t1, t2, t3, t4, t5, t6, t7))))))));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 8 {@link Stream} values.
     * <p>
     * All streams are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all streams, transformed by the yield function.
     *
     * @param s1 the first stream
     * @param s2 the second stream
     * @param s3 the third stream
     * @param s4 the 4th stream
     * @param s5 the 5th stream
     * @param s6 the 6th stream
     * @param s7 the 7th stream
     * @param s8 the 8th stream
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @param <T3> the type of the third stream
     * @param <T4> the type of the 4th stream
     * @param <T5> the type of the 5th stream
     * @param <T6> the type of the 6th stream
     * @param <T7> the type of the 7th stream
     * @param <T8> the type of the 8th stream
     * @return a for-comprehension over 8 stream values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> For8Stream<T1, T2, T3, T4, T5, T6, T7, T8> forc(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3, Stream<T4> s4, Stream<T5> s5, Stream<T6> s6, Stream<T7> s7, Stream<T8> s8) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        Objects.requireNonNull(s3, "s3 is null");
        Objects.requireNonNull(s4, "s4 is null");
        Objects.requireNonNull(s5, "s5 is null");
        Objects.requireNonNull(s6, "s6 is null");
        Objects.requireNonNull(s7, "s7 is null");
        Objects.requireNonNull(s8, "s8 is null");
        return new For8Stream<>(s1, s2, s3, s4, s5, s6, s7, s8);
    }

    /**
     * Represents a for-comprehension over 8 eagerly evaluated {@link Stream} values.
     *
     * @param <T1> the type of the first stream
     * @param <T2> the type of the second stream
     * @param <T3> the type of the third stream
     * @param <T4> the type of the 4th stream
     * @param <T5> the type of the 5th stream
     * @param <T6> the type of the 6th stream
     * @param <T7> the type of the 7th stream
     * @param <T8> the type of the 8th stream
     */
    public static final class For8Stream<T1, T2, T3, T4, T5, T6, T7, T8> {

        private final Stream<T1> s1;
        private final Stream<T2> s2;
        private final Stream<T3> s3;
        private final Stream<T4> s4;
        private final Stream<T5> s5;
        private final Stream<T6> s6;
        private final Stream<T7> s7;
        private final Stream<T8> s8;

        private For8Stream(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3, Stream<T4> s4, Stream<T5> s5, Stream<T6> s6, Stream<T7> s7, Stream<T8> s8) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
            this.s5 = s5;
            this.s6 = s6;
            this.s7 = s7;
            this.s8 = s8;
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
        public <R> Stream<R> yield(Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<T2> l2 = s2.collect(Collectors.toList());
            List<T3> l3 = s3.collect(Collectors.toList());
            List<T4> l4 = s4.collect(Collectors.toList());
            List<T5> l5 = s5.collect(Collectors.toList());
            List<T6> l6 = s6.collect(Collectors.toList());
            List<T7> l7 = s7.collect(Collectors.toList());
            List<T8> l8 = s8.collect(Collectors.toList());

            return s1.flatMap(t1 ->
                l2.stream().flatMap(t2 ->
                    l3.stream().flatMap(t3 ->
                        l4.stream().flatMap(t4 ->
                            l5.stream().flatMap(t5 ->
                                l6.stream().flatMap(t6 ->
                                    l7.stream().flatMap(t7 ->
                                        l8.stream().map(t8 -> f.apply(t1, t2, t3, t4, t5, t6, t7, t8)))))))));
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 2 {@link Iterable} values.
     * <p>
     * All iterables are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all iterables, transformed by the yield function.
     *
     * @param i1 the first iterable
     * @param i2 the second iterable
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @return a for-comprehension over 2 iterable values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2> For2Iterable<T1, T2> forc(Iterable<T1> i1, Iterable<T2> i2) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        return new For2Iterable<>(i1, i2);
    }

    /**
     * Represents a for-comprehension over 2 eagerly evaluated {@link Iterable} values.
     *
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     */
    public static final class For2Iterable<T1, T2> {

        private final Iterable<T1> i1;
        private final Iterable<T2> i2;

        private For2Iterable(Iterable<T1> i1, Iterable<T2> i2) {
            this.i1 = i1;
            this.i2 = i2;
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
        public <R> List<R> yield(Function2<? super T1, ? super T2, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2) {
                    result.add(f.apply(t1, t2));
                }
            }
            return result;
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 3 {@link Iterable} values.
     * <p>
     * All iterables are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all iterables, transformed by the yield function.
     *
     * @param i1 the first iterable
     * @param i2 the second iterable
     * @param i3 the third iterable
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @param <T3> the type of the third iterable
     * @return a for-comprehension over 3 iterable values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3> For3Iterable<T1, T2, T3> forc(Iterable<T1> i1, Iterable<T2> i2, Iterable<T3> i3) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        Objects.requireNonNull(i3, "i3 is null");
        return new For3Iterable<>(i1, i2, i3);
    }

    /**
     * Represents a for-comprehension over 3 eagerly evaluated {@link Iterable} values.
     *
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @param <T3> the type of the third iterable
     */
    public static final class For3Iterable<T1, T2, T3> {

        private final Iterable<T1> i1;
        private final Iterable<T2> i2;
        private final Iterable<T3> i3;

        private For3Iterable(Iterable<T1> i1, Iterable<T2> i2, Iterable<T3> i3) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
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
        public <R> List<R> yield(Function3<? super T1, ? super T2, ? super T3, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2) {
                    for (T3 t3 : i3) {
                        result.add(f.apply(t1, t2, t3));
                    }
                }
            }
            return result;
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 4 {@link Iterable} values.
     * <p>
     * All iterables are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all iterables, transformed by the yield function.
     *
     * @param i1 the first iterable
     * @param i2 the second iterable
     * @param i3 the third iterable
     * @param i4 the 4th iterable
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @param <T3> the type of the third iterable
     * @param <T4> the type of the 4th iterable
     * @return a for-comprehension over 4 iterable values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4> For4Iterable<T1, T2, T3, T4> forc(Iterable<T1> i1, Iterable<T2> i2, Iterable<T3> i3, Iterable<T4> i4) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        Objects.requireNonNull(i3, "i3 is null");
        Objects.requireNonNull(i4, "i4 is null");
        return new For4Iterable<>(i1, i2, i3, i4);
    }

    /**
     * Represents a for-comprehension over 4 eagerly evaluated {@link Iterable} values.
     *
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @param <T3> the type of the third iterable
     * @param <T4> the type of the 4th iterable
     */
    public static final class For4Iterable<T1, T2, T3, T4> {

        private final Iterable<T1> i1;
        private final Iterable<T2> i2;
        private final Iterable<T3> i3;
        private final Iterable<T4> i4;

        private For4Iterable(Iterable<T1> i1, Iterable<T2> i2, Iterable<T3> i3, Iterable<T4> i4) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
            this.i4 = i4;
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
        public <R> List<R> yield(Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2) {
                    for (T3 t3 : i3) {
                        for (T4 t4 : i4) {
                            result.add(f.apply(t1, t2, t3, t4));
                        }
                    }
                }
            }
            return result;
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 5 {@link Iterable} values.
     * <p>
     * All iterables are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all iterables, transformed by the yield function.
     *
     * @param i1 the first iterable
     * @param i2 the second iterable
     * @param i3 the third iterable
     * @param i4 the 4th iterable
     * @param i5 the 5th iterable
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @param <T3> the type of the third iterable
     * @param <T4> the type of the 4th iterable
     * @param <T5> the type of the 5th iterable
     * @return a for-comprehension over 5 iterable values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4, T5> For5Iterable<T1, T2, T3, T4, T5> forc(Iterable<T1> i1, Iterable<T2> i2, Iterable<T3> i3, Iterable<T4> i4, Iterable<T5> i5) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        Objects.requireNonNull(i3, "i3 is null");
        Objects.requireNonNull(i4, "i4 is null");
        Objects.requireNonNull(i5, "i5 is null");
        return new For5Iterable<>(i1, i2, i3, i4, i5);
    }

    /**
     * Represents a for-comprehension over 5 eagerly evaluated {@link Iterable} values.
     *
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @param <T3> the type of the third iterable
     * @param <T4> the type of the 4th iterable
     * @param <T5> the type of the 5th iterable
     */
    public static final class For5Iterable<T1, T2, T3, T4, T5> {

        private final Iterable<T1> i1;
        private final Iterable<T2> i2;
        private final Iterable<T3> i3;
        private final Iterable<T4> i4;
        private final Iterable<T5> i5;

        private For5Iterable(Iterable<T1> i1, Iterable<T2> i2, Iterable<T3> i3, Iterable<T4> i4, Iterable<T5> i5) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
            this.i4 = i4;
            this.i5 = i5;
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
        public <R> List<R> yield(Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2) {
                    for (T3 t3 : i3) {
                        for (T4 t4 : i4) {
                            for (T5 t5 : i5) {
                                result.add(f.apply(t1, t2, t3, t4, t5));
                            }
                        }
                    }
                }
            }
            return result;
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 6 {@link Iterable} values.
     * <p>
     * All iterables are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all iterables, transformed by the yield function.
     *
     * @param i1 the first iterable
     * @param i2 the second iterable
     * @param i3 the third iterable
     * @param i4 the 4th iterable
     * @param i5 the 5th iterable
     * @param i6 the 6th iterable
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @param <T3> the type of the third iterable
     * @param <T4> the type of the 4th iterable
     * @param <T5> the type of the 5th iterable
     * @param <T6> the type of the 6th iterable
     * @return a for-comprehension over 6 iterable values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4, T5, T6> For6Iterable<T1, T2, T3, T4, T5, T6> forc(Iterable<T1> i1, Iterable<T2> i2, Iterable<T3> i3, Iterable<T4> i4, Iterable<T5> i5, Iterable<T6> i6) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        Objects.requireNonNull(i3, "i3 is null");
        Objects.requireNonNull(i4, "i4 is null");
        Objects.requireNonNull(i5, "i5 is null");
        Objects.requireNonNull(i6, "i6 is null");
        return new For6Iterable<>(i1, i2, i3, i4, i5, i6);
    }

    /**
     * Represents a for-comprehension over 6 eagerly evaluated {@link Iterable} values.
     *
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @param <T3> the type of the third iterable
     * @param <T4> the type of the 4th iterable
     * @param <T5> the type of the 5th iterable
     * @param <T6> the type of the 6th iterable
     */
    public static final class For6Iterable<T1, T2, T3, T4, T5, T6> {

        private final Iterable<T1> i1;
        private final Iterable<T2> i2;
        private final Iterable<T3> i3;
        private final Iterable<T4> i4;
        private final Iterable<T5> i5;
        private final Iterable<T6> i6;

        private For6Iterable(Iterable<T1> i1, Iterable<T2> i2, Iterable<T3> i3, Iterable<T4> i4, Iterable<T5> i5, Iterable<T6> i6) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
            this.i4 = i4;
            this.i5 = i5;
            this.i6 = i6;
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
        public <R> List<R> yield(Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2) {
                    for (T3 t3 : i3) {
                        for (T4 t4 : i4) {
                            for (T5 t5 : i5) {
                                for (T6 t6 : i6) {
                                    result.add(f.apply(t1, t2, t3, t4, t5, t6));
                                }
                            }
                        }
                    }
                }
            }
            return result;
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 7 {@link Iterable} values.
     * <p>
     * All iterables are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all iterables, transformed by the yield function.
     *
     * @param i1 the first iterable
     * @param i2 the second iterable
     * @param i3 the third iterable
     * @param i4 the 4th iterable
     * @param i5 the 5th iterable
     * @param i6 the 6th iterable
     * @param i7 the 7th iterable
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @param <T3> the type of the third iterable
     * @param <T4> the type of the 4th iterable
     * @param <T5> the type of the 5th iterable
     * @param <T6> the type of the 6th iterable
     * @param <T7> the type of the 7th iterable
     * @return a for-comprehension over 7 iterable values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4, T5, T6, T7> For7Iterable<T1, T2, T3, T4, T5, T6, T7> forc(Iterable<T1> i1, Iterable<T2> i2, Iterable<T3> i3, Iterable<T4> i4, Iterable<T5> i5, Iterable<T6> i6, Iterable<T7> i7) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        Objects.requireNonNull(i3, "i3 is null");
        Objects.requireNonNull(i4, "i4 is null");
        Objects.requireNonNull(i5, "i5 is null");
        Objects.requireNonNull(i6, "i6 is null");
        Objects.requireNonNull(i7, "i7 is null");
        return new For7Iterable<>(i1, i2, i3, i4, i5, i6, i7);
    }

    /**
     * Represents a for-comprehension over 7 eagerly evaluated {@link Iterable} values.
     *
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @param <T3> the type of the third iterable
     * @param <T4> the type of the 4th iterable
     * @param <T5> the type of the 5th iterable
     * @param <T6> the type of the 6th iterable
     * @param <T7> the type of the 7th iterable
     */
    public static final class For7Iterable<T1, T2, T3, T4, T5, T6, T7> {

        private final Iterable<T1> i1;
        private final Iterable<T2> i2;
        private final Iterable<T3> i3;
        private final Iterable<T4> i4;
        private final Iterable<T5> i5;
        private final Iterable<T6> i6;
        private final Iterable<T7> i7;

        private For7Iterable(Iterable<T1> i1, Iterable<T2> i2, Iterable<T3> i3, Iterable<T4> i4, Iterable<T5> i5, Iterable<T6> i6, Iterable<T7> i7) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
            this.i4 = i4;
            this.i5 = i5;
            this.i6 = i6;
            this.i7 = i7;
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
        public <R> List<R> yield(Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2) {
                    for (T3 t3 : i3) {
                        for (T4 t4 : i4) {
                            for (T5 t5 : i5) {
                                for (T6 t6 : i6) {
                                    for (T7 t7 : i7) {
                                        result.add(f.apply(t1, t2, t3, t4, t5, t6, t7));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return result;
        }
    }

    /**
     * Creates a strict (eager) for-comprehension over 8 {@link Iterable} values.
     * <p>
     * All iterables are evaluated eagerly. The resulting comprehension yields
     * the cartesian product of all iterables, transformed by the yield function.
     *
     * @param i1 the first iterable
     * @param i2 the second iterable
     * @param i3 the third iterable
     * @param i4 the 4th iterable
     * @param i5 the 5th iterable
     * @param i6 the 6th iterable
     * @param i7 the 7th iterable
     * @param i8 the 8th iterable
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @param <T3> the type of the third iterable
     * @param <T4> the type of the 4th iterable
     * @param <T5> the type of the 5th iterable
     * @param <T6> the type of the 6th iterable
     * @param <T7> the type of the 7th iterable
     * @param <T8> the type of the 8th iterable
     * @return a for-comprehension over 8 iterable values
     * @throws NullPointerException if any argument is {@code null}
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> For8Iterable<T1, T2, T3, T4, T5, T6, T7, T8> forc(Iterable<T1> i1, Iterable<T2> i2, Iterable<T3> i3, Iterable<T4> i4, Iterable<T5> i5, Iterable<T6> i6, Iterable<T7> i7, Iterable<T8> i8) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        Objects.requireNonNull(i3, "i3 is null");
        Objects.requireNonNull(i4, "i4 is null");
        Objects.requireNonNull(i5, "i5 is null");
        Objects.requireNonNull(i6, "i6 is null");
        Objects.requireNonNull(i7, "i7 is null");
        Objects.requireNonNull(i8, "i8 is null");
        return new For8Iterable<>(i1, i2, i3, i4, i5, i6, i7, i8);
    }

    /**
     * Represents a for-comprehension over 8 eagerly evaluated {@link Iterable} values.
     *
     * @param <T1> the type of the first iterable
     * @param <T2> the type of the second iterable
     * @param <T3> the type of the third iterable
     * @param <T4> the type of the 4th iterable
     * @param <T5> the type of the 5th iterable
     * @param <T6> the type of the 6th iterable
     * @param <T7> the type of the 7th iterable
     * @param <T8> the type of the 8th iterable
     */
    public static final class For8Iterable<T1, T2, T3, T4, T5, T6, T7, T8> {

        private final Iterable<T1> i1;
        private final Iterable<T2> i2;
        private final Iterable<T3> i3;
        private final Iterable<T4> i4;
        private final Iterable<T5> i5;
        private final Iterable<T6> i6;
        private final Iterable<T7> i7;
        private final Iterable<T8> i8;

        private For8Iterable(Iterable<T1> i1, Iterable<T2> i2, Iterable<T3> i3, Iterable<T4> i4, Iterable<T5> i5, Iterable<T6> i6, Iterable<T7> i7, Iterable<T8> i8) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
            this.i4 = i4;
            this.i5 = i5;
            this.i6 = i6;
            this.i7 = i7;
            this.i8 = i8;
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
        public <R> List<R> yield(Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2) {
                    for (T3 t3 : i3) {
                        for (T4 t4 : i4) {
                            for (T5 t5 : i5) {
                                for (T6 t6 : i6) {
                                    for (T7 t7 : i7) {
                                        for (T8 t8 : i8) {
                                            result.add(f.apply(t1, t2, t3, t4, t5, t6, t7, t8));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return result;
        }
    }

    public static <T1, T2> ForLazy2Optional<T1, T2> forc(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        return new ForLazy2Optional<>(o1, o2);
    }

    public static final class ForLazy2Optional<T1, T2> {

        private final Optional<T1> o1;
        private final Function1<? super T1, Optional<T2>> o2;

        private ForLazy2Optional(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2) {
            this.o1 = o1;
            this.o2 = o2;
        }

        public <R> Optional<R> yield(Function2<? super T1, ? super T2, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.apply(t1).map(t2 -> f.apply(t1, t2)));
        }
    }

    public static <T1, T2, T3> ForLazy3Optional<T1, T2, T3> forc(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2, Function2<? super T1, ? super T2, Optional<T3>> o3) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        Objects.requireNonNull(o3, "o3 is null");
        return new ForLazy3Optional<>(o1, o2, o3);
    }

    public static final class ForLazy3Optional<T1, T2, T3> {

        private final Optional<T1> o1;
        private final Function1<? super T1, Optional<T2>> o2;
        private final Function2<? super T1, ? super T2, Optional<T3>> o3;

        private ForLazy3Optional(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2, Function2<? super T1, ? super T2, Optional<T3>> o3) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
        }

        public <R> Optional<R> yield(Function3<? super T1, ? super T2, ? super T3, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.apply(t1).flatMap(t2 ->
                    o3.apply(t1, t2).map(t3 -> f.apply(t1, t2, t3))));
        }
    }

    public static <T1, T2, T3, T4> ForLazy4Optional<T1, T2, T3, T4> forc(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2, Function2<? super T1, ? super T2, Optional<T3>> o3, Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        Objects.requireNonNull(o3, "o3 is null");
        Objects.requireNonNull(o4, "o4 is null");
        return new ForLazy4Optional<>(o1, o2, o3, o4);
    }

    public static final class ForLazy4Optional<T1, T2, T3, T4> {

        private final Optional<T1> o1;
        private final Function1<? super T1, Optional<T2>> o2;
        private final Function2<? super T1, ? super T2, Optional<T3>> o3;
        private final Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4;

        private ForLazy4Optional(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2, Function2<? super T1, ? super T2, Optional<T3>> o3, Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
            this.o4 = o4;
        }

        public <R> Optional<R> yield(Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.apply(t1).flatMap(t2 ->
                    o3.apply(t1, t2).flatMap(t3 ->
                        o4.apply(t1, t2, t3).map(t4 -> f.apply(t1, t2, t3, t4)))));
        }
    }

    public static <T1, T2, T3, T4, T5> ForLazy5Optional<T1, T2, T3, T4, T5> forc(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2, Function2<? super T1, ? super T2, Optional<T3>> o3, Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Optional<T5>> o5) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        Objects.requireNonNull(o3, "o3 is null");
        Objects.requireNonNull(o4, "o4 is null");
        Objects.requireNonNull(o5, "o5 is null");
        return new ForLazy5Optional<>(o1, o2, o3, o4, o5);
    }

    public static final class ForLazy5Optional<T1, T2, T3, T4, T5> {

        private final Optional<T1> o1;
        private final Function1<? super T1, Optional<T2>> o2;
        private final Function2<? super T1, ? super T2, Optional<T3>> o3;
        private final Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4;
        private final Function4<? super T1, ? super T2, ? super T3, ? super T4, Optional<T5>> o5;

        private ForLazy5Optional(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2, Function2<? super T1, ? super T2, Optional<T3>> o3, Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Optional<T5>> o5) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
            this.o4 = o4;
            this.o5 = o5;
        }

        public <R> Optional<R> yield(Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.apply(t1).flatMap(t2 ->
                    o3.apply(t1, t2).flatMap(t3 ->
                        o4.apply(t1, t2, t3).flatMap(t4 ->
                            o5.apply(t1, t2, t3, t4).map(t5 -> f.apply(t1, t2, t3, t4, t5))))));
        }
    }

    public static <T1, T2, T3, T4, T5, T6> ForLazy6Optional<T1, T2, T3, T4, T5, T6> forc(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2, Function2<? super T1, ? super T2, Optional<T3>> o3, Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Optional<T5>> o5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Optional<T6>> o6) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        Objects.requireNonNull(o3, "o3 is null");
        Objects.requireNonNull(o4, "o4 is null");
        Objects.requireNonNull(o5, "o5 is null");
        Objects.requireNonNull(o6, "o6 is null");
        return new ForLazy6Optional<>(o1, o2, o3, o4, o5, o6);
    }

    public static final class ForLazy6Optional<T1, T2, T3, T4, T5, T6> {

        private final Optional<T1> o1;
        private final Function1<? super T1, Optional<T2>> o2;
        private final Function2<? super T1, ? super T2, Optional<T3>> o3;
        private final Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4;
        private final Function4<? super T1, ? super T2, ? super T3, ? super T4, Optional<T5>> o5;
        private final Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Optional<T6>> o6;

        private ForLazy6Optional(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2, Function2<? super T1, ? super T2, Optional<T3>> o3, Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Optional<T5>> o5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Optional<T6>> o6) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
            this.o4 = o4;
            this.o5 = o5;
            this.o6 = o6;
        }

        public <R> Optional<R> yield(Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.apply(t1).flatMap(t2 ->
                    o3.apply(t1, t2).flatMap(t3 ->
                        o4.apply(t1, t2, t3).flatMap(t4 ->
                            o5.apply(t1, t2, t3, t4).flatMap(t5 ->
                                o6.apply(t1, t2, t3, t4, t5).map(t6 -> f.apply(t1, t2, t3, t4, t5, t6)))))));
        }
    }

    public static <T1, T2, T3, T4, T5, T6, T7> ForLazy7Optional<T1, T2, T3, T4, T5, T6, T7> forc(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2, Function2<? super T1, ? super T2, Optional<T3>> o3, Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Optional<T5>> o5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Optional<T6>> o6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Optional<T7>> o7) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        Objects.requireNonNull(o3, "o3 is null");
        Objects.requireNonNull(o4, "o4 is null");
        Objects.requireNonNull(o5, "o5 is null");
        Objects.requireNonNull(o6, "o6 is null");
        Objects.requireNonNull(o7, "o7 is null");
        return new ForLazy7Optional<>(o1, o2, o3, o4, o5, o6, o7);
    }

    public static final class ForLazy7Optional<T1, T2, T3, T4, T5, T6, T7> {

        private final Optional<T1> o1;
        private final Function1<? super T1, Optional<T2>> o2;
        private final Function2<? super T1, ? super T2, Optional<T3>> o3;
        private final Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4;
        private final Function4<? super T1, ? super T2, ? super T3, ? super T4, Optional<T5>> o5;
        private final Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Optional<T6>> o6;
        private final Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Optional<T7>> o7;

        private ForLazy7Optional(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2, Function2<? super T1, ? super T2, Optional<T3>> o3, Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Optional<T5>> o5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Optional<T6>> o6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Optional<T7>> o7) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
            this.o4 = o4;
            this.o5 = o5;
            this.o6 = o6;
            this.o7 = o7;
        }

        public <R> Optional<R> yield(Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.apply(t1).flatMap(t2 ->
                    o3.apply(t1, t2).flatMap(t3 ->
                        o4.apply(t1, t2, t3).flatMap(t4 ->
                            o5.apply(t1, t2, t3, t4).flatMap(t5 ->
                                o6.apply(t1, t2, t3, t4, t5).flatMap(t6 ->
                                    o7.apply(t1, t2, t3, t4, t5, t6).map(t7 -> f.apply(t1, t2, t3, t4, t5, t6, t7))))))));
        }
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> ForLazy8Optional<T1, T2, T3, T4, T5, T6, T7, T8> forc(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2, Function2<? super T1, ? super T2, Optional<T3>> o3, Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Optional<T5>> o5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Optional<T6>> o6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Optional<T7>> o7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, Optional<T8>> o8) {
        Objects.requireNonNull(o1, "o1 is null");
        Objects.requireNonNull(o2, "o2 is null");
        Objects.requireNonNull(o3, "o3 is null");
        Objects.requireNonNull(o4, "o4 is null");
        Objects.requireNonNull(o5, "o5 is null");
        Objects.requireNonNull(o6, "o6 is null");
        Objects.requireNonNull(o7, "o7 is null");
        Objects.requireNonNull(o8, "o8 is null");
        return new ForLazy8Optional<>(o1, o2, o3, o4, o5, o6, o7, o8);
    }

    public static final class ForLazy8Optional<T1, T2, T3, T4, T5, T6, T7, T8> {

        private final Optional<T1> o1;
        private final Function1<? super T1, Optional<T2>> o2;
        private final Function2<? super T1, ? super T2, Optional<T3>> o3;
        private final Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4;
        private final Function4<? super T1, ? super T2, ? super T3, ? super T4, Optional<T5>> o5;
        private final Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Optional<T6>> o6;
        private final Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Optional<T7>> o7;
        private final Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, Optional<T8>> o8;

        private ForLazy8Optional(Optional<T1> o1, Function1<? super T1, Optional<T2>> o2, Function2<? super T1, ? super T2, Optional<T3>> o3, Function3<? super T1, ? super T2, ? super T3, Optional<T4>> o4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Optional<T5>> o5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Optional<T6>> o6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Optional<T7>> o7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, Optional<T8>> o8) {
            this.o1 = o1;
            this.o2 = o2;
            this.o3 = o3;
            this.o4 = o4;
            this.o5 = o5;
            this.o6 = o6;
            this.o7 = o7;
            this.o8 = o8;
        }

        public <R> Optional<R> yield(Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return o1.flatMap(t1 ->
                o2.apply(t1).flatMap(t2 ->
                    o3.apply(t1, t2).flatMap(t3 ->
                        o4.apply(t1, t2, t3).flatMap(t4 ->
                            o5.apply(t1, t2, t3, t4).flatMap(t5 ->
                                o6.apply(t1, t2, t3, t4, t5).flatMap(t6 ->
                                    o7.apply(t1, t2, t3, t4, t5, t6).flatMap(t7 ->
                                        o8.apply(t1, t2, t3, t4, t5, t6, t7).map(t8 -> f.apply(t1, t2, t3, t4, t5, t6, t7, t8)))))))));
        }
    }

    public static <T1, T2> ForLazy2Stream<T1, T2> forc(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        return new ForLazy2Stream<>(s1, s2);
    }

    public static final class ForLazy2Stream<T1, T2> {

        private final Stream<T1> s1;
        private final Function1<? super T1, Stream<T2>> s2;

        private ForLazy2Stream(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2) {
            this.s1 = s1;
            this.s2 = s2;
        }

        public <R> Stream<R> yield(Function2<? super T1, ? super T2, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return s1.flatMap(t1 ->
                s2.apply(t1).map(t2 -> f.apply(t1, t2)));
        }
    }

    public static <T1, T2, T3> ForLazy3Stream<T1, T2, T3> forc(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2, Function2<? super T1, ? super T2, Stream<T3>> s3) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        Objects.requireNonNull(s3, "s3 is null");
        return new ForLazy3Stream<>(s1, s2, s3);
    }

    public static final class ForLazy3Stream<T1, T2, T3> {

        private final Stream<T1> s1;
        private final Function1<? super T1, Stream<T2>> s2;
        private final Function2<? super T1, ? super T2, Stream<T3>> s3;

        private ForLazy3Stream(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2, Function2<? super T1, ? super T2, Stream<T3>> s3) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
        }

        public <R> Stream<R> yield(Function3<? super T1, ? super T2, ? super T3, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return s1.flatMap(t1 ->
                s2.apply(t1).flatMap(t2 ->
                    s3.apply(t1, t2).map(t3 -> f.apply(t1, t2, t3))));
        }
    }

    public static <T1, T2, T3, T4> ForLazy4Stream<T1, T2, T3, T4> forc(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2, Function2<? super T1, ? super T2, Stream<T3>> s3, Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        Objects.requireNonNull(s3, "s3 is null");
        Objects.requireNonNull(s4, "s4 is null");
        return new ForLazy4Stream<>(s1, s2, s3, s4);
    }

    public static final class ForLazy4Stream<T1, T2, T3, T4> {

        private final Stream<T1> s1;
        private final Function1<? super T1, Stream<T2>> s2;
        private final Function2<? super T1, ? super T2, Stream<T3>> s3;
        private final Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4;

        private ForLazy4Stream(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2, Function2<? super T1, ? super T2, Stream<T3>> s3, Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
        }

        public <R> Stream<R> yield(Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return s1.flatMap(t1 ->
                s2.apply(t1).flatMap(t2 ->
                    s3.apply(t1, t2).flatMap(t3 ->
                        s4.apply(t1, t2, t3).map(t4 -> f.apply(t1, t2, t3, t4)))));
        }
    }

    public static <T1, T2, T3, T4, T5> ForLazy5Stream<T1, T2, T3, T4, T5> forc(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2, Function2<? super T1, ? super T2, Stream<T3>> s3, Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Stream<T5>> s5) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        Objects.requireNonNull(s3, "s3 is null");
        Objects.requireNonNull(s4, "s4 is null");
        Objects.requireNonNull(s5, "s5 is null");
        return new ForLazy5Stream<>(s1, s2, s3, s4, s5);
    }

    public static final class ForLazy5Stream<T1, T2, T3, T4, T5> {

        private final Stream<T1> s1;
        private final Function1<? super T1, Stream<T2>> s2;
        private final Function2<? super T1, ? super T2, Stream<T3>> s3;
        private final Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4;
        private final Function4<? super T1, ? super T2, ? super T3, ? super T4, Stream<T5>> s5;

        private ForLazy5Stream(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2, Function2<? super T1, ? super T2, Stream<T3>> s3, Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Stream<T5>> s5) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
            this.s5 = s5;
        }

        public <R> Stream<R> yield(Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return s1.flatMap(t1 ->
                s2.apply(t1).flatMap(t2 ->
                    s3.apply(t1, t2).flatMap(t3 ->
                        s4.apply(t1, t2, t3).flatMap(t4 ->
                            s5.apply(t1, t2, t3, t4).map(t5 -> f.apply(t1, t2, t3, t4, t5))))));
        }
    }

    public static <T1, T2, T3, T4, T5, T6> ForLazy6Stream<T1, T2, T3, T4, T5, T6> forc(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2, Function2<? super T1, ? super T2, Stream<T3>> s3, Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Stream<T5>> s5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Stream<T6>> s6) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        Objects.requireNonNull(s3, "s3 is null");
        Objects.requireNonNull(s4, "s4 is null");
        Objects.requireNonNull(s5, "s5 is null");
        Objects.requireNonNull(s6, "s6 is null");
        return new ForLazy6Stream<>(s1, s2, s3, s4, s5, s6);
    }

    public static final class ForLazy6Stream<T1, T2, T3, T4, T5, T6> {

        private final Stream<T1> s1;
        private final Function1<? super T1, Stream<T2>> s2;
        private final Function2<? super T1, ? super T2, Stream<T3>> s3;
        private final Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4;
        private final Function4<? super T1, ? super T2, ? super T3, ? super T4, Stream<T5>> s5;
        private final Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Stream<T6>> s6;

        private ForLazy6Stream(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2, Function2<? super T1, ? super T2, Stream<T3>> s3, Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Stream<T5>> s5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Stream<T6>> s6) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
            this.s5 = s5;
            this.s6 = s6;
        }

        public <R> Stream<R> yield(Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return s1.flatMap(t1 ->
                s2.apply(t1).flatMap(t2 ->
                    s3.apply(t1, t2).flatMap(t3 ->
                        s4.apply(t1, t2, t3).flatMap(t4 ->
                            s5.apply(t1, t2, t3, t4).flatMap(t5 ->
                                s6.apply(t1, t2, t3, t4, t5).map(t6 -> f.apply(t1, t2, t3, t4, t5, t6)))))));
        }
    }

    public static <T1, T2, T3, T4, T5, T6, T7> ForLazy7Stream<T1, T2, T3, T4, T5, T6, T7> forc(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2, Function2<? super T1, ? super T2, Stream<T3>> s3, Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Stream<T5>> s5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Stream<T6>> s6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Stream<T7>> s7) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        Objects.requireNonNull(s3, "s3 is null");
        Objects.requireNonNull(s4, "s4 is null");
        Objects.requireNonNull(s5, "s5 is null");
        Objects.requireNonNull(s6, "s6 is null");
        Objects.requireNonNull(s7, "s7 is null");
        return new ForLazy7Stream<>(s1, s2, s3, s4, s5, s6, s7);
    }

    public static final class ForLazy7Stream<T1, T2, T3, T4, T5, T6, T7> {

        private final Stream<T1> s1;
        private final Function1<? super T1, Stream<T2>> s2;
        private final Function2<? super T1, ? super T2, Stream<T3>> s3;
        private final Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4;
        private final Function4<? super T1, ? super T2, ? super T3, ? super T4, Stream<T5>> s5;
        private final Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Stream<T6>> s6;
        private final Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Stream<T7>> s7;

        private ForLazy7Stream(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2, Function2<? super T1, ? super T2, Stream<T3>> s3, Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Stream<T5>> s5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Stream<T6>> s6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Stream<T7>> s7) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
            this.s5 = s5;
            this.s6 = s6;
            this.s7 = s7;
        }

        public <R> Stream<R> yield(Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return s1.flatMap(t1 ->
                s2.apply(t1).flatMap(t2 ->
                    s3.apply(t1, t2).flatMap(t3 ->
                        s4.apply(t1, t2, t3).flatMap(t4 ->
                            s5.apply(t1, t2, t3, t4).flatMap(t5 ->
                                s6.apply(t1, t2, t3, t4, t5).flatMap(t6 ->
                                    s7.apply(t1, t2, t3, t4, t5, t6).map(t7 -> f.apply(t1, t2, t3, t4, t5, t6, t7))))))));
        }
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> ForLazy8Stream<T1, T2, T3, T4, T5, T6, T7, T8> forc(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2, Function2<? super T1, ? super T2, Stream<T3>> s3, Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Stream<T5>> s5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Stream<T6>> s6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Stream<T7>> s7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, Stream<T8>> s8) {
        Objects.requireNonNull(s1, "s1 is null");
        Objects.requireNonNull(s2, "s2 is null");
        Objects.requireNonNull(s3, "s3 is null");
        Objects.requireNonNull(s4, "s4 is null");
        Objects.requireNonNull(s5, "s5 is null");
        Objects.requireNonNull(s6, "s6 is null");
        Objects.requireNonNull(s7, "s7 is null");
        Objects.requireNonNull(s8, "s8 is null");
        return new ForLazy8Stream<>(s1, s2, s3, s4, s5, s6, s7, s8);
    }

    public static final class ForLazy8Stream<T1, T2, T3, T4, T5, T6, T7, T8> {

        private final Stream<T1> s1;
        private final Function1<? super T1, Stream<T2>> s2;
        private final Function2<? super T1, ? super T2, Stream<T3>> s3;
        private final Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4;
        private final Function4<? super T1, ? super T2, ? super T3, ? super T4, Stream<T5>> s5;
        private final Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Stream<T6>> s6;
        private final Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Stream<T7>> s7;
        private final Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, Stream<T8>> s8;

        private ForLazy8Stream(Stream<T1> s1, Function1<? super T1, Stream<T2>> s2, Function2<? super T1, ? super T2, Stream<T3>> s3, Function3<? super T1, ? super T2, ? super T3, Stream<T4>> s4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Stream<T5>> s5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Stream<T6>> s6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Stream<T7>> s7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, Stream<T8>> s8) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
            this.s5 = s5;
            this.s6 = s6;
            this.s7 = s7;
            this.s8 = s8;
        }

        public <R> Stream<R> yield(Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            return s1.flatMap(t1 ->
                s2.apply(t1).flatMap(t2 ->
                    s3.apply(t1, t2).flatMap(t3 ->
                        s4.apply(t1, t2, t3).flatMap(t4 ->
                            s5.apply(t1, t2, t3, t4).flatMap(t5 ->
                                s6.apply(t1, t2, t3, t4, t5).flatMap(t6 ->
                                    s7.apply(t1, t2, t3, t4, t5, t6).flatMap(t7 ->
                                        s8.apply(t1, t2, t3, t4, t5, t6, t7).map(t8 -> f.apply(t1, t2, t3, t4, t5, t6, t7, t8)))))))));
        }
    }

    public static <T1, T2> ForLazy2Iterable<T1, T2> forc(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        return new ForLazy2Iterable<>(i1, i2);
    }

    public static final class ForLazy2Iterable<T1, T2> {

        private final Iterable<T1> i1;
        private final Function1<? super T1, Iterable<T2>> i2;

        private ForLazy2Iterable(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2) {
            this.i1 = i1;
            this.i2 = i2;
        }

        public <R> List<R> yield(Function2<? super T1, ? super T2, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2.apply(t1)) {
                    result.add(f.apply(t1, t2));
                }
            }
            return Collections.unmodifiableList(result);
        }
    }

    public static <T1, T2, T3> ForLazy3Iterable<T1, T2, T3> forc(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2, Function2<? super T1, ? super T2, Iterable<T3>> i3) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        Objects.requireNonNull(i3, "i3 is null");
        return new ForLazy3Iterable<>(i1, i2, i3);
    }

    public static final class ForLazy3Iterable<T1, T2, T3> {

        private final Iterable<T1> i1;
        private final Function1<? super T1, Iterable<T2>> i2;
        private final Function2<? super T1, ? super T2, Iterable<T3>> i3;

        private ForLazy3Iterable(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2, Function2<? super T1, ? super T2, Iterable<T3>> i3) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
        }

        public <R> List<R> yield(Function3<? super T1, ? super T2, ? super T3, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2.apply(t1)) {
                    for (T3 t3 : i3.apply(t1, t2)) {
                        result.add(f.apply(t1, t2, t3));
                    }
                }
            }
            return Collections.unmodifiableList(result);
        }
    }

    public static <T1, T2, T3, T4> ForLazy4Iterable<T1, T2, T3, T4> forc(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2, Function2<? super T1, ? super T2, Iterable<T3>> i3, Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        Objects.requireNonNull(i3, "i3 is null");
        Objects.requireNonNull(i4, "i4 is null");
        return new ForLazy4Iterable<>(i1, i2, i3, i4);
    }

    public static final class ForLazy4Iterable<T1, T2, T3, T4> {

        private final Iterable<T1> i1;
        private final Function1<? super T1, Iterable<T2>> i2;
        private final Function2<? super T1, ? super T2, Iterable<T3>> i3;
        private final Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4;

        private ForLazy4Iterable(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2, Function2<? super T1, ? super T2, Iterable<T3>> i3, Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
            this.i4 = i4;
        }

        public <R> List<R> yield(Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2.apply(t1)) {
                    for (T3 t3 : i3.apply(t1, t2)) {
                        for (T4 t4 : i4.apply(t1, t2, t3)) {
                            result.add(f.apply(t1, t2, t3, t4));
                        }
                    }
                }
            }
            return Collections.unmodifiableList(result);
        }
    }

    public static <T1, T2, T3, T4, T5> ForLazy5Iterable<T1, T2, T3, T4, T5> forc(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2, Function2<? super T1, ? super T2, Iterable<T3>> i3, Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Iterable<T5>> i5) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        Objects.requireNonNull(i3, "i3 is null");
        Objects.requireNonNull(i4, "i4 is null");
        Objects.requireNonNull(i5, "i5 is null");
        return new ForLazy5Iterable<>(i1, i2, i3, i4, i5);
    }

    public static final class ForLazy5Iterable<T1, T2, T3, T4, T5> {

        private final Iterable<T1> i1;
        private final Function1<? super T1, Iterable<T2>> i2;
        private final Function2<? super T1, ? super T2, Iterable<T3>> i3;
        private final Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4;
        private final Function4<? super T1, ? super T2, ? super T3, ? super T4, Iterable<T5>> i5;

        private ForLazy5Iterable(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2, Function2<? super T1, ? super T2, Iterable<T3>> i3, Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Iterable<T5>> i5) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
            this.i4 = i4;
            this.i5 = i5;
        }

        public <R> List<R> yield(Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2.apply(t1)) {
                    for (T3 t3 : i3.apply(t1, t2)) {
                        for (T4 t4 : i4.apply(t1, t2, t3)) {
                            for (T5 t5 : i5.apply(t1, t2, t3, t4)) {
                                result.add(f.apply(t1, t2, t3, t4, t5));
                            }
                        }
                    }
                }
            }
            return Collections.unmodifiableList(result);
        }
    }

    public static <T1, T2, T3, T4, T5, T6> ForLazy6Iterable<T1, T2, T3, T4, T5, T6> forc(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2, Function2<? super T1, ? super T2, Iterable<T3>> i3, Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Iterable<T5>> i5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Iterable<T6>> i6) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        Objects.requireNonNull(i3, "i3 is null");
        Objects.requireNonNull(i4, "i4 is null");
        Objects.requireNonNull(i5, "i5 is null");
        Objects.requireNonNull(i6, "i6 is null");
        return new ForLazy6Iterable<>(i1, i2, i3, i4, i5, i6);
    }

    public static final class ForLazy6Iterable<T1, T2, T3, T4, T5, T6> {

        private final Iterable<T1> i1;
        private final Function1<? super T1, Iterable<T2>> i2;
        private final Function2<? super T1, ? super T2, Iterable<T3>> i3;
        private final Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4;
        private final Function4<? super T1, ? super T2, ? super T3, ? super T4, Iterable<T5>> i5;
        private final Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Iterable<T6>> i6;

        private ForLazy6Iterable(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2, Function2<? super T1, ? super T2, Iterable<T3>> i3, Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Iterable<T5>> i5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Iterable<T6>> i6) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
            this.i4 = i4;
            this.i5 = i5;
            this.i6 = i6;
        }

        public <R> List<R> yield(Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2.apply(t1)) {
                    for (T3 t3 : i3.apply(t1, t2)) {
                        for (T4 t4 : i4.apply(t1, t2, t3)) {
                            for (T5 t5 : i5.apply(t1, t2, t3, t4)) {
                                for (T6 t6 : i6.apply(t1, t2, t3, t4, t5)) {
                                    result.add(f.apply(t1, t2, t3, t4, t5, t6));
                                }
                            }
                        }
                    }
                }
            }
            return Collections.unmodifiableList(result);
        }
    }

    public static <T1, T2, T3, T4, T5, T6, T7> ForLazy7Iterable<T1, T2, T3, T4, T5, T6, T7> forc(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2, Function2<? super T1, ? super T2, Iterable<T3>> i3, Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Iterable<T5>> i5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Iterable<T6>> i6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Iterable<T7>> i7) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        Objects.requireNonNull(i3, "i3 is null");
        Objects.requireNonNull(i4, "i4 is null");
        Objects.requireNonNull(i5, "i5 is null");
        Objects.requireNonNull(i6, "i6 is null");
        Objects.requireNonNull(i7, "i7 is null");
        return new ForLazy7Iterable<>(i1, i2, i3, i4, i5, i6, i7);
    }

    public static final class ForLazy7Iterable<T1, T2, T3, T4, T5, T6, T7> {

        private final Iterable<T1> i1;
        private final Function1<? super T1, Iterable<T2>> i2;
        private final Function2<? super T1, ? super T2, Iterable<T3>> i3;
        private final Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4;
        private final Function4<? super T1, ? super T2, ? super T3, ? super T4, Iterable<T5>> i5;
        private final Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Iterable<T6>> i6;
        private final Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Iterable<T7>> i7;

        private ForLazy7Iterable(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2, Function2<? super T1, ? super T2, Iterable<T3>> i3, Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Iterable<T5>> i5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Iterable<T6>> i6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Iterable<T7>> i7) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
            this.i4 = i4;
            this.i5 = i5;
            this.i6 = i6;
            this.i7 = i7;
        }

        public <R> List<R> yield(Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2.apply(t1)) {
                    for (T3 t3 : i3.apply(t1, t2)) {
                        for (T4 t4 : i4.apply(t1, t2, t3)) {
                            for (T5 t5 : i5.apply(t1, t2, t3, t4)) {
                                for (T6 t6 : i6.apply(t1, t2, t3, t4, t5)) {
                                    for (T7 t7 : i7.apply(t1, t2, t3, t4, t5, t6)) {
                                        result.add(f.apply(t1, t2, t3, t4, t5, t6, t7));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return Collections.unmodifiableList(result);
        }
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> ForLazy8Iterable<T1, T2, T3, T4, T5, T6, T7, T8> forc(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2, Function2<? super T1, ? super T2, Iterable<T3>> i3, Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Iterable<T5>> i5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Iterable<T6>> i6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Iterable<T7>> i7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, Iterable<T8>> i8) {
        Objects.requireNonNull(i1, "i1 is null");
        Objects.requireNonNull(i2, "i2 is null");
        Objects.requireNonNull(i3, "i3 is null");
        Objects.requireNonNull(i4, "i4 is null");
        Objects.requireNonNull(i5, "i5 is null");
        Objects.requireNonNull(i6, "i6 is null");
        Objects.requireNonNull(i7, "i7 is null");
        Objects.requireNonNull(i8, "i8 is null");
        return new ForLazy8Iterable<>(i1, i2, i3, i4, i5, i6, i7, i8);
    }

    public static final class ForLazy8Iterable<T1, T2, T3, T4, T5, T6, T7, T8> {

        private final Iterable<T1> i1;
        private final Function1<? super T1, Iterable<T2>> i2;
        private final Function2<? super T1, ? super T2, Iterable<T3>> i3;
        private final Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4;
        private final Function4<? super T1, ? super T2, ? super T3, ? super T4, Iterable<T5>> i5;
        private final Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Iterable<T6>> i6;
        private final Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Iterable<T7>> i7;
        private final Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, Iterable<T8>> i8;

        private ForLazy8Iterable(Iterable<T1> i1, Function1<? super T1, Iterable<T2>> i2, Function2<? super T1, ? super T2, Iterable<T3>> i3, Function3<? super T1, ? super T2, ? super T3, Iterable<T4>> i4, Function4<? super T1, ? super T2, ? super T3, ? super T4, Iterable<T5>> i5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Iterable<T6>> i6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, Iterable<T7>> i7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, Iterable<T8>> i8) {
            this.i1 = i1;
            this.i2 = i2;
            this.i3 = i3;
            this.i4 = i4;
            this.i5 = i5;
            this.i6 = i6;
            this.i7 = i7;
            this.i8 = i8;
        }

        public <R> List<R> yield(Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> f) {
            Objects.requireNonNull(f, "f is null");

            List<R> result = new ArrayList<>();
            for (T1 t1 : i1) {
                for (T2 t2 : i2.apply(t1)) {
                    for (T3 t3 : i3.apply(t1, t2)) {
                        for (T4 t4 : i4.apply(t1, t2, t3)) {
                            for (T5 t5 : i5.apply(t1, t2, t3, t4)) {
                                for (T6 t6 : i6.apply(t1, t2, t3, t4, t5)) {
                                    for (T7 t7 : i7.apply(t1, t2, t3, t4, t5, t6)) {
                                        for (T8 t8 : i8.apply(t1, t2, t3, t4, t5, t6, t7)) {
                                            result.add(f.apply(t1, t2, t3, t4, t5, t6, t7, t8));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return Collections.unmodifiableList(result);
        }
    }
}
