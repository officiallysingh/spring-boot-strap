package ai.whilter.domain.service;

import static ai.whilter.domain.mapper.SampleMappers.CITY_BY_NAME_COMPARATOR;
import static ai.whilter.domain.mapper.SampleMappers.STATE_BY_NAME_COMPARATOR;

import ai.whilter.adapter.repository.CityRepository;
import ai.whilter.adapter.repository.StateRepository;
import ai.whilter.domain.model.City;
import ai.whilter.domain.model.State;
import ai.whilter.domain.model.dto.CityCreationRQ;
import ai.whilter.domain.model.dto.CityUpdationRQ;
import ai.whilter.domain.model.dto.StateCreationRQ;
import ai.whilter.domain.model.dto.StateUpdationRQ;
import com.ksoot.problem.core.Problems;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

  private final StateRepository stateRepository;

  private final CityRepository cityRepository;

  @Transactional(readOnly = true)
  public Boolean doesStateExists(final String code) {
    return this.stateRepository.existsByCode(code);
  }

  @Transactional
  @Override
  public State createState(final StateCreationRQ request) {
    final State state =
        State.builder().code(request.code()).name(request.name()).isUT(request.isUT()).build();
    return this.stateRepository.save(state);
  }

  @Transactional(readOnly = true)
  @Override
  public State getStateById(final String id) {
    return this.stateRepository.findById(id).orElseThrow(Problems::notFound);
  }

  @Transactional(readOnly = true)
  @Override
  public State getStateByCode(final String code) {
    return this.stateRepository.findByCode(code).orElseThrow(Problems::notFound);
  }

  @Transactional(readOnly = true)
  @Override
  public List<State> getAllStates() {
    return this.stateRepository.findAll().stream().sorted(STATE_BY_NAME_COMPARATOR).toList();
  }

  @Transactional
  @Override
  public State updateState(final String id, final StateUpdationRQ request) {
    final State state = this.getStateById(id);

    Optional.ofNullable(request.code()).ifPresent(state::setCode);
    Optional.ofNullable(request.name()).ifPresent(state::setName);
    Optional.ofNullable(request.isUT()).ifPresent(state::setIsUT);

    return this.stateRepository.save(state);
  }

  @Transactional
  @Override
  public void deleteState(final String id) {
    final State state = this.getStateById(id);
    this.stateRepository.deleteById(id);
    this.cityRepository.deleteAll(state.getCities());
  }

  // -------- Cities ----------
  @Transactional(readOnly = true)
  @Override
  public Boolean doesCityExists(final String code) {
    return this.cityRepository.existsByCode(code);
  }

  @Transactional
  @Override
  public City createCity(final String stateId, final CityCreationRQ request) {
    final State state = this.stateRepository.findById(stateId).orElseThrow(Problems::notFound);
    City city = City.builder().state(state).code(request.code()).name(request.name()).build();
    city = this.cityRepository.save(city);
    state.addCity(city);
    this.stateRepository.save(state);
    return city;
  }

  @Transactional(readOnly = true)
  @Override
  public City getCityById(final String id) {
    return this.cityRepository.findById(id).orElseThrow(Problems::notFound);
  }

  @Transactional(readOnly = true)
  @Override
  public City getCityByCode(final String code) {
    return this.cityRepository.findByCode(code).orElseThrow(Problems::notFound);
  }

  @Transactional(readOnly = true)
  @Override
  public List<City> findAllCitiesByStateId(final String stateId) {
    return this.cityRepository.findAllByStateId(stateId).stream()
        .sorted(CITY_BY_NAME_COMPARATOR)
        .toList();
  }

  @Transactional(readOnly = true)
  @Override
  public List<City> getAllCities() {
    return this.cityRepository.findAll().stream().sorted(CITY_BY_NAME_COMPARATOR).toList();
  }

  @Transactional
  @Override
  public City updateCity(final String id, final CityUpdationRQ request) {
    final City city = this.getCityById(id);

    Optional.ofNullable(request.code()).ifPresent(city::setCode);
    Optional.ofNullable(request.name()).ifPresent(city::setName);
    if (StringUtils.isNotBlank(request.stateId())
        && !city.getState().getId().equals(request.stateId())) {
      State oldState = city.getState();

      State newState = this.getStateById(request.stateId());
      city.setState(newState);

      oldState.removeCity(city);
      newState.addCity(city);
      this.stateRepository.save(oldState);
      this.stateRepository.save(newState);
    }
    this.cityRepository.save(city);
    return city;
  }

  @Transactional
  @Override
  public void deleteCity(final String id) {
    final City city = this.getCityById(id);
    final State state = city.getState();
    state.removeCity(city);
    this.stateRepository.save(state);
    this.cityRepository.deleteById(id);
  }
}
