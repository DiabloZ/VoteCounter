package suhov.vitaly

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object Utils {
	val scope = CoroutineScope(Dispatchers.IO)
	const val BLANK = ""
	const val TOTAL_SQUARE_METERS = 22332.0
}