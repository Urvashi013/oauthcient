$(document).ready(function() {
	
	debugger;
	var status = $.urlParam('status');
	var message = $.urlParam('message');
	var path = "";
	
	//if(status && message){
		
		if(status != null && message !=null){
			
			var op = "Status : " +  status + "\n" + "Message : " + message;
		
		if( status !=0 && message !=0)
			alert(op);
			
			
			path = window.location.href.split('?')[0];
			
			location.href = path;
		
			
		}
		
		
		
	//}

});

function selectDeployment(dType) {
	debugger;

	/*janParichayLocalClient = "0";
	janParichayStagClient = "1";
	janParichayDevClient = "2";
	parichayLocalClient = "3";
	parichayStagClient = "4";
	parichayDevClient = "5";
*/
	if (dType != null) {

		localStorage.setItem("dType", dType);
	
		location.href = baseURL + "?dType=" + dType;
		return;
	}else{
		alert("Invalid Request")
	}
}




$.urlParam = function(name) {
	var results = new RegExp('[\?&]' + name + '=([^&#]*)')
			.exec(window.location.href);
	if (results == null) {
		return null;
	}
	return decodeURI(results[1]) || "";
}
	
function home(){

			
	location.href = landingPage;
}