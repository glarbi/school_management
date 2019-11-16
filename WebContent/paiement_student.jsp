<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@page import="org.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Paiement élève</title>
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
	STUDENT myStudent = null;
	if (idStr != null) {
		try {
			idInt = Integer.parseInt(idStr);
			myStudent = DBManager.getSTUDENT(idInt);
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
		<caption>Paiements de l'élève [<%=idStr%>]: <%=myStudent.NOM%> <%=myStudent.PRENOM%></caption>
		<thead>
			<tr>
				<th></th>
				<th>Enseignant</th>
				<th>Matière</th>
				<th>Début</th>
				<th>Fin</th>
				<th>Montant</th>
			</tr>
		</thead>
		<tbody id="tbodyId">
		<%
		if (idInt != null) {
			ArrayList<ArrayList<Object>> paiements = DBManager.get_PAIEMENT(idInt);
			ArrayList<Object> ligne = null;
			for (int i = 0; i < paiements.size(); i++) {
				ligne = paiements.get(i);
				String _idTeacher = ligne.get(1).toString();
				TEACHER myTeacher = DBManager.getTEACHER(Integer.parseInt(_idTeacher));
				String _idSubject = ligne.get(2).toString();
				SUBJECT mySubject = DBManager.getSUBJECT_by_ID(Integer.parseInt(_idSubject));
				LEVEL myLevel = DBManager.getLEVEL_by_ID(mySubject.idlevel);
				String _debut = ligne.get(3).toString();
				String _fin = ligne.get(4).toString();
				String _montant = ligne.get(5).toString();
		%>
			<tr>
				<td></td>
				<td><%=myTeacher.NOM%> <%=myTeacher.PRENOM%></td>
				<td><%=mySubject.title%> <%=myLevel.levelTitle%></td>
				<td><%=_debut%></td>
				<td><%=_fin%></td>
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
				<th>Enseignant</th>
				<th>Matière</th>
				<th>Début</th>
				<th>Fin</th>
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
            //select: {
            //    style:    'os',
            //    selector: 'td:first-child'
            //},
            //order: [[ 1, 'asc' ]]
        } );
    } );
    
    var myDebut = null;
    var myTeacher = null;
    var mySubject = null;
    $('#paiementTable tbody').on( 'click', 'tr', function () {
    	myTeacher = myTable.row( this ).data()[1]; //nom prénom
    	mySubject = myTable.row( this ).data()[2]; //title levelTitle
    	myDebut = myTable.row( this ).data()[3];
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
    	var _studentId = location.search.split('ID=')[1];
    	myWindow = window.open("new_paiement_student.jsp?ID="+_studentId, "myWindow", "width=400,height=200");

    	//Fill teacher select list
    	$.ajax({
			url: "JSONServletPaiement",
			type: "GET",
			data: {addPaiementStudent_getTeachers_studentId:_studentId},
			dataType: "json",
			success: function (result) {
				//location.reload();
				myWindow.addEventListener("load", function(evt) {
					var _teacher = myWindow.document.getElementById("teacher");
					var _option = _teacher.appendChild(document.createElement('option'));
					_option.value = "";
					_option.text = "";
					$.each(result, function(key,value) {
						var _option = _teacher.appendChild(document.createElement('option'));
						_option.value = value[0]; //idTeacher
						_option.text = value[1]; //nom prénom
					});
				});
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert(xhr.status);
				alert(thrownError);
				location.reload();
			}
		   });

    	//Fill subjectLevel select list
    	/*
    	$.ajax({
			url: "JSONServletPaiement",
			type: "GET",
			data: {addPaiementStudent_getSubjects_studentId:_studentId,
				addPaiementStudent_getSubjects_teacherId:_firstTeacherId},
			dataType: "json",
			success: function (result) {
console.log("toto2");
				if (result.length == 0) {
					alert("The student isn't enrolled in any subject!!!");
					myWindow.close();
				} else {
					//location.reload();
					myWindow.addEventListener("load", function(evt) {
						var _subjectLevel = myWindow.document.getElementById("subjectLevel");
						$.each(result, function(key,value) {
							var _option = _subjectLevel.appendChild(document.createElement('option'));
							_option.value = value[0]; //subjectId
							_option.text = value[1]; //SubjectTitle LevelTitle
						});
					});
				}
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert(xhr.status);
				alert(thrownError);
				location.reload();
			}
		   });*/
    };
	
    function removePaiement() {
    	if (myDebut != null) {
			var r = confirm("Ete-vous sûr de supprimer le paiement du "+myDebut+" ?");
			if (r == true) {
		    	//console.log( "date début: "+myDebut );
		    	var _studentId = location.search.split('ID=')[1];
		    	var _teacherId = 
				$.ajax({
					url: "JSONServletRemove",
					type: "POST",
					data: {removePaiementStudent_StudentId:_studentId,
						removePaiementStudent_teacherNomPrenom:myTeacher,
						removePaiementStudent_subjectLevel:mySubject,
						removePaiementStudent_Debut:myDebut},
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