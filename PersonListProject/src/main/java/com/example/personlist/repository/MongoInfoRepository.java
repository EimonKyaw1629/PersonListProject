package com.example.personlist.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.personlist.model.MongoInfo;

public interface MongoInfoRepository extends MongoRepository<MongoInfo, Integer>{
	
	List<MongoInfo> findBygender(String gender);
	

}
