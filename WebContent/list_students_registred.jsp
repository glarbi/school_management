<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="org.*"%>
<%@ page import="java.time.*"%>
<%@ page import="java.time.format.DateTimeFormatter"%>

<!DOCTYPE HTML>
<!--
	Verti by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>LISTE DES INSCRIPTIONS DES ELEVES</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
<script type="text/javascript" src="assets/js/jszip.js"></script>
<script type="text/javascript" src="assets/js/FileSaver.js"></script>
<script type="text/javascript" src="assets/js/myexcel.js"></script>

</head>
<body class="is-preload no-sidebar">
	<div id="page-wrapper">
		<!-- Header -->
		<div id="header-wrapper">
			<header id="header" class="container">
				<!-- Header section is defined in another file -->
				<iframe height="335px" width="100%" src="header.html" overflow="auto" name="iframe_logo"></iframe>
			</header>
		</div>

		<!-- Main -->
		<div id="main-wrapper">
			<div class="container">
				<div id="content">
					<br />
					<input type="button" id="exportbtn" value="Exporter la liste" onclick="download_Students('Liste_Eleves.xlsx');" /> 
					
					<script src="assets/js/myScripts.js"></script>
					<script src="assets/js/scriptExcel_List_Students.js"></script>
					<br /> <br />

					<table border="1">
						<caption>
							<h2 style="color: white; background-color: #6683b1; text-align: center">Liste des inscriptions des élèves</h2>
						</caption>
						<tr>
							<!-- ligne1 -->
							<th>Identifiant</th>
							<!-- case 1 -->
							<th>Nom</th>
							<!-- case 2 -->
							<th>Prénom</th>
							<!-- case 3 -->
							<th>Matières</th>
							<!-- case 4 -->
							<th>Enseignant</th>
							<!-- case 5 -->
							<th>Date d'inscription</th>
							<!-- case 6 -->
							<th></th>
							<!-- case 6 -->
						</tr>
						<%
							ArrayList<ArrayList<Object>> registrations = null;
							registrations = DBManager.getStudents_Registrations();

							int registrationsSize = registrations.size();
						%>
						<script>
						for (var i=1;i < <%=registrationsSize%>;i++) excel_Students.set({row:i, style: i%2==0 ? evenRow: oddRow  });
						</script>
						<%
							ArrayList<Object> ligne1 = null;

							for (int i = 0; i < registrationsSize; i++) {
								ligne1 = registrations.get(i);
								Integer _idStudent = (Integer)ligne1.get(0);
								String _nomStudent = ligne1.get(1).toString();
								String _prenomStudent = ligne1.get(2).toString();
								Integer _idSubject = (Integer)ligne1.get(3);
								String _subject = ligne1.get(4).toString();
								String _level = ligne1.get(5).toString();
								Integer _idTeacher = (Integer)ligne1.get(6);
								String _nomTeacher = ligne1.get(7).toString();
								String _prenomTeacher = ligne1.get(8).toString();
								String _dateInscription = ligne1.get(9).toString();
								
								String link_student = "student.jsp?ID=" + _idStudent;

								String link_subject = "";
								link_subject = link_subject.concat("<a href=subject.jsp?ID="+ _idSubject+">"+_subject+" "+_level+"</a><br>");
								
								String link_teacher = "";
								link_teacher = link_teacher.concat("<a href=teacher.jsp?ID="+ _idTeacher+">"+_nomTeacher+" "+_prenomTeacher+"</a><br>");

						%>
						<tr>
							<!-- ligne i -->
							<th><a href=<%=link_student%>><%=_idStudent%></a></th>
							<!-- case 1 -->
							<th><%=_nomStudent%></th>
							<!-- case 2 -->
							<th><%=_prenomStudent%></th>
							<!-- case 3 -->
							<th><%=link_subject%></th>
							<!-- case 4 -->
							<th><%=link_teacher%></th>
							<!-- case 5 -->
							<th><%=_dateInscription%></th>
							<!-- case 6 -->
							<%String mycall = "\"removeRegistration("+_idStudent+","+_idTeacher+","+_idSubject+")\"";%>
							<th><INPUT type="button" id="SuppBtn" value="Annuler" onclick=<%=mycall%>/></th>
							<!-- case 6 -->
						</tr>
						<script>
						excel_Students.set(0,0,<%=i%>+1,"<%=_idStudent%>");
						excel_Students.set(0,1,<%=i%>+1,"<%=_nomStudent%>");
						excel_Students.set(0,2,<%=i%>+1,"<%=_prenomStudent%>");
						excel_Students.set(0,3,<%=i%>+1,"<%=_subject%> <%=_level%>");
						excel_Students.set(0,4,<%=i%>+1,"<%=_nomTeacher%> <%=_prenomTeacher%>");
						excel_Students.set(0,5,<%=i%>+1,"<%=_dateInscription%>");
						</script>
						<%
							}
						%>
					</table>
					<div id="Result" style="color:red;"></div>
				</div>
			</div>
		</div>

		<!-- Footer section is defined in another file -->
		<iframe height="650px" width="100%" src="footer.html" name="iframe_footer"></iframe>

	</div>

	<!-- Scripts -->

	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery.dropotron.min.js"></script>
	<script src="assets/js/browser.min.js"></script>
	<script src="assets/js/breakpoints.min.js"></script>
	<script src="assets/js/util.js"></script>
	<script src="assets/js/main.js"></script>
	<script>
	function removeRegistration(idSt, idT, idSub) {
		$.ajax({
			url: "JSONServletRemove",
			type: "POST",
			data: {idRegistrationStudent:idSt,
				idRegistrationTeacher:idT,
				idRegistrationSubject:idSub},
			dataType: "json",
			success: function (result) {
				//location.reload();
				document.getElementById("Result").innerHTML = result;
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert(xhr.status);
				alert(thrownError);
			}
		   });
	}
	</script>

</body>
</html>