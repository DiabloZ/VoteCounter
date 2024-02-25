package suhov.vitaly

import suhov.vitaly.Constants.BLANK
import suhov.vitaly.Constants.DEFAULT_METERS

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

		Logger.printResult("Квартиры проголосовавшие не за все вопросы (не будут учтены) - ")
		voteResults.votersWithOutAllVotes.forEach {
			Logger.printResult("${it.ownNumber}")
		}
		Logger.printResult(BLANK)

		Logger.printResult("Квартиры проголосовавшие без квадратных метров (не будут учтены) - ")
		voteResults.votersWithOutSquareMeters.forEach {
			Logger.printResult("${it.ownNumber}")
		}
		Logger.printResult(BLANK)

		Logger.printResult("Квартиры проголосовавшие без типа жилья (не будут учтены) - ")
		voteResults.votersWithOutOwnType.forEach {
			Logger.printResult("${it.ownNumber}")
		}
		Logger.printResult(BLANK)

		Logger.printResult("Квартиры проголосовавшие без вложения (не будут учтены) - ")
		voteResults.votersWithOutAttachment.forEach {
			Logger.printResult("${it.ownNumber}")
		}
		Logger.printResult(BLANK)

		Logger.printResult("Адреса, которые не указали номер квартиры (не будут учтены) - ")
		voteResults.votersWithOutOwnNumber.forEach {
			Logger.printResult(it.ownEmail)
		}
		Logger.printResult(BLANK)

	}
}