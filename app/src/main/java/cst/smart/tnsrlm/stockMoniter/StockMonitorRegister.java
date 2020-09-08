package cst.smart.tnsrlm.stockMoniter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cst.smart.tnsrlm.R;
import cst.smart.tnsrlm.app.AppConfig;
import cst.smart.tnsrlm.app.AppController;
import cst.smart.tnsrlm.app.DbMember;
import cst.smart.tnsrlm.member.Member;

import static cst.smart.tnsrlm.app.AppConfig.memberIdKey;
import static cst.smart.tnsrlm.app.AppConfig.mypreference;

public class StockMonitorRegister extends AppCompatActivity {
    ProgressDialog pDialog;

    StockMonitor stockMonitor = null;
    private String TAG = getClass().getSimpleName();
    EditText supervisedDate;
    EditText supervisorName;
    MaterialBetterSpinner commodity;
    EditText variety;
    EditText commodityQualityParameters;
    EditText moisture;
    EditText foreignMaterial;
    EditText color;
    EditText damaged;
    EditText firstGradePrice;
    EditText firstGradeKg;
    EditText secondGradePrice;
    EditText secondGradeKg;
    EditText thirdGradePrice;
    EditText thirdGradeKg;

    TextView submit;
    String mId = null;

    SharedPreferences sharedpreferences;
    private static final String[] COMMODITY = new String[]{
            "Red Gram", "Sesame", "Little millet", "Paddy", "Pearl Millet", "Finger Millet", "Groundnut", "Maize",
            "White Sorghum", "Red Sorghum", "Fox Style Millet"
    };
    ArrayAdapter<String> commodityAdapter;
    Member member;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_monitor_register);



        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        DbMember dbMember = new DbMember(this);
        member = new Gson().fromJson(dbMember.getDataBymemberid(sharedpreferences.getString(memberIdKey, "")).get(1),
                Member.class);

        commodity = (MaterialBetterSpinner) findViewById(R.id.commodity);
        commodityAdapter = new ArrayAdapter<String>(StockMonitorRegister.this,
                android.R.layout.simple_dropdown_item_1line, COMMODITY);
        commodity.setAdapter(commodityAdapter);

        supervisedDate = (EditText) findViewById(R.id.supervisedDate);
        supervisorName = (EditText) findViewById(R.id.supervisorName);
        variety = (EditText) findViewById(R.id.variety);
        commodityQualityParameters = (EditText) findViewById(R.id.commodityQualityParameters);
        moisture = (EditText) findViewById(R.id.moisture);
        foreignMaterial = (EditText) findViewById(R.id.foreignMaterial);
        color = (EditText) findViewById(R.id.color);
        damaged = (EditText) findViewById(R.id.damaged);
        firstGradePrice = (EditText) findViewById(R.id.firstGradePrice);
        firstGradeKg = (EditText) findViewById(R.id.firstGradeKg);
        secondGradePrice = (EditText) findViewById(R.id.secondGradePrice);
        secondGradeKg = (EditText) findViewById(R.id.secondGradeKg);
        thirdGradePrice = (EditText) findViewById(R.id.thirdGradePrice);
        thirdGradeKg = (EditText) findViewById(R.id.thirdGradeKg);


        submit = (TextView) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockMonitor temppricebean = new StockMonitor(
                        supervisedDate.getText().toString(),
                        supervisorName.getText().toString(),
                        commodity.getText().toString(),
                        variety.getText().toString(),
                        commodityQualityParameters.getText().toString(),
                        moisture.getText().toString(),
                        foreignMaterial.getText().toString(),
                        color.getText().toString(),
                        damaged.getText().toString(),
                        firstGradePrice.getText().toString(),
                        firstGradeKg.getText().toString(),
                        secondGradePrice.getText().toString(),
                        secondGradeKg.getText().toString(),
                        thirdGradePrice.getText().toString(),
                        thirdGradeKg.getText().toString()
                );
                if (stockMonitor != null) {
                    temppricebean.setId(stockMonitor.id);
                }
                String jsonVal = new Gson().toJson(temppricebean);
                getCreateTest(jsonVal);
            }
        });

        try {
            stockMonitor = (StockMonitor) getIntent().getSerializableExtra("object");
            if (stockMonitor != null) {
                mId = stockMonitor.id;
                supervisedDate.setText(stockMonitor.supervisedDate);
                supervisorName.setText(stockMonitor.supervisorName);
                commodity.setText(stockMonitor.commodity);
                variety.setText(stockMonitor.variety);
                commodityQualityParameters.setText(stockMonitor.commodityQualityParameters);
                moisture.setText(stockMonitor.moisture);
                foreignMaterial.setText(stockMonitor.foreignMaterial);
                color.setText(stockMonitor.color);
                damaged.setText(stockMonitor.damaged);
                firstGradePrice.setText(stockMonitor.firstGradePrice);
                firstGradeKg.setText(stockMonitor.firstGradeKg);
                secondGradePrice.setText(stockMonitor.secondGradePrice);
                secondGradeKg.setText(stockMonitor.secondGradeKg);
                thirdGradePrice.setText(stockMonitor.thirdGradePrice);
                thirdGradeKg.setText(stockMonitor.thirdGradeKg);

            }
        } catch (Exception e) {
            Log.e("xxxxxx", "Something went wrong");
        }

    }


    private void getCreateTest(final String data) {
        this.pDialog.setMessage("Creating...");
        showDialog();
        StringRequest local16 = new StringRequest(1, AppConfig.URL_CREATE_DAtA,
                new Response.Listener<String>() {
                    public void onResponse(String paramString) {
                        Log.d("tag", "Register Response: " + paramString.toString());
                        hideDialog();
                        try {
                            JSONObject localJSONObject1 = new JSONObject(paramString);
                            String str = localJSONObject1.getString("message");
                            if (localJSONObject1.getInt("success") == 1) {
                                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                            return;
                        } catch (JSONException localJSONException) {
                            localJSONException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError paramVolleyError) {
                Log.e("tag", "Fetch Error: " + paramVolleyError.getMessage());
                Toast.makeText(getApplicationContext(), paramVolleyError.getMessage(), Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> localHashMap = new HashMap<String, String>();
                if (mId != null) {
                    localHashMap.put("id", mId);
                }
                localHashMap.put("name", supervisorName.getText().toString());
                localHashMap.put("createdby", member.getName());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                localHashMap.put("createdTime", formatter.format(date));
                localHashMap.put("data", data);
                localHashMap.put("dbname", "stockMonitor");


                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(local16, TAG);
    }


    private void hideDialog() {

        if (this.pDialog.isShowing()) this.pDialog.dismiss();
    }

    private void showDialog() {

        if (!this.pDialog.isShowing()) this.pDialog.show();
    }

}
