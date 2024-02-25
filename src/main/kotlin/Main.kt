package suhov.vitaly

import suhov.vitaly.credentials.PropsReader
import suhov.vitaly.mail.MailConnector
import suhov.vitaly.models.Vote
import suhov.vitaly.models.VoteListener
import suhov.vitaly.models.VoteResults
import suhov.vitaly.models.VoteResultsListener
import suhov.vitaly.parsers.VoteCounter
import suhov.vitaly.printer.ResultPrinter

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