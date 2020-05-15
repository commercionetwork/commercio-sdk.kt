import java.security.KeyPair
import java.security.PrivateKey


//An asymmetric pair of public and private asymmetric keys
data class KeyPairWrapper(
    val publicWrapper: PublicKeyWrapper,
    val private: PrivateKey
) {
    fun toKeyPair(): KeyPair {
        return KeyPair(publicWrapper.public, private)
    }
}
