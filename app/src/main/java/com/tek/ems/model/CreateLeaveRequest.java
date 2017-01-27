package com.tek.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by uyalanat on 25-10-2016.
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateLeaveRequest {

    @JsonProperty("LeaveId")
    private int leaveId;

    @JsonProperty("EmployeeId")
    private int employeeId;

    @JsonProperty("AppliedDate")
    private String appliedDate;

    @Override
    public String toString() {
        return "CreateLeaveRequest{" +
                "leaveId=" + leaveId +
                ", employeeId=" + employeeId +
                ", appliedDate='" + appliedDate + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", leaveTypeId=" + leaveTypeId +
                ", comments='" + comments + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", actionDate='" + actionDate + '\'' +
                ", leaveStatusId=" + leaveStatusId +
                '}';
    }

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public int getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    public int getLeaveStatusId() {
        return leaveStatusId;
    }

    public void setLeaveStatusId(int leaveStatusId) {
        this.leaveStatusId = leaveStatusId;
    }

    @JsonProperty("DateFrom")
    private String dateFrom;

    @JsonProperty("DateTo")
    private String dateTo;

    @JsonProperty("LeaveTypeId")
    private int leaveTypeId;

    @JsonProperty("Comments")
    private String comments;

    @JsonProperty("AssignedTo")
    private String assignedTo;

    @JsonProperty("ActionDate")
    private String actionDate;

    @JsonProperty("LeaveStatusId")
    private int leaveStatusId;
}
