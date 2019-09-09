const req = new XMLHttpRequest();
req.open('GET', 'config.xml', false); 
req.send(null);

if (req.status == 200) {
    //console.log("Réponse reçue: %s", req.responseText);
    var parser = new DOMParser();
    var doc = parser.parseFromString(req.responseText, 'application/xml');
    var mySchoolYear = doc.getElementsByTagName('SCHOOL_YEAR')[0].childNodes[0].nodeValue;
    document.getElementById("SchoolYear").innerHTML = mySchoolYear;
} else {
    console.log("Status de la réponse: %d (%s)", req.status, req.statusText);
}
