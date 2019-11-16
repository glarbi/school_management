<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="org.*"%>
<%@ page import="java.util.StringTokenizer"%>
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
<title>Mise à jour d'une matière</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
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

					<%
						String t1 = request.getParameter("ID");
						String t2 = request.getParameter("TITLE");
						String t3 = request.getParameter("SchoolLevel");

						Integer idInt = null;
						LEVEL myLevel = null;
						if ((t1 != null && t2 != null && t3 != null)
								&& (!t1.isEmpty() && !t2.isEmpty() && !t3.isEmpty())) {
							try {
								idInt = Integer.parseInt(t1);
								myLevel = DBManager.getLEVEL_by_levelTitle(t3);
								DBManager.setSubject(idInt, t2, myLevel.ID);
								//Pour faire entrer un nouveau élève
								t1 = null;
								myLevel = null;
							} catch (java.lang.NumberFormatException e) {
								e.printStackTrace();
							}
						}

						SUBJECT mySubject = null;
						Integer freeSubjectID = DBManager.getFreeSubjectID();
						if (t1 == null) {
							idInt = freeSubjectID;
							t1 = idInt.toString();
							mySubject = new SUBJECT(idInt, "", 1);
						} else {
							if (t2==null || t2.isEmpty() || myLevel==null) {
								idInt = Integer.parseInt(t1);
								mySubject = DBManager.getSUBJECT_by_ID(idInt);
								myLevel = DBManager.getLEVEL_by_ID(mySubject.idlevel);
							} else {
								mySubject = new SUBJECT(idInt, t2, myLevel.ID);
							}
						}

					%>
					<form id="subjectFormId" name="subjectForm" method="get"
						style="width: 60%; margin: auto; background-color: #c1d9fc; padding-bottom: 15px;" accept-charset="UTF-8">
						<p align="center">
						<h2>Informations de la matière</h2>
						</p>
						<%
						ArrayList<SUBJECT> subjects = DBManager.getSUBJECTS();
						ArrayList<LEVEL> levels = DBManager.getLEVELS();
						%>
						<datalist id="idSubjectList">
						<%
						for (int i = 0; i < subjects.size(); i++) {
						%>
							<option><%=subjects.get(i).idsubject%></option>
						<%
						}
						%>
						</datalist>
						<datalist id="subjectList">
						<%
						for (int i = 0; i < subjects.size(); i++) {
						%>
							<option><%=subjects.get(i).title%></option>
						<%
						}
						%>
						</datalist>
						<datalist id="levelList">
						<%
						for (int i = 0; i < levels.size(); i++) {
						%>
							<option><%=levels.get(i).levelTitle%></option>
						<%
						}
						%>
						</datalist>
						<table>
							<tr>
								<th>
									<p align="left">Identifiant :</p>
								</th>
								<th>
									<p>
										<input type="text" id="ID" name="ID" style="color: graytext" list="idSubjectList" autocomplete="off" 
											value="<%=mySubject.idsubject%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Intitulé de la matière :</p>
								</th>
								<th>
									<p>
										<input type="text" id="TITLE" name="TITLE" style="color: graytext" list="subjectList" autocomplete="off" 
											value="<%=mySubject.title%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Niveau scolaire :</p>
								</th>
								<th>
									<p>
									<%
									String levelTitle = "";
									if (myLevel != null) levelTitle = myLevel.levelTitle; %>
										<input type="text" id="SchoolLevel" name="SchoolLevel" style="color: graytext" list="levelList" autocomplete="off" 
											value="<%=levelTitle%>" size=25 align="right" />
									</p>
								</th>
							</tr>
						</table>
						<p style="width: 100%; margin: auto;" align="center">
							<INPUT type="submit" name="OK" value="OK" /> <INPUT type="reset"> 
							<INPUT type="button" id="SuppBtn" value="Supprimer une matière" onclick="removeSubject()" />
						</p>
					</form>
					<div id="Result" style="color:red;"></div>
					<br />
					<%
						if (mySubject.idsubject < (freeSubjectID-1)) {
					%>
					<a href="subject.jsp?ID=<%=mySubject.idsubject + 1%>">Suivant</a><br />
					<%
						}
					%>
				</div>
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

	<script type="text/javascript">
	$(document).ready(function() {
		$("#ID").change(function(event){
			var _id = document.getElementById("ID").value;
			$.get('JSONServletSubject', {idS:_id}, function(responseJsonSubject) {
				if (responseJsonSubject != null) {
					document.getElementById("TITLE").value = responseJsonSubject.title;
					var _idlevel = responseJsonSubject.idlevel;
					$.get('JSONServletSubject', {idL:_idlevel}, function(responseJsonLevel) {
						if (responseJsonLevel != null) {
							document.getElementById("SchoolLevel").value = responseJsonLevel.levelTitle;
						}
					});
				}
			});
		});
	});
	function removeSubject() {
		var _subjectTitle = document.getElementById("TITLE").value;
		var _schoolLevel = document.getElementById("SchoolLevel").value;
		var r = confirm("Ete-vous sûr de supprimer la matière "+_subjectTitle+" "+_schoolLevel+" ?");
		if (r == true) {
			var _idsubject = document.getElementById("ID").value;
			$.ajax({
				url: "JSONServletRemove",
				type: "POST",
				data: {idS:_idsubject},
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
	}
	</script>

</body>
</html>