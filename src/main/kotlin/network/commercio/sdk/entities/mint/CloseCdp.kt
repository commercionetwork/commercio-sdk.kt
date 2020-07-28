package network.commercio.sdk.entities.mint

/**
 * Contains the data related to a Collateralized Debt Position
 * that is closed by a user.
 */
data class CloseCdp(
    val signerDid: String,
    val timeStamp: Int
)