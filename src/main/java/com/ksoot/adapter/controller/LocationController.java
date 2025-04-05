package com.ksoot.adapter.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.ksoot.common.CommonErrorKeys;
import com.ksoot.common.util.GeneralMessageResolver;
import com.ksoot.common.util.rest.response.APIResponse;
import com.ksoot.domain.mapper.SampleMappers;
import com.ksoot.domain.model.City;
import com.ksoot.domain.model.State;
import com.ksoot.domain.model.dto.*;
import com.ksoot.domain.service.LocationService;
import com.ksoot.problem.core.Problems;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class LocationController implements LocationApi {

  private final LocationService locationService;

  // -------- States ----------
  @Override
  public ResponseEntity<Boolean> doesStateExists(final String code) {
    return ResponseEntity.ok(this.locationService.doesStateExists(code));
  }

  @Override
  public ResponseEntity<APIResponse<?>> createState(final StateCreationRQ request) {
    final State state = this.locationService.createState(request);
    return ResponseEntity.created(
            linkTo(methodOn(LocationController.class).getStateById(state.getId(), null))
                .withSelfRel()
                .toUri())
        .body(APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_CREATED));
  }

  @Override
  public ResponseEntity<StateVM> getStateById(final String id, final String expand) {
    return ResponseEntity.ok(
        SampleMappers.INSTANCE.toStateViewModel(this.locationService.getStateById(id), expand));
  }

  @Override
  public ResponseEntity<StateVM> getStateByCode(final String code, final String expand) {
    return ResponseEntity.ok(
        SampleMappers.INSTANCE.toStateViewModel(this.locationService.getStateByCode(code), expand));
  }

  @Override
  public ResponseEntity<List<StateVM>> getAllStates(final String expand) {
    return ResponseEntity.ok(
        this.locationService.getAllStates().stream()
            .map(state -> SampleMappers.INSTANCE.toStateViewModel(state, expand))
            .toList());
  }

  @Override
  public ResponseEntity<List<Pair<String, String>>> getAllStateListItems() {
    return ResponseEntity.ok(
        this.locationService.getAllStates().stream().map(State::listItem).toList());
  }

  @Override
  public ResponseEntity<APIResponse<?>> updateState(
      final String id, final StateUpdationRQ request) {
    if (request.isEmpty()) {
      throw Problems.newInstance(CommonErrorKeys.EMPTY_UPDATE_REQUEST)
          .throwAble(HttpStatus.BAD_REQUEST);
    }
    this.locationService.updateState(id, request);
    return ResponseEntity.ok()
        .location(
            linkTo(methodOn(LocationController.class).getStateById(id, null)).withSelfRel().toUri())
        .body(APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_UPDATED));
  }

  @Override
  public ResponseEntity<APIResponse<?>> deleteState(final String id) {
    this.locationService.deleteState(id);
    return ResponseEntity.ok(
        APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_DELETED));
  }

  // -------- Cities ----------
  @Override
  public ResponseEntity<Boolean> doesCityExists(final String code) {
    return ResponseEntity.ok(this.locationService.doesCityExists(code));
  }

  @Override
  public ResponseEntity<APIResponse<?>> createCity(
      final String stateId, final CityCreationRQ request) {
    final City city = this.locationService.createCity(stateId, request);
    return ResponseEntity.created(
            linkTo(methodOn(LocationController.class).getCityById(city.getId(), null))
                .withSelfRel()
                .toUri())
        .body(APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_CREATED));
  }

  @Override
  public ResponseEntity<CityVM> getCityById(final String id, final List<String> expand) {
    return ResponseEntity.ok(
        SampleMappers.INSTANCE.toCityViewModel(this.locationService.getCityById(id), expand));
  }

  @Override
  public ResponseEntity<CityVM> getCityByCode(final String code, final List<String> expand) {
    return ResponseEntity.ok(
        SampleMappers.INSTANCE.toCityViewModel(this.locationService.getCityByCode(code), expand));
  }

  @Override
  public ResponseEntity<List<CityVM>> getAllCitiesByStateId(
      final String stateId, final List<String> expand) {
    return ResponseEntity.ok(
        this.locationService.findAllCitiesByStateId(stateId).stream()
            .map(city -> SampleMappers.INSTANCE.toCityViewModel(city, expand))
            .toList());
  }

  @Override
  public ResponseEntity<List<CityVM>> getAllCities(final List<String> expand) {
    return ResponseEntity.ok(
        this.locationService.getAllCities().stream()
            .map(city -> SampleMappers.INSTANCE.toCityViewModel(city, expand))
            .toList());
  }

  @Override
  public ResponseEntity<List<Pair<String, String>>> getAllCitiesListItems() {
    return ResponseEntity.ok(
        this.locationService.getAllCities().stream().map(City::listItem).toList());
  }

  @Override
  public ResponseEntity<APIResponse<?>> updateCity(final String id, final CityUpdationRQ request) {
    if (request.isEmpty()) {
      throw Problems.newInstance(CommonErrorKeys.EMPTY_UPDATE_REQUEST)
          .throwAble(HttpStatus.BAD_REQUEST);
    }
    this.locationService.updateCity(id, request);
    return ResponseEntity.ok()
        .location(
            linkTo(methodOn(LocationController.class).getCityById(id, null)).withSelfRel().toUri())
        .body(APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_UPDATED));
  }

  @Override
  public ResponseEntity<APIResponse<?>> deleteCity(final String id) {
    this.locationService.deleteCity(id);
    return ResponseEntity.ok(
        APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_DELETED));
  }
}
