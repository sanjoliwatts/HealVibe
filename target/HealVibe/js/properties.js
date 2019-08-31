var config={
    "protocol" : "http",
    "port" : "8080",
    "domain" : "localhost",
    "project" : "HealVibe"
}

/*
* Do not change the code below this point
* Only change the port number in the config object above
*/

function getURI(){
return config.protocol +"://"+ config.domain +":"+ config.port +"/"+ config.project +"/api/" ;
}