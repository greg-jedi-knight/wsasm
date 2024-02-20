package assembler

import parser.Instructions._
import LabelChecker.VInstruction
import utils.ErrorUtils.AsmError

object Assembler {

	def assemble(instructions: List[Instruction]): Either[AsmError, String] = {
		LabelChecker.check(instructions) match {
			case Right(checked) =>
				val wsCode = checked.foldLeft("") { (acc, instruction) =>
					val argBinary = instruction.arg.map(convertToBinary).getOrElse("")
					acc + instruction.opcode + argBinary
				}

				Right(wsCode)

			case Left(error) =>
				Left(error)	
		}
	}

	private def convertToBinary(num: Int): String = {
		val binaryString = num.abs.toBinaryString
		val prefix = if (num >= 0) 'S' else 'T'

		prefix + binaryString.collect {
			case '0' => 'S'
			case '1' => 'T'	
		}.mkString + 'L'
	} 
}