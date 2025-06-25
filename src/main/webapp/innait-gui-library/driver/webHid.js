var hidDefaults = [{ 'vendorId': 13196, 'productId': 1 }, { 'vendorId': 13196, 'productId': 2 }, 
		{ 'vendorId': 13196, 'productId': 3 }, { 'vendorId': 13196, 'productId': 4 }, 
		{ 'vendorId': 13196, 'productId': 5 }, { 'vendorId': 13196, 'productId': 6 }, 
		{ 'vendorId': 13196, 'productId': 7 }, { 'vendorId': 13196, 'productId': 8 }, 
		{ 'vendorId': 13196, 'productId': 9 }, { 'vendorId': 13196, 'productId': 10 },
		{ 'vendorId': 13196, 'productId': 11 }, { 'vendorId': 13196, 'productId': 12 },
		{ 'vendorId': 13196, 'productId': 13 }, { 'vendorId': 13196, 'productId': 14 },
		{ 'vendorId': 13196, 'productId': 15 }, { 'vendorId': 13196, 'productId': 16 }, 
		{ 'vendorId': 13196, 'productId': 33 }, { 'vendorId': 13196, 'productId': 34 }], 
	hidDefaultAdminInterface = 0,
	ctapDefaults = {
		CID_BROADCAST: new Uint8Array([0xff, 0xff, 0xff, 0xff]),
		COMMAND_GET_CHANNELID: [0x86],
		COMMAND_CBOR: [0x90],
		COMMAND_KEY: [0xD6],
		COMMAND_GET_VERSION: [0xD7],
		COMMAND_TPM_BINARY: [0xD8],
		COMMAND_TPM_PKCS11: [0xD9],
		PKT_CHANNELID: new Uint8Array([0xa1, 0x82, 0x66, 0xaf, 0x6d, 0x1b, 0xb4, 0x37]),
		REPORT_SIZE: 64,
		payloadPosition: 7,
		COMMAND_INNAIT_INFO: [0x40, 0x00, 0x00, 0x65, 0x01, 0x00, 0x05, 0x00, 0x60, 0x00, 0x01, 0x60, 0x00, 0x00, 0x00, 0x00, 0x02, 0x60, 0x00, 0x0d, 0x67, 0x65, 0x74, 0x44, 0x65, 0x76, 0x69, 0x63, 0x65, 0x49, 0x6e, 0x66, 0x6f, 0x00, 0x00, 0x03, 0x60, 0x00, 0x10, 0x46, 0x46, 0x38, 0x41, 0x38, 0x42, 0x32, 0x42, 0x30,0x43, 0x39, 0x35, 0x31, 0x34, 0x34, 0x32, 0x00, 0x00, 0x06, 0x00, 0x60, 0xe5, 0x44, 0x01, 0x00, 0x07, 0x60, 0x00, 0x24, 0x37, 0x61, 0x37, 0x65, 0x39, 0x39, 0x61, 0x32, 0x2d, 0x33, 0x32, 0x39, 0x64, 0x2d, 0x34, 0x31, 0x31, 0x30, 0x2d, 0x61, 0x35, 0x65, 0x34, 0x2d, 0x32, 0x31, 0x34, 0x34, 0x35, 0x36, 0x66, 0x66, 0x65, 0x39, 0x36, 0x31],
		COMMAND_CERTIFICATE_INFO: [0x40,0x00,0x00,0x72,0x01,0x02,0x05,0x00,0x6d,0x00,0x01,0x60,0x00,0x06,0x72,0x64,0x70,0x2d,0x76,0x31,0x00,0x00,0x02,0x60,0x00,0x14,0x67,0x65,0x74,0x44,0x65,0x76,0x69,0x63,0x65,0x43,0x65,0x72,0x74,0x69,0x66,0x69,0x63,0x61,0x74,0x65,0x00,0x00,0x03,0x60,0x00,0x10,0x41,0x44,0x44,0x31,0x45,0x31,0x34,0x35,0x42,0x39,0x43,0x43,0x33,0x33,0x35,0x31,0x00,0x00,0x06,0x00,0x63,0x98,0x10,0x61,0x00,0x07,0x60,0x00,0x24,0x63,0x38,0x31,0x61,0x39,0x36,0x61,0x39,0x2d,0x65,0x31,0x38,0x33,0x2d,0x34,0x31,0x39,0x30,0x2d,0x62,0x65,0x39,0x32,0x2d,0x35,0x63,0x61,0x64,0x38,0x34,0x63,0x64,0x31,0x35,0x33,0x63,0x00],
		COMMAND_ISSUER_LIST: [0x40,0x00,0x00,0xc3,0x01,0x02,0x05,0x00,0x77,0x00,0x01,0x60,0x00,0x23,0x70,0x72,0x65,0x63,0x69,0x73,0x69,0x6f,0x6e,0x2d,0x6d,0x61,0x6e,0x75,0x66,0x61,0x63,0x74,0x75,0x72,0x65,0x2e,0x69,0x6e,0x6e,0x61,0x69,0x74,0x6b,0x65,0x79,0x2e,0x63,0x6f,0x6d,0x00,0x00,0x02,0x60,0x00,0x0d,0x67,0x65,0x74,0x49,0x73,0x73,0x75,0x65,0x72,0x4c,0x69,0x73,0x74,0x00,0x00,0x03,0x60,0x00,0x10,0x42,0x34,0x30,0x34,0x44,0x42,0x43,0x37,0x31,0x39,0x46,0x38,0x34,0x33,0x36,0x38,0x00,0x00,0x06,0x00,0x00,0x02,0xfb,0xae,0x00,0x07,0x60,0x00,0x18,0x36,0x33,0x39,0x38,0x37,0x63,0x62,0x34,0x63,0x30,0x62,0x66,0x32,0x31,0x32,0x62,0x33,0x30,0x33,0x31,0x61,0x37,0x33,0x38,0x00,0x30,0x45,0x02,0x20,0x41,0x3a,0x03,0x77,0x4c,0xf1,0x36,0x8c,0xa9,0x1a,0x33,0x08,0xfa,0x77,0x34,0xbb,0xa8,0xb6,0x47,0x43,0x8a,0x4f,0xa7,0x64,0xaa,0x3f,0xd3,0x90,0xd3,0x0c,0xb4,0xf8,0x02,0x21,0x00,0xc3,0x4e,0xdd,0xbd,0xdc,0x49,0x60,0x13,0x24,0x83,0xca,0x71,0xab,0x51,0xf3,0xf7,0x43,0xda,0xa5,0x48,0x44,0xc4,0x36,0x6f,0x1d,0x11,0x9a,0x19,0x52,0x4d,0xb5,0x1e]
	}, 
	hidPluggedInCallback, hidPluggedOutCallback, hidDeviceList = [], hidOutputBuffer = [], hidKeyResponseReceived = false, hidTotalResponseBuffer;

