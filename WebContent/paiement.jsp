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
<title>PAGE DE PAIEMENT</title>
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
						PERSONNE myPersonne = new PERSONNE("_", "_", "_", "01/01/1990", "_", "_", "_", "01/01/1990");
						String idStr = request.getParameter("ID");
						Integer idInt = null;
						String montant = "";
						String check = "";
						ArrayList<ArrayList<Object>> affichages = null;
						if (idStr != null) {
							try {
								idInt = Integer.parseInt(idStr);
							} catch (java.lang.NumberFormatException e) {
								System.out.println("Exception : " + e.getMessage());
							}

							LocalDate d = LocalDate.now();
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

							int my_year = 0;
							if (d.getMonthValue() + 1 >= 10)
								my_year = d.getYear();
							else
								my_year = d.getYear() - 1;

							montant = request.getParameter("montant_Octobre");
							check = request.getParameter("Octobre");
							if (check != null && check.equals("on") && montant != null && Float.valueOf(montant) >= 0)
								DBManager.set_PAIEMENT(idInt, my_year + "/10/01", Float.parseFloat(montant));
							else if (check == null && montant != null)
								DBManager.set_PAIEMENT(idInt, my_year + "/10/01", Float.valueOf(-1));
							montant = request.getParameter("montant_Novembre");
							check = request.getParameter("Novembre");
							if (check != null && check.equals("on") && montant != null && Float.valueOf(montant) >= 0)
								DBManager.set_PAIEMENT(idInt, my_year + "/11/01", Float.parseFloat(montant));
							else if (check == null && montant != null)
								DBManager.set_PAIEMENT(idInt, my_year + "/11/01", Float.valueOf(-1));
							montant = request.getParameter("montant_Decembre");
							check = request.getParameter("Decembre");
							if (check != null && check.equals("on") && montant != null && Float.valueOf(montant) >= 0)
								DBManager.set_PAIEMENT(idInt, my_year + "/12/01", Float.parseFloat(montant));
							else if (check == null && montant != null)
								DBManager.set_PAIEMENT(idInt, my_year + "/12/01", Float.valueOf(-1));
							montant = request.getParameter("montant_Janvier");
							check = request.getParameter("Janvier");
							if (check != null && check.equals("on") && montant != null && Float.valueOf(montant) >= 0)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/01/01", Float.parseFloat(montant));
							else if (check == null && montant != null)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/01/01", Float.valueOf(-1));
							montant = request.getParameter("montant_Fevrier");
							check = request.getParameter("Fevrier");
							if (check != null && check.equals("on") && montant != null && Float.valueOf(montant) >= 0)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/02/01", Float.parseFloat(montant));
							else if (check == null && montant != null)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/02/01", Float.valueOf(-1));
							montant = request.getParameter("montant_Mars");
							check = request.getParameter("Mars");
							if (check != null && check.equals("on") && montant != null && Float.valueOf(montant) >= 0)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/03/01", Float.parseFloat(montant));
							else if (check == null && montant != null)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/03/01", Float.valueOf(-1));
							montant = request.getParameter("montant_Avril");
							check = request.getParameter("Avril");
							if (check != null && check.equals("on") && montant != null && Float.valueOf(montant) >= 0)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/04/01", Float.parseFloat(montant));
							else if (check == null && montant != null)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/04/01", Float.valueOf(-1));
							montant = request.getParameter("montant_Mai");
							check = request.getParameter("Mai");
							if (check != null && check.equals("on") && montant != null && Float.valueOf(montant) >= 0)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/05/01", Float.parseFloat(montant));
							else if (check == null && montant != null)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/05/01", Float.valueOf(-1));
							montant = request.getParameter("montant_Juin");
							check = request.getParameter("Juin");
							if (check != null && check.equals("on") && montant != null && Float.valueOf(montant) >= 0)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/06/01", Float.parseFloat(montant));
							else if (check == null && montant != null)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/06/01", Float.valueOf(-1));
							montant = request.getParameter("montant_Juillet");
							check = request.getParameter("Juillet");
							if (check != null && check.equals("on") && montant != null && Float.valueOf(montant) >= 0)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/07/01", Float.parseFloat(montant));
							else if (check == null && montant != null)
								DBManager.set_PAIEMENT(idInt, (my_year + 1) + "/07/01", Float.valueOf(-1));

							if (idStr != null) {
								ArrayList<ArrayList<Object>> myList = null;
								if (idInt <= 100) {
									myList = DBManager.getTEACHER(idInt, "", "");
									if (myList.size() == 0) {
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
									List<Object> ligne1 = myList.get(0);
									myPersonne.ID = ligne1.get(0).toString();
									myPersonne.NOM = ligne1.get(1).toString();
									myPersonne.PRENOM = ligne1.get(2).toString();
								}

								// Paiements
								ArrayList<ArrayList<Object>> paiements = DBManager.get_PAIEMENT(idInt);
								affichages = new ArrayList<ArrayList<Object>>();
								for (int i = 0; i < 10; i++) {
									ArrayList<Object> affichage = new ArrayList<Object>();
									affichage.add(0, "0.00");
									affichage.add(1, "");
									affichages.add(i, affichage);
								}
								ArrayList<Object> ligne = null;
								for (int i = 0; i < paiements.size(); i++) {
									ligne = paiements.get(i);
									String tmp = ligne.get(1).toString();
									int pos = tmp.lastIndexOf("-");
									int mois = Integer.parseInt(tmp.substring(pos - 2, pos)); // Mois
									tmp = ligne.get(2).toString(); //Montant
									ArrayList<Object> affichage = null;
									switch (mois) {
									case 10: {
										affichage = affichages.get(0);
										affichage.add(0, tmp);
										if (!tmp.equals("-1.00"))
											affichage.add(1, "CHECKED");
										break;
									}
									case 11: {
										affichage = affichages.get(1);
										affichage.add(0, tmp);
										if (!tmp.equals("-1.00"))
											affichage.add(1, "CHECKED");
										break;
									}
									case 12: {
										affichage = affichages.get(2);
										affichage.add(0, tmp);
										if (!tmp.equals("-1.00"))
											affichage.add(1, "CHECKED");
										break;
									}
									case 1: {
										affichage = affichages.get(3);
										affichage.add(0, tmp);
										if (!tmp.equals("-1.00"))
											affichage.add(1, "CHECKED");
										break;
									}
									case 2: {
										affichage = affichages.get(4);
										affichage.add(0, tmp);
										if (!tmp.equals("-1.00"))
											affichage.add(1, "CHECKED");
										break;
									}
									case 3: {
										affichage = affichages.get(5);
										affichage.add(0, tmp);
										if (!tmp.equals("-1.00"))
											affichage.add(1, "CHECKED");
										break;
									}
									case 4: {
										affichage = affichages.get(6);
										affichage.add(0, tmp);
										if (!tmp.equals("-1.00"))
											affichage.add(1, "CHECKED");
										break;
									}
									case 5: {
										affichage = affichages.get(7);
										affichage.add(0, tmp);
										if (!tmp.equals("-1.00"))
											affichage.add(1, "CHECKED");
										break;
									}
									case 6: {
										affichage = affichages.get(8);
										affichage.add(0, tmp);
										if (!tmp.equals("-1.00"))
											affichage.add(1, "CHECKED");
										break;
									}
									case 7: {
										affichage = affichages.get(9);
										affichage.add(0, tmp);
										if (!tmp.equals("-1.00"))
											affichage.add(1, "CHECKED");
										break;
									}
									}
								}
							}
						}
					%>
					<%
						if (idStr != null) {
					%>
					<a href="paiement.jsp?ID=<%=idInt + 1%>">Suivant --></a> <br />
					<%
						}
					%>
					
					<form name="PaiementForm" method="get"
						style="width: 50%; margin: auto; background-color: #c1d9fc; padding-bottom: 15px;">
						<%
							if (idInt <= 100) {
						%>
						<p align="center">
						<h2>Détails du paiement de l'enseignant</h2>
						</p>

						<%
							} else {
						%>
						<p align="center">
						<h2>Détails du paiement de l'élève</h2>
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
									<p align="left">
										<input type="checkbox" id="Octobre" name="Octobre" <%=(affichages.get(0)).get(1).toString()%>><label for="Octobre">Octobre</label>
									</p>
								</th>
								<th>
									<p align="left">
										Montant : <input type="text" name="montant_Octobre"
											style="color: graytext"
											value="<%=(affichages.get(0)).get(0).toString()%>" size=25
											align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">
										<input type="checkbox" id="Novembre" name="Novembre" <%=(affichages.get(1)).get(1).toString()%>><label for="Novembre">Novembre</label>
									</p>
								</th>
								<th>
									<p align="left">
										Montant : <input type="text" name="montant_Novembre"
											style="color: graytext"
											value="<%=(affichages.get(1)).get(0).toString()%>" size=25
											align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">
										<input type="checkbox" id="Decembre" name="Decembre" <%=(affichages.get(2)).get(1).toString()%>><label for="Decembre">Decembre</label>
									</p>
								</th>
								<th>
									<p align="left">
										Montant : <input type="text" name="montant_Decembre"
											style="color: graytext"
											value="<%=(affichages.get(2)).get(0).toString()%>" size=25
											align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">
										<input type="checkbox" id="Janvier" name="Janvier" <%=(affichages.get(3)).get(1).toString()%>><label for="Janvier">Janvier</label>
									</p>
								</th>
								<th>
									<p align="left">
										Montant : <input type="text" name="montant_Janvier"
											style="color: graytext"
											value="<%=(affichages.get(3)).get(0).toString()%>" size=25
											align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">
										<input type="checkbox" id="Fevrier" name="Fevrier" <%=(affichages.get(4)).get(1).toString()%>><label for="Fevrier">Fevrier</label>
									</p>
								</th>
								<th>
									<p align="left">
										Montant : <input type="text" name="montant_Fevrier"
											style="color: graytext"
											value="<%=(affichages.get(4)).get(0).toString()%>" size=25
											align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">
										<input type="checkbox" id="Mars" name="Mars" <%=(affichages.get(5)).get(1).toString()%>><label for="Mars">Mars</label>
									</p>
								</th>
								<th>
									<p align="left">
										Montant : <input type="text" name="montant_Mars"
											style="color: graytext"
											value="<%=(affichages.get(5)).get(0).toString()%>" size=25
											align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">
										<input type="checkbox" id="Avril" name="Avril" <%=(affichages.get(6)).get(1).toString()%>><label for="Avril">Avril</label>
									</p>
								</th>
								<th>
									<p align="left">
										Montant : <input type="text" name="montant_Avril"
											style="color: graytext"
											value="<%=(affichages.get(6)).get(0).toString()%>" size=25
											align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">
										<input type="checkbox" id="Mai" name="Mai" <%=(affichages.get(7)).get(1).toString()%>><label for="Mai">Mai</label>
									</p>
								</th>
								<th>
									<p align="left">
										Montant : <input type="text" name="montant_Mai"
											style="color: graytext"
											value="<%=(affichages.get(7)).get(0).toString()%>" size=25
											align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">
										<input type="checkbox" id="Juin" name="Juin" <%=(affichages.get(8)).get(1).toString()%>><label for="Juin">Juin</label>
									</p>
								</th>
								<th>
									<p align="left">
										Montant : <input type="text" name="montant_Juin"
											style="color: graytext"
											value="<%=(affichages.get(8)).get(0).toString()%>" size=25
											align="right" />
									</p>
								</th>
							</tr>
							<tr>
								<th>
									<p align="left">
										<input type="checkbox" id="Juillet" name="Juillet" <%=(affichages.get(9)).get(1).toString()%>><label for="Juillet">Juillet</label>
									</p>
								</th>
								<th>
									<p align="left">
										Montant : <input type="text" name="montant_Juillet"
											style="color: graytext"
											value="<%=(affichages.get(9)).get(0).toString()%>" size=25
											align="right" />
									</p>
								</th>
							</tr>
						</table>
						<INPUT TYPE=SUBMIT NAME="submit_paiement" VALUE="Mise à jour">
					</form>
					<br />
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