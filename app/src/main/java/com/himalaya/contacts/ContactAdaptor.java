package com.himalaya.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by user on 3/24/2018.
 */

public class ContactAdaptor extends ArrayAdapter {
    Context context;
    public ContactAdaptor(@NonNull Context context, int resource, List<Contact> contactList) {
        super(context, resource, contactList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contact contact = (Contact) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact, null);
//            convert xml file to view class file
        }
        TextView contact_name,contact_phonenumber;
        ImageView contact_call, conatact_message;
        contact_name= convertView.findViewById(R.id.contact_name);
        contact_phonenumber= convertView.findViewById(R.id.contact_phonenumber);
        contact_call= convertView.findViewById(R.id.contact_call);
        conatact_message = convertView.findViewById(R.id.contact_message);
        contact_name.setText(contact.getName());
        contact_phonenumber.setText(contact.getPhonenumber());
        conatact_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"message button at "+position + "is clicked",Toast.LENGTH_LONG).show();
            }


        });
        contact_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Call button at " + position + "is clicked", Toast.LENGTH_LONG).show();
            }
        });


        return convertView;
    }
}
