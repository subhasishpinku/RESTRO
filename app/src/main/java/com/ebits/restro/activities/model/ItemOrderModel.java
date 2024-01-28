package com.ebits.restro.activities.model;

/**
 * Created by Mr. Satyaki Mukherjee on 08/01/16.
 */
public class ItemOrderModel {

    private String itemName;
    private String itemNameId;
    private String itemRate;
    private String itemImage;
    private String itemDescription;
    private String itemValue;
    private String itemChargaqbleOrNot;
    private String itemQty;
    private String charge;
    private String itemcd;
    private String pax;

    public String getPax() {
        return pax;
    }

    public void setPax(String pax) {
        this.pax = pax;
    }

    String tablenum;
    String kotnumber;
    String item_kot;
    String qty_kot;
    String rate_kot;
    String value_kot;
    String update;
    String itemdescrr;
    private boolean isSelected;

    public String getTablenum() {
        return tablenum;
    }

    public void setTablenum(String tablenum) {
        this.tablenum = tablenum;
    }

    public String getKotnumber() {
        return kotnumber;
    }

    public void setKotnumber(String kotnumber) {
        this.kotnumber = kotnumber;
    }

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public ItemOrderModel() {
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {

        this.itemDescription = itemDescription;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNameId() {
        return itemNameId;
    }

    public void setItemNameId(String itemNameId) {
        this.itemNameId = itemNameId;
    }
    public String getItemcd() {
        return itemcd;
    }

    public void setItemcd(String itemcd) {
        this.itemcd = itemcd;
    }

    public String getItemRate() {
        return itemRate;
    }

    public void setItemRate(String itemRate) {
        this.itemRate = itemRate;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemChargaqbleOrNot() {
        return itemChargaqbleOrNot;
    }

    public void setItemChargaqbleOrNot(String itemChargaqbleOrNot) {
        this.itemChargaqbleOrNot = itemChargaqbleOrNot;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }


}
