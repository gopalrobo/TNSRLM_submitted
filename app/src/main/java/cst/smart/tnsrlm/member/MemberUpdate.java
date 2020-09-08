package cst.smart.tnsrlm.member;

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
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
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
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static cst.smart.tnsrlm.app.AppConfig.memberIdKey;
import static cst.smart.tnsrlm.app.AppConfig.mypreference;

/**
 * Created by user_1 on 11-07-2018.
 */

public class MemberUpdate extends AppCompatActivity implements Imageutils.ImageAttachmentListener {

    private static final int FINE_LOCATION_CODE = 199;

    Member member;
    MaterialBetterSpinner name;
    EditText contact;
    EditText whatsapp;
    EditText address;
    EditText pincode;
    EditText gmail;
    EditText designation;
    EditText password;
    EditText confirmPass;
    EditText geoTag;
    EditText initials;
    MaterialBetterSpinner accessLevel;
    MaterialBetterSpinner commodity;

    MaterialBetterSpinner village;
    MaterialBetterSpinner district;
    MaterialBetterSpinner block;
    MaterialBetterSpinner facilitator;

    String memberId = null;

    DbMember dbMember;

    private TextView submit;

    private ProgressDialog pDialog;

    SharedPreferences sharedpreferences;

    Imageutils imageutils;
    private CircleImageView studentImage;
    private String imageUrl = null;


    String[] districtArray = {
            "Thiruvallur",
            "Thiruvannamalai",
            "Theni",
            "Erode",
            "Karur",
            "Velur",
            "Thanjavur"
    };

    Map<String, String[]> districtBlockMap = new HashMap<String, String[]>() {{
        put("Thiruvallur", new String[]{"Gummidipoodni"});
        put("Thanjavur", new String[]{"Pattukottai"});
        put("Karur", new String[]{"Kadavur"});
        put("Thiruvannamalai", new String[]{"Javvadu Hills"});
        put("Velur", new String[]{"Alangayan"});
        put("Theni", new String[]{"Bodinaikanur"});
        put("Erode", new String[]{"Ammapettai", "Gobi"});
    }};

    Map<String, String[]> blockPanchayatMap = new HashMap<String, String[]>() {
        {
            put("Ammapettai", new String[]{"Sigampettai", "Vellithirupur", "Mathur", "Guruvareddiyur", "Kesarimangalam"});
            put("Gobi", new String[]{"Vellankovil", "Nagadavampalayam", "Ayalur", "Savindapur", "Nanjai Gobi"});
            put("Pattukottai", new String[]{"Alivaikkal", "Melaulur", "Cholapuram", "Thondarampattu", "Thirumangalakkottai", "Sendakkottai", "Maliakkadu", "Thokkalikadu", "Pudukkottai ullur", "Sembalur"});
            put("Alangayan", new String[]{"Beemakulam", "Naiyakanur", "Nellivasalnadu", "Pudhurnadu", "Pungapattunadu"});
            put("Javvadu Hills", new String[]{"Kovilur", "Palamarathur", "Puliyur", "Melsilampadi ", "Veerappanur", "Nammiyapattu", "Jamunamarathur", "Kilvilamuchi", "Melpattu", "Athipattu"});
            put("Kadavur", new String[]{"Thennilai", "Kelapaguthi", "Kadavur", "Tharagampatti", "Aathanur"});
            put("Gummidipoodni", new String[]{"Padhirivedu", "Madharpakkam", "Nemalure", "Sethilpakkam", "Pondhavakkam"});
            put("Bodinaikanur", new String[]{"Uppokkottai", "Rasingapuram", "Dombucheri", "B.Ammapatti", "Kodangipatti"});
        }
    };

