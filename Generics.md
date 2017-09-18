### Two kinds of bugs

+ Compile-time bugs

   bugs detected at compile time

   we can use the compiler's error messages to figure out what the problem is and fix it, right then and there

+ Runtime bugs
 
    bugs happened at runtime
 
    it may be at a point in the program that is far removed from the actual cause of the problem
    
    
### Some Examples of why we use generics

The cast on line 3 is essential, since the compiler can only guarantee that an Object will be returned by the `get()` method 

Although the programmer knows the type of objects stored in the list, the compiler doesn't know.

```java
List list = new ArrayList();
list.add("hello");
String s = (String) list.get(0);
```

In the code above, we put some `String` elements in the list.
How about putting an `Person` into the list

```java
List list = new ArrayList();
list.add("hello");
list.add(new Person("name", 18, "city"));
String s1 = (String) list.get(0);
String s2 = (String) list.get(1);

//no compile error
//Exception in thread "main" java.lang.ClassCastException: test.Person cannot be cast to java.lang.String
	at test.Demo.main(Demo.java:25)
```

What if programmers could actually express their intent, and mark a list as being restricted to contain a particular data type?

```java
List<String> list = new ArrayList();
list.add("hello");

//compile error: The method add(String) in the type List<String> is not applicable for the arguments (Person)
```

### Generics

generics enable types (classes and interfaces) to be parameters when defining classes, interfaces and methods. 

**Advantages of using generic**

+ Stronger type checks at compile time
   
   A Java compiler applies strong type checking to generic code and issues errors if the code violates type safety. Fixing compile-time errors is easier than fixing runtime errors, which can be difficult to find.

+ Elimination of casts
+ Enabling programmers to implement generic algorithms.

**How to define a generic class**
```java
class ClassName<T1, T2, ..., Tn> {...}
```

`T1, T2, ..., Tn` are type parameters. A type variable can be any **non-primitive** type you specify

By convention, type parameter names are single, uppercase letters.
Without this convention, it would be difficult to tell the difference between a type variable and an ordinary class or interface name.

The most commonly used type parameter names are:

- E - Element (used extensively by the Java Collections Framework)
- K - Key
- N - Number
- T - Type
- V - Value
- S,U,V etc. - 2nd, 3rd, 4th types

**Invoking and Instantiating a Generic Type**

a generic type invocation is similar to an ordinary method invocation, but instead of passing an argument to a method, you are passing a type argument 

```java
//a generic Box class is defined 
class Box<T> {...}

//a generic type invocation
Box<Integer> integerBox;

//create an instantiate
Box<Integer> integerBox = new Box<Integer>();

//the type arguements required to invoke the constructor of a generic class can be omitted 
//"<>"is called the diamond
Box<Integer> integerBox = new Box<>();
```

we can also substitute a type parameter with a parameterized type
```java
OrderedPair<String, Box<Integer>> p = new OrderedPair<>("primes", new Box<Integer>(...));
``

### Raw Types

A raw type is the name of a generic class or interface without any type arguments.

```java
//a generic Box class is defined 
class Box<T> {...}

Box rawBox = newBox();
````

The point is that, you defined a generic class, and you don't pass type arguements to it we you invoke it.
So, a non-generic class or interface type is not a raw type.
