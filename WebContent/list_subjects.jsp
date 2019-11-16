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
<title>LISTE DES MATIERES</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />

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
					<input type="button" id="addSubBtn" value="Ajout/Supp matière" onclick="redirectToSubject()" />
					<br /> <br />

					<table border="1">
						<caption>
							<h2 style="color: white; background-color: #6683b1; text-align: center">Liste des matières</h2>
						</caption>
						<tr>
							<!-- ligne1 -->
							<th>Identifiant</th>
							<!-- case 1 -->
							<th>Intitulé</th>
							<!-- case 2 -->
							<th>Niveau scolaire</th>
							<!-- case 3 -->
						</tr>
						<%
							SUBJECT mySubject = new SUBJECT();
							ArrayList<SUBJECT> subjects = null;
							subjects = DBManager.getSUBJECTS();
							for (int i = 0; i < subjects.size(); i++) {
								String link_subject = "subject.jsp?ID="+ subjects.get(i).idsubject;
								LEVEL myLevel = DBManager.getLEVEL_by_ID(subjects.get(i).idlevel);
								String link_level = "school_level.jsp?ID="+ subjects.get(i).idlevel;

						%>
						<tr>
							<!-- ligne i -->
							<th><a href=<%=link_subject%>><%=subjects.get(i).idsubject%></a></th>
							<!-- case 1 -->
							<th><%=subjects.get(i).title%></th>
							<!-- case 2 -->
							<th><a href=<%=link_level%>><%=myLevel.levelTitle%></a></th>
							<!-- case 3 -->
						</tr>
						<%
							}
						%>
					</table>
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
		function redirectToSubject() {
			location.replace("subject.jsp")
		}
	</script>
	
	
</body>
</html>