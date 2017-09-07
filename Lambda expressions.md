### Lambda expressions
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
