package fr.enseirb.t2;

import java.net.UnknownHostException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Path("/usercheck")
public class Userheader {

	
	@PUT
	@Produces("application/json")
	public  void login(String data) throws JSONException, UnknownHostException {

		ManageUser mUser = new ManageUser();
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "projet" );
		DBCollection coll = db.getCollection("users");	
		
		JSONObject obj = new JSONObject(data);
		String email = obj.getString("email");
		String pass = obj.getString("pass");

		mUser.modifyUser(db, coll, email, pass);
		mongoClient.close();	
	}
	
	@PUT
	@Path("logout")
	@Produces("application/json")
	public  void logout(String data) throws JSONException, UnknownHostException {

		ManageUser mUser = new ManageUser();
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "projet" );
		DBCollection coll = db.getCollection("users");	
		
		JSONObject obj = new JSONObject(data);
		String email = obj.getString("email");
		String pass = obj.getString("pass");

		mUser.logoutUser(db, coll, email, pass);
		mongoClient.close();	
	}
	
	@GET
	@Path("{email}")
	@Produces("application/json")
	public String status(@QueryParam("callback") String callback,  @PathParam("email") String email) throws UnknownHostException, JSONException {
		ManageUser mUser = new ManageUser();
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "projet" );
		DBCollection coll = db.getCollection("users");
		
		DBObject user = mUser.findUserByEmail(coll, email);

		JSONObject obj = new JSONObject(user.toString());
		System.out.println(obj.toString());
		
		String text = callback +"({\"todo\" : [{}," + obj.toString() + "]})";
		mongoClient.close();
		return text;	
		
	}
	
	@POST
	@Path("register")
	@Consumes("application/json")
	public  String register(String data) throws UnknownHostException, JSONException {
			
			System.out.println(data);
			ManageUser mUser = new ManageUser();
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "projet" );

			DBCollection coll = db.getCollection("users");
			
			System.out.println(data);
			
			// Checking _id field
			JSONObject object = new JSONObject(data);
			try {
				String email = object.getString("email");
				System.out.println(email);
				mUser.addUser(db, coll, data);	
			} catch (JSONException e){
				System.out.println("adding");

				mUser.addUser(db, coll, data);
			} 
			mongoClient.close();
		
		return null;
		
	}
	
	
}
