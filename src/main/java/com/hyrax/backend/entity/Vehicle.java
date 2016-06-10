package com.hyrax.backend.entity;

import java.sql.Timestamp;
import java.util.UUID;

public class Vehicle {

    private UUID id;
    private String userName;
    private String brand;
    private String mark;
    private String model;
    private String number;
    private String engine;
    private int doorNum;
    private int seatNum;
    private Timestamp createTime;
    private Timestamp modifyTime;
    private int gasCapacity;
    private int maintainCycle;
    private String carFrame;

    public static Vehicle newInstance() {
        return new Vehicle();
    }

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getDoorNum() {
        return doorNum;
    }

    public void setDoorNum(int doorNum) {
        this.doorNum = doorNum;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
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

    public int getGasCapacity() {
        return gasCapacity;
    }

    public void setGasCapacity(int gasCapacity) {
        this.gasCapacity = gasCapacity;
    }

    public int getMaintainCycle() {
        return maintainCycle;
    }

    public void setMaintainCycle(int maintainCycle) {
        this.maintainCycle = maintainCycle;
    }

    public String getCarFrame() {
        return carFrame;
    }

    public void setCarFrame(String carFrame) {
        this.carFrame = carFrame;
    }

    public Vehicle withId(UUID id) {
        this.setId(id);
        return this;
    }

    public Vehicle withUserName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public Vehicle withBrand(String brand) {
        this.setBrand(brand);
        return this;
    }

    public Vehicle withMark(String mark) {
        this.setMark(mark);
        return this;
    }

    public Vehicle withModel(String model) {
        this.setModel(model);
        return this;
    }

    public Vehicle withNumber(String number) {
        this.setNumber(number);
        return this;
    }

    public Vehicle withEngine(String engine) {
        this.setEngine(engine);
        return this;
    }

    public Vehicle withDoorNum(int doorNum) {
        this.setDoorNum(doorNum);
        return this;
    }

    public Vehicle withSeatNum(int seatNum) {
        this.setSeatNum(seatNum);
        return this;
    }

    public Vehicle withCreateTime(Timestamp createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public Vehicle withModifyTime(Timestamp modifyTime) {
        this.setModifyTime(modifyTime);
        return this;
    }

    public Vehicle withGasCapacity(int gasCapacity) {
        this.setGasCapacity(gasCapacity);
        return this;
    }

    public Vehicle withMaintainCycle(int maintainCycle) {
        this.setMaintainCycle(maintainCycle);
        return this;
    }

    public Vehicle withCarFrame(String carFrame) {
        this.setCarFrame(carFrame);
        return this;
    }
}
