<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nouveau paiement enseignant</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
</head>
<body>
	<label for="subjectLevel">Matière</label>
	<select id="subjectLevel"></select><br>
	<label for="monthId">Mois</label>
	<input type="date" id="monthId" /><br>
	<label for="montantId">Montant</label>
	<input type="number" id="montantId" min=0.00 max=100000.00 step=100.00 /><br><br>
	
	<INPUT type="button" id="addBtn" value="Enregistrer" onclick="savePayment()" />
	<INPUT type="button" id="cancelBtn" value="Annuler" onclick="cancel()" />
	
	<script>
	function savePayment() {
		var _teacherId = location.search.split('ID=')[1];
		var _subjectId = document.getElementById("subjectLevel").value;
    	var _mois = document.getElementById("monthId").value;
    	var _montant = document.getElementById("montantId").value;
    	$.ajax({
			url: "JSONServletPaiement",
			type: "POST",
			data: {savePaiementTeacher_idTeacher:_teacherId,
				savePaiementTeacher_subjectId:_subjectId,
				savePaiementTeacher_mois:_mois,
				savePaiementTeacher_montant:_montant},
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
	</script>
</body>
</html>