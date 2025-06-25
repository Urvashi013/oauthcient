var pingPongIntervalId, pingPongTimeout = 55, socket, messageSent = 0, connectionRetryPeriod = 5000, maxRetryCount = 5,
currentRetryCount = 1;


function initializeWebSocket(url, encryptionType, openCallback, dataCallback, errorCallback) {
	try {
		//if ("WebSocket" in window) {}
		//if(typeof(WebSocket) == "undefined") {
		// 	console.log("Error: Web socket is not supported in your browser");
	    // 	return;
	   	//}
	   	
	   	// Check if already connected to avoid getting duplicate messages
	  	//if (typeof(socket) !== "undefined") {
	    //   socket.close();
	   	//}
   
   		//console.log("Setting up new web socket");
		
		//socket = new WebSocket(url + '?encryptionType=' + encryptionType);
		url = url + '?timestamp=' + getTime('ms-cnt'); 
		socket = new WebSocket(url);
		
		// Change binary type from "blob" to "arraybuffer"
		socket.binaryType = 'arraybuffer';
		
		socket.onopen = function(event) {
		    //console.log(event);
			
			wsPingPong();
			
		    if ( typeof openCallback == 'function' ) {
				openCallback(event);
			}
		};
		
		socket.onmessage = function(event) {
		    if ( typeof dataCallback == 'function' ) {
		    	dataCallback(event.data);
	    	}
		};
		
		socket.onerror = function(event) {
		    if ( typeof errorCallback == 'function' ) {
		    	errorCallback(event);
	    	}
		};
		
		socket.onclose = function(event) {
		    socket = undefined;
			
			// retrying websocket server connection after n seconds
			if  (currentRetryCount <= maxRetryCount) {
				setTimeout(function() {
					currentRetryCount += 1;
					initializeWebSocket(url, encryptionType, openCallback, dataCallback, errorCallback);
				}, connectionRetryPeriod);
			}
		};
		
	}
	catch (err) {
	}
}

function wsPingPong() {
	messageSent = 0;
	
    pingPongIntervalId = setInterval(function () {
		messageSent = messageSent + 1;
		
		if (socket === undefined || socket === null)
			clearInterval(pingPongIntervalId);
        else {
            if (socket.readyState == WebSocket.OPEN && messageSent > pingPongTimeout) {
				//wsSend(JSON.stringify({}));
				messageSent = 0;
			}
			else if (socket.readyState != WebSocket.OPEN){
				clearInterval(pingPongIntervalId);
			}
        }
    }, 1000);
}

function wsSend(action, data) {
	if (socket != undefined) {
		if (socket.readyState == WebSocket.OPEN) {
			if (action == 'arraybuffer')
				socket.send(data);
			else {
				if (data != undefined) {
					let serverSocketData;

					if (action == 'GET_DEVICE_INFO')
						serverSocketData = { 'time': d.getTime(), 'command': action, 'target': '', 'parameter': '' };
					else
						serverSocketData = { 'issuer': 'ISSUER_SERVER', 'target': '', 'time': getTime('ms'), 'action': action, 
						'parameter': JSON.stringify(data) };
				
					socket.send(JSON.stringify(serverSocketData));
				}
				else
					socket.send({ 'action': action });
			}
			
			clearInterval(pingPongIntervalId);
			wsPingPong();
		}
		else
			wsClose();
	}
}


function wsClose() {
	if (socket != undefined) {
		if (socket.readyState == WebSocket.OPEN) {
			socket.close();
			socket = undefined;
		}
	}
	
	if (pingPongIntervalId != undefined)
		clearInterval(pingPongIntervalId);
}
