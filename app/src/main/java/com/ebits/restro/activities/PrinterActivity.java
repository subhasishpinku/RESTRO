package com.ebits.restro.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ebits.restro.R;
import com.ebits.restro.activities.utils.printermsg.PrinterShowMsg;
import com.ebits.restro.activities.utils.printermsg.PrinterSpnModelsItem;
import com.ebits.restro.activities.utils.tools.GeneralOperationTools;
import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PrinterActivity extends Activity implements View.OnClickListener, ReceiveListener {

    private Context mContext = null;
    private EditText mEditTarget = null;
    private Spinner mSpnSeries = null;
    private Spinner mSpnLang = null;
    private Printer mPrinter = null;
    private GeneralOperationTools   generalOperationTools;
    private String jsonOrder    =   "";
    private SharedPreferences sp;
    private int printer_ser=0,printer_lang=0;
    private String targetIp="192.168.1.200";


//    static Print printer = null;
//    String openDeviceName = "192.168.192.168";
//    int connectionType = Print.DEVTYPE_TCP;
//    int language = com.epson.eposprint.Builder.LANG_EN;
//    String printerName = "TM-T88V";

    //private String jsonOrder    =   "";
//    static void setPrinter(Print obj){
//        printer = obj;
//    }
//
//    static Print getPrinter(){
//
//        return printer;
  //  }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printer_activity_main);

    //    sp = getSharedPreferences(Consts.SP_NAME, MODE_PRIVATE);
//        printer_ser =   sp.getInt("PRINTER_SERIES",printer_ser);
//        printer_lang=   sp.getInt("PRINTER_LANG",printer_lang);
      //  targetIp    =   sp.getString("TARGET_IP",targetIp);
      //  openDeviceName = sp.getString("TARGET_IP",openDeviceName);
        mContext = this;
       System.out.println("mContext--------------"+mContext);
        generalOperationTools   =   GeneralOperationTools.generalOperationToolsInstance();
        System.out.println("generalOperationTools--------------"+generalOperationTools);
        Bundle bundle   =   getIntent().getExtras();
        jsonOrder   =   bundle.getString("JSON_ORDER");
        System.out.println("jsonOrder--------------"+jsonOrder);
        int[] target = {
            R.id.btnDiscovery,
            R.id.btnSampleReceipt,
            R.id.btnSampleCoupon,
            R.id.button_logsettings ,
                R.id.button_getname
        };

        for (int i = 0; i < target.length; i++) {
            Button button = (Button)findViewById(target[i]);
            button.setOnClickListener(this);
        }

        mSpnSeries = (Spinner)findViewById(R.id.spnModel);
        ArrayAdapter<PrinterSpnModelsItem> seriesAdapter = new ArrayAdapter<PrinterSpnModelsItem>(this, android.R.layout.simple_spinner_item);
        seriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_m10), Printer.TM_M10));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_m30), Printer.TM_M30));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_p20), Printer.TM_P20));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_p60), Printer.TM_P60));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_p60ii), Printer.TM_P60II));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_p80), Printer.TM_P80));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_t20), Printer.TM_T20));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_t60), Printer.TM_T60));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_t70), Printer.TM_T70));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_t81), Printer.TM_T81));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_t82), Printer.TM_T82));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_t83), Printer.TM_T83));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_t88), Printer.TM_T88));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_t90), Printer.TM_T90));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_t90kp), Printer.TM_T90KP));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_u220), Printer.TM_U220));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_u330), Printer.TM_U330));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_l90), Printer.TM_L90));
        seriesAdapter.add(new PrinterSpnModelsItem(getString(R.string.printerseries_h6000), Printer.TM_H6000));
        mSpnSeries.setAdapter(seriesAdapter);
        mSpnSeries.setSelection(printer_ser);

        mSpnSeries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

//                SharedPreferences.Editor editor =   sp.edit();
//                editor.putInt("PRINTER_SERIES",position);
//                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpnLang = (Spinner)findViewById(R.id.spnLang);
        ArrayAdapter<PrinterSpnModelsItem> langAdapter = new ArrayAdapter<PrinterSpnModelsItem>(this, android.R.layout.simple_spinner_item);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langAdapter.add(new PrinterSpnModelsItem(getString(R.string.lang_ank), Printer.MODEL_ANK));
        langAdapter.add(new PrinterSpnModelsItem(getString(R.string.lang_japanese), Printer.MODEL_JAPANESE));
        langAdapter.add(new PrinterSpnModelsItem(getString(R.string.lang_chinese), Printer.MODEL_CHINESE));
        langAdapter.add(new PrinterSpnModelsItem(getString(R.string.lang_taiwan), Printer.MODEL_TAIWAN));
        langAdapter.add(new PrinterSpnModelsItem(getString(R.string.lang_korean), Printer.MODEL_KOREAN));
        langAdapter.add(new PrinterSpnModelsItem(getString(R.string.lang_thai), Printer.MODEL_THAI));
        langAdapter.add(new PrinterSpnModelsItem(getString(R.string.lang_southasia), Printer.MODEL_SOUTHASIA));
        mSpnLang.setAdapter(langAdapter);
        mSpnLang.setSelection(printer_lang);

        mSpnLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

