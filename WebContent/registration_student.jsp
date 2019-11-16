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
<title>Inscription élève</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
</head>
<body class="is-preload no-sidebar">
	<!--<applet id="app" width=0 height=0 code="DBManagerApplet"></applet>-->
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
					String t1 = request.getParameter("lname");
					String t2 = request.getParameter("fname");
					String t3 = request.getParameter("subject");
					String t4 = request.getParameter("teacher");
					String date_inscription = request.getParameter("dateInsc");

					if ((t1 != null && t2 != null && t3 != null && t4 != null && date_inscription != null) && (!t1.isEmpty() && !t2.isEmpty() && !t3.isEmpty() && !t4.isEmpty() && !date_inscription.isEmpty())) {
						String[] t3_splitted = t3.split(" ");
						String[] t4_splitted = t4.split(" ");
						try {
							STUDENT st = DBManager.getSTUDENTbyName(t1, t2);
							TEACHER t = DBManager.getTEACHERbyName(t4_splitted[0], t4_splitted[1]);
							SUBJECT sb = DBManager.getSUBJECT_by_TITLE_Level(t3_splitted[0], t3_splitted[1]);
							DBManager.setRegistration(Integer.parseInt(st.ID), Integer.parseInt(t.ID), sb.idsubject, date_inscription);
						} catch (java.lang.NumberFormatException e) {
							e.printStackTrace();
						}
					}

					%>
					<form id="registrationFormId" name="registrationForm" method="get"
						style="width: 50%; margin: auto; background-color: #c1d9fc; padding-bottom: 15px;" accept-charset="UTF-8">
						<p align="center">
						<h2>Inscription de l'élève</h2>
						</p>

						<datalist id="lnameList">
						<%
						ArrayList<ArrayList<Object>> students = DBManager.getSTUDENT(0, "", "");
						for (int i = 0; i < students.size(); i++) {
						%>
							<option><%= students.get(i).get(1).toString()%></option>
						<%} %>
						</datalist>
						<datalist id="subjectList">
						<%
						ArrayList<SUBJECT> subjects = DBManager.getSUBJECTS();
						for (int i = 0; i < subjects.size(); i++) {
						%>
							<option><%= subjects.get(i).title%> <%= DBManager.getLEVEL_by_ID(subjects.get(i).idlevel).levelTitle%></option>
						<%} %>
						</datalist>
						<table>
							<tr>
								<th>
									<p align="left">Nom de l'élève :</p>
								</th>
								<th>
									<p>
										<input type="text" id="inputLname" name="lname" style="color: graytext"
											list="lnameList" autocomplete="off" size=25 align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Prénom de l'élève :</p>
								</th>
								<th>
									<p>
										<input type="text" id="inputFname" name="fname" style="color: graytext" autocomplete="off"
											size=25 align="right" disabled/>
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Matière :</p>
								</th>
								<th>
									<p>
										<input type="text" id="inputSubject" name="subject" style="color: graytext" list="subjectList" autocomplete="off"
											size=25 align="right"/>
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Enseignant :</p>
								</th>
								<th>
									<p>
										<input type="text" id="inputTeacher" name="teacher" style="color: graytext" autocomplete="off"
											size=25 align="right" disabled/>
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Date d'inscription :</p>
								</th>
								<th>
									<%
									Integer day = null;
									Integer month = null;
									Integer year = null;
									LocalDate date = LocalDate.now();
									DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
									String today = date.format(formatter);
									%>
									<input type="date" id="dateInscId" name="dateInsc" value="<%=today%>" />
								</th>
							</tr>
						</table>
						<p style="width: 50%; margin: auto;" align="center">
							<INPUT type="submit" name="OK" value="OK" /> <INPUT type="reset">
						</p>
					</form>
					<br />
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
		$("#inputLname").change(function(event){
			var _lname = document.getElementById("inputLname").value;
			$.get('JSONServletFnameStudent', {lname:_lname}, function(responseJson) {
				if (responseJson != null) {
					$("#fnameList").remove();
					$("#inputFname").val("");
					var _form = document.getElementById("registrationFormId");
					var _datalist = _form.appendChild(document.createElement('datalist'));
					_datalist.id = 'fnameList';
					var _inputFname = document.getElementById("inputFname");
					_inputFname.setAttribute('list','fnameList');
					$.each(responseJson, function(key,value) {
						var _option = _datalist.appendChild(document.createElement('option'));
						_option.text = value[2];
					});
					$("#inputFname").removeAttr('disabled');
				}
			});
		});
		$("#inputSubject").change(function(event){
			var _subject_title_level = document.getElementById("inputSubject").value;

			$.get('JSONServletTeacher_Subject', {subject_title_level:_subject_title_level}, function(responseJson) {
				if (responseJson != null) {
					$("#teacherList").remove();
					$("#inputTeacher").val("");
					var _form = document.getElementById("registrationFormId");
					var _datalist = _form.appendChild(document.createElement('datalist'));
					_datalist.id = 'teacherList';
					var _inputTeacher = document.getElementById("inputTeacher");
					_inputTeacher.setAttribute('list','teacherList');
					var teachers = $.parseJSON(responseJson);
					for(var i = 0; i < teachers.length; i++) {
						var _option = _datalist.appendChild(document.createElement('option'));
						_option.text = teachers[i].NOM + " " + teachers[i].PRENOM;
					}
					$("#inputTeacher").removeAttr('disabled');
				}
			});
		});
	});
	</script>
					
</body>
</html>