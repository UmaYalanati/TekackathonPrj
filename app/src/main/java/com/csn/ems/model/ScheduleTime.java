package com.csn.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by uyalanat on 29-10-2016.
 */
// @JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleTime {

    @JsonProperty("StartTime")
    private String startTime;

    @JsonProperty("EndTime")
    private String endTime;

    @JsonProperty("Holiday")
    private String holiday;

    @JsonProperty("WeekOff")
    private String weekOff;

    @Override
    public String toString() {
        return "ScheduleTime{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", holiday='" + holiday + '\'' +
                ", weekOff='" + weekOff + '\'' +
                '}';
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getWeekOff() {
        return weekOff;
    }

    public void setWeekOff(String weekOff) {
        this.weekOff = weekOff;
    }
}
