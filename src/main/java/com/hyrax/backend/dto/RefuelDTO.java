package com.hyrax.backend.dto;

import java.sql.Timestamp;

public class RefuelDTO {

    private String ownerName;
    private Timestamp fromTime;
    private Timestamp toTime;
    private String stationId;
    private String stationName;
    private String fuelType;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Timestamp getFromTime() {
        return fromTime;
    }

    public void setFromTime(Timestamp fromTime) {
        this.fromTime = fromTime;
    }

    public Timestamp getToTime() {
        return toTime;
    }

    public void setToTime(Timestamp toTime) {
        this.toTime = toTime;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public String toString() {
        return "ownerName:" + ownerName + " fromTime:" + fromTime + " toTime:" + toTime
                + " stationId:" + stationId + " stationName:" + stationName + " fuelType:" + fuelType;
    }
}
