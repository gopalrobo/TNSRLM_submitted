package cst.smart.tnsrlm.plf;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class PLFRegistration extends AppCompatActivity implements OnItemClick {

    ProgressDialog pDialog;

    LivelihoodCommiteeAdapter livelihoodCommiteeAdapter;
    EditText plfName;
    EditText secretaryName;
    EditText contact;
    EditText treasurerName;
    EditText gstin;
    EditText registrationNumber;
    EditText commission;
    TextView submit;
    String mId = null;
    private RecyclerView livelihoodCommiteelists;
    private ArrayList<LivelihoodCommitee> livelihoodCommitees = new ArrayList<>();
    PLF plf = null;
    private String TAG = getClass().getSimpleName();
    SharedPreferences sharedpreferences;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plf_registration);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        DbMember dbMember = new DbMember(this);
        member = new Gson().fromJson(dbMember.getDataBymemberid(sharedpreferences.getString(memberIdKey, "")).get(1),
                Member.class);


        plfName = (EditText) findViewById(R.id.plfName);
        secretaryName = (EditText) findViewById(R.id.secretaryName);
        contact = (EditText) findViewById(R.id.contact);
        treasurerName = (EditText) findViewById(R.id.treasurerName);
        gstin = (EditText) findViewById(R.id.gstin);
        registrationNumber = (EditText) findViewById(R.id.registrationNumber);
        commission = (EditText) findViewById(R.id.commission);
        submit = (TextView) findViewById(R.id.submit);

        livelihoodCommiteelists = (RecyclerView) findViewById(R.id.livelihoodCommittelists);
        livelihoodCommiteeAdapter = new LivelihoodCommiteeAdapter(this, livelihoodCommitees,
                this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        livelihoodCommiteelists.setLayoutManager(layoutManager);
        livelihoodCommiteelists.setAdapter(livelihoodCommiteeAdapter);
        final TextView addLivelihoodCommitteTxt = (TextView) findViewById(R.id.addLivelihoodCommitteTxt);
        final ImageView addLivelihoodCommitteImg = (ImageView) findViewById(R.id.addLivelihoodCommitteImg);
        addLivelihoodCommitteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLivelihoodDialog(-1);
            }
        });
        addLivelihoodCommitteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLivelihoodDialog(-1);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PLF tempconfbean = new PLF(
                        plfName.getText().toString(),
                        secretaryName.getText().toString(),
                        contact.getText().toString(),
                        treasurerName.getText().toString(),
                        livelihoodCommitees,
                        gstin.getText().toString(),
                        registrationNumber.getText().toString(),
                        commission.getText().toString()
                );
                if (plf != null) {
                    tempconfbean.setId(plf.id);
                }
                String jsonVal = new Gson().toJson(tempconfbean);

                getCreateTest(jsonVal);
            }
        });

        try {
            plf = (PLF) getIntent().getSerializableExtra("object");
            if (plf != null) {
                plfName.setText(plf.plfName);
                secretaryName.setText(plf.secretaryName);
                contact.setText(plf.contact);
                treasurerName.setText(plf.treasurerName);
                gstin.setText(plf.gstin);
                registrationNumber.setText(plf.registrationNumber);
                commission.setText(plf.commission);
                livelihoodCommitees = plf.livelihoodCommitees;
                livelihoodCommiteeAdapter.notifyData(livelihoodCommitees);

            }
        } catch (Exception e) {
            Log.e("xxxxxx", "Something went wrong");
        }


    }


    public void showLivelihoodDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PLFRegistration.this);
        LayoutInflater inflater = PLFRegistration.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.confed_details_register, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.consubmit);
        final EditText descriptionone = (EditText) dialogView.findViewById(R.id.descriptionone);
        final EditText descriptiontwo = (EditText) dialogView.findViewById(R.id.descriptiontwo);
        final EditText descriptionthree = (EditText) dialogView.findViewById(R.id.descriptionthree);


        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            LivelihoodCommitee bean = livelihoodCommitees.get(position);
            descriptionone.setText(bean.getDescriptionone());
            descriptiontwo.setText(bean.getDescriptiontwo());
            descriptionthree.setText(bean.getDescriptionthree());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    LivelihoodCommitee bean = new LivelihoodCommitee(
                            descriptionone.getText().toString(),
                            descriptiontwo.getText().toString(),
                            descriptionthree.getText().toString()
                    );
                    livelihoodCommitees.add(bean);
                } else {

                    livelihoodCommitees.get(position).setDescriptionone(descriptionone.getText().toString());
                    livelihoodCommitees.get(position).setDescriptiontwo(descriptiontwo.getText().toString());
                    livelihoodCommitees.get(position).setDescriptionthree(descriptionthree.getText().toString());
                }
                livelihoodCommiteeAdapter.notifyData(livelihoodCommitees);
                b.cancel();
            }
        });
        b.show();
    }


    @Override
    public void itemLivelihoodClick(int position) {
        showLivelihoodDialog(position);
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
                localHashMap.put("name", plfName.getText().toString());
                localHashMap.put("createdby", member.getName());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                localHashMap.put("createdTime", formatter.format(date));
                localHashMap.put("data", data);
                localHashMap.put("dbname", "plf");


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
