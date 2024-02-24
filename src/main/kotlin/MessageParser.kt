package suhov.vitaly

object MessageParser {

	private const val DELIMITER = "\n"
	private val regexPoint = Regex("\\d+")

	fun parse(message: String): Map<Int, VoteType> {
		val points = message.split(DELIMITER)
		val mapVotes = mutableMapOf<Int, VoteType>()
		points.forEach { point ->

			val answers = regexPoint
				.findAll(point)
				.toList()
				.mapNotNull { it.groupValues.firstOrNull() }
			val answerNumber = answers.firstOrNull()?.toIntOrNull()
			val answerDigit = answers.lastOrNull()?.toIntOrNull()

			if (answerNumber != null && answerDigit != null && answerNumber <= 24) {
				if (answers.size > 2 || answers.size <= 1) {
					mapVotes[answerNumber] = VoteType.ERROR
					return@forEach
				}
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
		"19     \n" +
		"20     5\n" +
		"21   12 3\n" +
		"23  2\n" +
		"27  2\n"

	MessageParser.parse(message).forEach {
		Logger.printText("MessageParserTEST", it)
	}
}