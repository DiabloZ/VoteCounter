package suhov.vitaly

data class VoteResults(
	val goodVoters: MutableList<Vote> = mutableListOf(),
	val votersWithOutOwnType: MutableList<Vote> = mutableListOf(),
	val votersWithOutAttachment: MutableList<Vote> = mutableListOf(),
	val votersWithOutAllVotes: MutableList<Vote> = mutableListOf(),
	val votersWithOutSquareMeters: MutableList<Vote> = mutableListOf(),
	val votersWithOutOwnNumber : MutableList<Vote> = mutableListOf(),
	var totalSuccessSquareMeters: Double = 0.0,
	val voteMap: MutableMap<Int, MutableMap<VoteType, Vote>> = createVoteMap()
) {
	fun addGoodVoter(voter: Vote) {
		goodVoters.add(voter)
	}

	fun calculateGoodVoters() {
		goodVoters.forEach { voter ->
			totalSuccessSquareMeters += voter.squareMeters
			voter.voteMap.forEach { (questionNumber, voteType) ->
				voteMap[questionNumber]?.set(voteType, voter)
			}
		}
	}

	fun addVoterWithOutAllVotes(voter: Vote) {
		votersWithOutAllVotes.add(voter)
	}

	fun addVoterWithOutAttachment(voter: Vote) {
		votersWithOutAttachment.add(voter)
	}

	fun addVoterWithOutOwnType(voter: Vote) {
		votersWithOutOwnType.add(voter)
	}

	fun addVotersWithOutSquareMeters(voter: Vote) {
		votersWithOutSquareMeters.add(voter)
	}
	fun addVoterWithOutOwnNumber(voter: Vote) {
		votersWithOutOwnNumber.add(voter)
	}
}

private fun createVoteMap(): MutableMap<Int, MutableMap<VoteType, Vote>> = mutableMapOf(
	1 to mutableMapOf(),
	2 to mutableMapOf(),
	3 to mutableMapOf(),
	4 to mutableMapOf(),
	5 to mutableMapOf(),
	6 to mutableMapOf(),
	7 to mutableMapOf(),
	8 to mutableMapOf(),
	9 to mutableMapOf(),
	10 to mutableMapOf(),
	11 to mutableMapOf(),
	12 to mutableMapOf(),
	13 to mutableMapOf(),
	14 to mutableMapOf(),
	15 to mutableMapOf(),
	16 to mutableMapOf(),
	17 to mutableMapOf(),
	18 to mutableMapOf(),
	19 to mutableMapOf(),
	20 to mutableMapOf(),
	21 to mutableMapOf(),
)