function hidConnectEvent(e) {
	//console.log("HID connected", e);
	
	if (e.device.collections.length > 0) {
		if (e.device.collections[0].inputReports.length > 0) {
			if (document.getElementById('innaitHidModal'))
				document.getElementById("innaitHidModal").classList.add("hidden");
				
			if (e.device != undefined) {
				addHidDevice(e.device);
								
				hidGetResponse(e.device.vendorId, e.device.productId, e.device.productName, 'open', undefined, function(responseData) {
					if ( typeof hidPluggedInCallback == 'function' )
						hidPluggedInCallback(e.device.vendorId, e.device.productId, getIKSNFromPN(e.device.productName));
				});
			}
		}
	}
}

function hidDisconnectEvent(e) {
	//console.log("HID disconnected", e);
	
	if (e.device.collections.length > 0) {
		if (e.device.collections[0].inputReports.length > 0) {
			if ( typeof hidPluggedOutCallback == 'function' )
				hidPluggedOutCallback(e.device.vendorId, e.device.productId, getIKSNFromPN(e.device.productName));
			
			let index = getHidDevice(e.device.vendorid, e.device.productId, e.device.productName);
			hidDeviceList.splice(index, 1);
			hidClose(e.device);
		}
	}
}

function initializeHidDriver(hidPluggedInCallbackMethod, hidPluggedOutCallbackMethod) {
	if (hidPluggedInCallbackMethod != undefined)
		hidPluggedInCallback = hidPluggedInCallbackMethod;
	
	if (hidPluggedOutCallbackMethod != undefined)
		hidPluggedOutCallback = hidPluggedOutCallbackMethod;
				
	navigator.hid.addEventListener("connect", hidConnectEvent);
	navigator.hid.addEventListener("disconnect", hidDisconnectEvent);
	 
	connnectHidDevice();
}

