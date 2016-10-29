package com.csn.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by uyalanat on 29-10-2016.
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRolePermission {

    @JsonProperty("EmployeeId")
    private int employeeId;

    @JsonProperty("RoleID")
    private int roleID;

    @JsonProperty("RoleName")
    private String roleName;

    @JsonProperty("PermissionID")
    private int permissionID;

    @JsonProperty("PermissionName")
    private String permissionName;

    @Override
    public String toString() {
        return "UserRolePermission{" +
                "employeeId=" + employeeId +
                ", roleID=" + roleID +
                ", roleName='" + roleName + '\'' +
                ", permissionID=" + permissionID +
                ", permissionName='" + permissionName + '\'' +
                '}';
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
