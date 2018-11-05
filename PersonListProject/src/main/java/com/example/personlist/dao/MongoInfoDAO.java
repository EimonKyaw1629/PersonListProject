package com.example.personlist.dao;

import static com.mongodb.client.model.Filters.eq;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	
	private Document ConvertObjToDoc(MongoInfo mongoinfo) {
		ObjectMapper oMapper = new ObjectMapper();
	    Map<String, Object> map = oMapper.convertValue(mongoinfo, new TypeReference<Map<String, Object>>() {});
	    Document company = new Document(new HashMap<String, Object>());
	    company.putAll(map);
		return company;
	}
	
	private MongoInfo ConvertDocToMongo(Document mongoinfo) {
		//Map<String, Object> map = new HashMap<>(mongoinfo);
		Map<String, Object> map = new HashMap<>(mongoinfo);
		MongoInfo info = new MongoInfo();
		info= (MongoInfo) MongoInfoDAO.convertMapToObject(map, info);
		return info;
	}
	public static Object convertMapToObject(Map<String, Object> map, Object objClass){
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString = null;
		Iterator<String> itr = map.keySet().iterator();
		while(itr.hasNext()){
			keyAttribute = (String) itr.next();
			methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
			try {
				Method[] methods = objClass.getClass().getDeclaredMethods();
				for(int i=0;i<=methods.length-1;i++){
					if(methodString.equals(methods[i].getName())){
						System.out.println("invoke : "+methodString);
						methods[i].invoke(objClass, map.get(keyAttribute));
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return objClass;
	}


	
	
	public void mongoInsert(MongoInfo mongoinfo) {
        MongoCollection<Document> collection = this.getMongoDatabase();
        Document mongodoc = this.ConvertObjToDoc(mongoinfo);
        collection.insertOne(mongodoc);
	}
	
	public void mongoUpdata(MongoInfo mongoinfo) {
		MongoCollection<Document> collection = this.getMongoDatabase();
		Document mongodoc = this.ConvertObjToDoc(mongoinfo);
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
				MongoInfo mongoinfo = this.ConvertDocToMongo(gender); //new MongoInfo((int)gender.getInteger("id"),gender.getString("gender"),gender.getInteger("age"),gender.getString("job"));
				
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
            MongoInfo info = this.ConvertDocToMongo(res);   
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
			
			List<Document> findList = (List<Document>) collection.find(whereQuery).into(new ArrayList<Document>());
			List<MongoInfo> gList = new ArrayList<MongoInfo>();
			for (Document lst : findList) {
				MongoInfo mongoinfo = this.ConvertDocToMongo(lst);
				gList.add(mongoinfo);
			}
			return gList;
		} catch (Exception ex) {
			return null;
		}
	}
}
