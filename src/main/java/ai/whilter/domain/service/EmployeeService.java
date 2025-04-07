package ai.whilter.domain.service;

import ai.whilter.adapter.repository.EmployeeRepository;
import ai.whilter.domain.model.Employee;
import ai.whilter.domain.model.dto.EmployeeCreationRQ;
import ai.whilter.domain.model.dto.EmployeeUpdationRQ;
import com.ksoot.problem.core.Problems;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  @Transactional(readOnly = true)
  public boolean doesEmployeeExist(final String code) {
    return this.employeeRepository.existsByCode(code);
  }

  @Transactional
  public Employee createEmployee(final EmployeeCreationRQ request) {
    final Employee employee =
        Employee.builder().code(request.code()).name(request.name()).dob(request.dob()).build();
    return this.employeeRepository.save(employee);
  }

  @Transactional(readOnly = true)
  public Employee getEmployeeById(final Long id) {
    return this.employeeRepository.findById(id).orElseThrow(Problems::notFound);
  }

  @Transactional(readOnly = true)
  public List<Employee> getAllEmployees() {
    return this.employeeRepository.findAll();
  }

  @Transactional
  public Employee updateEmployee(final Long id, final EmployeeUpdationRQ request) {
    final Employee employee = this.employeeRepository.findById(id).orElseThrow(Problems::notFound);

    Optional.ofNullable(request.name()).ifPresent(employee::setName);
    Optional.ofNullable(request.dob()).ifPresent(employee::setDob);

    return this.employeeRepository.save(employee);
  }

  @Transactional
  public void deleteEmployee(final Long id) {
    if (!this.employeeRepository.existsById(id)) {
      throw Problems.notFound();
    }
    this.employeeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public Page<Revision<Integer, Employee>> getEmployeeAuditHistory(
      final Long id, final Pageable pageRequest) {
    return this.employeeRepository.findRevisions(id, pageRequest);
  }
}
