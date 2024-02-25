package suhov.vitaly

suspend fun main() {
	Main().runAll()
}

class Main : VoteListener, VoteResultsListener {

	private val propsReader = PropsReader()
	private val mailConnector = MailConnector(this)
	private val voteCounter = VoteCounter(this)
	private val resultPrinter = ResultPrinter()

	suspend fun runAll() {
		val credentials = propsReader.getCredentials()
		mailConnector.prepareServer(credentials)
		mailConnector.startHandling()
	}

	override fun finishHandling(voteList: List<Vote>) {
		voteCounter.handleVotes(voteList)
	}

	override fun sendVoteResult(voteResults: VoteResults) {
		resultPrinter.printResults(voteResults)
	}
}