package suhov.vitaly.parsers

import suhov.vitaly.utils.Constants

object MailTextContentParser {
	private val regexHtml = Regex("<[^>]*>")
	fun cleanBodyContent(content: String): String = content
		.replace("<br />", "\n")
		.replace("</div><div>", "\n")
		.replace(" ", Constants.BLANK)
		.replace(regexHtml, Constants.BLANK)
}

fun main(){
	val string = "<div>1 1<br />2 2</div><div>3 3</div><div>4 3</div><div>5 2</div><div>6 2</div><div>7 1</div><div>8 - 3</div><div>9-1</div><div>10 2</div><div>11 3</div><div>12 1</div><div>13 2</div><div>14 3</div><div>15 2</div><div>16 1</div><div>17 2</div><div>18 3</div><div>19  3</div><div>20   1<br />21 2</div>"
	val result = MailTextContentParser.cleanBodyContent(string)
	result
}