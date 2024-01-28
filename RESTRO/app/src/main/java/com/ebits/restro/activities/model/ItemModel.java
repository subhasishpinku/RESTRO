package com.ebits.restro.activities.model;

/**
 * Created by Mr. Satyaki Mukherjee on 08/01/16.
 */
public class ItemModel {
    private String itemGroup;
    private String itemSubGroupName;
    private String itemSubGroupId;
    private String itemTypeName;
    private String itemTypeId;
    private String itemSubTypeName;
    private String itemSubTypeId;
    private String itemName;
    private String itemNameId;
    private String itemRate;
    private String itemImage;

    public ItemModel() {
    }

    public ItemModel(String itemGroup,String itemSubGroupName,String itemSubGroupId,String itemTypeName,String itemTypeId,
                     String itemSubTypeName,String itemSubTypeId,String itemName,String itemNameId,String itemRate,String itemImage) {
        this.itemGroup  =   itemGroup;
        this.itemSubGroupName   =   itemSubGroupName;
        this.itemSubGroupId     =   itemSubGroupId;
        this.itemTypeName       =   itemTypeName;
        this.itemTypeId         =   itemTypeId;
        this.itemSubTypeName    =   itemSubTypeName;
        this.itemSubTypeId      =   itemSubTypeId;
        this.itemName           =   itemName;
        this.itemNameId         =   itemNameId;
        this.itemRate           =   itemRate;
        this.itemImage          =   itemImage;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getItemSubGroupName() {
        return itemSubGroupName;
    }

    public void setItemSubGroupName(String itemSubGroupName) {
        this.itemSubGroupName = itemSubGroupName;
    }

    public String getItemSubGroupId() {
        return itemSubGroupId;
    }

    public void setItemSubGroupId(String itemSubGroupId) {
        this.itemSubGroupId = itemSubGroupId;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(String itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getItemSubTypeName() {
        return itemSubTypeName;
    }

    public void setItemSubTypeName(String itemSubTypeName) {
        this.itemSubTypeName = itemSubTypeName;
    }

    public String getItemSubTypeId() {
        return itemSubTypeId;
    }

    public void setItemSubTypeId(String itemSubTypeId) {
        this.itemSubTypeId = itemSubTypeId;
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

}
