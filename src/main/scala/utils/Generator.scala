package utils

object Generator {

	private var lblCounter: Int = 0

	def generateLabel(): Int = {
		lblCounter += 1

		lblCounter - 1
	}
}