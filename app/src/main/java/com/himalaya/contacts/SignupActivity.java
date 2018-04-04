package com.himalaya.contacts;

import android.app.Activity;
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
 * Created by user on 3/23/2018.
 */
//set initialize view
//set the listner to create button
//when button is clicked perform following operation
    /*
    i. get the dat from form
    ii.validate the form
    iii.confirm the password matched
    iv. if matched save in preferences else show the warning message
     */
public class SignupActivity extends Activity {
    EditText et_name, et_password, et_confrimpass;
    Button create;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//    set the view
        setContentView(R.layout.signup_activity);
//    initialize the view
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_pass);
        et_confrimpass = findViewById(R.id.confirm_pass);
        create = findViewById(R.id.create);
//        set the listenior to create button
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                get the data from form
                String name = et_name.getText().toString();
                String password = et_password.getText().toString();
                String confirmpass = et_confrimpass.getText().toString();
//                validate the credentials
                boolean validate = true;
                if (TextUtils.isEmpty(name)) {
                    et_name.setError("user name is empty");
                    validate = false;
                }
                if (TextUtils.isEmpty(password)) {
                    et_password.setError("password is empty");
                    validate = false;
                }
                if (TextUtils.isEmpty(password)) {
                    et_confrimpass.setError("Confirm password is empty");
                    validate = false;
                }
//                compare stored data
                if (validate) {
                    if (TextUtils.equals(password, confirmpass)) {
//                        save the data in preference
//                        saveUserData(name, password);
//                        save user data in server
                        saveuserdataInserver(name, password);
                        Toast.makeText(SignupActivity.this, "user created", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "password mismatched", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    private void saveuserdataInserver(String name, String password) {
        DataRequestPair requestPair = DataRequestPair.create();
        requestPair.put("password", password);
        requestPair.put("username", name);
        DataRequest request = DataRequest.getInstance();
//                .addHeaders(new String[]{"your_header_key"}, new String[]{"your_header_value"});
        // replace this with your domain to test
        request.addUrl("http://30.30.0.192:8000/contact/add/");
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
                        finish();
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

    public void saveUserData(String name, String password) {
//    initiliaze the preference
//        put the name for name and password for pass
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.edit().putString("name", name).commit();
        pref.edit().putString("pass", password).commit();
    }
}
