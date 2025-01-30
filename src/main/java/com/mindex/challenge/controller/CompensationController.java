package com.mindex.challenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.EmployeeCompensationDTO;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

@RestController
@RequestMapping("/compensation")
public class CompensationController {

    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/{employeeId}")
    public Compensation createCompensation(@PathVariable String employeeId, @RequestBody Compensation compensation) {
        return compensationService.create(employeeId, compensation);
    }

    @GetMapping("/{employeeId}")
    public List<Compensation> getCompensation(@PathVariable String employeeId) {
        return compensationService.getCompensation(employeeId);
    }

    @GetMapping("/info/{employeeId}")
    public EmployeeCompensationDTO getAllInfo(@PathVariable String employeeId) {
        Employee employee = employeeService.read(employeeId);
        List<Compensation> compensation = this.getCompensation(employeeId);

        return new EmployeeCompensationDTO(employee, compensation);
    }
}
