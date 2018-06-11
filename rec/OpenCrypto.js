'use strict';var _typeof='function'==typeof Symbol&&'symbol'==typeof Symbol.iterator?function(a){return typeof a}:function(a){return a&&'function'==typeof Symbol&&a.constructor===Symbol&&a!==Symbol.prototype?'symbol':typeof a},_createClass=function(){function a(b,c){for(var f,e=0;e<c.length;e++)f=c[e],f.enumerable=f.enumerable||!1,f.configurable=!0,'value'in f&&(f.writable=!0),Object.defineProperty(b,f.key,f)}return function(b,c,e){return c&&a(b.prototype,c),e&&a(b,e),b}}();function _classCallCheck(a,b){if(!(a instanceof b))throw new TypeError('Cannot call a class as a function')}var cryptoLib=window.crypto||window.msCrypto,cryptoApi=cryptoLib.subtle||cryptoLib.webkitSubtle,securePRNG=cryptoLib,chars='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/',lookup=new Uint8Array(256),OpenCrypto=function(){function a(){_classCallCheck(this,a);for(var b=0;b<chars.length;b++)lookup[chars.charCodeAt(b)]=b}return _createClass(a,[{key:'encodeAb',value:function encodeAb(b){for(var c=new Uint8Array(b),e=c.length,f='',g=0;g<e;g+=3)f+=chars[c[g]>>2],f+=chars[(3&c[g])<<4|c[g+1]>>4],f+=chars[(15&c[g+1])<<2|c[g+2]>>6],f+=chars[63&c[g+2]];return 2==e%3?f=f.substring(0,f.length-1)+'=':1==e%3&&(f=f.substring(0,f.length-2)+'=='),f}},{key:'decodeAb',value:function decodeAb(b){var g,j,k,l,c=0.75*b.length,e=b.length,f=0;'='===b[b.length-1]&&(c--,'='===b[b.length-2]&&c--);for(var m=new ArrayBuffer(c),n=new Uint8Array(m),o=0;o<e;o+=4)g=lookup[b.charCodeAt(o)],j=lookup[b.charCodeAt(o+1)],k=lookup[b.charCodeAt(o+2)],l=lookup[b.charCodeAt(o+3)],n[f++]=g<<2|j>>4,n[f++]=(15&j)<<4|k>>2,n[f++]=(3&k)<<6|63&l;return m}},{key:'addNewLines',value:function addNewLines(b){for(var c='';0<b.length;)c+=b.substring(0,64)+'\r\n',b=b.substring(64);return c}},{key:'removeLines',value:function removeLines(b){return b.replace(/\r?\n|\r/g,'')}},{key:'d2h',value:function d2h(b){var c=null;return'number'==typeof b?c=b.toString(16):'string'==typeof b&&(c=(b.length/2).toString(16)),c.length%2?'0'+c:c}},{key:'toAsn1',value:function toAsn1(b,c,e,f,g,j,k){var l={};b=this.arrayBufferToHexString(b),c=this.arrayBufferToHexString(c),e=this.arrayBufferToHexString(e),f=this.d2h(f);var m='06092a864886f70d01050d',n='06092a864886f70d01050c',C='02'+this.d2h(f.length/2)+f,D='0482'+this.d2h(b)+b,E='04'+this.d2h(c)+c,F='04'+this.d2h(e)+e;'AES-GCM'===j?256===k?l.CIPHER_OID='060960864801650304012e':192===k?l.CIPHER_OID='060960864801650304011a':128==k&&(l.CIPHER_OID='0609608648016503040106'):'AES-CBC'===j?256===k?l.CIPHER_OID='060960864801650304012a':192===k?l.CIPHER_OID='0609608648016503040116':128==k&&(l.CIPHER_OID='0609608648016503040102'):'AES-CFB'===j?256===k?l.CIPHER_OID='060960864801650304012c':192===k?l.CIPHER_OID='0609608648016503040118':128==k&&(l.CIPHER_OID='06086086480165030404'):void 0,'SHA-512'===g?l.HASH_OID='06082a864886f70d020b0500':'SHA-384'===g?l.HASH_OID='06082a864886f70d020a0500':'SHA-256'===g?l.HASH_OID='06082a864886f70d02090500':'SHA-1'===g?l.HASH_OID='06082a864886f70d02070500':void 0,l.SEQUENCE_AES_CONTAINER='30'+this.d2h(l.CIPHER_OID+F),l.SEQUENCE_HASH_CONTAINER='30'+this.d2h(l.HASH_OID),l.SEQUENCE_PBKDF2_INNER_CONTAINER='30'+this.d2h(E+C+l.SEQUENCE_HASH_CONTAINER+l.HASH_OID),l.SEQUENCE_PBKDF2_CONTAINER='30'+this.d2h(n+l.SEQUENCE_PBKDF2_INNER_CONTAINER+E+C+l.SEQUENCE_HASH_CONTAINER+l.HASH_OID),l.SEQUENCE_PBES2_INNER_CONTAINER='30'+this.d2h(l.SEQUENCE_PBKDF2_CONTAINER+n+l.SEQUENCE_PBKDF2_INNER_CONTAINER+E+C+l.SEQUENCE_HASH_CONTAINER+l.HASH_OID+l.SEQUENCE_AES_CONTAINER+l.CIPHER_OID+F),l.SEQUENCE_PBES2_CONTAINER='30'+this.d2h(m+l.SEQUENCE_PBES2_INNER_CONTAINER+l.SEQUENCE_PBKDF2_CONTAINER+n+l.SEQUENCE_PBKDF2_INNER_CONTAINER+E+C+l.SEQUENCE_HASH_CONTAINER+l.HASH_OID+l.SEQUENCE_AES_CONTAINER+l.CIPHER_OID+F);var G=l.SEQUENCE_PBES2_CONTAINER+m+l.SEQUENCE_PBES2_INNER_CONTAINER+l.SEQUENCE_PBKDF2_CONTAINER+n+l.SEQUENCE_PBKDF2_INNER_CONTAINER+E+C+l.SEQUENCE_HASH_CONTAINER+l.HASH_OID+l.SEQUENCE_AES_CONTAINER+l.CIPHER_OID+F,H=this.d2h(G+D),J=this.hexStringToArrayBuffer('3082'+H+G+D),K=this.arrayBufferToBase64(J);return K=this.addNewLines(K),K='-----BEGIN ENCRYPTED PRIVATE KEY-----\r\n'+K+'-----END ENCRYPTED PRIVATE KEY-----',K}},{key:'fromAsn1',value:function fromAsn1(b){var c={};b=this.removeLines(b),b=b.replace('-----BEGIN ENCRYPTED PRIVATE KEY-----',''),b=b.replace('-----END ENCRYPTED PRIVATE KEY-----',''),b=this.base64ToArrayBuffer(b);var e=this.arrayBufferToHexString(b);c.data=e;var g='06092a864886f70d01050c',j='060960864801650304012e',k='060960864801650304011a',l='0609608648016503040106',m='060960864801650304012a',n='0609608648016503040116',o='0609608648016503040102',q='060960864801650304012c',r='0609608648016503040118',s='06086086480165030404';c.data.includes('06092a864886f70d01050d')&&c.data.includes(g)&&(c.valid=!0),c.saltBegin=c.data.indexOf(g)+28,c.data.includes(j)?(c.cipher='AES-GCM',c.keyLength=256,c.ivBegin=c.data.indexOf(j)+24):c.data.includes(k)?(c.cipher='AES-GCM',c.keyLength=192,c.ivBegin=c.data.indexOf(k)+24):c.data.includes(l)?(c.cipher='AES-GCM',c.keyLength=128,c.ivBegin=c.data.indexOf(l)+24):c.data.includes(m)?(c.cipher='AES-CBC',c.keyLength=256,c.ivBegin=c.data.indexOf(m)+24):c.data.includes(n)?(c.cipher='AES-CBC',c.keyLength=192,c.ivBegin=c.data.indexOf(n)+24):c.data.includes(o)?(c.cipher='AES-CBC',c.keyLength=128,c.ivBegin=c.data.indexOf(o)+24):c.data.includes(q)?(c.cipher='AES-CFB',c.keyLength=256,c.ivBegin=c.data.indexOf(q)+24):c.data.includes(r)?(c.cipher='AES-CFB',c.keyLength=192,c.ivBegin=c.data.indexOf(r)+24):c.data.includes(s)&&(c.cipher='AES-CFB',c.keyLength=128,c.ivBegin=c.data.indexOf(s)+22),c.data.includes('06082a864886f70d020b')?c.hash='SHA-512':c.data.includes('06082a864886f70d020a')?c.hash='SHA-384':c.data.includes('06082a864886f70d0209')?c.hash='SHA-256':c.data.includes('06082a864886f70d0207')&&(c.hash='SHA-1'),c.saltLength=parseInt(c.data.substr(c.saltBegin,2),16),c.ivLength=parseInt(c.data.substr(c.ivBegin,2),16),c.salt=c.data.substr(c.saltBegin+2,2*c.saltLength),c.iv=c.data.substr(c.ivBegin+2,2*c.ivLength),c.iterBegin=c.saltBegin+4+2*c.saltLength,c.iterLength=parseInt(c.data.substr(c.iterBegin,2),16),c.iter=parseInt(c.data.substr(c.iterBegin+2,2*c.iterLength),16),c.sequenceLength=parseInt(c.data.substr(10,2),16),c.encryptedDataBegin=16+2*c.sequenceLength,c.encryptedDataLength=parseInt(c.data.substr(c.encryptedDataBegin,4),16),c.encryptedData=c.data.substr(c.encryptedDataBegin+4,2*c.encryptedDataLength);var x={salt:this.hexStringToArrayBuffer(c.salt),iv:this.hexStringToArrayBuffer(c.iv),cipher:c.cipher,keyLength:c.keyLength,hash:c.hash,iter:c.iter,encryptedData:this.hexStringToArrayBuffer(c.encryptedData)};return x}},{key:'arrayBufferToString',value:function arrayBufferToString(b){if('object'!==('undefined'==typeof b?'undefined':_typeof(b)))throw new TypeError('Expected input to be an ArrayBuffer Object');var c=new TextDecoder('utf-8');return c.decode(b)}},{key:'stringToArrayBuffer',value:function stringToArrayBuffer(b){if('string'!=typeof b)throw new TypeError('Expected input to be a String');var c=new TextEncoder('utf-8'),e=c.encode(b);return e.buffer}},{key:'arrayBufferToHexString',value:function arrayBufferToHexString(b){if('object'!==('undefined'==typeof b?'undefined':_typeof(b)))throw new TypeError('Expected input to be an ArrayBuffer Object');for(var c=new Uint8Array(b),e='',f=void 0,g=0;g<c.byteLength;g++)f=c[g].toString(16),2>f.length&&(f='0'+f),e+=f;return e}},{key:'hexStringToArrayBuffer',value:function hexStringToArrayBuffer(b){if('string'!=typeof b)throw new TypeError('Expected input of hexString to be a String');if(0!=b.length%2)throw new RangeError('Expected string to be an even number of characters');for(var c=new Uint8Array(b.length/2),e=0;e<b.length;e+=2)c[e/2]=parseInt(b.substring(e,e+2),16);return c.buffer}},{key:'arrayBufferToBase64',value:function arrayBufferToBase64(b){if('object'!==('undefined'==typeof b?'undefined':_typeof(b)))throw new TypeError('Expected input to be an ArrayBuffer Object');return this.encodeAb(b)}},{key:'base64ToArrayBuffer',value:function base64ToArrayBuffer(b){if('string'!=typeof b)throw new TypeError('Expected input to be a base64 String');return this.decodeAb(b)}},{key:'getKeyPair',value:function getKeyPair(b,c,e,f){return b='undefined'==typeof b?2048:b,c='undefined'==typeof c?['encrypt','decrypt','wrapKey','unwrapKey']:c,e='undefined'==typeof e?'SHA-512':e,f='undefined'==typeof f||f,new Promise(function(g,j){if('number'!=typeof b)throw new TypeError('Expected input of bits to be a Number');if('object'!==('undefined'==typeof c?'undefined':_typeof(c)))throw new TypeError('Expected input of usage to be an Array');if('string'!=typeof e)throw new TypeError('Expected input of algo expected to be a String');if('boolean'!=typeof f)throw new TypeError('Expected input of extractable to be a Boolean');cryptoApi.generateKey({name:'RSA-OAEP',modulusLength:b,publicExponent:new Uint8Array([1,0,1]),hash:{name:e}},f,c).then(function(k){g(k)}).catch(function(k){j(k)})})}},{key:'cryptoPrivateToPem',value:function cryptoPrivateToPem(b){var c=this;return new Promise(function(e,f){if('object'!==('undefined'==typeof b?'undefined':_typeof(b)))throw new TypeError('Expected input to be a CryptoKey Object');cryptoApi.exportKey('pkcs8',b).then(function(g){var j=c.arrayBufferToBase64(g),k=c.addNewLines(j);k='-----BEGIN PRIVATE KEY-----\r\n'+k+'-----END PRIVATE KEY-----',e(k)}).catch(function(g){f(g)})})}},{key:'pemPrivateToCrypto',value:function pemPrivateToCrypto(b){var c=this;return new Promise(function(e,f){if('string'!=typeof b)throw new TypeError('Expected input of PEM to be a String');b=b.replace('-----BEGIN PRIVATE KEY-----','');var g=b.replace('-----END PRIVATE KEY-----','');g=c.removeLines(g);var j=c.base64ToArrayBuffer(g);cryptoApi.importKey('pkcs8',j,{name:'RSA-OAEP',hash:{name:'SHA-512'}},!0,['decrypt','unwrapKey']).then(function(k){e(k)}).catch(function(k){f(k)})})}},{key:'cryptoPublicToPem',value:function cryptoPublicToPem(b){var c=this;return new Promise(function(e,f){if('object'!==('undefined'==typeof b?'undefined':_typeof(b)))throw new TypeError('Expected input to be a CryptoKey Object');cryptoApi.exportKey('spki',b).then(function(g){var j=c.arrayBufferToBase64(g),k=c.addNewLines(j);k='-----BEGIN PUBLIC KEY-----\r\n'+k+'-----END PUBLIC KEY-----',e(k)}).catch(function(g){f(g)})})}},{key:'pemPublicToCrypto',value:function pemPublicToCrypto(b){var c=this;return new Promise(function(e,f){if('string'!=typeof b)throw new TypeError('Expected input of PEM to be a String');b=c.removeLines(b),b=b.replace('-----BEGIN PUBLIC KEY-----','');var g=b.replace('-----END PUBLIC KEY-----',''),j=c.base64ToArrayBuffer(g);cryptoApi.importKey('spki',j,{name:'RSA-OAEP',hash:{name:'SHA-512'}},!0,['encrypt','wrapKey']).then(function(k){e(k)}).catch(function(k){f(k)})})}},{key:'encryptPrivateKey',value:function encryptPrivateKey(b,c,e,f,g,j){e='undefined'==typeof e?64000:e,f='undefined'==typeof f?'SHA-512':f,g='undefined'==typeof g?'AES-CBC':g,j='undefined'==typeof j?256:j;var k=this;return new Promise(function(l,m){if('object'!==('undefined'==typeof b?'undefined':_typeof(b)))throw new TypeError('Expected input of privateKey to be a CryptoKey Object');if('string'!=typeof c)throw new TypeError('Expected input of passphrase to be a String');if('number'!=typeof e)throw new TypeError('Expected input of iterations to be a Number');if('string'!=typeof f)throw new TypeError('Expected input of iterations to be a String');var n=null;'AES-GCM'===g?n=12:'AES-CBC'===g?n=16:'AES-CFB'==g&&(n=16);var o=securePRNG.getRandomValues(new Uint8Array(16)),q=securePRNG.getRandomValues(new Uint8Array(n));cryptoApi.importKey('raw',k.stringToArrayBuffer(c),{name:'PBKDF2'},!1,['deriveKey']).then(function(r){cryptoApi.deriveKey({name:'PBKDF2',salt:o,iterations:e,hash:f},r,{name:g,length:j},!0,['wrapKey']).then(function(s){cryptoApi.wrapKey('pkcs8',b,s,{name:g,iv:q}).then(function(t){var u=k.toAsn1(t,o,q,e,f,g,j);l(u)}).catch(function(t){m(t)})}).catch(function(s){m(s)})}).catch(function(r){m(r)})})}},{key:'decryptPrivateKey',value:function decryptPrivateKey(b,c){var e=this;return new Promise(function(f,g){if('string'!=typeof b)throw new TypeError('Expected input of encryptedPrivateKey to be a base64 String');if('string'!=typeof c)throw new TypeError('Expected input of passphrase to be a String');var j=e.fromAsn1(b,e);cryptoApi.importKey('raw',e.stringToArrayBuffer(c),{name:'PBKDF2'},!1,['deriveKey']).then(function(k){cryptoApi.deriveKey({name:'PBKDF2',salt:j.salt,iterations:j.iter,hash:j.hash},k,{name:j.cipher,length:j.keyLength},!0,['unwrapKey']).then(function(l){cryptoApi.unwrapKey('pkcs8',j.encryptedData,l,{name:j.cipher,iv:j.iv},{name:'RSA-OAEP',hash:{name:j.hash}},!0,['decrypt','unwrapKey']).then(function(m){f(m)}).catch(function(m){g(m)})}).catch(function(l){g(l)})}).catch(function(k){g(k)})})}},{key:'encryptPublic',value:function encryptPublic(b,c){var e=this;return new Promise(function(f,g){if('[object CryptoKey]'!==Object.prototype.toString.call(b)&&'public'!==b.type)throw new TypeError('Expected input of privateKey to be a CryptoKey of type public');if('string'!=typeof c)throw new TypeError('Expected input of data to be a String');cryptoApi.encrypt({name:'RSA-OAEP'},b,e.stringToArrayBuffer(c)).then(function(j){f(e.arrayBufferToBase64(j))}).catch(function(j){g(j)})})}},{key:'decryptPrivate',value:function decryptPrivate(b,c){var e=this;return new Promise(function(f,g){if('[object CryptoKey]'!==Object.prototype.toString.call(b)&&'private'!==b.type)throw new TypeError('Expected input of privateKey to be a CryptoKey of type private');if('string'!=typeof c)throw new TypeError('Expected input of encryptedData to be a String');cryptoApi.decrypt({name:'RSA-OAEP'},b,e.base64ToArrayBuffer(c)).then(function(j){f(e.arrayBufferToString(j))}).catch(function(j){g(j)})})}},{key:'encryptKey',value:function encryptKey(b,c,e){e='undefined'==typeof e?'SHA-512':e;var f=this;return new Promise(function(g,j){if('[object CryptoKey]'!==Object.prototype.toString.call(b)&&'public'!==b.type)throw new TypeError('Expected input of publicKey to be a CryptoKey of type public');if('[object CryptoKey]'!==Object.prototype.toString.call(c)&&'secret'!==c.type)throw new TypeError('Expected input of sharedKey to be a CryptoKey of type secret');cryptoApi.wrapKey('raw',c,b,{name:'RSA-OAEP',hash:{name:e}}).then(function(k){g(f.arrayBufferToBase64(k))}).catch(function(k){j(k)})})}},{key:'decryptKey',value:function decryptKey(b,c,e,f,g,j){e='undefined'==typeof e?'AES-GCM':e,f='undefined'==typeof f?256:f,g='undefined'==typeof g?2048:g,j='undefined'==typeof j?'SHA-512':j;var k=this;return new Promise(function(l,m){if('[object CryptoKey]'!==Object.prototype.toString.call(b)&&'private'!==b.type)throw new TypeError('Expected input of privateKey to be a CryptoKey of type private');if('string'!=typeof c)throw new TypeError('Expected input of encryptedSharedKey to be a base64 String');if('string'!=typeof e)throw new TypeError('Expected input of cipherSuite to be a String');if('number'!=typeof f)throw new TypeError('Expected input of keyLength to be a Number');if('number'!=typeof g)throw new TypeError('Expected input of privateKeyLength to be a Number');if('string'!=typeof j)throw new TypeError('Expected input of privateKeyHash to be a String');cryptoApi.unwrapKey('raw',k.base64ToArrayBuffer(c),b,{name:'RSA-OAEP',modulusLength:g,publicExponent:new Uint8Array([1,0,1]),hash:{name:j}},{name:e,length:f},!0,['encrypt','decrypt']).then(function(n){l(n)}).catch(function(n){m(n)})})}},{key:'getSharedKey',value:function getSharedKey(b,c,e,f){return b='undefined'==typeof b?256:b,c='undefined'==typeof c?['encrypt','decrypt','wrapKey','unwrapKey']:c,e='undefined'==typeof e||e,f='undefined'==typeof f?'AES-GCM':f,new Promise(function(g,j){if('number'!=typeof b)throw new TypeError('Expected input of bits to be a Number');if('object'!==('undefined'==typeof c?'undefined':_typeof(c)))throw new TypeError('Expected input of usage to be an Array');if('boolean'!=typeof e)throw new TypeError('Expected input of extractable expected to be a Boolean');if('string'!=typeof f)throw new TypeError('Expected input of cipherMode expected to be a String');cryptoApi.generateKey({name:f,length:b},e,c).then(function(k){g(k)}).catch(function(k){j(k)})})}},{key:'encrypt',value:function encrypt(b,c){var e=this;return new Promise(function(f,g){if('object'!==('undefined'==typeof b?'undefined':_typeof(b)))throw new TypeError('Expected input of sessionKey to be a CryptoKey Object');if('string'!=typeof c)throw new TypeError('Expected input of data to be a String');var j=securePRNG.getRandomValues(new Uint8Array(12));cryptoApi.encrypt({name:'AES-GCM',iv:j,tagLength:128},b,e.base64ToArrayBuffer(c)).then(function(k){var l=e.arrayBufferToBase64(j),m=e.arrayBufferToBase64(k);f(l+m)}).catch(function(k){g(k)})})}},{key:'decrypt',value:function decrypt(b,c){var e=this;return new Promise(function(f,g){if('object'!==('undefined'==typeof b?'undefined':_typeof(b)))throw new TypeError('Expected input of sessionKey to be a CryptoKey Object');if('string'!=typeof c)throw new TypeError('Expected input of encryptedData to be a String');var j=c.substring(0,16),k=c.substring(16),l=e.base64ToArrayBuffer(j),m=e.base64ToArrayBuffer(k);cryptoApi.decrypt({name:'AES-GCM',iv:l,tagLength:128},b,m).then(function(n){f(e.arrayBufferToBase64(n))}).catch(function(n){g(n)})})}},{key:'keyFromPassphrase',value:function keyFromPassphrase(b,c,e,f){e='undefined'==typeof e?3e5:e,f='undefined'==typeof f?'SHA-512':f;var g=this;return new Promise(function(j,k){if('string'!=typeof b)throw new TypeError('Expected input of passphrase to be a String');if('string'!=typeof c)throw new TypeError('Expected input of salt to be a String');if('number'!=typeof e)throw new TypeError('Expected input of iterations to be a Number');if('string'!=typeof f)throw new TypeError('Expected input of alg to be a String');cryptoApi.importKey('raw',g.stringToArrayBuffer(b),{name:'PBKDF2'},!1,['deriveKey']).then(function(l){cryptoApi.deriveKey({name:'PBKDF2',salt:g.stringToArrayBuffer(c),iterations:e,hash:f},l,{name:'AES-GCM',length:256},!0,['encrypt','decrypt','wrapKey','unwrapKey']).then(function(m){cryptoApi.exportKey('raw',m).then(function(n){j(g.arrayBufferToHexString(n))}).catch(function(n){k(n)})}).catch(function(m){k(m)})}).catch(function(l){k(l)})})}},{key:'cryptoKeyToFingerprint',value:function cryptoKeyToFingerprint(b,c){c='undefined'==typeof c?'SHA-1':c;var e=this;return new Promise(function(f,g){if('object'!==('undefined'==typeof b?'undefined':_typeof(b)))throw new TypeError('Expected input of key to be a CryptoKey Object');if('string'!=typeof c)throw new TypeError('Expected input of hash to be a String');var j=null;j='public'===b.type?'spki':'pkcs8',cryptoApi.exportKey(j,b).then(function(k){cryptoApi.digest({name:c},k).then(function(l){f(e.arrayBufferToHexString(l).toUpperCase().replace(/(.{4})/g,'$1 ').trim())}).catch(function(l){g(l)})}).catch(function(k){g(k)})})}},{key:'getRandomSalt',value:function getRandomSalt(b){b='undefined'==typeof b?16:b;var c=this;return new Promise(function(e){if('number'!=typeof b)throw new TypeError('Expected input of size to be a Number');var g=securePRNG.getRandomValues(new Uint8Array(b)),j=c.arrayBufferToHexString(g);e(j)})}},{key:'getEcKeyPair',value:function getEcKeyPair(b){return b='undefined'==typeof b?'P-256':b,new Promise(function(c,e){if('string'!=typeof b)throw new TypeError('Expected input of curve to be a String');cryptoApi.generateKey({name:'ECDH',namedCurve:b},!0,['deriveKey','deriveBits']).then(function(f){c(f)}).catch(function(f){e(f)})})}}]),a}();