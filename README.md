# for-comprehension4j

[![ci](https://github.com/pivovarit/for-comprehension4j/actions/workflows/ci.yml/badge.svg)](https://github.com/pivovarit/for-comprehension4j/actions/workflows/ci.yml)
[![pitest](https://github.com/pivovarit/for-comprehension4j/actions/workflows/pitest.yml/badge.svg)](https://pivovarit.github.io/for-comprehension4j/)

A fluent for-comprehension API for Java, inspired by Scala's for-expressions. It allows the composition of multiple monadic
or collection-like types in a type-safe and readable manner.

> **Note:** This project is a **proof of concept (PoC)** under active development. The current implementation supports
> `Optional`, `Stream`, and `Iterable`, with both **eager** and **lazy** evaluation.

## Usage

All examples assume:

```java
import static com.pivovarit.forc.ForComprehension.forc;
```

### Optional

Instead of nesting multiple `flatMap` and `map` calls, you can use `forc` to simulate a _for-comprehension_.

**Eager** (all inputs are provided upfront):

```java
Optional<Integer> width = Optional.of(3);
Optional<Integer> height = Optional.of(4);

Optional<Integer> area = forc(width, height)
  .yield((w, h) -> w * h);

// area -> Optional[12]
```

**Lazy** (later values can depend on earlier ones):

```java
Optional<Integer> width = Optional.of(3);

Optional<Integer> area = forc(
    width,
    w -> Optional.of(w + 1)
  ).yield((w, h) -> w * h);

// area -> Optional[12]
```

### Stream

The same approach works with `Stream` values, producing a cartesian product of all elements.

**Eager**:

```java
Stream<Integer> s1 = Stream.of(1, 2);
Stream<Integer> s2 = Stream.of(10, 20);

Stream<Integer> result = forc(s1, s2)
  .yield(Integer::sum);

// result -> [11, 21, 12, 22]
```

**Lazy** (the second stream can depend on elements from the first):

```java
Stream<Integer> result = forc(
    Stream.of(1, 2),
    v1 -> Stream.of(v1 * 10, v1 * 20)
  ).yield(Integer::sum);

// result -> [11, 21, 22, 42]
```

> **Note on `Stream`:** streams are single-use. In the eager `Stream` variant, later streams may be materialized internally
> to support generating the cartesian product reliably. If you need fully lazy behavior end-to-end, use the lazy overload.

### Iterable

`Iterable` works similarly, producing a cartesian product and returning a `List<R>`.

**Eager**:

```java
Iterable<Integer> i1 = List.of(1, 2);
Iterable<Integer> i2 = List.of(10, 20);

List<Integer> result = forc(i1, i2)
  .yield(Integer::sum);

// result -> [11, 21, 12, 22]
```

**Lazy**:

```java
List<Integer> result = forc(
    List.of(1, 2),
    v1 -> List.of(v1 * 10, v1 * 20)
  ).yield(Integer::sum);

// result -> [11, 21, 22, 42]
```
