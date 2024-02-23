package suhov.vitaly

sealed interface OwnType {

	val nameList: List<String>
	data object Flat: OwnType {
		override val nameList: List<String> = listOf("кв, квартира, к")
	}

	data object Storage: OwnType {
		override val nameList: List<String> = listOf("кладовка, кл, клад, кла")
	}
}

enum class OwnTypes(val nameList: List<String>) {
	FLAT(nameList = listOf("кв, квартира, к")),
	STORAGE(nameList = listOf("кладовка, кл, клад, кла"))
}
