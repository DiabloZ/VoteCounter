package suhov.vitaly

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.Jsoup
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.Properties
import javax.mail.*
import javax.mail.internet.MimeUtility

@Suppress("IMPLICIT_CAST_TO_ANY")
class MailConnector(private val voteListener: VoteListener) {

	private lateinit var store: Store
	private lateinit var credentials: Credentials
	val logger = Logger

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
			val some = CoroutineScope(Dispatchers.IO).async {
				handleMessage(message)
			}
			jobList.add(some)
		}
		jobList.forEach { it.await() }
		voteListener.finishHandling()
		folder.close(true)
		store.close()
	}

	private fun handleMessage(message: Message) {
		val funName = object : Any() {}.javaClass.enclosingMethod.name
		var name: String = ""
		var email: String = ""
		logger.printText(funName, "1 from -")

		message.from.forEach { address: Address? ->
			val addressString = MimeUtility.decodeText(address.toString())
			name = addressString.substringBefore(" <")
			email = addressString.substringAfter("<").substringBefore(">")
		}
		logger.printText(funName, "2 sentDate -")
		logger.printText(funName, message.sentDate)
		logger.printText(funName, "3 subject -")
		logger.printText(funName, message.subject)
		logger.printText(funName, "4 CONTENT -")
		val content = message.content
		val contentT = message.contentType

		val result = when {
			contentT.contains("html") -> Jsoup.parseBodyFragment(content as String).text()
			contentT.contains("multipart") -> {
				val mpContent = content as? Multipart ?: return

				for (i in 0..<mpContent.count) {
					val part = mpContent.getBodyPart(i)
					val disposition = part.disposition
					val type = MimeUtility.decodeText(part.contentType)
					if (disposition != null && disposition.contains(Part.ATTACHMENT)) {

						val fileName = MimeUtility.decodeText(part.fileName)
						val inputStream = part.inputStream
						//val newFile = File("attachments/$fileName")
						val dirPath = Paths.get("attachments")
						val filePath = Paths.get("attachments/$fileName")
						if (!Files.isDirectory(dirPath) && !Files.exists(filePath)) {
							Files.createDirectory(dirPath)
						}

						Files.copy(
							inputStream,
							filePath,
							StandardCopyOption.REPLACE_EXISTING
						)
					}
				}
			}

			else -> ""
		}
		val vote = Vote(
			ownNumber = "",
			ownType = OwnType.Flat,
			squareMeters = 0.0f,
			isHaveAttachment = false,
			voteMap = mapOf(),
			isHaveAllVotes = false,
			ownEmail = email,
			ownName = name,
			isHaveOwnType = false,
			isHaveSquareMeters = false,

			)
		voteListener.sendVote(vote)
		logger.printText(funName, vote)
	}
}