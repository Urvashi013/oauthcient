var pkcs11DriverUrl = 'https://localhost:1311';

var pkcs11driver = {
	getTokenList: function(callback) {
		makePKCS11DriverApiCall('GET', 'application/json', 'getTokenList', '', undefined, function(result) {
			callback(result);
		});
	},
	createKeyPair: function(serialNumber, label, description, credentialPin, algorithm, callback) {
		makePKCS11DriverApiCall('GET', 'application/json', 'createKeyPair', 'serialNumber=' + serialNumber + '&label=' + label
	+ '&description=' + description + '&credentialPin=' + credentialPin + '&algorithm=' + algorithm, undefined, function(result) {
			callback(result);
		});
	},
	getPublicKey: function(serialNumber, credentialId, callback) {
		makePKCS11DriverApiCall('GET', 'application/json', 'getPublicKey', 'serialNumber=' + serialNumber + '&credentialId=' + credentialId, undefined, function(result) {
			callback(result);
		});
	},
	sign: function(serialNumber, credentialId, credentialPin, hash, callback) {
		makePKCS11DriverApiCall('GET', 'application/json', 'sign', 'serialNumber=' + serialNumber + '&credentialId=' + credentialId + '&credentialPin=' + credentialPin + '&hash=' + hash, undefined, function(result) {
			callback(result);
		});
	},
	setCertificate: function(serialNumber, credentialId, x509Certificate, callback) {
		makePKCS11DriverApiCall('POST', 'application/json', 'setCertificate', 'serialNumber=' + serialNumber + '&credentialId=' + credentialId, x509Certificate, function(result) {
			callback(result);
		});
	},
	getCredentialList: function(serialNumber, callback) {
		makePKCS11DriverApiCall('GET', 'application/json', 'getCredentialList', 'serialNumber=' + serialNumber, undefined, function(result) {
			callback(result);
		});
	},
	deleteCredential: function(serialNumber, credentialId, tokenPin, callback) {
		makePKCS11DriverApiCall('GET', 'application/json', 'deleteCredential', 'serialNumber=' + serialNumber + '&credentialId=' + credentialId + '&tokenPin=' + tokenPin, undefined, function(result) {
			callback(result);
		});
	},
	getCertificate: function(serialNumber, credentialId, callback) {
		makePKCS11DriverApiCall('GET', 'application/json', 'getCertificate', 'serialNumber=' + serialNumber + '&credentialId=' + credentialId, undefined, function(result) {
			callback(result);
		});
	},
	changeTokenPassword: function(serialNumber, tokenPin, newTokenPin, callback) {
		makePKCS11DriverApiCall('GET', 'application/json', 'changeTokenPassword', 'serialNumber=' + serialNumber + '&tokenPin=' + tokenPin + '&newTokenPin=' + newTokenPin, undefined, function(result) {
			callback(result);
		});
	}
}

function isInnaITKeyDriverAvailable(callback) {
	makePKCS11DriverApiCall('GET', 'application/json', 'getTokenList', '', undefined, function(gdlResult) {
		callback(gdlResult.status == 0 ? true : false);
	});
}

function makePKCS11DriverApiCall(requestType, contentType, requestUrl, queryString, postData, callback) {
	var xhr = new XMLHttpRequest();
		
	xhr.open(requestType, pkcs11DriverUrl + '/' + requestUrl + '?'+ queryString, true);
	xhr.setRequestHeader("Content-type", contentType);
	
	xhr.onreadystatechange = function() { 
		if (this.readyState === XMLHttpRequest.DONE) {
			if(this.status === 200 || this.status == 204){
				result.status = 0;
				result.data = this.responseText;
			}
			else {
				result.status = -1;
				result.message = this.responseText;
			}
			
			callback(result);
		}
	}
	
	if (postData != undefined)
		xhr.send(postData);
	else
		xhr.send();
}