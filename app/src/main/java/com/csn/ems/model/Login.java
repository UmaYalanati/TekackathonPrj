package com.csn.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by uyalanat on 26-10-2016.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Login {
    @JsonProperty("EmployeeId")
    private int employeeId;

    @JsonProperty("EmployeeName")
    private String employeeName;

    @JsonProperty("LocationId")
    private int locationId;

    @JsonProperty("locationName")
    private String locationName;

    @Override
    public String toString() {
        return "Login{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }

    @JsonProperty("EmailId")
    private String emailId;

    @JsonProperty("PhotoPath")
    private String photoPath;

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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
