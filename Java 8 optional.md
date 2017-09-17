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
