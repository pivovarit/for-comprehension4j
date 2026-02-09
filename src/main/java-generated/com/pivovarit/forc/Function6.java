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

import java.util.Objects;

/**
 * Represents a function with 6 arguments.
 *
 * @param <T1> the type of the first argument
 * @param <T2> the type of the second argument
 * @param <T3> the type of the third argument
 * @param <T4> the type of the 4th argument
 * @param <T5> the type of the 5th argument
 * @param <T6> the type of the 6th argument
 * @param <R> the type of the result
 */
@FunctionalInterface
public interface Function6<T1, T2, T3, T4, T5, T6, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t1 the first argument
     * @param t2 the second argument
     * @param t3 the third argument
     * @param t4 the 4th argument
     * @param t5 the 5th argument
     * @param t6 the 6th argument
     * @return the function result
     */
    R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);

    /**
     * Returns a composed function that first applies this function to its input,
     * and then applies the {@code after} function to the result.
     *
     * @param after the function to apply after this function is applied
     * @param <V> the type of the output of the {@code after} function
     * @return a composed function
     * @throws NullPointerException if after is {@code null}
     */
    default <V> Function6<T1, T2, T3, T4, T5, T6, V> andThen(Function1<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (t1, t2, t3, t4, t5, t6) -> after.apply(apply(t1, t2, t3, t4, t5, t6));
    }
}
