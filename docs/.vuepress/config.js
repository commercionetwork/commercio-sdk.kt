module.exports = {
    title: "Commercio.network Kotlin SDK Documentation",
    description: "Documentation for the Commercio.network blockhain.",
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
                title: "Blockchain's Helpers",
                collapsable: false,
                children: [
                    ["lib/id/id_helper", "Id Helper"],
                    ["lib/id/did_document_helper", "Did Document Helper"],
                    ["lib/docs/docs_helper", "Docs Helper"],
                    ["lib/membership/membership_helper", "Membership Helper"],
                    ["lib/mint/mint_helper", "Mint Helper"],
                    ["lib/tx/tx_helper", "Tx Helper"],
                    ["lib/crypto/sign_helper", "Sign Helper"]
                ]
            },
            {
                title: "Utility Helpers",
                collapsable: false,
                children: [
                    ["lib/crypto/keys_helper", "Keys Helper"],
                    ["lib/crypto/encryption_helper", "Encryption Helper"],
                    ["lib/crypto/certificate_helper", "Certificate Helper"]
                ]
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
