package com.example.personlist.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.personlist.model.MongoInfo;

public interface MongoInfoRepository extends MongoRepository<MongoInfo, Integer>{

}
