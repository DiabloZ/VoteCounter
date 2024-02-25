package suhov.vitaly.models

import suhov.vitaly.utils.Constants
import suhov.vitaly.utils.Constants.NUMBER_OF_VOICES

data class VoteResults(
	val goodVoters: MutableList<Vote> = mutableListOf(),
	val votersWithOutOwnType: MutableList<Vote> = mutableListOf(),
	val votersWithOutAttachment: MutableList<Vote> = mutableListOf(),
	val votersWithOutAllVotes: MutableList<Vote> = mutableListOf(),
	val votersWithOutSquareMeters: MutableList<Vote> = mutableListOf(),
	val votersWithOutOwnNumber: MutableList<Vote> = mutableListOf(),
	var totalSuccessSquareMeters: Double = Constants.DEFAULT_METERS,
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

private fun createVoteMap(): MutableMap<Int, MutableMap<VoteType, Vote>> =
	mutableMapOf<Int, MutableMap<VoteType, Vote>>().apply {
		for (i in 1..NUMBER_OF_VOICES) {
			this[i] = mutableMapOf()
		}
	}