package parser

import utils.OpcodeTable._
import utils.Constants._

object OpcodeParser {

	def parseOpcodes(lines: List[String]): List[(String, OpcodeInfo)] = {
		lines.filterNot(_.startsWith(";")).map(parseLine)
	}

	private def parseLine(line: String): (String, OpcodeInfo) = {
		val parts = line.trim.split("\\s+")

		val mnemonic = parts(0)
		val arg = parts(1) match {
			case STR_LITERAL => Some(Number(0))
			case STR_LABEL   => Some(Label("label"))
			case _           => None	
		}

		val opcode = parts(2)

		(mnemonic, new OpcodeInfo(arg, opcode))
	}
}