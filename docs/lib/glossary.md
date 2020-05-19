# Glossary

Here are collected all that common terms that you should know while you are working with Commercio.network

## Did

Directly from [Did Specifications](https://w3c.github.io/did-core/):
> A DID is a simple text string that consists of three parts:
>
> 1) the URL scheme identifier (did);
> 2) the identifier for the DID method;
> 3) the DID method-specific identifier.

:::tip Example of Commercio.network Did
did:com:14zk9u8894eg7fhgw0dsesnqzmlrx85ga9rvnjc
:::

This Did in simple words it's nothing but a link that resolves to a specific Did Document.

### Did in SDK

Inside the SDK the did is an object of type `Did` which has a string field named `value` that contains a string like the example above.

```kotlin
inline class Did(val value: String)
```

## Did Document

Directly from [Did Specifications](https://w3c.github.io/did-core/):
> A Did Document contains information associated with the Did such as ways to cryptographically authenticate the entity in control of the Did, as well as services that can be used to interact with the entity.

:::tip Did Document Example (specific for Commercio.network)

  ```json
  {
    "@context":"https://www.w3.org/ns/did/v1",
    "id":"did:com:14zk9u8894eg7fhgw0dsesnqzmlrx85ga9rvnjc",
    "publicKey":[
        {
          "id":"did:com:14zk9u8894eg7fhgw0dsesnqzmlrx85ga9rvnjc#keys-1",
          "type":"RsaVerificationKey2018",
          "controller":"did:com:14zk9u8894eg7fhgw0dsesnqzmlrx85ga9rvnjc",
          "publicKeyPem":"-----BEGIN PUBLIC KEY----MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMr3V+Auyc+zvt2qX+jpwk3wM+m2DbfLjimByzQDIfrzSHMTQ8erL0kg69YsXHYXVX9mIZKRzk6VNwOBOQJSsIDf2jGbuEgI8EB4c3q1XykakCTvO3Ku3PJgZ9PO4qRw7QVvTkCbc91rT93/pD3/Ar8wqd4pNXtgbfbwJGviZ6kQIDAQAB-----END PUBLIC KEY-----\r\n"
        },
        {
          "id":"did:com:14zk9u8894eg7fhgw0dsesnqzmlrx85ga9rvnjc#keys-2",
          "type":"RsaSignature2018",
          "controller":"did:com:14zk9u8894eg7fhgw0dsesnqzmlrx85ga9rvnjc",
          "publicKeyPem":"-----BEGIN PUBLIC KEY----MIGfM3TvO3Ku3PJgZ9PO4qRw7+Auyc+zvt2qX+jpwk3wM+m2DbfLjimByzQDIfrzSHMTQ8erL0kg69YsXHYXVX9mIZKRzk6VNwOBOQJSsIDf2jGbuEgI8EB4c3q1XykakCQVvTkCbc9A0GCSqGSIbqd4pNXtgbfbwJGviZ6kQIDAQAB-----END PUBLIC KEY-----\r\n"
        }
    ],
    "proof":{
        "type":"EcdsaSecp256k1VerificationKey2019",
        "created":"2019-02-08T16:02:20Z",
        "proofPurpose":"authentication",
        "controller":"did:com:14zk9u8894eg7fhgw0dsesnqzmlrx85ga9rvnjc",
        "verificationMethod":"<did bech32 pubkey>",
        "signatureValue":"QNB13Y7Q91tzjn4w=="
    }
  }
  ```

:::

### DidDocument in SDK

Inside the SDK it is an object of type `DidDocument`:

```kotlin
data class DidDocument(
    @JsonProperty("@context") val context: String,
    @JsonProperty("id") val id: String,
    @JsonProperty("publicKey") val publicKeys: List<DidDocumentPublicKey>,
    @JsonProperty("proof") val proof: DidDocumentProof,
    @JsonProperty("service") @JsonInclude(JsonInclude.Include.NON_NULL) val service: List<DidDocumentService>?
)
```

## Did Power Up Request

Directly from [Commercio.network Documentation](https://docs.commercio.network/x/id/#requesting-a-did-power-up):
> A Did Power Up is the expression we use when referring to the willingness of a user to move a specified amount of tokens from external centralized entity to one of his private pairwise Did, making them able to send documents (which indeed require the user to spend some tokens as fees).

:::tip Did Power Up Request example

  ```json
  {
    "claimant":"address that sent funds to the centralized entity before",
    "amount":[
        {
          "denom":"ucommercio",
          "amount":"amount to transfer to the pairwise did, integer"
        }
    ],
    "proof":"proof string",
    "id":"randomly-generated UUID v4",
    "proof_key":"proof encryption key"
  }
  ```

:::

### MsgRequestDidPowerUp in SDK

Inside the SDK it is an object of type `MsgRequestDidPowerUp`

```kotlin
data class MsgRequestDidPowerUp(
    private val claimantDid: String,
    private val amount: List<StdCoin>,
    private val powerUpProof: String,
    private val uuid: String,
    private val proofKey: String
)
```
