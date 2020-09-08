package cst.smart.tnsrlm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cst.smart.tnsrlm.app.AppConfig;
import cst.smart.tnsrlm.app.AppController;
import cst.smart.tnsrlm.app.DbMember;
import cst.smart.tnsrlm.member.Member;
import cst.smart.tnsrlm.member.MemberRegister;

public class LoginActivity extends AppCompatActivity {

    public static final String buSurveyerId = "buSurveyerIdKey";
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    private ProgressDialog pDialog;
    DbMember dbMember;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        dbMember = new DbMember(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        final TextView login = (TextView) findViewById(R.id.login);
        final TextView addMember = (TextView) findViewById(R.id.addMember);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().length() > 0 && password.getText().toString().length() > 0) {
                 loginStudent();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter username and Password/Mobile", Toast.LENGTH_SHORT).show();
                }
            }
        });


        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent1 = new Intent(LoginActivity.this, MemberRegister.class);
                startActivity(localIntent1);
            }
        });
    }


    private void loginStudent() {
        String tag_string_req = "req_register";
        pDialog.setMessage("Validating ...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_MEMBER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register Response: ", response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    String msg = jObj.getString("message");
                    if (success == 1) {
                        dbMember.deleteAll();
                        Member student = new Gson().fromJson(jObj.getString("data"), Member.class);
                        String id = jObj.getString("id");
                        student.setId(id);
                        dbMember.addData(id, new Gson().toJson(student));
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(AppConfig.memberIdKey, id);
                        editor.commit();
                        startActivity(new Intent(LoginActivity.this, MainActivtyFirstActivity.class));
                    }
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("xxxxxxxxxxx", e.toString());
                    Toast.makeText(getApplicationContext(), "Some Network Error.Try after some time", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Registration Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap localHashMap = new HashMap();
                localHashMap.put("name", username.getText().toString());
                localHashMap.put("password", password.getText().toString());
                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



}
