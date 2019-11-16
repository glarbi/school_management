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
<title>LISTE DES ELEVES</title>
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
					<form name="studentForm" method="get"
						style="width: 50%; margin: auto; background-color: #c1d9fc; padding-bottom: 15px;">
						<div>
							<h2 style="color: white; background-color: #6683b1; text-align: center">CHERCHER UN ELEVE</h2>
							Nom : <input type="text" name="nom" style="color: graytext" align="right" />
							Prenom : <input type="text" name="prenom" style="color: graytext" align="right" />
							ID : <input type="text" name="id" style="color: graytext" value="0" size=25 align="right" />
							</br>
							<div style="width: 50%; margin: auto;">
								<input type="submit" name="OK" value="OK" />&emsp;&emsp;&emsp;&emsp;
								<input type="reset">
							</div>
						</div>
					</form>

					<br />
					<input type="button" id="exportbtn" value="Exporter la liste" onclick="download_Students('Liste_Eleves.xlsx');" /> 
					
					<script src="assets/js/myScripts.js"></script>
					<script src="assets/js/scriptExcel_List_Students.js"></script>
					<br /> <br />

					<table border="1">
						<caption>
							<h2 style="color: white; background-color: #6683b1; text-align: center">Liste des élèves</h2>
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
							<th>Niveau scolaire</th>
							<!-- case 8 -->
							<th>Prénom du père</th>
							<!-- case 9 -->
							<th>Profession du père</th>
							<!-- case 10 -->
							<th>Nom de la mère</th>
							<!-- case 11 -->
							<th>Prénom de la mère</th>
							<!-- case 12 -->
							<th>Profession de la mère</th>
							<!-- case 13 -->
							<th>Adresse</th>
							<!-- case 14 -->
							<th>Téléphone</th>
							<!-- case 15 -->
							<th>Date d'inscription</th>
							<!-- case 16 -->
							<th>Matières</th>
							<!-- case 17 -->
						</tr>
						<%
							STUDENT myStudent = new STUDENT();

							String t1 = request.getParameter("id");
							String t2 = request.getParameter("nom");
							String t3 = request.getParameter("prenom");
							if (t1 == null && t2 == null && t3 == null) {
								myStudent.ID = "0";
								myStudent.NOM = "";
								myStudent.PRENOM = "";
							} else {
								if (t1 != null)
									myStudent.ID = t1;
								if (t2 != null)
									myStudent.NOM = t2;
								if (t3 != null)
									myStudent.PRENOM = t3;
							}

							ArrayList<ArrayList<Object>> students = null;
							if (myStudent.ID.equals("0") && myStudent.NOM.isEmpty() && myStudent.PRENOM.isEmpty())
								students = DBManager.getSTUDENT(0, "", "");
							else {
								try {
									students = DBManager.getSTUDENT(Integer.parseInt(myStudent.ID), myStudent.NOM, myStudent.PRENOM);
								} catch (java.lang.NumberFormatException e) {
									e.printStackTrace();
								}
							}
							int studentsSize = students.size();
						%>
						<script>
						for (var i=1;i < <%=studentsSize%>;i++) excel_Students.set({row:i, style: i%2==0 ? evenRow: oddRow  });
						</script>
						<%
							ArrayList<Object> ligne1 = null;

							for (int i = 0; i < studentsSize; i++) {
								ligne1 = students.get(i);
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

								Integer studentIdInt = Integer.parseInt(myStudent.ID);
								String link_subject = "";
								ArrayList<SUBJECT> mySubjects = DBManager.getSUBJECT_by_Student(studentIdInt);
								for (int j=0; j<mySubjects.size(); j++) {
									LEVEL myLevel = DBManager.getLEVEL_by_ID(mySubjects.get(j).idlevel);
									link_subject = link_subject.concat("<a href=subject.jsp?ID="+ mySubjects.get(j).idsubject+">"+mySubjects.get(j).title+" "+myLevel.levelTitle+"</a><br>");
								}

								String link_student = "student.jsp?ID=" + myStudent.ID;
								String link_student_paiement = "paiement_student.jsp?ID=" + myStudent.ID;
								String link_student_assurance = "assurance.jsp?ID=" + myStudent.ID;

								LocalDate d = LocalDate.now();
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

								String paiement = "";
								String paiementToExcel = "";
								try {
									String date = d.format(formatter);
									ArrayList<REGISTRATION> registrations = DBManager.getRegistrations(Integer.parseInt(myStudent.ID));
									boolean isOK = true;
									int j=0, regSize = registrations.size();
									while (isOK && j<regSize) {
										REGISTRATION myReg = registrations.get(j);
										if (!DBManager.check_PAIEMENT(Integer.parseInt(myStudent.ID), myReg.idTeacher, myReg.idSubject, date)) isOK = false;
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
									if (DBManager.check_ASSURANCE(Integer.parseInt(myStudent.ID), d)) {
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
							<th><a href=<%=link_student%>><%=myStudent.ID%></a></th>
							<!-- case 1 -->
							<th><%=myStudent.NOM%></th>
							<!-- case 2 -->
							<th><%=myStudent.PRENOM%></th>
							<!-- case 3 -->
							<th><a href=<%=link_student_paiement%>><%=paiement%></a></th>
							<!-- case 4 -->
							<th><a href=<%=link_student_assurance%>><%=assurance%></a></th>
							<!-- case 5 -->
							<th><%=myStudent.DATE_NAIS%></th>
							<!-- case 6 -->
							<th><%=myStudent.LIEU_NAIS%></th>
							<!-- case 7 -->
							<th><%=myStudent.schoolLevel%></th>
							<!-- case 8 -->
							<th><%=myStudent.prenomPere%></th>
							<!-- case 9 -->
							<th><%=myStudent.profPere%></th>
							<!-- case 10 -->
							<th><%=myStudent.nomMere%></th>
							<!-- case 11 -->
							<th><%=myStudent.prenomMere%></th>
							<!-- case 12 -->
							<th><%=myStudent.profMere%></th>
							<!-- case 13 -->
							<th><%=myStudent.ADRESSE%></th>
							<!-- case 14 -->
							<th><%=myStudent.NUM_TEL%></th>
							<!-- case 15 -->
							<th><%=myStudent.DATE_INSCRIPTION%></th>
							<!-- case 16 -->
							<th><%=link_subject%></th>
							<!-- case 17 -->
						</tr>
						<script>
						excel_Students.set(0,0,<%=i%>+1,"<%=myStudent.ID%>");
						excel_Students.set(0,1,<%=i%>+1,"<%=myStudent.NOM%>");
						excel_Students.set(0,2,<%=i%>+1,"<%=myStudent.PRENOM%>");
						excel_Students.set(0,3,<%=i%>+1,"<%=paiementToExcel%>");
						excel_Students.set(0,4,<%=i%>+1,"<%=assuranceToExcel%>");
						excel_Students.set(0,5,<%=i%>+1,"<%=myStudent.DATE_NAIS%>");
						excel_Students.set(0,6,<%=i%>+1,"<%=myStudent.LIEU_NAIS%>");
						excel_Students.set(0,7,<%=i%>+1,"<%=myStudent.schoolLevel%>");
						excel_Students.set(0,8,<%=i%>+1,"<%=myStudent.prenomPere%>");
						excel_Students.set(0,9,<%=i%>+1,"<%=myStudent.profPere%>");
						excel_Students.set(0,10,<%=i%>+1,"<%=myStudent.nomMere%>");
						excel_Students.set(0,11,<%=i%>+1,"<%=myStudent.prenomMere%>");
						excel_Students.set(0,12,<%=i%>+1,"<%=myStudent.profMere%>");
						excel_Students.set(0,13,<%=i%>+1,"<%=myStudent.ADRESSE%>");
						excel_Students.set(0,14,<%=i%>+1,"<%=myStudent.NUM_TEL%>");
						excel_Students.set(0,15,<%=i%>+1,"<%=myStudent.DATE_INSCRIPTION%>");
						</script>
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
	
</body>
</html>