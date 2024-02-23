package suhov.vitaly

fun main() {
	Main().runAll()
}

class Main: VoteListener, VoteResultsListener {

	private val propsReader = PropsReader()
	private val mailConnector = MailConnector(this)
	private val voteCounter = VoteCounter(this)
	private val resultPrinter = ResultPrinter()
	fun runAll(){
		val credentials = propsReader.getCredentials()
		mailConnector.prepareServer(credentials)
		mailConnector.startHandling()
	}

	override fun sendVote(vote: Vote) {
		voteCounter.handleVote(vote)
	}

	override fun finishHandling() {
		voteCounter.finishHandling()
	}

	override fun sendVoteResult() {
		resultPrinter.printResults()
	}
}