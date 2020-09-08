package cst.smart.tnsrlm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import cst.smart.tnsrlm.bank.MainActivityAllBank;
import cst.smart.tnsrlm.employeeDetails.MainActivityAllEmployee;
import cst.smart.tnsrlm.employeeDetails.MainActivityAllEmployeeFrag;
import cst.smart.tnsrlm.farmerdetails.MainActivityAllFarmer;
import cst.smart.tnsrlm.farmerdetails.MainActivityAllFarmerFrag;
import cst.smart.tnsrlm.member.MemberUpdate;
import cst.smart.tnsrlm.plf.MainActivityAllPLF;
import cst.smart.tnsrlm.plf.MainActivityAllPLFFrag;
import cst.smart.tnsrlm.purchase.MainActivityAllPurchase;
import cst.smart.tnsrlm.sales.MainActivityAllSales;
import cst.smart.tnsrlm.stockMoniter.MainActivityAllStockMonitor;
import cst.smart.tnsrlm.stockMovement.MainActivityAllStockMovement;
import cst.smart.tnsrlm.stockRegister.MainActivityAllStockRegister;
import cst.smart.tnsrlm.web.MainActivityWeb;

import static cst.smart.tnsrlm.app.AppConfig.memberIdKey;
import static cst.smart.tnsrlm.app.AppConfig.mypreference;

public class MainActivtyFirstActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    private static final String TAG_MAIN = "Main";
    private static final String TAG_GUIDE = "Guide";
    private static final String TAG_FARMER = "Farmer";
    private static final String TAG_EMPLOYEE = "Employee";
    private static final String TAG_PLF = "PLF";
    public static String CURRENT_TAG = TAG_MAIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appbar_main);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        startFragment(new MainActivtyFirstFrag(),TAG_MAIN);
        final FloatingActionButton guidelines = (FloatingActionButton) findViewById(R.id.guidelines);
        final FloatingActionButton farmerDetails = (FloatingActionButton) findViewById(R.id.farmerDetails);
        final FloatingActionButton employeeDetails = (FloatingActionButton) findViewById(R.id.empdetails);
        final FloatingActionButton detailsConfederation = (FloatingActionButton) findViewById(R.id.detailsConfederation);


        guidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://coconutfpo.smartfpo.com/tnsrlm/tnsrlm_guide.pdf"));
                startActivity(browserIntent);
                farmerDetails.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                employeeDetails.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                detailsConfederation.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                guidelines.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

            }
        });

        farmerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new MainActivityAllFarmerFrag(), TAG_FARMER);
                farmerDetails.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blueGray)));
                employeeDetails.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                detailsConfederation.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                guidelines.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            }
        });

        employeeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new MainActivityAllEmployeeFrag(),TAG_EMPLOYEE);
                farmerDetails.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                employeeDetails.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blueGray)));
                detailsConfederation.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                guidelines.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            }
        });

        detailsConfederation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(new MainActivityAllPLFFrag(),TAG_PLF);
                farmerDetails.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                employeeDetails.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                detailsConfederation.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blueGray)));
                guidelines.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

            }
        });


    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.main_menu, paramMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        switch (paramMenuItem.getItemId()) {
            default:
                return super.onOptionsItemSelected(paramMenuItem);

            case R.id.worldwideweb:
                Intent intent = new Intent(MainActivtyFirstActivity.this, MainActivityWeb.class);
                startActivity(intent);
                return true;
            case R.id.editProfile:
                Intent io = new Intent(MainActivtyFirstActivity.this, MemberUpdate.class);
                startActivity(io);
                return true;
            case R.id.exit:
                if (!CURRENT_TAG.equals(TAG_MAIN)) {
                    startFragment(new MainActivtyFirstFrag(),TAG_MAIN);
                } else {
                    AlertDialog diaBox = Option();
                    diaBox.show();
                }

                return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (!CURRENT_TAG.equals(TAG_MAIN)) {
            startFragment(new MainActivtyFirstFrag(),TAG_MAIN);
        } else {
            AlertDialog diaBox = Option();
            diaBox.show();
        }

    }

    private AlertDialog Option() {

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Alert")
                .setMessage("Are you sure want to exit?")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code

                        dialog.dismiss();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.remove(memberIdKey);
                        editor.commit();
                        Intent io = new Intent(MainActivtyFirstActivity.this, LoginActivity.class);
                        startActivity(io);
                        finish();


                    }
                })


                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    private void startFragment(Fragment fragment, String tag) {
        CURRENT_TAG = tag;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

}
