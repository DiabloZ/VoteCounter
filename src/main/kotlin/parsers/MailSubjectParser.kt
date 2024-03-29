package suhov.vitaly.parsers

import suhov.vitaly.models.OwnType
import suhov.vitaly.utils.Constants.DEFAULT_METERS
import suhov.vitaly.utils.Constants.DEFAULT_NUMBER
import suhov.vitaly.utils.Constants.MAXIMUM_SIZE_OF_VOTE

object MailSubjectParser {

	private val regexDigital = Regex("(\\d+(?:\\.\\d+)?)")

	fun handleSubject(subject: String): Triple<OwnType, Int, Double> {
		val sub = subject.lowercase().replace(",", ".").trim()

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


		val flatNumber = digital.firstOrNull()?.toIntOrNull() ?: DEFAULT_NUMBER
		val totalSquareMeters = digital.lastOrNull()?.toDoubleOrNull() ?: DEFAULT_METERS
		val squareMeters = if (digital.size == MAXIMUM_SIZE_OF_VOTE && totalSquareMeters < 88.0) {
			totalSquareMeters
		} else {
			DEFAULT_METERS
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
	val subjectLast = "Квартира 1 Метраж 12,2"
	val lastResult = MailSubjectParser.handleSubject(subjectLast)

	result
	result2
	errResult
	lastResult
}