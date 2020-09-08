package cst.smart.tnsrlm.stockMovement;

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

public class StockMovementRegister extends AppCompatActivity {
    ProgressDialog pDialog;
    EditText date;
    EditText determinationDate;
    EditText materialAmount;
    EditText salesVolume;
    EditText personName;
    EditText goodsReceived;
    EditText dateCreditedAccount;
    EditText finalBalanceAccount;
    EditText employeeReserve;
    EditText contact;

    TextView submit;

    String mId = null;
    StockMovement stockMovement = null;
    private String TAG = getClass().getSimpleName();

    SharedPreferences sharedpreferences;
    Member member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_movement_register);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        DbMember dbMember = new DbMember(this);
        member = new Gson().fromJson(dbMember.getDataBymemberid(sharedpreferences.getString(memberIdKey, "")).get(1),
                Member.class);

        date = (EditText) findViewById(R.id.date);
        determinationDate = (EditText) findViewById(R.id.determinationDate);
        materialAmount = (EditText) findViewById(R.id.materialAmount);
        salesVolume = (EditText) findViewById(R.id.salesVolume);
        personName = (EditText) findViewById(R.id.personName);
        goodsReceived = (EditText) findViewById(R.id.goodsReceived);
        dateCreditedAccount = (EditText) findViewById(R.id.dateCreditedAccount);
        finalBalanceAccount = (EditText) findViewById(R.id.finalBalanceAccount);
        employeeReserve = (EditText) findViewById(R.id.employeeReserve);
        contact = (EditText) findViewById(R.id.contact);
        submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockMovement tempbalancemonitoringbean = new StockMovement(
                        date.getText().toString(),
                        determinationDate.getText().toString(),
                        materialAmount.getText().toString(),
                        salesVolume.getText().toString(),
                        personName.getText().toString(),
                        goodsReceived.getText().toString(),
                        dateCreditedAccount.getText().toString(),
                        finalBalanceAccount.getText().toString(),
                        employeeReserve.getText().toString(),
                        contact.getText().toString()
                );
                if (stockMovement != null) {
                    tempbalancemonitoringbean.setId(stockMovement.id);
                }
                String jsonVal = new Gson().toJson(tempbalancemonitoringbean);

                getCreateTest(jsonVal);
            }
        });

        try {
            stockMovement = (StockMovement) getIntent().getSerializableExtra("object");
            if (stockMovement != null) {
                mId = stockMovement.id;
                date.setText(stockMovement.date);
                determinationDate.setText(stockMovement.determinationDate);
                materialAmount.setText(stockMovement.materialAmount);
                salesVolume.setText(stockMovement.salesVolume);
                personName.setText(stockMovement.personName);
                goodsReceived.setText(stockMovement.goodsReceived);
                dateCreditedAccount.setText(stockMovement.dateCreditedAccount);
                finalBalanceAccount.setText(stockMovement.finalBalanceAccount);
                employeeReserve.setText(stockMovement.employeeReserve);
                contact.setText(stockMovement.contact);
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
                localHashMap.put("dbname", "stockMovement");


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

