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
 * Represents a function with 1 argument.
 *
 * @param <T1> the type of the first argument
 * @param <R> the type of the result
 */
@FunctionalInterface
public interface Function1<T1, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t1 the first argument
     * @return the function result
     */
    R apply(T1 t1);

    /**
     * Returns a composed function that first applies this function to its input,
     * and then applies the {@code after} function to the result.
     *
     * @param after the function to apply after this function is applied
     * @param <V> the type of the output of the {@code after} function
     * @return a composed function
     * @throws NullPointerException if after is {@code null}
     */
    default <V> Function1<T1, V> andThen(Function1<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (t1) -> after.apply(apply(t1));
    }
}
