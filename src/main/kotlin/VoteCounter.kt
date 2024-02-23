package suhov.vitaly

class VoteCounter(private val voteResultsListener: VoteResultsListener) {
	fun handleVote(vote: Vote) {
	}

	fun finishHandling() {
		voteResultsListener.sendVoteResult()
	}
}