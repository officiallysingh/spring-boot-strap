package com.ksoot.domain.service;

import static com.ksoot.TestConstants.SHOULD_THROW_SERVICE_EXCEPTION_MESSAGE;
import static com.ksoot.TestConstants.TEST_OBJECT_ID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ksoot.WebTestConfiguration;
import com.ksoot.adapter.repository.EmployeeRepository;
import com.ksoot.domain.model.Employee;
import com.ksoot.domain.model.dto.EmployeeCreationRQ;
import com.ksoot.domain.model.dto.EmployeeUpdationRQ;
import com.ksoot.problem.core.ApplicationProblem;
import com.ksoot.problem.spring.config.ProblemBeanRegistry;
import com.ksoot.problem.spring.config.ProblemMessageProviderConfig;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// @com.ksoot.common.spring.boot.annotation.Test
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = {WebTestConfiguration.class})
@ContextConfiguration(
    classes = {
      MessageSourceAutoConfiguration.class,
      ProblemBeanRegistry.class,
      ProblemMessageProviderConfig.class
    })
@TestPropertySource(
    locations = {
      "classpath:i18n/messages.properties",
      "classpath:i18n/errors.properties",
      "classpath:i18n/problems.properties"
    })
@TestPropertySource(properties = "detail.not.found=Requested resource not found")
public class EmployeeServiceTest {

  private static final String TEST_EMPLOYEE_CODE = "ABC123XYZ";
  private static final String EMPLOYEE_RESPONSE_SHOULD_NOT_BE_NULL_MESSAGE =
      "EmployeeResponse should not be null";
  private static final String EXPECTED_EMPLOYEE_RESPONSE_CODE_MESSAGE_TEMPLATE =
      "EmployeeResponse's '%s' should be '%s'";
  private static final String CODE = "code";

  @Mock private EmployeeRepository employeeRepository;

  // @Autowired
  // private MessageSource messageSource;
  // private MessageSource messageSource;

  @InjectMocks private EmployeeService employeeService;

  private EmployeeCreationRQ newEmployeeCreateRequest() {
    return EmployeeCreationRQ.builder()
        .code(TEST_EMPLOYEE_CODE)
        .name("Amit Dahiya")
        .dob(LocalDate.of(1980, 8, 17))
        .build();
  }

  private Employee newEmployee(final EmployeeCreationRQ request) {
    return Employee.builder().code(request.code()).name(request.name()).build();
  }

  private Employee newEmployee() {
    return Employee.builder()
        .code(TEST_EMPLOYEE_CODE)
        .name("Amit Dahiya")
        .dob(LocalDate.of(1980, 8, 17))
        .build();
  }

  @Test
  @DisplayName("Test Create Employee successfully")
  public void testCreateEmployee_Success() {
    final EmployeeCreationRQ request = this.newEmployeeCreateRequest();

    final Employee employee = this.newEmployee(request);
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    final Employee employeeResponse = this.employeeService.createEmployee(request);

    assertAll(
        "Verify Create Employee response",
        //        () -> assertNotNull(employeeResponse,
        // EMPLOYEE_RESPONSE_SHOULD_NOT_BE_NULL_MESSAGE),
        () ->
            assertEquals(
                employeeResponse.getCode(),
                request.code(),
                String.format(
                    EXPECTED_EMPLOYEE_RESPONSE_CODE_MESSAGE_TEMPLATE, CODE, request.code())));
  }

  @Test
  @DisplayName("Test Get Employee by Id or Code successfully")
  public void testGetEmployee_ByIdOrCode_Success() {
    final Employee employee = this.newEmployee();
    when(this.employeeRepository.findById(TEST_OBJECT_ID)).thenReturn(Optional.of(employee));

    final Employee employeeResponse = this.employeeService.getEmployeeById(TEST_OBJECT_ID);

    assertAll(
        "Verify Get Employee by Id or Code response",
        () -> assertNotNull(employeeResponse, EMPLOYEE_RESPONSE_SHOULD_NOT_BE_NULL_MESSAGE),
        () ->
            assertEquals(
                employeeResponse.getCode(),
                TEST_EMPLOYEE_CODE,
                String.format(
                    EXPECTED_EMPLOYEE_RESPONSE_CODE_MESSAGE_TEMPLATE, CODE, TEST_EMPLOYEE_CODE)));
  }

