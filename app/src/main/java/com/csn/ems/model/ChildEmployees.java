package com.csn.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by uyalanat on 21-11-2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildEmployees {
    @JsonProperty("EmployeeId")
    private int employeeId;

    @JsonProperty("EmployeeName")
    private String employeeName;

    @Override
    public String toString() {
        return "ChildEmployees{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
