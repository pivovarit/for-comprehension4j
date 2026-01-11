# for-comprehension4j

[![ci](https://github.com/pivovarit/for-comprehension4j/actions/workflows/ci.yml/badge.svg)](https://github.com/pivovarit/for-comprehension4j/actions/workflows/ci.yml)
[![pitest](https://github.com/pivovarit/for-comprehension4j/actions/workflows/pitest.yml/badge.svg)](https://pivovarit.github.io/for-comprehension4j/)

A fluent for-comprehension API for Java, inspired by Scala’s for-expressions. It allows the composition of multiple monadic
or collection-like types in a type-safe and readable manner.

> **Note:** This project is a **proof of concept (PoC)** under active development. The current implementation focuses on `Optional` values, with
> support for both eager and lazy evaluation. More monadic/collection types with up to 8 nested values are planned.

### Example

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