    Map<String, String[]> panchayatFaciliMap = new HashMap<String, String[]>() {
        {
            put("Vellankovil", new String[]{"T. Sasikala"});
            put("Nagadavampalayam", new String[]{"T. Sasikala"});
            put("Ayalur", new String[]{"T. Sasikala"});
            put("Savindapur", new String[]{"T. Sasikala"});
            put("Nanjai Gobi", new String[]{"T. Sasikala"});
            put("Sigampettai", new String[]{"R. Lakshmi"});
            put("Vellithirupur", new String[]{"R. Lakshmi"});
            put("Mathur", new String[]{"R. Lakshmi"});
            put("Guruvareddiyur", new String[]{"R. Lakshmi"});
            put("Kesarimangalam", new String[]{"R. Lakshmi"});
            put("Alivaikkal", new String[]{"Lalitha"});
            put("Melaulur", new String[]{"Selvi"});
            put("Cholapuram", new String[]{"Thelakavathi"});
            put("Thondarampattu", new String[]{"Anusiya"});
            put("Thirumangalakkottai", new String[]{"Koikila"});
            put("Sendakkottai", new String[]{"Sangeetha"});
            put("Maliakkadu", new String[]{"Vinitha"});
            put("Thokkalikadu", new String[]{"Neelavathi"});
            put("Pudukkottai ullur", new String[]{"Mahalakshmi"});
            put("Sembalur", new String[]{"Janzy rani"});
            put("Beemakulam", new String[]{"Venkatachalam"});
            put("Naiyakanur", new String[]{"Venkatachalam"});
            put("Nellivasalnadu", new String[]{"Murugan"});
            put("Pudhurnadu", new String[]{"Murugan"});
            put("Pungapattunadu", new String[]{"Murugan"});
            put("Kovilur", new String[]{"Kasi"});
            put("Palamarathur", new String[]{"Ushanandhini "});
            put("Puliyur", new String[]{"Vennila"});
            put("Melsilampadi ", new String[]{"Vennila"});
            put("Veerappanur", new String[]{"Ushanandhini "});
            put("Nammiyapattu", new String[]{"Ushanandhini "});
            put("Jamunamarathur", new String[]{"Kasi"});
            put("Kilvilamuchi", new String[]{"Vennila"});
            put("Melpattu", new String[]{"Vennila"});
            put("Athipattu", new String[]{"Kasi"});
            put("Thennilai", new String[]{"Kavitha"});
            put("Kelapaguthi", new String[]{"Kavitha"});
            put("Kadavur", new String[]{"Kavitha"});
            put("Tharagampatti", new String[]{"Kavitha"});
            put("Aathanur", new String[]{"Kavitha"});
            put("Padhirivedu", new String[]{"T.Amudhavalli"});
            put("Madharpakkam", new String[]{"T.Amudhavalli"});
            put("Nemalure", new String[]{"Thilagavathi"});
            put("Sethilpakkam", new String[]{"K. Kesammal"});
            put("Pondhavakkam", new String[]{"K. Kesammal"});
            put("Uppokkottai", new String[]{"P.Prema"});
            put("Rasingapuram", new String[]{"A.Mariammal"});
            put("Dombucheri", new String[]{"P.Prema"});
            put("B.Ammapatti", new String[]{"A.Mariammal"});
            put("Kodangipatti", new String[]{"P.Prema"});
        }
    };

