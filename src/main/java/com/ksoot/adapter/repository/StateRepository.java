package com.ksoot.adapter.repository;

import com.ksoot.domain.model.State;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StateRepository extends MongoRepository<State, String> {

  boolean existsByCode(final String code);

  Optional<State> findByCode(final String code);
}
