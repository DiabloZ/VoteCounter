package suhov.vitaly.printer

import suhov.vitaly.models.VoteResults
import suhov.vitaly.models.VoteType
import suhov.vitaly.utils.Constants
import suhov.vitaly.utils.Constants.BLANK
import suhov.vitaly.utils.Constants.DEFAULT_METERS
import suhov.vitaly.utils.Logger

class ResultPrinter {
	fun printResults(voteResults: VoteResults) {

		val percent = (voteResults.totalSuccessSquareMeters / Constants.TOTAL_SQUARE_METERS * 100)
		val stringPercent = String.format("%.3f", percent)

		Logger.printResult("Всего метров квадратных проголосовало - ${voteResults.totalSuccessSquareMeters} из ${Constants.TOTAL_SQUARE_METERS} это $stringPercent%\n")

		voteResults.voteMap.forEach { (voteNumber, map) ->
			var yesSum = DEFAULT_METERS
			var noSum = DEFAULT_METERS
			var abstainedSum = DEFAULT_METERS
			var errorSum = DEFAULT_METERS

			map.forEach { (voteType, vote) ->
				when(voteType){
					VoteType.ABSTAINED -> abstainedSum += vote.squareMeters
					VoteType.ERROR -> errorSum += vote.squareMeters
					VoteType.NO -> noSum += vote.squareMeters
					VoteType.YES -> yesSum += vote.squareMeters
				}
			}

			Logger.printResult("Вопрос №$voteNumber")
			Logger.printResult("Проголосовали -")
			Logger.printResult("За $yesSum")
			Logger.printResult("Против $noSum")
			Logger.printResult("Воздержались $abstainedSum")
			Logger.printResult("Проголосовали с ошибкой $errorSum")
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