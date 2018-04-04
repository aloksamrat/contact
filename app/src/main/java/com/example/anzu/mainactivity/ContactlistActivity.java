package com.example.anzu.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mantraideas.simplehttp.datamanager.DataRequestManager;
import com.mantraideas.simplehttp.datamanager.OnDataRecievedListener;
import com.mantraideas.simplehttp.datamanager.OnDataRecievedProgressListener;
import com.mantraideas.simplehttp.datamanager.dmmodel.DataRequest;
import com.mantraideas.simplehttp.datamanager.dmmodel.Method;
import com.mantraideas.simplehttp.datamanager.dmmodel.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashok Sir on 3/24/2018.
 */

public class ContactlistActivity extends Activity {
    private DatabaseReference mDatabase;
// ...

    List<Contact> contactList;
    FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);
        setTitle("Contact");
        contactList = new ArrayList<>();
        final ContactAdapter adapter = new ContactAdapter(this, R.layout.row_contact, contactList);
        ListView lv_contact;
        lv_contact = (ListView) findViewById(R.id.lv_contact);
        lv_contact.setAdapter(adapter);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d("ContactListActivity","database changed");
//                Toast.makeText(getApplicationContext(),"Database changed",Toast.LENGTH_SHORT).show();
//                Contact value = dataSnapshot.getValue(Contact.class);
//                Toast.makeText(getApplicationContext(), "Database changed of "+ value.name, Toast.LENGTH_SHORT).show();


//                Log.d("Contactlist", "Value is: " + value);
                contactList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    contactList.add(postSnapshot.getValue(Contact.class));
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        the data here is from dummy
//        String dummy_list = "[ \n" +
//                "\t\n" +
//                "        { \"name\":\"Ashok\",\n" +
//                "\t  \"phoneno\":\"9841212132\"\n" +
//                "\t},\n" +
//                "\t{ \"name\":\"Alok\",\n" +
//                "\t  \"phoneno\":\"9841959417\"\n" +
//                "\t},\n" +
//                "\t{ \"name\":\"Anju\",\n" +
//                "\t  \"phoneno\":\"9843575524\"\n" +
//                "\t},\n" +
//                "\t{ \"name\":\"Samrat\",\n" +
//                "\t  \"phoneno\":\"9851183826\"\n" +
//                "\t},\n" +
//                "\t{ \"name\":\"Bijay\",\n" +
//                "\t  \"phoneno\":\"9841245634\"\n" +
//                "\t}\n" +
//                "]\n";
//        try {
//            JSONArray contactArray = new JSONArray(dummy_list);
//            for (int i = 0; i < contactArray.length(); i++)
//            {
//                JSONObject contactjson = contactArray.getJSONObject(i);
//                String name= contactjson.getString("name");
//                String phone= contactjson.getString("phoneno");
//                Contact ct= new Contact(name, phone);
//                contactList.add(ct);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        The data from here is from database
//        DataBaseHandler dh = new DataBaseHandler(getApplicationContext());
//        contactList = dh.getAllContact();
//        the data here is from server
//        create the request
        DataRequest request = DataRequest.getInstance();
        // replace this with your domain to test
        request.addUrl("http://30.30.0.192:8000/contact/get");
        request.addMethod(Method.GET);
        // request.addMinimumServerCallTimeDifference(5000);
//        execute the request
//        DataRequestManager<String> requestManager = DataRequestManager.getInstance(getApplicationContext(), String.class);
//        requestManager.addRequestBody(request).addOnDataRecieveListner(new OnDataRecievedListener() {
//            @Override
//            public void onDataRecieved(Response response, Object object) {
//                if (response == Response.OK) {
//                    Log.d("test", " data from server = " + object.toString());
//                } else {
//                    Toast.makeText(ContactlistActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }, new OnDataRecievedProgressListener() {
//            @Override
//            public void onDataRecievedProgress(int completedPercentage) {
//                Log.d("MainActivity", "Progress = " + completedPercentage);
//            }
//        });
//        requestManager.sync();
        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactlistActivity.this, SaveContactActivity.class));
            }
        });
    }

}
