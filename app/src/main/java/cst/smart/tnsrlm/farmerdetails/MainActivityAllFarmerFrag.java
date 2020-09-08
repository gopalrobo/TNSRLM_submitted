package cst.smart.tnsrlm.farmerdetails;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static cst.smart.tnsrlm.app.AppConfig.mypreference;


public class MainActivityAllFarmerFrag extends Fragment {


    private ProgressDialog pDialog;
    private RecyclerView mastersList;
    private MasterAllFarmerAdapter mRecyclerAdapterMaster;
    private ArrayList<Farmer> itemArrayList = new ArrayList<>();

    SharedPreferences sharedpreferences;


    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_all_survey, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);


        FloatingActionButton addMaster = (FloatingActionButton)view. findViewById(R.id.addSurvey);
        addMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FarmerRegister.class);
                startActivity(intent);
            }
        });
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        mastersList = (RecyclerView)view. findViewById(R.id.recycler_view);
        mRecyclerAdapterMaster = new MasterAllFarmerAdapter(getActivity(), itemArrayList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mastersList.setLayoutManager(thirdManager);
        mastersList.setAdapter(mRecyclerAdapterMaster);

        return view;

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
                                Farmer farmer = new Gson().fromJson(dataObject.getString("data"), Farmer.class);
                                farmer.setId(dataObject.getString("id"));
                                itemArrayList.add(farmer);
                            } catch (Exception e) {
                                Log.e("xxxxxxxxxxx", e.toString());
                                //   Toast.makeText(getApplicationContext(), "Some Network Error.Try after some time", Toast.LENGTH_SHORT).show();
                            }
                        }
                        mRecyclerAdapterMaster.notifyData(itemArrayList);

                        if (itemArrayList.size() == 0) {
                            Intent intent = new Intent(getActivity(), FarmerRegister.class);
                            startActivity(intent);
                        }
                    }else{

                        Intent intent = new Intent(getActivity(), FarmerRegister.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Log.e("xxxxxxxxxxx", e.toString());
                    //     Toast.makeText(getApplicationContext(), "Some Network Error.Try after some time", Toast.LENGTH_SHORT).show();

                }
                hideDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(),
                        "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap localHashMap = new HashMap();
                localHashMap.put("key","farmerDetails");
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


