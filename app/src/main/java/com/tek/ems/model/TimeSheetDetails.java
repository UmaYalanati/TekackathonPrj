package com.tek.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by uyalanat on 26-10-2016.
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSheetDetails {
    @JsonProperty("timesheetId")
    private int timeSheetId;

   /* @JsonProperty("EmployeeId")
    private int employeeId;*/

    @JsonProperty("insertDate")
    private String insertDate;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("checkinTime")
    private String checkinTime;

    @JsonProperty("checkoutTime")
    private String checkoutTime;

    @JsonProperty("checkinLatitude")
    private double checkinLatitude;

    @JsonProperty("checkinLongitude")
    private double checkinLongitude;

    @JsonProperty("checkoutLatitude")
    private double checkoutLatitude;

    @JsonProperty("checkoutLongitude")
    private double checkoutLongitude;

    @JsonProperty("assignedTo")
    private String assignedTo;

    @JsonProperty("comments")
    private String comments;

    @JsonProperty("approvaltype")
    private String approvaltype;

    @JsonProperty("attendanceMode")
    private String attendanceMode;

    @JsonProperty("status")
    private String status;

    @JsonProperty("workingHours")
    private String workingHours;

    @JsonProperty("noOfDays")
    private String noOfDays;

    @JsonProperty("absenceCategory")
    private String absenceCategory;

    @JsonProperty("leaveReason")
    private String leaveReason;

    @JsonProperty("leaveComments")
    private String leaveComments;

    @Override
    public String toString() {
        return "InsertClockIn{" +
                "timeSheetId=" + timeSheetId +
                ", insertDate='" + insertDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", checkinTime='" + checkinTime + '\'' +
                ", checkoutTime='" + checkoutTime + '\'' +
                ", checkinLatitude=" + checkinLatitude +
                ", checkinLongitude=" + checkinLongitude +
                ", checkoutLatitude=" + checkoutLatitude +
                ", checkoutLongitude=" + checkoutLongitude +
                ", assignedTo='" + assignedTo + '\'' +
                ", comments='" + comments + '\'' +
                ", approvaltype='" + approvaltype + '\'' +
                ", attendanceMode='" + attendanceMode + '\'' +
                ", status='" + status + '\'' +
                ", workingHours='" + workingHours + '\'' +
                ", noOfDays='" + noOfDays + '\'' +
                ", absenceCategory='" + absenceCategory + '\'' +
                ", leaveReason='" + leaveReason + '\'' +
                ", leaveComments='" + leaveComments + '\'' +
                '}';
    }

    public int getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(int timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public double getCheckinLatitude() {
        return checkinLatitude;
    }

    public void setCheckinLatitude(double checkinLatitude) {
        this.checkinLatitude = checkinLatitude;
    }

    public double getCheckinLongitude() {
        return checkinLongitude;
    }

    public void setCheckinLongitude(double checkinLongitude) {
        this.checkinLongitude = checkinLongitude;
    }

    public double getCheckoutLatitude() {
        return checkoutLatitude;
    }

    public void setCheckoutLatitude(double checkoutLatitude) {
        this.checkoutLatitude = checkoutLatitude;
    }

    public double getCheckoutLongitude() {
        return checkoutLongitude;
    }

    public void setCheckoutLongitude(double checkoutLongitude) {
        this.checkoutLongitude = checkoutLongitude;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getApprovaltype() {
        return approvaltype;
    }

    public void setApprovaltype(String approvaltype) {
        this.approvaltype = approvaltype;
    }

    public String getAttendanceMode() {
        return attendanceMode;
    }

    public void setAttendanceMode(String attendanceMode) {
        this.attendanceMode = attendanceMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getAbsenceCategory() {
        return absenceCategory;
    }

    public void setAbsenceCategory(String absenceCategory) {
        this.absenceCategory = absenceCategory;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getLeaveComments() {
        return leaveComments;
    }

    public void setLeaveComments(String leaveComments) {
        this.leaveComments = leaveComments;
    }


}
