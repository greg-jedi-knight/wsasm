package input

import org.rogach.scallop._

object ArgsProcessor {

	def processCmdArgs(args: Array[String]): Map[String, Any] = {
		def nextArg(map: Map[String, Any], list: List[String]): Map[String, Any] = {
			list match {
				case Nil => map
				case "-o" :: outfile :: tail =>
					nextArg(map ++ Map("outfile" -> outfile), tail)
				case "-v" :: tail =>
					nextArg(map ++ Map("verbose" -> true), tail)
				case string :: Nil =>
					nextArg(map ++ Map("filename" -> string), list.tail)
				case unknown :: _ => map				
			}
		}

		nextArg(Map("outfile" -> "a.ws", "verbose" -> false), args.toList)
	}

}