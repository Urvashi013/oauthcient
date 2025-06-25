var driver_serverApiUrl = 'https://nic-poc.innait.com', 
	driver_serverWsUrl, 
	driver_serverEventSourceUrl, 
	crossDomainSupport = false;

var driver_config = {
	init: function () {
		var protocol = window.location.protocol, port = window.location.port, hostname = window.location.hostname;
		
		if (port != '')
			port = ':' + port;
		
		if (driver_serverApiUrl == '')
			driver_serverApiUrl = protocol + '//' + hostname + port;
	
		driver_serverWsUrl = driver_serverApiUrl.replace('http', 'ws') + '/ws';
		driver_serverApiUrl = driver_serverApiUrl + '/service';
		driver_serverEventSourceUrl = driver_serverApiUrl + '/createSSESource';
    }
}

driver_config.init();