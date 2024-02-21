package suhov.vitaly

import kotlinx.serialization.Serializable

@Serializable
data class Credentials (
	val server: String,
	val userName: String,
	val password: String
)