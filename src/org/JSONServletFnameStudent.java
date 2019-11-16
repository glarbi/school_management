package org;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class JSONServletFnameStudent
 */
@WebServlet("/JSONServletFnameStudent")
public class JSONServletFnameStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// This will store all received articles
	//List<STUDENT> retStudents = new LinkedList<STUDENT>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONServletFnameStudent() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String studentLastName = request.getParameter("lname");
    	ArrayList<ArrayList<Object>> students = DBManager.getSTUDENT(0,studentLastName,"");
    	
    	Gson gson = new Gson();
    	JsonElement elem = gson.toJsonTree(students, new TypeToken<List<List<Object>>>() {}.getType());
    	JsonArray jsonArray = elem.getAsJsonArray();
    	response.setContentType("application/json");
    	response.getWriter().print(jsonArray);
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		// 1. get received JSON data from request
				BufferedReader br = 
				new BufferedReader(new InputStreamReader(request.getInputStream()));
				
				String json = "";
				if(br != null){
					json = br.readLine();
					System.out.println(json);
				}
				
				// 2. initiate jackson mapper
				ObjectMapper mapper = new ObjectMapper();
		    	
				// 3. Convert received JSON to Article
				ArrayList<ArrayList<Object>> students = DBManager.getSTUDENT(0,json,"");
				STUDENT myStudent = new STUDENT();
				ArrayList<Object> ligne1 = students.get(0);
				
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
				
				STUDENT s = mapper.readValue(json, STUDENT.class);

				// 4. Set response type to JSON
				response.setContentType("application/json");		    
		    	
				// 5. Add article to List<Article>
				retStudents.add(s);

				// 6. Send List<Article> as JSON to client
				mapper.writeValue(response.getOutputStream(), retStudents);
				*/
	}

}
