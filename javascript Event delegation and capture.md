### Bubbling principle

When an event happens on an element, it first runs the handlers on it, then on its parent, then all the way up on other ancestors.

```html
<style>
  body * {
    margin: 10px;
    border: 1px solid blue;
  }
</style>

<form onclick="alert('form')">FORM
  <div onclick="alert('div')">DIV
    <p onclick="alert('p')">P</p>
  </div>
</form>
```
A click on the inner `<p>` first runs onclick:

1. On that `<p>`.
2. Then on the outer `<div>`
3. Then on the outer `<form>`
4. And so on upwards till the document object.

**A handler on a parent element can always get the details about where it actually happened.**

+ `event.target` – is the “target” element that initiated the event, it doesn’t change through the bubbling process.
+ `this`(`event.currentTarget`) – is the “current” element, the one that has a currently running handler on it.

```HTML
<form onclick="alert('target: '+ event.target.tagName + ' this: ' + this.tagName)">FORM
  <div onclick="alert('target: '+ event.target.tagName + ' this: ' + this.tagName)">DIV
    <p onclick="alert('target: '+ event.target.tagName + ' this: ' + this.tagName)">P</p>
  </div>
</form>
```

A click on the inner <p>
1. target: P this: P
2. target: P this: DIV
3. target: P this: FORM

**Stop Bubbling**

any handler may decide that the event has been fully processed and stop the bubbling by using `event.stopPropagation()`

```HTML
<form onclick="alert('target: '+ event.target.tagName + ' this: ' + this.tagName)">FORM
  <div onclick="{alert('target: '+ event.target.tagName + ' this: ' + this.tagName);event.stopPropagation()}">DIV
    <p onclick="alert('target: '+ event.target.tagName + ' this: ' + this.tagName)">P</p>
  </div>
</form>
```

A click on the inner <p>
1. target: P this: P
2. target: P this: DIV
<del>3. target: P this: FORM

`event.stopPropagation()` stops the move upwards, but on the current element all other handlers will run.
To stop the bubbling and prevent handlers on the current element from running, there’s a method `event.stopImmediatePropagation()`. After it no other handlers execute.

### capturing

3 phases of event propagation

1. Capturing phase – the event goes down to the element. (To catch an event on the capturing phase, we need to set the 3rd argument of `addEventListener` to true)
2. Target phase – the event reached the target element.
3. Bubbling phase – the event bubbles up from the element.


### The usage of bubbling and capturing
1. Suppose you want to respond to a click on any A tag on the page, even if the set of A tags changes over time. In particular, we don’t want to visit every A tag and add an event listener.
So, taking advantage of bubbling, we bind a single event handler on its parent element.

```HTML
<script>
  $(document).ready(function() {
    $(document.body).on("click", "#list-contaier > li.active", function() {
      alert($(this).index());
    })
  });
</script>

<ul id="list-contaier">
		<li>0</li>
		<li>0</li>
		<li>0</li>
		<li>0</li>
		<li class="active">0</li>
		<li>0</li>
		<li class="active">0</li>
		<li>0</li>
	</ul>
	<div>abc</div>
```

So with the code above, when you click a list-element whose class is set to active, the index of the element which you click will be alerted.

2. Although almost events bubble, there are some exceptions.
if we want catch the event which won't bubbles, we can use capture.

