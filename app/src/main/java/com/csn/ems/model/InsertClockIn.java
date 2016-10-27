package com.csn.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by uyalanat on 27-10-2016.
 */
//
// @JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InsertClockIn {

    @JsonProperty("TimeSheetId")
    private int timeSheetId;

    @JsonProperty("EmployeeId")
    private int employeeId;

    @JsonProperty("WorkingDate")
    private String workingDate;

    @JsonProperty("CheckIn")
    private String checkIn;

    @JsonProperty("CheckOut")
    private String checkOut;

    @JsonProperty("CheckInLattitude")
    private double checkInLattitude;

    @JsonProperty("CheckOutLattitude")
    private double checkOutLattitude;

    @JsonProperty("CheckOutLongitude")
    private double checkOutLongitude;

    @JsonProperty("CheckInLongitude")
    private double checkInLongitude;

    @JsonProperty("AssignedTo")
    private String assignedTo;

    @JsonProperty("ApprovalType")
    private String approvalType;

    @JsonProperty("Note")
    private String note;

    @JsonProperty("Status")
    private String status;

    @Override
    public String toString() {
        return "InsertClockIn{" +
                "timeSheetId=" + timeSheetId +
                ", employeeId=" + employeeId +
                ", workingDate='" + workingDate + '\'' +
                ", checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                ", checkInLattitude=" + checkInLattitude +
                ", checkOutLattitude=" + checkOutLattitude +
                ", checkOutLongitude=" + checkOutLongitude +
                ", checkInLongitude=" + checkInLongitude +
                ", assignedTo='" + assignedTo + '\'' +
                ", approvalType='" + approvalType + '\'' +
                ", note='" + note + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(int timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getWorkingDate() {
        return workingDate;
    }

    public void setWorkingDate(String workingDate) {
        this.workingDate = workingDate;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public double getCheckInLattitude() {
        return checkInLattitude;
    }

    public void setCheckInLattitude(double checkInLattitude) {
        this.checkInLattitude = checkInLattitude;
    }

    public double getCheckOutLattitude() {
        return checkOutLattitude;
    }

    public void setCheckOutLattitude(double checkOutLattitude) {
        this.checkOutLattitude = checkOutLattitude;
    }

    public double getCheckOutLongitude() {
        return checkOutLongitude;
    }

    public void setCheckOutLongitude(double checkOutLongitude) {
        this.checkOutLongitude = checkOutLongitude;
    }

    public double getCheckInLongitude() {
        return checkInLongitude;
    }

    public void setCheckInLongitude(double checkInLongitude) {
        this.checkInLongitude = checkInLongitude;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
