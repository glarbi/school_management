<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="org.*"%>
<%@ page import="java.time.*"%>

<!DOCTYPE HTML>
<!--
	Verti by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>LISTE DES ELEVES NON ASSURES</title>
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

					<script src="assets/js/scriptExcel_List_Students_non_Assures.js"></script>
					<h2
						style="color: white; background-color: #6683b1; text-align: center">Liste
						des élèves non assurés</h2>
					<table border="1">
						<tr>
							<!-- ligne1 -->
							<th>Identifiant</th>
							<!-- case 1 -->
							<th>Nom</th>
							<!-- case 2 -->
							<th>Prénom</th>
							<!-- case 3 -->
							<th>Date de naissace</th>
							<!-- case 4 -->
							<th>Depuis</th>
							<!-- case 5 -->
							<th>Jusqu'au</th>
							<!-- case 6 -->
						</tr>
						<%
							STUDENT myStudent = null;
							ArrayList<ArrayList<Object>> students = null;
							ArrayList<Object> ligne1 = null;
							String debut = "";
							String fin = "";
							students = DBManager.getSTUDENT_non_assures();
							int studentsSize = students.size();
						%>
							<script>
							for (var i=1;i < <%=studentsSize%>;i++) excel_Students_non_Assures.set({row:i, style: i%2==0 ? evenRow: oddRow  });
							</script>
						<%
							for (int i = 0; i < studentsSize; i++) {
								ligne1 = students.get(i);
								myStudent = DBManager.getSTUDENT(Integer.valueOf(ligne1.get(0).toString()));
								debut = ligne1.get(1).toString();
								fin = ligne1.get(2).toString();

								String link_student_assurance = "assurance.jsp?ID=" + myStudent.ID;

								LocalDate d = LocalDate.now();
								String assurance = "";
								try {
									if (DBManager.check_ASSURANCE(Integer.parseInt(myStudent.ID), d))
										assurance = "OK";
									else
										assurance = "<font color=\"red\">Non assuré</font>";
								} catch (java.lang.NumberFormatException e) {
									e.printStackTrace();
								}
						%>
						<tr>
							<!-- ligne i -->
							<th><a href=<%=link_student_assurance%>><%=myStudent.ID%></a></th>
							<!-- case 1 -->
							<th><%=myStudent.NOM%></th>
							<!-- case 2 -->
							<th><%=myStudent.PRENOM%></th>
							<!-- case 3 -->
							<th><%=myStudent.DATE_NAIS%></th>
							<!-- case 4 -->
							<th><%=debut%></th>
							<!-- case 5 -->
							<th><%=fin%></th>
							<!-- case 6 -->
						</tr>
						<script>
						excel_Students_non_Assures.set(0,0,<%=i%>+1,"<%=myStudent.ID%>");
						excel_Students_non_Assures.set(0,1,<%=i%>+1,"<%=myStudent.NOM%>");
						excel_Students_non_Assures.set(0,2,<%=i%>+1,"<%=myStudent.PRENOM%>");
						excel_Students_non_Assures.set(0,3,<%=i%>+1,"<%=myStudent.DATE_NAIS%>");
						excel_Students_non_Assures.set(0,4,<%=i%>+1,"<%=debut%>");
						excel_Students_non_Assures.set(0,5,<%=i%>+1,"<%=fin%>");
						</script>
						<%
							}
						%>
					</table>
					<br /> <br /> <input type="button" id="exportbtn"
						value="Exporter la liste"
						onclick="download_Students_non_Assures('Liste_Eleves_non_Assures.xlsx');" />
					<script src="assets/js/myScripts.js"></script>
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
</body>
</html>