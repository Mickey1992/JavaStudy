### inspect Java classes at runtime by using reflection

1. Before we can do any inspection on a class we need to obtain its java.lang.Class object.

   - if we know the name of a class at compile time, we can obtain a `Class` object like this way

   ```java
   Class myObjectClass = ClassName.class
   ```

   - if we do not know the name at compile time, but have the class name as a string at runtime

   ```java
   String className = "packageName.ClassName"; //packagename is also needed here
   Class myObjectClass = Class.forName(className);
   ```
2. Obtain the information from a `Class` object

   information we can obtain
   - class name  
   - class modifiers:    
   - package info
   - superclass   
   - implemented interfaces
   - constructors
   - methods
   - fields
   - annotations

### class name

 ```java
 String classNameWithPackage = myObjectClass.getName(); //packageName is included
 String classNameWithoutPackage = myObjectClass.getSimpleName(); //without packageName
 ```
 
### class modifiers

```java
int modifiers = myObjectClass.getModifiers()
```
The modifiers are packed into an int where each modifier is a flag bit that is either set or cleared.

```java
final Class classObject = Person.class;
int modifiers = classObject.getModifiers();

//public final class Person -> 17
//public class Person -> 1 
```
we can check the modifiers using these methods in the class java.lang.reflect.Modifier

```java
Modifier.isAbstract(int modifiers)
Modifier.isFinal(int modifiers)
Modifier.isInterface(int modifiers)
Modifier.isNative(int modifiers)
Modifier.isPrivate(int modifiers)
Modifier.isProtected(int modifiers)
Modifier.isPublic(int modifiers)
Modifier.isStatic(int modifiers)
Modifier.isStrict(int modifiers)
Modifier.isSynchronized(int modifiers)
Modifier.isTransient(int modifiers)
Modifier.isVolatile(int modifiers)
```

### package info

```java
Package objectPackage = classObject.getPackage()
```
### superclass

```java
Class superClass = classObject.getrSuperClass();
```

### implemented interfaces

```java
Class[] interfaces = classObject.getInterfaces();
```

Since a class is able to implement many interfaces, an Array of `Class` is returned.Interfaces are also represented by Class objects in Java Reflection.

NOTE: Only the interfaces specifically declared implemented by a given class is returned. If a superclass of the class implements an interface, but the class doesn't specifically state that it also implements that interface, that interface will not be returned in the array. Even if the class in practice implements that interface, because the superclass does.

### constructors
we can get a `java.lang.reflect.Constructor` instance for each `public` constructor declared in the class.

NOTE:if the modifier of a Constructor is not `public`, it will not be returned 

- obtain all `public` constructors declared in the class

   ```java
   Constructor[] constructors = classObject.getConstructors();
   ```
   
- obtain a certain `public` constructor with the precise parameter types

   ```java
   Constructor constructor = classObject.getConstructor(new Class [] {String.Class});
   ```

If no constructor matches the given constructor arguments, `NoSuchMethodException` will be thrown
   
### methods

```java
Method[] methods = classObject.getMethods();
```
### fields

```java
Field[] fields = classObject.getFields();
```

### annotations

```java
Annotation[] annotations = classObject.getAnnotations();
```
