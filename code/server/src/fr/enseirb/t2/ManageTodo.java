package fr.enseirb.t2;

import org.bson.types.ObjectId;
import org.json.JSONException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class ManageTodo {

	ManageTodo(){
		//constructor
	}


	public void addTodo(DB database,DBCollection coll,String json){

		DBObject dbObject = (DBObject) JSON.parse(json);
		coll.insert(dbObject);
	}

	public DBObject findTodoByID(DBCollection collection,String id) throws JSONException{
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		DBObject dbObj = collection.findOne(query);

		return dbObj;		
	}

	public void removeTodo (DB db, DBCollection coll, String id, String json){	
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		DBObject dbObj = coll.findOne(query);

		coll.remove(dbObj);
	}
	
	public void modifyTodo(DB db, DBCollection coll, String id, String json){
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		DBObject dbObj = coll.findOne(query);
		
		BasicDBObject newObj = (BasicDBObject) JSON.parse(json);

		coll.update(dbObj, newObj);
	}

}