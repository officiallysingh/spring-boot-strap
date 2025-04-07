package ai.whilter.domain.service;

import ai.whilter.domain.model.City;
import ai.whilter.domain.model.State;
import ai.whilter.domain.model.dto.CityCreationRQ;
import ai.whilter.domain.model.dto.CityUpdationRQ;
import ai.whilter.domain.model.dto.StateCreationRQ;
import ai.whilter.domain.model.dto.StateUpdationRQ;
import java.util.List;

public interface LocationService {

  Boolean doesStateExists(String code);

  State createState(StateCreationRQ request);

  State getStateById(String id);

  State getStateByCode(String code);

  List<State> getAllStates();

  State updateState(String id, StateUpdationRQ request);

  void deleteState(String id);

  // -------- Cities ----------
  Boolean doesCityExists(String code);

  City createCity(String stateId, CityCreationRQ request);

  City getCityById(String id);

  City getCityByCode(String code);

  List<City> findAllCitiesByStateId(String stateId);

  List<City> getAllCities();

  City updateCity(String id, CityUpdationRQ request);

  void deleteCity(String id);
}
