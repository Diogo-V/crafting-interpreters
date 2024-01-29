# Scanning

Its important to handle errors in the compiler. We should always print the line, column and if possible, a visual representation of the offending line to the user.

It is good practice to seperate the code that generates the errors from the code that reports them.

Various phases of the frontend will detect errors bu its not their job to know how to present them to the user. Usually, we will have multiple ways of showing an error like in the IDE, in the stderr, etc...

Ideally we would have an actual abstraction, some kind of "ErrorReporter" interface that gets passed to the scanner and parser so that we can swap out different reporting strategies.

For Lox, since it is so simple, we didn't do that.

A *Lexeme* is a set of chars that, when together, mean something to our implementation (like reserved keywords or even string variables).

A Parser cares about having lexemes but more than that, it needs to know which kind of lexeme.

We can have multiple types of keywords like operators and literal types.

Literal values are Strings and Numbers. The Scanner has to walk each character in the literal to correctly identify it and can also convert it to the correct runtime object.

Some Scanner implementations store the location as two numbers: the offset from the beginning of the source file to the beginning of the lexeme and the length of the lexeme. An offset can be converted to a line/column later by counting the number of preceding new lines. This is slow but since we only do it in an error, thats okay.

"The Dragon Book" covers the theory a bit better. Should take a look at it after.

The rules that dictate how a particular language bundles characters into lexemes is called *lexical grammar*.

To have a better user experience, we should always surface all errors found instead of surfacing only one error at a time when scanning. This allows the user to fix all the errors in one go.

The principal of *maximal munch* is when two lexical grammar rules can both match a chunk of code that the scanner is looking at, whichever one matches more characters wins. For instance, we can have an identifier called "orchid" and the keyword "or". All or statements would be incorrect if we did not handle keywords differently.

To handle that case, we should capture it as an identifier and only then, can we try to compare it against all known keywords of the language.

If it matches as one of the reserved keywords, we have to "promote" it to a specific token type of the reserved keyword that was matched.

A quick note on ";": most people nowadays prefer languages that do not have those but they were pretty useful to delimite end of lines. When not using them, we have to carefully consider what is a significant "\n" and what is not relevant
