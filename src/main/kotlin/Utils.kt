package suhov.vitaly

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object Utils {
	val scope = CoroutineScope(Dispatchers.IO)
	const val BLANK = ""
}