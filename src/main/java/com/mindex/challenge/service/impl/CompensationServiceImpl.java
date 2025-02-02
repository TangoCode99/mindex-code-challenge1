package com.mindex.challenge.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation create(String employeeId, Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        if (employee == null) {
            throw new RuntimeException("Employee not found with id: " + employeeId);
        }

        compensation.setCompensationId(UUID.randomUUID().toString());
        compensation.setEmployeeId(employeeId);

        compensationRepository.insert(compensation);

        return compensation;
    }

    @Override
    public Compensation getCompensation(String employeeId) throws ResponseStatusException {
        LOG.debug("Retrieving Employee's Compensation");

        Compensation compensation = compensationRepository.findByEmployeeId(employeeId);

        if (compensation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compensation Not Found");
        }

        return compensation;
    }
}
