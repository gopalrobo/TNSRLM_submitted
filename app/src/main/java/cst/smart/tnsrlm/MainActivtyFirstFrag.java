package cst.smart.tnsrlm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

public class MainActivtyFirstFrag extends Fragment {

    SharedPreferences sharedpreferences;

    private static final String TAG_MAIN = "Main";
    private static final String TAG_GUIDE = "Guide";
    private static final String TAG_FARMER = "Farmer";
    private static final String TAG_EMPLOYEE = "Employee";
    private static final String TAG_PLF = "PLF";
    public static String CURRENT_TAG = TAG_MAIN;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_front, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        RelativeLayout purchaseRecord = (RelativeLayout) view.findViewById(R.id.purchaseRecord);
        RelativeLayout balanceDepositDetails = (RelativeLayout) view.findViewById(R.id.balanceDepositDetails);
        RelativeLayout priceMaterial = (RelativeLayout) view.findViewById(R.id.priceMaterial);
        RelativeLayout balanceMonitoringRecord = (RelativeLayout) view.findViewById(R.id.balanceMonitoringRecord);
        RelativeLayout salesRegister = (RelativeLayout) view.findViewById(R.id.salesRegister);
        RelativeLayout bankDetails = (RelativeLayout) view.findViewById(R.id.bankDetails);
        RelativeLayout tnsrlm = (RelativeLayout) view.findViewById(R.id.tnsrlm);


        purchaseRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent5 = new Intent(getActivity(), MainActivityAllPurchase.class);
                startActivity(localIntent5);
            }
        });

        balanceDepositDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent6 = new Intent(getActivity(), MainActivityAllStockRegister.class);
                startActivity(localIntent6);
            }
        });

        priceMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent7 = new Intent(getActivity(), MainActivityAllStockMonitor.class);
                startActivity(localIntent7);
            }
        });

        balanceMonitoringRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent9 = new Intent(getActivity(), MainActivityAllStockMovement.class);
                startActivity(localIntent9);
            }
        });

        salesRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent10 = new Intent(getActivity(), MainActivityAllSales.class);
                startActivity(localIntent10);
            }
        });

        bankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localIntent11 = new Intent(getActivity(), MainActivityAllBank.class);
                startActivity(localIntent11);
            }
        });

        tnsrlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }


}
