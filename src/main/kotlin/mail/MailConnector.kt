package suhov.vitaly.mail

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import suhov.vitaly.models.Credentials
import suhov.vitaly.models.Vote
import suhov.vitaly.models.VoteListener
import suhov.vitaly.parsers.MessageHandler
import suhov.vitaly.utils.Logger
import suhov.vitaly.utils.Utils.scope
import java.util.Properties
import javax.mail.Folder
import javax.mail.Session
import javax.mail.Store

class MailConnector(private val voteListener: VoteListener) {

	private lateinit var store: Store
	private lateinit var credentials: Credentials

	fun prepareServer(credentials: Credentials) {
		this.credentials = credentials
		try {
			val properties = Properties()
			properties["mail.transport.protocol"] = "imaps"
			properties["mail.imap.port"] = "993"

			properties.setProperty("mail.store.protocol", "imap")
			properties.setProperty("mail.imap.host", "imap.yandex.ru")
			properties.setProperty("mail.user", credentials.userName) // ваш логин
			properties.setProperty("mail.password", credentials.password) // ваш пароль
			properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
			properties.setProperty("mail.smtp.auth", "true");
			val session = Session.getInstance(properties)
			store = session.getStore("imaps")
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	suspend fun startHandling(){
		val currentStore = store
		currentStore.connect(
			/* host = */ credentials.server,
			/* user = */ credentials.userName,
			/* password = */ credentials.password
		)

		val folder = currentStore.getFolder("INBOX")
		folder.open(Folder.READ_ONLY)

		val messages = folder.messages
		val jobList: MutableList<Deferred<Vote>> = mutableListOf()
		messages.forEach { message ->
			val deferred = scope.async {
				Logger.printResult("Обрабатываем сообщение - ${messages.indexOf(message) + 1}")
				MessageHandler.handleMessage(message)
			}
			jobList.add(deferred)
		}

		val voteList = jobList.map { deferred -> deferred.await() }
		voteListener.finishHandling(voteList)

		folder.close(true)
		store.close()
	}

}