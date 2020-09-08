package cst.smart.tnsrlm.farmerdetails;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cst.smart.tnsrlm.CustomFontEditText;
import cst.smart.tnsrlm.R;
import cst.smart.tnsrlm.app.AndroidMultiPartEntity;
import cst.smart.tnsrlm.app.AppConfig;
import cst.smart.tnsrlm.app.AppController;
import cst.smart.tnsrlm.app.DbMember;
import cst.smart.tnsrlm.app.GPSTracker;
import cst.smart.tnsrlm.app.GlideApp;
import cst.smart.tnsrlm.app.Imageutils;
import cst.smart.tnsrlm.member.Member;
import cst.smart.tnsrlm.member.MemberUpdate;
import cst.smart.tnsrlm.stockMoniter.StockMonitorRegister;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static cst.smart.tnsrlm.app.AppConfig.memberIdKey;
import static cst.smart.tnsrlm.app.AppConfig.mypreference;

public class FarmerRegister extends AppCompatActivity implements FarmOnItemClick, Imageutils.ImageAttachmentListener {

    private static final int FINE_LOCATION_CODE = 199;
    ProgressDialog pDialog;
    private RecyclerView farmerlists;
    private ArrayList<Farm> farmpowerarrylist = new ArrayList<>();
    FarmAdapter farmpoweradapter;

    EditText farmerName;
    EditText husbandFatherName;
    EditText contact;
    EditText geoTag;
    EditText address;
    EditText pincode;
    EditText communityType;
    EditText typeOfFarming;
    EditText landType;
    EditText deptStatus;
    EditText bankName;
    EditText branch;
    EditText accountNumber;
    EditText accountStartDate;
    EditText ifscCode;
    String mId = null;
    private EditText createdBy;
    TextView submit;

    Imageutils imageutils;
    private CircleImageView studentImage;
    private String imageUrl = null;
    GPSTracker gps;

    Farmer farmer = null;
    private String TAG = getClass().getSimpleName();
    SharedPreferences sharedpreferences;

