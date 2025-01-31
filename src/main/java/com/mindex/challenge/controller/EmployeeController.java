package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.model.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        LOG.debug("Retrieve All Employees");

        return employeeService.getAll();
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        LOG.debug("Received employee delete request for id [{}]", id);

        try {
            employeeService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    @GetMapping("/reporting-structure/{employeeId}")
    public ReportingStructure getReportingStructure(@PathVariable String employeeId) {
        LOG.debug("Get Reporting Structure for id [{}]", employeeId);

        return employeeService.getReportingStructure(employeeId);
    }

}
