package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompensationServiceTest {

    @Autowired
    private CompensationService compensationService;

    @MockBean
    private CompensationRepository compensationRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    // Scenario: Creating a compensation for an employee.
    @Test
    public void testCreateCompensation() {
        Compensation compensation = new Compensation(new BigDecimal(100000), LocalDate.parse("2025-01-01"), "16a596ae-edd3-4847-99fe-c4518e82c86f");
        Employee john = new Employee("16a596ae-edd3-4847-99fe-c4518e82c86f", "John", "Lennon", "Manager", "Engineering", null);
        compensation.setEmployeeId(john.getEmployeeId());

        when(employeeRepository.findByEmployeeId(john.getEmployeeId())).thenReturn(john);
        when(compensationRepository.save(any(Compensation.class))).thenReturn(compensation);

        Compensation createdCompensation = compensationService.create("16a596ae-edd3-4847-99fe-c4518e82c86f", compensation);

        assertNotNull(createdCompensation);
        assertEquals(new BigDecimal(100000), createdCompensation.getSalary());
        assertEquals(LocalDate.parse("2025-01-01"), createdCompensation.getEffectiveDate());
    }

    // Scenario: Getting an existing compensation.
    @Test
    public void testGetCompensation() {
        Employee john = new Employee("16a596ae-edd3-4847-99fe-c4518e82c86f", "John", "Lennon", "Manager", "Engineering", null);
        Compensation compensation = new Compensation(new BigDecimal(100000), LocalDate.parse("2025-01-01"), "16a596ae-edd3-4847-99fe-c4518e82c86f");
        compensation.setEmployeeId(john.getEmployeeId());

        when(compensationRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f")).thenReturn(List.of(compensation));

        List<Compensation> fetchCompensation = compensationService.getCompensation("16a596ae-edd3-4847-99fe-c4518e82c86f");

        assertNotNull(fetchCompensation);
        assertEquals(new BigDecimal(100000), fetchCompensation.get(0).getSalary());;
        assertEquals(LocalDate.parse("2025-01-01"), fetchCompensation.get(0).getEffectiveDate());
    }

    // Scenario: Attemp to fetch a compensation that doesn't exist.
    @Test
    public void testNoCompensation() {
        Employee john = new Employee("16a596ae-edd3-4847-99fe-c4518e82c86f", "John", "Lennon", "Manager", "Engineering", null);

        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class, 
            () -> compensationService.getCompensation(john.getEmployeeId())
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Compensation Not Found", exception.getReason());

    }

}
