package com.csn.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static com.csn.ems.emsconstants.EmsConstants.childEmployeeId;

/**
 * Created by uyalanat on 26-10-2016.
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    @JsonProperty("OrganizationName")
    private String organizationName;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("ChildEmployeeId")
    private int childEmployeeId;

    @JsonProperty("RoleName")
    private String roleName;

    @JsonProperty("ChildRoleName")
    private String childRoleName;

    @Override
    public String toString() {
        return "Login{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", message='" + message + '\'' +
                ", childEmployeeId=" + childEmployeeId +
                ", roleName='" + roleName + '\'' +
                ", childRoleName='" + childRoleName + '\'' +
                ", userRolePermission=" + userRolePermission +
                ", emailId='" + emailId + '\'' +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }

    public int getChildEmployeeId() {
        return childEmployeeId;
    }

    public void setChildEmployeeId(int childEmployeeId) {
        this.childEmployeeId = childEmployeeId;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getChildRoleName() {
        return childRoleName;
    }

    public void setChildRoleName(String childRoleName) {
        this.childRoleName = childRoleName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("UserRolePermission")
    private List<UserRolePermission> userRolePermission;

    public List<UserRolePermission> getUserRolePermission() {
        return userRolePermission;
    }

    public void setUserRolePermission(List<UserRolePermission> userRolePermission) {
        this.userRolePermission = userRolePermission;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
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
