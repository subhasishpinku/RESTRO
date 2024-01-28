package com.ebits.restro.activities.utils.printermsg;

public class PrinterSpnModelsItem {
    private String mModelName = "";
    private int mModelConstant = 0;

    public PrinterSpnModelsItem(String modelName, int modelConstant) {
        mModelName = modelName;
        mModelConstant = modelConstant;
    }

    public int getModelConstant() {
        return mModelConstant;
    }

    @Override
    public String toString() {
        return mModelName;
    }
}
