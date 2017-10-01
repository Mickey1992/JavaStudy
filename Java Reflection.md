## inspect Java classes at runtime by using reflection

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

## class name

 ```java
 String classNameWithPackage = myObjectClass.getName(); //packageName is included
 String classNameWithoutPackage = myObjectClass.getSimpleName(); //without packageName
 ```
 
## class modifiers

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

## package info

```java
Package objectPackage = classObject.getPackage()
```
## superclass

```java
Class superClass = classObject.getrSuperClass();
```

## implemented interfaces

```java
Class[] interfaces = classObject.getInterfaces();
```

Since a class is able to implement many interfaces, an Array of `Class` is returned.Interfaces are also represented by Class objects in Java Reflection.

NOTE: Only the interfaces specifically declared implemented by a given class is returned. If a superclass of the class implements an interface, but the class doesn't specifically state that it also implements that interface, that interface will not be returned in the array. Even if the class in practice implements that interface, because the superclass does.

## constructors
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

- **Instantiating Objects using Constructor Object**

   ```java
      Constructor constructor = classObject.getConstructor(new Class [] {String.Class});
      ClasName newObject = (ClassName) constructor.newInstance(paramaters);
      
      //the example of using Person Class
      Class classObject = Person.class;
	   Constructor constructor = classObject.getConstructor(new Class[] { String.class, int.class, String.class });
	   Person newPerson = (Person) constructor.newInstance("City", 20, "name");
   ```
   
## methods

we can get a `java.lang.reflect.Method` instance for each `public` method declared in the class.

- obtain all `public` methods declared in the class

```java
Method[] methods = classObject.getMethods();
```

- obtain a certain `public` method with the methodName and precise parameter types

```java
Method method = classObject.getMethod("methodName", new Class [] {String.Class});
```

If no method matches the given method name and arguments,a `NoSuchMethodException` will be thrown 

- we can use the `getParameterTypes()` method  to get the types of a method Parameters 
- we can use the `getReturnType()` method  to get the return type of a method

- **Invoking Methods using Method Object**

```java
Method method = classObject.getMethod("methodName", new Class [] {String.Class});

Object returnValue = method.invoke(instanceName, parameters)
```

**Note**
   
   if the method is a static method, we can pass a `null` instead of a `instanceName`

## fields
we can get a `java.lang.reflect.Field` instance for each `public` field declared in the class.

- obtain all `public` fields declared in the class

```java
Field[] fields = classObject.getFields();
```

- obtain a certain `public` field with its name

```java
Field[] fields = classObject.getField("fieldName");
```

If no field exists with the name given as parameter to the getField() method, a `NoSuchFieldException` will be thrown.

- we can use the `getName()` method  to get the field name
- we can use the `getType()` method  to get the field type

- **getting and setting field values**

   I have create a `Person` object as following
   
   ```java
   Person aPerson = new Person("name1", 29, "city1");
   ```
   
   - get the field value(using `field.get(instanceName)`)
   
   ```java
	Class classObject = Person.class;
	Field fieldCity = classObject.getField("city");
      
     	Object cityValue = fieldCity.get(aPerson);
	System.out.println(cityValue.toString()); //city1
   ```
   
   - set the field value(using `field.get(instanceName, value)`)
   
   ```java
  	Class classObject = Person.class;
	final Field fieldCity = classObject.getField("city");

	fieldCity.set(aPerson, "city2");
	System.out.println(aPerson.getCity()); //city2
   ```

   **Note**
   
   if the field is a static field, we can pass a `null` instead of a `instanceName` 
   
## Accessing the private filed/methods

### Accessing the private field

1. we can use `classObject.getDeclaredField(String name)` and `classObject.getDeclaredFields()` method to get all the fields(not only the `public` fields) 

2. call the `privateField.setAccessible(true)`method on the private field to turn off the access checks. (this only worls in reflection)

### Accessing the private method

1. we can use `classObject.getDeclaredMethod("methodName", new Class[] {parameterTypes})` and `classObject.getDeclaredMethod()` method to get all the fields(not only the `public` fields) 

2. call the `privateMethod.setAccessible(true)`method on the private field to turn off the access checks. (this only worls in reflection)

## annotations

```java
Annotation[] annotations = classObject.getAnnotations();
```

## The Person Class I defined

```java
  public class Person {
	public String city;
	private String name;
	private int age;

	public Person() {
		this.city = "";
		this.name = "";
		this.age = 0;
	}

	public Person(final String name, final int age, final String city) {
		this.name = name;
		this.age = age;
		this.city = city;
	}

	@Override
	public String toString() {
		return name + " " + age;
	}

	public String getCity() {
		return city;
	}

	public int getAge() {
		return age;
	}
}
```


## Generics

I defined an `Apple` Class

```java
public class Apple {

	public static List<Apple> apples = new LinkedList<>();

	public int weight;

	public void grow() {
		this.weight += 2;
	}

	public static int countApples() {
		return apples.size();
	}

	public static void addApple(final Apple apple) {
		apples.add(apple);
	}

	public List<Apple> getAllApples() {
		return apples;
	}
}
```

First,see the difference between `getGenericReturnType()` and `getReturnType()` method on a `Method` object

```java
final Class classObject = Apple.class;
final Method method = classObject.getMethod("getAllApples", null);
final Type returnType = method.getGenericReturnType();
System.out.println(returnType.toString()); //java.util.List<test.Apple>
System.out.println(method.getReturnType());//interface java.util.List
```

**Generic Method Return Types**

```java
final Class classObject = Apple.class;
final Method method = classObject.getMethod("getAllApples", null);
final Type returnType = method.getGenericReturnType();
System.out.println(returnType.toString()); /java.util.List<test.Apple>

if (returnType instanceof ParameterizedType) {
	final ParameterizedType type = (ParameterizedType) returnType;
	final Type[] typeArguments = type.getActualTypeArguments();
	for (final Type typeArgument : typeArguments) {
		final Class typeArgClass = (Class) typeArgument;
		System.out.println("typeArgClass = " + typeArgClass);
	}
}
//typeArgClass = class test.Apple
```

**Generic Method Parameter Types**

Similar to the method to obtain the returnType, we can use `getGenericParameterTypes()` or `getParameterTypes()` to obtain the ParameterTypes of a method

**Generic Field Types**

Similar to the method to obtain the returnType, we can use `getGenericType()` or `getType()` to obtain the type of a field
