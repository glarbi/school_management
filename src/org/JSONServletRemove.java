package org;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class JSONServletRemove
 */
@WebServlet(description = "Delete some entities like subject, level ...", urlPatterns = { "/JSONServletRemove" })
public class JSONServletRemove extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONServletRemove() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		//Delete a school level
		String idLevel = request.getParameter("idL");
		if (idLevel!=null && !idLevel.isEmpty()) {
			Integer idLevelInt = Integer.decode(idLevel);
			int ret = DBManager.deleteLEVEL_by_ID(idLevelInt);
	    	Gson gson = new Gson();
	    	String elem = "";
	    	if (ret>0) elem = gson.toJson("Niveau scolaire supprimé avec succès.");
	    	else elem = gson.toJson("Le niveau scolaire n'a pas été supprimé.");
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
		}
		
		//Delete a subject
		String idSubject = request.getParameter("idS");
		if (idSubject!=null && !idSubject.isEmpty()) {
			Integer idSubjectInt = Integer.decode(idSubject);
			int ret = DBManager.deleteSubject_by_ID(idSubjectInt);
	    	Gson gson = new Gson();
	    	String elem = "";
	    	if (ret>0) elem = gson.toJson("Matière supprimée avec succès.");
	    	else elem = gson.toJson("La matière n'a pas été supprimée.");
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
		}

		//Delete a student
		String idStudent = request.getParameter("idStudent");
		if (idStudent!=null && !idStudent.isEmpty()) {
			Integer idStudentInt = Integer.decode(idStudent);
			int ret = DBManager.deleteStudent_by_ID(idStudentInt);
	    	Gson gson = new Gson();
	    	String elem = "";
	    	if (ret>0) elem = gson.toJson("Etudiant supprimé avec succès.");
	    	else elem = gson.toJson("L'étudiant n'a pas été supprimé.");
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
		}

		//Delete a teacher
		String idTeacher = request.getParameter("idTeacher");
		if (idTeacher!=null && !idTeacher.isEmpty()) {
			Integer idTeacherInt = Integer.decode(idTeacher);
			int ret = DBManager.deleteTeacher_by_ID(idTeacherInt);
	    	Gson gson = new Gson();
	    	String elem = "";
	    	if (ret>0) elem = gson.toJson("Enseignant supprimé avec succès.");
	    	else elem = gson.toJson("L'enseignant n'a pas été supprimé.");
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
		}

		//Delete a registration
		String idRegistrationStudent = request.getParameter("idRegistrationStudent");
		String idRegistrationTeacher = request.getParameter("idRegistrationTeacher");
		String idRegistrationSubject = request.getParameter("idRegistrationSubject");
		if (idRegistrationStudent!=null && !idRegistrationStudent.isEmpty() && idRegistrationTeacher!=null && !idRegistrationTeacher.isEmpty() &&
				idRegistrationSubject!=null && !idRegistrationSubject.isEmpty()) {
			Integer idStudentInt = Integer.decode(idRegistrationStudent);
			Integer idTeacherInt = Integer.decode(idRegistrationTeacher);
			Integer idSubjectInt = Integer.decode(idRegistrationSubject);
			int ret = DBManager.deleteRegistration(idStudentInt, idTeacherInt, idSubjectInt);
	    	Gson gson = new Gson();
	    	String elem = "";
	    	if (ret>0) elem = gson.toJson("Inscription annulée avec succès.");
	    	else elem = gson.toJson("L'inscription n'a pas été annulée.");
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
		}

		//Delete a teacher->subject
		String _teacherSubject_idTeacher = request.getParameter("TeacherSubject_idTeacher");
		String _teacherSubject_idSubject = request.getParameter("TeacherSubject_idSubject");
		if (_teacherSubject_idTeacher!=null && !_teacherSubject_idTeacher.isEmpty() && _teacherSubject_idSubject!=null && !_teacherSubject_idSubject.isEmpty()) {
			Integer idTeacherInt = Integer.decode(_teacherSubject_idTeacher);
			Integer idSubjectInt = Integer.decode(_teacherSubject_idSubject);
			int ret = DBManager.deleteTeacherSubject(idTeacherInt, idSubjectInt);
	    	Gson gson = new Gson();
	    	String elem = "";
	    	if (ret>0) elem = gson.toJson("Liaison enseignant->matière annulée avec succès.");
	    	else elem = gson.toJson("La liaison enseignant->matière n'a pas été annulée.");
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
		}

		//Delete assurance
		String _assuranceId = request.getParameter("assuranceId");
		if (_assuranceId!=null && !_assuranceId.isEmpty()) {
			Integer idInt = Integer.decode(_assuranceId);
			int ret = DBManager.deleteAssurance(idInt);
	    	Gson gson = new Gson();
	    	String elem = "";
	    	if (ret>0) elem = gson.toJson("Assurance annulée avec succès.");
	    	else elem = gson.toJson("L'assurance n'a pas été annulée.");
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
		}
		
		//Delete Paiement
		String _paiement_StudentId = request.getParameter("removePaiementStudent_StudentId");
		String _paiement_TeacherNomPrenom = request.getParameter("removePaiementStudent_teacherNomPrenom");
		String _paiement_SubjectLevel = request.getParameter("removePaiementStudent_subjectLevel");
		String _paiement_Debut = request.getParameter("removePaiementStudent_Debut");
		if (_paiement_StudentId!=null && !_paiement_StudentId.isEmpty() && _paiement_Debut!=null && !_paiement_Debut.isEmpty()) {
			Integer studentId = Integer.decode(_paiement_StudentId);
			String[] splitted = _paiement_TeacherNomPrenom.split(" ");
			Integer teacherId = Integer.decode(DBManager.getTEACHERbyName(splitted[0], splitted[1]).ID);
			splitted = _paiement_SubjectLevel.split(" ");
			Integer subjectId = DBManager.getSUBJECT_by_TITLE_Level(splitted[0], splitted[1]).idsubject;
			int ret = DBManager.deletePaiement(studentId, teacherId, subjectId, _paiement_Debut);
	    	Gson gson = new Gson();
	    	String elem = "";
	    	if (ret>0) elem = gson.toJson("Paiement supprimé avec succès.");
	    	else elem = gson.toJson("Le paiement n'a pas été suprimé.");
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
		}
		
		//Delete PaiementTeacher
		String _paiementTeacher_TeacherId = request.getParameter("removePaiementTeacher_idTeacher");
		String _paiementTeacher_SubjectLevel = request.getParameter("removePaiementTeacher_Subject_Level");
		String _paiementTeacher_Mois = request.getParameter("removePaiementTeacher_Mois");
		if (_paiementTeacher_TeacherId!=null && !_paiementTeacher_TeacherId.isEmpty() &&
			_paiementTeacher_Mois!=null && !_paiementTeacher_Mois.isEmpty() &&
			_paiementTeacher_SubjectLevel!=null && !_paiementTeacher_SubjectLevel.isEmpty()) {
			String[] splitted = _paiementTeacher_SubjectLevel.split(" ");
			Integer subjectId = DBManager.getIDSujectbyTitleLevel(splitted);
			Integer teacherId = Integer.decode(_paiementTeacher_TeacherId);
			int ret = DBManager.deletePaiementTeacher(teacherId, subjectId, _paiementTeacher_Mois);
	    	Gson gson = new Gson();
	    	String elem = "";
	    	if (ret>0) elem = gson.toJson("Paiement supprimé avec succès.");
	    	else elem = gson.toJson("Le paiement n'a pas été suprimé.");
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
		}

	}

}
