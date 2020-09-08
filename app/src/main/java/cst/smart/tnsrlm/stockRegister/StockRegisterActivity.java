package cst.smart.tnsrlm.stockRegister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
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

public class StockRegisterActivity extends AppCompatActivity implements Serializable {
    ProgressDialog pDialog;
    String mId = null;

    EditText dateOfStock;
    EditText locationOfWarehouse;
    EditText stockQuantity;
    EditText warehouseReceipt;
    EditText monthlyWarehouseRent;
    EditText secretaryName;
    EditText contact;
    EditText treasurerName;
    EditText warehouseEmployeeName;

    TextView submit;

    StockRegister stockRegister = null;
    private String TAG = getClass().getSimpleName();

    SharedPreferences sharedpreferences;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_register);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        DbMember dbMember = new DbMember(this);
        member = new Gson().fromJson(dbMember.getDataBymemberid(sharedpreferences.getString(memberIdKey, "")).get(1),
                Member.class);

        dateOfStock = (EditText) findViewById(R.id.dateOfStock);
        locationOfWarehouse = (EditText) findViewById(R.id.locationOfWarehouse);
        stockQuantity = (EditText) findViewById(R.id.stockQuantity);
        warehouseReceipt = (EditText) findViewById(R.id.warehouseReceipt);
        monthlyWarehouseRent = (EditText) findViewById(R.id.monthlyWarehouseRent);
        secretaryName = (EditText) findViewById(R.id.secretaryName);
        contact = (EditText) findViewById(R.id.contact);
        treasurerName = (EditText) findViewById(R.id.treasurerName);
        warehouseEmployeeName = (EditText) findViewById(R.id.warehouseEmployeeName);


        submit = (TextView) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockRegister tempbalancedepositbean = new StockRegister(
                        dateOfStock.getText().toString(),
                        locationOfWarehouse.getText().toString(),
                        stockQuantity.getText().toString(),
                        warehouseReceipt.getText().toString(),
                        monthlyWarehouseRent.getText().toString(),
                        secretaryName.getText().toString(),
                        contact.getText().toString(),
                        treasurerName.getText().toString(),
                        warehouseEmployeeName.getText().toString()
                );


                if (stockRegister != null) {
                    tempbalancedepositbean.setId(stockRegister.id);
                }
                String jsonVal = new Gson().toJson(tempbalancedepositbean);
                getCreateTest(jsonVal);
            }
        });

        try {
            stockRegister = (StockRegister) getIntent().getSerializableExtra("object");
            if (stockRegister != null) {
                mId = stockRegister.id;
                dateOfStock.setText(stockRegister.dateOfStock);
                locationOfWarehouse.setText(stockRegister.locationOfWarehouse);
                stockQuantity.setText(stockRegister.stockQuantity);
                warehouseReceipt.setText(stockRegister.warehouseReceipt);
                monthlyWarehouseRent.setText(stockRegister.monthlyWarehouseRent);
                secretaryName.setText(stockRegister.secretaryName);
                contact.setText(stockRegister.contact);
                treasurerName.setText(stockRegister.treasurerName);
                warehouseEmployeeName.setText(stockRegister.warehouseEmployeeName);
            }else{
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date dateTime = new Date();
                dateOfStock.setText(formatter.format(dateTime));
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
                localHashMap.put("name", dateOfStock.getText().toString());
                localHashMap.put("createdby", member.getName());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                localHashMap.put("createdTime", formatter.format(date));
                localHashMap.put("data", data);
                localHashMap.put("dbname", "stockRegister");


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
