package utils

import scala.collection.mutable.Map

object LabelTable {

	class LabelInfo(private val address: Int, private val declared: Boolean) {

		def getAddress(): Int = address

		def isDeclared(): Boolean = declared
	}

	private val table: Map[String, LabelInfo] = Map()

	def addLabel(name: String, info: LabelInfo): Unit = table.put(name, info)

	def getLabel(name: String): Option[LabelInfo] = table.get(name)

	def getUndeclared(): List[String] = {
		table.collect {
			case (name, info) if !info.isDeclared() => name
		}.toList
	}
}