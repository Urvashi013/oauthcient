var source;

function initializeEventSource(url, encryptionType, jsessionId, openCallback, dataCallback, errorCallback) {
	try {
		//if (typeof(EventSource) == "undefined") {
	    // 	console.log("Error: Server-Sent Events are not supported in your browser");
	    // 	return;
	   	//}
	   	
	   	// Check if already connected to avoid getting duplicate messages
	  	//if (typeof(source) !== "undefined") {
	    //   source.close();
	   	//}
   
   		//console.log("Setting up new event source");
		
		//source = new EventSource(url + '?encryptionType=' + encryptionType);
		url = url + '?timestamp=' + getTime('ms-cnt'); 
		
		if (jsessionId != undefined)
			url = url + '&jsessionId=' + jsessionId; 
		
		source = new EventSource(url);
		
		source.onopen = function(event) {
		    if ( typeof openCallback == 'function' ) {
		    	openCallback(event);
	    	}
		};
		
		source.addEventListener('InnaITEventMessage', function(event) {
			if ( typeof dataCallback == 'function' ) {
		    	dataCallback(event.data);
	    	}
		}, false);
		
		source.onmessage = function(event) {
		    if ( typeof dataCallback == 'function' ) {
		    	dataCallback(event.data);
	    	}
		};
		
		source.onerror = function(event) {
		    if ( typeof errorCallback == 'function' ) {
		    	errorCallback(event);
	    	}
		};
		
	}	
	catch(err) {}
}

