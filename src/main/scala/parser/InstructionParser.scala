package parser

import utils.OpcodeTable
import utils.OpcodeTable._
import utils.ErrorUtils._
import utils.Constants._
import Instructions._

object InstructionParser {

	def parseInstructions(lines: List[String]): Either[AsmError, List[Instruction]] = {
		val parsedInstructions = lines
		.map { line => removeComment(line.trim) }
		.zipWithIndex
		.filter { case (line, _) => line.nonEmpty }
		.map { case (lineContent, lineNumber) => parseInstruction(lineContent, lineNumber + 1) }

		val (errors, instructions) = parsedInstructions.partition(_.isLeft)

		if (errors.nonEmpty) {
			val errorMessages = errors.collect { case Left(error) => error.msg }
			Left(AsmError(errorMessages.mkString("\n[wsasm error] ")))
		} else {
			Right(instructions.collect { case Right(instruction) => instruction })
		}
	}


	private def parseInstruction(lineContent: String, lineNum: Int): Either[AsmError, Instruction] = {
		val (mnemonic, pos) = parseMnemonic(lineContent, 0)

		parseArg(lineContent, pos, lineNum) match {
			case Right(optArg) =>
				OpcodeTable.getOperation(mnemonic.toLowerCase()) match {
					case Some(info) =>
						info.getArg() match {
							case Some(Number(_)) =>
								optArg match {
									case Some(Number(value)) =>
										Right(NumInstruction(value, info.getOpcode()))

									case _ =>
										Left(AsmError(s"Number as an argument expected | line ${lineNum}"))	
								}

							case Some(Label(_)) =>
								optArg match {
									case Some(Label(labelName)) =>
										Right(LabelInstruction(labelName, info.getOpcode(), lineNum))

									case _ =>
										Left(AsmError(s"label as an argument expected | line ${lineNum}"))	
								}	

							case _ =>
								optArg match {
									case Some(_) =>
										Left(AsmError(s"Unexpected argument for instruction | line ${lineNum}"))

									case _ =>
										Right(NoArgInstruction(info.getOpcode()))		
								}	
						}

					case _ =>
						if (LABEL_DECL_REGEX.matches(mnemonic)) {
							optArg match {
								case Some(_) =>
									Left(AsmError(s"Unexpected argument after label declaration | line ${lineNum}"))

								case _ =>
									Right(LabelDeclaration(mnemonic.dropRight(1), lineNum))	
							}
						} else {
							Left(AsmError(s"Unknown operation: ${mnemonic} | line ${lineNum}"))
						}	
				}

			case Left(error) =>
				Left(error)	
		}
	}

	private def parseArg(input: String, pos: Int, lineNum: Int): Either[AsmError, Option[Argument]] = {
		if (pos < input.length) {
			val arg = input.substring(pos).trim

			if (LABEL_REGEX.matches(arg)) {
				Right(Some(Label(arg)))
			} else if (DEC_REGEX.matches(arg)) {
				Right(Some(Number(arg.toInt)))
			} else if (BIN_REGEX.matches(arg)) {
				Right(Some(Number(strToInt(arg, 2))))
			} else if ((HEX_REGEX.matches(arg))) {
				Right(Some(Number(strToInt(arg, 16))))
			} else if (OCT_REGEX.matches(arg)) {
				Right(Some(Number(strToInt(arg, 8))))
			} else if (CHAR_REGEX.matches(arg)) {
				Right(Some(Number(arg.charAt(1).toInt)))
			} else {
				Left(AsmError(s"Invalid argument for instruction | line ${lineNum}"))
			}

		} else {
			Right(None)
		}
	}

	private def parseMnemonic(input: String, pos: Int): (String, Int) = {
		if (pos == input.length || input(pos) == ' ') {
			(input.substring(0, pos), pos)
		} else {
			parseMnemonic(input, pos + 1)
		}
	}

	private def removeComment(input: String): String = {
		input.split(';').headOption.map(_.trim).getOrElse(input)
	}

	private def strToInt(input: String, sys: Int): Int = Integer.parseInt(input.drop(2), sys)
}