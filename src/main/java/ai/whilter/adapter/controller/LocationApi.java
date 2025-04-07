package ai.whilter.adapter.controller;

import static ai.whilter.common.CommonConstants.HEADER_EXPAND;
import static ai.whilter.common.util.rest.ApiConstants.*;
import static ai.whilter.common.util.rest.ApiStatus.*;

import ai.whilter.common.util.rest.Api;
import ai.whilter.common.util.rest.response.APIResponse;
import ai.whilter.domain.model.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/location")
@Tag(name = "Location", description = "management APIs. Backed by MongoDB")
public interface LocationApi extends Api {

  // -------- States ----------
  @Operation(
      operationId = "state-exists-by-code",
      summary = "Check if a State with given code exists")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description = "Returns true or false if State exists or not respectively")
      })
  @GetMapping(path = "/states/code/{code}/exists", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Boolean> doesStateExists(
      @Parameter(description = "State Code", required = true, example = "HR")
          @PathVariable(name = "code")
          final String code);

  @Operation(operationId = "create-state", summary = "Creates a State")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_201,
            description = "State created successfully",
            content = @Content(examples = @ExampleObject(RECORD_CREATED_RESPONSE))),
        @ApiResponse(
            responseCode = SC_400,
            description = "Bad request",
            content = @Content(examples = @ExampleObject(BAD_REQUEST_EXAMPLE_RESPONSE)))
      })
  @PostMapping(
      path = "/states",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<APIResponse<?>> createState(
      @Parameter(description = "Create State request", required = true) @RequestBody @Valid
          final StateCreationRQ request);

  @Operation(operationId = "get-state-by-id", summary = "Gets a State by Id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = SC_200, description = "State returned successfully"),
        @ApiResponse(
            responseCode = SC_404,
            description = "Requested State not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @GetMapping(path = "/states/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<StateVM> getStateById(
      @Parameter(description = "State Id", required = true, example = "6558c30160463a1fee00c7dc")
          @PathVariable(name = "id")
          final String id,
      @Parameter(description = "<code>cities</code> to include Cities. Not included by default")
          @RequestHeader(name = HEADER_EXPAND, required = false)
          final String expand);

  @Operation(operationId = "get-state-by-code", summary = "Gets a State by code")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = SC_200, description = "State returned successfully"),
        @ApiResponse(
            responseCode = SC_404,
            description = "Requested State not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @GetMapping(path = "/states/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<StateVM> getStateByCode(
      @Parameter(description = "State Code", required = true, example = "HR")
          @PathVariable(name = "code")
          final String code,
      @Parameter(description = "<code>cities</code> to include Cities. Not included by default")
          @RequestHeader(name = HEADER_EXPAND, required = false)
          final String expand);

  @Operation(operationId = "get-all-states", summary = "Get all States")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description =
                "States list returned successfully. Returns an empty list if no records found")
      })
  @GetMapping(path = "/states", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<StateVM>> getAllStates(
      @Parameter(description = "<code>cities</code> to include Cities. Not included by default")
          @RequestHeader(name = HEADER_EXPAND, required = false)
          final String expand);

  @Operation(operationId = "get-state-list-items", summary = "Get all State List items")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description =
                "State list items returned successfully. Returns an empty list if no records found")
      })
  @GetMapping(path = "/states/list-items", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<Pair<String, String>>> getAllStateListItems();

  @Operation(operationId = "update-state", summary = "Updates a State")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description = "State updated successfully",
            content = @Content(examples = @ExampleObject(RECORD_UPDATED_RESPONSE))),
        @ApiResponse(
            responseCode = SC_400,
            description = "Bad request",
            content = @Content(examples = @ExampleObject(BAD_REQUEST_EXAMPLE_RESPONSE))),
        @ApiResponse(
            responseCode = SC_404,
            description = "Requested State not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @PatchMapping(
      path = "/states/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<APIResponse<?>> updateState(
      @Parameter(description = "State Id", required = true, example = "6558c30160463a1fee00c7dc")
          @PathVariable(name = "id")
          final String id,
      @Parameter(description = "Update State request", required = true) @RequestBody @Valid
          final StateUpdationRQ request);

  @Operation(operationId = "delete-state", summary = "Deletes a State")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description = "State deleted successfully",
            content = @Content(examples = @ExampleObject(RECORD_DELETED_RESPONSE))),
        @ApiResponse(
            responseCode = SC_404,
            description = "Requested State not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @DeleteMapping(path = "/states/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<APIResponse<?>> deleteState(
      @Parameter(description = "State Id", required = true, example = "6558c30160463a1fee00c7dc")
          @PathVariable(name = "id")
          final String id);

  // -------- Cities ----------
  @Operation(
      operationId = "city-exists-by-code",
      summary = "Check if a City with given code exists")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description = "Returns true or false if City exists or not respectively")
      })
  @GetMapping(path = "/cities/code/{code}/exists", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Boolean> doesCityExists(
      @Parameter(description = "City Code", required = true, example = "86")
          @PathVariable(name = "code")
          final String code);

  @Operation(operationId = "create-city", summary = "Creates a City")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_201,
            description = "City created successfully",
            content = @Content(examples = @ExampleObject(RECORD_CREATED_RESPONSE))),
        @ApiResponse(
            responseCode = SC_400,
            description = "Bad request",
            content = @Content(examples = @ExampleObject(BAD_REQUEST_EXAMPLE_RESPONSE)))
      })
  @PostMapping(
      path = "/states/{id}/cities",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<APIResponse<?>> createCity(
      @Parameter(description = "State Id", required = true, example = "6558c30160463a1fee00c7dc")
          @PathVariable(name = "id")
          final String stateId,
      @Parameter(description = "Create City request", required = true) @RequestBody @Valid
          final CityCreationRQ request);

  @Operation(operationId = "get-city-by-id", summary = "Gets a city by Id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = SC_200, description = "City returned successfully"),
        @ApiResponse(
            responseCode = SC_404,
            description = "Requested Fuel type not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @GetMapping(path = "/cities/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<CityVM> getCityById(
      @Parameter(description = "City Id", required = true, example = "6558c30160463a1fee00c7dc")
          @PathVariable(name = "id")
          final String id,
      @Parameter(
              description =
                  "<ul>"
                      + "<li><code>state</code> to include State</li>"
                      + "<li><code>areas</code> to include Areas</li>"
                      + "<li>None is included by default</li>"
                      + "</ul>")
          @RequestHeader(name = HEADER_EXPAND, required = false)
          final List<String> expand);

  @Operation(operationId = "get-city-by-code", summary = "Gets a City by code")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = SC_200, description = "City returned successfully"),
        @ApiResponse(
            responseCode = SC_404,
            description = "Requested City not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @GetMapping(path = "/cities/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<CityVM> getCityByCode(
      @Parameter(description = "City Code", required = true, example = "86")
          @PathVariable(name = "code")
          final String code,
      @Parameter(
              description =
                  "<ul>"
                      + "<li><code>state</code> to include State</li>"
                      + "<li><code>areas</code> to include Areas</li>"
                      + "<li>None is included by default</li>"
                      + "</ul>")
          @RequestHeader(name = HEADER_EXPAND, required = false)
          final List<String> expand);

  @Operation(operationId = "get-all-cities-by-state-id", summary = "Get all Cities by State Id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description =
                "Cities list returned successfully. Returns an empty list if no records found")
      })
  @GetMapping(path = "/states/{id}/cities", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<CityVM>> getAllCitiesByStateId(
      @Parameter(description = "State Id", required = true, example = "6558c30160463a1fee00c7dc")
          @PathVariable(name = "id")
          final String stateId,
      @Parameter(
              description =
                  "<ul>"
                      + "<li><code>state</code> to include State</li>"
                      + "<li><code>areas</code> to include Areas</li>"
                      + "<li>None is included by default</li>"
                      + "</ul>")
          @RequestHeader(name = HEADER_EXPAND, required = false)
          final List<String> expand);

  @Operation(operationId = "get-all-cities", summary = "Get all Cities")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description =
                "Cities list returned successfully. Returns an empty list if no records found")
      })
  @GetMapping(path = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<CityVM>> getAllCities(
      @Parameter(
              description =
                  "<ul>"
                      + "<li><code>state</code> to include State</li>"
                      + "<li><code>areas</code> to include Areas</li>"
                      + "<li>None is included by default</li>"
                      + "</ul>")
          @RequestHeader(name = HEADER_EXPAND, required = false)
          final List<String> expand);

  @Operation(operationId = "get-city-list-items", summary = "Get all City List items")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description =
                "City list items returned successfully. Returns an empty list if no records found")
      })
  @GetMapping(path = "/cities/list-items", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<Pair<String, String>>> getAllCitiesListItems();

  @Operation(operationId = "update-city", summary = "Updates a City")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description = "City updated successfully",
            content = @Content(examples = @ExampleObject(RECORD_UPDATED_RESPONSE))),
        @ApiResponse(
            responseCode = SC_400,
            description = "Bad request",
            content = @Content(examples = @ExampleObject(BAD_REQUEST_EXAMPLE_RESPONSE))),
        @ApiResponse(
            responseCode = SC_404,
            description = "Requested City not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @PatchMapping(
      path = "/cities/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<APIResponse<?>> updateCity(
      @Parameter(description = "City Id", required = true, example = "6558c30160463a1fee00c7dc")
          @PathVariable(name = "id")
          final String id,
      @Parameter(description = "Update City request", required = true) @RequestBody @Valid
          final CityUpdationRQ request);

  @Operation(operationId = "delete-city", summary = "Deletes a City")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description = "City deleted successfully",
            content = @Content(examples = @ExampleObject(RECORD_DELETED_RESPONSE))),
        @ApiResponse(
            responseCode = SC_404,
            description = "Requested City not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @DeleteMapping(path = "/cities/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<APIResponse<?>> deleteCity(
      @Parameter(description = "City Id", required = true, example = "6558c30160463a1fee00c7dc")
          @PathVariable(name = "id")
          final String id);
}
