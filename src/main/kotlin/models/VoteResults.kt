package suhov.vitaly.models

import suhov.vitaly.utils.Constants

data class VoteResults(
	val goodVoters: MutableList<Vote> = mutableListOf(),
	val votersWithOutOwnType: MutableList<Vote> = mutableListOf(),
	val votersWithOutAttachment: MutableList<Vote> = mutableListOf(),
	val votersWithOutAllVotes: MutableList<Vote> = mutableListOf(),
	val votersWithOutSquareMeters: MutableList<Vote> = mutableListOf(),
	val votersWithOutOwnNumber: MutableList<Vote> = mutableListOf(),
	var totalSuccessSquareMeters: Double = Constants.DEFAULT_METERS,
) {

	fun addGoodVoter(voter: Vote) {
		goodVoters.add(voter)
	}

	fun calculateGoodVoters() {
		goodVoters.forEach { voter ->
			totalSuccessSquareMeters += voter.squareMeters
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
