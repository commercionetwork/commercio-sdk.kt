![](.img/logo.png)

[![JitPack](https://img.shields.io/jitpack/v/github/commercionetwork/sdk.kt)](https://jitpack.io/#commercionetwork/sdk.kt)
[![Travis](https://img.shields.io/travis/com/commercionetwork/sdk.kt)](https://travis-ci.com/commercionetwork/sdk.kt)
![](https://img.shields.io/badge/compatible-Kotlin-blue)
![](https://img.shields.io/badge/compatible-JVM-blue)

This repository contains the code of the official [Commercio.network](https://commercio.network) Kotlin-JVM SDK, 
entirely based on [Sacco.kt](https://github.com/commercionetwork/sacco.kt). 

The main features are: 
* Entirely made in Kotlin-JVM
* Completely stateless

Thanks to these characteristics, you can use this SDK inside any Android application or even into 
your JVM-based backend servers. 

## Helper methods
Inside the SDK you will find the following helper methods that will help you with almost any operation 
that you might want to perform on the Commercio.network blockchain.

Please note that you can find usage examples of the following methods inside the 
[Examples.kt](src/test/kotlin/network/commercio/sdk/Examples.kt) file. 
We highly suggest you checking it out to have a complete reference of the SDK.  

### Crypto
- Create HD wallet  
- Generate AES key
- Generate RSA key

### CommercioDOCS
- Share document
- Send receipt
- List documents
- List receipts

### CommercioID
- Create Did Document
- Request deposit
- Request power up
- Create connection invitation
- Verify connection invitation

### CommercioMINT
- Open CDP
- Close CDP

### CommercioMEMBERSHIP
- Invite user
- Buy membership