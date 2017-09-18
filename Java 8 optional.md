To avoid `NullPointerException` we always add checks to prevent null dereferences like this

```java
String version = "UNKNOWN";
if(computer != null){
  Soundcard soundcard = computer.getSoundcard();
  if(soundcard != null){
    USB usb = soundcard.getUSB();
    if(usb != null){
      version = usb.getVersion();
    }
  }
}
```

But! This decreasing the overall readability of our program.
And the check may be forgeted and it is not a quite good way to represent the absence of a value by using `null`

### `java.util.Optional<T>` calss in Java

```java
public Class Computer {
  private Optional<Soundcard> soundcard;
  public Optional<Soundcard> getSoundCard() {...}
 }
 ```
 
 Here we define a Computer, a computer might or might not have a sound card (it is optional)
 
 **How to create Optional objects**
    + an empty optional
       ```java
       Optional<Soundcard> sc = Optional.empty();
       ```
    + an Optional with a non-null value
       ```java
       SoundCard soundcard = new Soundcard();
       Optional<Soundcard> sc = Optional.of(soundcard);
       ```
       
       If soundcard were null, a NullPointerException would be immediately thrown
       
    + using ofNullable to create an Optional object that may hold a null value
    
       ```java
       Optional<Soundcard> sc = Optional.ofNullable(soundcard); 
       ```
       
 **Do Something If a Value Is Present**
 We can use the `ifPresent()` method
 
 ```java
 Optional<Soundcard> soundcard = ...;
 soundcard.ifPresent(System.out::println);
 ```
 
 **Default Values and Actions**
 
 A typical pattern is to return a default value if you determine that the result of an operation is null
 We may write this way before we know `Optional`
 
 ```java
 Soundcard soundcard = 
  maybeSoundcard != null ? maybeSoundcard 
            : new Soundcard("basic_sound_card");
 ```
 
 if we use `Optional`,we can write like that
 ```java
 Soundcard soundcard = maybeSoundcard.orElse(new Soundcard("defaut"));
 ```
 
 Here the `orElse()` method means, it will provide a default value if Optional is empty
 
 **Rejecting Certain Values Using the filter Method**
 non-optional vs optional
 ```java
USB usb = ...;
if(usb != null && "3.0".equals(usb.getVersion())){
  System.out.println("ok");
}

 ```
 ```java
 Optional<USB> maybeUSB = ...;
 maybeUSB.filter(usb -> "3.0".equals(usb.getVersion())
                    .ifPresent(() -> System.out.println("ok"));
 ```
 
 **Extracting and Transforming Values Using the map Method**
 ```java
 Optional<USB> usb = maybeSoundcard.map(Soundcard::getUSB);
 ```
 
 The value contained in maybeSoundcard is transformed by the function `Soundcard::getUSB`.
 
 Nothing will happen if maybeSoundcard (Optional) is empty
 
 **Cascading Optional Objects Using the flatMap Method**
 
 ```java
 String version = computer.getSoundcard().getUSB().getVersion();
 ```
 
 After we change to use the optional, and rewrite the code above as the fllowing, there will be an compile error
 
 ```java
 String version = computer.map(Computer::getSoundcard)
                  .map(Soundcard::getUSB)
                  .map(USB::getVersion)
                  .orElse("UNKNOWN");
 ```
 
 First, let's see the defination of the `map` method in `Optional` Class
 
 ```java
 public<U> Optional<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return Optional.ofNullable(mapper.apply(value));
        }
    }
 ```
 
  The variable computer is of type `Optional<Computer>`, so it is perfectly correct to call the map method. However, getSoundcard() returns an object of type `Optional<Soundcard>`. This means the result of the map operation is an object of type `Optional<Optional<Soundcard>>`.As a result, the call to `getUSB()` is invalid because the outermost `Optional` contains as its value another `Optional`, which of course doesn't support the `getUSB()` method
  
  To solve this problem, we use the `flatMap` method
  
  ```java
  String version = computer.flatMap(Computer::getSoundcard)
                   .flatMap(Soundcard::getUSB)
                   .map(USB::getVersion)
                   .orElse("UNKNOWN");
  ```
  
  ### Advantages of Java 8 Optional

+ Null checks are not required.
+ No more NullPointerException at run-time.
+ We can develop clean and neat APIs.
+ No more Boiler plate code
  
