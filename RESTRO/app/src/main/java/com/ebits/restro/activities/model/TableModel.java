package com.ebits.restro.activities.model;

/**
 * Created by Mr. Satyaki Mukherjee on 18/07/16.
 */
public class TableModel {
    private String tableStatus;
    private String tableCategory;
    private String tableId;

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
