package com.tek.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by uyalanat on 29-10-2016.
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSheetReport {

    @JsonProperty("Month")
    private String month;

    @JsonProperty("PWD")
    private int pWD;

    @JsonProperty("AWD")
    private int aWD;

    @JsonProperty("LOP")
    private int lOP;

    @JsonProperty("Leaves")
    private int leaves;


    @JsonProperty("TGS")
    private int tGS;

    @JsonProperty("TDS")
    private int tDS;

    @JsonProperty("TNA")
    private int tNA;

    @Override
    public String toString() {
        return "TimeSheetReport{" +
                "month='" + month + '\'' +
                ", pWD=" + pWD +
                ", aWD=" + aWD +
                ", lOP=" + lOP +
                ", leaves=" + leaves +
                ", tGS=" + tGS +
                ", tDS=" + tDS +
                ", tNA=" + tNA +
                '}';
    }

    public int gettGS() {
        return tGS;
    }

    public void settGS(int tGS) {
        this.tGS = tGS;
    }

    public int gettDS() {
        return tDS;
    }

    public void settDS(int tDS) {
        this.tDS = tDS;
    }

    public int gettNA() {
        return tNA;
    }

    public void settNA(int tNA) {
        this.tNA = tNA;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getpWD() {
        return pWD;
    }

    public void setpWD(int pWD) {
        this.pWD = pWD;
    }

    public int getaWD() {
        return aWD;
    }

    public void setaWD(int aWD) {
        this.aWD = aWD;
    }

    public int getlOP() {
        return lOP;
    }

    public void setlOP(int lOP) {
        this.lOP = lOP;
    }

    public int getLeaves() {
        return leaves;
    }

    public void setLeaves(int leaves) {
        this.leaves = leaves;
    }
}
