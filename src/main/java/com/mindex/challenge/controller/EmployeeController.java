package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.model.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }

    // This method allows the user to quickly retrieve all the employees in the database
    @GetMapping("/getAllEmployees")
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }

    // This method will handle requests to get the ReportingStructure
    @GetMapping("/reporting-structure/{id}")
    public ReportingStructure getReportingStructure(@PathVariable String id) {
        // Retrieve employee based on employeeId
        Employee employee = read(id); // You need to implement this logic

        // If employee is not found, return a 404 or handle accordingly
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }

        // Compute the number of direct reports (this could be dynamic depending on your
        // data)
        int numberOfReports = computeNumberOfReports(employee); // Implement this logic

        // Create and return the ReportingStructure
        return new ReportingStructure(employee, numberOfReports);
    }

    // This method will compute the number of reports when given the employee id.
    private int computeNumberOfReports(@PathVariable Employee employee) {
        if (employee == null || employee.getDirectReports() == null) {
            return 0; // No reports if there are none or if the employee doesn't exist
        }

        int count = 0;

        for (Employee directReport : employee.getDirectReports()) {
            // Fetch the full Employee object for each direct report
            Employee fullDirectReport = this.read(directReport.getEmployeeId());

            if (fullDirectReport != null) {
                count++; // Count this direct report
                count += computeNumberOfReports(fullDirectReport); // Recursively count their reports
            }
        }

        return count;
    }
}
