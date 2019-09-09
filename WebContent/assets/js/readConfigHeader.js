const reqLogo = new XMLHttpRequest();
reqLogo.open('GET', 'config.xml', false); 
reqLogo.send(null);

if (reqLogo.status == 200) {
    //console.log("Réponse reçue: %s", reqLogo.responseText);
    var parser = new DOMParser();
    var doc = parser.parseFromString(reqLogo.responseText, 'application/xml');
    var mySchoolName = doc.getElementsByTagName('SCHOOL_NAME')[0].childNodes[0].nodeValue;
    var myElements = document.getElementsByClassName("SchoolName");
    var i;
    for (i = 0; i < myElements.length; i++) {
    	myElements[i].innerHTML = mySchoolName;
    }
} else {
    console.log("Status de la réponse: %d (%s)", reqLogo.status, reqLogo.statusText);
}