//                SharedPreferences.Editor editor =   sp.edit();
//                editor.putInt("PRINTER_LANG",position);
//                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        try {
//            Log.setLogSettings(mContext, Log.PERIOD_TEMPORARY, Log.OUTPUT_STORAGE, null, 0, 1, Log.LOGLEVEL_LOW);
//        }
//        catch (Exception e) {
//            PrinterShowMsg.showException(e, "setLogSettings", mContext);
//        }
//        mEditTarget = (EditText)findViewById(R.id.edtTarget);
//
//        mEditTarget.setText(targetIp);
      //  mEditTarget.setText(openDeviceName);
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {
//        if (data != null && resultCode == RESULT_OK) {
//            String target = data.getStringExtra(getString(R.string.title_target));
//            if (target != null) {
//                EditText mEdtTarget = (EditText)findViewById(R.id.edtTarget);
//                mEdtTarget.setText(target);
//            }
//        }

//        if(data != null){
//            if(resultCode == 1 || resultCode == 2){
//                connectionType = data.getIntExtra("devtype", 0);
//                openDeviceName = data.getStringExtra("ipaddress");
//            }
//            if(resultCode == 2){
//                printerName = data.getStringExtra("printername");
//                language = data.getIntExtra("language", 0);
//            }
//        }
//
//        if (printer != null) {
//            printer.setStatusChangeEventCallback(this);
//            printer.setBatteryStatusChangeEventCallback(this);
//        }
//        updateButtonState();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        outState.putString("openDeviceName", openDeviceName);
//        outState.putInt("connectionType", connectionType);
//        outState.putInt("language", language);
//        outState.putString("printerName", printerName);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

//        openDeviceName = savedInstanceState.getString("openDeviceName");
//        connectionType = savedInstanceState.getInt("connectionType");
//        language = savedInstanceState.getInt("language");
//        printerName = savedInstanceState.getString("printerName");
    }



