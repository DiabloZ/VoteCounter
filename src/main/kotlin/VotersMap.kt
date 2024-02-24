package suhov.vitaly

data class VotersMap(
	val voteMap: Map<Int, Map<VoteType, Vote>>
)