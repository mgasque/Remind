package fr.enseirb.t2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Path("/todoget")
public class Todoget {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static String goGetTodos(@QueryParam("callback") String callback) {
		try{
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "projet" );
			DBCollection coll = db.getCollection("todos");
			DBCursor cursor = coll.find();
						
			String text = "{\"todos\" : [{}";
			int i=1;
			while (cursor.hasNext()) { 
				text =  text + "," + cursor.next().toString();
				i++;
			}
			text = text + "]}";
			text = callback +"(" + text + ")";
			mongoClient.close();
			return text;
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return "tata";
		}
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public static String doGetTodo(@QueryParam("callback") String callback, @PathParam("id") String id) {
		try{
			ManageTodo mTodo = new ManageTodo();
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "projet" );

			DBCollection coll = db.getCollection("todos");
			DBObject todo = mTodo.findTodoByID(coll, id);
			
			String text = callback +"({\"todo\" : [{}," + todo.toString() + "]})";
			mongoClient.close();
			return text;

		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return "toto";
		}
	}
}
