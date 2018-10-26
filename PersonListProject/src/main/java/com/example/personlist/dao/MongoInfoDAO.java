package com.example.personlist.dao;

import java.util.List;

import javax.sql.DataSource;

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
