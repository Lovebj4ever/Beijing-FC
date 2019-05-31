package com.example.dell.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myapplication.database.database1;

public class RegisterActivity extends AppCompatActivity {
    //private TextView v_back;
    private Button r_button;
    private EditText r_account;
    private EditText r_password;
    private EditText r_again_password;
    private String account;
    private String password;
    private String againpassword;
    database1 dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dbHelper = new database1(this,"SpecialDay.db",null,1);
        r_button=findViewById(R.id.r_buttons);
        r_account=findViewById(R.id.r_account);
        r_password=findViewById(R.id.r_password);
        r_again_password=findViewById(R.id.r_again_password);
//        v_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RegisterActivity.this.finish();
//            }
//        });
        //register button
        r_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //get string
                account=r_account.getText().toString().trim();
                password=r_password.getText().toString().trim();
                againpassword=r_again_password.getText().toString().trim();
                if(TextUtils.isEmpty(account)){
                    Toast.makeText(RegisterActivity.this,"please create an account", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this,"Please create a password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(againpassword)){
                    Toast.makeText(RegisterActivity.this,"Please enter the password again", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(againpassword)){
                    Toast.makeText(RegisterActivity.this,"The passwords entered do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(accountExist(account)){
                    Toast.makeText(RegisterActivity.this,"This account exists!", Toast.LENGTH_SHORT).show();
                    return;
                }


                    dbHelper.getWritableDatabase();
                    SQLiteDatabase d = dbHelper.getReadableDatabase();
                    ContentValues values =new ContentValues();
                    values.put("id",account);
                    values.put("password",password);
                    d.insert("user",null,values);
                    values.clear();
                    Toast.makeText(RegisterActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                //put the new account and its password in SharedPreferences
                saveAccount(account,password);
                //put the account in  loginActivity
                Intent value=new Intent();
                value.putExtra("account", account);
                setResult(RESULT_OK, value);
                /*startActivity(value);*/
                RegisterActivity.this.finish();
            }
        });


        }

        private boolean accountExist(String account){
            SharedPreferences sp=getSharedPreferences("login", MODE_PRIVATE);
            String sharedPass=sp.getString(account,"");
            //if the password is not null, then the account exists
            if (!TextUtils.isEmpty(sharedPass)){
                return true;
            }
            else{
                return false;
            }
    }
    //save the account as key, password as value in SharedPreferences
        private void saveAccount(String account, String password){
            SharedPreferences sp=getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString(account,password);
            editor.commit();
        }




}
