<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nouveau paiement élève</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
</head>
<body>
	<label for="teacher">Enseignant</label>
	<select id="teacher" onchange="fillSubjects()"></select><br>
	<label for="subjectLevel">Matière</label>
	<select id="subjectLevel"></select><br>
	<label for="debutId">Début</label>
	<input type="date" id="debutId" /><br>
	<label for="finId">Fin</label>
	<input type="date" id="finId" /><br>
	<label for="montantId">Montant</label>
	<input type="number" id="montantId" min=0.00 max=100000.00 step=100.00 /><br><br>
	
	<INPUT type="button" id="addBtn" value="Enregistrer" onclick="savePayment()" />
	<INPUT type="button" id="cancelBtn" value="Annuler" onclick="cancel()" />
	
	<script>
	function savePayment() {
		var _studentId = location.search.split('ID=')[1];
		var _teacherId = document.getElementById("teacher").value;
		var _subjectId = document.getElementById("subjectLevel").value;
    	var _debut = document.getElementById("debutId").value;
    	var _fin = document.getElementById("finId").value;
    	var _montant = document.getElementById("montantId").value;
    	$.ajax({
			url: "JSONServletPaiement",
			type: "POST",
			data: {savePaiementStudent_idStudent:_studentId,
				savePaiementStudent_idTeacher:_teacherId,
				savePaiementStudent_idSubject:_subjectId,
				savePaiementStudent_debut:_debut,
				savePaiementStudent_fin:_fin,
				savePaiementStudent_montant:_montant},
			dataType: "json",
			success: function (result) {
				//location.reload();
				self.close ();
				window.opener.location.reload();
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert(xhr.status);
				alert(thrownError);
				self.close ();
			}
		   });
	}
	
	function cancel() {
		self.close ();
	}
	
	function fillSubjects() {
		var _studentId = location.search.split('ID=')[1];
		var _teacherId = document.getElementById("teacher").value;
		if (_teacherId != "") {
	    	$.ajax({
				url: "JSONServletPaiement",
				type: "GET",
				data: {addPaiementStudent_getSubjects_studentId:_studentId,
					addPaiementStudent_getSubjects_teacherId:_teacherId},
				dataType: "json",
				success: function (result) {
					if (result.length == 0) {
						alert("The student isn't enrolled in any subject!!!");
						self.close();
					} else {
						var _subjectLevel = document.getElementById("subjectLevel");
						while (_subjectLevel.hasChildNodes()) {  
							_subjectLevel.removeChild(_subjectLevel.firstChild);
						}
						$.each(result, function(key,value) {
							var _option = _subjectLevel.appendChild(document.createElement('option'));
							_option.value = value[0]; //subjectId
							_option.text = value[1]; //SubjectTitle LevelTitle
						});
					}
				},
				error: function (xhr, ajaxOptions, thrownError) {
					alert(xhr.status);
					alert(thrownError);
					location.reload();
				}
			   });
		} else {
			var _subjectLevel = document.getElementById("subjectLevel");
			while (_subjectLevel.hasChildNodes()) {  
				_subjectLevel.removeChild(_subjectLevel.firstChild);
			}			
		}
	}
	</script>
</body>
</html>