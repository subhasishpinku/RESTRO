package com.ebits.restro.activities.model;

/**
 * Created by Mr. Satyaki Mukherjee on 08/07/16.
 */
public class TypeModel {
    private String typeName;
    private String typepId;
    private String typepImage;

    public TypeModel() {
    }

    public TypeModel(String typeName, String typepId, String typepImage) {
        this.typeName = typeName;
        this.typepId = typepId;
        this.typepImage = typepImage;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypepId() {
        return typepId;
    }

    public void setTypepId(String typepId) {
        this.typepId = typepId;
    }

    public String getTypepImage() {
        return typepImage;
    }

    public void setTypepImage(String typepImage) {
        this.typepImage = typepImage;
    }
}
