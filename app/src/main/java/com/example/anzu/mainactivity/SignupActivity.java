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

import com.mantraideas.simplehttp.datamanager.DataRequestManager;
import com.mantraideas.simplehttp.datamanager.OnDataRecievedListener;
import com.mantraideas.simplehttp.datamanager.OnDataRecievedProgressListener;
import com.mantraideas.simplehttp.datamanager.dmmodel.DataRequest;
import com.mantraideas.simplehttp.datamanager.dmmodel.DataRequestPair;
import com.mantraideas.simplehttp.datamanager.dmmodel.Method;
import com.mantraideas.simplehttp.datamanager.dmmodel.Response;

import org.json.JSONObject;

/**
 * Created by ANZU on 3/23/2018.
 */
//set initialized view
//set the listener to create button
//    when button is clicked, perform following operation
//    get the data from the form
//    validate the form
//    confirm the pw matched
//    if matched, save in preferences else show the warning message

public class SignupActivity extends Activity {

    EditText et_name;
    EditText et_pass;
    EditText et_conpass;
    Button btn_create;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    set the view
        setContentView(R.layout.activity_signup);
//        initialize view
        et_name= findViewById(R.id.et_name);
        et_pass=findViewById(R.id.et_pw);
        et_conpass=findViewById(R.id.et_conpw);
        btn_create=findViewById(R.id.btn_create);
//        set listener to create button
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                get data from the form
                String name= et_name.getText().toString();
                String pass= et_pass.getText().toString();
                String conpass = et_conpass.getText().toString();
//                validate the form
                boolean validate = true;
                if(TextUtils.isEmpty(name))
                {
                    et_name.setError("Name is empty");
                    validate = false;
                }
                if(TextUtils.isEmpty(pass) || TextUtils.isEmpty(conpass) || !TextUtils.equals(pass,conpass))
                {
                    Toast.makeText(SignupActivity.this,"password mismatched", Toast.LENGTH_SHORT).show();
                    validate = false;
                }
//                save the date in preference
                if (validate)
                {
//                    saveUserData(name,pass);
                    saveUserDataInServer(name,pass);

                    Toast.makeText(SignupActivity.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });
    }

    private void saveUserDataInServer(String name, String pass) {
        DataRequestPair requestPair = DataRequestPair.create();
        requestPair.put("password", pass);
        requestPair.put("username", name);
        DataRequest request = DataRequest.getInstance();

        // replace this with your domain to test
        request.addUrl("http://30.30.0.192:8000/contact/add/");
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
                        finish();
                    }


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

    public void saveUserData(String name, String pass)
    {
//        initialize the preference
//        put the key name for name and pass for password
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.edit().putString("name",name).commit();
        pref.edit().putString("password",pass).commit();
    }
}
