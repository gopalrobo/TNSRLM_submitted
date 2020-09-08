package cst.smart.tnsrlm.employeeDetails;

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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cst.smart.tnsrlm.R;
import cst.smart.tnsrlm.app.AndroidMultiPartEntity;
import cst.smart.tnsrlm.app.AppConfig;
import cst.smart.tnsrlm.app.AppController;
import cst.smart.tnsrlm.app.DbMember;
import cst.smart.tnsrlm.app.GPSTracker;
import cst.smart.tnsrlm.app.GlideApp;
import cst.smart.tnsrlm.app.Imageutils;
import cst.smart.tnsrlm.farmerdetails.FarmerRegister;
import cst.smart.tnsrlm.member.Member;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static cst.smart.tnsrlm.app.AppConfig.memberIdKey;
import static cst.smart.tnsrlm.app.AppConfig.mypreference;

public class EmployeeRegister extends AppCompatActivity implements Imageutils.ImageAttachmentListener {
    private static final int FINE_LOCATION_CODE = 199;

    Imageutils imageutils;
    private CircleImageView studentImage;
    private String imageUrl = null;
    GPSTracker gps;

    ProgressDialog pDialog;
    String mId = null;
    EditText employeeName;
    EditText responsibility;
    EditText mobileNumber;
    EditText educationQualification;
    EditText geoTag;
    EditText address;
    EditText pincode;
    EditText createdBy;
    TextView submit;


    Employee employee = null;
    private String TAG = getClass().getSimpleName();

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        DbMember dbMember = new DbMember(this);
        Member member = new Gson().fromJson(dbMember.getDataBymemberid(sharedpreferences.getString(memberIdKey, "")).get(1),
                Member.class);

        employeeName = (EditText) findViewById(R.id.employeeName);
        responsibility = (EditText) findViewById(R.id.responsibility);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        educationQualification = (EditText) findViewById(R.id.educationQualification);
        geoTag = (EditText) findViewById(R.id.geotag);
        address = (EditText) findViewById(R.id.address);
        pincode = (EditText) findViewById(R.id.pincode);
        createdBy = (EditText) findViewById(R.id.createdBy);
        createdBy.setText(member.getName());
        createdBy.setEnabled(false);

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

        submit = (TextView) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employee tempempbean = new Employee(
                        imageUrl,
                        employeeName.getText().toString(),
                        responsibility.getText().toString(),
                        mobileNumber.getText().toString(),
                        educationQualification.getText().toString(),
                        geoTag.getText().toString(),
                        address.getText().toString(),
                        pincode.getText().toString());
                if (employee != null) {
                    tempempbean.setId(employee.id);
                }
                String jsonVal = new Gson().toJson(tempempbean);
                getCreateTest(jsonVal);

            }
        });


        try {
            employee = (Employee) getIntent().getSerializableExtra("object");
            if (employee != null) {
                mId=employee.id;
                employeeName.setText(employee.employeeName);
                responsibility.setText(employee.responsibility);
                mobileNumber.setText(employee.mobileNumber);
                educationQualification.setText(employee.educationQualification);
                geoTag.setText(employee.geoTag);
                address.setText(employee.address);
                pincode.setText(employee.pincode);
                imageUrl = employee.imgUrl;

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
                localHashMap.put("name", employeeName.getText().toString());
                localHashMap.put("createdby", createdBy.getText().toString());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                localHashMap.put("createdTime", formatter.format(date));
                localHashMap.put("data", data);
                localHashMap.put("dbname", "employeeDetails");


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
        new AlertDialog.Builder(EmployeeRegister.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void locationFetcher(EditText gpsLocal, EditText addressLocal) {
        // check if GPS enabled
        gps = new GPSTracker(EmployeeRegister.this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            gpsLocal.setText(latitude + "," + longitude);
            try {
                if (addressLocal != null) {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(EmployeeRegister.this, Locale.getDefault());
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
