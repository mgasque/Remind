package fr.enseirb.t2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;


//@Path("/test")
@WebServlet(urlPatterns="/test")
public class HelloWorld extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@GET
	//@Produces("text/plain")
	//public String coucou() {
	//	return "Coucou c'est moi :)";
	//}
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response
			) {

		try {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.println("angular.callbacks._0");
			out.println("(");
			out.println("{");
			out.println("\"First Name\": \"Devesh\",");
			out.println("\"Last Name\": \"Sharma\"");
			out.println("}");
			out.println(")");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}