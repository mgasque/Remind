package fr.enseirb.t2;

import java.net.UnknownHostException;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


import org.json.JSONException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;




@Path("/todoput")
public class Todoput {

	
	@PUT
	@Path("/{id}")
	@Produces("application/json")
	public  void doPut(String data, @PathParam("id") String id) throws JSONException, UnknownHostException {
		System.out.println(id);
		ManageTodo mTodo = new ManageTodo();
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "projet" );
		DBCollection coll = db.getCollection("todos");		
		mTodo.modifyTodo(db, coll, id, data);
		mongoClient.close();	
		System.out.println(data);
	}
}
