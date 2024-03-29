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
<title>PAGE D'ASSURANCE</title>
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

					<%
						String dateDebut = "";
						String dateFin = "";

						PERSONNE myPersonne = new PERSONNE("_", "_", "_", "01/01/2019", "_", "_", "_", "01/01/2019");
						String idStr = request.getParameter("ID");
						Integer idInt = null;
						if (idStr == null)
							idInt = 0;
						else {
							try {
								idInt = Integer.parseInt(idStr);
							} catch (java.lang.NumberFormatException e) {
								e.printStackTrace();
							}
						}

						if (idInt > 0) {
							ArrayList<Object> assurance = DBManager.get_ASSURANCE(idInt);
							if (assurance.size() > 0) {
								dateDebut = (String) assurance.get(0);
								dateFin = (String) assurance.get(1);
							}
						}

						String tmp = null;

						tmp = request.getParameter("date_debut");
						if (tmp != null)
							dateDebut = tmp;
						tmp = request.getParameter("date_fin");
						if (tmp != null)
							dateFin = tmp;

						if (dateDebut != null && !dateDebut.isEmpty() && dateFin != null && !dateFin.isEmpty()) {
							DBManager.set_ASSURANCE(idInt, dateDebut, dateFin);
						}

						if (idStr != null) {
							ArrayList<ArrayList<Object>> myList = null;
							if (idInt <= 100) {
								myList = DBManager.getTEACHER(idInt, "", "");
								if (myList.isEmpty()) {
									idInt = 1;
									myList = DBManager.getTEACHER(idInt, "", "");
								}
							} else {
								myList = DBManager.getSTUDENT(idInt, "", "");
								if (myList.isEmpty()) {
									idInt = 101;
									myList = DBManager.getSTUDENT(idInt, "", "");
								}
							}
							if (myList.size() > 0) {
								ArrayList<Object> ligne1 = myList.get(0);
								myPersonne.ID = ligne1.get(0).toString();
								myPersonne.NOM = ligne1.get(1).toString();
								myPersonne.PRENOM = ligne1.get(2).toString();
							}
						}
						ArrayList<Object> assurance = DBManager.get_ASSURANCE(idInt);
						if (assurance.size() > 0) {
							dateDebut = (String) assurance.get(0);
							dateFin = (String) assurance.get(1);
						}
					%>
					<%
						if (idStr != null) {
					%>
					<a href="assurance.jsp?ID=<%=idInt + 1%>">Suivant --></a> <br />
					<%
						}
					%>
					<form name="AssuranceForm" method="get"
						style="width: 50%; margin: auto; background-color: #c1d9fc; padding-bottom: 15px;">
						<%
							if (idInt <= 100) {
						%>
						<p align="center">
							<h2>Information sur l'assurance de l'enseignant</h2>
						</p>
						<%
							} else {
						%>
						<p align="center">
							<h2>Information sur l'assurance de l'élève</h2>
						</p>
						<%
							}
						%>
						<table>
							<tr>
								<th>
									<p align="left">Identifiant :</p>
								</th>
								<th>
									<p>
										<input readonly="readonly" type="text" id="ID" name="ID"
											style="color: graytext" value="<%=myPersonne.ID%>" size=25
											align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Nom de l'élève :</p>
								</th>
								<th>
									<p align="left"><%=myPersonne.NOM%></p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Prénom de l'élève :</p>
								</th>
								<th>
									<p align="left"><%=myPersonne.PRENOM%></p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left"></p>
								</th>
								<th>
									<p align="left"></p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Date début :</p>
								</th>
								<th>
									<input type="date" id="debutId" name="date_debut" value="<%=dateDebut%>" />
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Date fin :</p>
								</th>
								<th>
									<input type="date" id="finId" name="date_fin" value="<%=dateFin%>" />
								</th>
							</tr>
						</table>
						<INPUT TYPE=SUBMIT NAME="submit_paiement" VALUE="Mise à jour"> 
						<INPUT type="button" id="SuppBtn" value="Supprimer assurance" onclick="removeAssurance()"/>
					</form>
					<div id="Result" style="color:red;"></div>
				</div>
			</div>
			<!-- Footer section is defined in another file -->
			<iframe height="650px" width="100%" src="footer.html" name="iframe_footer"></iframe>
			
		</div>

		<!-- Scripts -->

		<!-- <script src="assets/js/readConfigAssurance.js"></script> -->
		<script src="assets/js/jquery.min.js"></script>
		<script src="assets/js/jquery.dropotron.min.js"></script>
		<script src="assets/js/browser.min.js"></script>
		<script src="assets/js/breakpoints.min.js"></script>
		<script src="assets/js/util.js"></script>
		<script src="assets/js/main.js"></script>
		<script>
		function removeAssurance() {
			var _id = document.getElementById("ID").value;
			$.ajax({
				url: "JSONServletRemove",
				type: "POST",
				data: {assuranceId:_id},
				dataType: "json",
				success: function (result) {
					document.getElementById("Result").innerHTML = result;
					document.getElementById("subjectFormId").reset();
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