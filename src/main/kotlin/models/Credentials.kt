package suhov.vitaly.models

import kotlinx.serialization.Serializable

@Serializable
data class Credentials (
	val server: String,
	val userName: String,
	val password: String
)