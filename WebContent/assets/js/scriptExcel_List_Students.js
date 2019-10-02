var excel_Students = $JExcel.new("Calibri light 10 #333333");
excel_Students.set( {sheet:0,value:"Liste des élèves" } );
var evenRow = excel_Students.addStyle( { border: "none,none,none,thin #333333" });
var oddRow = excel_Students.addStyle ( { fill: "#ECECEC", border: "none,none,none,thin #333333" });
 
var headers=["Identifiant", "Nom", "Prénom", "Paiement", "Assurance", "Date de naissance", "Lieu de naissance", "Niveau scolaire", "Prénom du père", "Profession du père", "Nom de la mère", "Prénom de la mère", "Profession de la mère", "Adresse", "Téléphone", "Date inscription"];
var formatHeader = excel_Students.addStyle ( {
    border: "none,none,none,thin #333333", font: "Calibri 12 #0000AA B"}
);
for (var i=0; i<headers.length; i++){              // Loop headers
	excel_Students.set(0, i, 0, headers[i], formatHeader);    // Set CELL header text & header format
	excel_Students.set(0, i, undefined, "auto");             // Set COLUMN width to auto 
	excel_Students.set(0, i, undefined, 30, excel_Students.addStyle( {align:"L C"})); // Align columns to the left center
}
