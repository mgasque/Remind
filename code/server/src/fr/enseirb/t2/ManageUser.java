package fr.enseirb.t2;

import org.bson.types.ObjectId;
import org.json.JSONException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class ManageUser {
	
	ManageUser(){
		//constructor
	}
	
	public void modifyUser(DB db, DBCollection coll, String email, String pass){
		BasicDBObject query = new BasicDBObject();
		query.put("email", email);
		query.put("pass", pass);
		DBObject dbObj = coll.findOne(query);
		
		System.out.println(dbObj);
		
		if(dbObj!=null){
			query.put("connected", true);
			coll.update(dbObj, query);
			System.out.println("trouve");
		}else{
			System.out.println("non trouve");
		}
	}
	
	public void logoutUser(DB db, DBCollection coll, String email, String pass){
		BasicDBObject query = new BasicDBObject();
		query.put("email", email);
		query.put("pass", pass);
		DBObject dbObj = coll.findOne(query);
		
		System.out.println(dbObj);
		
		if(dbObj!=null){
			query.put("connected", false);
			coll.update(dbObj, query);
			System.out.println("trouve");
		}else{
			System.out.println("non trouve");
		}
	}
	
	public DBObject findUserByEmail(DBCollection collection,String email) throws JSONException{
		BasicDBObject query = new BasicDBObject();
		query.put("email", email);
		DBObject dbObj = collection.findOne(query);

		return dbObj;		
	}
	
	public void addUser(DB database,DBCollection coll,String json){
		DBObject dbObject = (DBObject) JSON.parse(json);
		coll.insert(dbObject);
	}

}
