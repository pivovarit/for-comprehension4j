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

        @Nested
        class Arity2 {
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
            void shouldTestEagerOptionalFilter2() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> o2 = Optional.of(2);
                assertThat(ForComprehension.forc(o1, o2).filter((t1, t2) -> t1 + t2 == 3).yield((t1, t2) -> t1 + t2)).hasValue(3);
                assertThat(ForComprehension.forc(o1, o2).filter((t1, t2) -> false).yield((t1, t2) -> t1 + t2)).isEmpty();
            }
        }

        @Nested
        class Arity3 {
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
            void shouldTestEagerOptionalFilter3() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> o2 = Optional.of(2);
                Optional<Integer> o3 = Optional.of(3);
                assertThat(ForComprehension.forc(o1, o2, o3).filter((t1, t2, t3) -> t1 + t2 + t3 == 6).yield((t1, t2, t3) -> t1 + t2 + t3)).hasValue(6);
                assertThat(ForComprehension.forc(o1, o2, o3).filter((t1, t2, t3) -> false).yield((t1, t2, t3) -> t1 + t2 + t3)).isEmpty();
            }
        }

        @Nested
        class Arity4 {
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
            void shouldTestEagerOptionalFilter4() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> o2 = Optional.of(2);
                Optional<Integer> o3 = Optional.of(3);
                Optional<Integer> o4 = Optional.of(4);
                assertThat(ForComprehension.forc(o1, o2, o3, o4).filter((t1, t2, t3, t4) -> t1 + t2 + t3 + t4 == 10).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).hasValue(10);
                assertThat(ForComprehension.forc(o1, o2, o3, o4).filter((t1, t2, t3, t4) -> false).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).isEmpty();
            }
        }

        @Nested
        class Arity5 {
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
            void shouldTestEagerOptionalFilter5() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> o2 = Optional.of(2);
                Optional<Integer> o3 = Optional.of(3);
                Optional<Integer> o4 = Optional.of(4);
                Optional<Integer> o5 = Optional.of(5);
                assertThat(ForComprehension.forc(o1, o2, o3, o4, o5).filter((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5 == 15).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).hasValue(15);
                assertThat(ForComprehension.forc(o1, o2, o3, o4, o5).filter((t1, t2, t3, t4, t5) -> false).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).isEmpty();
            }
        }

        @Nested
        class Arity6 {
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
            void shouldTestEagerOptionalFilter6() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> o2 = Optional.of(2);
                Optional<Integer> o3 = Optional.of(3);
                Optional<Integer> o4 = Optional.of(4);
                Optional<Integer> o5 = Optional.of(5);
                Optional<Integer> o6 = Optional.of(6);
                assertThat(ForComprehension.forc(o1, o2, o3, o4, o5, o6).filter((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6 == 21).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).hasValue(21);
                assertThat(ForComprehension.forc(o1, o2, o3, o4, o5, o6).filter((t1, t2, t3, t4, t5, t6) -> false).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).isEmpty();
            }
        }

        @Nested
        class Arity7 {
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
            void shouldTestEagerOptionalFilter7() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> o2 = Optional.of(2);
                Optional<Integer> o3 = Optional.of(3);
                Optional<Integer> o4 = Optional.of(4);
                Optional<Integer> o5 = Optional.of(5);
                Optional<Integer> o6 = Optional.of(6);
                Optional<Integer> o7 = Optional.of(7);
                assertThat(ForComprehension.forc(o1, o2, o3, o4, o5, o6, o7).filter((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 == 28).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).hasValue(28);
                assertThat(ForComprehension.forc(o1, o2, o3, o4, o5, o6, o7).filter((t1, t2, t3, t4, t5, t6, t7) -> false).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).isEmpty();
            }
        }

        @Nested
        class Arity8 {
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

            @Test
            void shouldTestEagerOptionalFilter8() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> o2 = Optional.of(2);
                Optional<Integer> o3 = Optional.of(3);
                Optional<Integer> o4 = Optional.of(4);
                Optional<Integer> o5 = Optional.of(5);
                Optional<Integer> o6 = Optional.of(6);
                Optional<Integer> o7 = Optional.of(7);
                Optional<Integer> o8 = Optional.of(8);
                assertThat(ForComprehension.forc(o1, o2, o3, o4, o5, o6, o7, o8).filter((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8 == 36).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).hasValue(36);
                assertThat(ForComprehension.forc(o1, o2, o3, o4, o5, o6, o7, o8).filter((t1, t2, t3, t4, t5, t6, t7, t8) -> false).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).isEmpty();
            }
        }
    }

    @Nested
    class EagerStream {

        @Nested
        class Arity2 {
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
            void shouldTestEagerStreamFilter2() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20)).filter((t1, t2) -> t1 == 1).yield((t1, t2) -> t1 + t2))
                    .containsExactly(11, 21);
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20)).filter((t1, t2) -> false).yield((t1, t2) -> t1 + t2))
                    .isEmpty();
            }
        }

        @Nested
        class Arity3 {
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
            void shouldTestEagerStreamFilter3() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100)).filter((t1, t2, t3) -> t1 == 1).yield((t1, t2, t3) -> t1 + t2 + t3))
                    .containsExactly(111, 121);
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100)).filter((t1, t2, t3) -> false).yield((t1, t2, t3) -> t1 + t2 + t3))
                    .isEmpty();
            }
        }

        @Nested
        class Arity4 {
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
            void shouldTestEagerStreamFilter4() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000)).filter((t1, t2, t3, t4) -> t1 == 1).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                    .containsExactly(1111, 1121);
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000)).filter((t1, t2, t3, t4) -> false).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                    .isEmpty();
            }
        }

        @Nested
        class Arity5 {
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
            void shouldTestEagerStreamFilter5() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000)).filter((t1, t2, t3, t4, t5) -> t1 == 1).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                    .containsExactly(11111, 11121);
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000)).filter((t1, t2, t3, t4, t5) -> false).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                    .isEmpty();
            }
        }

        @Nested
        class Arity6 {
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
            void shouldTestEagerStreamFilter6() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000)).filter((t1, t2, t3, t4, t5, t6) -> t1 == 1).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                    .containsExactly(111111, 111121);
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000)).filter((t1, t2, t3, t4, t5, t6) -> false).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                    .isEmpty();
            }
        }

        @Nested
        class Arity7 {
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
            void shouldTestEagerStreamFilter7() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000), Stream.of(1000000)).filter((t1, t2, t3, t4, t5, t6, t7) -> t1 == 1).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                    .containsExactly(1111111, 1111121);
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000), Stream.of(1000000)).filter((t1, t2, t3, t4, t5, t6, t7) -> false).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                    .isEmpty();
            }
        }

        @Nested
        class Arity8 {
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

            @Test
            void shouldTestEagerStreamFilter8() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000), Stream.of(1000000), Stream.of(10000000)).filter((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 == 1).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                    .containsExactly(11111111, 11111121);
                assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.of(10, 20), Stream.of(100), Stream.of(1000), Stream.of(10000), Stream.of(100000), Stream.of(1000000), Stream.of(10000000)).filter((t1, t2, t3, t4, t5, t6, t7, t8) -> false).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                    .isEmpty();
            }
        }
    }

    @Nested
    class EagerIterable {

        @Nested
        class Arity2 {
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
            void shouldTestEagerIterableFilter2() {
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20)).filter((t1, t2) -> t1 == 1).yield((t1, t2) -> t1 + t2))
                    .containsExactly(11, 21);
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20)).filter((t1, t2) -> false).yield((t1, t2) -> t1 + t2))
                    .isEmpty();
            }
        }

        @Nested
        class Arity3 {
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
            void shouldTestEagerIterableFilter3() {
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100)).filter((t1, t2, t3) -> t1 == 1).yield((t1, t2, t3) -> t1 + t2 + t3))
                    .containsExactly(111, 121);
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100)).filter((t1, t2, t3) -> false).yield((t1, t2, t3) -> t1 + t2 + t3))
                    .isEmpty();
            }
        }

        @Nested
        class Arity4 {
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
            void shouldTestEagerIterableFilter4() {
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000)).filter((t1, t2, t3, t4) -> t1 == 1).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                    .containsExactly(1111, 1121);
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000)).filter((t1, t2, t3, t4) -> false).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                    .isEmpty();
            }
        }

        @Nested
        class Arity5 {
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
            void shouldTestEagerIterableFilter5() {
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000)).filter((t1, t2, t3, t4, t5) -> t1 == 1).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                    .containsExactly(11111, 11121);
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000)).filter((t1, t2, t3, t4, t5) -> false).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                    .isEmpty();
            }
        }

        @Nested
        class Arity6 {
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
            void shouldTestEagerIterableFilter6() {
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000)).filter((t1, t2, t3, t4, t5, t6) -> t1 == 1).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                    .containsExactly(111111, 111121);
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000)).filter((t1, t2, t3, t4, t5, t6) -> false).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                    .isEmpty();
            }
        }

        @Nested
        class Arity7 {
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
            void shouldTestEagerIterableFilter7() {
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000), List.of(1000000)).filter((t1, t2, t3, t4, t5, t6, t7) -> t1 == 1).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                    .containsExactly(1111111, 1111121);
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000), List.of(1000000)).filter((t1, t2, t3, t4, t5, t6, t7) -> false).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                    .isEmpty();
            }
        }

        @Nested
        class Arity8 {
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

            @Test
            void shouldTestEagerIterableFilter8() {
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000), List.of(1000000), List.of(10000000)).filter((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 == 1).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                    .containsExactly(11111111, 11111121);
                assertThat(ForComprehension.forc(List.of(1, 2), List.of(10, 20), List.of(100), List.of(1000), List.of(10000), List.of(100000), List.of(1000000), List.of(10000000)).filter((t1, t2, t3, t4, t5, t6, t7, t8) -> false).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                    .isEmpty();
            }
        }
    }

    @Nested
    class LazyOptional {

        @Nested
        class Arity2 {
            @Test
            void shouldTestLazyOptional2() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> empty = Optional.empty();

                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2)).yield((t1, t2) -> t1 + t2)).hasValue(3);
                assertThat(ForComprehension.forc(empty, v1 -> Optional.of(2)).yield((t1, t2) -> t1 + t2)).isEmpty();
                assertThat(ForComprehension.forc(o1, v1 -> empty).yield((t1, t2) -> t1 + t2)).isEmpty();
            }

            @Test
            void shouldTestLazyOptionalFilter2() {
                Optional<Integer> o1 = Optional.of(1);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2)).filter((t1, t2) -> t1 + t2 == 3).yield((t1, t2) -> t1 + t2)).hasValue(3);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2)).filter((t1, t2) -> false).yield((t1, t2) -> t1 + t2)).isEmpty();
            }
        }

        @Nested
        class Arity3 {
            @Test
            void shouldTestLazyOptional3() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> empty = Optional.empty();

                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3)).yield((t1, t2, t3) -> t1 + t2 + t3)).hasValue(6);
                assertThat(ForComprehension.forc(empty, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3)).yield((t1, t2, t3) -> t1 + t2 + t3)).isEmpty();
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> empty).yield((t1, t2, t3) -> t1 + t2 + t3)).isEmpty();
            }

            @Test
            void shouldTestLazyOptionalFilter3() {
                Optional<Integer> o1 = Optional.of(1);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3)).filter((t1, t2, t3) -> t1 + t2 + t3 == 6).yield((t1, t2, t3) -> t1 + t2 + t3)).hasValue(6);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3)).filter((t1, t2, t3) -> false).yield((t1, t2, t3) -> t1 + t2 + t3)).isEmpty();
            }
        }

        @Nested
        class Arity4 {
            @Test
            void shouldTestLazyOptional4() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> empty = Optional.empty();

                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4)).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).hasValue(10);
                assertThat(ForComprehension.forc(empty, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4)).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).isEmpty();
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> empty).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).isEmpty();
            }

            @Test
            void shouldTestLazyOptionalFilter4() {
                Optional<Integer> o1 = Optional.of(1);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4)).filter((t1, t2, t3, t4) -> t1 + t2 + t3 + t4 == 10).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).hasValue(10);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4)).filter((t1, t2, t3, t4) -> false).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).isEmpty();
            }
        }

        @Nested
        class Arity5 {
            @Test
            void shouldTestLazyOptional5() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> empty = Optional.empty();

                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5)).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).hasValue(15);
                assertThat(ForComprehension.forc(empty, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5)).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).isEmpty();
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> empty).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).isEmpty();
            }

            @Test
            void shouldTestLazyOptionalFilter5() {
                Optional<Integer> o1 = Optional.of(1);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5)).filter((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5 == 15).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).hasValue(15);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5)).filter((t1, t2, t3, t4, t5) -> false).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).isEmpty();
            }
        }

        @Nested
        class Arity6 {
            @Test
            void shouldTestLazyOptional6() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> empty = Optional.empty();

                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6)).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).hasValue(21);
                assertThat(ForComprehension.forc(empty, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6)).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).isEmpty();
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> empty).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).isEmpty();
            }

            @Test
            void shouldTestLazyOptionalFilter6() {
                Optional<Integer> o1 = Optional.of(1);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6)).filter((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6 == 21).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).hasValue(21);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6)).filter((t1, t2, t3, t4, t5, t6) -> false).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).isEmpty();
            }
        }

        @Nested
        class Arity7 {
            @Test
            void shouldTestLazyOptional7() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> empty = Optional.empty();

                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6), (v1, v2, v3, v4, v5, v6) -> Optional.of(7)).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).hasValue(28);
                assertThat(ForComprehension.forc(empty, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6), (v1, v2, v3, v4, v5, v6) -> Optional.of(7)).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).isEmpty();
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6), (v1, v2, v3, v4, v5, v6) -> empty).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).isEmpty();
            }

            @Test
            void shouldTestLazyOptionalFilter7() {
                Optional<Integer> o1 = Optional.of(1);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6), (v1, v2, v3, v4, v5, v6) -> Optional.of(7)).filter((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 == 28).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).hasValue(28);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6), (v1, v2, v3, v4, v5, v6) -> Optional.of(7)).filter((t1, t2, t3, t4, t5, t6, t7) -> false).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).isEmpty();
            }
        }

        @Nested
        class Arity8 {
            @Test
            void shouldTestLazyOptional8() {
                Optional<Integer> o1 = Optional.of(1);
                Optional<Integer> empty = Optional.empty();

                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6), (v1, v2, v3, v4, v5, v6) -> Optional.of(7), (v1, v2, v3, v4, v5, v6, v7) -> Optional.of(8)).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).hasValue(36);
                assertThat(ForComprehension.forc(empty, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6), (v1, v2, v3, v4, v5, v6) -> Optional.of(7), (v1, v2, v3, v4, v5, v6, v7) -> Optional.of(8)).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).isEmpty();
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6), (v1, v2, v3, v4, v5, v6) -> Optional.of(7), (v1, v2, v3, v4, v5, v6, v7) -> empty).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).isEmpty();
            }

            @Test
            void shouldTestLazyOptionalFilter8() {
                Optional<Integer> o1 = Optional.of(1);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6), (v1, v2, v3, v4, v5, v6) -> Optional.of(7), (v1, v2, v3, v4, v5, v6, v7) -> Optional.of(8)).filter((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8 == 36).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).hasValue(36);
                assertThat(ForComprehension.forc(o1, v1 -> Optional.of(2), (v1, v2) -> Optional.of(3), (v1, v2, v3) -> Optional.of(4), (v1, v2, v3, v4) -> Optional.of(5), (v1, v2, v3, v4, v5) -> Optional.of(6), (v1, v2, v3, v4, v5, v6) -> Optional.of(7), (v1, v2, v3, v4, v5, v6, v7) -> Optional.of(8)).filter((t1, t2, t3, t4, t5, t6, t7, t8) -> false).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).isEmpty();
            }
        }
    }

    @Nested
    class LazyStream {

        @Nested
        class Arity2 {
            @Test
            void shouldTestLazyStream2() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20)).yield((t1, t2) -> t1 + t2))
                    .containsExactly(11, 21, 12, 22);
            }

            @Test
            void shouldTestLazyStreamWithEmptyStream2() {
                assertThat(ForComprehension.forc(Stream.<Integer>empty(), v1 -> Stream.of(10, 20)).yield((t1, t2) -> t1 + t2))
                    .isEmpty();
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.<Integer>empty()).yield((t1, t2) -> t1 + t2))
                    .isEmpty();
            }

            @Test
            void shouldTestLazyStreamFilter2() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20)).filter((t1, t2) -> t1 == 1).yield((t1, t2) -> t1 + t2))
                    .containsExactly(11, 21);
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20)).filter((t1, t2) -> false).yield((t1, t2) -> t1 + t2))
                    .isEmpty();
            }
        }

        @Nested
        class Arity3 {
            @Test
            void shouldTestLazyStream3() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100)).yield((t1, t2, t3) -> t1 + t2 + t3))
                    .containsExactly(111, 121, 112, 122);
            }

            @Test
            void shouldTestLazyStreamWithEmptyStream3() {
                assertThat(ForComprehension.forc(Stream.<Integer>empty(), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100)).yield((t1, t2, t3) -> t1 + t2 + t3))
                    .isEmpty();
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.<Integer>empty()).yield((t1, t2, t3) -> t1 + t2 + t3))
                    .isEmpty();
            }

            @Test
            void shouldTestLazyStreamFilter3() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100)).filter((t1, t2, t3) -> t1 == 1).yield((t1, t2, t3) -> t1 + t2 + t3))
                    .containsExactly(111, 121);
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100)).filter((t1, t2, t3) -> false).yield((t1, t2, t3) -> t1 + t2 + t3))
                    .isEmpty();
            }
        }

        @Nested
        class Arity4 {
            @Test
            void shouldTestLazyStream4() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000)).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                    .containsExactly(1111, 1121, 1112, 1122);
            }

            @Test
            void shouldTestLazyStreamWithEmptyStream4() {
                assertThat(ForComprehension.forc(Stream.<Integer>empty(), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000)).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                    .isEmpty();
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.<Integer>empty()).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                    .isEmpty();
            }

            @Test
            void shouldTestLazyStreamFilter4() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000)).filter((t1, t2, t3, t4) -> t1 == 1).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                    .containsExactly(1111, 1121);
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000)).filter((t1, t2, t3, t4) -> false).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                    .isEmpty();
            }
        }

        @Nested
        class Arity5 {
            @Test
            void shouldTestLazyStream5() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000)).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                    .containsExactly(11111, 11121, 11112, 11122);
            }

            @Test
            void shouldTestLazyStreamWithEmptyStream5() {
                assertThat(ForComprehension.forc(Stream.<Integer>empty(), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000)).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                    .isEmpty();
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.<Integer>empty()).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                    .isEmpty();
            }

            @Test
            void shouldTestLazyStreamFilter5() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000)).filter((t1, t2, t3, t4, t5) -> t1 == 1).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                    .containsExactly(11111, 11121);
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000)).filter((t1, t2, t3, t4, t5) -> false).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                    .isEmpty();
            }
        }

        @Nested
        class Arity6 {
            @Test
            void shouldTestLazyStream6() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000)).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                    .containsExactly(111111, 111121, 111112, 111122);
            }

            @Test
            void shouldTestLazyStreamWithEmptyStream6() {
                assertThat(ForComprehension.forc(Stream.<Integer>empty(), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000)).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                    .isEmpty();
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.<Integer>empty()).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                    .isEmpty();
            }

            @Test
            void shouldTestLazyStreamFilter6() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000)).filter((t1, t2, t3, t4, t5, t6) -> t1 == 1).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                    .containsExactly(111111, 111121);
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000)).filter((t1, t2, t3, t4, t5, t6) -> false).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                    .isEmpty();
            }
        }

        @Nested
        class Arity7 {
            @Test
            void shouldTestLazyStream7() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000), (v1, v2, v3, v4, v5, v6) -> Stream.of(1000000)).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                    .containsExactly(1111111, 1111121, 1111112, 1111122);
            }

            @Test
            void shouldTestLazyStreamWithEmptyStream7() {
                assertThat(ForComprehension.forc(Stream.<Integer>empty(), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000), (v1, v2, v3, v4, v5, v6) -> Stream.of(1000000)).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                    .isEmpty();
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000), (v1, v2, v3, v4, v5, v6) -> Stream.<Integer>empty()).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                    .isEmpty();
            }

            @Test
            void shouldTestLazyStreamFilter7() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000), (v1, v2, v3, v4, v5, v6) -> Stream.of(1000000)).filter((t1, t2, t3, t4, t5, t6, t7) -> t1 == 1).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                    .containsExactly(1111111, 1111121);
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000), (v1, v2, v3, v4, v5, v6) -> Stream.of(1000000)).filter((t1, t2, t3, t4, t5, t6, t7) -> false).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                    .isEmpty();
            }
        }

        @Nested
        class Arity8 {
            @Test
            void shouldTestLazyStream8() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000), (v1, v2, v3, v4, v5, v6) -> Stream.of(1000000), (v1, v2, v3, v4, v5, v6, v7) -> Stream.of(10000000)).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                    .containsExactly(11111111, 11111121, 11111112, 11111122);
            }

            @Test
            void shouldTestLazyStreamWithEmptyStream8() {
                assertThat(ForComprehension.forc(Stream.<Integer>empty(), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000), (v1, v2, v3, v4, v5, v6) -> Stream.of(1000000), (v1, v2, v3, v4, v5, v6, v7) -> Stream.of(10000000)).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                    .isEmpty();
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000), (v1, v2, v3, v4, v5, v6) -> Stream.of(1000000), (v1, v2, v3, v4, v5, v6, v7) -> Stream.<Integer>empty()).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                    .isEmpty();
            }

            @Test
            void shouldTestLazyStreamFilter8() {
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000), (v1, v2, v3, v4, v5, v6) -> Stream.of(1000000), (v1, v2, v3, v4, v5, v6, v7) -> Stream.of(10000000)).filter((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 == 1).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                    .containsExactly(11111111, 11111121);
                assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(10, 20), (v1, v2) -> Stream.of(100), (v1, v2, v3) -> Stream.of(1000), (v1, v2, v3, v4) -> Stream.of(10000), (v1, v2, v3, v4, v5) -> Stream.of(100000), (v1, v2, v3, v4, v5, v6) -> Stream.of(1000000), (v1, v2, v3, v4, v5, v6, v7) -> Stream.of(10000000)).filter((t1, t2, t3, t4, t5, t6, t7, t8) -> false).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                    .isEmpty();
            }
        }
    }

    @Nested
    class LazyIterable {

        @Nested
        class Arity2 {
            @Test
            void shouldTestLazyIterable2() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20)).yield((t1, t2) -> t1 + t2))
                    .containsExactly(11, 21, 12, 22);
            }

            @Test
            void shouldTestLazyIterableWithEmptyIterable2() {
                assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), v1 -> List.of(10, 20)).yield((t1, t2) -> t1 + t2)).isEmpty();
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> Collections.<Integer>emptyList()).yield((t1, t2) -> t1 + t2)).isEmpty();
            }

            @Test
            void shouldTestLazyIterableFilter2() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20)).filter((t1, t2) -> t1 == 1).yield((t1, t2) -> t1 + t2))
                    .containsExactly(11, 21);
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20)).filter((t1, t2) -> false).yield((t1, t2) -> t1 + t2))
                    .isEmpty();
            }
        }

        @Nested
        class Arity3 {
            @Test
            void shouldTestLazyIterable3() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100)).yield((t1, t2, t3) -> t1 + t2 + t3))
                    .containsExactly(111, 121, 112, 122);
            }

            @Test
            void shouldTestLazyIterableWithEmptyIterable3() {
                assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), v1 -> List.of(10, 20), (v1, v2) -> List.of(100)).yield((t1, t2, t3) -> t1 + t2 + t3)).isEmpty();
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> Collections.<Integer>emptyList()).yield((t1, t2, t3) -> t1 + t2 + t3)).isEmpty();
            }

            @Test
            void shouldTestLazyIterableFilter3() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100)).filter((t1, t2, t3) -> t1 == 1).yield((t1, t2, t3) -> t1 + t2 + t3))
                    .containsExactly(111, 121);
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100)).filter((t1, t2, t3) -> false).yield((t1, t2, t3) -> t1 + t2 + t3))
                    .isEmpty();
            }
        }

        @Nested
        class Arity4 {
            @Test
            void shouldTestLazyIterable4() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000)).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                    .containsExactly(1111, 1121, 1112, 1122);
            }

            @Test
            void shouldTestLazyIterableWithEmptyIterable4() {
                assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000)).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).isEmpty();
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> Collections.<Integer>emptyList()).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4)).isEmpty();
            }

            @Test
            void shouldTestLazyIterableFilter4() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000)).filter((t1, t2, t3, t4) -> t1 == 1).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                    .containsExactly(1111, 1121);
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000)).filter((t1, t2, t3, t4) -> false).yield((t1, t2, t3, t4) -> t1 + t2 + t3 + t4))
                    .isEmpty();
            }
        }

        @Nested
        class Arity5 {
            @Test
            void shouldTestLazyIterable5() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000)).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                    .containsExactly(11111, 11121, 11112, 11122);
            }

            @Test
            void shouldTestLazyIterableWithEmptyIterable5() {
                assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000)).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).isEmpty();
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> Collections.<Integer>emptyList()).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5)).isEmpty();
            }

            @Test
            void shouldTestLazyIterableFilter5() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000)).filter((t1, t2, t3, t4, t5) -> t1 == 1).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                    .containsExactly(11111, 11121);
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000)).filter((t1, t2, t3, t4, t5) -> false).yield((t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5))
                    .isEmpty();
            }
        }

        @Nested
        class Arity6 {
            @Test
            void shouldTestLazyIterable6() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000)).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                    .containsExactly(111111, 111121, 111112, 111122);
            }

            @Test
            void shouldTestLazyIterableWithEmptyIterable6() {
                assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000)).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).isEmpty();
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> Collections.<Integer>emptyList()).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6)).isEmpty();
            }

            @Test
            void shouldTestLazyIterableFilter6() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000)).filter((t1, t2, t3, t4, t5, t6) -> t1 == 1).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                    .containsExactly(111111, 111121);
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000)).filter((t1, t2, t3, t4, t5, t6) -> false).yield((t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6))
                    .isEmpty();
            }
        }

        @Nested
        class Arity7 {
            @Test
            void shouldTestLazyIterable7() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000), (v1, v2, v3, v4, v5, v6) -> List.of(1000000)).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                    .containsExactly(1111111, 1111121, 1111112, 1111122);
            }

            @Test
            void shouldTestLazyIterableWithEmptyIterable7() {
                assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000), (v1, v2, v3, v4, v5, v6) -> List.of(1000000)).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).isEmpty();
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000), (v1, v2, v3, v4, v5, v6) -> Collections.<Integer>emptyList()).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7)).isEmpty();
            }

            @Test
            void shouldTestLazyIterableFilter7() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000), (v1, v2, v3, v4, v5, v6) -> List.of(1000000)).filter((t1, t2, t3, t4, t5, t6, t7) -> t1 == 1).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                    .containsExactly(1111111, 1111121);
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000), (v1, v2, v3, v4, v5, v6) -> List.of(1000000)).filter((t1, t2, t3, t4, t5, t6, t7) -> false).yield((t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7))
                    .isEmpty();
            }
        }

        @Nested
        class Arity8 {
            @Test
            void shouldTestLazyIterable8() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000), (v1, v2, v3, v4, v5, v6) -> List.of(1000000), (v1, v2, v3, v4, v5, v6, v7) -> List.of(10000000)).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                    .containsExactly(11111111, 11111121, 11111112, 11111122);
            }

            @Test
            void shouldTestLazyIterableWithEmptyIterable8() {
                assertThat(ForComprehension.forc(Collections.<Integer>emptyList(), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000), (v1, v2, v3, v4, v5, v6) -> List.of(1000000), (v1, v2, v3, v4, v5, v6, v7) -> List.of(10000000)).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).isEmpty();
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000), (v1, v2, v3, v4, v5, v6) -> List.of(1000000), (v1, v2, v3, v4, v5, v6, v7) -> Collections.<Integer>emptyList()).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8)).isEmpty();
            }

            @Test
            void shouldTestLazyIterableFilter8() {
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000), (v1, v2, v3, v4, v5, v6) -> List.of(1000000), (v1, v2, v3, v4, v5, v6, v7) -> List.of(10000000)).filter((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 == 1).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                    .containsExactly(11111111, 11111121);
                assertThat(ForComprehension.forc(List.of(1, 2), v1 -> List.of(10, 20), (v1, v2) -> List.of(100), (v1, v2, v3) -> List.of(1000), (v1, v2, v3, v4) -> List.of(10000), (v1, v2, v3, v4, v5) -> List.of(100000), (v1, v2, v3, v4, v5, v6) -> List.of(1000000), (v1, v2, v3, v4, v5, v6, v7) -> List.of(10000000)).filter((t1, t2, t3, t4, t5, t6, t7, t8) -> false).yield((t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8))
                    .isEmpty();
            }
        }
    }
}
