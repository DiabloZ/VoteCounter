package suhov.vitaly

interface VoteListener {
	fun finishHandling(voteList: List<Vote>)
}