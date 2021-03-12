module.exports = {
    title: "Commercio.network Kotlin SDK Documentation",
    description: "Documentation for the Commercio.network blockhain Kotlin SDK.",
    head: [
        ['link', { rel: "commercio-icon", href: "/.vuepress/icon.png" }]
    ],
    markdown: {
        lineNumbers: true
    },
    themeConfig: {
        repo: "commercionetwork/commercio-sdk.kt",
        editLinks: true,
        docsDir: "docs",
        docsBranch: "master",
        editLinkText: "Edit this page on Github",
        lastUpdated: true,
        nav: [
            {text: "Commercio.network", link: "https://commercio.network"}
        ],
        sidebarDepth: 2,
        sidebar: [
            {
                title: "Wallet",
                collapsable: false,
                children: [
                    ["wallet/create-wallet", "Create a wallet"]
                ]
            },
            {
                    title: 'Id helpers',
                    collapsable: true,
                    children: [
                      ['lib/id/id_helper', 'IdHelper'],
                      ['lib/id/did_document_helper', 'DidDocumentHelper'],
                      ['lib/id/request_did_power_up_helper', 'RequestDidPowerUpHelper'],
                    ],
            },
                  {
                    title: 'Docs helpers',
                    collapsable: true,
                    children: [
                      ['lib/docs/docs_helper', 'DocsHelper'],
                      ['lib/docs/commercio_doc_helper', 'CommercioDocHelper'],
                      ['lib/docs/commercio_doc_receipt_helper','CommercioDocReceiptHelper']
                    ],
                  },
                        {
                          title: 'Mint helpers',
                          collapsable: true,
                          children: [
                            ['lib/mint/mint_helper', 'MintHelper'],
                            ['lib/mint/mint_ccc_helper', 'MintCccHelper'],
                            ['lib/mint/burn_ccc_helper', 'BurnCccHelper']
                          ],
                        },
              {
                title: 'Kyc helpers',
                collapsable: true,
                children: [
                  ['lib/kyc/kyc_helper', 'KycHelper'],
                  ['lib/kyc/buy_membership_helper', 'BuyMembershipHelper'],
                  ['lib/kyc/invite_user_helper', 'InviteUserHelper'],
                  ['lib/kyc/reward_pool_deposit_helper', 'RewardPoolDepositHelper'],
                ],
              },
              {
                title: 'Tx helpers',
                collapsable: true,
                children: [['lib/tx/tx_helper', 'TxHelper']]
              },

            {
                title: "Utility Helpers",
                collapsable: true,
                children: [
                    ["lib/crypto/keys_helper", "Keys Helper"],
                    ["lib/crypto/encryption_helper", "Encryption Helper"],
                    ["lib/crypto/certificate_helper", "Certificate Helper"],
                    ['lib/crypto/sign_helper', 'SignHelper']
                ]
            },
              {
                title: 'Localnet',
                collapsable: true,
                children: [['localnet/testing', 'Testing locally']],
              },
            {
                title: "Glossary",
                collapsable:false,
                children: [
                    ["lib/glossary", "Glossary"]
                ]
            }
        ]
    }
};