//    private void updateButtonState() {
//        int[] target1 = {
//                R.id.btnSampleReceipt,
//                R.id.button_getname,
//
//        };
////        int[] target2 = {
////                R.id.button_close,
////                R.id.button_text,
////                R.id.button_image,
////                R.id.button_barcode,
////                R.id.button_2dcode,
////                R.id.button_pagemode,
////                R.id.button_cut,
////                R.id.button_getstatus,
////        };
//        int[] enableTarget = null;
//        int[] disableTarget = null;
////        if(printer == null){
////            enableTarget = target1;
////            //       disableTarget = target2;
////        }else{
////            //  enableTarget = target2;
////            disableTarget = target1;
////        }
//        for(int i = 0; i < enableTarget.length; i++){
//            Button button = (Button)findViewById(enableTarget[i]);
//            button.setEnabled(true);
//        }
////        for(int i = 0; i < disableTarget.length; i++){
////            Button button = (Button)findViewById(disableTarget[i]);
////            button.setEnabled(false);
////        }
//    }



    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnDiscovery:
                intent = new Intent(this, PrinterDiscoveryActivity.class);
                startActivityForResult(intent, 0);
                break;
            case    R.id.button_logsettings:
             //   intent = new Intent(this, LogSettingsActivity.class);
                startActivityForResult(intent, 0);
                break;
            case    R.id.button_getname:
                intent = new Intent(this, GetNameActivity.class);
               startActivityForResult(intent, 0);
                break;
            case R.id.btnSampleReceipt:
                System.out.println("btnSampleReceipt------------------------");
                updateButtonState(false);
                if (!runPrintReceiptSequence()) {
                    System.out.println("runPrintReceiptSequence------------------------");
//                    SharedPreferences.Editor editor =   sp.edit();
//                    editor.putString("TARGET_IP",mEditTarget.getText().toString().trim());
//                    editor.commit();
                    updateButtonState(true);
                }
                break;

            /*case R.id.btnSampleCoupon:
                updateButtonState(false);
                if (!runPrintCouponSequence()) {
                    updateButtonState(true);
                }
                break;
*/


            default:
                // Do nothing
                break;
        }
        if(intent != null){
            //show activity
//            intent.putExtra("devtype", connectionType);
//            intent.putExtra("ipaddress", openDeviceName);
//            intent.putExtra("printername", printerName);
//            intent.putExtra("language", language);
//            startActivityForResult(intent, 0);
        }
    }

    private boolean runPrintReceiptSequence() {
        System.out.println("runPrintReceiptSequence method------------------------");
        if (!initializeObject()) {
            System.out.println("initializeObject ------------------------");
            return false;
        }

        if (!createReceiptData()) {
            System.out.println("createReceiptData ------------------------");
            finalizeObject();
            return false;
        }

        if (!printData()) {
            System.out.println("!printData ------------------------");
            finalizeObject();
            return false;
        }

        return true;
    }





    private boolean createReceiptData() {

        String method = "";
      //  Bitmap logoData = BitmapFactory.decodeResource(getResources(), R.drawable.store);
        StringBuilder textData = new StringBuilder();
        System.out.println("StringBuilder--------------"+textData);
        final int barcodeWidth = 2;
        final int barcodeHeight = 100;
        JSONObject  jsonObject=null;
        try {
            jsonObject  =   new JSONObject(jsonOrder);
            System.out.println("Printer JSON-- "+jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mPrinter == null) {
            return false;
        }
          System.out.println("mPrinter--------------"+mPrinter);
        try {

//            method = "addTextAlign";
//            mPrinter.addTextAlign(Printer.ALIGN_CENTER);

       //     method = "addImage";
//            mPrinter.addImage(logoData, 0, 0,
//                              logoData.getWidth(),
//                              logoData.getHeight(),
//                              Printer.COLOR_1,
//                              Printer.MODE_MONO,
//                              Printer.HALFTONE_DITHER,
//                              Printer.PARAM_DEFAULT,
//                              Printer.COMPRESS_AUTO);

      //     method = "addFeedLine";
       //     mPrinter.addFeedLine(1);
       //     textData.append("S MUKHERJEE 123 (555) 555 – 5555\n");

            textData.append("Subhasish");
            System.out.println("Subhasish--------------------"+textData);

//            textData.append("STORE DIRECTOR – Satyaki Mukherjee\n");
//            textData.append("\n");
//            textData.append(generalOperationTools.getCurrentTimeStamp()+" KOT #- "+jsonObject.getString("KOT_NO")+"\n");
//            textData.append("TABLE #-"+jsonObject.getString("TABLE_NO")+", "+jsonObject.getString("SESSION_DETAILS")+", \n"+jsonObject.getString("OUTLET_DETAILS")+"\n");
//            textData.append("------------------------------\n");
//            method = "addText";
            mPrinter.addText(textData.toString());
            System.out.println("addtext--------------------"+mPrinter);
            textData.delete(0, textData.length());
            System.out.println("delete--------------------"+textData);
//
//            JSONArray   orderArray  =   jsonObject.getJSONArray("ORDER_ITEMS");
//            for(int i=0;i<orderArray.length();i++){
//                JSONObject  orderJson   =   orderArray.getJSONObject(i);
//
//                textData.append(orderJson.getString("ITEM_ID")+" "+orderJson.getString("ITEM_NAME")+"("+orderJson.getString("ITEM_DES")+"), QTY-"
//                        +orderJson.getString("ITEM_QTY")+" RATE- "+orderJson.getString("ITEM_RATE")+" R "+orderJson.getString("ITEM_VALUE")+" R\n");
//            }
//            textData.append("------------------------------\n");
//            method = "addText";
//            mPrinter.addText(textData.toString());
//            textData.delete(0, textData.length());
//
//            textData.append("SUBTOTAL              "+jsonObject.getString("TOT_PRICE")+" R\n");
//            double taxPrice= 14.43;
//            double subTotal=Double.valueOf(jsonObject.getString("TOT_PRICE").trim());
//            double  taxAmt= (subTotal*taxPrice)/100.00;
//            double  total= subTotal+taxAmt;
//
//            textData.append("TAX      "+taxPrice+"%     "+taxAmt+" R\n");
//            method = "addText";
//            mPrinter.addText(textData.toString());
//            textData.delete(0, textData.length());
//
////            method = "addTextSize";
////            mPrinter.addTextSize(2, 2);
////            method = "addText";
//            mPrinter.addText("TOTAL    "+total+ "R\n");
////            method = "addTextSize";
////            mPrinter.addTextSize(1, 1);
////            method = "addFeedLine";
////            mPrinter.addFeedLine(1);
//
//            textData.append("------------------------------\n");
////            method = "addText";
////            mPrinter.addText(textData.toString());
////            textData.delete(0, textData.length());
//
//            textData.append("Any Remarks\n");
//            textData.append(jsonObject.getString("REMARKS").trim()+"\n");
//            textData.append("Thank You! Visit Again.\n");
////            method = "addText";
//            mPrinter.addText(textData.toString());
//            textData.delete(0, textData.length());
//            method = "addFeedLine";
//            mPrinter.addFeedLine(2);

//            method = "addBarcode";
//            mPrinter.addBarcode("01209457",
//                                Printer.BARCODE_CODE39,
//                                Printer.HRI_BELOW,
//                                Printer.FONT_A,
//                                barcodeWidth,
//                                barcodeHeight);
//
//            method = "addCut";
//            mPrinter.addCut(Printer.CUT_FEED);
        }
        catch (Exception e) {
            PrinterShowMsg.showException(e, method, mContext);
            System.out.println("PrinterShowMsg--------------------");
            return false;
        }

        textData = null;

        return true;
    }

    private boolean runPrintCouponSequence() {
        if (!initializeObject()) {
            return false;
        }

        if (!createCouponData()) {
            finalizeObject();
            return false;
        }

        if (!printData()) {
            finalizeObject();
            return false;
        }

        return true;
    }

    private boolean createCouponData() {
        String method = "";
        Bitmap coffeeData = BitmapFactory.decodeResource(getResources(), R.drawable.coffee);
        Bitmap wmarkData = BitmapFactory.decodeResource(getResources(), R.drawable.wmark);

        final int barcodeWidth = 2;
        final int barcodeHeight = 64;
        final int pageAreaHeight = 500;
        final int pageAreaWidth = 500;
        final int fontAHeight = 24;
        final int fontAWidth = 12;
        final int barcodeWidthPos = 110;
        final int barcodeHeightPos = 70;

        if (mPrinter == null) {
            return false;
        }

        try {
            method = "addPageBegin";
            mPrinter.addPageBegin();

            method = "addPageArea";
            mPrinter.addPageArea(0, 0, pageAreaWidth, pageAreaHeight);

            method = "addPageDirection";
            mPrinter.addPageDirection(Printer.DIRECTION_TOP_TO_BOTTOM);

            method = "addPagePosition";
            mPrinter.addPagePosition(0, coffeeData.getHeight());

            method = "addImage";
            mPrinter.addImage(coffeeData, 0, 0, coffeeData.getWidth(), coffeeData.getHeight(), Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, 3, Printer.PARAM_DEFAULT);

            method = "addPagePosition";
            mPrinter.addPagePosition(0, wmarkData.getHeight());

            method = "addImage";
            mPrinter.addImage(wmarkData, 0, 0, wmarkData.getWidth(), wmarkData.getHeight(), Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT);

            method = "addPagePosition";
            mPrinter.addPagePosition(fontAWidth * 4, (pageAreaHeight / 2) - (fontAHeight * 2));

            method = "addTextSize";
            mPrinter.addTextSize(3, 3);

            method = "addTextStyle";
            mPrinter.addTextStyle(Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.TRUE, Printer.PARAM_DEFAULT);

            method = "addTextSmooth";
            mPrinter.addTextSmooth(Printer.TRUE);

            method = "addText";
            mPrinter.addText("FREE Coffee\n");

            method = "addPagePosition";
            mPrinter.addPagePosition((pageAreaWidth / barcodeWidth) - barcodeWidthPos, coffeeData.getHeight() + barcodeHeightPos);

            method = "addBarcode";
            mPrinter.addBarcode("01234567890", Printer.BARCODE_UPC_A, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, barcodeWidth, barcodeHeight);

            method = "addPageEnd";
            mPrinter.addPageEnd();

            method = "addCut";
            mPrinter.addCut(Printer.CUT_FEED);
        }
        catch (Exception e) {
            PrinterShowMsg.showException(e, method, mContext);
            return false;
        }

        return true;
    }

    private boolean printData() {
        System.out.println("printData----------------");
        if (mPrinter == null) {
            return false;

        }

        if (!connectPrinter()) {
            System.out.println("connectPrinter----------------");
            return false;
        }

        PrinterStatusInfo status = mPrinter.getStatus();
        System.out.println("PrinterStatusInfo status----------------"+status);
        dispPrinterWarnings(status);

        if (!isPrintable(status)) {
            PrinterShowMsg.showMsg(makeErrorMessage(status), mContext);
            try {
                mPrinter.disconnect();
                System.out.println(" mPrinter-disconnect----------------"+mPrinter);

            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        try {
            mPrinter.sendData(Printer.PARAM_DEFAULT);
            System.out.println(" mPrinter-sendData----------------"+mPrinter);
        }
        catch (Exception e) {
            PrinterShowMsg.showException(e, "sendData", mContext);
            try {
                mPrinter.disconnect();
                System.out.println("  mPrinter.disconnect----------------"+mPrinter);
            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        return true;
    }

    private boolean initializeObject() {
     System.out.println("initializeObject1-----------------------------------");
        try {
            mPrinter = new Printer(((PrinterSpnModelsItem) mSpnSeries.getSelectedItem()).getModelConstant(),
                    ((PrinterSpnModelsItem) mSpnLang.getSelectedItem()).getModelConstant(),
                    mContext);
        }
        catch (Exception e) {
            PrinterShowMsg.showException(e, "Printer", mContext);
            return false;
        }

        mPrinter.setReceiveEventListener(this);

        return true;
    }

    private void finalizeObject() {
        System.out.println("finalizeObject ------------------------");
        if (mPrinter == null) {
            return;
        }

     //   mPrinter.clearCommandBuffer();

        mPrinter.setReceiveEventListener(null);
        System.out.println("mPrinter ------------------------"+mPrinter);
        mPrinter = null;
    }

    private boolean connectPrinter() {
        System.out.println("connectPrinter ------------------------");
        boolean isBeginTransaction = false;

        if (mPrinter == null) {
            return false;
        }

        try {
            mPrinter.connect(mEditTarget.getText().toString(), Printer.PARAM_DEFAULT);
        }
        catch (Exception e) {
            PrinterShowMsg.showException(e, "connect", mContext);
            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        }
        catch (Exception e) {
            PrinterShowMsg.showException(e, "beginTransaction", mContext);
        }

        if (isBeginTransaction == false) {
            try {
                mPrinter.disconnect();
            }
            catch (Epos2Exception e) {
                // Do nothing
                return false;
            }
        }

        return true;
    }

    private void disconnectPrinter() {
        if (mPrinter == null) {
            return;
        }

        try {
            mPrinter.endTransaction();
        }
        catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    PrinterShowMsg.showException(e, "endTransaction", mContext);
                }
            });
        }

        try {
            mPrinter.disconnect();
        }
        catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    PrinterShowMsg.showException(e, "disconnect", mContext);
                }
            });
        }

        finalizeObject();
    }

    private boolean isPrintable(PrinterStatusInfo status) {
        if (status == null) {
            return false;
        }

        if (status.getConnection() == Printer.FALSE) {
            return false;
        }
        else if (status.getOnline() == Printer.FALSE) {
            return false;
        }
        else {
            ;//print available
        }

        return true;
    }

    private String makeErrorMessage(PrinterStatusInfo status) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += getString(R.string.handlingmsg_err_autocutter);
            msg += getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += getString(R.string.handlingmsg_err_battery_real_end);
        }

        return msg;
    }

    private void dispPrinterWarnings(PrinterStatusInfo status) {
        EditText edtWarnings = (EditText)findViewById(R.id.edtWarnings);
        String warningsMsg = "";

        if (status == null) {
            return;
        }

        if (status.getPaper() == Printer.PAPER_NEAR_END) {
            warningsMsg += getString(R.string.handlingmsg_warn_receipt_near_end);
        }

        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_1) {
            warningsMsg += getString(R.string.handlingmsg_warn_battery_near_end);
        }

        edtWarnings.setText(warningsMsg);
    }

    private void updateButtonState(boolean state) {
        Button btnReceipt = (Button)findViewById(R.id.btnSampleReceipt);
        Button btnCoupon = (Button)findViewById(R.id.btnSampleCoupon);
        btnReceipt.setEnabled(state);
        btnCoupon.setEnabled(state);
    }

    @Override
    public void onPtrReceive(final Printer printerObj, final int code, final PrinterStatusInfo status, final String printJobId) {
        runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {
                PrinterShowMsg.showResult(code, makeErrorMessage(status), mContext);

                dispPrinterWarnings(status);

                updateButtonState(true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disconnectPrinter();
                    }
                }).start();
            }
        });
    }

//    @Override
//    public void onStatusChangeEvent(final String deviceName, final int status) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public synchronized void run() {
//                ShowMsg.showStatusChangeEvent(deviceName, status, PrinterActivity.this);
//            }
//        });
//    }
//
//    @Override
//    public void onBatteryStatusChangeEvent(final String deviceName, final int battery) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public synchronized void run() {
//                ShowMsg.showBatteryStatusChangeEvent(deviceName, battery, PrinterActivity.this);
//            }
//        });
//    }
}
