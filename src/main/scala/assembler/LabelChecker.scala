package assembler

import parser.Instructions._
import utils.ErrorUtils._
import utils.LabelTable
import utils.LabelTable._
import utils.Generator.generateLabel
import utils.Constants._

object LabelChecker {

	case class VInstruction(arg: Option[Int], opcode: String)

	def check(instructions: List[Instruction]): Either[AsmError, List[VInstruction]] = {
		val (errors, checked) = instructions.map(analyze).partition(_.isLeft)

		if (errors.nonEmpty) {
			val errorMessages = errors.collect { case Left(error) => error.msg }
			Left(AsmError(errorMessages.mkString("\n[wsasm error] ")))
		} else {
			val undeclared = LabelTable.getUndeclared()

			if (undeclared.isEmpty) {
				Right(checked.collect { case Right(cInst) => cInst })
			} else {
				Left(AsmError(undeclared.map { lblName => s"Label '${lblName}' is not declared" }.mkString("\n[wsasm] ")))
			}
		}
	}

	private def analyze(instruction: Instruction): Either[AsmError, VInstruction] = {
		instruction match {
			case LabelDeclaration(name, lineNum) =>
				LabelTable.getLabel(name) match {
					case Some(info) =>
						if (info.isDeclared()) {
							Left(AsmError(s"'${name}' is already declared | line ${lineNum}"))
						} else {
							LabelTable.addLabel(name, new LabelInfo(info.getAddress(), true))
							Right(VInstruction(Some(info.getAddress()), LABEL_DECL_OPCODE))
						}

					case _ =>
						val labelNum = generateLabel()

						LabelTable.addLabel(name, new LabelInfo(labelNum, true))
						Right(VInstruction(Some(labelNum), LABEL_DECL_OPCODE))	
				}

			case LabelInstruction(arg, opcode, lineNum) =>
				LabelTable.getLabel(arg) match {
					case Some(info) =>
						Right(VInstruction(Some(info.getAddress()), opcode))

					case _ =>
						val labelNum = generateLabel()

						LabelTable.addLabel(arg, new LabelInfo(labelNum, false))
						Right(VInstruction(Some(labelNum), opcode))	
				}

			case NumInstruction(arg, opcode) =>
				Right(VInstruction(Some(arg), opcode))

			case NoArgInstruction(opcode) =>
				Right(VInstruction(None, opcode))			
		}
	}
}