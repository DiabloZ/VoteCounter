package suhov.vitaly

class ResultPrinter {
	fun printResults(voteResults: VoteResults) {


		Logger.printResult("Всего метров квадратных проголосовало - ${voteResults.totalSuccessSquareMeters}\n")

		voteResults.voteMap.forEach { (voteNumber, map) ->
			var yesSum = 0.0
			var noSum = 0.0
			var abstainedSum = 0.0
			var errorSum = 0.0

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

	}
}