const req = new XMLHttpRequest();
req.open('GET', 'config.xml', false); 
req.send(null);

if (req.status == 200) {
    //console.log("Réponse reçue: %s", req.responseText);
    var parser = new DOMParser();
    var doc = parser.parseFromString(req.responseText, 'application/xml');
    var mySchoolNameAbbreviation = doc.getElementsByTagName('SCHOOL_NAME_ABBREVIATION')[0].childNodes[0].nodeValue;
    var mySchoolAdress = doc.getElementsByTagName('SCHOOL_ADRESS')[0].childNodes[0].nodeValue;
    var mySchoolEmail = doc.getElementsByTagName('SCHOOL_EMAIL')[0].childNodes[0].nodeValue;
    var mySchoolPhone = doc.getElementsByTagName('SCHOOL_PHONE')[0].childNodes[0].nodeValue;
    var mySchoolTwitter = doc.getElementsByTagName('SCHOOL_TWITTER')[0].childNodes[0].nodeValue;
    var mySchoolFacebook = doc.getElementsByTagName('SCHOOL_FACEBOOK')[0].childNodes[0].nodeValue;

    document.getElementById("SchoolNameAbbreviation").innerHTML = mySchoolNameAbbreviation;
    document.getElementById("SchoolAdress").innerHTML = mySchoolAdress;
    document.getElementById("SchoolEmail").innerHTML = mySchoolEmail;
    document.getElementById("SchoolPhone").innerHTML = mySchoolPhone;
    document.getElementById("SchoolTwitter").href = mySchoolTwitter;
    document.getElementById("SchoolFacebook").href = mySchoolFacebook;
} else {
    console.log("Status de la réponse: %d (%s)", req.status, req.statusText);
}
