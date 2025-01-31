package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.model.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        LOG.debug("Getting All Employees Information");

        return employeeRepository.findAll();
    }

    @Override
    public ReportingStructure getReportingStructure(String employeeId) {
        LOG.debug("Getting Reporting Structure for Employee");

        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        // If employee does not exist in the database, throw error.
        if(employee == null) {
            throw new RuntimeException("Employee not found");
        }

        int numberOfReports = calculateNumberOfReports(employee);
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(numberOfReports);

        return reportingStructure;
    }

    private int calculateNumberOfReports(Employee employee) {
        LOG.debug("Calculate the Number of Reports for an Employee");

        // If Employee does not have any direct reports.
        if(employee.getDirectReports() == null) return 0;

        // Recursively look through each of the direct reports... like a tree!
        int count = 0;
        for(Employee directReport: employee.getDirectReports()) {
            Employee currEmployee = employeeRepository.findByEmployeeId(directReport.getEmployeeId());
            count += 1 + calculateNumberOfReports(currEmployee);
        }

        return count;
    }
}
