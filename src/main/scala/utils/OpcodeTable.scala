package utils

import scala.collection.mutable.Map

object OpcodeTable {

	sealed trait Argument
	case class Number(value: Int) extends Argument
	case class Label(name: String) extends Argument

	class OpcodeInfo(private val arg: Option[Argument], private val opcode: String) {

		def getArg(): Option[Argument] = arg

		def getOpcode(): String = opcode
	}

	private val table: Map[String, OpcodeInfo] = Map()

	def addOperation(mnemonic: String, info: OpcodeInfo): Unit = table.put(mnemonic, info)

	def getOperation(mnemonic: String): Option[OpcodeInfo] = table.get(mnemonic)

	def populateOpcodeTable(opcodes: List[(String, OpcodeInfo)]): Unit = {
		opcodes.foreach { op =>
			addOperation(op._1, op._2)
		}
	}
}