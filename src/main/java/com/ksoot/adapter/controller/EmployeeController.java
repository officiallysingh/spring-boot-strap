package com.ksoot.adapter.controller;

import static com.ksoot.common.CommonErrorKeys.EMPTY_UPDATE_REQUEST;
import static com.ksoot.domain.mapper.SampleMappers.EMPLOYEE_AUDIT_PAGE_TRANSFORMER;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.ksoot.common.jpa.RevisionRecord;
import com.ksoot.common.util.GeneralMessageResolver;
import com.ksoot.common.util.pagination.PaginatedResource;
import com.ksoot.common.util.pagination.PaginatedResourceAssembler;
import com.ksoot.common.util.rest.response.APIResponse;
import com.ksoot.domain.mapper.SampleMappers;
import com.ksoot.domain.model.Employee;
import com.ksoot.domain.model.dto.EmployeeCreationRQ;
import com.ksoot.domain.model.dto.EmployeeUpdationRQ;
import com.ksoot.domain.model.dto.EmployeeVM;
import com.ksoot.domain.service.EmployeeService;
import com.ksoot.problem.core.Problems;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class EmployeeController implements EmployeeApi {

  private final EmployeeService employeeService;

  @Override
  public ResponseEntity<Boolean> validateEmployeeCode(final String code) {
    return ResponseEntity.ok(this.employeeService.doesEmployeeExist(code));
  }

  @Override
  public ResponseEntity<APIResponse<?>> createEmployee(final EmployeeCreationRQ request) {
    final Long id = this.employeeService.createEmployee(request).getId();
    return ResponseEntity.created(
            linkTo(methodOn(EmployeeController.class).getEmployee(id)).withSelfRel().toUri())
        .body(APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_CREATED));
  }

  @Override
  public ResponseEntity<EmployeeVM> getEmployee(final Long id) {
    return ResponseEntity.ok(
        SampleMappers.INSTANCE.toEmployeeVM(this.employeeService.getEmployeeById(id)));
  }

  @Override
  public ResponseEntity<APIResponse<?>> updateEmployee(
      final Long id, final EmployeeUpdationRQ request) {
    if (request.isEmpty()) {
      throw Problems.newInstance(EMPTY_UPDATE_REQUEST).throwAble(HttpStatus.BAD_REQUEST);
    }
    final Employee employee = this.employeeService.updateEmployee(id, request);
    return ResponseEntity.ok()
        .location(
            linkTo(methodOn(EmployeeController.class).getEmployee(employee.getId()))
                .withSelfRel()
                .toUri())
        .body(APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_UPDATED));
  }

  @Override
  public ResponseEntity<List<EmployeeVM>> getAllEmployees() {
    return ResponseEntity.ok(
        this.employeeService.getAllEmployees().stream()
            .map(SampleMappers.INSTANCE::toEmployeeVM)
            .toList());
  }

  @Override
  public ResponseEntity<APIResponse<?>> deleteEmployee(final Long id) {
    this.employeeService.deleteEmployee(id);
    return ResponseEntity.ok(
        APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_DELETED));
  }

  @Override
  public PaginatedResource<RevisionRecord<Integer, Employee, EmployeeVM>> getEmployeesAuditHistory(
      final Long id, final Pageable pageRequest) {
    Page<Revision<Integer, Employee>> auditHistoryPage =
        this.employeeService.getEmployeeAuditHistory(id, pageRequest);
    return PaginatedResourceAssembler.assemble(auditHistoryPage, EMPLOYEE_AUDIT_PAGE_TRANSFORMER);
  }
}
