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

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ForComprehensionTest {

    @Nested
    class EagerOptional {

        @Test
        void shouldTestEagerOptional2() {
            Optional<Integer> o1 = Optional.of(1);
            Optional<Integer> o2 = Optional.of(2);
            Optional<Integer> empty = Optional.empty();

            assertThat(ForComprehension.forc(o1, o2).yield((t1, t2) -> t1 + t2)).hasValue(3);
            assertThat(ForComprehension.forc(empty, o2).yield((t1, t2) -> t1 + t2)).isEmpty();
            assertThat(ForComprehension.forc(o1, empty).yield((t1, t2) -> t1 + t2)).isEmpty();
        }

        @Test
        void shouldTestEagerOptional3() {
            Optional<Integer> o1 = Optional.of(1);
            Optional<Integer> o2 = Optional.of(2);
            Optional<Integer> o3 = Optional.of(3);
            Optional<Integer> empty = Optional.empty();

            assertThat(ForComprehension.forc(o1, o2, o3).yield((t1, t2, t3) -> t1 + t2 + t3)).hasValue(6);
            assertThat(ForComprehension.forc(empty, o2, o3).yield((t1, t2, t3) -> t1 + t2 + t3)).isEmpty();
            assertThat(ForComprehension.forc(o1, o2, empty).yield((t1, t2, t3) -> t1 + t2 + t3)).isEmpty();
        }

        @Test
        void shouldTestEagerOptional4() {
            Optional<Integer> o1 = Optional.of(1);
            Optional<Integer> o2 = Optional.of(2);
            Optional<Integer> o3 = Optional.of(3);
            Optional<Integer> o4 = Optional.of(4);
            Optional<Integer> empty = Optional.empty();

            assertThat(ForComprehension.forc(o1, o2, o3, o4).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).hasValue(10);
            assertThat(ForComprehension.forc(empty, o2, o3, o4).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).isEmpty();
            assertThat(ForComprehension.forc(o1, o2, o3, empty).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).isEmpty();
        }

        @Test
        void shouldTestEagerOptional5() {
            Optional<Integer> o1 = Optional.of(1);
            Optional<Integer> o2 = Optional.of(2);
            Optional<Integer> o3 = Optional.of(3);
            Optional<Integer> o4 = Optional.of(4);
            Optional<Integer> o5 = Optional.of(5);
            Optional<Integer> empty = Optional.empty();

            assertThat(ForComprehension.forc(o1, o2, o3, o4, o5).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).hasValue(15);
            assertThat(ForComprehension.forc(empty, o2, o3, o4, o5).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).isEmpty();
            assertThat(ForComprehension.forc(o1, o2, o3, o4, empty).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).isEmpty();
        }

        @Test
        void shouldTestEagerOptional6() {
            Optional<Integer> o1 = Optional.of(1);
            Optional<Integer> o2 = Optional.of(2);
            Optional<Integer> o3 = Optional.of(3);
            Optional<Integer> o4 = Optional.of(4);
            Optional<Integer> o5 = Optional.of(5);
            Optional<Integer> o6 = Optional.of(6);
            Optional<Integer> empty = Optional.empty();

            assertThat(ForComprehension.forc(o1, o2, o3, o4, o5, o6).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).hasValue(21);
            assertThat(ForComprehension.forc(empty, o2, o3, o4, o5, o6).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).isEmpty();
            assertThat(ForComprehension.forc(o1, o2, o3, o4, o5, empty).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).isEmpty();
        }

        @Test
        void shouldTestEagerOptional7() {
            Optional<Integer> o1 = Optional.of(1);
            Optional<Integer> o2 = Optional.of(2);
            Optional<Integer> o3 = Optional.of(3);
            Optional<Integer> o4 = Optional.of(4);
            Optional<Integer> o5 = Optional.of(5);
            Optional<Integer> o6 = Optional.of(6);
            Optional<Integer> o7 = Optional.of(7);
            Optional<Integer> empty = Optional.empty();

            assertThat(ForComprehension.forc(o1, o2, o3, o4, o5, o6, o7).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).hasValue(28);
            assertThat(ForComprehension.forc(empty, o2, o3, o4, o5, o6, o7).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).isEmpty();
            assertThat(ForComprehension.forc(o1, o2, o3, o4, o5, o6, empty).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).isEmpty();
        }

        @Test
        void shouldTestEagerOptional8() {
            Optional<Integer> o1 = Optional.of(1);
            Optional<Integer> o2 = Optional.of(2);
            Optional<Integer> o3 = Optional.of(3);
            Optional<Integer> o4 = Optional.of(4);
            Optional<Integer> o5 = Optional.of(5);
            Optional<Integer> o6 = Optional.of(6);
            Optional<Integer> o7 = Optional.of(7);
            Optional<Integer> o8 = Optional.of(8);
            Optional<Integer> empty = Optional.empty();

            assertThat(ForComprehension.forc(o1, o2, o3, o4, o5, o6, o7, o8).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).hasValue(36);
            assertThat(ForComprehension.forc(empty, o2, o3, o4, o5, o6, o7, o8).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).isEmpty();
            assertThat(ForComprehension.forc(o1, o2, o3, o4, o5, o6, o7, empty).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).isEmpty();
        }
    }

    @Nested
    class EagerStream {

        @Test
        void shouldTestEagerStream2() {
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20)).yield((t1, t2) -> t1 + t2))
                .containsExactly(11, 21, 12, 22);
        }

        @Test
        void shouldTestEagerStreamWithEmptyStream2() {
            assertThat(ForComprehension.forc(Stream.<Integer>empty(), Stream.of(10, 20)).yield((t1, t2) -> t1 + t2))
                .isEmpty();
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.<Integer>empty()).yield((t1, t2) -> t1 + t2))
                .isEmpty();
        }

        @Test
        void shouldTestEagerStream3() {
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100)).yield((t1, t2, t3) -> t1 + t2 + t3))
                .containsExactly(111, 121, 112, 122);
        }

        @Test
        void shouldTestEagerStreamWithEmptyStream3() {
            assertThat(ForComprehension.forc(Stream.<Integer>empty(), Stream.of(10, 20), Stream.of(100)).yield((t1, t2, t3) -> t1 + t2 + t3))
                .isEmpty();
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.<Integer>empty()).yield((t1, t2, t3) -> t1 + t2 + t3))
                .isEmpty();
        }

        @Test
        void shouldTestEagerStream4() {
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000)).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                .containsExactly(1111, 1121, 1112, 1122);
        }

        @Test
        void shouldTestEagerStreamWithEmptyStream4() {
            assertThat(ForComprehension.forc(Stream.<Integer>empty(), Stream.of(10, 20), Stream.of(100), Stream.of(1000)).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                .isEmpty();
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.<Integer>empty()).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                .isEmpty();
        }

        @Test
        void shouldTestEagerStream5() {
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000)).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                .containsExactly(11111, 11121, 11112, 11122);
        }

        @Test
        void shouldTestEagerStreamWithEmptyStream5() {
            assertThat(ForComprehension.forc(Stream.<Integer>empty(), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000)).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                .isEmpty();
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.<Integer>empty()).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                .isEmpty();
        }

        @Test
        void shouldTestEagerStream6() {
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000)).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                .containsExactly(111111, 111121, 111112, 111122);
        }

        @Test
        void shouldTestEagerStreamWithEmptyStream6() {
            assertThat(ForComprehension.forc(Stream.<Integer>empty(), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000)).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                .isEmpty();
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.<Integer>empty()).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                .isEmpty();
        }

        @Test
        void shouldTestEagerStream7() {
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000), Stream.of(1000000)).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                .containsExactly(1111111, 1111121, 1111112, 1111122);
        }

        @Test
        void shouldTestEagerStreamWithEmptyStream7() {
            assertThat(ForComprehension.forc(Stream.<Integer>empty(), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000), Stream.of(1000000)).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                .isEmpty();
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000), Stream.<Integer>empty()).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                .isEmpty();
        }

        @Test
        void shouldTestEagerStream8() {
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000), Stream.of(1000000), Stream.of(10000000)).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                .containsExactly(11111111, 11111121, 11111112, 11111122);
        }

        @Test
        void shouldTestEagerStreamWithEmptyStream8() {
            assertThat(ForComprehension.forc(Stream.<Integer>empty(), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000), Stream.of(1000000), Stream.of(10000000)).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                .isEmpty();
            assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000), Stream.of(1000000), Stream.<Integer>empty()).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                .isEmpty();
        }
    }

    @Nested
    class EagerIterable {

        @Test
        void shouldTestEagerIterable2() {
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20)).yield((t1, t2) -> t1 + t2))
                .containsExactly(11, 21, 12, 22);
        }

        @Test
        void shouldTestEagerIterableWithEmptyIterable2() {
            assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), List.of(10, 20)).yield((t1, t2) -> t1 + t2)).isEmpty();
            assertThat(ForComprehension.forc(List.of(1, 2), Collections.<Integer>emptyList()).yield((t1, t2) -> t1 + t2)).isEmpty();
        }

        @Test
        void shouldTestEagerIterable3() {
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100)).yield((t1, t2, t3) -> t1 + t2 + t3))
                .containsExactly(111, 121, 112, 122);
        }

        @Test
        void shouldTestEagerIterableWithEmptyIterable3() {
            assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), List.of(10, 20), List.of(100)).yield((t1, t2, t3) -> t1 + t2 + t3)).isEmpty();
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), Collections.<Integer>emptyList()).yield((t1, t2, t3) -> t1 + t2 + t3)).isEmpty();
        }

        @Test
        void shouldTestEagerIterable4() {
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000)).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                .containsExactly(1111, 1121, 1112, 1122);
        }

        @Test
        void shouldTestEagerIterableWithEmptyIterable4() {
            assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), List.of(10, 20), List.of(100), List.of(1000)).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).isEmpty();
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), Collections.<Integer>emptyList()).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).isEmpty();
        }

        @Test
        void shouldTestEagerIterable5() {
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000)).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                .containsExactly(11111, 11121, 11112, 11122);
        }

        @Test
        void shouldTestEagerIterableWithEmptyIterable5() {
            assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), List.of(10, 20), List.of(100), List.of(1000), List.of(10000)).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).isEmpty();
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), Collections.<Integer>emptyList()).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).isEmpty();
        }

        @Test
        void shouldTestEagerIterable6() {
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000)).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                .containsExactly(111111, 111121, 111112, 111122);
        }

        @Test
        void shouldTestEagerIterableWithEmptyIterable6() {
            assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000)).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).isEmpty();
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), Collections.<Integer>emptyList()).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).isEmpty();
        }

        @Test
        void shouldTestEagerIterable7() {
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000), List.of(1000000)).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                .containsExactly(1111111, 1111121, 1111112, 1111122);
        }

        @Test
        void shouldTestEagerIterableWithEmptyIterable7() {
            assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000), List.of(1000000)).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).isEmpty();
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000), Collections.<Integer>emptyList()).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).isEmpty();
        }

        @Test
        void shouldTestEagerIterable8() {
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000), List.of(1000000), List.of(10000000)).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                .containsExactly(11111111, 11111121, 11111112, 11111122);
        }

        @Test
        void shouldTestEagerIterableWithEmptyIterable8() {
            assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000), List.of(1000000), List.of(10000000)).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).isEmpty();
            assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000), List.of(1000000), Collections.<Integer>emptyList()).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).isEmpty();
        }
    }

    @Nested
    class Lazy {

        @Test
        void shouldTestLazyOptional() {
            Optional<Integer> o1 = Optional.of(1);
            Optional<Integer> empty = Optional.empty();

            assertThat(ForComprehension.forc(o1, v1 -> Optional.of(v1)).yield((t1, t2) -> t1 + t2)).hasValue(2);
            assertThat(ForComprehension.forc(o1, v1 -> empty).yield((t1, t2) -> t1 + t2)).isEmpty();
            assertThat(ForComprehension.forc(empty, v1 -> o1).yield((t1, t2) -> t1 + t2)).isEmpty();
            assertThat(ForComprehension.forc(empty, v1 -> empty).yield((t1, t2) -> t1 + t2)).isEmpty();
        }

        @Test
        void shouldTestLazyStream() {
            assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(v1 * 10, v1 * 20)).yield((t1, t2) -> t1 + t2))
                .containsExactly(11, 21, 22, 42);
        }

        @Test
        void shouldTestLazyStreamWithEmptyStream() {
            assertThat(ForComprehension.forc(Stream.<Integer>empty(), v1 -> Stream.of(v1)).yield((t1, t2) -> t1 + t2)).isEmpty();
            assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.<Integer>empty()).yield((t1, t2) -> t1 + t2)).isEmpty();
        }

        @Test
        void shouldTestLazyIterable() {
            assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(v1 * 10, v1 * 20)).yield((t1, t2) -> t1 + t2))
                .containsExactly(11, 21, 22, 42);
        }

        @Test
        void shouldTestLazyIterableWithEmptyIterable() {
            assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), v1 -> List.of(v1)).yield((t1, t2) -> t1 + t2)).isEmpty();
            assertThat(ForComprehension.forc(List.of(1, 2), v1 -> Collections.<Integer>emptyList()).yield((t1, t2) -> t1 + t2)).isEmpty();
        }
    }
}
