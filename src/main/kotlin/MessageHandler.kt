package suhov.vitaly

import suhov.vitaly.Constants.BLANK
import javax.mail.Address
import javax.mail.Message
import javax.mail.Multipart
import javax.mail.Part
import javax.mail.internet.MimeUtility

object MessageHandler {
	fun handleMessage(message: Message): Vote {
		val date = message.sentDate.toString()
		val (name, email)  = handleAddress(message)
		val (ownType, ownNumber, ownSquareMeters) = MailSubjectParser.handleSubject(message.subject)

		var voteMap: Map<Int, VoteType> = emptyMap()
		var isHaveAttachment = false

		val content = message.content
		val contentT = message.contentType

		when {
			contentT.contains("html") -> {
				val answersText = MailTextContentParser.cleanBodyContent(content.toString())
				voteMap = MailContentParser.parse(answersText)
			}
			contentT.contains("multipart") -> {
				val mpContent = content as? Multipart
				mpContent?.apply {
					for (i in 0..< mpContent.count) {
						val part = mpContent.getBodyPart(i)
						val disposition = part.disposition
						when {
							disposition != null && disposition.contains(Part.ATTACHMENT) -> {
								val fileName = MimeUtility.decodeText(part.fileName)
								isHaveAttachment = true
							}
							part.content != null -> {
								val answersText = MailTextContentParser.cleanBodyContent(part.content.toString())
								voteMap = MailContentParser.parse(answersText)
							}
						}
					}
				}

			}
		}

		val vote = Vote(
			date = date,
			ownName = name,
			ownEmail = email,
			ownNumber = ownNumber,
			ownType = ownType,
			squareMeters = ownSquareMeters,
			voteMap = voteMap,
			isHaveAttachment = isHaveAttachment
		)
		return vote
	}

	private fun handleAddress(
		message: Message,
	): Pair<String, String> {
		var name: String = BLANK
		var email: String = BLANK
		message.from.forEach { address: Address? ->
			val addressString = MimeUtility.decodeText(address.toString())
			name = addressString.substringBefore(" <")
			email = addressString.substringAfter("<").substringBefore(">")
		}
		return Pair(name, email)
	}
}