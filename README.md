# for-comprehension4j

[![ci](https://github.com/pivovarit/for-comprehension4j/actions/workflows/ci.yml/badge.svg)](https://github.com/pivovarit/for-comprehension4j/actions/workflows/ci.yml)
[![pitest](https://github.com/pivovarit/for-comprehension4j/actions/workflows/pitest.yml/badge.svg)](https://pivovarit.github.io/for-comprehension4j/)

A fluent for-comprehension API for Java, inspired by Scala's for-expressions. It allows the composition of multiple monadic
or collection-like types in a type-safe and readable manner.

> **Note:** This project is a **proof of concept (PoC)** under active development. The current implementation supports `Optional` and `Stream` values, with
> both eager and lazy evaluation. More monadic/collection types with up to 8 nested values are planned.

### Optional

Instead of nesting multiple `flatMap` and `map` calls, you can use the `forc` method that simulates a _for-comprehension_:

```java
Optional<Integer> width = Optional.of(3);
Optional<Integer> height = Optional.of(4);

Optional<Integer> area = forc(
  width,
  height
  ).yield((w, h) -> w * h);

// area -> Optional[12]
```

```java
Optional<Integer> width = Optional.of(3);

Optional<Integer> area = forc(
    width,
    w -> Optional.of(w + 1)
  ).yield((w, h) -> w * h);

// area -> Optional[12]
```

### Stream

The same approach works with `Stream` values, producing a cartesian product of all elements:

```java
Stream<Integer> s1 = Stream.of(1, 2);
Stream<Integer> s2 = Stream.of(10, 20);

Stream<Integer> result = forc(s1, s2)
  .yield(Integer::sum);

// result -> [11, 21, 12, 22]
```

With lazy evaluation, the second stream can depend on elements from the first:

```java
Stream<Integer> result = forc(
    Stream.of(1, 2),
    v1 -> Stream.of(v1 * 10, v1 * 20)
  ).yield(Integer::sum);

// result -> [11, 21, 22, 42]
```
