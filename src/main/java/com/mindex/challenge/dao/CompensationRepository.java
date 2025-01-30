package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
    List<Compensation> findByEmployeeId(String employeeId);
}