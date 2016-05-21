package com.hyrax.backend.entity;

import com.hyrax.backend.entity.state.RefuelState;

import java.sql.Timestamp;
import java.util.UUID;

public class Refuel {

    private UUID id;
    private String userName;
    private String ownerName;
    private Timestamp fromTime;
    private Timestamp toTime;
    private String stationId;
    private String stationName;
    private String fuelType;
    private double litre;
    private double cost;
    private RefuelState state;
    private Timestamp createTime;
    private Timestamp modifyTime;

    public static Refuel newInstance() {
        return new Refuel();
    }



    // set and get method
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public double getLitre() {
        return litre;
    }

    public void setLitre(double litre) {
        this.litre = litre;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public RefuelState getState() {
        return state;
    }

    public void setState(RefuelState state) {
        this.state = state;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Refuel withId(UUID id) {
        this.setId(id);
        return this;
    }


    // with method
    public Refuel withUserName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public Refuel withOwnerName(String ownerName) {
        this.setOwnerName(ownerName);
        return this;
    }

    public Refuel withFromTime(Timestamp fromTime) {
        this.setFromTime(fromTime);
        return this;
    }

    public Refuel withToTime(Timestamp toTime) {
        this.setToTime(toTime);
        return this;
    }

    public Refuel withStationId(String stationId) {
        this.setStationId(stationId);
        return this;
    }

    public Refuel withStationName(String stationName) {
        this.setStationName(stationName);
        return this;
    }

    public Refuel withFuelType(String fuelType) {
        this.setFuelType(fuelType);
        return this;
    }

    public Refuel withLitre(double litre) {
        this.setLitre(litre);
        return this;
    }

    public Refuel withCost(double cost) {
        this.setCost(cost);
        return this;
    }

    public Refuel withState(RefuelState state) {
        this.setState(state);
        return this;
    }

    public Refuel withCreateTime(Timestamp createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public Refuel withModifyTime(Timestamp modifyTime) {
        this.setModifyTime(modifyTime);
        return this;
    }
}
