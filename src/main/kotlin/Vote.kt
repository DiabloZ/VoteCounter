package suhov.vitaly

data class Vote (
	val date: String,
	val ownNumber: Int,
	val ownEmail: String,
	val ownName: String,
	val ownType: OwnType,
	val squareMeters: Double,
	val voteMap: Map<Int, VoteType>,
	val isHaveOwnType: Boolean = ownType != OwnType.NotFound,
	val isHaveSquareMeters: Boolean = squareMeters != Utils.defaultMeters,
	val isHaveAttachment: Boolean,
	val isHaveAllVotes: Boolean = checkVotes(voteMap),
	val isHaveOwnNumber: Boolean = ownNumber != Utils.defaultNumber
) {
	fun isFill() = isHaveSquareMeters && isHaveOwnType && isHaveAttachment && isHaveAllVotes
}

const val numberOfVoices = 21

private fun checkVotes(voteMap: Map<Int, VoteType>): Boolean {
	var isContainAllAnswers = true
	var numAnswer = 0
	voteMap.forEach { (num, voteType) ->
		if (!isContainAllAnswers) {
			return@forEach
		}
		numAnswer++
		isContainAllAnswers = num == numAnswer && (voteType != VoteType.ERROR)
	}
	return isContainAllAnswers && numAnswer == numberOfVoices
}
