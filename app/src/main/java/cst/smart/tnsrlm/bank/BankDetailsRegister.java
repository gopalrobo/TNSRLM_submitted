package cst.smart.tnsrlm.bank;

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

public class BankDetailsRegister extends AppCompatActivity {
    ProgressDialog pDialog;
    String mId = null;
    EditText bankname;
    EditText bankbranch;
    EditText bankacno;
    EditText bankstartdate;
    EditText bankifsc;
    EditText bankbalanceamount;
    TextView banksubmit;
    BankDetails bankDetails = null;

    private String TAG = getClass().getSimpleName();

    SharedPreferences sharedpreferences;
    Member member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankdetails);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        DbMember dbMember = new DbMember(this);
        member = new Gson().fromJson(dbMember.getDataBymemberid(sharedpreferences.getString(memberIdKey, "")).get(1),
                Member.class);


        bankname = (EditText) findViewById(R.id.bankname);
        bankbranch = (EditText) findViewById(R.id.bankbranch);
        bankacno = (EditText) findViewById(R.id.bankacno);
        bankstartdate = (EditText) findViewById(R.id.bankstartdate);
        bankifsc = (EditText) findViewById(R.id.bankifsc);
        bankbalanceamount = (EditText) findViewById(R.id.bankbalanceamount);
        banksubmit = (TextView) findViewById(R.id.banksubmit);

        banksubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BankDetails tempbankdetailbean = new BankDetails(
                        bankname.getText().toString(),
                        bankbranch.getText().toString(),
                        bankacno.getText().toString(),
                        bankstartdate.getText().toString(),
                        bankifsc.getText().toString(),
                        bankbalanceamount.getText().toString());

                if (bankDetails != null) {
                    tempbankdetailbean.setId(bankDetails.id);
                }
                String jsonVal = new Gson().toJson(tempbankdetailbean);
                getCreateTest(jsonVal);

            }
        });

        try {
            bankDetails = (BankDetails) getIntent().getSerializableExtra("object");
            if (bankDetails != null) {
                mId = bankDetails.id;
                bankname.setText(bankDetails.bankname);
                bankbranch.setText(bankDetails.bankbranch);
                bankacno.setText(bankDetails.bankacno);
                bankstartdate.setText(bankDetails.bankstartdate);
                bankifsc.setText(bankDetails.bankifsc);
                bankbalanceamount.setText(bankDetails.bankbalanceamount);


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
                localHashMap.put("name", bankacno.getText().toString());
                localHashMap.put("createdby", member.getName());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                localHashMap.put("createdTime", formatter.format(date));
                localHashMap.put("data", data);
                localHashMap.put("dbname", "bank");


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
