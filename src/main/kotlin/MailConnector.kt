package suhov.vitaly

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import suhov.vitaly.Utils.scope
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
		val jobList: MutableList<Deferred<Unit>> = mutableListOf()
		messages.forEach { message ->
			val deferred = scope.async {
				val vote = MessageHandler.handleMessage(message)
				voteListener.sendVote(vote)
			}
			jobList.add(deferred)
		}
		jobList.forEach { deferred -> deferred.await() }
		voteListener.finishHandling()

		folder.close(true)
		store.close()
	}

}