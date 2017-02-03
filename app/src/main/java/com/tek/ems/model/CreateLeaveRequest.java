package com.tek.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by uyalanat on 25-10-2016.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateLeaveRequest {

    @JsonProperty("leaveId")
    private int leaveId;

    @JsonProperty("employeeId")
    private int employeeId;

    @JsonProperty("absenceCategory")
    private String absenceCategory;

    @JsonProperty("attendanceMode")
    private String attendanceMode;

    @JsonProperty("leaveReason")
    private String leaveReason;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("noOfDays")
    private int noOfDays;

    @JsonProperty("leaveComments")
    private String leaveComments;

    @JsonProperty("reportingManagerId")
    private String reportingManagerId;

    @Override
    public String toString() {
        return "CreateLeaveRequest{" +
                "leaveId=" + leaveId +
                ", employeeId=" + employeeId +
                ", absenceCategory='" + absenceCategory + '\'' +
                ", attendanceMode='" + attendanceMode + '\'' +
                ", leaveReason='" + leaveReason + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", noOfDays=" + noOfDays +
                ", leaveComments='" + leaveComments + '\'' +
                ", reportingManagerId='" + reportingManagerId + '\'' +
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

    public String getAbsenceCategory() {
        return absenceCategory;
    }

    public void setAbsenceCategory(String absenceCategory) {
        this.absenceCategory = absenceCategory;
    }

    public String getAttendanceMode() {
        return attendanceMode;
    }

    public void setAttendanceMode(String attendanceMode) {
        this.attendanceMode = attendanceMode;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getLeaveComments() {
        return leaveComments;
    }

    public void setLeaveComments(String leaveComments) {
        this.leaveComments = leaveComments;
    }

    public String getReportingManagerId() {
        return reportingManagerId;
    }

    public void setReportingManagerId(String reportingManagerId) {
        this.reportingManagerId = reportingManagerId;
    }
}
