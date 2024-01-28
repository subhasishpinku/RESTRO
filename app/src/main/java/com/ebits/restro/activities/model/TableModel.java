package com.ebits.restro.activities.model;

/**
 * Created by Mr. Satyaki Mukherjee on 18/07/16.
 */
public class TableModel {
    private String tableStatus;
    private String tableCategory;
    private String tableId;
    private String kotnumber;
    String item_kot;
    String qty_kot;
    String rate_kot;
    String value_kot;
    String update;
    String itemdescrr;

    public String getItem_kot() {
        return item_kot;
    }

    public void setItem_kot(String item_kot) {
        this.item_kot = item_kot;
    }

    public String getQty_kot() {
        return qty_kot;
    }

    public void setQty_kot(String qty_kot) {
        this.qty_kot = qty_kot;
    }

    public String getRate_kot() {
        return rate_kot;
    }

    public void setRate_kot(String rate_kot) {
        this.rate_kot = rate_kot;
    }

    public String getValue_kot() {
        return value_kot;
    }

    public void setValue_kot(String value_kot) {
        this.value_kot = value_kot;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getItemdescrr() {
        return itemdescrr;
    }

    public void setItemdescrr(String itemdescrr) {
        this.itemdescrr = itemdescrr;
    }

    public String getKotnumber() {
        return kotnumber;
    }

    public void setKotnumber(String kotnumber) {
        this.kotnumber = kotnumber;
    }

    public TableModel() {
    }

    public TableModel(String tableStatus, String tableCategory, String tableId) {
        this.tableStatus = tableStatus;
        this.tableCategory = tableCategory;
        this.tableId = tableId;
    }

    public String getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }

    public String getTableCategory() {
        return tableCategory;
    }

    public void setTableCategory(String tableCategory) {
        this.tableCategory = tableCategory;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

}
