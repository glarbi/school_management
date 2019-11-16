package org;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class JSONServletPaiement
 */
@WebServlet(description = "This servlet allows handling paiements", urlPatterns = { "/JSONServletPaiement" })
public class JSONServletPaiement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONServletPaiement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//doPost(request, response);
		
		//Get student's subjects taught by a teacher
		String _idStudent = request.getParameter("addPaiementStudent_getSubjects_studentId");
		String _idTeacher = request.getParameter("addPaiementStudent_getSubjects_teacherId");
		if (_idStudent!=null && !_idStudent.isEmpty() && _idTeacher!=null && !_idTeacher.isEmpty()) {
			Integer idStudent = Integer.decode(_idStudent);
			Integer idTeacher = Integer.decode(_idTeacher);
			ArrayList<SUBJECT> mySubjects = DBManager.getSUBJECT_by_StudentTeacher(idStudent, idTeacher);
//retourner <idSubject, "title level">
			ArrayList<ArrayList<String>> rets = new ArrayList<ArrayList<String>>();
			for (int j=0; j<mySubjects.size(); j++) {
				ArrayList<String> ret = new ArrayList<String>();
				LEVEL myLevel = DBManager.getLEVEL_by_ID(mySubjects.get(j).idlevel);
				ret.add(mySubjects.get(j).idsubject.toString());
				ret.add(mySubjects.get(j).title+" "+myLevel.levelTitle);
				rets.add(ret);
			}
			Gson gson = new Gson();
		    JsonElement element = gson.toJsonTree(rets, new TypeToken<List<List<String>>>() {}.getType());

		    response.setContentType("application/json; charset=UTF-8");
		    PrintWriter printOut = response.getWriter();

		    JsonArray jsonArray = element.getAsJsonArray();
		    printOut.print(jsonArray);
		    printOut.flush();
		    printOut.close();
		}
		
		//Get student's teachers
		_idStudent = request.getParameter("addPaiementStudent_getTeachers_studentId");
System.out.println("_idStudent: "+_idStudent);
		if (_idStudent!=null && !_idStudent.isEmpty()) {
			Integer idInt = Integer.decode(_idStudent);
			ArrayList<TEACHER> myTeachers = DBManager.getTEACHER_by_Student(idInt);
//retourner <idTeacher, "nom prénom">
			ArrayList<ArrayList<String>> rets = new ArrayList<ArrayList<String>>();
			for (int j=0; j<myTeachers.size(); j++) {
				ArrayList<String> ret = new ArrayList<String>();
				ret.add(myTeachers.get(j).ID.toString());
				ret.add(myTeachers.get(j).NOM+" "+myTeachers.get(j).PRENOM);
				rets.add(ret);
			}
			Gson gson = new Gson();
		    JsonElement element = gson.toJsonTree(rets, new TypeToken<List<List<String>>>() {}.getType());

		    response.setContentType("application/json; charset=UTF-8");
		    PrintWriter printOut = response.getWriter();

		    JsonArray jsonArray = element.getAsJsonArray();
		    printOut.print(jsonArray);
		    printOut.flush();
		    printOut.close();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		//Ajouter un paiement
		String _idStudent = request.getParameter("savePaiementStudent_idStudent");
		String _idTeacher = request.getParameter("savePaiementStudent_idTeacher");
		String _idSubject = request.getParameter("savePaiementStudent_idSubject");
		String _debut = request.getParameter("savePaiementStudent_debut");
		String _fin = request.getParameter("savePaiementStudent_fin");
		String _montant = request.getParameter("savePaiementStudent_montant");
		if (_idStudent!=null && !_idStudent.isEmpty() && _debut!=null && !_debut.isEmpty() && _fin!=null && !_fin.isEmpty() && _montant!=null && !_montant.isEmpty()) {
			Integer idStudentInt = Integer.decode(_idStudent);
			Integer idTeacherInt = Integer.decode(_idTeacher);
			Integer idSubjectInt = Integer.decode(_idSubject);
			int ret = DBManager.set_PAIEMENT(idStudentInt, idTeacherInt, idSubjectInt, _debut, _fin, Float.parseFloat(_montant));
	    	Gson gson = new Gson();
	    	String elem = "";
	    	if (ret>0) elem = gson.toJson("Paiement créé avec succès.");
	    	else elem = gson.toJson("Le paiement n'a pas été créé.");
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
		}
		
		//Ajouter un paiementTeacher
		_idTeacher = request.getParameter("savePaiementTeacher_idTeacher");
		_idSubject = request.getParameter("savePaiementTeacher_subjectId");
		String _mois = request.getParameter("savePaiementTeacher_mois");
		_montant = request.getParameter("savePaiementTeacher_montant");
		if (_idTeacher!=null && !_idTeacher.isEmpty() && _idSubject!=null && !_idSubject.isEmpty() && _mois!=null && !_mois.isEmpty() && _montant!=null && !_montant.isEmpty()) {
			Integer idTeacherInt = Integer.decode(_idTeacher);
			Integer idSubjectInt = Integer.decode(_idSubject);
			int ret = DBManager.set_PAIEMENT_TEACHER(idTeacherInt, idSubjectInt, _mois, Float.parseFloat(_montant));
	    	Gson gson = new Gson();
	    	String elem = "";
	    	if (ret>0) elem = gson.toJson("Paiement créé avec succès.");
	    	else elem = gson.toJson("Le paiement n'a pas été créé.");
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
		}

	}

}
