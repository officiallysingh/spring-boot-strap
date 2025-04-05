package com.ksoot.adapter.controller;

import static com.ksoot.common.CommonConstants.DEFAULT_PAGE_SIZE;
import static com.ksoot.common.util.rest.ApiConstants.*;
import static com.ksoot.common.util.rest.ApiStatus.*;

import com.ksoot.common.jpa.RevisionRecord;
import com.ksoot.common.util.pagination.PaginatedResource;
import com.ksoot.common.util.rest.Api;
import com.ksoot.common.util.rest.response.APIResponse;
import com.ksoot.domain.model.Employee;
import com.ksoot.domain.model.dto.EmployeeCreationRQ;
import com.ksoot.domain.model.dto.EmployeeUpdationRQ;
import com.ksoot.domain.model.dto.EmployeeVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/employees")
@Tag(name = "Employee", description = "management APIs. Backed by PostgresDB")
public interface EmployeeApi extends Api {

  @Operation(
      operationId = "validate-employee-code",
      summary = "Validate Employee code. Check if a Employee with given code exists")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description = "Returns true or false if Employee exists or not respectively")
      })
  @GetMapping(path = "/{code}/validate", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Boolean> validateEmployeeCode(
      @Parameter(description = "Employee Code", required = true) @PathVariable(name = "code")
          final String code);

  @Operation(operationId = "create-employee", summary = "Creates a Employee")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = SC_201, description = "Employee created successfully"),
        @ApiResponse(
            responseCode = SC_400,
            description = "Bad request",
            content = @Content(examples = @ExampleObject(BAD_REQUEST_EXAMPLE_RESPONSE)))
      })
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<APIResponse<?>> createEmployee(
      @Parameter(description = "Create Employee request", required = true) @RequestBody @Valid
          final EmployeeCreationRQ request);

  @Operation(operationId = "get-employee-by-id", summary = "Gets a Employee by id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = SC_200, description = "Employee returned successfully"),
        @ApiResponse(
            responseCode = SC_404,
            description = "Requested Employee not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<EmployeeVM> getEmployee(
      @Parameter(description = "Employee Id", required = true, example = "1")
          @PathVariable(name = "id")
          final Long id);

  @Operation(operationId = "get-all-employees", summary = "Get all Employees")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Employees list returned successfully. Returns an empty list if no records found"),
        @ApiResponse(responseCode = "500", description = "Internal Server error")
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<EmployeeVM>> getAllEmployees();

  @Operation(operationId = "update-employee", summary = "Updates an Employee")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
        @ApiResponse(
            responseCode = SC_400,
            description = "Bad request",
            content = @Content(examples = @ExampleObject(BAD_REQUEST_EXAMPLE_RESPONSE))),
        @ApiResponse(
            responseCode = "404",
            description = "State not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @PatchMapping(
      path = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<APIResponse<?>> updateEmployee(
      @Parameter(description = "Employee Id or Code", required = true, example = "1")
          @PathVariable(name = "id")
          final Long id,
      @Parameter(description = "Update Employee request", required = true) @RequestBody @Valid
          final EmployeeUpdationRQ request);

  @Operation(operationId = "delete-employee", summary = "Deletes an Employee")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description = "Employee deleted successfully",
            content = @Content(examples = @ExampleObject(RECORD_DELETED_RESPONSE))),
        @ApiResponse(
            responseCode = SC_404,
            description = "Requested Employee not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<APIResponse<?>> deleteEmployee(
      @Parameter(description = "Employee Id", required = true, example = "1")
          @PathVariable(name = "id")
          final Long id);

  @Operation(operationId = "get-employees-audit-history", summary = "Get Employees Audit History")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description =
                "States Audit History returned successfully. Returns an empty Page if no records found")
      })
  @GetMapping(path = "/audit-history/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  PaginatedResource<RevisionRecord<Integer, Employee, EmployeeVM>> getEmployeesAuditHistory(
      @Parameter(description = "Employee Id", required = true, example = "1")
          @PathVariable(name = "id")
          final Long id,
      @ParameterObject @PageableDefault(size = DEFAULT_PAGE_SIZE) final Pageable pageRequest);
}
