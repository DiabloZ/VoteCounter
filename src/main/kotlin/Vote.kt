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
	val isHaveSquareMeters: Boolean = squareMeters != Constants.DEFAULT_METERS,
	val isHaveAttachment: Boolean,
	val isHaveAllVotes: Boolean = checkVotes(voteMap),
	val isHaveOwnNumber: Boolean = ownNumber != Constants.DEFAULT_NUMBER
) {
	fun isFill() = isHaveSquareMeters && isHaveOwnType && isHaveAttachment && isHaveAllVotes
}



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
	return isContainAllAnswers && numAnswer == Constants.NUMBER_OF_VOICES
}
