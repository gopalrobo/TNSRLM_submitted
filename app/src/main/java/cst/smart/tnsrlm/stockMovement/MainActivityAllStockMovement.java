package cst.smart.tnsrlm.stockMovement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cst.smart.tnsrlm.R;
import cst.smart.tnsrlm.app.AppConfig;
import cst.smart.tnsrlm.app.AppController;
import cst.smart.tnsrlm.stockMoniter.MainActivityAllStockMonitor;
import cst.smart.tnsrlm.stockMoniter.StockMonitor;
import cst.smart.tnsrlm.stockMoniter.StockMonitorRegister;

import static cst.smart.tnsrlm.app.AppConfig.mypreference;


public class MainActivityAllStockMovement extends AppCompatActivity {


    private ProgressDialog pDialog;
    private RecyclerView mastersList;
    private MasterAllStockMovementAdapter mRecyclerAdapterMaster;
    private ArrayList<StockMovement> itemArrayList = new ArrayList<>();

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balancemonitoringcontent_all_survey);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        getSupportActionBar().setTitle("Stock Movement Register");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);


        FloatingActionButton addMaster = (FloatingActionButton) findViewById(R.id.addSurvey);
        addMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAllStockMovement.this, StockMovementRegister.class);
                startActivity(intent);
            }
        });
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mastersList = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerAdapterMaster = new MasterAllStockMovementAdapter(this, itemArrayList);
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
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject dataObject = jsonArray.getJSONObject(i);
                                StockMovement farmer = new Gson().fromJson(dataObject.getString("data"), StockMovement.class);
                                farmer.setId(dataObject.getString("id"));
                                itemArrayList.add(farmer);
                            } catch (Exception e) {
                                Log.e("xxxxxxxxxxx", e.toString());
                            }
                        }
                        getSupportActionBar().setSubtitle(String.valueOf(itemArrayList.size()) + " Stock Movement Details");
                        mRecyclerAdapterMaster.notifyData(itemArrayList);

                        if (itemArrayList.size() == 0) {
                            Intent intent = new Intent(MainActivityAllStockMovement.this, StockMovementRegister.class);
                            startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(MainActivityAllStockMovement.this, StockMovementRegister.class);
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
                localHashMap.put("key", "stockMovement");
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


}


