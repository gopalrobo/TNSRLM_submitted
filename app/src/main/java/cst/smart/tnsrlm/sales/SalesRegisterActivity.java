package cst.smart.tnsrlm.sales;

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

public class SalesRegisterActivity extends AppCompatActivity {
    ProgressDialog pDialog;

    EditText date;
    EditText buyerName;
    EditText commodity;
    EditText pricePerKg;
    EditText purchasedQuantity;
    EditText totalAmount;
    EditText bankTransferDate;
    EditText soldBy;
    EditText warehouseReceiptNumber;

    TextView submit;

    String mId = null;

    Sales sales = null;
    private String TAG = getClass().getSimpleName();

    SharedPreferences sharedpreferences;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesregister);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        DbMember dbMember = new DbMember(this);
        member = new Gson().fromJson(dbMember.getDataBymemberid(sharedpreferences.getString(memberIdKey, "")).get(1),
                Member.class);

        date = (EditText) findViewById(R.id.date);
        buyerName = (EditText) findViewById(R.id.buyerName);
        commodity = (EditText) findViewById(R.id.commodity);
        pricePerKg = (EditText) findViewById(R.id.pricePerKg);
        purchasedQuantity = (EditText) findViewById(R.id.purchasedQuantity);
        totalAmount = (EditText) findViewById(R.id.totalAmount);
        bankTransferDate = (EditText) findViewById(R.id.bankTransferDate);
        soldBy = (EditText) findViewById(R.id.soldBy);
        warehouseReceiptNumber = (EditText) findViewById(R.id.warehouseReceiptNumber);

        submit = (TextView) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sales tempsalesbean = new Sales(
                        date.getText().toString(),
                        buyerName.getText().toString(),
                        commodity.getText().toString(),
                        pricePerKg.getText().toString(),
                        purchasedQuantity.getText().toString(),
                        totalAmount.getText().toString(),
                        bankTransferDate.getText().toString(),
                        soldBy.getText().toString(),
                        warehouseReceiptNumber.getText().toString()
                );


                if (sales != null) {
                    tempsalesbean.setId(sales.id);
                }
                String jsonVal = new Gson().toJson(tempsalesbean);
                getCreateTest(jsonVal);
            }
        });
        try {
            sales = (Sales) getIntent().getSerializableExtra("object");
            if (sales != null) {
                mId=sales.id;

                date.setText(sales.date);
                buyerName.setText(sales.buyerName);
                commodity.setText(sales.commodity);
                pricePerKg.setText(sales.pricePerKg);
                purchasedQuantity.setText(sales.purchasedQuantity);
                totalAmount.setText(sales.totalAmount);
                bankTransferDate.setText(sales.bankTransferDate);
                soldBy.setText(sales.soldBy);
                warehouseReceiptNumber.setText(sales.warehouseReceiptNumber);
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
                localHashMap.put("name", date.getText().toString());
                localHashMap.put("createdby", member.getName());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                localHashMap.put("createdTime", formatter.format(date));
                localHashMap.put("data", data);
                localHashMap.put("dbname", "sales");


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
