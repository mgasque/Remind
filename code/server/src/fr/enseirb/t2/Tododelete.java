package fr.enseirb.t2;

import java.net.UnknownHostException;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.json.JSONException;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;


@Path("/tododelete")
public class Tododelete {

	@DELETE
	@Path("/{id}")
	@Produces("application/json")
		public  void doPut(String data, @PathParam("id") String id) throws JSONException, UnknownHostException {
			System.out.println(id);
			ManageTodo mTodo = new ManageTodo();
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "projet" );
			DBCollection coll = db.getCollection("todos");		
			mTodo.removeTodo(db, coll, id, data);	
			System.out.println(data);
		}
}
