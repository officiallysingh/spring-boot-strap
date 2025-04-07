package ai.whilter.adapter.controller;

import static ai.whilter.TestConstants.FORWARD_SLASH;
import static ai.whilter.TestConstants.HEADER_LOCATION;
import static ai.whilter.TestConstants.TEST_OBJECT_ID;
import static ai.whilter.TestConstants.THREE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ai.whilter.WebTestConfiguration;
import ai.whilter.domain.model.Employee;
import ai.whilter.domain.model.dto.EmployeeCreationRQ;
import ai.whilter.domain.model.dto.EmployeeUpdationRQ;
import ai.whilter.domain.service.EmployeeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksoot.problem.core.Problems;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

// @Disabled
@WebMvcTest(EmployeeController.class)
@TestPropertySource(
    // Specify the location of your YAML file
    locations = "classpath:config/application-test.yml",
    // Activate the "test" profile
    properties = "spring.profiles.active=test")
@ImportAutoConfiguration(classes = {WebTestConfiguration.class})
class EmployeeControllerTest {

  private static final String EMPLOYEE_ENDPOINT_PATH = "/v1/employees";
  private static final String TEST_EMPLOYEE_CODE = "ABC123XYZ";
  private static final String VALIDATE_PATH_PART = "/validate";

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private Environment environment;

  @MockitoBean private EmployeeService employeeService;

  //  @BeforeAll
  //  public static void init() {
  //    System.out.println("Active profile --->>" +
  // DefaultBeanRegistry.getBean(Environment.class).getProperty("spring.profiles.active"));
  //    System.out.println("init.");
  //  }

  private EmployeeCreationRQ newEmployeeCreateRequest(
      final String code, final String name, final LocalDate dob) {
    return EmployeeCreationRQ.builder().code(code).name(name).dob(dob).build();
  }

  private Employee newEmployee() {
    return Employee.builder().code(TEST_EMPLOYEE_CODE).name("Amit Dahiya").build();
  }

