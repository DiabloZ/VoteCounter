package suhov.vitaly

object MessageParser {

	private const val DELIMITER = "\n"
	private val regexPoint = Regex("\\d+")
	fun parse(message: String): Map<Int, VoteType> {
		val points = message.split(DELIMITER)
		val mapVotes = mutableMapOf<Int, VoteType>()
		points.forEach { point ->
			val answerNumber = regexPoint.find(point)?.groupValues?.firstOrNull()?.toIntOrNull()
			val answerDigit = point.lastOrNull()?.digitToIntOrNull()
			if (answerNumber != null && answerDigit != null && answerNumber <= 24) {
				val voteType = when(answerDigit) {
					VoteType.YES.voteNumber -> VoteType.YES
					VoteType.NO.voteNumber -> VoteType.NO
					VoteType.ABSTAINED.voteNumber -> VoteType.ABSTAINED
					else -> VoteType.ERROR
				}
				mapVotes[answerNumber] = voteType
			}
		}
		return mapVotes
	}
}

fun main(){
	val message = "1. 1\n" +
		"2 - 3\n" +
		"3- 2\n" +
		"4.3\n" +
		"4.3\n" +
		"5-1\n" +
		"6  2\n" +
		"7   1\n" +
		"23  2\n"

	MessageParser.parse(message).forEach {
		Logger.printText("MessageParserTEST", it)
	}
}