package suhov.vitaly

interface VoteListener {
	fun sendVote(vote: Vote)
	fun finishHandling()
}