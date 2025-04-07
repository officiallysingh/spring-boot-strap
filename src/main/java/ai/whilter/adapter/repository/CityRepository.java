package ai.whilter.adapter.repository;

import ai.whilter.domain.model.City;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityRepository extends MongoRepository<City, String> {

  boolean existsByCode(final String code);

  Optional<City> findByCode(final String code);

  List<City> findAllByStateId(final String stateId);
}