  @Test
  @DisplayName("Test Create Employee successfully")
  public void testCreateEmployee_Success() throws Exception {
    System.out.println(
        "Active profile --->>" + this.environment.getProperty("spring.profiles.active"));
    final EmployeeCreationRQ request =
        this.newEmployeeCreateRequest(
            TEST_EMPLOYEE_CODE, "Rajesh Kumar", LocalDate.of(1983, 10, 25));

    final Employee response = this.newEmployee();
    when(this.employeeService.createEmployee(request)).thenReturn(response);

    this.mockMvc
        .perform(
            post(EMPLOYEE_ENDPOINT_PATH)
                .contentType(APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(header().exists(HEADER_LOCATION));
  }

  @Test
  @DisplayName("Test Create Employee failure due to null code")
  public void testCreateEmployee_WithNullCode_Failure() throws Exception {
    final EmployeeCreationRQ request =
        this.newEmployeeCreateRequest(null, "Rajesh Kumar", LocalDate.of(1983, 10, 25));

    this.mockMvc
        .perform(
            post(EMPLOYEE_ENDPOINT_PATH)
                .contentType(APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
    // .andExpect(jsonPath(JSON_PATH_ERROR_CODE).value(ERROR_CODE_BAD_REQUEST))
    // .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value("code: must not be empty"));
  }

  @Test
  @DisplayName("Test Create Employee failure due to null name")
  public void testCreateEmployee_WithNullName_Failure() throws Exception {
    final EmployeeCreationRQ request =
        this.newEmployeeCreateRequest(TEST_EMPLOYEE_CODE, null, LocalDate.of(1983, 10, 25));

    this.mockMvc
        .perform(
            post(EMPLOYEE_ENDPOINT_PATH)
                .contentType(APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
    // .andExpect(jsonPath(JSON_PATH_ERROR_CODE).value(ERROR_CODE_BAD_REQUEST))
    // .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value("name: must not be empty"));
  }

  @Test
  @DisplayName("Test Create Employee failure due to null DOB")
  public void testCreateEmployee_WithNullDOB_Failure() throws Exception {
    final EmployeeCreationRQ request =
        this.newEmployeeCreateRequest(TEST_EMPLOYEE_CODE, "Rajesh Kumar", null);

    this.mockMvc
        .perform(
            post(EMPLOYEE_ENDPOINT_PATH)
                .contentType(APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
    // .andExpect(jsonPath(JSON_PATH_ERROR_CODE).value(ERROR_CODE_BAD_REQUEST))
    // .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value("gstCode: must not be
    // empty"));
  }

  @Test
  @DisplayName("Test Create Employee failure invalid Employee Name format")
  public void testCreateEmployee_WithInvalidNameFormat_Failure() throws Exception {
    final EmployeeCreationRQ request =
        this.newEmployeeCreateRequest(TEST_EMPLOYEE_CODE, "Rajesh Kumar 3_", null);

    this.mockMvc
        .perform(
            post(EMPLOYEE_ENDPOINT_PATH)
                .contentType(APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
    // .andExpect(jsonPath(JSON_PATH_ERROR_CODE).value(ERROR_CODE_BAD_REQUEST))
    // .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value("gstCode: must not be
    // empty"));
  }

  @Test
  @DisplayName("Test Get Employee by Id or Code successfully")
  public void testGetEmployee_ByIdSuccess() throws Exception {
    final Employee response = this.newEmployee();
    when(this.employeeService.getEmployeeById(TEST_OBJECT_ID)).thenReturn(response);
    this.mockMvc
        .perform(
            get(EMPLOYEE_ENDPOINT_PATH + FORWARD_SLASH + TEST_OBJECT_ID)
                .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
    // .andExpect(jsonPath("$.id").value(TEST_OBJECT_ID));
  }

  @Test
  @DisplayName("Test Get Employee by Id or Code failure due to non existent record")
  public void testGetEmployee_ById_NotFound_Failure() throws Exception {
    when(this.employeeService.getEmployeeById(TEST_OBJECT_ID)).thenThrow(Problems.notFound());
    this.mockMvc
        .perform(
            get(EMPLOYEE_ENDPOINT_PATH + FORWARD_SLASH + TEST_OBJECT_ID)
                .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Test validate Employee for given code success")
  public void testValidateEmployee_ByCode_Success() throws Exception {
    when(this.employeeService.doesEmployeeExist(TEST_EMPLOYEE_CODE)).thenReturn(true);

    this.mockMvc
        .perform(
            get(EMPLOYEE_ENDPOINT_PATH + FORWARD_SLASH + TEST_EMPLOYEE_CODE + VALIDATE_PATH_PART)
                .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString()
        .contentEquals("true");
  }

  @Test
  @DisplayName("Test validate Employee for given code failure")
  public void testValidateEmployee_ByCode_Failure() throws Exception {
    when(this.employeeService.doesEmployeeExist(TEST_EMPLOYEE_CODE)).thenReturn(true);

    this.mockMvc
        .perform(
            get(EMPLOYEE_ENDPOINT_PATH + FORWARD_SLASH + TEST_EMPLOYEE_CODE + VALIDATE_PATH_PART)
                .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString()
        .contentEquals("false");
  }

  @Test
  public void testUpdateEmployee_Success() throws Exception {
    final EmployeeUpdationRQ request =
        EmployeeUpdationRQ.builder()
            .name("Another name")
            .dob(LocalDate.now().minusYears(25))
            .build();

    final Employee response = this.newEmployee();
    when(this.employeeService.updateEmployee(TEST_OBJECT_ID, request)).thenReturn(response);

    this.mockMvc
        .perform(
            patch(EMPLOYEE_ENDPOINT_PATH + FORWARD_SLASH + TEST_OBJECT_ID)
                .contentType(APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(header().exists(HEADER_LOCATION));
  }

  @Test
  public void testUpdateEmployee_ValidationErrors_Failure() throws Exception {
    // Make empty request
    this.mockMvc
        .perform(
            patch(EMPLOYEE_ENDPOINT_PATH + FORWARD_SLASH + TEST_OBJECT_ID)
                .contentType(APPLICATION_JSON_VALUE)
                .content(
                    this.objectMapper.writeValueAsString(EmployeeUpdationRQ.builder().build())))
        .andExpect(status().isBadRequest());

    final EmployeeUpdationRQ request =
        EmployeeUpdationRQ.builder().name("New name 234").dob(LocalDate.now().plusDays(1)).build();

    this.mockMvc
        .perform(
            patch(EMPLOYEE_ENDPOINT_PATH + FORWARD_SLASH + TEST_OBJECT_ID)
                .contentType(APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
    // .andExpect(jsonPath(JSON_PATH_ERROR_CODE).value(ERROR_CODE_BAD_REQUEST));
  }

  @Test
  public void testUpdateEmployee_NotFound_Failure() throws Exception {
    final EmployeeUpdationRQ request =
        EmployeeUpdationRQ.builder().name("Rajesh Kumar").dob(LocalDate.of(2000, 10, 23)).build();

    when(this.employeeService.updateEmployee(TEST_OBJECT_ID, request))
        .thenThrow(Problems.notFound());

    this.mockMvc
        .perform(
            patch(EMPLOYEE_ENDPOINT_PATH + FORWARD_SLASH + TEST_OBJECT_ID)
                .contentType(APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Test get all Employees successfully")
  public void testGetAllEmployees_Success() throws Exception {
    final Employee response = this.newEmployee();
    when(this.employeeService.getAllEmployees()).thenReturn(List.of(response, response, response));

    final String responseContent =
        this.mockMvc
            .perform(get(EMPLOYEE_ENDPOINT_PATH).contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    final JsonNode resultNode = this.objectMapper.readTree(responseContent);

    assertAll(
        "Verify Get all Employees response",
        () -> assertTrue(resultNode.isArray(), "Expected Array response"),
        () -> assertEquals(resultNode.size(), THREE, "Expected 3 Employees"));
  }
}
