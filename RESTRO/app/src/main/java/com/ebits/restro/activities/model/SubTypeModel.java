package com.ebits.restro.activities.model;

/**
 * Created by Mr. Satyaki Mukherjee on 08/07/16.
 */
public class SubTypeModel {
    private String subTypeName;
    private String subTypepId;
    private String subTypepImage;

    public SubTypeModel() {
    }

    public SubTypeModel(String subTypeName, String subTypepId, String subTypepImage) {
        this.subTypeName = subTypeName;
        this.subTypepId = subTypepId;
        this.subTypepImage = subTypepImage;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public String getSubTypepId() {
        return subTypepId;
    }

    public void setSubTypepId(String subTypepId) {
        this.subTypepId = subTypepId;
    }

    public String getSubTypepImage() {
        return subTypepImage;
    }

    public void setSubTypepImage(String subTypepImage) {
        this.subTypepImage = subTypepImage;
    }
}
