# First Interpreter

## Parts of the compiler

Scanner takes in a stream of characters and outputs a set of tokens (words) that are meaningful for a program to use.

A Lexer takes in a set of tokens created by a Scanner and builds an Abstract Syntax Tree (AST) from it that encompases the logic behind a stream of characters.

After we have the Abstract Syntax Tree, we perform Syntax Analysis. 

The first form of syntax analysis done is usually to perform variable resolution (check where in the scope they are).

After finding where the variables are located, we can then perform type check.

With this, we can now do one of 3 things:

1. store the obtained information back in the AST
2. store data in a lookup table (typically called symbol table)
3. transform the tree into another structure that is better suited to express the semantics of the code

All of the above is usually called the *Frontend of the Compiler*

The *Backend of the Compiler* is concerned with where the code will run (the kind of hardware)

In between these two, there exists a form called *Middle End* that encompases writting *Intermediate Representations* (IRs). These IRs are useful to be able to support different frontend implementations to a single backend implementation.

There are multiple styles of IRs:
* Control Flow Graph
* Static Single-Assignment
* Continuation-Passing Style
* Three-Address Code

The Middle End is also used to optimize the program before passing it to the backend. 

For instance, one such example is *Constant Folding* where we precompute values.

More examples of optimizations are:
* Constant Propagation
* Common Subexpression Elimination
* Loop Invariant Code Motion
* Global Value Numbering
* Strength Reduction
* Scalar Replacement of Aggregates
* Dead Code Elimination
* Loop Unrolling

Finally, the Compiler's backend is reponsible for code generation. We can decide to generate instructions for a real CPU (making our compiler bound to the specific architecture that we are targeting) or generate *Bytecode* which is then going to have to be ran by a *Virtual Machine*.

The advantage of using a VM is that we can implement it ourselves in another language like C and then use GCC to compile it and run the bytecode. A drawback of this approach is that it makes the code execution slower than translating it to native code.

## Types of Compilers

There are some compilers called *Single-pass* compilers. This type of compilers interleave parsing, analysis and code generation so that they produce the output code directly in the parser without ever allocating any syntax trees or other IRs. Pascal and C are an example. They don't use much memory and are quite efficient.

*Tree-Walk* Interpreters are slow and mostly used for educational purposes. They execute code right after parsing it into an AST and it traverses the syntax tree one branch at a time, evaluating each node as it goes.

*Transpilers* are compilers that mainly implement the frontend of a language and then produce a string of valid code for another language. After that, you use the other language's compilation tools. This can be a good solution because we can target C code which is fast and reliable.

*Just-in-time* compilers compile the code in the user's machine right before executing it. This makes it possible to not have to think about which architectures the users are going to use. This is the case of HotSpot JVM. The most sophisticated JITs also insert profiling blocks into the code so that they can use runtime information to further optimize it.

## Compilers vs Interpreters

A Compiler is a technique that translates a source language to some other - usually lower level - form.

An Interpreter does not perform translation and instead executes it immediately.
