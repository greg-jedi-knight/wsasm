## Whitespace Assembler

This project is an implementation of a Whitespace assembler, dsigned to translate human-readable instruction names (e.g. "push", "pop") into corresponding Whitespace instructions. Whitespace is an esoteric programming language where only whitespace characters (space, tab, and newline) have meaning, making it challenging and intriguing to work with.

Customizable instruction set is defined in "opcodes.dat", allowing easy extension of supported instructions.

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).

### Example - Truth-machine

A truth machine is a concept in the realm of theoretical computer science and esoteric programming languages. It refers to a program that takes input and produces output in a way that:

- If the input is 1, the truth machine outputs 1 indefinitely.
- If the input is 0, the truth machine outputs 0 and halts.

```
	push 0
	duplicate	
	read_num	
	retrieve
	jz zero
one:
	push 1
	output_num
	jump one
zero:
	push 0
	output_num
	exit	

```
