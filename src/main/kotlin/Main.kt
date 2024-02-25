package suhov.vitaly

import suhov.vitaly.credentials.PropsReader
import suhov.vitaly.mail.MailConnector
import suhov.vitaly.models.Vote
import suhov.vitaly.models.VoteListener
import suhov.vitaly.models.VoteResults
import suhov.vitaly.models.VoteResultsListener
import suhov.vitaly.parsers.VoteCounter
import suhov.vitaly.printer.ResultPrinter
import suhov.vitaly.utils.Logger

suspend fun main() {
	Main().runAll()
}

class Main : VoteListener, VoteResultsListener {

	private val propsReader = PropsReader()
	private val mailConnector = MailConnector(this)
	private val voteCounter = VoteCounter(this)
	private val resultPrinter = ResultPrinter()

	suspend fun runAll() {
		Logger.printResult("Запуск программы")
		val credentials = propsReader.getCredentials()
		Logger.printResult("Получили настройки для почтового сервера")
		mailConnector.prepareServer(credentials)
		Logger.printResult("Подготовили почтовый сервер, начинаем обработку сообщений")
		mailConnector.startHandling()

	}

	override fun finishHandling(voteList: List<Vote>) {
		Logger.printResult("Обработали сообщения")
		voteCounter.handleVotes(voteList)
	}

	override fun sendVoteResult(voteResults: VoteResults) {
		Logger.printResult("Подводим результаты")
		resultPrinter.printResults(voteResults)
	}
}