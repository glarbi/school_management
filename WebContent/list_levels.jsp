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
<title>LISTE DES NIVEAUX SCOLAIRES</title>
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
					<input type="button" id="addLevelBtn" value="Ajout/Supp un niveau" onclick="redirectToLevel()" />
					<br /> <br />

					<table border="1">
						<caption>
							<h2 style="color: white; background-color: #6683b1; text-align: center">Liste des niveaux scolaires</h2>
						</caption>
						<tr>
							<!-- ligne1 -->
							<th>Identifiant</th>
							<!-- case 1 -->
							<th>Niveau scolaire</th>
							<!-- case 2 -->
						</tr>
						<%
							LEVEL myLevel = new LEVEL();
							ArrayList<LEVEL> levels = DBManager.getLEVELS();
							for (int i = 0; i < levels.size(); i++) {
								String link_level = "school_level.jsp?ID="+ levels.get(i).ID;
						%>
						<tr>
							<!-- ligne i -->
							<th><a href=<%=link_level%>><%=levels.get(i).ID%></a></th>
							<!-- case 1 -->
							<th><%=levels.get(i).levelTitle%></th>
							<!-- case 2 -->
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
		function redirectToLevel() {
			location.replace("school_level.jsp")
		}
	</script>
	
	
</body>
</html>