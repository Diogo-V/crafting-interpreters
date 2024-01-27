# The Lox Language

Is a high level language that is close to C and JavaScript.

Lox is going to be dynamically typed.

There are two main strategies for memory management:
* Reference Counting
* Tracing Garbage Collection

Reference Counting is simpler to implement but start having limitations once the language grows.

The language is going to have a Nil value because it is dynamically typed and so, it comes in useful.

*Expressions* produce values while *Statements* produce effects.

An *argument* is the actual value you pass to the function while the *parameter* is the variable that holds the value that has been passed.

In Lox, functions are *first-class* which means that you can get a reference to it, store in vars and pass around.

You can also declare functions inside other functions which means that we can have *Closures*.

Closures are functions that also keep a data structure that contains the information about the used variables from within its own scope and surrounding scope.

This adds some complexity because we can no longer assume that scope works strictly as a stack where local varibales evaporate the moment a function returns.

Lox has classes and can be object oriented. OOP is super useful for dynamically typed languages to define compound data types and bundle blobs of stuff together.

When it comes to objects, we can follow two approaches:
1. Classes
2. Prototypes

*Classes* came first and are more common thanks to c++ and java. They have two core concepts, instances and classes. Instances are concretized forms of classes and store their own state while classes are blueprints.

*Prototypes* came with the invention of JavaScript. There are no classes and instead we have objects. Each object can contain state and methods and can inherint directly from each other. They are simpler and support patterns that classes try to steer you away from. People usually prefer classes and not prototypes

In Lox, we are going to have Classes and they also are first class citizens.

In other languages, the keyword `super` is used to call a method on our own instance without hitting our own methods (thus calling a parent's method).

Lox is not a pure OOP language because it has primitive types such as int and boolean. To be pure OOP, it would have to be like Java where they are also classes.

Lox also has a standard library but it is going to be super minimal (only a print statement and a clock function to track time).
