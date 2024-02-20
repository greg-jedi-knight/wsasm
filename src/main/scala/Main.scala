import input.ArgsProcessor.processCmdArgs
import input.FileReader.readFile
import utils.ErrorUtils.reportError
import utils.Constants._
import utils.OpcodeTable.populateOpcodeTable
import parser.OpcodeParser.parseOpcodes
import parser.InstructionParser.parseInstructions
import assembler.Assembler.assemble
import output.FileCreator.createOutFile

object Main {
  private val DEFAULT_OUTFILE = "a.ws"

  def main(args: Array[String]): Unit = {
    
    val options = processCmdArgs(args)

    val filename = options.getOrElse("filename", {
      println(USAGE)
      sys.exit(1)
    }).toString

    readFileAndProcess(filename, options)
  }

  def readFileAndProcess(filename: String, options: Map[String, Any]): Unit = {
    readFile(filename) match {
      case Right(sourceLines) =>
        readFile(OPCODES_FILENAME) match {
          case Right(opcodeLines) =>
            populateOpcodeTable(parseOpcodes(opcodeLines))
            parseAndAssembleInstructions(sourceLines, options)

          case Left(error) =>
            reportError(error)
            sys.exit(1)
        }

      case Left(error) =>
        reportError(error)
        sys.exit(1)
    }
  }

  def parseAndAssembleInstructions(sourceLines: List[String], options: Map[String, Any]): Unit = {
    parseInstructions(sourceLines) match {
      case Right(instructions) =>
        assemble(instructions) match {
          case Right(wsCode) =>
            val outfile = options.getOrElse("outfile", DEFAULT_OUTFILE).asInstanceOf[String]
            val verbose = options.getOrElse("verbose", false).asInstanceOf[Boolean]
            createOutFile(outfile, wsCode, verbose)

          case Left(error) =>
            reportError(error)
            sys.exit(1)
        }

      case Left(error) =>
        reportError(error)
        sys.exit(1)
    }
  }
}