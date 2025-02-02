package com.mindex.challenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.mindex.challenge.controller.EmployeeController;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.model.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class ChallengeApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	// Test 1 confirms the current reporting structure of John Lennon returns 4.
	@Test
	public void testCalculateNumberOfReports1() throws Exception {
		Employee john = new Employee("16a596ae-edd3-4847-99fe-c4518e82c86f", "John", "Lennon", "Manager", "Engineering", null);
		Employee paul = new Employee("b7839309-3348-463b-a7e3-5de1c168beb3", "Paul", "McCartney", "Developer", "Engineering", null);
		Employee ringo = new Employee("03aa1462-ffa9-4978-901b-7c001562cf6f", "Ringo", "Starr", "Developer", "Engineering", null);
		Employee pete = new Employee("62c1084e-6e34-4630-93fd-9153afb65309", "Pete", "Best", "Developer", "Engineering", null);
		Employee george = new Employee("c0c2293d-16bd-4603-8e08-638a9d18b22c", "George", "Harrison", "Developer", "Engineering", null);

		ReportingStructure reportingStructure = new ReportingStructure();
		reportingStructure.setEmployee(john);
		reportingStructure.setNumberOfReports(4);

		john.setDirectReports(List.of(paul, ringo));
		ringo.setDirectReports(List.of(pete, george));

		Mockito.when(employeeService.getReportingStructure(john.getEmployeeId())).thenReturn(reportingStructure);

		mockMvc.perform(get("/reporting-structure/{id}", john.getEmployeeId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.employee.firstName").value("John"))
			.andExpect(jsonPath("$.numberOfReports").value(4));
	}

	// Test 3 confirms the current reporting structure of Ringo Starr returns 2.
	@Test
	public void testCalculateNumberOfReports2() throws Exception {
		Employee ringo = new Employee("03aa1462-ffa9-4978-901b-7c001562cf6f", "Ringo", "Starr", "Developer", "Engineering", null);
		Employee pete = new Employee("62c1084e-6e34-4630-93fd-9153afb65309", "Pete", "Best", "Developer", "Engineering", null);
		Employee george = new Employee("c0c2293d-16bd-4603-8e08-638a9d18b22c", "George", "Harrison", "Developer", "Engineering", null);

		ReportingStructure reportingStructure = new ReportingStructure();
		reportingStructure.setEmployee(ringo);
		reportingStructure.setNumberOfReports(2);

		ringo.setDirectReports(List.of(pete, george));

		Mockito.when(employeeService.getReportingStructure(ringo.getEmployeeId())).thenReturn(reportingStructure);

		mockMvc.perform(get("/reporting-structure/{id}", ringo.getEmployeeId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.employee.firstName").value("Ringo"))
			.andExpect(jsonPath("$.numberOfReports").value(2));
	}

	// Test 3 confirms that an employee (George Harrison) without reports returns 0.
	@Test
	public void testCalculateNumberOfReports3() throws Exception {
		Employee george = new Employee("c0c2293d-16bd-4603-8e08-638a9d18b22c", "George", "Harrison", "Developer", "Engineering", null);
		
		ReportingStructure reportingStructure = new ReportingStructure();
		reportingStructure.setEmployee(george);
		reportingStructure.setNumberOfReports(0);

		Mockito.when(employeeService.getReportingStructure(george.getEmployeeId())).thenReturn(reportingStructure);

		mockMvc.perform(get("/reporting-structure/{id}", george.getEmployeeId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.employee.firstName").value("George"))
			.andExpect(jsonPath("$.numberOfReports").value(0));
	}

	// Test 4 confirms to return the reporting structure for a non-existent employee
	@Test
	public void testCalculateNumberOfReports4() {
		ReportingStructure reportingStructure = new ReportingStructure();
		Mockito.when(employeeService.getReportingStructure("123456")).thenReturn(reportingStructure);

		assertNull(reportingStructure.getEmployee());
		assertEquals(0, reportingStructure.getNumberOfReports());
	}

}
