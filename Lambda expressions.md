## Lambda expressions
Lamada Expression comes from anonymous objects

### anonymous objects
An anonymous class is an expression. 
The syntax of an anonymous class expression is like the invocation of a constructor, except that there is a class definition contained in a block of code.

The anonymous class expression consists of the following:

- The new operator
- The name of an interface to implement or a class to extend. In this example, the anonymous class is implementing the interface HelloWorld.
- Parentheses that contain the arguments to a constructor, just like a normal class instance creation expression. Note: When you implement an interface, there is no constructor, so you use an empty pair of parentheses, as in this example.
- A body, which is a class declaration body. More specifically, in the body, method declarations are allowed but statements are not

```java
  HelloWorld frenchGreeting = new HelloWorld() {
            String name = "tout le monde";
            public void greet() {
                greetSomeone("tout le monde");
            }
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Salut " + name);
            }
        };
```

### Lambda expressions
A simple example of how to sort a list of strings in prior versions of Java

```java
  List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

  Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return b.compareTo(a);
    }
  });
```
A shorter ver by using lamada expressions

```java
  Collections.sort(names, (String a, String b) -> {
    return b.compareTo(a);
  });
```
For one line method bodies you can skip both the braces {} and the return keyword

```java
  Collections.sort(names, (String a, String b) -> b.compareTo(a));
```

And then,since the java compiler is aware of the parameter types so you can skip them as well

```java
  Collections.sort(names, (a, b) -> b.compareTo(a));
```

### Functional Interface
Each lambda corresponds to a given type, specified by an interface. 
A so called functional interface must contain exactly one abstract method declaration. 
Each lambda expression of that type will be matched to this abstract method. 
Since default methods are not abstract you're free to add default methods to your functional interface.

To ensure that your interface meet the requirements, you should add the `@FunctionalInterface` annotation. The compiler is aware of this annotation and throws a compiler error as soon as you try to add a second abstract method declaration to the interface.

### Method References
- Reference to a static method `ContainingClass::staticMethodName`
- Reference to an instance method of a particular object `containingObject::instanceMethodName`
- Reference to an instance method of an arbitrary object of a particular type `ContainingType::methodName`
- Reference to a constructor `ClassName::new`

### Lambda Scopes
- Accessing local variables:
  t\The local must be implictly.(declared final or never be changed)
  And only have the read access but no write access

- Accessing fields and static variables:
  have both read and write access

- Accessing Default Interface Methods:
  Default methods cannot be accessed from within lambda expressions.
