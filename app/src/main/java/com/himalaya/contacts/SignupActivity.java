package com.himalaya.contacts;

import android.app.Activity;
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
                        saveUserData(name, password);
                        Toast.makeText(SignupActivity.this, "user created", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "password mismatched", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    public void saveUserData(String name, String password) {
//    initiliaze the preference
//        put the name for name and password for pass
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.edit().putString("name", name).commit();
        pref.edit().putString("pass", password).commit();
    }
}
