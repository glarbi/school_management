package org;

import java.io.IOException;
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
 * Servlet implementation class JSONServletSubject
 */
@WebServlet("/JSONServletSubject")
public class JSONServletSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONServletSubject() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String idSubject = request.getParameter("idS");
		if (idSubject!=null && !idSubject.isEmpty()) {
			Integer idInt = Integer.decode(idSubject);
			SUBJECT mySubject = DBManager.getSUBJECT_by_ID(idInt);
	    	Gson gson = new Gson();
	    	String elem = gson.toJson(mySubject);
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
		}
		String idLevel = request.getParameter("idL");
		if (idLevel!=null && !idLevel.isEmpty()) {
			Integer idLevelInt = Integer.decode(idLevel);
			LEVEL myLevel = DBManager.getLEVEL_by_ID(idLevelInt);
	    	Gson gson = new Gson();
	    	String elem = gson.toJson(myLevel);
	    	response.setContentType("application/json");
	    	response.getWriter().print(elem);
	    	response.getWriter().flush();
	    	response.getWriter().close();
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
