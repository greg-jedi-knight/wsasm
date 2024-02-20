package utils

object Constants {

	val USAGE: String = "usage: wsasm [-o outfile] [-v] filename"

	val OPCODES_FILENAME = "opcodes.dat"

	val LABEL_DECL_OPCODE = "LSS"

	val STR_LABEL = "label"
	val STR_LITERAL = "literal"
	val DASH = "-"

	val LABEL_DECL_REGEX = "^[a-zA-Z_]+:$".r
	val LABEL_REGEX = "^[a-zA-Z_]+$".r
	val DEC_REGEX = "-?\\d+".r
	val HEX_REGEX = """0[xX][0-9a-fA-F]+""".r
	val BIN_REGEX = """0[bB][01]+""".r
	val OCT_REGEX = """0[cC][0-7]+""".r
	val CHAR_REGEX = "'(.)'".r
}