async function requestHidDevice(e) {
	if (document.getElementById('innaitHidModal'))
		document.getElementById("innaitHidModal").classList.add("hidden");
		
	// Prompt user to select a device.
	const devices = await navigator.hid.requestDevice({ filters: hidDefaults, includeHidDevices: true }); 
	
	if (devices.length > 0) {
		for (var i = 0; i < devices.length; i++) {
			if (devices[i].collections.length > 0) {
				if (devices[i].collections[0].inputReports.length > 0) {
					addHidDevice(devices[i]);
			
					let event_data = { 'device': devices[i] };
					hidConnectEvent(event_data);
				}
			}
		}
	}
}

async function connnectHidDevice() {
	try {
		// Get all devices the user has previously granted the website access to.
		const devices = await navigator.hid.getDevices();
		
		if (devices.length > 0) {
			let funUpload, uploadInterval, uploadCount = 0;
		
			funUpload = function () {
				if (uploadInterval != undefined)
					clearInterval(uploadInterval);
				
				for(var j = 0; j < hidDefaults.length; j++) {
					if (devices[uploadCount].collections.length > 0) {
						if (devices[uploadCount].collections[0].inputReports.length > 0) {
							if (devices[uploadCount].vendorId == hidDefaults[j].vendorId && devices[uploadCount].productId == hidDefaults[j].productId) {
								addHidDevice(devices[uploadCount]);
								
								let event_data = { 'device': devices[uploadCount] };
								hidConnectEvent(event_data);
								
								j = hidDefaults.length;
							}
						}
					}
				}				
									
				uploadCount = uploadCount + 1;
									
				if(uploadCount < devices.length)
					uploadInterval = setInterval(funUpload, 1000);
				
			};
			
			uploadInterval = setInterval(funUpload, 0);
		}
	} catch (error) {}
}

function showHidRequestModal() {
	if (document.getElementById('innaitHidModal'))
		document.getElementById("innaitHidModal").classList.remove("hidden");
	else {
		var css = '.js-modal { text-align: center; justify-content: center; font-family: "Inter", sans-serif; padding: 1.3rem; border: 1px solid #f7f7f7; border-radius: 5px; top: 20%; left: 50%; position: absolute; background-color: #f9f9f9; z-index: 9; transform: translate(-50%, 50%); } ' +
		' .js-modal h5 { margin-top: -10px; margin-bottom: 5px; font-size: 12px; } ' +
		' .js-modal .flex { text-align: right; margin: 10px 0 0 0; } ' +
		' .js-modal .js-modal-content { margin-top: -30px; }' + 
		' .js-modal button { cursor: pointer; border: none; font-weight: 600; } ' +
		' .js-modal .btn { display: inline-block; padding: 0.8rem 1.4rem; font-weight: 700; background-color: black; color: white; border-radius: 5px; text-align: center; font-size: 1em; } ' +
		' .js-modal .btn-close { transform: translate(10px, -20px); padding: 0px 7px 2px 7px; background: #666666; border-radius: 50%; color: #fff; } ' +
		' @keyframes blink-text { 0% { color: black; opacity: 1; } 20% { color: black;opacity: .8; } 30% { color: black;opacity: .6; } 40% { color: black;opacity: .4; } 50% { color: black;opacity: .2; } 60% { color: black;opacity: 0; } 70% { color: black;opacity: .2; } 80% { color: black;opacity: .4; } 90% { color: black;opacity: .6; } 98% { color: black;opacity: .8; } 100% { color: black;opacity: 1; } } ' +
		' .js-modal .blinktext { -webkit-animation: blink-text 800ms linear infinite; -moz-animation: blink-text 800ms linear infinite; -ms-animation: blink-text 800ms linear infinite; -o-animation: blink-text 800ms linear infinite; animation: blink-text 2000ms linear infinite; } ' +
		' .hidden { display: none; }',
		head = document.head || document.getElementsByTagName('head')[0],
		style = document.createElement('style');
		head.appendChild(style);
		style.type = 'text/css';
		style.appendChild(document.createTextNode(css));	
		
		hidModal = document.createElement('div');
		
		hidModal.innerHTML =
		'<div class="js-modal" id="innaitHidModal">' +
		'<div class="js-modal-content" id="jsModalContent"></div>' +
		'</div>';
		
		document.body.appendChild(hidModal);
		
		var html =
		'<div>' +
			'<div class="flex">' +
				'<button class="btn-close" id="btnCloseHid">x</button>' +
			'</div>' +
			'<div>' +
				'<h5 class="blinktext">Give access to your device</h5>' +
			'</div>' +
			'<div>' +
				'<button class="btn" id="btnEnableHid">Pair InnaIT Authenticator</button>' +
			'</div>' +
		'</div>';
		
		document.getElementById("jsModalContent").innerHTML = html;
				
		document.getElementById('btnEnableHid').onclick = function(){ requestHidDevice() };
		document.getElementById('btnCloseHid').onclick = function(){ document.getElementById("innaitHidModal").classList.add("hidden"); };
		document.body.addEventListener("click", function (evt) {
			document.getElementById("innaitHidModal").classList.add("hidden"); 
		});
	}
}

