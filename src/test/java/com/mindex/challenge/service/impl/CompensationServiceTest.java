package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void testCreateCompensation() {
        Compensation compensation = new Compensation(new BigDecimal(100000), LocalDate.parse("2025-01-01"), "16a596ae-edd3-4847-99fe-c4518e82c86f");
        Employee john = new Employee("16a596ae-edd3-4847-99fe-c4518e82c86f", "John", "Lennon", "Manager", "Engineering", null);
        compensation.setEmployeeId(john.getEmployeeId());

        when(employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f")).thenReturn(john);
        when(compensationRepository.save(any(Compensation.class))).thenReturn(compensation);

        Compensation createdCompensation = compensationService.create("16a596ae-edd3-4847-99fe-c4518e82c86f", compensation);

        assertNotNull(createdCompensation);
        assertEquals(new BigDecimal(100000), createdCompensation.getSalary());
        assertEquals(LocalDate.parse("2025-01-01"), createdCompensation.getEffectiveDate());
    }

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
}
