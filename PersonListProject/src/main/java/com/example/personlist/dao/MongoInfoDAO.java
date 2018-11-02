package com.example.personlist.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.personlist.model.MongoInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

@Component
@Repository
@Transactional
public class MongoInfoDAO extends JdbcDaoSupport {

	private MongoClient client;

	@Autowired
	public MongoInfoDAO(DataSource ds) {
		this.setDataSource(ds);
	}
	
	private MongoCollection<Document> getMongoDatabase()
	{
		client = new MongoClient("localhost", 27017);
		MongoCollection<Document> collection = client.getDatabase("MongoInfo").getCollection("mongoInfo");
		return collection;
	}
	
	private Document mongoDoc(MongoInfo mongoinfo) {
		ObjectMapper oMapper = new ObjectMapper();
	    Map<String, Object> map = oMapper.convertValue(mongoinfo, new TypeReference<Map<String, Object>>() {});
	    Document company = new Document(new HashMap<String, Object>());
	    company.putAll(map);
		return company;
	}
	
	
	public void mongoInsert(MongoInfo mongoinfo) {
        MongoCollection<Document> collection = this.getMongoDatabase();
        Document mongodoc = this.mongoDoc(mongoinfo);
        collection.insertOne(mongodoc);
	}
	
	public void mongoUpdata(MongoInfo mongoinfo) {
		MongoCollection<Document> collection = this.getMongoDatabase();
		Document mongodoc = this.mongoDoc(mongoinfo);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("id", mongoinfo.getId());
        collection.findOneAndReplace(whereQuery, mongodoc);
	}
	
	public void mongoDelete(int pid) {
		MongoCollection<Document> collection = this.getMongoDatabase();
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("id",pid);
        collection.findOneAndDelete(whereQuery);
	}
	
	public List<MongoInfo> SelectAllGender() {
		try {
			MongoCollection<Document> collection = this.getMongoDatabase();
			List<Document> listGender = (List<Document>) collection.find().into(new ArrayList<Document>());
			List<MongoInfo> gList = new ArrayList<MongoInfo>();
			
			for (Document gender : listGender) {
				MongoInfo mongoinfo = new MongoInfo();
				mongoinfo.setId((int)gender.getInteger("id"));
				mongoinfo.setGender(gender.getString("gender"));
				mongoinfo.setAge(gender.getInteger("age"));
				mongoinfo.setJob(gender.getString("job"));
				
				gList.add(mongoinfo);
			}
			return gList;
		} catch (Exception ex) {
			return null;
		}
	}

	public MongoInfo mongoFindbyPersonID(int pid) {
        try {
        	MongoCollection<Document> collection = this.getMongoDatabase();
            Document res = collection.find(eq("id", pid)).first();
            MongoInfo info = new MongoInfo(res.getInteger("id"),res.getString("gender"),res.getInteger("age"),res.getString("job"));   
            return info;
        } catch (Exception ex) {
            return null;
        }
    }

	public List<MongoInfo> mongoFindGenderJob(MongoInfo info){

		try {
			MongoCollection<Document> collection = this.getMongoDatabase();
			BasicDBObject whereQuery = new BasicDBObject();
		
			whereQuery.put("job", java.util.regex.Pattern.compile(info.getJob()));
			whereQuery.put("gender", java.util.regex.Pattern.compile(info.getGender()));
			
			List<Document> employees = (List<Document>) collection.find(whereQuery).into(new ArrayList<Document>());
			List<MongoInfo> gList = new ArrayList<MongoInfo>();
			for (Document employee : employees) {
				MongoInfo mongoinfo = new MongoInfo();
				mongoinfo.setId(employee.getInteger("id"));
				mongoinfo.setGender(employee.getString("gender"));
				mongoinfo.setAge(employee.getInteger("age"));
				mongoinfo.setJob(employee.getString("job"));
				gList.add(mongoinfo);
			}
			return gList;
		} catch (Exception ex) {
			return null;
		}
	}
}
