<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@page import="org.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Paiement Enseignant</title>
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" />
<link rel="stylesheet" href="https://cdn.datatables.net/select/1.3.1/css/select.dataTables.min.css" />
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/select/1.3.1/js/dataTables.select.min.js"></script>
</head>
<body>
	<%
	String idStr = request.getParameter("ID");
	Integer idInt = null;
	TEACHER myTeacher = null;
	if (idStr != null) {
		try {
			idInt = Integer.parseInt(idStr);
			myTeacher = DBManager.getTEACHER(idInt);
		} catch (java.lang.NumberFormatException e) {
			e.printStackTrace();
		}
	}
	%>
	<!-- Header -->
	<div id="header-wrapper">
		<header id="header" class="container">
			<!-- Header section is defined in another file -->
			<iframe height="335px" width="100%" src="header.html" overflow="auto" name="iframe_logo"></iframe>
		</header>
	</div>
	<table id="paiementTable" class="display" style="width: 100%">
		<caption>Paiements de l'enseignant [<%=idStr%>]: <%=myTeacher.NOM%> <%=myTeacher.PRENOM%></caption>
		<thead>
			<tr>
				<th></th>
				<th>Matière</th>
				<th>Mois</th>
				<th>Montant</th>
			</tr>
		</thead>
		<tbody id="tbodyId">
		<%
		if (idInt != null) {
			ArrayList<ArrayList<Object>> paiements = DBManager.get_PAIEMENT_TEACHER(idInt);
			ArrayList<Object> ligne = null;
			for (int i = 0; i < paiements.size(); i++) {
				ligne = paiements.get(i);
				String _idTeacher = ligne.get(1).toString();
				String _idSubject = ligne.get(2).toString();
				String _mois = ligne.get(3).toString();
				String _montant = ligne.get(4).toString();
				
				Integer _idSubjectInt = Integer.parseInt(_idSubject);
				SUBJECT mySubject = DBManager.getSUBJECT_by_ID(_idSubjectInt);
				LEVEL myLevel = DBManager.getLEVEL_by_ID(mySubject.idlevel);

		%>
			<tr>
				<td></td>
				<td><%=mySubject.title%> <%=myLevel.levelTitle%></td>
				<td><%=_mois%></td>
				<td><%=_montant%></td>
			</tr>
		<%
			}
		}
		%>
		</tbody>
		<tfoot>
			<tr>
				<th></th>
				<th>Matière</th>
				<th>Mois</th>
				<th>Montant</th>
			</tr>
		</tfoot>
	</table>
	<INPUT type="button" id="addBtn" value="Ajouter un paiement" /> 
	<INPUT type="button" id="SuppBtn" value="Supprimer un paiement" disabled onclick="removePaiement()" />
	<script>
	document.getElementById("addBtn").addEventListener("click", addPaiement);
	var myTable = null;
    $(document).ready(function() {
        myTable = $('#paiementTable').DataTable( {
            columnDefs: [ {
                orderable: false,
                className: 'select-checkbox',
                targets:   0
            } ],
            select: true
        } );
    } );
    
    var myMonth = null;
    var mySubject = null;
    $('#paiementTable tbody').on( 'click', 'tr', function () {
    	mySubject = myTable.row( this ).data()[1];
    	myMonth = myTable.row( this ).data()[2];
        //console.log( myTable.row( this ).data()[1],myTable.row( this ).data()[2],myTable.row( this ).data()[3] );
        
        document.getElementById("SuppBtn").disabled = false;
        //For single row selection
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
            document.getElementById("SuppBtn").disabled = true;
            myDebut = null;
        }
        else {
        	myTable.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );
    
    var myWindow;
    function addPaiement() {
    	var _teacherId = location.search.split('ID=')[1];
    	myWindow = window.open("new_paiement_teacher.jsp?ID="+_teacherId, "myWindow", "width=400,height=200");

    	$.ajax({
			url: "JSONServletTeacher_Subject",
			type: "GET",
			data: {addPaiementTeacher_idTeacher:_teacherId},
			dataType: "json",
			success: function (result) {
				//location.reload();
				myWindow.onload = function(){
					var _subjectLevel = myWindow.document.getElementById("subjectLevel");
					$.each(result, function(key,value) {
						var _option = _subjectLevel.appendChild(document.createElement('option'));
						_option.value = value[0]; //subjectId
						_option.text = value[1]; //SubjectTitle LevelTitle
					});
				};
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert(xhr.status);
				alert(thrownError);
				location.reload();
			}
		   });

    	//var _addBtn = document.getElementById("addBtn");
    	//_addBtn.value = "Enregistrer";
    	//_addBtn.removeEventListener("click", addPaiement);
    	//_addBtn.addEventListener("click", savePaiement);
    };
    
    function removePaiement() {
    	if (myMonth != null) {
			var r = confirm("Etes-vous sûr de supprimer le paiement de "+mySubject+" du "+myMonth+" ?");
			if (r == true) {
		    	//console.log( "date début: "+myDebut );
		    	var _teacherId = location.search.split('ID=')[1];
				$.ajax({
					url: "JSONServletRemove",
					type: "POST",
					data: {removePaiementTeacher_idTeacher:_teacherId,
						removePaiementTeacher_Subject_Level:mySubject,
						removePaiementTeacher_Mois:myMonth},
					dataType: "json",
					success: function (result) {
						location.reload();
					},
					error: function (xhr, ajaxOptions, thrownError) {
						alert(xhr.status);
						alert(thrownError);
						location.reload();
					}
				   });
			}
    	}
	};
    </script>
</body>
</html>