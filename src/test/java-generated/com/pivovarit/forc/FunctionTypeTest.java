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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FunctionTypeTest {

      @Test
      void shouldAndThen1() {
          Function1<Integer, Integer> f = t1 -> t1;
          assertThat(f.andThen(r -> r * 2).apply(1)).isEqualTo(2);
      }

      @Test
      void shouldAndThenThrowOnNull1() {
          Function1<Integer, Integer> f = t1 -> t1;
          assertThatThrownBy(() -> f.andThen(null)).isInstanceOf(NullPointerException.class);
      }

      @Test
      void shouldAndThen2() {
          Function2<Integer, Integer, Integer> f = (t1, t2) -> t1 + t2;
          assertThat(f.andThen(r -> r * 2).apply(1, 2)).isEqualTo(6);
      }

      @Test
      void shouldAndThenThrowOnNull2() {
          Function2<Integer, Integer, Integer> f = (t1, t2) -> t1 + t2;
          assertThatThrownBy(() -> f.andThen(null)).isInstanceOf(NullPointerException.class);
      }

      @Test
      void shouldAndThen3() {
          Function3<Integer, Integer, Integer, Integer> f = (t1, t2, t3) -> t1 + t2 + t3;
          assertThat(f.andThen(r -> r * 2).apply(1, 2, 3)).isEqualTo(12);
      }

      @Test
      void shouldAndThenThrowOnNull3() {
          Function3<Integer, Integer, Integer, Integer> f = (t1, t2, t3) -> t1 + t2 + t3;
          assertThatThrownBy(() -> f.andThen(null)).isInstanceOf(NullPointerException.class);
      }

      @Test
      void shouldAndThen4() {
          Function4<Integer, Integer, Integer, Integer, Integer> f = (t1, t2, t3, t4) -> t1 + t2 + t3 + t4;
          assertThat(f.andThen(r -> r * 2).apply(1, 2, 3, 4)).isEqualTo(20);
      }

      @Test
      void shouldAndThenThrowOnNull4() {
          Function4<Integer, Integer, Integer, Integer, Integer> f = (t1, t2, t3, t4) -> t1 + t2 + t3 + t4;
          assertThatThrownBy(() -> f.andThen(null)).isInstanceOf(NullPointerException.class);
      }

      @Test
      void shouldAndThen5() {
          Function5<Integer, Integer, Integer, Integer, Integer, Integer> f = (t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5;
          assertThat(f.andThen(r -> r * 2).apply(1, 2, 3, 4, 5)).isEqualTo(30);
      }

      @Test
      void shouldAndThenThrowOnNull5() {
          Function5<Integer, Integer, Integer, Integer, Integer, Integer> f = (t1, t2, t3, t4, t5) -> t1 + t2 + t3 + t4 + t5;
          assertThatThrownBy(() -> f.andThen(null)).isInstanceOf(NullPointerException.class);
      }

      @Test
      void shouldAndThen6() {
          Function6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> f = (t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6;
          assertThat(f.andThen(r -> r * 2).apply(1, 2, 3, 4, 5, 6)).isEqualTo(42);
      }

      @Test
      void shouldAndThenThrowOnNull6() {
          Function6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> f = (t1, t2, t3, t4, t5, t6) -> t1 + t2 + t3 + t4 + t5 + t6;
          assertThatThrownBy(() -> f.andThen(null)).isInstanceOf(NullPointerException.class);
      }

      @Test
      void shouldAndThen7() {
          Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> f = (t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7;
          assertThat(f.andThen(r -> r * 2).apply(1, 2, 3, 4, 5, 6, 7)).isEqualTo(56);
      }

      @Test
      void shouldAndThenThrowOnNull7() {
          Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> f = (t1, t2, t3, t4, t5, t6, t7) -> t1 + t2 + t3 + t4 + t5 + t6 + t7;
          assertThatThrownBy(() -> f.andThen(null)).isInstanceOf(NullPointerException.class);
      }

      @Test
      void shouldAndThen8() {
          Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> f = (t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8;
          assertThat(f.andThen(r -> r * 2).apply(1, 2, 3, 4, 5, 6, 7, 8)).isEqualTo(72);
      }

      @Test
      void shouldAndThenThrowOnNull8() {
          Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> f = (t1, t2, t3, t4, t5, t6, t7, t8) -> t1 + t2 + t3 + t4 + t5 + t6 + t7 + t8;
          assertThatThrownBy(() -> f.andThen(null)).isInstanceOf(NullPointerException.class);
      }
}
