package com.pivovarit.forc;

import org.junit.jupiter.api.Test;

import java.util.Optional;

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
}