  //  @Disabled
  @Test
  @DisplayName("Test Get Employee by Id or Code failure due to non existent record")
  public void testGetEmployee_ById_NotFound_Failure() {

    //    when(this.messageSource.getMessage(any(MessageSourceResolvable.class),
    // any(Locale.class))).thenReturn("Some message");
    when(this.employeeRepository.findById(TEST_OBJECT_ID)).thenReturn(Optional.empty());

    final ApplicationProblem exception =
        assertThrows(
            ApplicationProblem.class,
            () -> this.employeeService.getEmployeeById(TEST_OBJECT_ID),
            SHOULD_THROW_SERVICE_EXCEPTION_MESSAGE);
  }

  @Test
  @DisplayName("Test validate Employee for given code success")
  public void testValidateEmployee_ByCode_Success() {
    when(this.employeeRepository.existsByCode(TEST_EMPLOYEE_CODE)).thenReturn(true);
    assertTrue(
        this.employeeService.doesEmployeeExist(TEST_EMPLOYEE_CODE),
        "Employee should exist with code: " + TEST_EMPLOYEE_CODE);
  }

  @Test
  @DisplayName("Test validate Employee for given code failure")
  public void testValidateEmployee_ByCode_Failure() {
    when(this.employeeRepository.existsByCode(TEST_EMPLOYEE_CODE)).thenReturn(false);
    assertFalse(
        this.employeeService.doesEmployeeExist(TEST_EMPLOYEE_CODE),
        "Employee should not exist with code: " + TEST_EMPLOYEE_CODE);
  }

  @Test
  @DisplayName("Test update Employee successfully")
  public void testUpdateEmployee_Success() {
    final EmployeeUpdationRQ request =
        EmployeeUpdationRQ.builder()
            .name("Another Name")
            .dob(LocalDate.now().minusYears(20))
            .build();

    final Employee existingEmployee = this.newEmployee();
    when(this.employeeRepository.findById(TEST_OBJECT_ID))
        .thenReturn(Optional.of(existingEmployee));

    final Employee updatedEmployee =
        Employee.builder().code(TEST_EMPLOYEE_CODE).name(request.name()).dob(request.dob()).build();
    when(this.employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

    final Employee employeeResponse = this.employeeService.updateEmployee(TEST_OBJECT_ID, request);
    this.assertUpdateEmployeeResponse(request, employeeResponse);
  }

  private void assertUpdateEmployeeResponse(
      final EmployeeUpdationRQ request, final Employee response) {
    assertAll(
        "Verify Update Employee response",
        () -> assertNotNull(response, EMPLOYEE_RESPONSE_SHOULD_NOT_BE_NULL_MESSAGE),
        () ->
            assertEquals(
                response.getName(),
                request.name(),
                String.format(
                    EXPECTED_EMPLOYEE_RESPONSE_CODE_MESSAGE_TEMPLATE, "name", request.name())),
        () ->
            assertEquals(
                response.getDob(),
                request.dob(),
                String.format(
                    EXPECTED_EMPLOYEE_RESPONSE_CODE_MESSAGE_TEMPLATE, "dob", request.dob())));
  }

  //  @Disabled
  @Test
  @DisplayName("Test update Employee failure for non existent Employee record with given Id")
  public void testUpdateEmployee_NotFound_Failure() {
    final EmployeeUpdationRQ request =
        EmployeeUpdationRQ.builder().name("Yet another name").build();

    when(this.employeeRepository.findById(TEST_OBJECT_ID)).thenReturn(Optional.empty());
    final ApplicationProblem exception =
        assertThrows(
            ApplicationProblem.class,
            () -> this.employeeService.updateEmployee(TEST_OBJECT_ID, request),
            SHOULD_THROW_SERVICE_EXCEPTION_MESSAGE);
    assertAll(
        "Verify Exception attributes",
        () -> assertNotNull(exception, "exception should not be null"),
        () ->
            assertEquals(
                exception.getStatus(),
                HttpStatus.NOT_FOUND,
                "Expected exception HttpStatus is " + HttpStatus.NOT_FOUND));
  }

  @Test
  @DisplayName("Test get all Employees successfully")
  public void testGetAllEmployees_Success() {
    final Employee employee = this.newEmployee();
    when(this.employeeRepository.findAll()).thenReturn(List.of(employee, employee));
    final List<Employee> response = this.employeeService.getAllEmployees();

    assertAll(
        "Verify Get All Employees response",
        () -> assertNotNull(response, "Get all Employees response should not be null"),
        () -> assertEquals(response.size(), 2, "Expected number of records is 2"));
  }
}
