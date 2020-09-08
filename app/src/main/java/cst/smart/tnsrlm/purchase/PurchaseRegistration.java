package cst.smart.tnsrlm.purchase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cst.smart.tnsrlm.R;
import cst.smart.tnsrlm.app.AppConfig;
import cst.smart.tnsrlm.app.AppController;
import cst.smart.tnsrlm.app.DbMember;
import cst.smart.tnsrlm.app.HeaderFooterPageEvent;
import cst.smart.tnsrlm.member.Member;

import static cst.smart.tnsrlm.app.AppConfig.memberIdKey;
import static cst.smart.tnsrlm.app.AppConfig.mypreference;

public class PurchaseRegistration extends AppCompatActivity {
    ProgressDialog pDialog;

    MaterialBetterSpinner crops;
    MaterialBetterSpinner grade;
    EditText farmerName;
    EditText variety;
    EditText humidity;
    EditText pricePerKg;
    EditText purchasedQuantity;
    EditText totalPrice;
    EditText itemsRejected;
    EditText reasonsRejected;
    TextView submit;

    String mId = null;
    Purchase purchase = null;
    private String TAG = getClass().getSimpleName();
    SharedPreferences sharedpreferences;
    private static final String[] COMMODITY = new String[]{
            "Red Gram", "Sesame", "Little millet", "Paddy", "Pearl Millet", "Finger Millet", "Groundnut", "Maize",
            "White Sorghum", "Red Sorghum", "Fox Style Millet"
    };
    ArrayAdapter<String> commodityAdapter;
    private static final String[] GRADE = new String[]{
            "A", "B", "C", "Mix"
    };

    ArrayAdapter<String> gradeAdapter;
    Member member;

    TextView billNumber, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_purchase);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        DbMember dbMember = new DbMember(this);
        member = new Gson().fromJson(dbMember.getDataBymemberid(sharedpreferences.getString(memberIdKey, "")).get(1),
                Member.class);

        crops = (MaterialBetterSpinner) findViewById(R.id.crops);
        commodityAdapter = new ArrayAdapter<String>(PurchaseRegistration.this,
                android.R.layout.simple_dropdown_item_1line, COMMODITY);
        crops.setAdapter(commodityAdapter);

        grade = (MaterialBetterSpinner) findViewById(R.id.grade);
        gradeAdapter = new ArrayAdapter<String>(PurchaseRegistration.this,
                android.R.layout.simple_dropdown_item_1line, GRADE);
        grade.setAdapter(gradeAdapter);


        billNumber = (TextView) findViewById(R.id.billNumber);
        date = (TextView) findViewById(R.id.date);

        farmerName = (EditText) findViewById(R.id.farmerName);
        variety = (EditText) findViewById(R.id.variety);
        humidity = (EditText) findViewById(R.id.humidity);
        pricePerKg = (EditText) findViewById(R.id.pricePerKg);
        purchasedQuantity = (EditText) findViewById(R.id.purchasedQuantity);
        totalPrice = (EditText) findViewById(R.id.totalPrice);
        itemsRejected = (EditText) findViewById(R.id.itemsRejected);
        reasonsRejected = (EditText) findViewById(R.id.reasonsRejected);

        submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Purchase temppurchasebean = new Purchase(
                        date.getText().toString(),
                        crops.getText().toString(),
                        farmerName.getText().toString(),
                        variety.getText().toString(),
                        grade.getText().toString(),
                        humidity.getText().toString(),
                        pricePerKg.getText().toString(),
                        purchasedQuantity.getText().toString(),
                        totalPrice.getText().toString(),
                        itemsRejected.getText().toString(),
                        reasonsRejected.getText().toString()
                );
                if (purchase != null) {
                    temppurchasebean.setId(purchase.id);
                }
                String jsonVal = new Gson().toJson(temppurchasebean);
                getCreateTest(jsonVal);
            }
        });

        try {
            purchase = (Purchase) getIntent().getSerializableExtra("object");
            if (purchase != null) {
                mId = purchase.id;
                billNumber.setText("Record No: " + mId);
                if (purchase.getDate() != null) {
                    date.setText(purchase.date);
                }
                if (purchase.getGrade() != null) {
                    grade.setText(purchase.grade);
                }

                crops.setText(purchase.crops);
                farmerName.setText(purchase.farmerName);
                variety.setText(purchase.variety);
                humidity.setText(purchase.humidity);
                pricePerKg.setText(purchase.pricePerKg);
                purchasedQuantity.setText(purchase.purchasedQuantity);
                totalPrice.setText(purchase.totalPrice);
                itemsRejected.setText(purchase.itemsRejected);
                reasonsRejected.setText(purchase.reasonsRejected);
            }else{
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date dateTime = new Date();
                date.setText(formatter.format(dateTime));
            }
        } catch (Exception e) {
            Log.e("xxxxxx", "Something went wrong");
        }


        pricePerKg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (purchasedQuantity.getText().toString().length() > 0 && s.toString().length() > 0) {
                    totalPrice.setText(String.valueOf(Float.parseFloat(purchasedQuantity.getText().toString())
                            * Float.parseFloat(s.toString())) + "0");
                }
            }
        });

        purchasedQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (pricePerKg.getText().toString().length() > 0 && s.toString().length() > 0) {
                    totalPrice.setText(String.valueOf(Float.parseFloat(pricePerKg.getText().toString())
                            * Float.parseFloat(s.toString())) + "0");
                }
            }
        });
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
                localHashMap.put("name", farmerName.getText().toString());
                localHashMap.put("createdby", member.getName());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                localHashMap.put("createdTime", formatter.format(date));
                localHashMap.put("data", data);
                localHashMap.put("dbname", "purchase");


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
