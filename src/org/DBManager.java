package org;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBManager {
	public static Connection getConnection() {
		Connection con = null;

		try {
			Context context = new InitialContext();
			//DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/SMDB");  //to use H2 datasource
			DataSource ds = (DataSource) context.lookup("java:jboss/datasources/SMDB");
			con = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static int creatTables()
	{
		int ret = 0;
		Connection con = getConnection();
		if (con==null) {
			System.out.println("con = null");
			ret = -1;
			return ret;
		}
		String myRequest = "";
		PreparedStatement pStmt;
		try {
			//Création de la table "STUDENT"
			myRequest = "CREATE TABLE IF NOT EXISTS STUDENT ("+
			"ID INTEGER PRIMARY KEY,"+ // Identifiant l'élève (101-infini)
			"NOM VARCHAR(30) NOT NULL,"+
			"PRENOM VARCHAR(30) NOT NULL,"+
			"DATE_NAIS DATE,"+
			"LIEU_NAIS VARCHAR(30) NOT NULL,"+
			"PRENOM_PERE VARCHAR(30) NOT NULL,"+
			"PROFESSION_PERE VARCHAR(30) NOT NULL,"+
			"NOM_MERE VARCHAR(30) NOT NULL,"+
			"PRENOM_MERE VARCHAR(30) NOT NULL,"+
			"PROFESSION_MERE VARCHAR(30) NOT NULL,"+
			"ADRESSE VARCHAR(1024) NOT NULL,"+
			"NUM_TEL VARCHAR(30) NOT NULL,"+
			"DATE_INSCRIPTION DATE,"+
			"IMG_PATH VARCHAR(1024)) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
//Afficher myRequest
System.out.println("INFO -- Create table : "+myRequest);
			pStmt = con.prepareStatement(myRequest);
			pStmt.executeUpdate();
			pStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ret = -1;
		}

		try {
			//Création de la table "SCHOOL_LEVEL"
			myRequest = "CREATE TABLE IF NOT EXISTS SCHOOL_LEVEL ("+
			"ID INTEGER PRIMARY KEY,"+ // Identifiant du niveau scolaire
			"LEVEL VARCHAR(30) NOT NULL) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
//Afficher myRequest
System.out.println("INFO -- Create table : "+myRequest);
			pStmt = con.prepareStatement(myRequest);
			pStmt.executeUpdate();
			pStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ret = -1;
		}

		try {
			//Création de la table "STUDENT_LEVEL"
			myRequest = "CREATE TABLE IF NOT EXISTS STUDENT_LEVEL ("+
			"IDSTUDENT INTEGER NOT NULL,"+ // Identifiant du niveau scolaire
			"IDLEVEL INTEGER NOT NULL,"+
			"PRIMARY KEY (IDSTUDENT,IDLEVEL)"+
			"FOREIGN KEY (IDSTUDENT) REFERENCES STUDENT(ID)"+
			"FOREIGN KEY (IDLEVEL) REFERENCES SCHOOL_LEVEL(ID)) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
//Afficher myRequest
System.out.println("INFO -- Create table : "+myRequest);
			pStmt = con.prepareStatement(myRequest);
			pStmt.executeUpdate();
			pStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ret = -1;
		}

		try {
			//Création de la table "SUBJECT"
			myRequest = "CREATE TABLE IF NOT EXISTS SUBJECT ("+
			"ID INTEGER PRIMARY KEY,"+ // Identifiant de la matière
			"TITLE VARCHAR(255) NOT NULL,"+
			"IDLEVEL INTEGER NOT NULL,"+
			"FOREIGN KEY (IDLEVEL) REFERENCES SCHOOL_LEVEL(ID)) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
//Afficher myRequest
System.out.println("INFO -- Create table : "+myRequest);
			pStmt = con.prepareStatement(myRequest);
			pStmt.executeUpdate();
			pStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ret = -1;
		}

		try {
			//Création de la table "TEACHER"
			myRequest = "CREATE TABLE IF NOT EXISTS TEACHER ("+
			"ID INTEGER PRIMARY KEY,"+ // Identifiant enseignant (1-100)
			"NOM VARCHAR(30) NOT NULL,"+
			"PRENOM VARCHAR(30) NOT NULL,"+
			"DATE_NAIS DATE,"+
			"LIEU_NAIS VARCHAR(30) NOT NULL,"+
			"ADRESSE VARCHAR(1024) NOT NULL,"+
			"NUM_TEL VARCHAR(30) NOT NULL,"+
			"DATE_INSCRIPTION DATE,"+ 
			"IMG_PATH VARCHAR(1024),"+
			"FOREIGN KEY (IDSB) REFERENCES SUBJECT(ID)) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
//Afficher myRequest
System.out.println("INFO -- Create table : "+myRequest);
			pStmt = con.prepareStatement(myRequest);
			pStmt.executeUpdate();
			pStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ret = -1;
		}

		try {
			//Création de la table "TEACHER_SUBJECT"
			myRequest = "CREATE TABLE IF NOT EXISTS TEACHER_SUBJECT ("+
			"IDT INTEGER NOT NULL,"+ // Identifiant enseignant (1-100)
			"IDSB INTEGER NOT NULL,"+ // Identifiant matière
			"PRIMARY KEY (IDT,IDSB),"+
			"FOREIGN KEY (IDT) REFERENCES TEACHER(ID),"+
			"FOREIGN KEY (IDSB) REFERENCES SUBJECT(ID)) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
//Afficher myRequest
System.out.println("INFO -- Create table : "+myRequest);
			pStmt = con.prepareStatement(myRequest);
			pStmt.executeUpdate();
			pStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ret = -1;
		}

		try {
			//Création de la table "REGISTRATION"
			myRequest = "CREATE TABLE IF NOT EXISTS REGISTRATION ("+
			"IDSTUDENT INTEGER NOT NULL,"+ // Identifiant de l'étudiant (100-infini)
			"IDTEACHER INTEGER NOT NULL,"+ // Identifiant de l'enseignant (1-100)
			"IDSUBJECT INTEGER NOT NULL,"+
			"DATE_REG DATE,"+
			"PRIMARY KEY (IDSTUDENT,IDTEACHER,IDSUBJECT),"+
			"FOREIGN KEY (IDSTUDENT) REFERENCES STUDENT(ID),"+
			"FOREIGN KEY (IDTEACHER) REFERENCES TEACHER(ID),"+
			"FOREIGN KEY (IDSUBJECT) REFERENCES SUBJECT(ID)) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
//Afficher myRequest
System.out.println("INFO -- Create table : "+myRequest);
			pStmt = con.prepareStatement(myRequest);
			pStmt.executeUpdate();
			pStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ret = -1;
		}

		try {
			//Création de la table "PAIEMENT"
			myRequest = "CREATE TABLE IF NOT EXISTS PAIEMENT ("+
			"ID INTEGER,"+ // ID>100 (STUDENT)
			"IDTEACHER INTEGER,"+
			"IDSUBJECT INTEGER,"+
			"DEBUT DATE,"+
			"FIN DATE,"+
			"MONTANT DECIMAL(8,2),"+ //8 chiffres dont 2 aprés la virgule
			"PRIMARY KEY (ID,IDTEACHER,IDSUBJECT,DEBUT),"+
			"FOREIGN KEY (ID) REFERENCES STUDENT(ID),"+
			"FOREIGN KEY (IDTEACHER) REFERENCES TEACHER(ID),"+
			"FOREIGN KEY (IDSUBJECT) REFERENCES SUBJECT(ID)) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
//Afficher myRequest
System.out.println("INFO -- Create table : "+myRequest);
			pStmt = con.prepareStatement(myRequest);
			pStmt.executeUpdate();
			pStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ret = -1;
		}

		try {
			//Création de la table "PAIEMENT_TEACHER"
			myRequest = "CREATE TABLE IF NOT EXISTS PAIEMENT_TEACHER ("+
			"ID INTEGER PRIMARY KEY,"+
			"IDTEACHER INTEGER,"+ // 1<ID<=100 (TEACHER)
			"IDSUBJECT INTEGER,"+
			"MOIS DATE,"+
			"MONTANT DECIMAL(8,2),"+ //8 chiffres dont 2 aprés la virgule
			"FOREIGN KEY (IDTEACHER) REFERENCES TEACHER(ID),"+
			"FOREIGN KEY (IDSUBJECT) REFERENCES SUBJECT(ID)) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
//Afficher myRequest
System.out.println("INFO -- Create table : "+myRequest);
			pStmt = con.prepareStatement(myRequest);
			pStmt.executeUpdate();
			pStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ret = -1;
		}

		try {
			//Création de la table "ASSURANCE"
			myRequest = "CREATE TABLE IF NOT EXISTS ASSURANCE ("+
			"ID INTEGER PRIMARY KEY,"+ // 1<ID<=100 (TEACHER) /   ID>100 (STUDENT)
			"debut DATE,"+
			"fin DATE,"+
			"FOREIGN KEY (ID) REFERENCES STUDENT(ID)) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
//Afficher myRequest
System.out.println("INFO -- Create table : "+myRequest);
			pStmt = con.prepareStatement(myRequest);
			pStmt.executeUpdate();
			pStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ret = -1;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	public static void setSTUDENT(Integer id, Integer idLev, String nom, String prenom, String dateNais, String lieuNais,
			String prenomPere, String profPere, String nomMere, String prenomMere, String profMere,
			String adresse, String tel, String dateInscription)
	{
		if (id.intValue() > 0) {
			if (nom == null) nom = "";
			if (prenom == null) prenom = "";
			if (dateNais == null) dateNais = "01/01/2000";
			if (lieuNais == null) lieuNais = "";
			if (idLev == null) idLev = 0;
			if (prenomPere == null) prenomPere = "";
			if (profPere == null) profPere = "";
			if (nomMere == null) nomMere = "";
			if (prenomMere == null) prenomMere = "";
			if (profMere == null) profMere = "";
			if (adresse == null) adresse = "";
			if (tel == null) tel = "";
			if (dateInscription == null) dateInscription = "01/01/2000";
			Connection con = getConnection();
			try {
				String myRequest = "SELECT * FROM STUDENT WHERE ID=" + id.toString();
//Afficher myRequest
System.out.println("setSTUDENT1 : "+myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if ( rs.next() ) {
					pStmt.close();
					myRequest = "UPDATE STUDENT SET NOM='"+nom
					+"', PRENOM='"+prenom
					+"', DATE_NAIS='"+dateNais
					+"', LIEU_NAIS='"+lieuNais
					+"', PRENOM_PERE='"+prenomPere
					+"', PROFESSION_PERE='"+profPere
					+"', NOM_MERE='"+nomMere
					+"', PRENOM_MERE='"+prenomMere
					+"', PROFESSION_MERE='"+profMere
					+"', ADRESSE='"+adresse
					+"', NUM_TEL='"+tel
					+"', DATE_INSCRIPTION='"+dateInscription
					+"' WHERE ID="+id.toString();
//Afficher myRequest
System.out.println("setSTUDENT2 : "+myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				}
				else {
					pStmt.close();
					myRequest = "INSERT INTO STUDENT (ID,NOM,PRENOM,DATE_NAIS,LIEU_NAIS,PRENOM_PERE,PROFESSION_PERE,"+
								"NOM_MERE,PRENOM_MERE,PROFESSION_MERE,ADRESSE,NUM_TEL,DATE_INSCRIPTION) VALUES ("
								+id+",'"+nom+"','"+prenom+"','"+dateNais+"','"+lieuNais+"','"+prenomPere+"','"+profPere+"','"
								+nomMere+"','"+prenomMere+"','"+profMere+"','"+adresse+"','"+tel+"','"+dateInscription+"')";
//Afficher myRequest
System.out.println("setSTUDENT3 : "+myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				}

				myRequest = "SELECT * FROM STUDENT_LEVEL WHERE IDSTUDENT=" + id.toString();
//Afficher myRequest
System.out.println("setSTUDENT4 : "+myRequest);
				pStmt = con.prepareStatement(myRequest);
				rs = pStmt.executeQuery();
				if ( rs.next() ) {
					pStmt.close();
					myRequest = "UPDATE STUDENT_LEVEL SET IDLEVEL='" + idLev + "' WHERE IDSTUDENT=" + id.toString();
//Afficher myRequest
System.out.println("setSTUDENT5 : "+myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				}
				else {
					pStmt.close();
					myRequest = "INSERT INTO STUDENT_LEVEL (IDSTUDENT,IDLEVEL) VALUES (" + id + ",'" + idLev + "')";
//Afficher myRequest
System.out.println("setSTUDENT6 : " + myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void setTEACHER(Integer id, String nom, String prenom, String dateNais, String lieuNais,
			String adresse, String tel, String dateInscription)
	{
		if (id.intValue() > 0) {
			if (nom == null) nom = "";
			if (prenom == null) prenom = "";
			if (dateNais == null) dateNais = "01/01/2000";
			if (lieuNais == null) lieuNais = "";
			if (adresse == null) adresse = "";
			if (tel == null) tel = "";
			if (dateInscription == null) dateInscription = "01/01/2000";
			Connection con = getConnection();
			try {
				String myRequest = "SELECT * FROM TEACHER WHERE ID=" + id.toString();
//Afficher myRequest
System.out.println("setTEACHER1 : "+myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if ( rs.next() ) {
					pStmt.close();
					myRequest = "UPDATE TEACHER SET NOM='"+nom
					+"', PRENOM='"+prenom
					+"', DATE_NAIS='"+dateNais
					+"', LIEU_NAIS='"+lieuNais
					+"', ADRESSE='"+adresse
					+"', NUM_TEL='"+tel
					+"', DATE_INSCRIPTION='"+dateInscription
					+"' WHERE ID="+id.toString();
//Afficher myRequest
System.out.println("setTEACHER2 : "+myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				}
				else {
					pStmt.close();
					myRequest = "INSERT INTO TEACHER (ID,NOM,PRENOM,DATE_NAIS,LIEU_NAIS,"+
								"ADRESSE,NUM_TEL,DATE_INSCRIPTION) VALUES ("
								+id+",'"+nom+"','"+prenom+"','"+dateNais+"','"+lieuNais+"','"+adresse+"','"+tel+"','"+dateInscription+"')";
//Afficher myRequest
System.out.println("setTEACHER3 : "+myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}		
	}
	
	public static void setTeacherSubject(String nom, String prenom, String subjectLevel)
	{
		if (nom == null) nom = "";
		if (prenom == null) prenom = "";
		if (subjectLevel == null) subjectLevel = "";
		if (!nom.isEmpty() && !prenom.isEmpty() && !subjectLevel.isEmpty()) {
			String[] splitted = subjectLevel.split(" ");
			Integer idSubject = getIDSujectbyTitleLevel(splitted);
			TEACHER myTeacher = getTEACHERbyName(nom, prenom);
			Connection con = getConnection();
			try {
				String myRequest = "SELECT * FROM TEACHER_SUBJECT WHERE IDT=" + myTeacher.ID + " AND IDSB=" + idSubject;
//Afficher myRequest
System.out.println("setTeacherSubject : "+myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if ( !rs.next() ) {
					pStmt.close();
					myRequest = "INSERT INTO TEACHER_SUBJECT (IDT,IDSB) VALUES ("+myTeacher.ID+","+idSubject+")";
//Afficher myRequest
System.out.println("setTeacherSubject : "+myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void setSubject(Integer id, String title, Integer idLevel)
	{
		if (id!=null && id>0 && !title.isEmpty() && idLevel!=null && idLevel>0) {
			Connection con = getConnection();
			try {
				String myRequest = "SELECT * FROM SUBJECT WHERE ID=" + id;
//Afficher myRequest
System.out.println("setSubject1 : "+myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if ( !rs.next() ) {
					pStmt.close();
					myRequest = "INSERT INTO SUBJECT (ID,TITLE,IDLEVEL) VALUES ("+id+",'"+title+"',"+idLevel+")";
//Afficher myRequest
System.out.println("setSubject2 : "+myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				} else {
					pStmt.close();
					myRequest = "UPDATE SUBJECT SET TITLE='"+title+"', IDLEVEL="+idLevel+" WHERE ID="+id;
//Afficher myRequest
System.out.println("setSubject3 : "+myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void setLevel(Integer id, String levelTitle)
	{
		if (id!=null && id>0 && !levelTitle.isEmpty()) {
			Connection con = getConnection();
			try {
				String myRequest = "SELECT * FROM SCHOOL_LEVEL WHERE ID=" + id;
//Afficher myRequest
System.out.println("setLevel1 : "+myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if ( !rs.next() ) {
					pStmt.close();
					myRequest = "INSERT INTO SCHOOL_LEVEL (ID,level) VALUES ("+id+",'"+levelTitle+"')";
//Afficher myRequest
System.out.println("setLevel2 : "+myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				} else {
					pStmt.close();
					myRequest = "UPDATE SCHOOL_LEVEL SET level='"+levelTitle+"' WHERE ID="+id;
//Afficher myRequest
System.out.println("setLevel3 : "+myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void setRegistration(Integer idStudent, Integer idTeacher, Integer idSubject, String regDate)
	{
		if (idStudent>100 && idTeacher>0 && idTeacher<=100 && idSubject>0 && regDate!=null && !regDate.isEmpty()) {
			Connection con = getConnection();
			try {
				String myRequest = "SELECT * FROM REGISTRATION WHERE IDSTUDENT=" + idStudent +
						" AND IDTEACHER=" + idTeacher + " AND IDSUBJECT=" + idSubject;
//Afficher myRequest
System.out.println("setRegistration1 : "+myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if ( !rs.next() ) {
					pStmt.close();
					myRequest = "INSERT INTO REGISTRATION (IDSTUDENT,IDTEACHER,IDSUBJECT,DATE_REG) VALUES ("+idStudent+","+idTeacher+","+idSubject+",'"+regDate+"')";
//Afficher myRequest
System.out.println("setRegistration2 : "+myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static ArrayList<REGISTRATION> getRegistrations(Integer idStudent) {
		ArrayList<REGISTRATION> registrations = new ArrayList<REGISTRATION>();

		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				myRequest = "SELECT * FROM REGISTRATION WHERE IDSTUDENT="+idStudent.toString();
//Afficher myRequest
System.out.println("getRegistrations : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					REGISTRATION registration = new REGISTRATION(rs.getInt("IDSTUDENT"),
							rs.getInt("IDTEACHER"), rs.getInt("IDSUBJECT"), rs.getDate("DATE_REG").toString());

					registrations.add(j, registration);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return registrations;
	}

	public static Integer getFreeStudentID(){
		int max = 100;
		Connection con = getConnection();
		if (con == null) {
			return -1;
		} else {
			try {
				String myRequest = "SELECT MAX(ID) AS MYID FROM STUDENT";
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) {
					max = rs.getInt("MYID");
					if (max == 0) max = 100; // cas où MYID == NULL getInt retourne 0 !!!
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (max < 100) return -1;
		return max+1;
	}
	
	public static Integer getFreeSubjectID(){
		int max = 0;
		Connection con = getConnection();
		if (con == null) {
			return -1;
		} else {
			try {
				String myRequest = "SELECT MAX(ID) AS MYID FROM SUBJECT";
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) {
					max = rs.getInt("MYID");
					if (max == 0) max = 1; // cas où MYID == NULL getInt retourne 0 !!!  Empty table
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return max+1;
	}

	public static Integer getFreeLevelID(){
		int max = 0;
		Connection con = getConnection();
		if (con == null) {
			return -1;
		} else {
			try {
				String myRequest = "SELECT MAX(ID) AS MYID FROM SCHOOL_LEVEL";
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) {
					max = rs.getInt("MYID");
					if (max == 0) max = 1; // cas où MYID == NULL getInt retourne 0 !!! Empty table
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return max+1;
	}

	public static Integer getFreeTeacherID(){
		int max = 0;
		Connection con = getConnection();
		if (con == null) {
			return -1;
		} else {
			try {
				String myRequest = "SELECT MAX(ID) AS MYID FROM TEACHER";
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) max = rs.getInt("MYID");
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (max >= 100) return -1;
		return max+1;
	}
	
	public static Integer getFreePaiementTecherID(){
		int max = 0;
		Connection con = getConnection();
		if (con == null) {
			return -1;
		} else {
			try {
				String myRequest = "SELECT MAX(ID) AS MYID FROM PAIEMENT_TEACHER";
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) max = rs.getInt("MYID");
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return max+1;
	}
	
	public static ArrayList<ArrayList<Object>> getSTUDENT(Integer id, String nom,String prenom) {
		ArrayList<ArrayList<Object>> students = new ArrayList<ArrayList<Object>>();

		Connection con = getConnection();
		if (con == null) {
			ArrayList<Object> ret = new ArrayList<Object>();
			ret.add(0, "Problème de connection à la base de données");
			students.add(0, ret);
		} else {
			try {
				String myRequest = "", myRequestLevel = "";
				if ((id != null && nom != null && prenom != null
					&& id == 0 && nom.isEmpty() && prenom.isEmpty()) ||
					(id != null && nom != null && prenom != null
					&& id == 0 && nom.equals("Tapez le nom de l_élève") && prenom.equals("Tapez le prénom de l_élève"))){
					myRequest = "SELECT * FROM STUDENT";
				} else {
					myRequest = "SELECT * FROM STUDENT WHERE ";
					if (id==null || id.toString().isEmpty() || id == 0)
						myRequest = myRequest + "ID=0";
					else
						myRequest = myRequest + "ID=" + id.toString();
					if (nom!=null && !nom.isEmpty() && !nom.equals("Tapez le nom de l_élève")) 
						myRequest = myRequest + " OR NOM='" + nom + "'";
					if (prenom!=null && !prenom.isEmpty() && !prenom.equals("Tapez le prénom de l_élève")) 
						myRequest = myRequest + " OR PRENOM='" + prenom + "'";
				}
				if (!myRequest.isEmpty()) myRequest = myRequest + " ORDER BY ID ASC";
//Afficher myRequest
System.out.println("getstudents : "+myRequest);

				
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				String _sLevel = "";
				while (rs.next()) {
					ArrayList<Object> student = new ArrayList<Object>();

					Integer _id = rs.getInt("ID");
					String _nom = rs.getString("NOM");
					String _prenom = rs.getString("PRENOM");
					String _dateNais = rs.getDate("DATE_NAIS").toString();
					String _lieuNais = rs.getString("LIEU_NAIS");
					String _prenomPere = rs.getString("PRENOM_PERE");
					String _profPere = rs.getString("PROFESSION_PERE");
					String _nomMere = rs.getString("NOM_MERE");
					String _prenomMere = rs.getString("PRENOM_MERE");
					String _profMere = rs.getString("PROFESSION_MERE");
					String _adresse = rs.getString("ADRESSE");
					String _tel = rs.getString("NUM_TEL");
					String _dateInscription = rs.getDate("DATE_INSCRIPTION").toString();

					student.add(0, _id);
					student.add(1, _nom);
					student.add(2, _prenom);
					student.add(3, _dateNais);
					student.add(4, _lieuNais);
					student.add(5, _prenomPere);
					student.add(6, _profPere);
					student.add(7, _nomMere);
					student.add(8, _prenomMere);
					student.add(9, _profMere);
					student.add(10, _adresse);
					student.add(11, _tel);
					student.add(12, _dateInscription);
					
					myRequestLevel = "SELECT LEVEL FROM SCHOOL_LEVEL,STUDENT_LEVEL WHERE SCHOOL_LEVEL.ID=STUDENT_LEVEL.IDLEVEL AND STUDENT_LEVEL.IDSTUDENT=" + _id.toString();
//Afficher myRequest
System.out.println("getstudents : "+myRequestLevel);
					PreparedStatement pStmtLevel = con.prepareStatement(myRequestLevel);
					ResultSet rsLevel = pStmtLevel.executeQuery();
					_sLevel = "";
					if (rsLevel.next()) _sLevel = rsLevel.getString("LEVEL");

					student.add(13, _sLevel);

					students.add(j, student);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return students;
	}
	
	public static ArrayList<ArrayList<Object>> getStudents_Registrations() {
		ArrayList<ArrayList<Object>> registrations = new ArrayList<ArrayList<Object>>();

		Connection con = getConnection();
		if (con == null) {
			ArrayList<Object> ret = new ArrayList<Object>();
			ret.add(0, "Problème de connection à la base de données");
			registrations.add(0, ret);
		} else {
			try {
				String myRequest = "SELECT student.ID,student.NOM,student.PRENOM,subject.ID,subject.TITLE,school_level.level,teacher.ID,teacher.NOM,teacher.PRENOM,registration.DATE_REG FROM registration, student, subject,teacher,school_level " + 
						"WHERE registration.IDSTUDENT=student.ID AND registration.IDSUBJECT=subject.ID AND registration.IDTEACHER=teacher.ID AND subject.IDLEVEL=school_level.ID "+
						"ORDER BY student.NOM,student.PRENOM ASC";

//Afficher myRequest
System.out.println("getStudents_Registrations : "+myRequest);
				
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				String _sLevel = "";
				while (rs.next()) {
					ArrayList<Object> registration = new ArrayList<Object>();

					Integer _idStudent = rs.getInt("ID");
					String _nomStudent = rs.getString("student.NOM");
					String _prenomStudent = rs.getString("student.PRENOM");
					Integer _idSubject = rs.getInt("subject.ID");
					String _subject = rs.getString("TITLE");
					String _level = rs.getString("level");
					Integer _idTeacher = rs.getInt("teacher.ID");
					String _nomTeacher = rs.getString("teacher.NOM");
					String _prenomTeacher = rs.getString("teacher.PRENOM");
					String _dateInscription = rs.getDate("registration.DATE_REG").toString();

					registration.add(0, _idStudent);
					registration.add(1, _nomStudent);
					registration.add(2, _prenomStudent);
					registration.add(3, _idSubject);
					registration.add(4, _subject);
					registration.add(5, _level);
					registration.add(6, _idTeacher);
					registration.add(7, _nomTeacher);
					registration.add(8, _prenomTeacher);
					registration.add(9, _dateInscription);
					
					registrations.add(j, registration);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return registrations;
	}
	
	public static STUDENT getSTUDENT(Integer id) {
		STUDENT ret = null;
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (id != null && id > 100){
					myRequest = "SELECT * FROM STUDENT WHERE ID=" + id.toString();
//Afficher myRequest
System.out.println("getSTUDENT(id) : "+myRequest);

					PreparedStatement pStmt = con.prepareStatement(myRequest);
					ResultSet rs = pStmt.executeQuery();
					if (rs.next()) {
						ret = new STUDENT();
						ret.ID = String.valueOf(rs.getInt("ID"));
						ret.NOM = rs.getString("NOM");
						ret.PRENOM = rs.getString("PRENOM");
						ret.DATE_NAIS = rs.getDate("DATE_NAIS").toString();
						ret.LIEU_NAIS = rs.getString("LIEU_NAIS");
						ret.prenomPere = rs.getString("PRENOM_PERE");
						ret.profPere = rs.getString("PROFESSION_PERE");
						ret.nomMere = rs.getString("NOM_MERE");
						ret.prenomMere = rs.getString("PRENOM_MERE");
						ret.profMere = rs.getString("PROFESSION_MERE");
						ret.ADRESSE = rs.getString("ADRESSE");
						ret.NUM_TEL = rs.getString("NUM_TEL");
						ret.DATE_INSCRIPTION = rs.getDate("DATE_INSCRIPTION").toString();
						
						myRequest = "SELECT LEVEL FROM SCHOOL_LEVEL,STUDENT_LEVEL WHERE SCHOOL_LEVEL.ID=STUDENT_LEVEL.IDLEVEL AND STUDENT_LEVEL.IDSTUDENT = " + id.toString();
//Afficher myRequest
System.out.println("getSTUDENT(id)1 : "+myRequest);
						PreparedStatement pStmtLevel = con.prepareStatement(myRequest);
						ResultSet rsLevel = pStmtLevel.executeQuery();
						if (rsLevel.next()) ret.schoolLevel = rsLevel.getString("LEVEL");
					}
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return ret;
	}
	
	public static STUDENT getSTUDENTbyName(String lname, String fname) {
		STUDENT ret = null;
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (lname != null && !lname.isEmpty() && fname != null && !fname.isEmpty()){
					myRequest = "SELECT * FROM STUDENT WHERE NOM='" + lname + "' AND PRENOM='" + fname + "'";
//Afficher myRequest
System.out.println("getSTUDENTbyName1 : "+myRequest);

					PreparedStatement pStmt = con.prepareStatement(myRequest);
					ResultSet rs = pStmt.executeQuery();
					if (rs.next()) {
						ret = new STUDENT();
						ret.ID = String.valueOf(rs.getInt("ID"));
						ret.NOM = rs.getString("NOM");
						ret.PRENOM = rs.getString("PRENOM");
						ret.DATE_NAIS = rs.getDate("DATE_NAIS").toString();
						ret.LIEU_NAIS = rs.getString("LIEU_NAIS");
						ret.prenomPere = rs.getString("PRENOM_PERE");
						ret.profPere = rs.getString("PROFESSION_PERE");
						ret.nomMere = rs.getString("NOM_MERE");
						ret.prenomMere = rs.getString("PRENOM_MERE");
						ret.profMere = rs.getString("PROFESSION_MERE");
						ret.ADRESSE = rs.getString("ADRESSE");
						ret.NUM_TEL = rs.getString("NUM_TEL");
						ret.DATE_INSCRIPTION = rs.getDate("DATE_INSCRIPTION").toString();
						
						myRequest = "SELECT LEVEL FROM SCHOOL_LEVEL,STUDENT_LEVEL WHERE SCHOOL_LEVEL.ID=STUDENT_LEVEL.IDLEVEL AND STUDENT_LEVEL.IDSTUDENT=" + ret.ID;
//Afficher myRequest
System.out.println("getSTUDENTbyName2 : "+myRequest);
						PreparedStatement pStmtLevel = con.prepareStatement(myRequest);
						ResultSet rsLevel = pStmtLevel.executeQuery();
						if (rsLevel.next()) ret.schoolLevel = rsLevel.getString("LEVEL");
					}
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return ret;
	}

	public static ArrayList<SUBJECT> getSUBJECTS() {
		ArrayList<SUBJECT> subjects = new ArrayList<SUBJECT>();

		Connection con = getConnection();
		if (con == null) {
			SUBJECT subject = new SUBJECT(0, "Probléme de connection à la base de données", 0);
			subjects.add(0, subject);
		} else {
			try {
				String myRequest = "SELECT * FROM SUBJECT";
//Afficher myRequest
System.out.println("getSUBJECTS : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					SUBJECT subject = new SUBJECT();

					subject.idsubject = rs.getInt("ID");
					subject.title = rs.getString("TITLE");
					subject.idlevel = rs.getInt("IDLEVEL");

					subjects.add(j, subject);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return subjects;
	}
	
	public static SUBJECT getSUBJECT_by_ID(int id) {
		SUBJECT mySubject = null;

		Connection con = getConnection();
		if (con == null) {
			mySubject = new SUBJECT(0, "Probléme de connection à la base de données", 0);
		} else {
			try {
				String myRequest = "SELECT * FROM SUBJECT WHERE ID="+id;
//Afficher myRequest
System.out.println("getSUBJECT_by_ID : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) {
					mySubject = new SUBJECT();
					mySubject.idsubject = rs.getInt("ID");
					mySubject.title = rs.getString("TITLE");
					mySubject.idlevel = rs.getInt("IDLEVEL");
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return mySubject;
	}

	public static SUBJECT getSUBJECT_by_TITLE_Level(String subject, String level) {
		SUBJECT mySubject = null;

		Connection con = getConnection();
		if (con == null) {
			mySubject = new SUBJECT(0, "Probléme de connection à la base de données", 0);
		} else {
			try {
				String myRequest = "";
				myRequest = "SELECT * FROM SUBJECT,SCHOOL_LEVEL " + 
						"WHERE SUBJECT.IDLEVEL=SCHOOL_LEVEL.ID AND SUBJECT.TITLE=\""+subject+"\" AND SCHOOL_LEVEL.level=\""+level+"\"";
//Afficher myRequest
System.out.println("getSUBJECT_by_TITLE_Level : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) {
					mySubject = new SUBJECT();
					mySubject.idsubject = rs.getInt("ID");
					mySubject.title = rs.getString("TITLE");
					mySubject.idlevel = rs.getInt("IDLEVEL");
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return mySubject;
	}
	
	public static ArrayList<SUBJECT> getSUBJECT_by_Teacher(int idTeacher) {
		ArrayList<SUBJECT> subjects = new ArrayList<SUBJECT>();

		Connection con = getConnection();
		if (con == null) {
			SUBJECT subject = new SUBJECT(0, "Probléme de connection à la base de données", 0);
			subjects.add(0, subject);
		} else {
			try {
				String myRequest = "";
				myRequest = "SELECT SUBJECT.ID,SUBJECT.TITLE,SUBJECT.IDLEVEL "+
				"FROM TEACHER_SUBJECT,SUBJECT " + 
				"WHERE TEACHER_SUBJECT.IDSB=SUBJECT.ID AND IDT="+idTeacher;
//Afficher myRequest
System.out.println("getSUBJECT_by_Teacher : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					SUBJECT mySubject = new SUBJECT();
					mySubject.idsubject = rs.getInt("ID");
					mySubject.title = rs.getString("TITLE");
					mySubject.idlevel = rs.getInt("IDLEVEL");
					
					subjects.add(j, mySubject);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return subjects;
	}

	public static ArrayList<SUBJECT> getSUBJECT_by_Student(int idStudent) {
		ArrayList<SUBJECT> subjects = new ArrayList<SUBJECT>();

		Connection con = getConnection();
		if (con == null) {
			SUBJECT subject = new SUBJECT(0, "Probléme de connection à la base de données", 0);
			subjects.add(0, subject);
		} else {
			try {
				String myRequest = "";
				myRequest = "SELECT SUBJECT.ID,SUBJECT.TITLE,SUBJECT.IDLEVEL "+
				"FROM REGISTRATION,SUBJECT " + 
				"WHERE REGISTRATION.IDSUBJECT=SUBJECT.ID AND IDSTUDENT="+idStudent;
//Afficher myRequest
System.out.println("getSUBJECT_by_Student : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					SUBJECT mySubject = new SUBJECT();
					mySubject.idsubject = rs.getInt("ID");
					mySubject.title = rs.getString("TITLE");
					mySubject.idlevel = rs.getInt("IDLEVEL");
					
					subjects.add(j, mySubject);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return subjects;
	}
	
	public static ArrayList<SUBJECT> getSUBJECT_by_StudentTeacher(int idStudent, int idTeacher) {
		ArrayList<SUBJECT> subjects = new ArrayList<SUBJECT>();

		Connection con = getConnection();
		if (con == null) {
			SUBJECT subject = new SUBJECT(0, "Probléme de connection à la base de données", 0);
			subjects.add(0, subject);
		} else {
			try {
				String myRequest = "";
				myRequest = "SELECT SUBJECT.ID,SUBJECT.TITLE,SUBJECT.IDLEVEL "+
				"FROM REGISTRATION,SUBJECT " + 
				"WHERE REGISTRATION.IDSUBJECT=SUBJECT.ID AND IDSTUDENT="+idStudent+" AND IDTEACHER="+idTeacher;
//Afficher myRequest
System.out.println("getSUBJECT_by_Student : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					SUBJECT mySubject = new SUBJECT();
					mySubject.idsubject = rs.getInt("ID");
					mySubject.title = rs.getString("TITLE");
					mySubject.idlevel = rs.getInt("IDLEVEL");
					
					subjects.add(j, mySubject);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return subjects;
	}

	public static ArrayList<LEVEL> getLEVELS() {
		ArrayList<LEVEL> levels = new ArrayList<LEVEL>();

		Connection con = getConnection();
		if (con == null) {
			levels.add(0, new LEVEL(0,"Probléme de connection à la base de données"));
		} else {
			try {
				String myRequest = "SELECT * FROM SCHOOL_LEVEL";
//Afficher myRequest
System.out.println("getLEVELS : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					LEVEL myLevel = new LEVEL(rs.getInt("ID"), rs.getString("LEVEL"));
					levels.add(j, myLevel);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return levels;
	}

	public static LEVEL getLEVEL_by_ID(int id) {
		LEVEL myLevel = null;

		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				myRequest = "SELECT * FROM SCHOOL_LEVEL WHERE ID="+id;
//Afficher myRequest
System.out.println("getLEVEL_by_ID : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) {
					myLevel = new LEVEL(rs.getInt("ID"),rs.getString("LEVEL"));
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return myLevel;
	}
	
	
	/**
	 * deleteLEVEL_by_ID allows deleting a school level from database
	 * @param id School level identifier
	 * @return Number of affected rows (1:OK, 0:Not OK)
	 */
	public static int deleteLEVEL_by_ID(int id) {
		int rows = 0;

		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				myRequest = "SELECT SUBJECT.IDLEVEL FROM SUBJECT, STUDENT_LEVEL WHERE SUBJECT.IDLEVEL=STUDENT_LEVEL.IDLEVEL AND SUBJECT.IDLEVEL="+id;
//Afficher myRequest
System.out.println("deleteLEVEL_by_ID1 : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (!rs.next()) {
					pStmt.close();
	
					myRequest = "DELETE FROM SCHOOL_LEVEL WHERE ID="+id;
//Afficher myRequest
System.out.println("deleteLEVEL_by_ID2 : " + myRequest);
	
					pStmt = con.prepareStatement(myRequest);
					rows = pStmt.executeUpdate();
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return rows;
	}

	/**
	 * deleteSubject_by_ID allows deleting a subject from database
	 * @param id Subject identifier
	 * @return Number of affected rows (1:OK, 0:Not OK)
	 */
	public static int deleteSubject_by_ID(int id) {
		int rows = 0;

		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				myRequest = "SELECT REGISTRATION.IDSUBJECT FROM REGISTRATION, TEACHER_SUBJECT WHERE REGISTRATION.IDSUBJECT=TEACHER_SUBJECT.IDSB AND REGISTRATION.IDSUBJECT="+id;
//Afficher myRequest
System.out.println("deleteSubject_by_ID1 : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (!rs.next()) {
					pStmt.close();
					myRequest = "DELETE FROM SUBJECT WHERE ID="+id;
//Afficher myRequest
System.out.println("deleteSubject_by_ID2 : " + myRequest);

					pStmt = con.prepareStatement(myRequest);
					rows = pStmt.executeUpdate();
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return rows;
	}

	/**
	 * deleteStudent_by_ID allows deleting a student from database
	 * @param id Student identifier
	 * @return Number of affected rows (1:OK, 0:Not OK)
	 */
	public static int deleteStudent_by_ID(int id) {
		int ret = 0;
		int[] rows = null;

		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				myRequest = "SELECT DISTINCT IDSTUDENT FROM REGISTRATION WHERE IDSTUDENT="+id;
//Afficher myRequest
System.out.println("deleteStudent_by_ID1 : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (!rs.next()) {
					pStmt.close();
					Statement s = (Statement) con.createStatement();
					String myRequest1 = "DELETE FROM STUDENT_LEVEL WHERE IDSTUDENT="+id;
					String myRequest2 = "DELETE FROM PAIEMENT WHERE ID="+id;
					String myRequest3 = "DELETE FROM ASSURANCE WHERE ID="+id;
					String myRequest4 = "DELETE FROM STUDENT WHERE ID="+id;
					s.addBatch(myRequest1);
					s.addBatch(myRequest2);
					s.addBatch(myRequest3);
					s.addBatch(myRequest4);
//Afficher myRequest
System.out.println("deleteStudent_by_ID2 : " + s.toString());

					//pStmt = con.prepareStatement(myRequest);
					rows = s.executeBatch();
					s.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (rows != null) for (int i=0; i<4; i++) ret = ret + rows[i];
		return ret;
	}
	
	/**
	 * deleteTeacher_by_ID allows deleting a teacher from database
	 * @param id Teacher identifier
	 * @return Number of affected rows (1:OK, 0:Not OK)
	 */
	public static int deleteTeacher_by_ID(int id) {
		int ret = 0;
		int[] rows = null;

		Connection con = getConnection();
		if (con != null) {
			try {

					Statement s = (Statement) con.createStatement();
					String myRequest1 = "DELETE FROM REGISTRATION WHERE IDTEACHER="+id;
					String myRequest2 = "DELETE FROM TEACHER_SUBJECT WHERE IDT="+id;
					String myRequest3 = "DELETE FROM TEACHER WHERE ID="+id;
//Afficher myRequest
System.out.println("deleteStudent_by_ID1 : " + myRequest1 + ";" + myRequest2 + ";" + myRequest3);
					s.addBatch(myRequest1);
					s.addBatch(myRequest2);
					s.addBatch(myRequest3);

					rows = s.executeBatch();
					s.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (rows != null) for (int i=0; i<3; i++) ret = ret + rows[i];
		return ret;
	}

	/**
	 * deleteRegistration allows deleting a student registration from database
	 * @param idSt Student identifier
	 * @param idT Teacher identifier
	 * @param idSub Subject identifier
	 * @return Number of affected rows (1:OK, 0:Not OK)
	 */
	public static int deleteRegistration(int idSt, int idT, int idSub) {
		int rows = 0;

		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				myRequest = "DELETE FROM REGISTRATION WHERE IDSTUDENT="+idSt+" AND IDTEACHER="+idT+" AND IDSUBJECT="+idSub;
//Afficher myRequest
System.out.println("deleteRegistration : " + myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				rows = pStmt.executeUpdate();
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return rows;
	}
	
	/**
	 * deleteTeacherSubject allows deleting a link between teacher->subject from database
	 * @param idT Teacher identifier
	 * @param idSub Subject identifier
	 * @return Number of affected rows (1:OK, 0:Not OK)
	 */
	public static int deleteTeacherSubject(int idT, int idSub) {
		int rows = 0;

		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				myRequest = "DELETE FROM TEACHER_SUBJECT WHERE IDT="+idT+" AND IDSB="+idSub;
//Afficher myRequest
System.out.println("deleteTeacherSubject : " + myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				rows = pStmt.executeUpdate();
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return rows;
	}

	/**
	 * deleteAssurance allows deleting an insurance from database
	 * @param id Personne's identifier
	 * @return Number of affected rows (1:OK, 0:Not OK)
	 */
	public static int deleteAssurance(int id) {
		int rows = 0;

		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				myRequest = "UPDATE ASSURANCE SET debut=\"2018-01-01\",fin=\"2018-01-01\" WHERE ID="+id;
//Afficher myRequest
System.out.println("deleteAssurance : " + myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				rows = pStmt.executeUpdate();
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return rows;
	}

	public static int deletePaiement(int idStudent, int idTeacher, int idSubject, String dateDebut) {
		int rows = 0;

		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				myRequest = "DELETE FROM PAIEMENT WHERE ID="+idStudent+" AND IDTEACHER="+idTeacher+" AND IDSUBJECT="+idSubject+" AND DEBUT='"+dateDebut+"'";
//Afficher myRequest
System.out.println("deletePaiement : " + myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				rows = pStmt.executeUpdate();
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return rows;
	}

	public static int deletePaiementTeacher(int idTeacher, int idSubject, String mois) {
		int rows = 0;

		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				myRequest = "DELETE FROM PAIEMENT_TEACHER WHERE IDTEACHER="+idTeacher+" AND IDSUBJECT="+idSubject+" AND MOIS='"+mois+"'";
//Afficher myRequest
System.out.println("deletePaiementTeacher : " + myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				rows = pStmt.executeUpdate();
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return rows;
	}

	public static LEVEL getLEVEL_by_levelTitle(String levelTitle) {
		LEVEL myLevel = null;

		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				myRequest = "SELECT * FROM SCHOOL_LEVEL WHERE level='"+levelTitle+"'";
//Afficher myRequest
System.out.println("getLEVEL_by_levelTitle : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) {
					myLevel = new LEVEL(rs.getInt("ID"), rs.getString("level"));
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return myLevel;
	}
	
	public static ArrayList<ArrayList<Object>> get_PAIEMENT(Integer id) {
		ArrayList<ArrayList<Object>> paiements = new ArrayList<ArrayList<Object>>();

		Connection con = getConnection();
		if (con == null) {
			ArrayList<Object> ret = new ArrayList<Object>();
			ret.add(0, "Probléme de connection à la base de données");
			paiements.add(0,ret);
		} else {
			try {
				String myRequest = "";
				if (id != null	&& id == 0) {
					myRequest = "SELECT * FROM PAIEMENT";
				} else {
					myRequest = "SELECT * FROM PAIEMENT WHERE ";
					if (id==null || id.toString().isEmpty())
						myRequest = myRequest + "ID=0";
					else
						myRequest = myRequest + "ID=" + id.toString();
				}
//Afficher myRequest
System.out.println("get_PAIEMENT : "+myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					ArrayList<Object> paiement = new ArrayList<Object>();

					Integer _id = rs.getInt("ID");
					Integer _idTeacher = rs.getInt("IDTEACHER");
					Integer _idSubject = rs.getInt("IDSUBJECT");
					String _debut = rs.getDate("DEBUT").toString();
					String _fin = rs.getDate("FIN").toString();
					Float _montant = rs.getFloat("MONTANT");

					paiement.add(0, _id);
					paiement.add(1, _idTeacher);
					paiement.add(2, _idSubject);
					paiement.add(3, _debut);
					paiement.add(4, _fin);
					paiement.add(5, _montant);

					paiements.add(j, paiement);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return paiements;
	}
	
	public static ArrayList<ArrayList<Object>> get_PAIEMENT_TEACHER(Integer id) {
		ArrayList<ArrayList<Object>> paiements = new ArrayList<ArrayList<Object>>();

		Connection con = getConnection();
		if (con == null) {
			ArrayList<Object> ret = new ArrayList<Object>();
			ret.add(0, "Probléme de connection à la base de données");
			paiements.add(0,ret);
		} else {
			try {
				String myRequest = "";
				if (id != null	&& id == 0) {
					myRequest = "SELECT * FROM PAIEMENT_TEACHER";
				} else {
					myRequest = "SELECT * FROM PAIEMENT_TEACHER WHERE ";
					if (id==null || id.toString().isEmpty())
						myRequest = myRequest + "IDTEACHER=0";
					else
						myRequest = myRequest + "IDTEACHER=" + id.toString();
				}
//Afficher myRequest
System.out.println("get_PAIEMENT_TEACHER : "+myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					ArrayList<Object> paiement = new ArrayList<Object>();

					Integer _id = rs.getInt("ID");
					Integer _idTeacher = rs.getInt("IDTEACHER");
					Integer _idSubject = rs.getInt("IDSUBJECT");
					String _mois = rs.getDate("MOIS").toString();
					Float _montant = rs.getFloat("MONTANT");

					paiement.add(0, _id);
					paiement.add(1, _idTeacher);
					paiement.add(2, _idSubject);
					paiement.add(3, _mois);
					paiement.add(4, _montant);

					paiements.add(j, paiement);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return paiements;
	}
	
	/**
	 * Get the amount of payment of person number 'id' for a registration with fixed beginning date
	 * @param id Person's id
	 * @param idTeacher Teacher's id
	 * @param idSubject Subject's id
	 * @param date Beginning date of the payment "yyyy-mm-dd"
	 * @return Float containing the amount of paiement if exists.
	 */
	public static Float get_PAIEMENT(Integer id, Integer idTeacher, Integer idSubject, String date) {
		Float ret = null;
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (id != null	&& id > 0 && idTeacher != null	&& idTeacher > 0 && idSubject != null	&& idSubject > 0 && date != null && !date.isEmpty())
					myRequest = "SELECT * FROM PAIEMENT WHERE ID=" + id.toString() + "AND IDTEACHER=" + idTeacher.toString() +
					"AND IDSUBJECT=" + idSubject.toString() + " AND DEBUT='" + date + "'";
//Afficher myRequest
System.out.println("get_PAIEMENT : "+myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next())
					ret = Float.parseFloat(rs.getString("MONTANT"));
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * Get the amount of teacher's payment.
	 * @param idTeacher	Teacher's id
	 * @param idSubject	Subject's id
	 * @param mois		Month's payment to be checked "yyyy-mm-dd"
	 * @return Float containing the amount of payment if exists.
	 */
	public static Float get_PAIEMENT_TEACHER(Integer idTeacher, Integer idSubject, String mois) {
		Float ret = null;
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (idTeacher != null	&& idTeacher > 0 && idSubject != null	&& idSubject > 0 && mois != null)
					myRequest = "SELECT * FROM PAIEMENT_TEACHER WHERE IDTEACHER=" + idTeacher.toString() +
					"AND IDSUBJECT=" + idSubject.toString() + " AND MOIS='" + mois + "'";
//Afficher myRequest
System.out.println("get_PAIEMENT_TEACHER : "+myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next())
					ret = Float.parseFloat(rs.getString("MONTANT"));
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * Allows to check a student's payment
	 * @param id		Student's id
	 * @param idTeacher	Teacher's id
	 * @param idSubject	Subject's id
	 * @param date		Should be between DEBUT and FIN
	 * @return	True if a payment exists for a person who payed a registration.
	 */
	public static boolean check_PAIEMENT(Integer id, Integer idTeacher, Integer idSubject, String date) {
		boolean ret = false;
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (id != null && idTeacher != null && idSubject != null && date != null &&
					id > 0 && idTeacher > 0 && idSubject > 0 && !date.isEmpty()) {

					myRequest = "SELECT * FROM PAIEMENT WHERE ID=" + id.toString() + " AND IDTEACHER=" + idTeacher.toString() +
							" AND IDSUBJECT=" + idSubject.toString() + " AND DEBUT<'" + date + "' AND FIN>'" + date + "' AND MONTANT>=0.00";
//Afficher myRequest
System.out.println("check_PAIEMENT : "+myRequest);
					PreparedStatement pStmt = con.prepareStatement(myRequest);
					ResultSet rs = pStmt.executeQuery();
					if (rs.next())
						ret = true;
					pStmt.close();						
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * Allows to check a teacher's payment
	 * @param		idTeacher	Teacher's id
	 * @param		idSubject	Subject's id
	 * @param mois	Month's payment to be checked "yyyy-mm-01"
	 * @return	True if a payment exists for a person who payed a registration.
	 */
	public static boolean check_PAIEMENT_TEACHER(Integer idTeacher, Integer idSubject, String mois) {
		boolean ret = false;
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (idTeacher != null && idSubject != null && mois != null)
				{
					if (idTeacher > 0 && idSubject > 0 && !mois.isEmpty()) {
						myRequest = "SELECT * FROM PAIEMENT_TEACHER WHERE IDTEACHER=" + idTeacher.toString() +
								" AND IDSUBJECT=" + idSubject.toString() + " AND MOIS='" + mois + "' AND MONTANT>=0.00";
					}
				}
//Afficher myRequest
System.out.println("check_PAIEMENT_TEACHER : "+myRequest);
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				while (rs.next())
					ret = true;
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * @param id		Student's id
	 * @param idTeacher	Teacher's id
	 * @param idSubject	Subject's id
	 * @param dateDebut	yyyy-mm-dd
	 * @param dateFin	yyyy-mm-dd
	 * @param montant	Integer
	 * @return			Number of affected rows
	 */
	public static int set_PAIEMENT(Integer id, Integer idTeacher, Integer idSubject, String dateDebut, String dateFin, Float montant) {
		int rows = 0;
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (id != null && dateDebut != null && dateFin != null)
				{
					if (id > 0 && !dateDebut.isEmpty() && !dateFin.isEmpty()) {
						if (get_PAIEMENT(id, idTeacher, idSubject, dateDebut) != null)
							myRequest = "UPDATE PAIEMENT SET MONTANT=" + montant.toString() + ", FIN='" + dateFin + "' WHERE ID=" + id.toString() +
							"AND IDTEACHER=" + idTeacher.toString() + "AND IDSUBJECT=" + idSubject.toString() + " AND DEBUT='" + dateDebut + "'";
						else
							myRequest = "INSERT INTO PAIEMENT (ID,IDTEACHER,IDSUBJECT,DEBUT,FIN,MONTANT) VALUES ("+
									id.toString()+","+idTeacher.toString()+","+idSubject.toString()+",'"+dateDebut+"','"+dateFin+"',"+montant.toString()+")";
					}
//Afficher myRequest
System.out.println("set_PAIEMENT : "+myRequest);
					PreparedStatement pStmt = con.prepareStatement(myRequest);
					rows = pStmt.executeUpdate();
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return rows;
	}
	
	/**
	 * @param idTeacher	Teacher's id
	 * @param idSubject	Subject's id
	 * @param mois	yyyy-mm-dd
	 * @param montant	Integer
	 * @return			Number of affected rows
	 */
	public static int set_PAIEMENT_TEACHER(Integer idTeacher, Integer idSubject, String mois, Float montant) {
		int rows = 0;
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (idTeacher != null && idSubject != null && mois != null)
				{
					if (idTeacher > 0 && idSubject > 0 && !mois.isEmpty()) {
						String _mois = mois.substring(0, 8) + "01";
						if ( check_PAIEMENT_TEACHER(idTeacher, idSubject, _mois) )
							myRequest = "UPDATE PAIEMENT_TEACHER SET MONTANT=" + montant.toString() + ", MOIS='" + _mois + "' WHERE IDTEACHER=" + idTeacher.toString() +
							"AND IDSUBJECT=" + idSubject.toString() + "'";
						else {
							Integer freeId = getFreePaiementTecherID();
							myRequest = "INSERT INTO PAIEMENT_TEACHER (ID,IDTEACHER,IDSUBJECT,MOIS,MONTANT) VALUES ("+
									freeId.toString()+","+idTeacher.toString()+","+idSubject.toString()+",'"+_mois+"',"+montant.toString()+")";
						}
					}
//Afficher myRequest
System.out.println("set_PAIEMENT_TEACHER : "+myRequest);
					PreparedStatement pStmt = con.prepareStatement(myRequest);
					rows = pStmt.executeUpdate();
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return rows;
	}
	
	public static ArrayList<Object> get_ASSURANCE(Integer id) {
		ArrayList<Object> assurance = new ArrayList<Object>();

		Connection con = getConnection();
		if (con == null) {
			assurance.add(0, "Probléme de connection à la base de données");
		} else {
			try {
				String myRequest = "";
				if ((id != null	&& id == 0) ||
					(id != null	&& id == 0)){
					myRequest = "SELECT * FROM ASSURANCE";
				} else {
					myRequest = "SELECT * FROM ASSURANCE WHERE ";
					if (id==null || id.toString().isEmpty() || id == 0)
						myRequest = myRequest + "ID=0";
					else
						myRequest = myRequest + "ID=" + id.toString();
				}
//Afficher myRequest
System.out.println("get_ASSURANCE : "+myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) {
					Integer _id = rs.getInt("ID");
					String _debut = rs.getString("DEBUT");
					String _fin = rs.getString("FIN");

					assurance.add(0, _debut);
					assurance.add(1, _fin);
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return assurance;
	}
	
	public static boolean check_ASSURANCE(Integer id, LocalDate date) {
		boolean ret = false;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String d = date.format(formatter);
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (id != null && date != null && id > 0)
				{
					myRequest = "SELECT * FROM ASSURANCE WHERE ID="+id.toString()+" AND FIN>='"+d+"'";
				}
//Afficher myRequest
System.out.println("check_ASSURANCE : "+myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				while (rs.next())
					ret = true;
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return ret;
	}
	
	//debut et fin sour forme "yyyy-mm-dd"
	public static void set_ASSURANCE(Integer id, String debut, String fin) {
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (id != null && debut != null && fin != null)
				{
					if (id > 0) {
						if (get_ASSURANCE(id).size() > 0)
							myRequest = "UPDATE ASSURANCE SET DEBUT='"+debut+"',FIN='"+fin+"' WHERE ID="+id.toString();
						else
						{
							if (!debut.isEmpty() && !fin.isEmpty())
								myRequest = "INSERT INTO ASSURANCE (ID,DEBUT,FIN) VALUES ("+id.toString()+",'"+debut+"','"+fin+"')";
							else
								myRequest = "INSERT INTO ASSURANCE (ID) VALUES ("+id.toString()+")";
						}
					}
				}
//Afficher myRequest
System.out.println("set_ASSURANCE : "+myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				pStmt.executeUpdate();
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void init_ASSURANCE(Integer id) {
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (id != null && id > 0) {
					if (get_ASSURANCE(id).size() > 0)
					{
						myRequest = "UPDATE ASSURANCE SET DEBUT='2018/01/01',FIN='2018/01/01' WHERE ID="+id.toString();
					}
					else
					{
						myRequest = "INSERT INTO ASSURANCE (ID,DEBUT,FIN) VALUES ("+id.toString()+",'2018/01/01','2018/01/01')";
					}
//Afficher myRequest
System.out.println("set_ASSURANCE : "+myRequest);
					PreparedStatement pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static int getYearFromConfig(String configFileName) {
		try {

			File fXmlFile = new File(configFileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			String yearStr = doc.getElementsByTagName("SCHOOL_YEAR").item(0).getTextContent().replaceAll(" ", "").replaceAll("\n", "");
			Integer yearInt = Integer.parseInt(yearStr.substring(0, 4));
			return yearInt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static ArrayList<ArrayList<Object>> getTEACHER(Integer id, String nom,String prenom) {
		ArrayList<ArrayList<Object>> teachers = new ArrayList<ArrayList<Object>>();

		Connection con = getConnection();
		if (con == null) {
			ArrayList<Object> ret = new ArrayList<Object>();
			ret.add(0, "Probléme de connection à la base de données");
			teachers.add(0,ret);
		} else {
			try {
				String myRequest = "";
				if ((id != null && nom != null && prenom != null
					&& id == 0 && nom.isEmpty() && prenom.isEmpty()) ||
					(id != null && nom != null && prenom != null
					&& id == 0 && nom.equals("Tapez le nom de l_enseignant") && prenom.equals("Tapez le prénom de l_enseignant"))){
					myRequest = "SELECT * FROM TEACHER";
				} else {
					myRequest = "SELECT * FROM TEACHER WHERE ";
					if (id==null || id.toString().isEmpty() || id == 0)
						myRequest = myRequest + "ID=0";
					else
						myRequest = myRequest + "ID=" + id.toString();
					if (nom!=null && !nom.isEmpty() && !nom.equals("Tapez le nom de l_enseignant")) 
						myRequest = myRequest + " OR NOM='" + nom + "'";
					if (prenom!=null && !prenom.isEmpty() && !prenom.equals("Tapez le prénom de l_enseignant")) 
						myRequest = myRequest + " OR PRENOM='" + prenom + "'";
				}
				myRequest = myRequest + " ORDER BY ID ASC";
//Afficher myRequest
System.out.println("getTEACHER : "+myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					ArrayList<Object> teacher = new ArrayList<Object>();

					Integer _id = rs.getInt("ID");
					String _nom = rs.getString("NOM");
					String _prenom = rs.getString("PRENOM");
					String _dateNais = rs.getDate("DATE_NAIS").toString();
					String _lieuNais = rs.getString("LIEU_NAIS");
					String _adresse = rs.getString("ADRESSE");
					String _tel = rs.getString("NUM_TEL");
					String _dateInscription = rs.getDate("DATE_INSCRIPTION").toString();
					teacher.add(0, _id);
					teacher.add(1, _nom);
					teacher.add(2, _prenom);
					teacher.add(3, _dateNais);
					teacher.add(4, _lieuNais);
					teacher.add(5, _adresse);
					teacher.add(6, _tel);
					teacher.add(7, _dateInscription);

					teachers.add(j, teacher);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return teachers;
	}

	public static Integer getIDSujectbyTitleLevel(String[] subject_title_level) {
		Integer idSubject = 0;
		String myRequest = "";
		Connection con = getConnection();
		if (con == null) {
			return null;
		} else {
			if (subject_title_level == null || (subject_title_level != null && (subject_title_level[0].isEmpty() || subject_title_level[1].isEmpty()))) {
				return null;
			} else {
				try {
					myRequest = "SELECT subject.ID FROM subject,school_level WHERE subject.IDLEVEL=school_level.ID and subject.TITLE=\""+subject_title_level[0]+
							"\" and school_level.level=\""+subject_title_level[1]+"\"";
//Afficher myRequest
System.out.println("getIDSujectbyTitleLevel : "+myRequest);
	
					PreparedStatement pStmt = con.prepareStatement(myRequest);
					ResultSet rs = pStmt.executeQuery();
					if (rs.next()) {
						idSubject = rs.getInt("ID");
					}
					pStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (con != null) {
						try {
							con.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				return idSubject;
			}
		}
	}
	
	/**
	 * getTEACHERSbySubject allow to get list of teachers using subject title and school level.
	 * @param subject_title_level : subject_title_level[0]: Subject title, subject_title_level[1]: School level
	 * @return Array list containing teacher concerned by subject of 'subject_title_level'
	 */
	public static ArrayList<TEACHER> getTEACHERSbySubject(String[] subject_title_level) {
		ArrayList<TEACHER> teachers = new ArrayList<TEACHER>();

		Connection con = getConnection();
		if (con == null) {
			teachers.add(0,null);
		} else {
			try {
				Integer idSubject = 0;
				String myRequest = "";
				if (subject_title_level == null || (subject_title_level != null && (subject_title_level[0].isEmpty() || subject_title_level[1].isEmpty()))) {
					return null;
				} else {
					myRequest = "SELECT subject.ID FROM subject,school_level WHERE subject.IDLEVEL=school_level.ID and subject.TITLE=\""+subject_title_level[0]+
					"\" and school_level.level=\""+subject_title_level[1]+"\"";
//Afficher myRequest
System.out.println("getTEACHERSbySubject1 : "+myRequest);

					PreparedStatement pStmt = con.prepareStatement(myRequest);
					ResultSet rs = pStmt.executeQuery();
					if (rs.next()) {
						idSubject = rs.getInt("ID");
						
						myRequest = "SELECT * FROM TEACHER_SUBJECT,TEACHER WHERE TEACHER_SUBJECT.IDT=TEACHER.ID AND IDSB=" + idSubject.toString();
//Afficher myRequest
System.out.println("getTEACHERSbySubject2 : "+myRequest);

						pStmt = con.prepareStatement(myRequest);
						rs = pStmt.executeQuery();
						int j = 0;
						while (rs.next()) {
							TEACHER myteacher = new TEACHER();

							myteacher.ID = new Integer(rs.getInt("ID")).toString();
							myteacher.NOM = rs.getString("NOM");
							myteacher.PRENOM = rs.getString("PRENOM");
							myteacher.DATE_NAIS = rs.getDate("DATE_NAIS").toString();
							myteacher.LIEU_NAIS = rs.getString("LIEU_NAIS");
							myteacher.ADRESSE = rs.getString("ADRESSE");
							myteacher.NUM_TEL = rs.getString("NUM_TEL");
							myteacher.DATE_INSCRIPTION = rs.getDate("DATE_INSCRIPTION").toString();

							teachers.add(j, myteacher);
							j++;
						}
					}
					pStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return teachers;
	}

	public static TEACHER getTEACHER(Integer id) {
		TEACHER ret = null;
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (id != null && id > 0){
					myRequest = "SELECT * FROM TEACHER WHERE ID=" + id.toString();
				}
//Afficher myRequest
System.out.println("getTEACHER(id) : "+myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				while (rs.next()) {
					ret = new TEACHER();
					ret.ID = String.valueOf(rs.getInt("ID"));
					ret.NOM = rs.getString("NOM");
					ret.PRENOM = rs.getString("PRENOM");
					ret.DATE_NAIS = rs.getDate("DATE_NAIS").toString();
					ret.LIEU_NAIS = rs.getString("LIEU_NAIS");
					ret.ADRESSE = rs.getString("ADRESSE");
					ret.NUM_TEL = rs.getString("NUM_TEL");
					ret.DATE_INSCRIPTION = rs.getDate("DATE_INSCRIPTION").toString();
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return ret;
	}
	
	public static TEACHER getTEACHERbyName(String lname, String fname) {
		TEACHER ret = null;
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (lname != null && fname != null && !lname.isEmpty() && !fname.isEmpty()){
					myRequest = "SELECT * FROM TEACHER WHERE NOM=\"" + lname + "\" AND PRENOM=\"" + fname + "\"";
				}
//Afficher myRequest
System.out.println("getTEACHERbyName(id) : "+myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) {
					ret = new TEACHER();
					ret.ID = String.valueOf(rs.getInt("ID"));
					ret.NOM = rs.getString("NOM");
					ret.PRENOM = rs.getString("PRENOM");
					ret.DATE_NAIS = rs.getDate("DATE_NAIS").toString();
					ret.LIEU_NAIS = rs.getString("LIEU_NAIS");
					ret.ADRESSE = rs.getString("ADRESSE");
					ret.NUM_TEL = rs.getString("NUM_TEL");
					ret.DATE_INSCRIPTION = rs.getDate("DATE_INSCRIPTION").toString();
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return ret;
	}
	
	public static ArrayList<TEACHER> getTEACHER_by_Student(int idStudent) {
		ArrayList<TEACHER> teachers = new ArrayList<TEACHER>();

		Connection con = getConnection();
		if (con == null) {
			teachers.add(0, null);
		} else {
			try {
				String myRequest = "";
				myRequest = "SELECT TEACHER.ID,TEACHER.NOM,TEACHER.PRENOM "+
				"FROM REGISTRATION,TEACHER " + 
				"WHERE REGISTRATION.IDTEACHER=TEACHER.ID AND IDSTUDENT="+idStudent;
//Afficher myRequest
System.out.println("getTEACHER_by_Student : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					TEACHER myTeacher = new TEACHER();
					myTeacher.ID = rs.getString("ID");
					myTeacher.NOM = rs.getString("NOM");
					myTeacher.PRENOM = rs.getString("PRENOM");
					
					teachers.add(j, myTeacher);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return teachers;
	}
	
	public static ArrayList<ArrayList<Object>> getTEACHER_non_assures() {
		ArrayList<ArrayList<Object>> trachers = new ArrayList<ArrayList<Object>>();

		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Connection con = getConnection();
		if (con == null) {
			ArrayList<Object> ret = new ArrayList<Object>();
			ret.add(0, "Probléme de connection à la base de données");
			trachers.add(0, ret);
		} else {
			try {
				String myRequest = "";
				String d = date.format(formatter);
				myRequest = "SELECT * FROM ASSURANCE WHERE ID < 101 AND FIN<'"+d+"'";
//Afficher myRequest
System.out.println("getTEACHER_non_assures : "+myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					ArrayList<Object> teacher = new ArrayList<Object>();

					Integer _id = rs.getInt("ID");
					String _debut = rs.getString("DEBUT");
					String _fin = rs.getString("FIN");
					teacher.add(0, _id);
					teacher.add(1, _debut);
					teacher.add(2, _fin);

					trachers.add(j, teacher);
					j++;
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return trachers;
	}
	
	public static ArrayList<ArrayList<Object>> getSTUDENT_non_assures() {
		ArrayList<ArrayList<Object>> students = new ArrayList<ArrayList<Object>>();

		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Connection con = getConnection();
		if (con == null) {
			ArrayList<Object> ret = new ArrayList<Object>();
			ret.add(0, "Probléme de connection à la base de données");
			students.add(0, ret);
		} else {
			try {
				String myRequest = "";
				String d = date.format(formatter);
				
				//Etudiants avec assurance périmée
				myRequest = "SELECT STUDENT.ID,DEBUT,FIN FROM STUDENT,ASSURANCE WHERE STUDENT.ID=ASSURANCE.ID AND STUDENT.ID NOT IN (SELECT ID FROM ASSURANCE WHERE ID>100 AND FIN>='"+d+"')";

//Afficher myRequest
System.out.println("getSTUDENT_non_assures1 : "+myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					ArrayList<Object> student = new ArrayList<Object>();

					Integer _id = rs.getInt("ID");
					String _debut = rs.getString("DEBUT");
					String _fin = rs.getString("FIN");
					student.add(0, _id);
					student.add(1, _debut);
					student.add(2, _fin);

					students.add(j, student);
					j++;
				}
				pStmt.close();
				
				//Etudiants sans assurance
				myRequest = "SELECT ID FROM STUDENT WHERE ID NOT IN (SELECT ID FROM ASSURANCE WHERE ID>100)";
//Afficher myRequest
System.out.println("getSTUDENT_non_assures2 : "+myRequest);
				pStmt = con.prepareStatement(myRequest);
				rs = pStmt.executeQuery();
				while (rs.next()) {
					ArrayList<Object> student = new ArrayList<Object>();
				
					Integer _id = rs.getInt("ID");
					student.add(0, _id);
					student.add(1, "");
					student.add(2, "");
				
					students.add(j, student);
					j++;
				}
				pStmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return students;
	}
	
	public static ArrayList<Object> getSTUDENTS_non_payes() {
		ArrayList<Object> students = new ArrayList<Object>();

		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		Connection con = getConnection();
		if (con == null) {
			students.add("Probléme de connection à la base de données");
		} else {
			try {
				String myRequest = "";
				String d = date.format(formatter);
				myRequest = "SELECT DISTINCT registration.IDSTUDENT FROM registration " + 
							"LEFT JOIN paiement ON registration.IDSTUDENT=paiement.ID AND registration.IDTEACHER=paiement.IDTEACHER AND registration.IDSUBJECT=paiement.IDSUBJECT "+
							"AND DEBUT<='"+d+"' AND FIN>='"+d+"' AND MONTANT>=0 " + 
							"WHERE paiement.ID IS NULL";
//Afficher myRequest
System.out.println("getSTUDENTS_non_payes : "+myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				while (rs.next()) {
					Integer _id = rs.getInt("IDSTUDENT");
					students.add(_id);
				}
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return students;
	}
	
}
