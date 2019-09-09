
function download_Students(filename) {
	console.log("download_Students");
	excel_Students.generate(filename);
}

function download_Students_non_Assures(filename) {
	console.log("download_Students_non_Assures");
	excel_Students_non_Assures.generate(filename);
}

function download_Students_non_Payes(filename) {
	console.log("download_Students_non_Payes");
	excel_Students_non_Payes.generate(filename);
}

function download_Teachers(filename) {
	console.log("download_Teachers");
	excel_Teachers.generate(filename);
}

/******************** Read XML file *******************************************/


function readXMLDoc(xml) {
    var x, i, xmlDoc, txt;
    xmlDoc = xml.responseXML;
    txt = "";
    x = xmlDoc.documentElement.childNodes;
    for (i = 0; i < x.length; i++) { 
        if (x[i].nodeType == 1) {	//Node.ELEMENT_NODE	1	Un noeud Element  tel que <p> ou <div>.
        							// more information on: https://developer.mozilla.org/fr/docs/Web/API/Node/nodeType
            txt += x[i].nodeName + "<br>";
        }
    }
    document.getElementById("demo").innerHTML = txt; 
}
