package com.tek.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by uyalanat on 01-11-2016.
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaveCategorylist {
    @JsonProperty("Pending")
    private List<LeaveDetails> pending;

    @JsonProperty("Approved")
    private List<LeaveDetails> approved;

    @JsonProperty("Rejected")
    private List<LeaveDetails> rejected;

    @Override
    public String toString() {
        return "LeaveCategorylist{" +
                "pending=" + pending +
                ", approved=" + approved +
                ", rejected=" + rejected +
                '}';
    }

    public List<LeaveDetails> getPending() {
        return pending;
    }

    public void setPending(List<LeaveDetails> pending) {
        this.pending = pending;
    }

    public List<LeaveDetails> getApproved() {
        return approved;
    }

    public void setApproved(List<LeaveDetails> approved) {
        this.approved = approved;
    }

    public List<LeaveDetails> getRejected() {
        return rejected;
    }

    public void setRejected(List<LeaveDetails> rejected) {
        this.rejected = rejected;
    }
}
