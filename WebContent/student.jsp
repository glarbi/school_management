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
<title>Nouvel élève</title>
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
						ArrayList<LEVEL> levelList = DBManager.getLEVELS();

						String date_naissance = "";
						String date_inscription = "";
						String tmp = null;

						tmp = request.getParameter("dateNais");
						if (tmp != null)
							date_naissance = tmp;
						tmp = request.getParameter("dateInsc");
						if (tmp != null)
							date_inscription = tmp;
						tmp = request.getParameter("Day_naissance");

						String t1 = request.getParameter("ID");
						String t2 = request.getParameter("Nom");
						String t3 = request.getParameter("Prenom");
						//String t4 = year_naissance.toString() + "-" + month_naissance.toString() + "-" + day_naissance.toString();
						String t4 = request.getParameter("dateNais");
						String t5 = request.getParameter("LieuNais");
						String t6 = request.getParameter("PrenomPere");
						String t7 = request.getParameter("ProfPere");
						String t8 = request.getParameter("NomMere");
						String t9 = request.getParameter("PrenomMere");
						String t10 = request.getParameter("ProfMere");
						String t11 = request.getParameter("Adresse");
						String t12 = request.getParameter("Tel");
						//String t13 = year_inscription.toString() + "-" + month_inscription.toString() + "-" + day_inscription.toString();
						String t13 = request.getParameter("dateInsc");
						String t14 = request.getParameter("SchoolLevel");

						if ((t1 != null && t2 != null && t3 != null && t5 != null && t6 != null && t7 != null && t8 != null
								&& t9 != null && t10 != null && t11 != null && t12 != null && t14 != null
								&& (!t1.isEmpty() && !t2.isEmpty() && !t3.isEmpty() && !t5.isEmpty() && !t6.isEmpty()
										&& !t7.isEmpty() && !t8.isEmpty() && !t9.isEmpty() && !t10.isEmpty() && !t11.isEmpty()
										&& !t12.isEmpty() && !t14.isEmpty()))) {
							try {
								Integer t1int = Integer.parseInt(t1);
								LEVEL myLevel = DBManager.getLEVEL_by_levelTitle(t14);
								DBManager.setSTUDENT(t1int, myLevel.ID, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
								t1 = null; //Pour faire entrer un nouveau élève
							} catch (java.lang.NumberFormatException e) {
								e.printStackTrace();
							}
						}

						if (t1 == null)
							t1 = DBManager.getFreeStudentID().toString();
						LocalDate date = LocalDate.now();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						String today = date.format(formatter);

						STUDENT myStudent = new STUDENT(t1, "_", "_", "1990-01-01", "_", "_", "_", today, "_", "_", "_", "_", "_", "_");
						Integer day = null;
						Integer month = null;
						Integer year = null;
						Integer idInt = null;
						String level = null;
						if (t1 == null || t1.equals("-1"))
							idInt = 0;
						else {
							try {
								idInt = Integer.parseInt(t1);
							} catch (java.lang.NumberFormatException e) {
								e.printStackTrace();
							}
						}
						if (t1 != null) {
							ArrayList<ArrayList<Object>> students = DBManager.getSTUDENT(idInt, "", "");
							if (students.size() > 0) {
								List<Object> ligne1 = (List<Object>) (students.get(0));
								myStudent.ID = ligne1.get(0).toString();
								myStudent.NOM = ligne1.get(1).toString();
								myStudent.PRENOM = ligne1.get(2).toString();
								myStudent.DATE_NAIS = ligne1.get(3).toString();
								myStudent.LIEU_NAIS = ligne1.get(4).toString();
								myStudent.schoolLevel = ligne1.get(13).toString();
								myStudent.prenomPere = ligne1.get(5).toString();
								myStudent.profPere = ligne1.get(6).toString();
								myStudent.nomMere = ligne1.get(7).toString();
								myStudent.prenomMere = ligne1.get(8).toString();
								myStudent.profMere = ligne1.get(9).toString();
								myStudent.ADRESSE = ligne1.get(10).toString();
								myStudent.NUM_TEL = ligne1.get(11).toString();
								myStudent.DATE_INSCRIPTION = ligne1.get(12).toString();
							}
						}
					%>
					<form id="studentFormId" name="studentForm" method="get"
						style="width: 50%; margin: auto; background-color: #c1d9fc; padding-bottom: 15px;" accept-charset="UTF-8">
						<p align="center">
						<h2>Informations de l'élève</h2>
						</p>
						<table>
							<tr>
								<th>
									<p align="left">Identifiant :</p>
								</th>
								<th>
									<p>
										<input type="text" id="ID" name="ID" style="color: graytext"
											value="<%=myStudent.ID%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Nom de l'élève :</p>
								</th>
								<th>
									<p>
										<input type="text" name="Nom" style="color: graytext"
											value="<%=myStudent.NOM%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Prénom de l'élève :</p>
								</th>
								<th>
									<p>
										<input type="text" name="Prenom" style="color: graytext"
											value="<%=myStudent.PRENOM%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Niveau Scolaire :</p>
								</th>

								<th><%level = myStudent.schoolLevel; %>
									<select name="SchoolLevel">
											<%
												for (int i = 0; i < levelList.size(); i++) {
													if (level.equals(levelList.get(i).levelTitle)) {
											%>
											<option value=<%=levelList.get(i).levelTitle%> selected><%=levelList.get(i).levelTitle%></option>
											<%
													} else {
											%>
											<option value=<%=levelList.get(i).levelTitle%>><%=levelList.get(i).levelTitle%></option>
											<%
													}
												}
											%>
									</select>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Date de naissance :</p>
								</th>
								<th>
									<input type="date" id="dateNaisId" name="dateNais" value="<%=myStudent.DATE_NAIS%>" />
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Lieu de naissance :</p>
								</th>
								<th>
									<p>
										<input type="text" name="LieuNais" style="color: graytext"
											value="<%=myStudent.LIEU_NAIS%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Prénom du père :</p>
								</th>
								<th>
									<p>
										<input type="text" name="PrenomPere" style="color: graytext"
											value="<%=myStudent.prenomPere%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Profession du père :</p>
								</th>
								<th>
									<p>
										<input type="text" name="ProfPere" style="color: graytext"
											value="<%=myStudent.profPere%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Nom de la mère :</p>
								</th>
								<th>
									<p>
										<input type="text" name="NomMere" style="color: graytext"
											value="<%=myStudent.nomMere%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Prénom de la mère :</p>
								</th>
								<th>
									<p>
										<input type="text" name="PrenomMere" style="color: graytext"
											value="<%=myStudent.prenomMere%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Profession de la mère :</p>
								</th>
								<th>
									<p>
										<input type="text" name="ProfMere" style="color: graytext"
											value="<%=myStudent.profMere%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Adresse :</p>
								</th>
								<th>
									<p>
										<input type="text" name="Adresse" style="color: graytext"
											value="<%=myStudent.ADRESSE%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Téléphone :</p>
								</th>
								<th>
									<p>
										<input type="text" name="Tel" style="color: graytext"
											value="<%=myStudent.NUM_TEL%>" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Date d'inscription :</p>
								</th>
								<th>
									<input type="date" id="dateInscId" name="dateInsc" value="<%=myStudent.DATE_INSCRIPTION%>" />
								</th>
							</tr>
						</table>
						<p style="width: 100%; margin: auto;" align="center">
							<INPUT type="submit" name="OK" value="OK" /> <INPUT type="reset"> 
							<INPUT type="button" id="SuppBtn" value="Supprimer un élève" onclick="removeStudent()" />
						</p>
					</form>
					<div id="Result" style="color:red;"></div>
					<br />
					<%
						if (!myStudent.ID.equals(DBManager.getFreeStudentID().toString())) {
					%>
					<a href="student.jsp?ID=<%=Integer.parseInt(myStudent.ID) + 1%>">Suivant</a><br />
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
	<script>
	function removeStudent() {
		var _idstudent = document.getElementById("ID").value;
		$.ajax({
			url: "JSONServletRemove",
			type: "POST",
			data: {idStudent:_idstudent},
			dataType: "json",
			success: function (result) {
				document.getElementById("Result").innerHTML = result;
				document.getElementById("studentFormId").reset();
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