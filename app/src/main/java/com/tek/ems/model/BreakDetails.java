package com.tek.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by uyalanat on 28-10-2016.
 */
// @JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BreakDetails {

    @JsonProperty("BreakId")
    private int breakId;

    @JsonProperty("TimeSheetId")
    private int timeSheetId;

    @JsonProperty("WorkingDate")
    private String workingDate;

    @JsonProperty("BreakIn")
    private String breakIn;

    @JsonProperty("BreakOut")
    private String breakOut;

    @JsonProperty("BreakInLattitude")
    private double breakInLattitude;

    @JsonProperty("BreakInLongitude")
    private double breakInLongitude;

    @JsonProperty("BreakOutLattitude")
    private double breakOutLattitude;

    @JsonProperty("BreakOutLongitude")
    private double breakOutLongitude;

    @JsonProperty("Comments")
    private String comments;

    @Override
    public String toString() {
        return "BreakDetails{" +
                "breakId=" + breakId +
                ", timeSheetId=" + timeSheetId +
                ", workingDate='" + workingDate + '\'' +
                ", breakIn='" + breakIn + '\'' +
                ", breakOut='" + breakOut + '\'' +
                ", breakInLattitude=" + breakInLattitude +
                ", breakInLongitude=" + breakInLongitude +
                ", breakOutLattitude=" + breakOutLattitude +
                ", breakOutLongitude=" + breakOutLongitude +
                ", comments='" + comments + '\'' +
                '}';
    }

    public int getBreakId() {
        return breakId;
    }

    public void setBreakId(int breakId) {
        this.breakId = breakId;
    }

    public int getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(int timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public String getWorkingDate() {
        return workingDate;
    }

    public void setWorkingDate(String workingDate) {
        this.workingDate = workingDate;
    }

    public String getBreakIn() {
        return breakIn;
    }

    public void setBreakIn(String breakIn) {
        this.breakIn = breakIn;
    }

    public String getBreakOut() {
        return breakOut;
    }

    public void setBreakOut(String breakOut) {
        this.breakOut = breakOut;
    }

    public double getBreakInLattitude() {
        return breakInLattitude;
    }

    public void setBreakInLattitude(double breakInLattitude) {
        this.breakInLattitude = breakInLattitude;
    }

    public double getBreakInLongitude() {
        return breakInLongitude;
    }

    public void setBreakInLongitude(double breakInLongitude) {
        this.breakInLongitude = breakInLongitude;
    }

    public double getBreakOutLattitude() {
        return breakOutLattitude;
    }

    public void setBreakOutLattitude(double breakOutLattitude) {
        this.breakOutLattitude = breakOutLattitude;
    }

    public double getBreakOutLongitude() {
        return breakOutLongitude;
    }

    public void setBreakOutLongitude(double breakOutLongitude) {
        this.breakOutLongitude = breakOutLongitude;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
