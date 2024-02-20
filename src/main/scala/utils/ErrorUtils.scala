package utils

object ErrorUtils {

	sealed trait Error
	case class FileNotFoundError(filename: String) extends Error
	case class IOError(filename: String) extends Error
	case class AsmError(msg: String) extends Error

	def reportError(error: Error): Unit = error match
		case FileNotFoundError(filename) => println(s"[wsasm error] cannot find ${filename}")
		case IOError(filename)           => println(s"[wsasm error] IO error has occured, file: ${filename}")
		case AsmError(msg)               => println(s"[wsasm error] ${msg}")	
}