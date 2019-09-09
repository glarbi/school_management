var excel_Teachers = $JExcel.new("Calibri light 10 #333333");
excel_Teachers.set( {sheet:0,value:"Liste des enseignants" } );
var evenRow = excel_Teachers.addStyle( { border: "none,none,none,thin #333333"});
var oddRow = excel_Teachers.addStyle ( { fill: "#ECECEC", border: "none,none,none,thin #333333"}); 
 
var headers = ["Identifiant", "Nom", "Prénom", "Paiement", "Assurance", "Date de naissance", "Lieu de naissance", "Adresse", "Téléphone", "Date inscription"];
var formatHeader = excel_Teachers.addStyle ( {
    border: "none,none,none,thin #333333", font: "Calibri 12 #0000AA B"}
);
for (var i=0; i<headers.length; i++){              // Loop headers
	excel_Teachers.set(0, i, 0, headers[i], formatHeader);    // Set CELL header text & header format
	excel_Teachers.set(0, i, undefined, "auto");             // Set COLUMN width to auto 
	excel_Teachers.set(0, i, undefined, 30, excel_Teachers.addStyle( {align:"L C"})); // Align columns to the left center
}
