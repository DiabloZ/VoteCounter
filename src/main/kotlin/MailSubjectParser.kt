package suhov.vitaly

object MailSubjectParser {

	private const val defaultMeters = 0.0
	private const val defaultNumber = 0
	private val regexDigital = Regex("\\d+.\\d")
	fun handleSubject(subject: String): Triple<OwnType, Int, Double> {
		val sub = subject.lowercase()

		val ownType = when {
			OwnType.Storage.nameList.any { sub.contains(it) } -> OwnType.Storage
			OwnType.Flat.nameList.any { sub.contains(it) } -> OwnType.Flat
			else -> OwnType.NotFound
		}

		val (flatNumber, squareMeters) = handleOtherPartInfo(sub)
		return Triple(ownType, flatNumber, squareMeters)
	}

	private fun handleOtherPartInfo(subject: String): Pair<Int, Double> {
		val digital = regexDigital.findAll(subject)
			.toList()
			.mapNotNull { it.groupValues.firstOrNull() }


		val flatNumber = digital.firstOrNull()?.toIntOrNull() ?: defaultNumber
		val totalSquareMeters = digital.lastOrNull()?.toDoubleOrNull() ?: defaultMeters
		val squareMeters = if (digital.size == 2 && totalSquareMeters < 88.0) {
			totalSquareMeters
		} else {
			defaultMeters
		}
		return flatNumber to squareMeters
	}
}

fun main(){
	val subject = "Квартира 1234, Метраж 82.2"
	val result = MailSubjectParser.handleSubject(subject)
	val subject2 = "Кладовка 123, Метраж 3.2"
	val result2 = MailSubjectParser.handleSubject(subject2)
	val errSubject = "Квартира 1234, Метраж "
	val errResult = MailSubjectParser.handleSubject(errSubject)

	result
	result2
	errResult
}