### How to create a class

```javascript
function QNode(raw)
{
  this.raw = raw;
}
```

### How to create an instance

```javascript
//javascript is not a strong-typed language
var node = new QNode(document.querySelector("title"));
console.info(node.raw.textContent);
```

### How to add an instance method/property

```javascript
QNode.prototype.instanceProperty = "abc";
QNode.prototype.printContent = function()
{
  console.info(this.raw.textContent);
  //this will report error, you cannot omit this
  console.info(raw.content);
}

node.printContent();
```
### How to add a static method/property

```javascript
QNode.staticProperty = "abc";
QNode.staticMethod = function()
{
  //this will report error, because this is static method, and static method cannot access instance property
  console.info(this.raw.textContent);
}

console.info(QNode.staticProperty)
QNode.staticMethod();
```

### A better way to create static method property

The way we introduced above has two disadvantages:

1. You have to always write the class name when you access them, this is very annoying
2. You cannot may static method/property private

To overcome them, you need to introduce another technique called `anonymous method`:

```javascript
//a normal method
function test() {
  //do something
}

//a method may not have a name
var test = function() {
  //do something
}
test();

//What if we do not assign an anonymous method to a variable, but invoke it directly
(function() {
  console.info("It works!");
})()
```

Now we can define static method/property like this:

```javascript
(function() {
  var staticProperty = "abc";
  function staticMethod()
  {
    //this will report error, because this is static method, and static method cannot access instance property
    console.info(this.raw.textContent);
  }
  
  function QNode(raw)
  {
    this.raw = raw;
  }
  QNode.prototype.printContent = function()
  {
    console.info(this.raw.textContent);
    //this will report error, you cannot omit this
    console.info(raw.content);
  }
  
  console.info(staticProperty)
  staticMethod();
  
  window.QNode = QNode;
})();

//When you access a method/property
test();
//It'll try to find that method/property on a built-in object: window
//so the statement above is equivalent to:
window.test();

var node = new QNode();
//It's equivalent to:
var node = new window.QNode();
```

### Inheritance in javascript

```javascript  
function QNode(raw)
{
  this.raw = raw;
}
QNode.prototype.printContent = function()
{
  console.info(this.raw.textContent);
}

function QButton(raw) {
  //I'll explain call later, for now, you only have to know, it's similar to `super(raw)` in java
  QNode.call(this, raw);
}

QButton.prototype = Object.create(QNode.prototype);
QButton.prototype.constructor = QButton;

var button = new QButton(document.querySelector("title"))
button.printContent()
```

### Anonymous class

In java, we can define anonymous class in this way:

```java
TestInterface instance = new TestInterface() {
   public void test() {
    //do something
   }
};
```

In javascript, it's much easier to do the same thing:

```javascript
var instance = {
  value: "I'm value",
  testMethod: function() {
    console.info(this.value);
  }
}
//This will print "I'm value"
instance.testMethod();
```

### `this` keyword

`this` is very tricky in javascript. Its content can easily change. By default, it points to its instance. But we can change it in following ways:

**Use `call` or `apply` method**

```javascript
function test(arg1, arg2) {
   console.info(this.value, arg1, arg2);
}
//This will print: undefined a b
test("a", "b");

var o = {value: "I'm not empty!"}
//This will print: I'm not empty! a b
test.call(o, "a", "b");
//This will print: I'm not empty! a b
test.apply(o, ["a", "b"]);
```

**Use `bind` method**

```javascript
function test(arg1, arg2) {
   console.info(this.value, arg1, arg2);
}
//This will print: undefined a b
test("a", "b");

var o = {value: "I'm not empty!"}
var boundTest = test.bind(o);
//This will print: I'm not empty! a b
boundTest("a", "b");
//This will print: undefined a b
test("a", "b");
```
