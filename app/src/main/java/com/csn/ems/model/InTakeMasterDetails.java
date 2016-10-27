package com.csn.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by uyalanat on 27-10-2016.
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InTakeMasterDetails {

    @JsonProperty("Positions")
    private List<LeaveStatus> positions;

    @JsonProperty("BusinessAreas")
    private List<LeaveStatus> businessAreas;

    @JsonProperty("SubBusinessAreas")
    private List<LeaveStatus> subBusinessAreas;

    @Override
    public String toString() {
        return "InTakeMasterDetails{" +
                "positions=" + positions +
                ", businessAreas=" + businessAreas +
                ", subBusinessAreas=" + subBusinessAreas +
                ", states=" + states +
                ", locations=" + locations +
                ", schedule=" + schedule +
                ", leaveTypes=" + leaveTypes +
                ", leaveStatus=" + leaveStatus +
                '}';
    }

    @JsonProperty("States")
    private List<LeaveStatus> states;

    @JsonProperty("Locations")
    private List<LeaveStatus> locations;

    @JsonProperty("Schedule")
    private List<LeaveStatus> schedule;

    @JsonProperty("LeaveTypes")
    private List<LeaveStatus> leaveTypes;

    @JsonProperty("LeaveStatus")
    private List<LeaveStatus> leaveStatus;

    public List<LeaveStatus> getPositions() {
        return positions;
    }

    public void setPositions(List<LeaveStatus> positions) {
        this.positions = positions;
    }

    public List<LeaveStatus> getBusinessAreas() {
        return businessAreas;
    }

    public void setBusinessAreas(List<LeaveStatus> businessAreas) {
        this.businessAreas = businessAreas;
    }

    public List<LeaveStatus> getSubBusinessAreas() {
        return subBusinessAreas;
    }

    public void setSubBusinessAreas(List<LeaveStatus> subBusinessAreas) {
        this.subBusinessAreas = subBusinessAreas;
    }

    public List<LeaveStatus> getStates() {
        return states;
    }

    public void setStates(List<LeaveStatus> states) {
        this.states = states;
    }

    public List<LeaveStatus> getLocations() {
        return locations;
    }

    public void setLocations(List<LeaveStatus> locations) {
        this.locations = locations;
    }

    public List<LeaveStatus> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<LeaveStatus> schedule) {
        this.schedule = schedule;
    }

    public List<LeaveStatus> getLeaveTypes() {
        return leaveTypes;
    }

    public void setLeaveTypes(List<LeaveStatus> leaveTypes) {
        this.leaveTypes = leaveTypes;
    }

    public List<LeaveStatus> getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(List<LeaveStatus> leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
}
