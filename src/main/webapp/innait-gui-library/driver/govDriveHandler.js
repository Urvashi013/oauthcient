var govDriveUrl = 'https://localhost', result = { status: -1, message: '', data: '' };

var govDriveHandler = {
	getLocalFileList : function (path, callback) {
		makeGovDriveRequestApiCall('file/getLocalFileList', 'GET', '', true, 'path=' + path, '', false, function(response) {
			callback(response);
		});
	},
	
	login : function (tokenId, codeVerifier ,callback) {
		makeGovDriveRequestApiCall('govDrive/login', 'POST', '', true, 'tokenId=' + tokenId + '&codeVerifier=' + codeVerifier, '', true, function(response) {
			callback(response);
		});
	},
	
	encryptFile : function (inputFilePath, outputFilePath, fileEncryptionType, callback) {
		makeGovDriveRequestApiCall('govDrive/encryptFile', 'POST', '', true,'inputFilePath=' + inputFilePath + '&outputFilePath=' + outputFilePath + '&fileEncryptionType=' + fileEncryptionType, '', true, function(response) {
			callback(response);
		});
	},
	
	decryptFile : function (inputFilePath, outputFilePath, metaData, callback) {
		makeGovDriveRequestApiCall('govDrive/decryptFile', 'POST', '', true,'inputFilePath=' + inputFilePath + '&outputFilePath=' + outputFilePath, metaData, true, function(response) {
			callback(response);
		});
	},
	
	shareFile : function (userRef, metaData, callback) {
		makeGovDriveRequestApiCall('govDrive/shareFile', 'POST', '', true,'userRef=' + userRef, metaData, true, function(response) {
			callback(response);
		});
	},
	
	logout : function (callback) {
		makeGovDriveRequestApiCall('govDrive/logout', 'POST', '', true, '', '', true, function(response) {
			callback(response);
		});
	},
	
	encryptMultipartFile : function (file, callback) {
		makeGovDriveRequestApiCall('des/encryptMultipartFile', 'POST', false, false, '', file, false, function(response) {
			callback(response);
		});
	},
	
	delete : function (filePath, callback) {
		makeGovDriveRequestApiCall('file/delete', 'GET', '', true,'filePath=' + filePath, '', false, function(response) {
			callback(response);
		});
	}
}

function makeGovDriveRequestApiCall(requestUrl, requestType, contType, isProcessData, queryData, postData, codeWaiting, callback) {
	let timestamp = getTime('ms');
	let restApiMethodUrl = govDriveUrl + '/' + requestUrl + '?timestamp=' + timestamp;
	
	if (contType === '')
		contType = "application/json";
	
	if (queryData != '')
		restApiMethodUrl = restApiMethodUrl + '&' + queryData;
	
	$.ajax({ url: restApiMethodUrl, type: requestType, crossDomain: true, xhrFields: { withCredentials: true }, async: codeWaiting,
		data: postData, contentType: contType, processData: isProcessData,
		success: function (response, status, xhr) {
			result.status = 0;
			result.data = response;
			result.message = '';
			callback(result);
		},
		error: function (jqXHR, textStatus, errorThrown) {
			result.status = -1;
			result.data = '';
			result.message = FormatGovDriveErrorMessage(jqXHR);
			callback(result);
		}
	});
}

function FormatGovDriveErrorMessage(jqXHR) {
   	if (jqXHR.status === 0)
         return ('Not connected. Verify your network connection');
   	else if (jqXHR.status == 200)
         return (jqXHR.responseText);
   	else if (jqXHR.status == 401)
         return ('Access denied');
   	else if (jqXHR.status == 403)
         return ('Forbidden');
   	else if (jqXHR.status == 404)
         return ('The requested page not found');
   	else if (jqXHR.status == 500)
		return ('Internal Server Error');
	else if (jqXHR.status == 503)
     	return ('Service Unavailable');
	else {
 		if (jqXHR.status != "" && jqXHR.status != null)
        	return jqXHR.responseText;
     	else 
        	return ('Uncaught Error. ' + jqXHR.responseText);
	}
}