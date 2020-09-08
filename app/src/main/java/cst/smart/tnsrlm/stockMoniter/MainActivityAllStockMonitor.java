package cst.smart.tnsrlm.stockMoniter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cst.smart.tnsrlm.R;
import cst.smart.tnsrlm.app.AppConfig;
import cst.smart.tnsrlm.app.AppController;
import cst.smart.tnsrlm.app.HeaderFooterPageEvent;
import cst.smart.tnsrlm.purchase.MainActivityAllPurchase;
import cst.smart.tnsrlm.purchase.Purchase;
import cst.smart.tnsrlm.purchase.PurchaseRegistration;
import cst.smart.tnsrlm.stockRegister.StockRegisterActivity;

import static cst.smart.tnsrlm.app.AppConfig.mypreference;


public class MainActivityAllStockMonitor extends AppCompatActivity {


    private ProgressDialog pDialog;
    private RecyclerView mastersList;
    private MasterAllStockMonitorAdapter mRecyclerAdapterMaster;
    private ArrayList<StockMonitor> itemArrayList = new ArrayList<>();
    SharedPreferences sharedpreferences;


    TextView firstGradePrice;
    TextView secondGradePrice;
    TextView thirdGradePrice;
    TextView firstGradeKg;
    TextView secondGradeKg;
    TextView thirdGradeKg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stockcontent_all_survey);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        firstGradePrice = (TextView) findViewById(R.id.firstGradePrice);
        secondGradePrice = (TextView) findViewById(R.id.secondGradePrice);
        thirdGradePrice = (TextView) findViewById(R.id.thirdGradePrice);

        firstGradeKg = (TextView) findViewById(R.id.firstGradeKg);
        secondGradeKg = (TextView) findViewById(R.id.secondGradeKg);
        thirdGradeKg = (TextView) findViewById(R.id.thirdGradeKg);


        getSupportActionBar().setTitle("Stock Monitor Register");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);


        FloatingActionButton addMaster = (FloatingActionButton) findViewById(R.id.addSurvey);
        addMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAllStockMonitor.this, StockMonitorRegister.class);
                startActivity(intent);
            }
        });
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mastersList = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerAdapterMaster = new MasterAllStockMonitorAdapter(this, itemArrayList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mastersList.setLayoutManager(thirdManager);
        mastersList.setAdapter(mRecyclerAdapterMaster);


    }

    private void getAllData() {
        String tag_string_req = "req_register";
        pDialog.setMessage("Validating ...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register Response: ", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONArray jsonArray = jObj.getJSONArray("datas");
                        itemArrayList = new ArrayList<>();
                        float firstGradePriceTemp=0;
                        float secondGradePriceTemp=0;
                        float thirdGradePriceTemp=0;

                        float firstGradeKgTemp=0;
                        float secondGradeKgTemp=0;
                        float thirdGradeKgTemp=0;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject dataObject = jsonArray.getJSONObject(i);
                                StockMonitor farmer = new Gson().fromJson(dataObject.getString("data"), StockMonitor.class);
                                farmer.setId(dataObject.getString("id"));
                                itemArrayList.add(farmer);

                                firstGradePriceTemp=firstGradePriceTemp+
                                        Float.parseFloat(farmer.firstGradePrice);
                                secondGradePriceTemp=secondGradePriceTemp+
                                        Float.parseFloat(farmer.secondGradePrice);
                                thirdGradePriceTemp=thirdGradePriceTemp+
                                        Float.parseFloat(farmer.thirdGradePrice);


                                firstGradeKgTemp=firstGradeKgTemp+
                                        Float.parseFloat(farmer.firstGradeKg);
                                secondGradeKgTemp=secondGradeKgTemp+
                                        Float.parseFloat(farmer.secondGradeKg);
                                thirdGradeKgTemp=thirdGradeKgTemp+
                                        Float.parseFloat(farmer.thirdGradeKg);

                            } catch (Exception e) {
                                Log.e("xxxxxxxxxxx", e.toString());
                            }
                        }
                        getSupportActionBar().setSubtitle(String.valueOf(itemArrayList.size()) + " Stock Register Details");
                        mRecyclerAdapterMaster.notifyData(itemArrayList);


                        if (itemArrayList.size() == 0) {
                            Intent intent = new Intent(MainActivityAllStockMonitor.this, StockMonitorRegister.class);
                            startActivity(intent);
                        }else {
                            firstGradePrice.setText(String.valueOf(firstGradeKgTemp/itemArrayList.size()));
                            secondGradePrice.setText(String.valueOf(secondGradeKgTemp/itemArrayList.size()));
                            thirdGradePrice.setText(String.valueOf(thirdGradePriceTemp/itemArrayList.size()));

                            firstGradeKg.setText(String.valueOf(firstGradeKgTemp));
                            secondGradeKg.setText(String.valueOf(secondGradeKgTemp));
                            thirdGradeKg.setText(String.valueOf(thirdGradeKgTemp));

                        }
                    } else {
                        Intent intent = new Intent(MainActivityAllStockMonitor.this, StockMonitorRegister.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Log.e("xxxxxxxxxxx", e.toString());
                }
                hideDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),
                        "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap localHashMap = new HashMap();
                localHashMap.put("key", "stockMonitor");
                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void hideDialog() {
        if (this.pDialog.isShowing())
            this.pDialog.dismiss();
    }


    private void showDialog() {
        if (!this.pDialog.isShowing())
            this.pDialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        getAllData();

    }


    private boolean isValidString(String string) {

        if (string == null) {
            return false;
        } else if (string.length() <= 0) {
            return false;
        } else if (string.startsWith("http")) {
            return false;
        }

        return true;
    }

    public String getfilename_from_path(String path) {
        return path.substring(path.lastIndexOf('/') + 1, path.length());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.print) {
            printFunction();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void printFunction() {
        pDialog.setMessage("Printing Please wait...");
        showDialog();
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            Log.d("PDFCreator", "PDF Path: " + path);
            File file = new File(dir, "demo" + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            Document document = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, fOut);
            Rectangle rect = new Rectangle(175, 20, 530, 800);
            pdfWriter.setBoxSize("art", rect);

            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.cst_pdf);
            Bitmap bu = BitmapFactory.decodeResource(getResources(), R.drawable.cst_pdf);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bu.compress(Bitmap.CompressFormat.PNG, 100, stream1);
            byte[] byteArray1 = stream1.toByteArray();

            HeaderFooterPageEvent event = new HeaderFooterPageEvent(Image.getInstance(byteArray),
                    Image.getInstance(byteArray1));
            pdfWriter.setPageEvent(event);

            document.open();
            AppConfig.addMetaData(document);
            // AppConfig.addTitlePage(document);
            AppConfig.addContentMoniter(document, itemArrayList, MainActivityAllStockMonitor.this);
            document.close();


        } catch (Error | Exception e) {
            e.printStackTrace();
        }
        hideDialog();

        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                getApplicationContext().getPackageName() + ".cst.smart.tnsrlm.provider",
                new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF/"
                        + "demo" + ".pdf"));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(photoURI
                , "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);

    }

}


