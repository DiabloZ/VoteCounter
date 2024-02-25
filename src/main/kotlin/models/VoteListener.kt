package suhov.vitaly.models

interface VoteListener {
	fun finishHandling(voteList: List<Vote>)
}