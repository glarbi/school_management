package org;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
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
			//Création de la table "STUDENT"
			myRequest = "CREATE TABLE IF NOT EXISTS SCHOOL_LEVEL ("+
			"ID INTEGER PRIMARY KEY,"+ // Identifiant l'élève (101-infini)
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
			//Création de la table "TEACHER"
			myRequest = "CREATE TABLE IF NOT EXISTS TEACHER ("+
			"ID INTEGER PRIMARY KEY,"+ // Identifiant enseignant (1-100)
			"NOM VARCHAR(30) NOT NULL,"+
			"PRENOM VARCHAR(30) NOT NULL,"+
			"DATE_NAIS DATE,"+
			"LIEU_NAIS VARCHAR(30) NOT NULL,"+
			"ADRESSE VARCHAR(1024) NOT NULL,"+
			"NUM_TEL VARCHAR(30) NOT NULL,"+
			"DATE_INSCRIPTION DATE,\"+\r\n" + 
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
			//Création de la table "PAIEMENT"
			myRequest = "CREATE TABLE IF NOT EXISTS PAIEMENT ("+
			"ID INTEGER,"+ // 1<ID<=100 (TEACHER) /   ID>100 (STUDENT)
			"MOIS DATE,"+
			"MONTANT DECIMAL(8,2),"+ //8 chiffres dont 2 aprés la virgule
			"PRIMARY KEY (ID,MOIS)) ENGINE = InnoDB";
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
			"fin DATE) ENGINE = InnoDB";
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
	
	public static void setSTUDENT(Integer id, String nom, String prenom, String dateNais, String lieuNais,
			String sLevel, String prenomPere, String profPere, String nomMere, String prenomMere, String profMere,
			String adresse, String tel, String dateInscription)
	{
		if (id.intValue() > 0) {
			if (nom == null) nom = "";
			if (prenom == null) prenom = "";
			if (dateNais == null) dateNais = "01/01/2000";
			if (lieuNais == null) lieuNais = "";
			if (sLevel == null) sLevel = "";
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

					//Initialisation de l'assurance
					init_ASSURANCE(id);
					//Initialisation du paiement
					init_PAIEMENT(id);
				}

				myRequest = "SELECT * FROM SCHOOL_LEVEL WHERE ID=" + id.toString();
//Afficher myRequest
System.out.println("setSTUDENT4 : "+myRequest);
				pStmt = con.prepareStatement(myRequest);
				rs = pStmt.executeQuery();
				if ( rs.next() ) {
					pStmt.close();
					myRequest = "UPDATE SCHOOL_LEVEL SET level='" + sLevel + "' WHERE ID=" + id.toString();
//Afficher myRequest
System.out.println("setSTUDENT5 : "+myRequest);
					pStmt = con.prepareStatement(myRequest);
					pStmt.executeUpdate();
					pStmt.close();
				}
				else {
					pStmt.close();
					myRequest = "INSERT INTO SCHOOL_LEVEL (ID,LEVEL) VALUES (" + id + ",'" + sLevel + "')";
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
					//Initialisation de l'assurance
					init_ASSURANCE(id);
					//Initialisation du paiement
					init_PAIEMENT(id);
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
	
	public static Integer getFreeStudentID(){
		int max = 100;
		Connection con = getConnection();
		if (con == null) {
			return -1;
		} else {
			try {
				String myRequest = "SELECT MAX(ID) AS MYID FROM STUDENT";//"SELECT * FROM STUDENT";
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) {
					max = rs.getInt("MYID");
					if (max == 0) max = 100; // cas où MYID == NULL getInt retourne 0 !!!
				}
				/*while (rs.next()) {
					Integer _id = rs.getInt("ID");
					if (_id > max) max = _id;
				}*/
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
	
	public static Integer getFreeTeacherID(){
		int max = 0;
		Connection con = getConnection();
		if (con == null) {
			return -1;
		} else {
			try {
				String myRequest = "SELECT MAX(ID) AS MYID FROM TEACHER";//"SELECT * FROM TEACHER";
				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				if (rs.next()) max = rs.getInt("MYID");
				/*while (rs.next()) {
					Integer _id = rs.getInt("ID");
					if (_id > max) max = _id;
				}*/
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
					
					myRequestLevel = "SELECT * FROM SCHOOL_LEVEL WHERE ID=" + _id.toString();
//Afficher myRequest
System.out.println("getstudents : "+myRequestLevel);
					PreparedStatement pStmtLevel = con.prepareStatement(myRequestLevel);
					ResultSet rsLevel = pStmtLevel.executeQuery();
					_sLevel = "";
					if (rsLevel.next()) _sLevel = rsLevel.getString("level");

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
						
						myRequest = "SELECT * FROM SCHOOL_LEVEL WHERE ID=" + id.toString();
//Afficher myRequest
System.out.println("getSTUDENT(id)1 : "+myRequest);
						PreparedStatement pStmtLevel = con.prepareStatement(myRequest);
						ResultSet rsLevel = pStmtLevel.executeQuery();
						if (rsLevel.next()) ret.schoolLevel = rsLevel.getString("level");
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
			ArrayList<Object> ret = new ArrayList<Object>();
			SUBJECT subject = new SUBJECT(0, "Probléme de connection à la base de données");
			subjects.add(0, subject);
		} else {
			try {
				String myRequest = "";
				//String d = String.valueOf(date.getYear()+1900)+"-"+String.valueOf(date.getMonth()+1)+"-"+String.valueOf(date.getDate());
				myRequest = "SELECT * FROM SUBJECT";
//Afficher myRequest
System.out.println("getSUBJECTS : " + myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				int j = 0;
				while (rs.next()) {
					SUBJECT subject = new SUBJECT();

					subject.idsubject = rs.getInt("IDSUBJECT");
					subject.intitule = rs.getString("INTITULE");

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
				if ((id != null	&& id == 0) ||
					(id != null	&& id == 0)){
					myRequest = "SELECT * FROM PAIEMENT";
				} else {
					myRequest = "SELECT * FROM PAIEMENT WHERE ";
					if (id==null || id.toString().isEmpty() || id == 0)
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
					String _mois = rs.getDate("MOIS").toString();
					String _montant = rs.getString("MONTANT");

					paiement.add(0, _id);
					paiement.add(1, _mois);
					paiement.add(2, _montant);

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
	
	//date : mm/01/yyyy
	public static Float get_PAIEMENT(Integer id, String date) {
		Float ret = null;
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (id != null	&& id > 0 && date != null && !date.isEmpty())
					myRequest = "SELECT * FROM PAIEMENT WHERE ID=" + id.toString() + " AND MOIS='"+date+"'";
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
	
	//date : yyyy-mm-01
	public static boolean check_PAIEMENT(Integer id, String date) {
		boolean ret = false;
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (id != null && date != null)
				{
					if (id > 0 && !date.isEmpty()) {
						myRequest = "SELECT * FROM PAIEMENT WHERE ID="+id.toString()+" AND MOIS='"+date+"' AND MONTANT>=0.00";
					}
				}
//Afficher myRequest
System.out.println("check_PAIEMENT : "+myRequest);
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
	
	//date : mm/01/yyyy
	public static void set_PAIEMENT(Integer id, String date, Float montant) {
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (id != null && date != null)
				{
					if (id > 0 && !date.isEmpty()) {
						if (get_PAIEMENT(id, date) != null)
							myRequest = "UPDATE PAIEMENT SET MONTANT="+montant.toString()+" WHERE ID="+id.toString()+" AND MOIS='"+date+"'";
						else
							myRequest = "INSERT INTO PAIEMENT (ID,MOIS,MONTANT) VALUES ("+id.toString()+",'"+date+"',"+montant.toString()+")";
					}
				}
//Afficher myRequest
System.out.println("set_PAIEMENT : "+myRequest);
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
	
	
	public static void init_PAIEMENT(Integer id) {
		String baseDir = System.getProperty("jboss.server.base.dir");

		String appDir = baseDir+"\\deployments\\"+(new TOOLS()).getAppName()+".war\\config.xml";
System.out.println("init_PAIEMENT1 : myDir="+appDir);

		int school_year = getYearFromConfig(appDir);
//Afficher myRequest
System.out.println("init_PAIEMENT1 : school_year="+school_year);
		Connection con = getConnection();
		if (con != null) {
			try {
				String myRequest = "";
				if (id != null && id > 0) {
					PreparedStatement pStmt = null;
					if (get_PAIEMENT(id).size() > 0)
					{
						myRequest = "UPDATE PAIEMENT SET MONTANT=-1.00 WHERE ID="+id.toString();
//Afficher myRequest
System.out.println("init_PAIEMENT2 : "+myRequest);
						pStmt = con.prepareStatement(myRequest);
						pStmt.executeUpdate();
						pStmt.close();
					}
					else
					{
						myRequest = "INSERT INTO PAIEMENT (ID,MOIS,MONTANT) VALUES ("+id.toString()+",'"+school_year+"/09/01',-1.00)";
//Afficher myRequest
System.out.println("init_PAIEMENT3 : "+myRequest);
						pStmt = con.prepareStatement(myRequest);
						pStmt.executeUpdate();
						pStmt.close();
						
						myRequest = "INSERT INTO PAIEMENT (ID,MOIS,MONTANT) VALUES ("+id.toString()+",'"+school_year+"/10/01',-1.00)";
//Afficher myRequest
System.out.println("init_PAIEMENT : "+myRequest);
						pStmt = con.prepareStatement(myRequest);
						pStmt.executeUpdate();
						pStmt.close();
						
						myRequest = "INSERT INTO PAIEMENT (ID,MOIS,MONTANT) VALUES (" + id.toString() + ",'" + school_year + "/11/01',-1.00)";
//Afficher myRequest
System.out.println("init_PAIEMENT : "+myRequest);
						pStmt = con.prepareStatement(myRequest);
						pStmt.executeUpdate();
						pStmt.close();

						myRequest = "INSERT INTO PAIEMENT (ID,MOIS,MONTANT) VALUES (" + id.toString() + ",'" + school_year + "/12/01',-1.00)";
//Afficher myRequest
System.out.println("init_PAIEMENT : "+myRequest);
						pStmt = con.prepareStatement(myRequest);
						pStmt.executeUpdate();
						pStmt.close();

						myRequest = "INSERT INTO PAIEMENT (ID,MOIS,MONTANT) VALUES ("+ id.toString() + ",'" + (school_year+1) + "/01/01',-1.00)";
// Afficher myRequest
System.out.println("init_PAIEMENT : " + myRequest);
						pStmt = con.prepareStatement(myRequest);
						pStmt.executeUpdate();
						pStmt.close();

						myRequest = "INSERT INTO PAIEMENT (ID,MOIS,MONTANT) VALUES (" + id.toString() + ",'" + (school_year+1) + "/02/01',-1.00)";
// Afficher myRequest
System.out.println("init_PAIEMENT : " + myRequest);
						pStmt = con.prepareStatement(myRequest);
						pStmt.executeUpdate();
						pStmt.close();

						myRequest = "INSERT INTO PAIEMENT (ID,MOIS,MONTANT) VALUES (" + id.toString() + ",'" + (school_year+1) + "/03/01',-1.00)";
// Afficher myRequest
System.out.println("init_PAIEMENT : " + myRequest);
						pStmt = con.prepareStatement(myRequest);
						pStmt.executeUpdate();
						pStmt.close();

						myRequest = "INSERT INTO PAIEMENT (ID,MOIS,MONTANT) VALUES (" + id.toString() + ",'" + (school_year+1) + "/04/01',-1.00)";
// Afficher myRequest
System.out.println("init_PAIEMENT : " + myRequest);
						pStmt = con.prepareStatement(myRequest);
						pStmt.executeUpdate();
						pStmt.close();

						myRequest = "INSERT INTO PAIEMENT (ID,MOIS,MONTANT) VALUES (" + id.toString() + ",'" + (school_year+1) + "/05/01',-1.00)";
// Afficher myRequest
System.out.println("init_PAIEMENT : " + myRequest);
						pStmt = con.prepareStatement(myRequest);
						pStmt.executeUpdate();
						pStmt.close();

						myRequest = "INSERT INTO PAIEMENT (ID,MOIS,MONTANT) VALUES (" + id.toString() + ",'" + (school_year+1) + "/06/01',-1.00)";
// Afficher myRequest
System.out.println("init_PAIEMENT : " + myRequest);
						pStmt = con.prepareStatement(myRequest);
						pStmt.executeUpdate();
						pStmt.close();

						myRequest = "INSERT INTO PAIEMENT (ID,MOIS,MONTANT) VALUES (" + id.toString() + ",'" + (school_year+1) + "/07/01',-1.00)";
// Afficher myRequest
System.out.println("init_PAIEMENT : " + myRequest);
						pStmt = con.prepareStatement(myRequest);
						pStmt.executeUpdate();
						pStmt.close();
					}
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
				//String d = String.valueOf(date.getYear()+1900)+"-"+String.valueOf(date.getMonth()+1)+"-"+String.valueOf(date.getDate());
				myRequest = "SELECT * FROM ASSURANCE WHERE ID>100 AND FIN<'"+d+"'";
//Afficher myRequest
System.out.println("getSTUDENT_non_assures : "+myRequest);

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
	
	public static ArrayList<Object> getSTUDENT_non_payes() {
		ArrayList<Object> students = new ArrayList<Object>();

		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-");
		
		Connection con = getConnection();
		if (con == null) {
			students.add("Probléme de connection à la base de données");
		} else {
			try {
				String myRequest = "";
				String d = date.format(formatter);
				myRequest = "SELECT * FROM STUDENT LEFT JOIN paiement ON paiement.ID = STUDENT.ID WHERE paiement.ID IS NULL "+
						"UNION "+
						"SELECT * FROM STUDENT,paiement WHERE STUDENT.ID=paiement.ID AND STUDENT.ID>100 AND paiement.MOIS='"+d+"01' AND paiement.MONTANT=-1.00";
//Afficher myRequest
System.out.println("getSTUDENT_non_payes : "+myRequest);

				PreparedStatement pStmt = con.prepareStatement(myRequest);
				ResultSet rs = pStmt.executeQuery();
				while (rs.next()) {
					Integer _id = rs.getInt("ID");
					if (!students.contains(_id))
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
