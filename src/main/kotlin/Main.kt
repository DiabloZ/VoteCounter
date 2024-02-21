package suhov.vitaly

import kotlinx.serialization.json.Json
import org.jsoup.Jsoup
import java.io.File
import java.io.InputStream
import java.util.Properties
import javax.mail.Address
import javax.mail.Folder
import javax.mail.Session
import javax.mail.internet.MimeUtility

fun main() {
	val inputStream: InputStream = File("props.txt").inputStream()
	val inputString = inputStream.bufferedReader().use { it.readText() }
	val credentials: Credentials = Json.decodeFromString(inputString)

	prepareServer(credentials = credentials)
}

fun prepareServer(credentials: Credentials) {
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
		val store = session.getStore("imaps")

		store.connect(
			/* host = */ credentials.server,
			/* user = */ credentials.userName,
			/* password = */ credentials.password
		)

		val folder = store.getFolder("INBOX")
		folder.open(Folder.READ_ONLY)

		val messages = folder.messages

		messages.forEach {
			println("1 from -")
			it.from.forEach { address: Address? ->
				println(MimeUtility.decodeText(address.toString()))
			}
			println("2 sentDate -")
			println(it.sentDate)
			println("3 subject -")
			println(it.subject)
			println("4 CONTENT -")
			val content = it.content
			val contentT = it.contentType
			val result = when {
				contentT.contains("html") -> Jsoup.parseBodyFragment(it.content as String).text()
				else -> ""
			}
			println(result)
		}

	} catch (e: Exception) {
		println("2")
		e.printStackTrace()
	}
}



