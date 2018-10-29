package com.example.personlist.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.personlist.model.MongoInfo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
@Component
@Repository
@Transactional
public class MongoInfoDAO extends JdbcDaoSupport{
	
	@Autowired
	public MongoInfoDAO(DataSource ds)
	{
		this.setDataSource(ds);
	}

	@Value("${spring.data.mongodb.database}")
	private String database;
	private MongoClient mongoClient;
	public void mongoInsert(MongoInfo mongoinfo){

		mongoClient = new MongoClient();
		DB db = mongoClient.getDB(database);
		
	    DBCollection mongoTable = db.getCollection("mongoInfo");
	    BasicDBObject doc = new BasicDBObject("_id", mongoinfo.getId()).
	    		append("gender", mongoinfo.getGender()).
	    		append("age", mongoinfo.getAge());
	    
	    mongoTable.insert(doc);
	}
	
	public List<MongoInfo> SelectAll()
	{
		try
		{
		List<DBObject> myList = null;
		
        MongoClient client = new MongoClient("localhost", 27017);
		MongoDatabase database = client.getDatabase("MongoInfo");
		MongoCollection<Document> collection = database
				.getCollection("mongoInfo");
 
		List<Document> employees = (List<Document>) collection.find().into(
				new ArrayList<Document>());
		List<MongoInfo> gList= new ArrayList<MongoInfo>();
		for (Document employee : employees) {
			
				MongoInfo mongoinfo = new MongoInfo();
				 mongoinfo.setId(employee.getInteger("_id"));
	             mongoinfo.setGender(employee.getString("gender"));
	             mongoinfo.setAge( employee.getInteger("age"));
	             gList.add(mongoinfo);
				
		}
        
        return gList;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	
	public MongoInfo mongoFind(int pid) {
		
		List<DBObject> myList = null;
		MongoInfo mongoinfo = new MongoInfo();
		mongoClient = new MongoClient();
		DB db = mongoClient.getDB(database);
		
		
		DBCollection mongoTable = db.getCollection("mongoInfo");
		BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", pid);
        DBCursor cursor = mongoTable.find(whereQuery);
        myList = cursor.toArray();
        
        mongoinfo.setId((int)(myList.get(0).get("_id")));
        mongoinfo.setGender((String)(myList.get(0).get("gender")));
        mongoinfo.setAge((int)(myList.get(0).get("age")));
        
        return mongoinfo;
		
	}
	
	public List<MongoInfo> mongoFindGender(String g) {
		
		try
		{
		List<DBObject> myList = null;
		MongoInfo mongoinfo = new MongoInfo();
		List<MongoInfo> gList= new ArrayList<MongoInfo>();
		mongoClient = new MongoClient();
		DB db = mongoClient.getDB(database);
		
		
		DBCollection mongoTable = db.getCollection("mongoInfo");
		BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("gender", g);
        DBCursor cursor = mongoTable.find(whereQuery);
        myList = cursor.toArray();
        
        mongoinfo.setId((int)(myList.get(0).get("_id")));
        mongoinfo.setGender((String)(myList.get(0).get("gender")));
        mongoinfo.setAge((int)(myList.get(0).get("age")));
        gList.add(mongoinfo);
        return gList;
		}
		catch(Exception ex)
		{
			return null;
		}
      
	}
	
	public void mongoUpdata(MongoInfo mongoinfo) {
		
		mongoClient = new MongoClient();
		DB db = mongoClient.getDB(database);
		DBCollection mongoTable = db.getCollection("mongoInfo");
		
		BasicDBObject updateQuery = new BasicDBObject()
				.append("gender", mongoinfo.getGender())
				.append("age", mongoinfo.getAge());
        
        BasicDBObject searchQuery = new BasicDBObject()
        		.append("_id", mongoinfo.getId());
        
        mongoTable.update(searchQuery, updateQuery);
     
	}
	
	public void mongoDelete(int pid) {
		
		mongoClient = new MongoClient();
		DB db = mongoClient.getDB(database);
		DBCollection mongoTable = db.getCollection("mongoInfo");
		
		BasicDBObject deleteQuery = new BasicDBObject()
				.append("_id", pid);
		
		mongoTable.remove(deleteQuery);
	}
	
}
