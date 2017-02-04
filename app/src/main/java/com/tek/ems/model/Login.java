package com.tek.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by uyalanat on 26-10-2016.
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Login {
    @JsonProperty("employeeId")
    private int employeeId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("pincode")
    private String pincode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("emailId")
    private String emailid;

    @JsonProperty("contactNo")
    private String contactNo;

    @JsonProperty("dateOfBirth")
    private String dateOfBirth;

    @JsonProperty("designation")
    private String designation;

    @JsonProperty("dateOfJoining")
    private String dateOfJoining;

    @JsonProperty("yearsOfExperience")
    private String yearsOfExperience;

    @JsonProperty("reportingManagerId")
    private int reportingManagerId;

    @JsonProperty("casualLeaves")
    private int casualLeaves;

    @JsonProperty("earnedLeaves")
    private int earnedLeaves;

    @JsonProperty("sickLeaves")
    private int sickLeaves;

    @JsonProperty("compOffs")
    private int compOffs;

    @JsonProperty("sharedLeaves")
    private int sharedLeaves;

    @JsonProperty("createdDate")
    private String createdDate;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("deviceId")
    private String deviceId;

    @Override
    public String toString() {
        return "Login{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pincode='" + pincode + '\'' +
                ", country='" + country + '\'' +
                ", emailid='" + emailid + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", designation='" + designation + '\'' +
                ", dateOfJoining='" + dateOfJoining + '\'' +
                ", yearsOfExperience='" + yearsOfExperience + '\'' +
                ", reportingManagerId=" + reportingManagerId +
                ", casualLeaves=" + casualLeaves +
                ", earnedLeaves=" + earnedLeaves +
                ", sickLeaves=" + sickLeaves +
                ", compOffs=" + compOffs +
                ", sharedLeaves=" + sharedLeaves +
                ", createdDate='" + createdDate + '\'' +
                ", companyName='" + companyName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getReportingManagerId() {
        return reportingManagerId;
    }

    public void setReportingManagerId(int reportingManagerId) {
        this.reportingManagerId = reportingManagerId;
    }

    public int getCasualLeaves() {
        return casualLeaves;
    }

    public void setCasualLeaves(int casualLeaves) {
        this.casualLeaves = casualLeaves;
    }

    public int getEarnedLeaves() {
        return earnedLeaves;
    }

    public void setEarnedLeaves(int earnedLeaves) {
        this.earnedLeaves = earnedLeaves;
    }

    public int getSickLeaves() {
        return sickLeaves;
    }

    public void setSickLeaves(int sickLeaves) {
        this.sickLeaves = sickLeaves;
    }

    public int getCompOffs() {
        return compOffs;
    }

    public void setCompOffs(int compOffs) {
        this.compOffs = compOffs;
    }

    public int getSharedLeaves() {
        return sharedLeaves;
    }

    public void setSharedLeaves(int sharedLeaves) {
        this.sharedLeaves = sharedLeaves;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
/*  @JsonProperty("ChildRoleName")
    private String childRoleName;

    @JsonProperty("ChildEmployees")
    private List<ChildEmployees> childEmployees;*/


}