    Map<String, String[]> facliStaffMap = new HashMap<String, String[]>() {
        {
            put("Vellankovil", new String[]{"A Marayal"});
            put("Nagadavampalayam", new String[]{"P. Devi"});
            put("Ayalur", new String[]{"K Eswari"});
            put("Savindapur", new String[]{"R Gomathi"});
            put("Nanjai Gobi", new String[]{"K Amsaveni"});
            put("Sigampettai", new String[]{"K Kunavathi"});
            put("Vellithirupur", new String[]{"S Kowsalya"});
            put("Mathur", new String[]{"S Banumathi"});
            put("Guruvareddiyur", new String[]{"S Mathammal"});
            put("Kesarimangalam", new String[]{"M Latha"});
            put("Alivaikkal", new String[]{"Vijayarani"});
            put("Melaulur", new String[]{"Vijayarani"});
            put("Cholapuram", new String[]{"Mahadevi"});
            put("Thondarampattu", new String[]{"Latha"});
            put("Thirumangalakkottai", new String[]{"Ambika"});
            put("Sendakkottai", new String[]{"Vasantha"});
            put("Maliakkadu", new String[]{"Devi"});
            put("Thokkalikadu", new String[]{"Malliga"});
            put("Pudukkottai ullur", new String[]{"Devi"});
            put("Sembalur", new String[]{"Parameswari"});
            put("Beemakulam", new String[]{"Sridevi"});
            put("Naiyakanur", new String[]{"Malarvizhi"});
            put("Nellivasalnadu", new String[]{"Parijathagapushpam"});
            put("Pudhurnadu", new String[]{"Chellamal"});
            put("Pungapattunadu", new String[]{"Thikiyammal"});
            put("Kovilur", new String[]{"Kavitha"});
            put("Palamarathur", new String[]{"Jeeva"});
            put("Puliyur", new String[]{"Vithya"});
            put("Melsilampadi ", new String[]{"Umadevi"});
            put("Veerappanur", new String[]{"Anuratha"});
            put("Nammiyapattu", new String[]{"Selvarani"});
            put("Jamunamarathur", new String[]{"Priya"});
            put("Kilvilamuchi", new String[]{"Sagunthala"});
            put("Melpattu", new String[]{"Maheshwari"});
            put("Athipattu", new String[]{"Sagunthala"});
            put("Thennilai", new String[]{"Vijayalakshmi"});
            put("Kelapaguthi", new String[]{"Perumayee"});
            put("Kadavur", new String[]{"Thamaraiselvi"});
            put("Tharagampatti", new String[]{"Kalaimani"});
            put("Aathanur", new String[]{"Pushba"});
            put("Padhirivedu", new String[]{"G. Kavitha"});
            put("Madharpakkam", new String[]{"Gayathiri"});
            put("Nemalure", new String[]{"Devi"});
            put("Sethilpakkam", new String[]{"Menaka"});
            put("Pondhavakkam", new String[]{"Nalini"});
            put("Uppokkottai", new String[]{"S.Kodiyarasi"});
            put("Rasingapuram", new String[]{"S.Saranya"});
            put("Dombucheri", new String[]{"R.Vairamuthu"});
            put("B.Ammapatti", new String[]{"M.Malarvizhi"});
            put("Kodangipatti", new String[]{"M.Sumalatha"});

        }
    };


    String[] blockArray = {"Select District"};

    String[] villageArray = {"Select Block"};

    String[] faciliArray = {"Select Panchayat"};

    String[] nameArray = {"Select Facilitator"};


    private static final String[] ACCESS_LEVEL = new String[]{
            "District", "Block", "Panchayat", "Village"
    };
    ArrayAdapter<String> accessLevelAdapter;

