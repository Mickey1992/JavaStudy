### What is Stream
A stream represents a sequence of elements and supports different kind of operations to perform computations upon those elements.

Two kinds of Stream Operations
+ intermediate peration: return a stream, and we can chain multiple intermediate operations without using semicolons(several statements -> one)
+ terminal operation: void or return a non-stream type result

### How to create a Stream
**Call the `Stream()`method on a collection**

```java
 List<String> myList = Arrays.asList("aa", "bc", "ac");
		myList.stream()
```

**Use `Stream.of()`to create a stream from a bunch of object references**

```java
Stream.of("aa", "bc", "ac");
```

**Create primitive stream**

e.g. IntStream

```java
IntStream.range(1, 4)
    .forEach(System.out::println);

// 1
// 2
// 3
```
also there are LongStream, DoubleStream

**Transform a regular object stream to a primitive stream**

```java
Stream.of("1", "2", "3")
  .mapToInt(Integer::parseInt)
  .average()
  .ifPresent(System.out::println);
```

also,there are mapToLong, mapToDouble

**Transform a primitive stream to a regular object stream**
do this by using `mapToObj()`

```java
//the output is "class java.lang.Integer" * 3
IntStream.range(1, 3).mapToObj(Integer::new).map(s -> s.getClass()).forEach(System.out::println);

//the output is "class java.lang.Integer" * 3
Stream.of(1, 2, 3).map(s -> s.getClass()).forEach(System.out::println);

//compile error, because int is not an object type, we can not get its class
//this can prove that the elements are not changed to Integer
IntStream.range(1, 3).map(s -> s.getClass()).forEach(System.out::println);
```

