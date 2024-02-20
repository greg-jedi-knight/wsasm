package input

import utils.ErrorUtils._
import utils.OpcodeTable

import scala.io.Source
import java.io.{FileNotFoundException, IOException}

object FileReader {

	def readFile(filePath: String): Either[Error, List[String]] = {
		try {
			val source = Source.fromFile(filePath)
			val lines = source.getLines().toList

			source.close()

			Right(lines)
			
		} catch {
			case e: FileNotFoundException =>
				Left(FileNotFoundError(filePath))
			case e: IOException =>
				Left(IOError(filePath))	
		}
		
	}
}