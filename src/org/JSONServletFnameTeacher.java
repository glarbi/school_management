package org;

import java.io.IOException;
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
 * Servlet implementation class JSONServletFnameTeacher
 */
@WebServlet(description = "Get teachers first names using their last names.", urlPatterns = { "/JSONServletFnameTeacher" })
public class JSONServletFnameTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONServletFnameTeacher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String teacherLastName = request.getParameter("lname");
    	ArrayList<ArrayList<Object>> teachers = DBManager.getTEACHER(0,teacherLastName,"");
    	
    	Gson gson = new Gson();
    	JsonElement elem = gson.toJsonTree(teachers, new TypeToken<List<List<Object>>>() {}.getType());
    	JsonArray jsonArray = elem.getAsJsonArray();
    	response.setContentType("application/json");
    	response.getWriter().print(jsonArray);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
