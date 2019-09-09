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
<title>LISTE DES ENSEIGNANTS NON ASSURES</title>
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
					<h2
						style="color: white; background-color: #6683b1; text-align: center">Liste
						des enseignants non assurés</h2>
					<table border="1">
						<tr>
							<!-- ligne1 -->
							<th>Identifiant</th>
							<!-- case 1 -->
							<th>Nom</th>
							<!-- case 2 -->
							<th>Prénom</th>
							<!-- case 3 -->
							<th>Depuis</th>
							<!-- case 4 -->
							<th>Jusqu'au</th>
							<!-- case 5 -->
						</tr>
						<%
							TEACHER myTeacher = null;
							ArrayList<ArrayList<Object>> teachers = null;
							ArrayList<Object> ligne1 = null;
							String debut = "";
							String fin = "";
							teachers = DBManager.getTEACHER_non_assures();
							for (int i = 0; i < teachers.size(); i++) {
								ligne1 = teachers.get(i);
								myTeacher = DBManager.getTEACHER(Integer.valueOf(ligne1.get(0).toString()));
								debut = ligne1.get(1).toString();
								fin = ligne1.get(2).toString();

								String link_teacher_assurance = "assurance.jsp?ID=" + myTeacher.ID;

								//java.util.Date d = new java.util.Date();
								LocalDate d = LocalDate.now();
								String assurance = "";
								try {
									if (DBManager.check_ASSURANCE(Integer.parseInt(myTeacher.ID), d))
										assurance = "OK";
									else
										assurance = "<font color=\"red\">Non assuré</font>";
								} catch (java.lang.NumberFormatException e) {
									System.out.println("Exception : " + e.getMessage());
								}
						%>
						<tr>
							<!-- ligne i -->
							<th><a href=<%=link_teacher_assurance%>><%=myTeacher.ID%></a>
							</th>
							<!-- case 1 -->
							<th><%=myTeacher.NOM%></th>
							<!-- case 2 -->
							<th><%=myTeacher.PRENOM%></th>
							<!-- case 3 -->
							<th><%=debut%></th>
							<!-- case 4 -->
							<th><%=fin%></th>
							<!-- case 5 -->
						</tr>
						<%
							}
						%>
					</table>
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