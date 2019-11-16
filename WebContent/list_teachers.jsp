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
<title>LISTE DES ENSEIGNANTS</title>
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
					<form name="teacherForm" method="get"
						style="width: 50%; margin: auto; background-color: #c1d9fc; padding-bottom: 15px;">
						<div>
							<h2 style="color: white; background-color: #6683b1; text-align: center">CHERCHER UN ENSEIGNANT</h2>
							Nom : <input type="text" name="nom" style="color: graytext" align="right" />
							Prenom : <input type="text" name="prenom" style="color: graytext" align="right" />
							ID : <input type="text" name="id" style="color: graytext" value="0" align="right" />
							</br>
							<div style="width: 50%; margin: auto;">
								<input type="submit" name="OK" value="OK" />&emsp;&emsp;&emsp;&emsp;
								<input type="reset">
							</div>
						</div>
					</form>
					<br />
					<input type="button" id="exportbtn" value="Exporter la liste" onclick="download_Teachers('Liste_Enseignants.xlsx');" />
					<script src="assets/js/myScripts.js"></script>
					<script src="assets/js/scriptExcel_List_Teachers.js"></script>
					<br /> <br />

					<table border="1">
						<caption>
							<h2
								style="color: white; background-color: #6683b1; text-align: center">Liste des enseignants</h2>
						</caption>
						<tr>
							<!-- ligne1 -->
							<th>Identifiant</th>
							<!-- case 1 -->
							<th>Nom</th>
							<!-- case 2 -->
							<th>Prénom</th>
							<!-- case 3 -->
							<th>Paiement</th>
							<!-- case 4 -->
							<th>Assurance</th>
							<!-- case 5 -->
							<th>Date de naissance</th>
							<!-- case 6 -->
							<th>Lieu de naissance</th>
							<!-- case 7 -->
							<th>Adresse</th>
							<!-- case 8 -->
							<th>Téléphone</th>
							<!-- case 9 -->
							<th>Date d'inscription</th>
							<!-- case 10 -->
							<th>Matières enseignées</th>
							<!-- case 11 -->
						</tr>
						<%
							TEACHER myTeacher = new TEACHER();

							String t1 = request.getParameter("id");
							String t2 = request.getParameter("nom");
							String t3 = request.getParameter("prenom");
							if (t1 == null && t2 == null && t3 == null) {
								myTeacher.ID = "0";
								myTeacher.NOM = "";
								myTeacher.PRENOM = "";
							} else {
								if (t1 != null)
									myTeacher.ID = t1;
								if (t2 != null)
									myTeacher.NOM = t2;
								if (t3 != null)
									myTeacher.PRENOM = t3;
							}

							ArrayList<ArrayList<Object>> teachers = null;
							if (myTeacher.ID == "0" && myTeacher.NOM.isEmpty() && myTeacher.PRENOM.isEmpty())
								teachers = DBManager.getTEACHER(0, "", "");
							else {
								try {
									teachers = DBManager.getTEACHER(Integer.parseInt(myTeacher.ID), myTeacher.NOM, myTeacher.PRENOM);
								} catch (java.lang.NumberFormatException e) {
									e.printStackTrace();
								}
							}
							int teachersSize = teachers.size();
						%>
						<script>
						for (var i=1; i < <%=teachersSize%>; i++) excel_Teachers.set({row:i,style: i%2==0 ? evenRow: oddRow  });
						</script>
						<%
							ArrayList<Object> ligne1 = null;
							for (int i = 0; i < teachersSize; i++) {
								ligne1 = teachers.get(i);
								myTeacher.ID = ligne1.get(0).toString();
								myTeacher.NOM = ligne1.get(1).toString();
								myTeacher.PRENOM = ligne1.get(2).toString();
								myTeacher.DATE_NAIS = ligne1.get(3).toString();
								myTeacher.LIEU_NAIS = ligne1.get(4).toString();
								myTeacher.ADRESSE = ligne1.get(5).toString();
								myTeacher.NUM_TEL = ligne1.get(6).toString();
								myTeacher.DATE_INSCRIPTION = ligne1.get(7).toString();
								
								Integer teacherIdInt = Integer.parseInt(myTeacher.ID);
								String link_teacher_subject = "";
								ArrayList<SUBJECT> mySubjects = DBManager.getSUBJECT_by_Teacher(teacherIdInt);
								for (int j=0; j<mySubjects.size(); j++) {
									LEVEL myLevel = DBManager.getLEVEL_by_ID(mySubjects.get(j).idlevel);
									link_teacher_subject = link_teacher_subject.concat("<a href=teacher_subject.jsp?idTeacher="+teacherIdInt+"&idSubject="+ mySubjects.get(j).idsubject+">"+mySubjects.get(j).title+" "+myLevel.levelTitle+"</a><br>");
								}

								String link_teacher = "teacher.jsp?ID=" + myTeacher.ID;
								String link_teacher_paiement = "paiement_teacher.jsp?ID=" + myTeacher.ID;
								String link_teacher_assurance = "assurance.jsp?ID=" + myTeacher.ID;

								LocalDate d = LocalDate.now();
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-");

								String paiement = "";
								String paiementToExcel = "";
								try {
									String date = d.format(formatter);
									ArrayList<SUBJECT> subjects = DBManager.getSUBJECT_by_Teacher(teacherIdInt);
									boolean isOK = true;
									int j=0, subjectsSize = subjects.size();
									while (isOK && j<subjectsSize) {
										SUBJECT myReg = subjects.get(j);
										if (!DBManager.check_PAIEMENT_TEACHER(teacherIdInt, myReg.idsubject, date+"01")) isOK = false;
										j++;
									}

									if (isOK) {
										paiement = "OK";
										paiementToExcel = "OK";
									} else {
										paiement = "<font color=\"red\">Non payé</font>";
										paiementToExcel = "no OK";
									}
								} catch (java.lang.NumberFormatException e) {
									e.printStackTrace();
								}
								String assurance = "";
								String assuranceToExcel = "";
								try {
									if (DBManager.check_ASSURANCE(Integer.parseInt(myTeacher.ID), d)) {
										assurance = "OK";
										assuranceToExcel = "OK";
									} else {
										assurance = "<font color=\"red\">Non assuré</font>";
										assuranceToExcel = "no OK";
									}
								} catch (java.lang.NumberFormatException e) {
									e.printStackTrace();
								}
						%>
						<tr>
							<!-- ligne i -->
							<th><a href=<%=link_teacher%>><%=myTeacher.ID%></a></th>
							<!-- case 1 -->
							<th><%=myTeacher.NOM%></th>
							<!-- case 2 -->
							<th><%=myTeacher.PRENOM%></th>
							<!-- case 3 -->
							<th><a href=<%=link_teacher_paiement%>><%=paiement%></a></th>
							<!-- case 4 -->
							<th><a href=<%=link_teacher_assurance%>><%=assurance%></a></th>
							<!-- case 5 -->
							<th><%=myTeacher.DATE_NAIS%></th>
							<!-- case 6 -->
							<th><%=myTeacher.LIEU_NAIS%></th>
							<!-- case 7 -->
							<th><%=myTeacher.ADRESSE%></th>
							<!-- case 8 -->
							<th><%=myTeacher.NUM_TEL%></th>
							<!-- case 9 -->
							<th><%=myTeacher.DATE_INSCRIPTION%></th>
							<!-- case 10 -->
							<th><%=link_teacher_subject%></th>
							<!-- case 11 -->
						</tr>
						<script>
						excel_Teachers.set(0,0,<%=i%>+1,"<%=myTeacher.ID%>");
						excel_Teachers.set(0,1,<%=i%>+1,"<%=myTeacher.NOM%>");
						excel_Teachers.set(0,2,<%=i%>+1,"<%=myTeacher.PRENOM%>");
						excel_Teachers.set(0,3,<%=i%>+1,"<%=paiementToExcel%>");
						excel_Teachers.set(0,4,<%=i%>+1,"<%=assuranceToExcel%>");
						excel_Teachers.set(0,5,<%=i%>+1,"<%=myTeacher.DATE_NAIS%>");
						excel_Teachers.set(0,6,<%=i%>+1,"<%=myTeacher.LIEU_NAIS%>");
						excel_Teachers.set(0,7,<%=i%>+1,"<%=myTeacher.ADRESSE%>");
						excel_Teachers.set(0,8,<%=i%>+1,"<%=myTeacher.NUM_TEL%>");
						excel_Teachers.set(0,9,<%=i%>+1,"<%=myTeacher.DATE_INSCRIPTION%>");
						excel_Teachers.set(0,10,<%=i%>+1,"matières");
						</script>
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