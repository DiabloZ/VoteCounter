package suhov.vitaly.models

interface VoteResultsListener {
	fun sendVoteResult(voteResults: VoteResults)
}