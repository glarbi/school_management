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
						Integer day_begin = 1;
						Integer month_begin = 1;
						Integer year_begin = 2018;
						Integer day_end = 1;
						Integer month_end = 1;
						Integer year_end = 2018;

						List<Integer> dayList = new ArrayList<Integer>();
						for (int i = 0; i < 31; i++)
							dayList.add(i, Integer.valueOf(i + 1));

						List<Integer> monthList = new ArrayList<Integer>();
						for (int i = 0; i < 12; i++)
							monthList.add(i, Integer.valueOf(i + 1));

						List<Integer> yearList = new ArrayList<Integer>();
						for (int i = 0; i < 30; i++)
							yearList.add(i, Integer.valueOf(i + year_begin));

						PERSONNE myPersonne = new PERSONNE("_", "_", "_", "01/01/"+year_begin, "_", "_", "_", "01/01/"+year_begin);
						String idStr = request.getParameter("ID");
						Integer idInt = null;
						if (idStr == null)
							idInt = 0;
						else {
							try {
								idInt = Integer.parseInt(idStr);
							} catch (java.lang.NumberFormatException e) {
								System.out.println("Exception : " + e.getMessage());
							}
						}

						if (idInt > 0) {
							ArrayList<Object> assurance = DBManager.get_ASSURANCE(idInt);
							if (assurance.size() > 0) {
								String debut = (String) assurance.get(0);
								year_begin = Integer.valueOf(debut.substring(0, 4));
								month_begin = Integer.valueOf(debut.substring(5, 7));
								day_begin = Integer.valueOf(debut.substring(8, 10));
								String fin = (String) assurance.get(1);
								year_end = Integer.valueOf(fin.substring(0, 4));
								month_end = Integer.valueOf(fin.substring(5, 7));
								day_end = Integer.valueOf(fin.substring(8, 10));
							}
						}

						String tmp = null;

						tmp = request.getParameter("Day_begin");
						if (tmp != null)
							day_begin = Integer.valueOf(tmp);
						tmp = request.getParameter("Month_begin");
						if (tmp != null)
							month_begin = Integer.valueOf(tmp);
						tmp = request.getParameter("Year_begin");
						if (tmp != null)
							year_begin = Integer.valueOf(tmp);
						tmp = request.getParameter("Day_end");
						if (tmp != null)
							day_end = Integer.valueOf(tmp);
						tmp = request.getParameter("Month_end");
						if (tmp != null)
							month_end = Integer.valueOf(tmp);
						tmp = request.getParameter("Year_end");
						if (tmp != null)
							year_end = Integer.valueOf(tmp);

						if (!(day_begin.equals(1) && month_begin.equals(1) && year_begin.equals(2018) && day_end.equals(1) && month_end.equals(1)
								&& year_end.equals(2018))) {
							DBManager.set_ASSURANCE(idInt,
									year_begin.toString() + "/" + month_begin.toString() + "/" + day_begin.toString(),
									year_end.toString() + "/" + month_end.toString() + "/" + day_end.toString());
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
							String debut = (String) assurance.get(0);
							year_begin = Integer.valueOf(debut.substring(0, 4));
							month_begin = Integer.valueOf(debut.substring(5, 7));
							day_begin = Integer.valueOf(debut.substring(8, 10));
							String fin = (String) assurance.get(1);
							year_end = Integer.valueOf(fin.substring(0, 4));
							month_end = Integer.valueOf(fin.substring(5, 7));
							day_end = Integer.valueOf(fin.substring(8, 10));
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
										<input readonly="readonly" type="text" name="ID"
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
								<th>Jour : <select name="Day_begin">
										<%
											for (int i = 0; i < dayList.size(); i++) {
												if (day_begin.equals(dayList.get(i))) {
										%>
										<option value=<%=i + 1%> selected><%=dayList.get(i)%></option>
										<%
											} else {
										%>
										<option value=<%=i + 1%>><%=dayList.get(i)%></option>
										<%
											}
											}
										%>
								</select> Mois : <select name="Month_begin">
										<%
											for (int i = 0; i < monthList.size(); i++) {
												if (month_begin.equals(monthList.get(i))) {
										%>
										<option value=<%=i + 1%> selected><%=monthList.get(i)%></option>
										<%
											} else {
										%>
										<option value=<%=i + 1%>><%=monthList.get(i)%></option>
										<%
											}
											}
										%>
								</select> Année : <select name="Year_begin">
										<%
											for (int i = 0; i < yearList.size(); i++) {
												if (year_begin.equals(yearList.get(i))) {
										%>
										<option value=<%=year_begin + i%> selected><%=yearList.get(i)%></option>
										<%
											} else {
										%>
										<option value=<%=year_begin + i%>><%=yearList.get(i)%></option>
										<%
											}
											}
										%>
								</select>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Date fin :</p>
								</th>
								<th>Jour : <select name="Day_end">
										<%
											for (int i = 0; i < dayList.size(); i++) {
												if (day_end.equals(dayList.get(i))) {
										%>
										<option value=<%=i + 1%> selected><%=dayList.get(i)%></option>
										<%
											} else {
										%>
										<option value=<%=i + 1%>><%=dayList.get(i)%></option>
										<%
											}
											}
										%>
								</select> Mois : <select name="Month_end">
										<%
											for (int i = 0; i < monthList.size(); i++) {
												if (month_end.equals(monthList.get(i))) {
										%>
										<option value=<%=i + 1%> selected><%=monthList.get(i)%></option>
										<%
											} else {
										%>
										<option value=<%=i + 1%>><%=monthList.get(i)%></option>
										<%
											}
											}
										%>
								</select> Année : <select name="Year_end">
										<%
											for (int i = 0; i < yearList.size(); i++) {
												if (year_end.equals(yearList.get(i))) {
										%>
										<option value=<%=year_begin + i%> selected><%=yearList.get(i)%></option>
										<%
											} else {
										%>
										<option value=<%=year_begin + i%>><%=yearList.get(i)%></option>
										<%
											}
											}
										%>
								</select>
								</th>
							</tr>
						</table>
						<INPUT TYPE=SUBMIT NAME="submit_paiement" VALUE="Mise à jour">
					</form>
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
</body>
</html>