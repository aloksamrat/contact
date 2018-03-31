package com.example.anzu.mainactivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ANZU on 3/28/2018.
 */

public class SaveContactActivity extends Activity
{
    EditText et_name,et_phoneno,et_description,et_email;
    Button btn_save;
        @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savecontact);
        et_name = findViewById(R.id.et_name);
        et_phoneno = findViewById(R.id.et_phoneno);
        et_description= findViewById(R.id.et_description);
        et_email = findViewById(R.id.et_email);
        btn_save= findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String phoneno = et_phoneno.getText().toString();
                String description = et_description.getText().toString();
                String email = et_email.getText().toString();
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(phoneno)||TextUtils.isEmpty(description)||TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Some fields are missing",Toast.LENGTH_LONG).show();
                                    }
                                    else
                {
                    Contact contact = new Contact(name,phoneno,description,email);
                    DataBaseHandler dh = new DataBaseHandler(getApplicationContext());
                    dh.saveContact(contact);
                    Toast.makeText(getApplicationContext(),"Contact saved",Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}
