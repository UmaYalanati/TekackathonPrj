package com.csn.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by uyalanat on 25-10-2016.
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaveDetails implements Serializable {


    @JsonProperty("LeaveId")
    private int leaveId;

    @JsonProperty("EmployeeId")
    private int employeeId;

    @JsonProperty("AppliedDate")
    private String appliedDate;

    @JsonProperty("DateFrom")
    private String dateFrom;

    @JsonProperty("DateTo")
    private String dateTo;

    @JsonProperty("LeaveDate")
    private String leaveDate;

    @JsonProperty("LeaveTypeId")
    private int leaveTypeId;

    @JsonProperty("Comments")
    private String comments;

    @JsonProperty("AssignedTo")
    private int assignedTo;

    @Override
    public String toString() {
        return "LeaveDetails{" +
                "leaveId=" + leaveId +
                ", employeeId=" + employeeId +
                ", appliedDate='" + appliedDate + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", leaveDate='" + leaveDate + '\'' +
                ", leaveTypeId=" + leaveTypeId +
                ", comments='" + comments + '\'' +
                ", assignedTo=" + assignedTo +
                ", actionDate='" + actionDate + '\'' +
                ", leaveStatusId=" + leaveStatusId +
                ", leaveStatus='" + leaveStatus + '\'' +
                ", leaveType='" + leaveType + '\'' +
                '}';
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
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

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
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

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    @JsonProperty("ActionDate")

    private String actionDate;

    @JsonProperty("LeaveStatusId")
    private int leaveStatusId;

    @JsonProperty("LeaveStatus")
    private String leaveStatus;

    @JsonProperty("LeaveType")
    private String leaveType;
}
