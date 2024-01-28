package com.ebits.restro.activities.model;

/**
 * Created by Mr. Satyaki Mukherjee on 08/07/16.
 */
public class SubGroupModel {
    private String subGroupName;
    private String subGroupId;
    private String subGroupImage;

    public SubGroupModel() {
    }

    public SubGroupModel(String subGroupName, String subGroupId, String subGroupImage) {
        this.subGroupName = subGroupName;
        this.subGroupId = subGroupId;
        this.subGroupImage = subGroupImage;
    }

    public String getSubGroupName() {
        return subGroupName;
    }

    public void setSubGroupName(String subGroupName) {
        this.subGroupName = subGroupName;
    }

    public String getSubGroupId() {
        return subGroupId;
    }

    public void setSubGroupId(String subGroupId) {
        this.subGroupId = subGroupId;
    }

    public String getSubGroupImage() {
        return subGroupImage;
    }

    public void setSubGroupImage(String subGroupImage) {
        this.subGroupImage = subGroupImage;
    }
}
