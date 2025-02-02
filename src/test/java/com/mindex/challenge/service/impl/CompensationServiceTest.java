package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceTest {

    private String employeeUrl;
    private String compensationUrl;

    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        compensationUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    // Scenario: Creating/Reading a compensation for an employee.
    @Test
    public void testCreateReadCompensation() {
        // Create Employee
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Lennon");
        testEmployee.setPosition("Developer");
        testEmployee.setDepartment("Engineering");

        // Mock the creation of the employee
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        // Create Compensation Checks
        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);

        // Create Compensation
        Compensation testCompensation = new Compensation();
        testCompensation.setSalary(new BigDecimal(100000));
        testCompensation.setEffectiveDate(LocalDate.parse("2025-01-01"));

        // Mock the creation of the compensation
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class, createdEmployee.getEmployeeId()).getBody();

        // Create Compensation Checks
        assertNotNull(createdCompensation.getCompensationId());
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Read Compensation Checks
        Compensation readCompensation = restTemplate.getForEntity(compensationUrl, Compensation.class, createdEmployee.getEmployeeId()).getBody();
        assertEquals(createdCompensation.getCompensationId(), readCompensation.getCompensationId());
        assertCompensationEquivalence(createdCompensation, readCompensation);
    }

    // Scenario: Attempt to fetch a compensation that doesn't exist.
    // Error is thrown on the server side, TestRestTemplate is handled on the client side
    // Therefore it never can catch and handle the exception that is thrown internally
    // @Test
    // public void testNoCompensation() {
    //     ResponseEntity<Compensation> response = restTemplate.getForEntity(compensationUrl, Compensation.class, "1234567");
    //     assertThrows(ResponseStatusException.class, () -> response.getBody());

    //     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    //     assertEquals("Compensation Not Found", response.getBody());
    // }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }

}
