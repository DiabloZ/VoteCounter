package suhov.vitaly.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object Utils {
	val scope = CoroutineScope(Dispatchers.IO)
}