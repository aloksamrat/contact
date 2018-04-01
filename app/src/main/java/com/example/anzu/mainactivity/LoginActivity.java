package com.example.anzu.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by ANZU on 3/22/2018.
 */
//initialize the view
//btn click event set ( login, signup)
//    when login botton is clicked
//    get the name and password from edittext, validate and compare with stored name and password
//    if matched then open the next activity else show the error message

public class LoginActivity extends Activity {
    EditText et_name,et_password;
    Button btn_login,btn_signup;
    private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        set the view
        setContentView(R.layout.activity_login);
//        initialize view
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
//        btn click event set (login,signup)
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here when login button is clicked
//                get the name and password from user
                String name = et_name.getText().toString();
                String password = et_password.getText().toString();
//                validate the credential
                boolean validate = true;
                if (TextUtils.isEmpty(name)) {
                    et_name.setError("Name is empty");
                    validate = false;
                }
                if (TextUtils.isEmpty(password)) {
                    et_password.setError("password is empty");
                    validate = false;
                }
//                compare with name and password after validation
                if (validate) {
//                    dummy credential
                  /*  String dummyname = "ramesh";
                    String dummypassword = "ramesh123";*/
                  String data[] = getNameAndPasswordFromPref();
                    if (TextUtils.equals(name, data[0]) && TextUtils.equals(password, data[1])) {
//                        open the next page
                        startActivity(new Intent(LoginActivity.this, ContactlistActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "User name or Password mismatched", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                here when sign up button is clicked
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        MobileAds.initialize(this, "ca-app-pub-5852942202086652~3117374042");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
        public String[] getNameAndPasswordFromPref()
        {
            SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(this);
            String name= pf.getString("name","");
            String pass=pf.getString("password","");
            String[] data = {name, pass};
            return  data;
        }


}
