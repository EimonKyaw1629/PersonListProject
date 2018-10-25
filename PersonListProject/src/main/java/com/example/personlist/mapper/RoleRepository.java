package com.example.personlist.mapper;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.personlist.model.Role;

public interface RoleRepository extends MongoRepository<Role, String>{
	 Role findByRole(String role);

}
