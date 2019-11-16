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
 * Servlet implementation class JSONServletTeacherFromSubject
 */
@WebServlet(name = "JSONServletTeacher_Subject", description = "Get Teachers from a Subject or inverse", urlPatterns = { "/JSONServletTeacher_Subject" })
public class JSONServletTeacher_Subject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONServletTeacher_Subject() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//Teacher from subject
		String _subject_title_level = request.getParameter("subject_title_level");
		if (_subject_title_level != null && !_subject_title_level.isEmpty()) {
			String[] splitted = _subject_title_level.split(" ");
	    	ArrayList<TEACHER> teachers = DBManager.getTEACHERSbySubject(splitted);
	    	
	    	Gson gson = new Gson();
	    	//JsonElement elem = gson.toJsonTree(teachers, new TypeToken<List<TEACHER>>() {}.getType());
	    	//JsonArray jsonArray = elem.getAsJsonArray();
	    	//response.setContentType("application/json");
	    	//response.getWriter().print(jsonArray);
	    	
	    	PrintWriter out = response.getWriter();
			out.print(gson.toJson(teachers));
			out.flush();
			out.close();
		}
		
		//Subject from teacher
		String _idTeacher = request.getParameter("addPaiementTeacher_idTeacher");
		if (_idTeacher != null && !_idTeacher.isEmpty()) {
			Integer teacherIdInt = Integer.parseInt(_idTeacher);
			ArrayList<SUBJECT> mySubjects = DBManager.getSUBJECT_by_Teacher(teacherIdInt);
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

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
