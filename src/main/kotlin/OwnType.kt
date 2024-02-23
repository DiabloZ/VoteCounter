package suhov.vitaly

sealed interface OwnType {

	data object Flat: OwnType {
		val nameList: List<String> = listOf("кв, квартира, к")
	}

	data object Storage: OwnType {
		val nameList: List<String> = listOf("кладовка, кл, клад, кла")
	}

	data object NotFound: OwnType
}

enum class OwnTypes(val nameList: List<String>) {
	FLAT(nameList = listOf("кв, квартира, к")),
	STORAGE(nameList = listOf("кладовка, кл, клад, кла"))
}