    private static final String[] COMMODITY = new String[]{
            "Red Gram", "Sesame", "Little millet", "Paddy", "Pearl Millet", "Finger Millet", "Groundnut", "Maize",
            "White Sorghum", "Red Sorghum", "Fox tail Millet"
    };
    ArrayAdapter<String> commodityAdapter;
    GPSTracker gps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_register);

        imageutils = new Imageutils(this);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        memberId = sharedpreferences.getString(memberIdKey, "");

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        dbMember = new DbMember(this);

        studentImage = (CircleImageView) findViewById(R.id.studentImage);
        studentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageutils.imagepicker(1);
            }
        });

        name = (MaterialBetterSpinner) findViewById(R.id.name);
        ArrayAdapter<String> nameadapter = new ArrayAdapter<String>(this
                , android.R.layout.select_dialog_item, nameArray);
        name.setAdapter(nameadapter);

        contact = (EditText) findViewById(R.id.contact);
        initials = (EditText) findViewById(R.id.initials);
        whatsapp = (EditText) findViewById(R.id.whatsapp);
        address = (EditText) findViewById(R.id.address);
        pincode = (EditText) findViewById(R.id.pincode);
        gmail = (EditText) findViewById(R.id.gmail);
        designation = (EditText) findViewById(R.id.designation);
        password = (EditText) findViewById(R.id.password);
        confirmPass = (EditText) findViewById(R.id.confirmPass);

        district = (MaterialBetterSpinner) findViewById(R.id.district);
        ArrayAdapter<String> distadapter = new ArrayAdapter<String>(this
                , android.R.layout.select_dialog_item, districtArray);
        district.setAdapter(distadapter);

        block = (MaterialBetterSpinner) findViewById(R.id.block);
        ArrayAdapter<String> blockadapter = new ArrayAdapter<String>(this
                , android.R.layout.select_dialog_item, blockArray);
        block.setAdapter(blockadapter);

        facilitator = (MaterialBetterSpinner) findViewById(R.id.facilitator);
        ArrayAdapter<String> facilitatoradapter = new ArrayAdapter<String>(this
                , android.R.layout.select_dialog_item, faciliArray);
        facilitator.setAdapter(facilitatoradapter);

        village = (MaterialBetterSpinner) findViewById(R.id.panchayat);
        ArrayAdapter<String> villageadapter = new ArrayAdapter<String>(this
                , android.R.layout.select_dialog_item, villageArray);
        village.setAdapter(villageadapter);


        district.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (districtBlockMap.containsKey(district.getText().toString())) {
                    ArrayAdapter<String> blockadapter = new ArrayAdapter<String>(MemberUpdate.this
                            , android.R.layout.select_dialog_item,
                            districtBlockMap.get(district.getText().toString()));
                    block.setAdapter(blockadapter);
                }

            }
        });

        block.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (blockPanchayatMap.containsKey(block.getText().toString())) {
                    ArrayAdapter<String> blockadapter = new ArrayAdapter<String>(MemberUpdate.this
                            , android.R.layout.select_dialog_item,
                            blockPanchayatMap.get(block.getText().toString()));
                    village.setAdapter(blockadapter);
                }

            }
        });

        village.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (panchayatFaciliMap.containsKey(village.getText().toString())) {
                    ArrayAdapter<String> blockadapter = new ArrayAdapter<String>(MemberUpdate.this
                            , android.R.layout.select_dialog_item,
                            panchayatFaciliMap.get(village.getText().toString()));
                    facilitator.setAdapter(blockadapter);
                }

                if (facliStaffMap.containsKey(village.getText().toString())) {
                    ArrayAdapter<String> blockadapter = new ArrayAdapter<String>(MemberUpdate.this
                            , android.R.layout.select_dialog_item,
                            facliStaffMap.get(village.getText().toString()));
                    name.setAdapter(blockadapter);
                }

            }
        });

        facilitator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (facliStaffMap.containsKey(village.getText().toString())) {
                    ArrayAdapter<String> blockadapter = new ArrayAdapter<String>(MemberUpdate.this
                            , android.R.layout.select_dialog_item,
                            facliStaffMap.get(village.getText().toString()));
                    name.setAdapter(blockadapter);
                }

            }
        });


        accessLevel = ((MaterialBetterSpinner) findViewById(R.id.accessLevel));
        accessLevelAdapter = new ArrayAdapter<String>(MemberUpdate.this,
                android.R.layout.simple_dropdown_item_1line, ACCESS_LEVEL);
        accessLevel.setAdapter(accessLevelAdapter);

        commodity = ((MaterialBetterSpinner) findViewById(R.id.commodity));
        commodityAdapter = new ArrayAdapter<String>(MemberUpdate.this,
                android.R.layout.simple_dropdown_item_1line, COMMODITY);
        commodity.setAdapter(commodityAdapter);


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
                if (name.getText().toString().length() > 0 &&
                        contact.getText().toString().length() > 0 &&
                        address.getText().toString().length() > 0 &&
                        whatsapp.getText().toString().length() > 0 &&
                        password.getText().toString().length() > 0 &&
                        confirmPass.getText().toString().length() > 0) {

                    if (!password.getText().toString().equalsIgnoreCase(confirmPass.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Password & Confirm password not matched", Toast.LENGTH_SHORT).show();
                    } else if (password.getText().toString().length() <= 7) {
                        Toast.makeText(getApplicationContext(), "Password is too weak", Toast.LENGTH_SHORT).show();
                    } else if (contact.getText().toString().length() != 10 || contact.getText().toString().matches(".*[a-zA-Z]+.*")) {
                        Toast.makeText(getApplicationContext(), "Enter a valid Contact", Toast.LENGTH_SHORT).show();
                    }

//                    else if (imageUrl == null) {
//                        Toast.makeText(getApplicationContext(), "Select a Image", Toast.LENGTH_SHORT).show();
//                    }

                    else {
                        Member student = new Member(name.getText().toString(),
                                initials.getText().toString(),
                                contact.getText().toString(),
                                whatsapp.getText().toString(),
                                geoTag.getText().toString(),
                                address.getText().toString(),
                                pincode.getText().toString(),
                                gmail.getText().toString(),
                                designation.getText().toString(),
                                commodity.getText().toString(),
                                accessLevel.getText().toString(),
                                district.getText().toString(),
                                block.getText().toString(),
                                village.getText().toString(),
                                password.getText().toString(), imageUrl);

                        registerUser(new Gson().toJson(student));
                    }
                }
            }
        });


        try {
            member = new Gson().fromJson(dbMember.getDataBymemberid(memberId).get(1), Member.class);
            name.setText(member.name);
            initials.setText(member.initials);
            contact.setText(member.contact);
            whatsapp.setText(member.whatsapp);
            geoTag.setText(member.geotag);
            address.setText(member.address);
            pincode.setText(member.pincode);
            gmail.setText(member.gmail);
            designation.setText(member.designation);
            commodity.setText(member.commodity);
            accessLevel.setText(member.accessLevel);
            district.setText(member.district);
            block.setText(member.block);
            village.setText(member.village);
            password.setText(member.password);
            imageUrl = member.image;


            GlideApp.with(getApplicationContext())
                    .load("http://" + imageUrl)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .placeholder(R.drawable.profile)
                    .into(studentImage);

            submit.setText("Update");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No Student found", Toast.LENGTH_SHORT).show();
            finish();
        }


    }


    private void registerUser(final String mData) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Processing ...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CREATE_MEMBER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register Response: ", response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response.substring(response.indexOf("{"), response.length()));
                    int success = jObj.getInt("success");
                    String msg = jObj.getString("message");
                    if (success == 1) {
                        dbMember.updatedataBymemberid(memberId, mData);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.commit();
                        finish();
                    }
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
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
                localHashMap.put("id", memberId);
                localHashMap.put("data", mData);
                localHashMap.put("password", password.getText().toString());
                localHashMap.put("name", name.getText().toString());
                localHashMap.put("contact", contact.getText().toString());
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
                entity.addPart("oldimg", new StringBody(imageutils.getfilename_from_path(imageUrl)));
                entity.addPart("image", new FileBody(sourceFile));

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

    @Override
    protected void onPause() {
        super.onPause();
        hideDialog();
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
        new AlertDialog.Builder(MemberUpdate.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void locationFetcher(EditText gpsLocal, EditText addressLocal) {
        // check if GPS enabled
        gps = new GPSTracker(MemberUpdate.this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            gpsLocal.setText(latitude + "," + longitude);
            try {
                if (addressLocal != null) {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(MemberUpdate.this, Locale.getDefault());
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


}
