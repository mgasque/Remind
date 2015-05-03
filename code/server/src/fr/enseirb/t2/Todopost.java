package fr.enseirb.t2;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;


@Path("/todopost")
public class Todopost {

	@POST
	@Consumes("application/json")
	public  String doPost(String data) {
		try{
			System.out.println(data);
			ManageTodo mTodo = new ManageTodo();
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "projet" );

			DBCollection coll = db.getCollection("todos");
			
			System.out.println(data);
			
			// Checking _id field
			JSONObject object = new JSONObject(data);
			try {
				String id = object.getString("_id");
				System.out.println(id);
				mTodo.addTodo(db, coll, data);	
			} catch (JSONException e){
				System.out.println("adding");

				mTodo.addTodo(db, coll, data);
			} 
			mongoClient.close();

		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return null;
		}
		return null;
		
	}
}
