package com.himalaya.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user on 3/28/2018.
 */

public class SavecontactActivity extends Activity
{
    EditText et_name, et_phonenumber,et_email;
    Button bt_save;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savecontact);
        et_name= findViewById(R.id.et_name);
        et_phonenumber= findViewById(R.id.et_phonenumber);
        et_email= findViewById(R.id.et_email);
        bt_save= findViewById(R.id.bt_save);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
              String name=et_name.getText().toString();
              String phonenumber= et_phonenumber.getText().toString();
              String email= et_email.getText().toString();
                boolean validate= true;
                if (TextUtils.isEmpty(name))
                {
                    et_name.setError("name is empty");
                    validate= false;
                }
                if (TextUtils.isEmpty(phonenumber))
                {
                    et_phonenumber.setError("phonenumber is empty");
                    validate= false;
                }
                if (TextUtils.isEmpty(email))
                {
                    et_email.setError("email is empty");
                    validate= false;
                }
                if (validate)
                {
                    Contact contacts=new Contact(name, phonenumber, email);
                    DataBaseHandler dh = new DataBaseHandler(getApplicationContext());
                    dh.saveContact(contacts);
                    Toast.makeText(SavecontactActivity.this,"contact added sucessfully",Toast.LENGTH_LONG).show();
                }
                else
                    {
                        Toast.makeText(SavecontactActivity.this,"user name or phonenumber or email not entered",Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
