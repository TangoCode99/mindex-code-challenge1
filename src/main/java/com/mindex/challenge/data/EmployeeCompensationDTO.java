package com.mindex.challenge.data;

import java.util.List;

public class EmployeeCompensationDTO {

    private Employee employee;
    private List<Compensation> compensation;

    public EmployeeCompensationDTO(Employee employee, List<Compensation> compensation) {
        this.employee = employee;
        this.compensation = compensation;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Compensation> getCompensation() {
        return this.compensation;
    }

    public void setCompensation(List<Compensation> compensation) {
        this.compensation = compensation;
    }
    
}
