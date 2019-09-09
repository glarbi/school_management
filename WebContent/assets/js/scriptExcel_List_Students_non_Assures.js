var excel_Students_non_Assures = $JExcel.new("Calibri light 10 #333333");
excel_Students_non_Assures.set( {sheet:0,value:"Liste des élèves" } );
var evenRow = excel_Students_non_Assures.addStyle( { border: "none,none,none,thin #333333" });
var oddRow = excel_Students_non_Assures.addStyle ( { fill: "#ECECEC", border: "none,none,none,thin #333333" });
 
var headers=["Identifiant", "Nom", "Prénom", "Date de naissance", "Depuis", "Jusqu'au"];
var formatHeader = excel_Students_non_Assures.addStyle ( {
    border: "none,none,none,thin #333333", font: "Calibri 12 #0000AA B"}
);
for (var i=0; i<headers.length; i++){              // Loop headers
	excel_Students_non_Assures.set(0, i, 0, headers[i], formatHeader);    // Set CELL header text & header format
	excel_Students_non_Assures.set(0, i, undefined, "auto");             // Set COLUMN width to auto 
	excel_Students_non_Assures.set(0, i, undefined, 30, excel_Students_non_Assures.addStyle( {align:"L C"})); // Align columns to the left center
}
