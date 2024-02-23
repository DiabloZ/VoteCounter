package suhov.vitaly

data class Vote (
	val ownNumber: String,
	val ownType: OwnType,
	val squareMeters: Float,
	val isHaveAttachment: Boolean,
	val voteMap: Map<Int, VoteType>,
	val isHaveAllVotes: Boolean
)