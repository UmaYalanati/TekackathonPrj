package com.csn.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONArray;

import java.util.Arrays;

/**
 * Created by uyalanat on 24-10-2016.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDetails {
    @JsonProperty("EmployeeId")
    private int employeeId;

    @JsonProperty("PropertyOwnerName")
    private String propertyOwnerName;

    @JsonProperty("EmployeeCode")
    private int employeeCode;

    @JsonProperty("EmployeeName")
    private String employeeName;

    @JsonProperty("EmailId")
    private String emailId;

    @JsonProperty("ContactNumber")
    private String contactNumber;

    @JsonProperty("Gender")
    private String Gender;

    @JsonProperty("DOB")
    private String dOB;

    @JsonProperty("Address1")
    private String address1;

    @JsonProperty("address2")
    private String address2;

    @JsonProperty("City")
    private String city;

    @JsonProperty("StateId")
    private int stateId;

    @JsonProperty("PostalCode")
    private int postalCode;

    @JsonProperty("PositionId")
    private int positionId;

    @JsonProperty("BusinessAreaId")
    private int businessAreaId;

    @JsonProperty("SubBusinessAreaId")
    private int subBusinessAreaId;

    @JsonProperty("ScheduleId")
    private int scheduleId;

    @JsonProperty("Wage")
    private int wage;

    @JsonProperty("HoursPerDay")
    private String hoursPerDay;

    @JsonProperty("JoiningDate")
    private String joiningDate;

    @JsonProperty("Status")
    private Boolean status;

    @JsonProperty("StateName")
    private String stateName;

    @JsonProperty("PositionName")
    private String positionName;

    @JsonProperty("BusinessAreaName")
    private String businessAreaName;

    @JsonProperty("SubBusinessAreaName")
    private String SubBusinessAreaName;

    @JsonProperty("ScheduleName")
    private String scheduleName;

    @JsonProperty("Photo")
    private String photo;

    @JsonProperty("ByteArrayPhoto")
    private byte[] byteArrayPhoto;

    @JsonProperty("EmployeeLocation")
    private JSONArray employeeLocation;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPropertyOwnerName() {
        return propertyOwnerName;
    }

    public void setPropertyOwnerName(String propertyOwnerName) {
        this.propertyOwnerName = propertyOwnerName;
    }

    public int getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(int employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getBusinessAreaId() {
        return businessAreaId;
    }

    public void setBusinessAreaId(int businessAreaId) {
        this.businessAreaId = businessAreaId;
    }

    public int getSubBusinessAreaId() {
        return subBusinessAreaId;
    }

    public void setSubBusinessAreaId(int subBusinessAreaId) {
        this.subBusinessAreaId = subBusinessAreaId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    public String getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(String hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getBusinessAreaName() {
        return businessAreaName;
    }

    public void setBusinessAreaName(String businessAreaName) {
        this.businessAreaName = businessAreaName;
    }

    public String getSubBusinessAreaName() {
        return SubBusinessAreaName;
    }

    public void setSubBusinessAreaName(String subBusinessAreaName) {
        SubBusinessAreaName = subBusinessAreaName;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public byte[] getByteArrayPhoto() {
        return byteArrayPhoto;
    }

    public void setByteArrayPhoto(byte[] byteArrayPhoto) {
        this.byteArrayPhoto = byteArrayPhoto;
    }

    public JSONArray getEmployeeLocation() {
        return employeeLocation;
    }

    public void setEmployeeLocation(JSONArray employeeLocation) {
        this.employeeLocation = employeeLocation;
    }

    @Override
    public String toString() {
        return "EmployeeDetails{" +
                "employeeId=" + employeeId +
                ", propertyOwnerName='" + propertyOwnerName + '\'' +
                ", employeeCode=" + employeeCode +
                ", employeeName='" + employeeName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", Gender='" + Gender + '\'' +
                ", dOB='" + dOB + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", stateId=" + stateId +
                ", postalCode=" + postalCode +
                ", positionId=" + positionId +
                ", businessAreaId=" + businessAreaId +
                ", subBusinessAreaId=" + subBusinessAreaId +
                ", scheduleId=" + scheduleId +
                ", wage=" + wage +
                ", hoursPerDay='" + hoursPerDay + '\'' +
                ", joiningDate='" + joiningDate + '\'' +
                ", status=" + status +
                ", stateName='" + stateName + '\'' +
                ", positionName='" + positionName + '\'' +
                ", businessAreaName='" + businessAreaName + '\'' +
                ", SubBusinessAreaName='" + SubBusinessAreaName + '\'' +
                ", scheduleName='" + scheduleName + '\'' +
                ", photo='" + photo + '\'' +
                ", byteArrayPhoto=" + Arrays.toString(byteArrayPhoto) +
                ", employeeLocation=" + employeeLocation +
                '}';
    }
}
