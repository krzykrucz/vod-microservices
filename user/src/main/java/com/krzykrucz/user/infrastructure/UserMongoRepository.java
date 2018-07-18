package com.krzykrucz.user.infrastructure;

import com.krzykrucz.user.domain.User;
import com.krzykrucz.user.domain.UserId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserMongoRepository extends MongoRepository<User, UserId> {

    Optional<User> findByName_Name(@Param("name") String userName);
}
