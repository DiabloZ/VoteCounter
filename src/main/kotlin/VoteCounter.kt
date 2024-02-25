package suhov.vitaly

class VoteCounter(private val voteResultsListener: VoteResultsListener) {
	fun handleVotes(voteList: List<Vote>) {
		val voteResults = VoteResults().apply {
			voteList.forEach { voter ->
				when {
					voter.isFill() -> addGoodVoter(voter)
					!voter.isHaveOwnNumber -> addVoterWithOutOwnNumber(voter)
					else -> {
						if (!voter.isHaveAllVotes) {
							addVoterWithOutAllVotes(voter)
						}
						if (!voter.isHaveAttachment) {
							addVoterWithOutAttachment(voter)
						}
						if (!voter.isHaveOwnType) {
							addVoterWithOutOwnType(voter)
						}
						if (!voter.isHaveSquareMeters) {
							addVotersWithOutSquareMeters(voter)
						}
					}
				}
			}
		}
		voteResults.calculateGoodVoters()
		voteResultsListener.sendVoteResult(voteResults)
	}
}