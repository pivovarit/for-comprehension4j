# for-comprehension4j

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

A fluent for-comprehension API for Java, inspired by Scala’s for-expressions. It allows composition of multiple monadic
or collection-like types in a type-safe and readable manner.

> **Note:** This project is a **proof of concept (PoC)** under active development. The current implementation focuses on `Optional` values, with
> support for both eager and lazy evaluation. More monadic/collection types with up to 8 nested values are planned.

### Example

```java
Optional<Integer> width = Optional.of(3);
Optional<Integer> height = Optional.of(4);

Optional<Integer> area = forc(
  width, 
  height
  ).yield((w, h) -> w * h);

// area -> Optional[12]
```

```
Optional<Integer> width = Optional.of(3);

Optional<Integer> area = ForComprehension.forc(
    width, 
    w -> Optional.of(w + 1)
  ).yield((w, h) -> w * h);

// area -> Optional[12]
```
