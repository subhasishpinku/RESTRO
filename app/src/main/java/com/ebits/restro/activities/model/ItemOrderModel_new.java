package com.ebits.restro.activities.model;

/**
 * Created by Admin on 16/10/2017.
 */

public class ItemOrderModel_new {
    private int dtlcount;
    private String orderDate;
    private String serial;
    private String itemCd;
    private String qty;
    private String rate;
    private  String value;
    private String remarks;
    private String location;
    private String tableNum;
    private String userName;
    private String finYr;

    public int getDtlcount(int sizenew) {
        return dtlcount;
    }

    public void setDtlcount(int dtlcount) {
        this.dtlcount = dtlcount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getItemCd() {
        return itemCd;
    }

    public void setItemCd(String itemCd) {
        this.itemCd = itemCd;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTableNum() {
        return tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFinYr() {
        return finYr;
    }

    public void setFinYr(String finYr) {
        this.finYr = finYr;
    }
}
