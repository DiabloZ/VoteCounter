package suhov.vitaly

sealed interface VoteType {
	val voteNumber: Int
	data object YES: VoteType {
		override val voteNumber: Int = 1
	}

	data object NO: VoteType {
		override val voteNumber: Int = 2
	}

	data object ABSTAINED: VoteType {
		override val voteNumber: Int = 3
	}

	data object ERROR: VoteType {
		override val voteNumber: Int = 4
	}
}