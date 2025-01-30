package com.mindex.challenge;

import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.ResponseEntity;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.model.ReportingStructure;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChallengeApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;
	// Simulate HTTP calles to the application

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetReportingStructure() {
		Employee john = new Employee("16a596ae-edd3-4847-99fe-c4518e82c86f", "John", "Lennon", "Development Manager", "Engineering", null);
		Employee paul = new Employee("b7839309-3348-463b-a7e3-5de1c168beb3", "Paul", "McCartney", "Developer I", "Engineering", null);
		john.setDirectReports(List.of(paul));
		employeeRepository.save(john);
		employeeRepository.save(paul);

		ResponseEntity<ReportingStructure> response = restTemplate.getForEntity("/reporting-structure/16a596ae-edd3-4847-99fe-c4518e82c86f", ReportingStructure.class);

		assertThat(response.getStatusCode()).isEqualTo(200);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getNumberOfReports()).isEqualTo(1);
		assertThat(response.getBody().getEmployee().getFirstName()).isEqualTo("John");
	
	}

	@Test
	public void testCompensation() {
		// Create Employee
		Employee john = new Employee("16a596ae-edd3-4847-99fe-c4518e82c86f", "John", "Lennon", "Development Manager", "Engineering", null);
		employeeRepository.save(john);

		// Create Compensation
		Compensation compensation = new Compensation(new BigDecimal(100000), LocalDate.parse("2025-03-04"), "16a596ae-edd3-4847-99fe-c4518e82c86f");
		ResponseEntity<Compensation> createReponse = restTemplate.postForEntity("/compensation", compensation, Compensation.class);

		// Validate Create Response
		assertThat(createReponse.getStatusCode()).isEqualTo(200);
		assertThat(createReponse.getBody()).isNotNull();
		assertThat(createReponse.getBody().getSalary()).isEqualTo(new BigDecimal(100000));
		assertThat(createReponse.getBody().getEffectiveDate()).isEqualTo(LocalDate.parse("2025-03-04"));
	}

}