async function handleHidInputReport(e) {
	let responseBuffer = new Uint8Array(e.data.buffer);
	//console.log('Received from device ' + convertBase.bytesToHex(responseBuffer).replace(/.{2}/g, '$& '));
	
	let irVendorId = e.device.vendorId, irProductId = e.device.productId, irSerialNumber = e.device.productName;
	let index = getHidDevice(irVendorId, irProductId, irSerialNumber); 
	let objHid = hidDeviceList[index]; 
	
	if (objHid.channel == undefined) {
		let commandId = responseBuffer[4];
		
		if (commandId == convertBase.decTohex(ctapDefaults.COMMAND_GET_CHANNELID)) {
			const sliced = new Uint8Array(responseBuffer.slice(15, 19));
			hidDeviceList[index].channel = sliced;
			
			for(var i = 0; i < hidOutputBuffer.length; i++) {
				if (hidOutputBuffer[i].vendorId == irVendorId && hidOutputBuffer[i].productId == irProductId 
				&& (hidOutputBuffer[i].serialNumber == getIKSNFromPN(irSerialNumber) || hidOutputBuffer[i].serialNumber == irSerialNumber)) {
					hidOutputBuffer[i].buffer = '';
				}
			}
		}
	}
	else {
		const slicedChannelBuffer = new Uint8Array(responseBuffer.slice(0, 4));
		let responseChannelId = convertBase.hexTodec(convertBase.bytesToHex(slicedChannelBuffer));
		let setChannelId = convertBase.hexTodec(convertBase.bytesToHex(objHid.channel));
		
		if (setChannelId == responseChannelId) {
			if (objHid.numberOfBytesToRead == 0) {
				if (responseBuffer[4] == 187) {}
				else {
					const lenSliced = new Uint8Array(responseBuffer.slice(5, 7));
					let numberOfBytesToRead = convertBase.hexTodec(convertBase.bytesToHex(lenSliced));
					hidTotalResponseBuffer = new Uint8Array(1);
					hidTotalResponseBuffer = new Uint8Array(numberOfBytesToRead + 64);
									
					const resSliced = new Uint8Array(responseBuffer.slice(7, 64));
					hidTotalResponseBuffer.set(resSliced, 0);
					
					hidDeviceList[index].responseSequence = 57;
					hidDeviceList[index].bytesReceived = ctapDefaults.REPORT_SIZE - 7;
					hidDeviceList[index].numberOfBytesToRead = numberOfBytesToRead;
					hidDeviceList[index].reportBuffer = hidTotalResponseBuffer;
				}
			}
			else {
				let hidTotalResponseBuffer = new Uint8Array(objHid.numberOfBytesToRead + 64);
				hidTotalResponseBuffer = objHid.reportBuffer;
				
				const resSliced = new Uint8Array(responseBuffer.slice(5, 64));
				hidTotalResponseBuffer.set(resSliced, objHid.responseSequence);
				
				hidDeviceList[index].responseSequence = objHid.responseSequence + 59;
				hidDeviceList[index].bytesReceived = objHid.bytesReceived + (ctapDefaults.REPORT_SIZE - 5);
				hidDeviceList[index].reportBuffer = hidTotalResponseBuffer;
			}
						
			if (objHid.bytesReceived >= objHid.numberOfBytesToRead && objHid.numberOfBytesToRead > 0) {
				hidKeyResponseReceived = true;
				
				for(i = 0; i < hidOutputBuffer.length; i++) {
					if (hidOutputBuffer[i].vendorId == irVendorId && hidOutputBuffer[i].productId == irProductId 
					&& (hidOutputBuffer[i].serialNumber == getIKSNFromPN(irSerialNumber) || hidOutputBuffer[i].serialNumber == irSerialNumber)) {
						hidOutputBuffer[i].buffer = hidDeviceList[index].reportBuffer;
					}
				}
				
				hidDeviceList[index].responseSequence = 0;
				hidDeviceList[index].reportBuffer = undefined;
				hidDeviceList[index].numberOfBytesToRead = 0;
				hidDeviceList[index].bytesReceived = 0;
				
				let device = objHid.device;
				await device.close();
			}
		}
	}	
}

