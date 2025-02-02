package com.mindex.challenge.data;

public class EmployeeCompensationDTO {

    private Employee employee;
    private Compensation compensation;

    public EmployeeCompensationDTO(Employee employee, Compensation compensation) {
        this.employee = employee;
        this.compensation = compensation;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Compensation getCompensation() {
        return this.compensation;
    }

    public void setCompensation(Compensation compensation) {
        this.compensation = compensation;
    }
    
}
