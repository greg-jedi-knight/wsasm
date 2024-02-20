package output

import utils.ErrorUtils.{reportError, IOError}
import java.io.{FileOutputStream, IOException}

object FileCreator {

	def createOutFile(filename: String, code: String, verbose: Boolean): Unit = {
		val spaceByte = ' '.toByte
		val tabByte = '\t'.toByte
		val lfByte = '\n'.toByte

		val output = code.flatMap {
			case 'S' =>
				if (verbose) List[Byte]('S'.toByte, spaceByte) else List[Byte](spaceByte)

			case 'T' =>
				if (verbose) List[Byte]('T'.toByte, tabByte) else List[Byte](tabByte)

			case 'L' =>
				if (verbose) List[Byte]('L'.toByte, lfByte) else List[Byte](lfByte)

		}.toList

		val fileOutputStream = new FileOutputStream(filename)

		try {
			fileOutputStream.write(output.toArray)
			fileOutputStream.close()
		} catch {
			case e: IOException =>
				reportError(IOError(filename))
				sys.exit(1)
		}
	}
}