function addHidDevice(device) {
	let index = getHidDevice(device.vendorId, device.productId, device.productName);
	
	if (index != undefined)
		hidDeviceList.splice(index, 1);
	
	hidDeviceList.push({ 'device': device, 'channel': undefined, 'responseSequence': 0, 'reportBuffer': undefined, 'numberOfBytesToRead': 0, 'bytesReceived': 0 });
	device.addEventListener('inputreport', handleHidInputReport);
}

function getHidDevice(vendorId, productId, productName) {
	let index;
	
	try {
		for(var i = 0; i < hidDeviceList.length; i++) {
			if (hidDeviceList[i].device.vendorId.toString() == vendorId.toString() && 
				hidDeviceList[i].device.productId.toString() == productId.toString() && 
				(
					hidDeviceList[i].device.productName.toString() == productName.toString() ||
					getIKSNFromPN(hidDeviceList[i].device.productName.toString()) == productName.toString() ||
					hidDeviceList[i].device.productName.toString() == getIKSNFromPN(productName.toString())
				)
			) {
				index = i;
				break;
			}				
		}
	} catch(err){}
		
	return index;
}

function hidSendReport(vid, pid, serialNumber, transmitBuffer, command) {
	let index = getHidDevice(vid, pid, serialNumber);
	let objHid = hidDeviceList[index];
	let device = objHid.device;
	
	if (command == undefined || command == 'COMMAND_CBOR')
		command = ctapDefaults.COMMAND_CBOR;
	else if (command == 'COMMAND_KEY')
		command = ctapDefaults.COMMAND_KEY;
	else if (command == 'COMMAND_GET_VERSION')
		command = ctapDefaults.COMMAND_GET_VERSION;
	else if (command == 'COMMAND_TPM_BINARY')
		command = ctapDefaults.COMMAND_TPM_BINARY;
	else if (command == 'COMMAND_TPM_PKCS11')
		command = ctapDefaults.COMMAND_TPM_PKCS11;
	
	if (device != undefined) {
		let outputReport = new Uint8Array(ctapDefaults.REPORT_SIZE);
		let dataLenPacket = convertBase.numberToTwoDigitArrayBuffer(transmitBuffer.length);
		
		let channel;

		if (objHid.channel == undefined)
			channel = ctapDefaults.CID_BROADCAST;
		else
			channel = objHid.channel;
	
		outputReport.set(channel, 0); // set channel id
		outputReport.set(command, channel.length); // set command
		outputReport.set(dataLenPacket, channel.length + 1); // set data length
		
		let totalPacketLen = transmitBuffer.length
		let transmitSequence = Math.ceil(totalPacketLen / 59);
		totalPacketLen = totalPacketLen + ((transmitSequence - 1) * 5) + ctapDefaults.payloadPosition;
		transmitSequence = Math.ceil(totalPacketLen / ctapDefaults.REPORT_SIZE);
				
		let transmitIntervalCnt = 0, transmitInterval, transmitPosition = 0;
		let totalOutputReportArr = [];
		
		transmitInterval = setInterval(function() {
			transmitIntervalCnt++;
			
			if (transmitSequence >= transmitIntervalCnt) {
				if (transmitIntervalCnt == 1) {
					transmitPosition = 56;
		
					let transmitSliced = transmitBuffer.slice(0, transmitPosition + 1);
					outputReport.set(transmitSliced, ctapDefaults.payloadPosition); // data
				}
				else {
					outputReport = new Uint8Array(ctapDefaults.REPORT_SIZE);
					outputReport.set(channel, 0); // set channel id
					outputReport.set(convertBase.decToUint8Array(transmitIntervalCnt - 2), channel.length); // set sequence id
												
					let transmitSliced = transmitBuffer.slice(transmitPosition + 1, transmitPosition + 60);
					outputReport.set(transmitSliced, 5); // data
					
					transmitPosition += 59; 
				}
									
				totalOutputReportArr.push(outputReport);
			}
			else {
				clearInterval(transmitInterval);
				
				if (!device.opened)
					device.open();
				
				let openIntervalCount = 0;
				let openInterval = setInterval(function() {
					openIntervalCount += 1;
					
					if (device.opened || openIntervalCount >= 40)
						clearInterval(openInterval);
					
					if (device.opened) {
						//let printSendReport = '';
						for (var i = 0; i < totalOutputReportArr.length; i++) {
							device.sendReport(hidDefaultAdminInterface, totalOutputReportArr[i]);
							//printSendReport = printSendReport + convertBase.bytesToHex(totalOutputReportArr[i]);
						}
						//console.log('Send to device ' + printSendReport.replace(/.{2}/g, '$& '));
					}
				}, 125); 
			}
		}, 0);
	}
}

