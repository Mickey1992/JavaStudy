## What is Java annotations

Java annotations are used to provide meta data for your Java code. 
Being meta data, Java annotations do not directly affect the execution of your code, although some types of annotations can actually be used for that purpose.

Purposes

- Compiler instructions
- Build-time instructions
- Runtime instructions

## Annotation Basics

A Java annotation looks like

```java
//annotation without any elements
@AnnotationName 

//annotation with some elements
@AnnotationName(elementName1 = "value1", elementName2 = "value2", ...)

//annotation with only one element
@AnnotationName("value")
```

Java annotations can be placed above classes, interfaces, methods, method parameters, fields and local variables.

```java
@Entity
public class Vehicle {

    @Persistent
    protected String vehicleName = null;


    @Getter
    public String getVehicleName() {
        return this.vehicleName;
    }

    public void setVehicleName(@Optional vehicleName) {
        this.vehicleName = vehicleName;
    }

    public List addVehicleNameToList(List names) {

        @Optional
        List localNames = names;

        if(localNames == null) {
            localNames = new ArrayList();
        }
        localNames.add(getVehicleName());

        return localNames;
    }

}
```

## Built-in Java Annotations

Java comes with three built-in annotations which are used to give the Java compiler instructions. 

- `@Deprecated`

it is used to mark a class, method or field as deprecated, meaning it should no longer be used. 
If your code uses deprecated classes, methods or fields, the compiler will give you a warning. 

When you use the @Deprecated annotation, it is a good idea to also use the corresponding @deprecated JavaDoc symbol, and explain why the class, method or field is deprecated, and what the programmer should use instead. 

```java
@Deprecated
/**
  @deprecated Use MyNewComponent instead.
*/
public class MyComponent {
}
```
- `@Override`

It is used above methods that override methods in a superclass. 
If the method does not match a method in the superclass, the compiler will give you an error.

- `@SuppressWarnings`

It makes the compiler suppress `warnings` (not errors) for a given **method**.
