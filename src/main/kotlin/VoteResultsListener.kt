package suhov.vitaly

interface VoteResultsListener {
	fun sendVoteResult(voteResults: VoteResults)
}