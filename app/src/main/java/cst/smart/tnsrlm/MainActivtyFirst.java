package cst.smart.tnsrlm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import cst.smart.tnsrlm.bank.MainActivityAllBank;
import cst.smart.tnsrlm.employeeDetails.MainActivityAllEmployee;
import cst.smart.tnsrlm.farmerdetails.MainActivityAllFarmer;
import cst.smart.tnsrlm.member.MemberUpdate;
import cst.smart.tnsrlm.plf.MainActivityAllPLF;
import cst.smart.tnsrlm.purchase.MainActivityAllPurchase;
import cst.smart.tnsrlm.sales.MainActivityAllSales;
import cst.smart.tnsrlm.stockMoniter.MainActivityAllStockMonitor;
import cst.smart.tnsrlm.stockMovement.MainActivityAllStockMovement;
import cst.smart.tnsrlm.stockRegister.MainActivityAllStockRegister;
import cst.smart.tnsrlm.web.MainActivityWeb;

import static cst.smart.tnsrlm.app.AppConfig.memberIdKey;
import static cst.smart.tnsrlm.app.AppConfig.mypreference;

public class MainActivtyFirst extends AppCompatActivity {

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
        setContentView(R.layout.activity_front);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

//        RelativeLayout guidelines = (RelativeLayout) findViewById(R.id.guidelines);
//        RelativeLayout farmerDetails = (RelativeLayout) findViewById(R.id.farmerDetails);
//        RelativeLayout employeeDetails = (RelativeLayout) findViewById(R.id.employeeDetails);
//        RelativeLayout detailsConfederation = (RelativeLayout) findViewById(R.id.detailsConfederation);
        RelativeLayout purchaseRecord = (RelativeLayout) findViewById(R.id.purchaseRecord);
        RelativeLayout balanceDepositDetails = (RelativeLayout) findViewById(R.id.balanceDepositDetails);
        RelativeLayout priceMaterial = (RelativeLayout) findViewById(R.id.priceMaterial);
        RelativeLayout balanceMonitoringRecord = (RelativeLayout) findViewById(R.id.balanceMonitoringRecord);
        RelativeLayout salesRegister = (RelativeLayout) findViewById(R.id.salesRegister);
        RelativeLayout bankDetails = (RelativeLayout) findViewById(R.id.bankDetails);
        RelativeLayout tnsrlm = (RelativeLayout) findViewById(R.id.tnsrlm);

//        guidelines.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://coconutfpo.smartfpo.com/tnsrlm/tnsrlm_guide.pdf"));
//                startActivity(browserIntent);
//
//            }
//        });
//
//        farmerDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent localIntent2 = new Intent(MainActivtyFirst.this, MainActivityAllFarmer.class);
//                startActivity(localIntent2);
//            }
//        });
//
//        employeeDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent localIntent3 = new Intent(MainActivtyFirst.this, MainActivityAllEmployee.class);
//                startActivity(localIntent3);
//            }
//        });
//
//        detailsConfederation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent localIntent4 = new Intent(MainActivtyFirst.this, MainActivityAllPLF.class);
//                startActivity(localIntent4);
//            }
//        });
//
//        purchaseRecord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent localIntent5 = new Intent(MainActivtyFirst.this, MainActivityAllPurchase.class);
//                startActivity(localIntent5);
//            }
//        });

        balanceDepositDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent6 = new Intent(MainActivtyFirst.this, MainActivityAllStockRegister.class);
                startActivity(localIntent6);
            }
        });

        priceMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent7 = new Intent(MainActivtyFirst.this, MainActivityAllStockMonitor.class);
                startActivity(localIntent7);
            }
        });

        balanceMonitoringRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent9 = new Intent(MainActivtyFirst.this, MainActivityAllStockMovement.class);
                startActivity(localIntent9);
            }
        });

        salesRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent10 = new Intent(MainActivtyFirst.this, MainActivityAllSales.class);
                startActivity(localIntent10);
            }
        });

        bankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent11 = new Intent(MainActivtyFirst.this, MainActivityAllBank.class);
                startActivity(localIntent11);
            }
        });

        tnsrlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                Intent intent = new Intent(MainActivtyFirst.this, MainActivityWeb.class);
                startActivity(intent);
                return true;
            case R.id.editProfile:
                Intent io = new Intent(MainActivtyFirst.this, MemberUpdate.class);
                startActivity(io);
                return true;
            case R.id.exit:
                AlertDialog diaBox = Option();
                diaBox.show();
                return true;
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
                        Intent io = new Intent(MainActivtyFirst.this, LoginActivity.class);
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

}
