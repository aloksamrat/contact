package com.example.anzu.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mantraideas.simplehttp.datamanager.DataRequestManager;
import com.mantraideas.simplehttp.datamanager.OnDataRecievedListener;
import com.mantraideas.simplehttp.datamanager.dmmodel.DataRequest;
import com.mantraideas.simplehttp.datamanager.dmmodel.DataRequestPair;
import com.mantraideas.simplehttp.datamanager.dmmodel.Method;
import com.mantraideas.simplehttp.datamanager.dmmodel.Response;

import org.json.JSONObject;

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
//                  String data[] = getNameAndPasswordFromPref();
                    validateCredentialWithServer(name,password);
//                    if (TextUtils.equals(name, data[0]) && TextUtils.equals(password, data[1])) {
//                        open the next page
//                        startActivity(new Intent(LoginActivity.this, ContactlistActivity.class));
//                    } else {
//                        Toast.makeText(LoginActivity.this, "User name or Password mismatched", Toast.LENGTH_LONG).show();
//                    }
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

    private void validateCredentialWithServer(String name, String password) {
        DataRequestPair requestPair = DataRequestPair.create();
        requestPair.put("password", password);
        requestPair.put("username", name);
        DataRequest request = DataRequest.getInstance();

        // replace this with your domain to test
        request.addUrl("http://30.30.0.192:8000/contact/validate/");
        request.addDataRequestPair(requestPair);
        request.addMethod(Method.POST);

        DataRequestManager<String> requestManager = DataRequestManager.getInstance(getApplicationContext(), String.class);
        requestManager.addRequestBody(request).addOnDataRecieveListner(new OnDataRecievedListener() {
            @Override
            public void onDataRecieved(Response response, Object object) {
                if(response==Response.OK){
                    Log.d("test", " data from server = " + object.toString());
                    try {
                        JSONObject JSon = new JSONObject(object.toString());
                        boolean success = JSon.optBoolean("success");
                        String message = JSon.optString("message");
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        if(success){
                            startActivity(new Intent(LoginActivity.this, ContactlistActivity.class));
                        }
                        Toast.makeText(LoginActivity.this, "User name or Password mismatched", Toast.LENGTH_LONG).show();



                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),response.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }

        });
        requestManager.sync();
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
