<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="org.*"%>

<!DOCTYPE HTML>
<!--
	Verti by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>LISTE DES ELEVES EN RETARD DE PAIEMENT</title>
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

					<script src="assets/js/scriptExcel_Liste_Students_non_Payes.js"></script>
					<h2
						style="color: white; background-color: #6683b1; text-align: center">Liste
						des élèves en retard de paiement</h2>
					<table border="1">
						<tr>
							<!-- ligne1 -->
							<th>Identifiant</th>
							<!-- case 1 -->
							<th>Nom</th>
							<!-- case 2 -->
							<th>Prénom</th>
							<!-- case 3 -->
						</tr>
						<%
							STUDENT myStudent = null;
							ArrayList<Object> students = DBManager.getSTUDENTS_non_payes();
							int studentsSize = students.size();
						%>
							<script>
							for (var i=1;i < <%=studentsSize%>;i++) excel_Students_non_Payes.set({row:i, style: i%2==0 ? evenRow: oddRow  });
							</script>
						<%
							for (int i = 0; i < students.size(); i++) {
								myStudent = DBManager.getSTUDENT((Integer) (students.get(i)));

								String link_student_paiement = "paiement_student.jsp?ID=" + myStudent.ID;
						%>
						<tr>
							<!-- ligne i -->
							<th><a href=<%=link_student_paiement%>><%=myStudent.ID%></a>
							</th>
							<!-- case 1 -->
							<th><%=myStudent.NOM%></th>
							<!-- case 2 -->
							<th><%=myStudent.PRENOM%></th>
							<!-- case 3 -->
						</tr>
						<script>
						excel_Students_non_Payes.set(0,0,<%=i%>+1,"<%=myStudent.ID%>");
						excel_Students_non_Payes.set(0,1,<%=i%>+1,"<%=myStudent.NOM%>");
						excel_Students_non_Payes.set(0,2,<%=i%>+1,"<%=myStudent.PRENOM%>");
						</script>
						<%
							}
						%>
					</table>
					<br /> <br /> <input type="button" id="exportbtn" value="Exporter la liste"
						onclick="download_Students_non_Payes('Liste_Eleves_non_Payes.xlsx');" />
					<script src="assets/js/myScripts.js"></script>

					<br /> <br /> <br />
				</div>
			</div>
		</div>
		<!-- Scripts -->

		<script src="assets/js/jquery.min.js"></script>
		<script src="assets/js/jquery.dropotron.min.js"></script>
		<script src="assets/js/browser.min.js"></script>
		<script src="assets/js/breakpoints.min.js"></script>
		<script src="assets/js/util.js"></script>
		<script src="assets/js/main.js"></script>
</body>
</html>