    private static final String[] COMMODITY = new String[]{
            "Red Gram", "Sesame", "Little millet", "Paddy", "Pearl Millet", "Finger Millet", "Groundnut", "Maize",
            "White Sorghum", "Red Sorghum", "Fox Style Millet"
    };
    ArrayAdapter<String> commodityAdapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_register);

        commodityAdapter = new ArrayAdapter<String>(FarmerRegister.this,
                android.R.layout.simple_dropdown_item_1line, COMMODITY);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        DbMember dbMember = new DbMember(this);
        Member member = new Gson().fromJson(dbMember.getDataBymemberid(sharedpreferences.getString(memberIdKey, "")).get(1),
                Member.class);

        farmerName = (EditText) findViewById(R.id.farmerName);
        husbandFatherName = (EditText) findViewById(R.id.husbandFatherName);
        contact = (EditText) findViewById(R.id.contact);
        geoTag = (EditText) findViewById(R.id.geotag);
        address = (EditText) findViewById(R.id.address);
        pincode = (EditText) findViewById(R.id.pincode);
        communityType = (EditText) findViewById(R.id.communityType);
        typeOfFarming = (EditText) findViewById(R.id.typeOfFarming);
        landType = (EditText) findViewById(R.id.landType);
        deptStatus = (EditText) findViewById(R.id.deptStatus);
        bankName = (EditText) findViewById(R.id.bankName);
        branch = (EditText) findViewById(R.id.branch);
        accountNumber = (EditText) findViewById(R.id.accountNumber);
        accountStartDate = (EditText) findViewById(R.id.accountStartDate);
        ifscCode = (EditText) findViewById(R.id.ifscCode);
        createdBy = (EditText) findViewById(R.id.createdBy);
        createdBy.setText(member.getName());
        createdBy.setEnabled(false);

        submit = (TextView) findViewById(R.id.submit);

        imageutils = new Imageutils(this);
        studentImage = (CircleImageView) findViewById(R.id.studentImage);
        studentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageutils.imagepicker(1);
            }
        });

        geoTag = (EditText) findViewById(R.id.geotag);
        ImageView georefresh = (ImageView) findViewById(R.id.refresh);

        geoTag.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!checkPermission(new String[]{ACCESS_FINE_LOCATION})) {
                        requestPermission(new String[]{ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
                    } else {
                        locationFetcher(geoTag, address);
                    }
                }
            }
        });

        georefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkPermission(new String[]{ACCESS_FINE_LOCATION})) {
                    requestPermission(new String[]{ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
                } else {
                    locationFetcher(geoTag, address);
                }


            }
        });

        farmerlists = (RecyclerView) findViewById(R.id.farmerlists);
        farmpoweradapter = new FarmAdapter(this, farmpowerarrylist, this);
        final LinearLayoutManager farmmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        farmerlists.setLayoutManager(farmmanager);
        farmerlists.setAdapter(farmpoweradapter);
        TextView addFarmerTxt = (TextView) findViewById(R.id.addFarmerTxt);
        ImageView addFarmImg = (ImageView) findViewById(R.id.addFarmImg);
        addFarmerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFarmDialog(-1);
            }
        });
        addFarmImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFarmDialog(-1);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Farmer tempmainbean = new Farmer(
                        imageUrl,
                        farmerName.getText().toString(),
                        husbandFatherName.getText().toString(),
                        contact.getText().toString(),
                        geoTag.getText().toString(),
                        address.getText().toString(),
                        pincode.getText().toString(),
                        communityType.getText().toString(),
                        typeOfFarming.getText().toString(),
                        landType.getText().toString(),
                        deptStatus.getText().toString(),
                        farmpowerarrylist,
                        bankName.getText().toString(),
                        branch.getText().toString(),
                        accountNumber.getText().toString(),
                        accountStartDate.getText().toString(),
                        ifscCode.getText().toString()
                );
                String jsonVal = new Gson().toJson(tempmainbean);
                Log.e("xxxxxxxxxxxxx", jsonVal);

                if (farmer != null) {
                    tempmainbean.setId(farmer.id);
                }
                getCreateTest(jsonVal);

            }
        });


        try {
            farmer = (Farmer) getIntent().getSerializableExtra("object");
            if (farmer != null) {
                mId = farmer.id;
                farmerName.setText(farmer.farmerName);
                husbandFatherName.setText(farmer.husbandFatherName);
                contact.setText(farmer.contact);
                geoTag.setText(farmer.geoTag);
                address.setText(farmer.address);
                pincode.setText(farmer.pincode);
                communityType.setText(farmer.communityType);
                typeOfFarming.setText(farmer.typeOfFarming);
                landType.setText(farmer.landType);
                deptStatus.setText(farmer.deptStatus);
                bankName.setText(farmer.bankName);
                branch.setText(farmer.branch);
                accountNumber.setText(farmer.accountNumber);
                accountStartDate.setText(farmer.accountStartDate);
                ifscCode.setText(farmer.ifscCode);
                imageUrl = farmer.imgUrl;
                farmpowerarrylist = farmer.farms;
                farmpoweradapter.notifyData(farmpowerarrylist);

                GlideApp.with(getApplicationContext())
                        .load("http://" + imageUrl)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                        .placeholder(R.drawable.profile)
                        .into(studentImage);

            }
        } catch (Exception e) {
            Log.e("xxxxxx", "Something went wrong");
        }


    }


    public void showFarmDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FarmerRegister.this);
        LayoutInflater inflater = FarmerRegister.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.farm_details_register, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final MaterialBetterSpinner cultivatedcrop1 = (MaterialBetterSpinner) dialogView.findViewById(R.id.cultivatedCrop1);
        final MaterialBetterSpinner cultivatedcrop2 = (MaterialBetterSpinner) dialogView.findViewById(R.id.cultivatedCrop2);
        final MaterialBetterSpinner cultivatedcrop3 = (MaterialBetterSpinner) dialogView.findViewById(R.id.cultivatedCrop3);
        final EditText cultivatedcrop4 = (EditText) dialogView.findViewById(R.id.cultivatedCrop4);
        final EditText cultivatedcrop5 = (EditText) dialogView.findViewById(R.id.cultivatedCrop5);
        final EditText cultivatedcrop6 = (EditText) dialogView.findViewById(R.id.cultivatedCrop6);


        cultivatedcrop1.setAdapter(commodityAdapter);
        cultivatedcrop2.setAdapter(commodityAdapter);
        cultivatedcrop3.setAdapter(commodityAdapter);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Farm bean = farmpowerarrylist.get(position);

            cultivatedcrop1.setText(bean.cultivatedCrop1);
            cultivatedcrop2.setText(bean.cultivatedCrop2);
            cultivatedcrop3.setText(bean.cultivatedCrop3);
            cultivatedcrop4.setText(bean.cultivatedCrop4);
            cultivatedcrop5.setText(bean.cultivatedCrop5);
            cultivatedcrop6.setText(bean.cultivatedCrop6);

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Farm bean = new Farm(
                            cultivatedcrop1.getText().toString(),
                            cultivatedcrop2.getText().toString(),
                            cultivatedcrop3.getText().toString(),
                            cultivatedcrop4.getText().toString(),
                            cultivatedcrop5.getText().toString(),
                            cultivatedcrop6.getText().toString()
                            );
                    farmpowerarrylist.add(bean);
                } else {

                    farmpowerarrylist.get(position).setCultivatedCrop1(cultivatedcrop1.getText().toString());
                    farmpowerarrylist.get(position).setCultivatedCrop2(cultivatedcrop2.getText().toString());
                    farmpowerarrylist.get(position).setCultivatedCrop3(cultivatedcrop3.getText().toString());
                    farmpowerarrylist.get(position).setCultivatedCrop4(cultivatedcrop4.getText().toString());
                    farmpowerarrylist.get(position).setCultivatedCrop5(cultivatedcrop5.getText().toString());
                    farmpowerarrylist.get(position).setCultivatedCrop6(cultivatedcrop6.getText().toString());
                }
                farmpoweradapter.notifyData(farmpowerarrylist);
                b.cancel();
            }
        });
        b.show();
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
                localHashMap.put("createdby", createdBy.getText().toString());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                localHashMap.put("createdTime", formatter.format(date));
                localHashMap.put("data", data);
                localHashMap.put("dbname", "farmerDetails");


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

    @Override
    public void itemFarmClick(int position) {
        showFarmDialog(position);
    }


    private void requestPermission(String[] permissions, int code) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == FINE_LOCATION_CODE) {
            if (grantResults.length > 0) {
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (locationAccepted) {
                    Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access location data.", Toast.LENGTH_LONG).show();
                    locationFetcher(geoTag, address);
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access location data", Toast.LENGTH_LONG).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                            showMessageOKCancel(getResources().getString(R.string.locationPermissionAlert),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                        FINE_LOCATION_CODE);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }
            }
        } else {
            imageutils.request_permission_result(requestCode, permissions, grantResults);
        }
    }

    private boolean checkPermission(String[] permissions) {
        boolean result = false;
        for (int i = 0; i < permissions.length; i++) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[i]) == 0;
            if (!result) {
                return false;
            }
        }
        return result;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(FarmerRegister.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void locationFetcher(EditText gpsLocal, EditText addressLocal) {
        // check if GPS enabled
        gps = new GPSTracker(FarmerRegister.this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            gpsLocal.setText(latitude + "," + longitude);
            try {
                if (addressLocal != null) {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(FarmerRegister.this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String addresstxt = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    addressLocal.setText(addresstxt + "," + addresses.get(0).getPostalCode());
                }
            } catch (Exception e) {
                Log.d("Error", e.toString());
            }
            // \n is for new line
            //       Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        String path = Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imageutils.createImage(file, filename, path, false);
        pDialog.setMessage("Uploading...");
        showDialog();
        new UploadFileToServer().execute(imageutils.getPath(uri));
    }

    private class UploadFileToServer extends AsyncTask<String, Integer, String> {
        String filepath;
        public long totalSize = 0;

        @Override
        protected void onPreExecute() {
            // setting progress bar to zero

            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            pDialog.setMessage("Uploading..." + (String.valueOf(progress[0])));
        }

        @Override
        protected String doInBackground(String... params) {
            filepath = params[0];
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(AppConfig.URL_IMAGE_UPLOAD);
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(filepath);
                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));
                if (imageUrl != null && imageUrl.length() > 5) {
                    entity.addPart("oldimg", new StringBody(imageutils.getfilename_from_path(imageUrl)));
                }
                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);

                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;

                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Response from server: ", result);
            try {
                JSONObject jsonObject = new JSONObject(result.toString());
                if (!jsonObject.getBoolean("error")) {
                    GlideApp.with(getApplicationContext())
                            .load(filepath)
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .skipMemoryCache(false)
                            .placeholder(R.drawable.profile)
                            .into(studentImage);
                    imageUrl = AppConfig.ipcloud + "/uploads/" + imageutils.getfilename_from_path(filepath);
                } else {
                    imageUrl = null;
                }
                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Image not uploaded", Toast.LENGTH_SHORT).show();
            }
            hideDialog();
            // showing the server response in an alert dialog
            //showAlert(result);


            super.onPostExecute(result);
        }

    }

}
