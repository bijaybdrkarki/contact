package com.himalaya.contacts;

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

/**
 * Created by user on 3/22/2018.
 */
//initialize the view
// button click event set (login,signup)
//    when login button is clicked
//    get the name and password from edittext, validate and compare with stored name and password
//    if matched then open next activity else show error message
public class Loginactivity extends Activity {
    EditText et_name,et_pass;
    Button bt_login,bt_signup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        set the view
        setContentView(R.layout.activity_login);
//        intialize view
        et_name= findViewById(R.id.et_name);
        et_pass= findViewById(R.id.et_pass);
        bt_login= findViewById(R.id.bt_login);
        bt_signup= findViewById(R.id.bt_signup);

//        button click event
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                here when bt_login is clicked
                //   get the name and password from edit text
                String name= et_name.getText().toString();
                String password= et_pass.getText().toString();
//                validate the credentials
                boolean validate= true;
                if (TextUtils.isEmpty(name))
                {
                    et_name.setError("user name is empty");
                    validate= false;
                }
                if (TextUtils.isEmpty(password))
                {
                    et_pass.setError("password is empty");
                    validate= false;
                }
//                compare stored data
                if(validate)
                {
//                    dumy credentail stored
                   /* String dumy_name="ramesh";
                    String dumy_pass="ramesh123";*/
                   String data[]= getnameAndPasswordfrompref();
                    if (TextUtils.equals(name,data[0])&& TextUtils.equals(password,data[1]))
//                        open the next page
                        startActivity(new Intent(Loginactivity.this,ContactlistActivity.class));
                }
                else
                    {
                        Toast.makeText(Loginactivity.this,"user name or password mismatched",Toast.LENGTH_LONG).show();
                    }

            };
        });
    bt_signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            here when bt_signup is clicked
            startActivity(new Intent(Loginactivity.this,SignupActivity.class));

        };
    });

    }
public String[] getnameAndPasswordfrompref()
{
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
    String name= pref.getString("name","");
    String password= pref.getString("pass","");
    String[] data ={name, password};
    return data;
}
}
