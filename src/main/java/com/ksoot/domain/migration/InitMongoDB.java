package com.ksoot.domain.migration;

import static com.ksoot.common.mongo.MongoSchema.*;

import com.ksoot.adapter.repository.CityRepository;
import com.ksoot.adapter.repository.StateRepository;
import com.ksoot.domain.model.City;
import com.ksoot.domain.model.State;
import io.mongock.api.annotations.*;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Slf4j
@ChangeUnit(id = "init-master-schema", order = "001", author = "rajveer")
public class InitMongoDB {

  private static final String STATES_DATA_FILE = "migration/mongo/states.csv";
  private static final String CITIES_DATA_FILE = "migration/mongo/cities.csv";

  @BeforeExecution
  public void beforeExecution(final MongoTemplate mongoTemplate) {
    this.ensureStateIndexes(mongoTemplate);
    this.ensureCityIndexes(mongoTemplate);
  }

  private void ensureStateIndexes(final MongoTemplate mongoTemplate) {
    final Index unqIdxStateCode =
        new Index().named("idx_unq_code").on("code", Sort.Direction.ASC).unique();
    mongoTemplate.indexOps(COLLECTION_STATE).ensureIndex(unqIdxStateCode);
    final Index unqIdxStateName =
        new Index().named("idx_unq_name").on("name", Sort.Direction.ASC).unique();
    mongoTemplate.indexOps(COLLECTION_STATE).ensureIndex(unqIdxStateName);
  }

  private void ensureCityIndexes(final MongoTemplate mongoTemplate) {
    final Index unqIdxCityCodeName =
        new Index()
            .named("idx_unq_code_name")
            .on("code", Sort.Direction.ASC)
            .on("name", Sort.Direction.ASC)
            .unique();
    mongoTemplate.indexOps(COLLECTION_CITY).ensureIndex(unqIdxCityCodeName);
    final Index unqIdxCityCode =
        new Index().named("idx_unq_code").on("code", Sort.Direction.ASC).unique();
    mongoTemplate.indexOps(COLLECTION_CITY).ensureIndex(unqIdxCityCode);
    final Index idxStateId = new Index().named("idx_state_id").on("state", Sort.Direction.ASC);
    mongoTemplate.indexOps(COLLECTION_CITY).ensureIndex(idxStateId);
  }

  @RollbackBeforeExecution
  public void rollbackBeforeExecution(final MongoTemplate mongoTemplate) {
    // Rollback logic for before execution
    mongoTemplate.dropCollection(COLLECTION_CITY);
    mongoTemplate.dropCollection(COLLECTION_STATE);
  }

  @Execution
  public void execution(
      final StateRepository stateRepository, final CityRepository cityRepository) {
    // ChangeSet execution logic
    final Map<String, State> states = this.populateStates(stateRepository);
    final Map<String, City> cities = this.populateCities(stateRepository, cityRepository, states);
  }

  private Map<String, State> populateStates(final StateRepository stateRepository) {
    final Resource resource = new ClassPathResource(STATES_DATA_FILE);
    try (InputStream inputStream = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      final Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
      return StreamSupport.stream(records.spliterator(), false)
          .map(
              record ->
                  State.of(
                      record.get("code"), record.get("name"), Boolean.valueOf(record.get("isUT"))))
          .peek(System.out::println)
          .map(stateRepository::save)
          .collect(Collectors.toMap(state -> state.getCode(), Function.identity()));
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private Map<String, City> populateCities(
      final StateRepository stateRepository,
      final CityRepository cityRepository,
      final Map<String, State> states) {
    final Resource resource = new ClassPathResource(CITIES_DATA_FILE);
    try (InputStream inputStream = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      final Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
      final Map<State, List<City>> batches =
          StreamSupport.stream(records.spliterator(), false)
              .map(
                  record ->
                      City.of(
                          states.get(record.get("state_code")),
                          record.get("city_code"),
                          record.get("city_name")))
              //              .peek(System.out::println)
              .collect(Collectors.groupingBy(City::getState));
      final Map<String, City> citiesMap =
          batches.entrySet().stream()
              .peek(System.out::println)
              .flatMap(
                  e -> {
                    if (CollectionUtils.isNotEmpty(e.getValue())) {
                      final List<City> cities = cityRepository.saveAll(e.getValue());
                      final State state = e.getKey();
                      state.addCities(cities);
                      stateRepository.save(state);
                      return cities.stream();
                    } else {
                      System.out.println("cities null for state: " + e.getValue());
                      return Stream.empty();
                    }
                  })
              .collect(Collectors.toMap(city -> city.getCode(), Function.identity()));

      return citiesMap;
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @RollbackExecution
  public void rollbackExecution(
      final StateRepository stateRepository, final CityRepository cityRepository) {
    // Rollback logic for execution
    cityRepository.deleteAll();
    stateRepository.deleteAll();
  }
}
