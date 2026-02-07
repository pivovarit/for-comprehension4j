package com.pivovarit.forc;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ForComprehensionTest {

    @Test
    void shouldTestEager() {
        Optional<Integer> o1 = Optional.of(1);
        Optional<Integer> o2 = Optional.of(2);
        Optional<Integer> empty = Optional.empty();

        assertThat(ForComprehension.forc(o1, o2).yield(Integer::sum)).hasValue(3);
        assertThat(ForComprehension.forc(o1, empty).yield(Integer::sum)).isEmpty();
        assertThat(ForComprehension.forc(empty, o1).yield(Integer::sum)).isEmpty();
        assertThat(ForComprehension.forc(empty, empty).yield(Integer::sum)).isEmpty();
    }

    @Test
    void shouldTestLazy() {
        Optional<Integer> o1 = Optional.of(1);
        Optional<Integer> empty = Optional.empty();

        assertThat(ForComprehension.forc(o1, v1 -> Optional.of(v1)).yield(Integer::sum)).hasValue(2);
        assertThat(ForComprehension.forc(o1, v1 -> empty).yield(Integer::sum)).isEmpty();
        assertThat(ForComprehension.forc(empty, v1 -> o1).yield(Integer::sum)).isEmpty();
        assertThat(ForComprehension.forc(empty, v1 -> empty).yield(Integer::sum)).isEmpty();
    }

    @Test
    void shouldTestEagerStream() {
        Stream<Integer> s1 = Stream.of(1, 2);
        Stream<Integer> s2 = Stream.of(10, 20);

        assertThat(ForComprehension.forc(s1, s2).yield(Integer::sum))
            .containsExactly(11, 21, 12, 22);
    }

    @Test
    void shouldTestEagerStreamWithEmptyStream() {
        assertThat(ForComprehension.forc(Stream.<Integer>empty(), Stream.of(1, 2)).yield(Integer::sum))
            .isEmpty();
        assertThat(ForComprehension.forc(Stream.of(1, 2), Stream.<Integer>empty()).yield(Integer::sum))
            .isEmpty();
    }

    @Test
    void shouldTestLazyStream() {
        assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.of(v1 * 10, v1 * 20)).yield(Integer::sum))
            .containsExactly(11, 21, 22, 42);
    }

    @Test
    void shouldTestLazyStreamWithEmptyStream() {
        assertThat(ForComprehension.forc(Stream.<Integer>empty(), v1 -> Stream.of(v1)).yield(Integer::sum))
            .isEmpty();
        assertThat(ForComprehension.forc(Stream.of(1, 2), v1 -> Stream.<Integer>empty()).yield(Integer::sum))
            .isEmpty();
    }
}
