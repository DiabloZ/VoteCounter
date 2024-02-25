package suhov.vitaly.models

sealed interface OwnType {

	data object Flat: OwnType {
		val nameList: List<String> = listOf("кв", "квартира", "к.")
	}

	data object Storage: OwnType {
		val nameList: List<String> = listOf("кладовка", "кл.", "клад", "кла")
	}

	data object NotFound: OwnType
}