async function hidOpen(vendorId, productId, serialNumber) {
	let index = getHidDevice(vendorId, productId, serialNumber);
	let objHid = hidDeviceList[index];
	let device;
	
	try {
		device = objHid.device;
	} catch(err) {}
	
	if (device != undefined) {
		if (!device.opened)
			await device.open();
		
		hidDeviceList[index].device = device;
		hidSendReport(vendorId, productId, serialNumber, ctapDefaults.PKT_CHANNELID, ctapDefaults.COMMAND_GET_CHANNELID);
	}
}

function hidGetResponse(vendorId, productId, serialNumber, command, transmitBuffer, callback) {
	hidOutputBuffer.push({ 'vendorId': vendorId, 'productId': productId, 'serialNumber': serialNumber, 'buffer': undefined });
	
	if (command == 'open')
		hidOpen(vendorId, productId, serialNumber);
	else {
		let tbUArray = new Uint8Array(transmitBuffer);
		hidSendReport(vendorId, productId, serialNumber, tbUArray, command);
	}
	
	let funBuffer, bufferInterval, response, count = 0;
				
	funBuffer = function () {
		if (bufferInterval != undefined)
			clearInterval(bufferInterval);
		
		if (hidOutputBuffer.length > 0) {
			for(var i = 0; i < hidOutputBuffer.length; i++) {
				if (hidOutputBuffer[i].vendorId == vendorId && hidOutputBuffer[i].productId == productId 
				&& (hidOutputBuffer[i].serialNumber == getIKSNFromPN(serialNumber) || hidOutputBuffer[i].serialNumber == serialNumber) && hidOutputBuffer[i].buffer != undefined) {
					response = hidOutputBuffer[i].buffer;
					hidOutputBuffer.splice(i, 1);
					callback(response);
					break;
				}
			}
		}
		
		count += 1;
		
		if (count < 6000 && response == undefined) // 5 Minutes
			bufferInterval = setInterval(funBuffer, 50);
	};
	
	bufferInterval = setInterval(funBuffer, 0);
}

async function hidClose(device) {
	let index = getHidDevice(device.vendorId, device.productId, device.productName);
	
	try {
		let device = hidDeviceList[index].device;
			
		if (device != undefined) {
			if (device.opened) {
				await device.close();
				hidDeviceList[index].device = device;
				hidDeviceList[index].channel = undefined;
			}
		}
	} catch(err) {}
}

function getIKSNFromPN(productName) {
	let splitPN = productName.split(' ');
	return splitPN[splitPN.length -1];
}