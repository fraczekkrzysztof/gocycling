package com.fraczekkrzysztof.gocycling.dao;


import com.fraczekkrzysztof.gocycling.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@RepositoryRestResource(path = "users")
@Transactional
public interface UserRepository extends JpaRepository<User,String> {


}
