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
