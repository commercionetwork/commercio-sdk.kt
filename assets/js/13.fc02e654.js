(window.webpackJsonp=window.webpackJsonp||[]).push([[13],{368:function(s,t,a){"use strict";a.r(t);var n=a(45),e=Object(n.a)({},(function(){var s=this,t=s.$createElement,a=s._self._c||t;return a("ContentSlotsDistributor",{attrs:{"slot-key":s.$parent.slotKey}},[a("h1",{attrs:{id:"commercio-doc-receipt-helper"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#commercio-doc-receipt-helper"}},[s._v("#")]),s._v(" Commercio Doc Receipt Helper")]),s._v(" "),a("p",[s._v("Allows to easily create a CommercioDocReceipt and perform common related operations.")]),s._v(" "),a("h2",{attrs:{id:"provide-operations"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#provide-operations"}},[s._v("#")]),s._v(" Provide Operations")]),s._v(" "),a("ol",[a("li",[a("p",[s._v("Creates a CommercioDoc from the given "),a("code",[s._v("wallet")]),s._v(", "),a("code",[s._v("recipient")]),s._v(", "),a("code",[s._v("txHash")]),s._v(", "),a("code",[s._v("documentId")]),s._v(" and optionally "),a("code",[s._v("proof")]),s._v(".")]),s._v(" "),a("div",{staticClass:"language-kotlin line-numbers-mode"},[a("pre",{pre:!0,attrs:{class:"language-kotlin"}},[a("code",[a("span",{pre:!0,attrs:{class:"token keyword"}},[s._v("fun")]),s._v(" "),a("span",{pre:!0,attrs:{class:"token function"}},[s._v("fromWallet")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v("(")]),s._v("\n    wallet"),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v(":")]),s._v(" Wallet"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n    recipient"),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v(":")]),s._v(" Did"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n    txHash"),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v(":")]),s._v(" String"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n    documentId"),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v(":")]),s._v(" String"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n    proof"),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v(":")]),s._v(" String "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('""')]),s._v("\n"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(")")]),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v(":")]),s._v(" CommercioDocReceipt\n")])]),s._v(" "),a("div",{staticClass:"line-numbers-wrapper"},[a("span",{staticClass:"line-number"},[s._v("1")]),a("br"),a("span",{staticClass:"line-number"},[s._v("2")]),a("br"),a("span",{staticClass:"line-number"},[s._v("3")]),a("br"),a("span",{staticClass:"line-number"},[s._v("4")]),a("br"),a("span",{staticClass:"line-number"},[s._v("5")]),a("br"),a("span",{staticClass:"line-number"},[s._v("6")]),a("br"),a("span",{staticClass:"line-number"},[s._v("7")]),a("br")])])])]),s._v(" "),a("h2",{attrs:{id:"usage-examples"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#usage-examples"}},[s._v("#")]),s._v(" Usage examples")]),s._v(" "),a("p",[s._v("Suppose that we received a "),a("code",[s._v("MsgShareDocument")]),s._v(" with our wallet address in the "),a("code",[s._v("recipients")]),s._v(" list and we whant to send back\na receipt. Let's assume that we check the transaction\nat http://localhost:1337/txs/3959641D57D8B6DE0DE7F71CFB636F3140AB0F8FD9976E996477C6AAD5FBF730 and extract the needed\ninformation to build our receipt in the following example:")]),s._v(" "),a("div",{staticClass:"language-kotlin line-numbers-mode"},[a("pre",{pre:!0,attrs:{class:"language-kotlin"}},[a("code",[a("span",{pre:!0,attrs:{class:"token comment"}},[s._v("// Configure the blockchain network")]),s._v("\n"),a("span",{pre:!0,attrs:{class:"token keyword"}},[s._v("val")]),s._v(" info "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" "),a("span",{pre:!0,attrs:{class:"token function"}},[s._v("NetworkInfo")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v("(")]),s._v("\n   bech32Hrp "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"did:com:"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   lcdUrl "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"http://localhost:1317"')]),s._v("\n"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(")")]),s._v("\n\n"),a("span",{pre:!0,attrs:{class:"token comment"}},[s._v("// Build our wallet from the mnemonics")]),s._v("\n"),a("span",{pre:!0,attrs:{class:"token keyword"}},[s._v("val")]),s._v(" mnemonic "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" "),a("span",{pre:!0,attrs:{class:"token function"}},[s._v("listOf")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v("(")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"will"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"hard"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"topic"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"spray"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"beyond"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"ostrich"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"moral"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"morning"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"gas"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"loyal"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"couch"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"horn"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"boss"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"across"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"age"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"post"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"october"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"blur"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"piece"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"wheel"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"film"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"notable"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"word"')]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"man"')]),s._v("\n"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(")")]),s._v("\n"),a("span",{pre:!0,attrs:{class:"token keyword"}},[s._v("val")]),s._v(" wallet "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" Wallet"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(".")]),a("span",{pre:!0,attrs:{class:"token function"}},[s._v("derive")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v("(")]),s._v("mnemonic "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" mnemonic"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v(" networkInfo "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" info"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(")")]),s._v("\n\n"),a("span",{pre:!0,attrs:{class:"token comment"}},[s._v("// The received MsgShareDocument transaction hash")]),s._v("\n"),a("span",{pre:!0,attrs:{class:"token keyword"}},[s._v("val")]),s._v(" txHash "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"3959641D57D8B6DE0DE7F71CFB636F3140AB0F8FD9976E996477C6AAD5FBF730"')]),s._v("\n\n"),a("span",{pre:!0,attrs:{class:"token comment"}},[s._v("// The MsgShareDocument sender")]),s._v("\n"),a("span",{pre:!0,attrs:{class:"token keyword"}},[s._v("val")]),s._v(" shareDocSender "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"did:com:1cc65t29yuwuc32ep2h9uqhnwrregfq230lf2rj"')]),s._v("\n\n"),a("span",{pre:!0,attrs:{class:"token comment"}},[s._v("// The MsgShareDocument UUID")]),s._v("\n"),a("span",{pre:!0,attrs:{class:"token keyword"}},[s._v("val")]),s._v(" docId "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" "),a("span",{pre:!0,attrs:{class:"token string"}},[s._v('"63df6ade-d2c7-490b-9191-f56f88c8e5eb"')]),s._v("\n\n"),a("span",{pre:!0,attrs:{class:"token keyword"}},[s._v("val")]),s._v(" commercioDocReceipt "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" CommercioDocReceiptHelper"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(".")]),a("span",{pre:!0,attrs:{class:"token function"}},[s._v("fromWallet")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v("(")]),s._v("\n   wallet "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" wallet"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   recipient "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" "),a("span",{pre:!0,attrs:{class:"token function"}},[s._v("Did")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v("(")]),s._v("shareDocSender"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(")")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   txHash "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" txHash"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(",")]),s._v("\n   documentId "),a("span",{pre:!0,attrs:{class:"token operator"}},[s._v("=")]),s._v(" docId\n"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[s._v(")")]),s._v("\n")])]),s._v(" "),a("div",{staticClass:"line-numbers-wrapper"},[a("span",{staticClass:"line-number"},[s._v("1")]),a("br"),a("span",{staticClass:"line-number"},[s._v("2")]),a("br"),a("span",{staticClass:"line-number"},[s._v("3")]),a("br"),a("span",{staticClass:"line-number"},[s._v("4")]),a("br"),a("span",{staticClass:"line-number"},[s._v("5")]),a("br"),a("span",{staticClass:"line-number"},[s._v("6")]),a("br"),a("span",{staticClass:"line-number"},[s._v("7")]),a("br"),a("span",{staticClass:"line-number"},[s._v("8")]),a("br"),a("span",{staticClass:"line-number"},[s._v("9")]),a("br"),a("span",{staticClass:"line-number"},[s._v("10")]),a("br"),a("span",{staticClass:"line-number"},[s._v("11")]),a("br"),a("span",{staticClass:"line-number"},[s._v("12")]),a("br"),a("span",{staticClass:"line-number"},[s._v("13")]),a("br"),a("span",{staticClass:"line-number"},[s._v("14")]),a("br"),a("span",{staticClass:"line-number"},[s._v("15")]),a("br"),a("span",{staticClass:"line-number"},[s._v("16")]),a("br"),a("span",{staticClass:"line-number"},[s._v("17")]),a("br"),a("span",{staticClass:"line-number"},[s._v("18")]),a("br"),a("span",{staticClass:"line-number"},[s._v("19")]),a("br"),a("span",{staticClass:"line-number"},[s._v("20")]),a("br"),a("span",{staticClass:"line-number"},[s._v("21")]),a("br"),a("span",{staticClass:"line-number"},[s._v("22")]),a("br"),a("span",{staticClass:"line-number"},[s._v("23")]),a("br"),a("span",{staticClass:"line-number"},[s._v("24")]),a("br"),a("span",{staticClass:"line-number"},[s._v("25")]),a("br"),a("span",{staticClass:"line-number"},[s._v("26")]),a("br"),a("span",{staticClass:"line-number"},[s._v("27")]),a("br"),a("span",{staticClass:"line-number"},[s._v("28")]),a("br"),a("span",{staticClass:"line-number"},[s._v("29")]),a("br"),a("span",{staticClass:"line-number"},[s._v("30")]),a("br"),a("span",{staticClass:"line-number"},[s._v("31")]),a("br"),a("span",{staticClass:"line-number"},[s._v("32")]),a("br"),a("span",{staticClass:"line-number"},[s._v("33")]),a("br"),a("span",{staticClass:"line-number"},[s._v("34")]),a("br"),a("span",{staticClass:"line-number"},[s._v("35")]),a("br"),a("span",{staticClass:"line-number"},[s._v("36")]),a("br"),a("span",{staticClass:"line-number"},[s._v("37")]),a("br"),a("span",{staticClass:"line-number"},[s._v("38")]),a("br"),a("span",{staticClass:"line-number"},[s._v("39")]),a("br"),a("span",{staticClass:"line-number"},[s._v("40")]),a("br"),a("span",{staticClass:"line-number"},[s._v("41")]),a("br"),a("span",{staticClass:"line-number"},[s._v("42")]),a("br"),a("span",{staticClass:"line-number"},[s._v("43")]),a("br"),a("span",{staticClass:"line-number"},[s._v("44")]),a("br"),a("span",{staticClass:"line-number"},[s._v("45")]),a("br"),a("span",{staticClass:"line-number"},[s._v("46")]),a("br"),a("span",{staticClass:"line-number"},[s._v("47")]),a("br"),a("span",{staticClass:"line-number"},[s._v("48")]),a("br"),a("span",{staticClass:"line-number"},[s._v("49")]),a("br"),a("span",{staticClass:"line-number"},[s._v("50")]),a("br")])])])}),[],!1,null,null,null);t.default=e.exports}}]);