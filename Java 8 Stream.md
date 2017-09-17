### What is Stream
A stream represents a sequence of elements and supports different kind of operations to perform computations upon those elements.

Two kinds of Stream Operations
+ intermediate peration: return a stream, and we can chain multiple intermediate operations without using semicolons(several statements -> one)
+ terminal operation: void or return a non-stream type result

Most stream operations accept some kind of lambda expression parameter, a functional interface specifying the exact behavior of the operation. Most of those operations must be both non-interfering and stateless.
+ A function is non-interfering when it does not modify the underlying data source of the stream
+ A function is stateless when the execution of the operation is deterministic


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
//the output is "class java.lang.Integer" * 2
IntStream.range(1, 3).mapToObj(Integer::new).map(s -> s.getClass()).forEach(System.out::println);

//the output is "class java.lang.Integer" * 3
Stream.of(1, 2, 3).map(s -> s.getClass()).forEach(System.out::println);

//compile error:Cannot invoke getClass() on the primitive type int
//this can prove that the elements are not changed to Integer
IntStream.range(1, 3).map(s -> s.getClass()).forEach(System.out::println);
```



### Processing Order
**Intermediate operations will only be excuted when a terminal operation is present**

Without terminal operation

```java
Stream.of("a", "b", "c")
	.filter(s -> {
		System.out.println("filter " + s);
		return true;
	});
System.out.println("end of program");
```
only "end of program" is printed to the console 

Add a terminal operation `forEach`

```java
Stream.of("a", "b", "c")
	.filter(s -> {
		System.out.println("filter " + s);
		return true;
	})
	.forEach(s -> System.out.println("forEach " + s));
System.out.println("end of program");
```

The Output is
```java
filter a
forEach a
filter b
forEach b
filter c
forEach c
end of program
```
**Each element moves along the chain vertically.**

But not execute the operations horizontally one after another on all elements of the stream.

We can reduce the actual number of operations performed on each element
```java
Stream.of("a1", "b1", "b2")
	.filter(s -> {
		System.out.println("filter " + s);
		return true;
	})
	.anyMatch(s -> {
		System.out.println("anyMatch " + s);
		return s.startsWith("b");
	});
System.out.println("end of program");

//filter a1
//anyMatch a1
//filter b1
//anyMatch b1
//end of program
```

**the `sorted`operation**
- it is a intermediate operation
- it is a stateful operation (since in order to sort a collection of elements you have to maintain state during ordering)
- it is executed on the entire input collection (see next example)
- it is never been called if the input collection has only one element

```java
Stream.of("d2", "a2", "b1", "b3", "c")
    .sorted((s1, s2) -> {
        System.out.printf("sort: %s; %s\n", s1, s2);
        return s1.compareTo(s2);
    })
    .filter(s -> {
        System.out.println("filter: " + s);
        return s.startsWith("a");
    })
    .map(s -> {
        System.out.println("map: " + s);
        return s.toUpperCase();
    })
    .forEach(s -> System.out.println("forEach: " + s));
    
//sort:    a2; d2
//sort:    b1; a2
//sort:    b1; d2
//sort:    b1; a2
//sort:    b3; b1
//sort:    b3; d2
//sort:    c; b3
//sort:    c; d2
//filter:  a2
//map:     a2
//forEach: A2
//filter:  b1
//filter:  b3
//filter:  c
//filter:  d2
```

### How to reuse a stream
As soon as a terminal operation is called, the Stream is closed.

=We can not ask a Stream which has already got to the terminate station go back to a middle station .

```java
Stream<String> stream = Stream.of("a", "b", "c");
stream.forEach(System.out::println); //ok
stream.forEach(s -> System.out.println("reuse: " + s)); //exception
```

Here is the result of the code above

```java
a
b
c
Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
	at java.util.stream.AbstractPipeline.sourceStageSpliterator(AbstractPipeline.java:279)
	at java.util.stream.ReferencePipeline$Head.forEach(ReferencePipeline.java:580)
	at test.Demo.main(Demo.java:9)
```

To solve this problem, we can create a stream `Supplier` to construct a new stream with all intermediate operations already set up.

**The `Supplier` interface**
- a function interfance
- it takes no arguement, and returns some value by calling its `get()` method

**The `Consumer` interface**
- a function interfance
- it does the opposite of the Supplier
- it takes a single arguement, and doesn't return anything by calling its `accept()` method

```java
Supplier<Stream<String>> streamSupplier = () -> Stream.of("a", "b", "c");
streamSupplier.get().forEach(System.out::println); //ok
streamSupplier.get().forEach(s -> System.out.println("reuse: " + s)); //ok
```
the code above is equivalent to

```java
Stream.of("a", "b", "c").forEach(System.out::println);
Stream.of("a", "b", "c").forEach(s -> System.out.println("reuse: " + s));
```
Here, in fact, we create two streams.
While in the NG example,only one stream is created.

### Advanced Operations
For all the operations supported by Stream: [Stream Javadoc](http://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html)

**Collect**
- a terminal operation
- is used to transform the elements of the stream into a different kind of result(List, Set, Map...)
- accepts a `Collector`which consists of four different operations: a supplier, an accumulator, a combiner and a finisher. (Java 8 supports various built-in collectors via the `Collectors` class)

Construct a List/Set from Stream
```java
Supplier<Stream<String>> streamSupplier = () -> Stream.of(1, 2, 3, 3)
						.map(s -> "a" + s);
List<String> strNumsList = streamSupplier.get().collect(Collectors.toList());
Set<String> strNumsSet = streamSupplier.get().collect(Collectors.toSet());

System.out.println("List: " + strNumsList);
System.out.println("Set: " + strNumsSet);

//List: [a1, a2, a3, a3]
//Set: [a1, a2, a3]
```





