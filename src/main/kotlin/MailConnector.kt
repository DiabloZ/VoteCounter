package suhov.vitaly

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

	fun startHandling(){
		val currentStore = store
		currentStore.connect(
			/* host = */ credentials.server,
			/* user = */ credentials.userName,
			/* password = */ credentials.password
		)

		val folder = currentStore.getFolder("INBOX")
		folder.open(Folder.READ_ONLY)

		val messages = folder.messages

		messages.forEach { message ->
			println("1 from -")
			message.from.forEach { address: Address? ->
				println(MimeUtility.decodeText(address.toString()))
			}
			println("2 sentDate -")
			println(message.sentDate)
			println("3 subject -")
			println(message.subject)
			println("4 CONTENT -")
			val content = message.content
			val contentT = message.contentType

			val result = when {
				contentT.contains("html") -> Jsoup.parseBodyFragment(content as String).text()
				contentT.contains("multipart") -> {
					val mpContent = content as? Multipart ?: return

					for (i in 0..< mpContent.count){
						val part = mpContent.getBodyPart(i)
						val disposition = part.disposition
						val type = MimeUtility.decodeText(part.contentType)
						if (disposition != null && disposition.contains(Part.ATTACHMENT)){

							val fileName = MimeUtility.decodeText(part.fileName)
							val inputStream = part.inputStream
							//val newFile = File("attachments/$fileName")
							val dirPath = Paths.get("attachments")
							if (!Files.isDirectory(dirPath)){
								Files.createDirectory(dirPath)
							}

							Files.copy(
								inputStream,
								Paths.get("attachments/$fileName"),
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
				isHaveAllVotes = false
			)
			voteListener.sendVote(vote)
			println(result)
		}
	}
}