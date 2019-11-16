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
<title>Affectation Enseignant-Matière</title>
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
					<%
					String t1 = request.getParameter("lname");
					String t2 = request.getParameter("fname");
					String t3 = request.getParameter("subjectLevel");
					if (t1 != null && t2 != null && t3 != null && (!t1.isEmpty() && !t2.isEmpty() && !t3.isEmpty())) {
						try {
							DBManager.setTeacherSubject(t1, t2, t3);
						} catch (java.lang.NumberFormatException e) {
							e.printStackTrace();
						}
					}
					
					String t4 = request.getParameter("idTeacher");
					String t5 = request.getParameter("idSubject");
					TEACHER myTeacher = null;
					SUBJECT mySubject = null;
					boolean view = false;
					if (t4 != null && t5 != null && (!t4.isEmpty()) && (!t5.isEmpty())) {
						myTeacher = DBManager.getTEACHER(Integer.parseInt(t4));
						mySubject = DBManager.getSUBJECT_by_ID(Integer.parseInt(t5));
						view = true;
					}
					%>

					<form id="teacherSubjectFormId" name="teacherSubjectFormId" method="get"
						style="width: 50%; margin: auto; background-color: #c1d9fc; padding-bottom: 15px;" accept-charset="UTF-8">
						<p align="center">
						<h2>Affectation Enseignant-Matière</h2>
						</p>

						<datalist id="lnameList">
						<%
						ArrayList<ArrayList<Object>> teachers = DBManager.getTEACHER(0, "", "");
						for (int i = 0; i < teachers.size(); i++) {
						%>
							<option><%= teachers.get(i).get(1).toString()%></option>
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
									<p align="left">Nom de l'enseignant :</p>
								</th>
								<th>
									<p>
									<%if (view) {%>
										<input type="text" id="inputLname" name="lname" style="color: graytext"
											list="lnameList" value="<%=myTeacher.NOM%>" autocomplete="off" size=25 align="right" />
									<%} else {%>											
										<input type="text" id="inputLname" name="lname" style="color: graytext"
											list="lnameList" autocomplete="off" size=25 align="right" />
									<%}%>
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Prénom de l'enseignant :</p>
								</th>
								<th>
									<p>
									<%if (view) {%>
										<input type="text" id="inputFname" name="fname" style="color: graytext" 
										value="<%=myTeacher.PRENOM%>" autocomplete="off" size=25 align="right"/>
									<%} else {%>											
										<input type="text" id="inputFname" name="fname" style="color: graytext" 
										autocomplete="off" size=25 align="right" disabled/>
									<%}%>
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">Matière :</p>
								</th>
								<th>
									<p>
									<%if (view) {%>
										<input type="text" id="inputSubject" name="subjectLevel" style="color: graytext" list="subjectList" 
										value="<%=mySubject.title%> <%=DBManager.getLEVEL_by_ID(mySubject.idlevel).levelTitle%>" autocomplete="off" size=25 align="right"/>
									<%} else {%>											
										<input type="text" id="inputSubject" name="subjectLevel" style="color: graytext" list="subjectList" 
										autocomplete="off" size=25 align="right"/>
									<%}%>
									</p>
								</th>
							</tr>
						</table>
						<p style="width: 100%; margin: auto;" align="center">
							<INPUT type="submit" name="OK" value="OK" /> <INPUT type="reset"> 
							<%
							String mycall = "";
							if (view) {
								mycall = "\"removeTeacherSubject("+myTeacher.ID+","+mySubject.idsubject+")\"";
							%>
							<INPUT type="button" id="SuppBtn" value="Annuler Enseignant->Matière" onclick=<%=mycall%> />
							<%
							}
							%>
						</p>
					</form>
					<div id="Result" style="color:red;"></div>
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
		//document.getElementById("SuppBtn").disabled = true;
		$("#inputLname").change(function(event){
			var _inputLname = document.getElementById("inputLname");
			var _inputFname = document.getElementById("inputFname");
			var _inputSubject = document.getElementById("inputSubject");
			if (_inputLname.value != "" && _inputFname.value != "" && _inputSubject.value != "") document.getElementById("SuppBtn").disabled = false;
			$.get('JSONServletFnameTeacher', {lname:_inputLname.value}, function(responseJson) {
				if (responseJson != null) {
					$("#fnameList").remove();
					$("#inputFname").val("");
					var _form = document.getElementById("teacherSubjectFormId");
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
	});
	function removeTeacherSubject(idT, idSub) {
		$.ajax({
			url: "JSONServletRemove",
			type: "POST",
			data: {TeacherSubject_idTeacher:idT,
				TeacherSubject_idSubject:idSub},
			dataType: "json",
			success: function (result) {
				//location.reload();
				document.getElementById("Result").innerHTML = result;
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