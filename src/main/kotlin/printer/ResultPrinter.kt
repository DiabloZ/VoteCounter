package suhov.vitaly.printer

import suhov.vitaly.models.VoteResults
import suhov.vitaly.models.VoteType
import suhov.vitaly.utils.Constants
import suhov.vitaly.utils.Constants.BLANK
import suhov.vitaly.utils.Constants.DEFAULT_METERS
import suhov.vitaly.utils.Logger
import suhov.vitaly.utils.Utils.calculatePercent
import suhov.vitaly.utils.Utils.questionMap

class ResultPrinter {
	fun printResults(voteResults: VoteResults) {

		val stringPercent = calculatePercent(sum = voteResults.totalSuccessSquareMeters, total = Constants.TOTAL_SQUARE_METERS)
		Logger.printResult("\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
		Logger.printResult("Всего метров квадратных проголосовало - ${voteResults.totalSuccessSquareMeters.cutAfterComma()} из ${Constants.TOTAL_SQUARE_METERS} это $stringPercent%\n")

		for (voteNumber in 1..Constants.NUMBER_OF_VOICES) {
			var yesSum = DEFAULT_METERS
			var noSum = DEFAULT_METERS
			var abstainedSum = DEFAULT_METERS
			var errorSum = DEFAULT_METERS

			voteResults.goodVoters.forEach { voter ->
				val voteType = voter.voteMap[voteNumber]
				when(voteType){
					VoteType.ABSTAINED -> abstainedSum += voter.squareMeters
					VoteType.NO -> noSum += voter.squareMeters
					VoteType.YES -> yesSum += voter.squareMeters
					else -> errorSum += voter.squareMeters
				}
			}

			val totalVoters = yesSum + noSum + abstainedSum + errorSum
			val yesSumPercent = calculatePercent(sum = yesSum, total = totalVoters)
			val noSumPercent = calculatePercent(sum = noSum, total = totalVoters)
			val abstainedSumPercent = calculatePercent(sum = abstainedSum, total = totalVoters)
			val errorSumPercent = calculatePercent(sum = errorSum, total = totalVoters)

			Logger.printResult("Вопрос №$voteNumber")
			Logger.printResult("${questionMap[voteNumber]}\n")
			Logger.printResult("Проголосовали -")
			Logger.printResult("За - кв.м ${yesSum.cutAfterComma()}, $yesSumPercent%")
			Logger.printResult("Против - кв.м ${noSum.cutAfterComma()}, $noSumPercent%")
			Logger.printResult("Воздержались - кв.м ${abstainedSum.cutAfterComma()}, $abstainedSumPercent%")
			Logger.printResult("Проголосовали с ошибкой кв.м - ${errorSum.cutAfterComma()}, $errorSumPercent%")
			Logger.printResult("//////////////////////////////////\n")
		}

		Logger.printResult("НЕ УЧТЁННЫЕ АДРЕСА/КВАРТИРЫ - ПРИЧИНЫ - ")
		Logger.printResult("Квартиры проголосовавшие не на все вопросы - ")
		voteResults.votersWithOutAllVotes.forEach { voter ->

			if (!voteResults.goodVoters.any { it.ownEmail == voter.ownEmail }) {
				Logger.printResult("${voter.ownNumber}")
			}
		}
		Logger.printResult(BLANK)

		Logger.printResult("Квартиры проголосовавшие без квадратных метров - ")
		voteResults.votersWithOutSquareMeters.forEach {voter ->
			if (!voteResults.goodVoters.any { it.ownEmail == voter.ownEmail }) {
				Logger.printResult("${voter.ownNumber}")
			}
		}
		Logger.printResult(BLANK)

		Logger.printResult("Квартиры проголосовавшие без типа жилья - ")
		voteResults.votersWithOutOwnType.forEach {voter ->
			if (!voteResults.goodVoters.any { it.ownEmail == voter.ownEmail }) {
				Logger.printResult("${voter.ownNumber}")
			}
		}
		Logger.printResult(BLANK)

		Logger.printResult("Квартиры проголосовавшие без вложения - ")
		voteResults.votersWithOutAttachment.forEach {voter ->
			if (!voteResults.goodVoters.any { it.ownEmail == voter.ownEmail }) {
				Logger.printResult("${voter.ownNumber}")
			}
		}
		Logger.printResult(BLANK)

		Logger.printResult("Адреса, которые не указали номер квартиры- ")
		voteResults.votersWithOutOwnNumber.forEach {voter ->
			if (!voteResults.goodVoters.any { it.ownEmail == voter.ownEmail }) {
				Logger.printResult(voter.ownEmail)
			}
		}
		Logger.printResult(BLANK)

	}
}

fun Number.cutAfterComma(num: Int = 1): String = String.format("%.${num}f", this)