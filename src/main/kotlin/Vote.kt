package suhov.vitaly

data class Vote (
	val ownNumber: String,
	val ownEmail: String,
	val ownName: String,
	val ownType: OwnType,
	val squareMeters: Float,
	val voteMap: Map<Int, VoteType>,
	val isHaveOwnType: Boolean,
	val isHaveSquareMeters: Boolean,
	val isHaveAttachment: Boolean,
	val isHaveAllVotes: Boolean
) {
	fun isFill() = isHaveSquareMeters && isHaveOwnType && isHaveAttachment && isHaveAllVotes
}