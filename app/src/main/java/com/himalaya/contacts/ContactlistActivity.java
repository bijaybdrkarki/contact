package com.himalaya.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.mantraideas.simplehttp.datamanager.DataRequestManager;
import com.mantraideas.simplehttp.datamanager.OnDataRecievedListener;
import com.mantraideas.simplehttp.datamanager.OnDataRecievedProgressListener;
import com.mantraideas.simplehttp.datamanager.dmmodel.DataRequest;
import com.mantraideas.simplehttp.datamanager.dmmodel.Method;
import com.mantraideas.simplehttp.datamanager.dmmodel.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/24/2018.
 */

public class ContactlistActivity extends Activity {
    List<Contact> contactList;
    FloatingActionButton fab_add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);
        setTitle("MyContacts");
        contactList = new ArrayList<>();
  /*  the data here is dummy data
  String dummy_list= "[\n" +
                "{\n" +
                "\"name\":\"bijay\",\n" +
                "\"phonenumber\":\"9849624061\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"bijay\",\n" +
                "\"phonenumber\":\"9849624061\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"bijay\",\n" +
                "\"phonenumber\":\"9849624061\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"bijay\",\n" +
                "\"phonenumber\":\"9849624061\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"bijay\",\n" +
                "\"phonenumber\":\"9849624061\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"bijay\",\n" +
                "\"phonenumber\":\"9849624061\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"bijay\",\n" +
                "\"phonenumber\":\"9849624061\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"bijay\",\n" +
                "\"phonenumber\":\"9849624061\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"bijay\",\n" +
                "\"phonenumber\":\"9849624061\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"bijay\",\n" +
                "\"phonenumber\":\"9849624061\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"bijay\",\n" +
                "\"phonenumber\":\"9849624061\"\n" +
                "},\n" +
                "{\n" +
                "\"name\":\"Himal\",\n" +
                "\"phonenumber\":\"9849624061\"\n" +
                "}\n" +
                "]";
        try {
            JSONArray contactArray = new JSONArray(dummy_list);
            for (int i=0;i< contactArray.length();i++)
            {
                JSONObject contactjason = contactArray.getJSONObject(i);
                String name= contactjason.getString("name");
                String phonenumber= contactjason.getString("phonenumber");
                Contact ct= new Contact(name, phonenumber);
                contactList.add(ct);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        the data from here  is from database
        DataBaseHandler dh = new DataBaseHandler(getApplicationContext());
        contactList = dh.getAllContact();*/
// the data from here is from server
//        create the request

        DataRequest request = DataRequest.getInstance();

        // replace this with your domain to test
        request.addUrl("https://30.30.0.192:8000/contact/get/");
        //request.addDataRequestPair(requestPair);
        request.addMethod(Method.GET);
        request.addMinimumServerCallTimeDifference(2000);
        // execute the request
        DataRequestManager<String> requestManager = DataRequestManager.getInstance(getApplicationContext(), String.class);
        requestManager.addRequestBody(request).addOnDataRecieveListner(new OnDataRecievedListener() {
            @Override
            public void onDataRecieved(Response response, Object object) {
                if (response == Response.OK) {
                    Log.d("test", " data from server = " + object.toString());
                } else {
                    Toast.makeText(ContactlistActivity.this, "no internet", Toast.LENGTH_LONG).show();
                }

            }
        }, new OnDataRecievedProgressListener() {
            @Override
            public void onDataRecievedProgress(int completedPercentage) {
                Log.d("MainActivity", "Progress = " + completedPercentage);
            }
        });
        requestManager.sync();
        ContactAdaptor adaptor = new ContactAdaptor(this, R.layout.row_contact, contactList);
        ListView all_contact;
        all_contact = findViewById(R.id.all_contact);
        all_contact.setAdapter(adaptor);
        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactlistActivity.this, SavecontactActivity.class));
            }
        });
    }
}
