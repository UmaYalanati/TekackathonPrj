package com.csn.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by uyalanat on 25-10-2016.
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeLocation {

    @JsonProperty("EmployeeLocationId")
    private int employeeLocationId;

    @JsonProperty("EmployeeId")
    private int employeeId;

    @JsonProperty("LocationId")
    private int locationId;

    @JsonProperty("LocationName")
    private String locationName;

    @Override
    public String toString() {
        return "EmployeeLocation{" +
                "employeeLocationId=" + employeeLocationId +
                ", employeeId=" + employeeId +
                ", locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                '}';
    }

    public int getEmployeeLocationId() {
        return employeeLocationId;
    }

    public void setEmployeeLocationId(int employeeLocationId) {
        this.employeeLocationId = employeeLocationId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
