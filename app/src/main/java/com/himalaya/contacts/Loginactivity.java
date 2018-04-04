package com.himalaya.contacts;

import android.annotation.SuppressLint;
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
 * Created by user on 3/22/2018.
 */
//initialize the view
// button click event set (login,signup)
//    when login button is clicked
//    get the name and password from edittext, validate and compare with stored name and password
//    if matched then open next activity else show error message
public class Loginactivity extends Activity {
    EditText et_name, et_pass;
    Button bt_login, bt_signup;
    private AdView mAdView;

  //  @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        set the view
        setContentView(R.layout.activity_login);
//        intialize view
        et_name = findViewById(R.id.et_name);
        et_pass = findViewById(R.id.et_pass);
        bt_login = findViewById(R.id.bt_login);
        bt_signup = findViewById(R.id.bt_signup);

//        button click event
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here when bt_login is clicked
                //   get the name and password from edit text
                String name = et_name.getText().toString();
                String password = et_pass.getText().toString();
//                validate the credentials
                boolean validate = true;
                if (TextUtils.isEmpty(name)) {
                    et_name.setError("user name is empty");
                    validate = false;
                }
                if (TextUtils.isEmpty(password)) {
                    et_pass.setError("password is empty");
                    validate = false;
                }
//                compare stored data
                if (validate) {
//                    dumy credentail stored
                   /* String dumy_name="ramesh";
                    String dumy_pass="ramesh123";*/
//                    String data[] = getnameAndPasswordfrompref();
                    validateCredentialwithserver(name, password);
//                    if (TextUtils.equals(name, data[0]) && TextUtils.equals(password, data[1]))
////                        open the next page
//                        startActivity(new Intent(Loginactivity.this, ContactlistActivity.class));
//                } else {
//                    Toast.makeText(Loginactivity.this, "user name or password mismatched", Toast.LENGTH_LONG).show();
//                }
                }

            }

            ;
        });
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            here when bt_signup is clicked
                startActivity(new Intent(Loginactivity.this, SignupActivity.class));

            }

            ;
        });
        MobileAds.initialize(this, "ca-app-pub-9711539423493324~7388146769");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void validateCredentialwithserver(String name, String password) {
        DataRequestPair requestPair = DataRequestPair.create();
        requestPair.put("password", password);
        requestPair.put("username", name);
        DataRequest request = DataRequest.getInstance();
//                .addHeaders(new String[]{"your_header_key"}, new String[]{"your_header_value"});
        // replace this with your domain to test
        request.addUrl("http://30.30.0.192:8000/contact/validate/");
        request.addDataRequestPair(requestPair);
        request.addMethod(Method.POST);
        DataRequestManager<String> requestManager = DataRequestManager.getInstance(getApplicationContext(), String.class);
        requestManager.addRequestBody(request).addOnDataRecieveListner(new OnDataRecievedListener() {
            @Override
            public void onDataRecieved(Response response, Object object) {
                if (response==Response.OK){
                    Log.d("test", " data from server = " + object.toString());
                    try {
                        JSONObject jason=new JSONObject(object.toString());
                        boolean success=jason.optBoolean( "success");
                        String message= jason.getString("message");
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        if (success){
                           startActivity(new Intent(Loginactivity.this, ContactlistActivity.class));
                        }else {
                            Toast.makeText(Loginactivity.this, "login fail", Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),response.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        requestManager.sync();
    }

    public String[] getnameAndPasswordfrompref() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String name = pref.getString("name", "");
        String password = pref.getString("pass", "");
        String[] data = {name, password};
        return data;
    }
}
