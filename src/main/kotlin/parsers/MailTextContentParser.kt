package suhov.vitaly.parsers

import org.jsoup.Jsoup
import suhov.vitaly.utils.Constants
import suhov.vitaly.utils.Constants.CARRIAGE_RETURN
import suhov.vitaly.utils.Logger

object MailTextContentParser {
	private val regexHtml = Regex("<[^>]*>")
	private val regexDigital = Regex("\\d+\\D*\\d+")
	fun cleanBodyContent(content: String): String  {
		val clearedContext = Jsoup.parse(content).text()
		val resultClear = clearedContext
			.replace("<br />", CARRIAGE_RETURN)
			.replace("\n\n", CARRIAGE_RETURN)
			.replace("</div><div>", CARRIAGE_RETURN)
			.replace("\r", Constants.BLANK)
			.replace(" ", Constants.BLANK)
			.replace(regexHtml, Constants.BLANK)

		var sumText = Constants.BLANK
		val cleanFromText = regexDigital
			.findAll(resultClear)
			.toList()
			.map { matchResult -> matchResult.groupValues.firstOrNull()?.trim() + CARRIAGE_RETURN }
		cleanFromText.forEach {
			sumText += it
		}
		return sumText.trim()
	}
}

fun main(){
	val result = MailTextContentParser.cleanBodyContent(string)
	val result2 = MailTextContentParser.cleanBodyContent(otherString)
	val result3 = MailTextContentParser.cleanBodyContent(otherString2)
	val result4 = MailTextContentParser.cleanBodyContent(trashString)
	val voteMap1 = MailContentParser.parse(result)
	val voteMap2 = MailContentParser.parse(result2)
	val voteMap3 = MailContentParser.parse(result3)
	val voteMap4 = MailContentParser.parse(result4)
	assert(voteMap1.size == 21)
	assert(voteMap2.size == 21)
	assert(voteMap3.size == 21)
	assert(voteMap4.size == 21)
	if (
		voteMap1.size != 21
		|| voteMap2.size != 21
		|| voteMap3.size != 21
		|| voteMap4.size != 21
	){
		error("Не пройден")
	}
	Logger.printResult("Успешно пройден")
}

private val trashString = "<div><div><div><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"> день</p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"> дошлю</p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">1- 2</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">2- 2</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">3- 1</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">4- 1</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">5- 2</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">6- 2</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">7- 2</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">8- 1</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">9- 1</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">10- 1</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">11- 1</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">12- 1</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">13- 1</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">14- 2</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">15- 2</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">16- 2</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">17- 2</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">18- 1</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">19- 2</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">20- 1</span></strong></p><p style=\"line-height:1.38;margin-bottom:0pt;margin-left:36pt;margin-top:0pt\"><strong style=\"font-weight:normal\"><span style=\"background-color:transparent;color:#000000;font-family:'arial' , sans-serif;font-size:11pt;font-style:normal;font-variant:normal;font-weight:400;text-decoration:none;vertical-align:baseline;white-space:pre-wrap\">21- 1</span></strong></p></div></div></div>"
private val string = "<div>1 1<br />2 2</div><div>3 3</div><div>4 3</div><div>5 2</div><div>6 2</div><div>7 1</div><div>8 - 3</div><div>9-1</div><div>10 2</div><div>11 3</div><div>12 1</div><div>13 2</div><div>14 3</div><div>15 2</div><div>16 1</div><div>17 2</div><div>18 3</div><div>19  3</div><div>20   1<br />21 2</div>"
private val otherString = "1- 2\r\n" +
	"2- 2\r\n" +
	"3- 1\r\n" +
	"4- 2\r\n" +
	"5- 2\r\n" +
	"6- 2\r\n" +
	"7- 2\r\n" +
	"8- 1\r\n" +
	"9- 1\r\n" +
	"10- 1\r\n" +
	"11- 1\r\n" +
	"12- 1\r\n" +
	"13- 1\r\n" +
	"14- 2\r\n" +
	"15- 2\r\n" +
	"16- 2\r\n" +
	"17- 2\r\n" +
	"18- 1\r\n" +
	"19- 1\r\n" +
	"20- 1\r\n" +
	"21- 1\r\n"
private val otherString2 = "Добрый день!\n" +
	"\n" +
	"\n" +
	"\n" +
	"1- 2\n" +
	"\n" +
	"2- 2\n" +
	"\n" +
	"3- 1\n" +
	"\n" +
	"4- 1\n" +
	"\n" +
	"5- 2\n" +
	"\n" +
	"6- 2\n" +
	"\n" +
	"7- 2\n" +
	"\n" +
	"8- 1\n" +
	"\n" +
	"9- 1\n" +
	"\n" +
	"10- 1\n" +
	"\n" +
	"11- 1\n" +
	"\n" +
	"12- 1\n" +
	"\n" +
	"13- 1\n" +
	"\n" +
	"14- 2\n" +
	"\n" +
	"15- 2\n" +
	"\n" +
	"16- 2\n" +
	"\n" +
	"17- 2\n" +
	"\n" +
	"18- 1\n" +
	"\n" +
	"19- 2\n" +
	"\n" +
	"20- 1\n" +
	"\n" +
	"21- 1"