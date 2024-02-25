package suhov.vitaly.parsers

import suhov.vitaly.utils.Constants

object MailTextContentParser {
	private val regexHtml = Regex("<[^>]*>")
	fun cleanBodyContent(content: String): String = content
		.replace("<br />", "\n")
		.replace("</div><div>", "\n")
		.replace("\r", Constants.BLANK)
		.replace(" ", Constants.BLANK)
		.replace(regexHtml, Constants.BLANK)
		.trim()
}

fun main(){
	val string = "<div>1 1<br />2 2</div><div>3 3</div><div>4 3</div><div>5 2</div><div>6 2</div><div>7 1</div><div>8 - 3</div><div>9-1</div><div>10 2</div><div>11 3</div><div>12 1</div><div>13 2</div><div>14 3</div><div>15 2</div><div>16 1</div><div>17 2</div><div>18 3</div><div>19  3</div><div>20   1<br />21 2</div>"
	val otherString = "1- 2\r\n" +
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
	val result = MailTextContentParser.cleanBodyContent(string)
	val result2 = MailTextContentParser.cleanBodyContent(otherString)
	result.trim()
	result2.trim()
}