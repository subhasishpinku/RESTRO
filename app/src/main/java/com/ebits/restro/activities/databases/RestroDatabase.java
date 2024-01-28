package com.ebits.restro.activities.databases;

/**
 * Created by Satyaki Mukherjee on 8/3/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ebits.restro.activities.model.FinalBillOrderModel;
import com.ebits.restro.activities.model.ItemModel;
import com.ebits.restro.activities.model.ItemOrderModel;
import com.ebits.restro.activities.model.SearchItemModel;

public class RestroDatabase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "RESTRO_DB";

    //TOP ITEMS TABLE NAME
    private static final String TOP_ITEMS = "TOP_ITEMS";
    //SEARCH ITEMS TABLE NAME
    private static final String SEARCH_ITEMS = "SEARCH_ITEMS";
    //ORDER ITEMS TABLE NAME
    private static final String ORDER_ITEMS = "ORDER_ITEMS";
    //FINAL ORDER TABLE NAME
    private static final String FINAL_ORDER_BILL = "FINAL_ORDER";

    public RestroDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * TOP ITEMS TABLE Creation
     **/
    private static final String CREATE_TOP_ITEMS = "CREATE TABLE " + TOP_ITEMS + "("
            + "ITEM_ID" + " TEXT PRIMARY KEY,"
            + "GROUP_NAME" + " TEXT,"
            + "SUB_GROUP_NAME" + " TEXT,"
            + "SUB_GROUP_ID" + " TEXT,"
            + "TYPE_ID" + " TEXT,"
            + "TYPE_NAME" + " TEXT,"
            + "SUB_TYPE_ID" + " TEXT,"
            + "SUB_TYPE_NAME" + " TEXT,"
            + "ITEM_NAME" + " TEXT,"
            + "ITEM_RATE" + " TEXT,"
            + "ITEM_IMAGE" + " TEXT"
            + ")";

    /**
     * SEARCH ITEMS TABLE Creation
     **/
    private static final String CREATE_SEARCH_ITEMS = "CREATE TABLE " + SEARCH_ITEMS + "("
            + "S_ITEM_ID" + " TEXT PRIMARY KEY,"
            + "S_GROUP_NAME" + " TEXT,"
            + "S_GROUP_ID" + " TEXT,"
            + "S_SUB_GROUP_NAME" + " TEXT,"
            + "S_SUB_GROUP_ID" + " TEXT,"
            + "S_TYPE_ID" + " TEXT,"
            + "S_TYPE_NAME" + " TEXT,"
            + "S_SUB_TYPE_ID" + " TEXT,"
            + "S_SUB_TYPE_NAME" + " TEXT,"
            + "S_ITEM_NAME" + " TEXT,"
            + "S_ITEM_RATE" + " TEXT,"
            + "S_ITEM_IMAGE" + " TEXT"
            + ")";

    /**
     * ORDER ITEMS TABLE Creation
     **/
    private static final String CREATE_ORDER_ITEMS = "CREATE TABLE " + ORDER_ITEMS + "("
            + "O_ITEM_ID" + " TEXT PRIMARY KEY,"
            + "O_ITEM_NAME" + " TEXT,"
            + "O_ITEM_RATE" + " TEXT,"
            + "O_ITEM_DESCRIPTION" + " TEXT,"
            + "O_ITEM_VALUE" + " TEXT,"
            + "O_ITEM_CHARGE_OR_NONCHARGE" + " TEXT,"
            + "O_ITEM_QTY" + " TEXT"
            + ")";

    /**
     * FINAL BILL Table Creation
     **/
    private static final String CREATE_FINAL_ORDER_BILL_ITEMS = "CREATE TABLE " + FINAL_ORDER_BILL + "("
            + "BILL_ID" + "INTEGER AUTOINCREMENT PRIMARY KEY ,"
            + "F_ITEM_ID" + " TEXT ,"
            + "F_ITEM_NAME" + " TEXT,"
            + "F_ITEM_RATE" + " TEXT,"
            + "F_ITEM_DESCRIPTION" + " TEXT,"
            + "F_ITEM_VALUE" + " TEXT,"
            + "F_ITEM_CHARGE_OR_NONCHARGE" + " TEXT,"
            + "F_ITEMS_ANY_REMARKS" + " TEXT,"
            + "F_ITEM_QTY" + " TEXT"
            + ")";


    /**
     * Consumer Table INDEXING
     * VERSION 2
     */
    /*private static final String CREATE_CONSUMER_INDEX1 = "CREATE INDEX CONSUMER_INDEX1 ON " + CONSUMER_TABLE+"(" +
            "TOWN_ID,SUPPLIER_ID,CON_ID)";

    private static final String CREATE_CONSUMER_INDEX2 = "CREATE INDEX CONSUMER_INDEX2 ON " + CONSUMER_TABLE+"(" +
            "TOWN_ID,SUPPLIER_ID,CON_NO)";

    private static final String CREATE_CONSUMER_INDEX3 = "CREATE INDEX CONSUMER_INDEX3 ON " + CONSUMER_TABLE+"(" +
            "TOWN_ID,SUPPLIER_ID,CON_METER_NUMBER)";

    private static final String CREATE_CONSUMER_INDEX4 = "CREATE INDEX CONSUMER_INDEX4 ON " + CONSUMER_TABLE+"(" +
            "TOWN_ID,SUPPLIER_ID,CON_SERVICE_CONNECTION_NO)";

    *//** VERSION 3**//*
    private static final String CREATE_LOGIN_INDEX11 = "CREATE INDEX LOGIN_INDEX1 ON " + LOGIN_TABLE+"(" +
            "USER_ID)";

    private static final String CREATE_DTR_INDEX1 = "CREATE INDEX DTR_INDEX1 ON " + DTR_TABLE+"(" +
            "DTR_UPDLOADED_OR_NOT)";

    private static final String CREATE_POLE_INDEX1 = "CREATE INDEX POLE_INDEX1 ON " + POLE_TABLE+"(" +
            "POLE_UPDLOADED_OR_NOT)";

    private static final String CREATE_CONSUMER_INDEX5 = "CREATE INDEX CONSUMER_INDEX5 ON " + CONSUMER_TABLE+"(" +
            "BOOLEAN_SURVEYED_NEW)";

    private static final String CREATE_DTR_INDEX2 = "CREATE INDEX DTR_INDEX2 ON " + DTR_TABLE+"(" +
            "DTR_CODE)";

    private static final String CREATE_POLE_INDEX2 = "CREATE INDEX POLE_INDEX2 ON " + POLE_TABLE+"(" +
            "POLE_CODE)";

    private static final String CREATE_CONSUMER_INDEX6 = "CREATE INDEX CONSUMER_INDEX6 ON " + CONSUMER_TABLE+"(" +
            "CON_METER_NUMBER)";

    private static final String CREATE_CONSUMER_INDEX7 = "CREATE INDEX CONSUMER_INDEX7 ON " + CONSUMER_TABLE+"(" +
            "CON_IS_EDITED)";

    private static final String CREATE_CONSUMER_INDEX8 = "CREATE INDEX CONSUMER_INDEX8 ON " + CONSUMER_TABLE+"(" +
            "CON_TABLE_ID)";*/

    /**
     * All table created here by SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TOP_ITEMS);
        System.out.println(CREATE_TOP_ITEMS);

        db.execSQL(CREATE_SEARCH_ITEMS);
        System.out.println(CREATE_SEARCH_ITEMS);

        db.execSQL(CREATE_ORDER_ITEMS);
        System.out.println(CREATE_ORDER_ITEMS);

        db.execSQL(CREATE_FINAL_ORDER_BILL_ITEMS);
        System.out.println(CREATE_FINAL_ORDER_BILL_ITEMS);

    }

    /**
     * All table upgrade here by SQLiteDatabase
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    /**
     * Insert TOP ITEMS TABLE info
     */
    public synchronized void addTopItems(ItemModel itemModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("ITEM_ID", itemModel.getItemNameId());
        content.put("GROUP_NAME", itemModel.getItemGroup());
        content.put("SUB_GROUP_NAME", itemModel.getItemSubGroupName());
        content.put("SUB_GROUP_ID", itemModel.getItemSubGroupId());
        content.put("TYPE_ID", itemModel.getItemTypeId());
        content.put("TYPE_NAME", itemModel.getItemTypeName());
        content.put("SUB_TYPE_ID", itemModel.getItemSubTypeId());
        content.put("SUB_TYPE_NAME", itemModel.getItemSubTypeName());
        content.put("ITEM_NAME", itemModel.getItemName());
        content.put("ITEM_RATE", itemModel.getItemRate());
        content.put("ITEM_IMAGE", itemModel.getItemImage());

        db.insert(TOP_ITEMS, null, content);
        db.close(); //Close the database Connection
    }

    // Deleting all top items
    public synchronized void deleteAllTopItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TOP_ITEMS);
        db.close();
    }

    /**
     * Insert SEARCH ITEMS TABLE info
     */
    public synchronized void addSearchItems(SearchItemModel searchItemModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("S_ITEM_ID", searchItemModel.getItemNameId());
        content.put("S_GROUP_NAME", searchItemModel.getItemGroup());
        content.put("S_SUB_GROUP_NAME", searchItemModel.getItemSubGroupName());
        content.put("S_SUB_GROUP_ID", searchItemModel.getItemSubGroupId());
        content.put("S_TYPE_ID", searchItemModel.getItemTypeId());
        content.put("S_TYPE_NAME", searchItemModel.getItemTypeName());
        content.put("S_SUB_TYPE_ID", searchItemModel.getItemSubTypeId());
        content.put("S_SUB_TYPE_NAME", searchItemModel.getItemSubTypeName());
        content.put("S_ITEM_NAME", searchItemModel.getItemName());
        content.put("S_ITEM_RATE", searchItemModel.getItemRate());
        content.put("S_ITEM_IMAGE", searchItemModel.getItemImage());

        db.insert(SEARCH_ITEMS, null, content);
        db.close(); //Close the database Connection
    }

    // Deleting all search items
    public synchronized void deleteAllSearchItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + SEARCH_ITEMS);
        db.close();
    }

    /**
     * Insert data of Order table info
     */
    public synchronized void addOrderInfo(ItemOrderModel itemOrderModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("O_ITEM_ID", itemOrderModel.getItemNameId());
        content.put("O_ITEM_NAME", itemOrderModel.getItemName());
        content.put("O_ITEM_RATE", itemOrderModel.getItemRate());
        content.put("O_ITEM_DESCRIPTION", itemOrderModel.getItemDescription());
        content.put("O_ITEM_VALUE", itemOrderModel.getItemValue());
        content.put("O_ITEM_CHARGE_OR_NONCHARGE", itemOrderModel.getItemChargaqbleOrNot());
        content.put("O_ITEM_QTY", itemOrderModel.getItemQty());

        db.insert(ORDER_ITEMS, null, content);
        db.close(); //Close the database Connection
    }


    // Updating single data of Order table info
    public synchronized int updateOrderInfo(ItemOrderModel itemOrderModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("O_ITEM_DESCRIPTION", itemOrderModel.getItemDescription());
        values.put("O_ITEM_VALUE", itemOrderModel.getItemValue());
        values.put("O_ITEM_CHARGE_OR_NONCHARGE", itemOrderModel.getItemChargaqbleOrNot());
        values.put("O_ITEM_QTY", itemOrderModel.getItemQty());

        // updating row
        return db.update(ORDER_ITEMS, values, "O_ITEM_ID" + " = ?",
                new String[]{String.valueOf(itemOrderModel.getItemNameId())});
    }

    // Deleting single  data of Order table info
    public synchronized void deleteSingleOrderInfo(ItemOrderModel itemOrderModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ORDER_ITEMS, "O_ITEM_ID" + " = ?",
                new String[]{String.valueOf(itemOrderModel.getItemNameId())});
        db.close();
    }


    /**
     * Insert data for final bill operation
     */
    public synchronized void addFinalBillInfo(FinalBillOrderModel finalBillOrderModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("F_ITEM_ID", finalBillOrderModel.getItemNameId());
        content.put("F_ITEM_NAME", finalBillOrderModel.getItemName());
        content.put("F_ITEM_RATE", finalBillOrderModel.getItemRate());
        content.put("F_ITEM_DESCRIPTION", finalBillOrderModel.getItemDescription());
        content.put("F_ITEM_VALUE", finalBillOrderModel.getItemValue());
        content.put("F_ITEM_CHARGE_OR_NONCHARGE", finalBillOrderModel.getItemChargaqbleOrNot());
        content.put("F_ITEM_QTY", finalBillOrderModel.getItemQty());
        content.put("F_ITEMS_ANY_REMARKS", finalBillOrderModel.getAnyRemarks());

        db.insert(FINAL_ORDER_BILL, null, content);
        db.close(); //Close the database Connection
    }


    /******************************************************************
     * ****************************************************************
     * Query Operations
     *
     *
     * ****************************************************************
     ******************************************************************/

}

