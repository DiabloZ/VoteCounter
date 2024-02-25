package suhov.vitaly

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object Utils {
	val scope = CoroutineScope(Dispatchers.IO)
	const val BLANK = ""
	const val TOTAL_SQUARE_METERS = 22332.0
	const val defaultMeters = 0.0
	const val defaultNumber = 0
}