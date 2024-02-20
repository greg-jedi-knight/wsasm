package parser

import utils.OpcodeTable.Argument

object Instructions {

	sealed trait Instruction
	case class LabelDeclaration(name: String, lineNum: Int) extends Instruction
	case class LabelInstruction(arg: String, opcode: String, lineNum: Int) extends Instruction
	case class NumInstruction(arg: Int, opcode: String) extends Instruction
	case class NoArgInstruction(opcode: String) extends Instruction
}