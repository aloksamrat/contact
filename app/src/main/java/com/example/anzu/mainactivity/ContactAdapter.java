package com.example.anzu.mainactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Ashok Sir on 3/24/2018.
 */
public class ContactAdapter extends ArrayAdapter {
    Context context;
    public ContactAdapter(Context context, int resource, List<Contact> contactList) {
        super(context, resource, contactList);
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Contact contact = (Contact) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact, null);
            // xml file is converted to view_type_class file
        }
        TextView tv_contact_name,tv_contact_phone;
        ImageView iv_contact_message,iv_contact_call;
        tv_contact_name= (TextView) convertView.findViewById(R.id.tv_contact_name);
        tv_contact_phone= (TextView) convertView.findViewById(R.id.tv_contact_phone);
        iv_contact_message= (ImageView) convertView.findViewById(R.id.iv_contact_message);
        iv_contact_call= (ImageView) convertView.findViewById(R.id.iv_contact_call);
        tv_contact_name.setText(contact.name);
        tv_contact_phone.setText(contact.phoneno);
        iv_contact_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(context,"Message button at "+position+" is clicked",Toast.LENGTH_LONG).show();

            }

        });
        iv_contact_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"Call button at "+position+" is clicked",Toast.LENGTH_LONG).show();

            }
        });





        return convertView;
    }
}
