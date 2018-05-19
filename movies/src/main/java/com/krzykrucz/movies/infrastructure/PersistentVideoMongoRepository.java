package com.krzykrucz.movies.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

interface PersistentVideoMongoRepository extends MongoRepository<PersistentVideoInfo, UUID> {
    Optional<PersistentVideoInfo> findByTitle(@Param("title") String title);
}
