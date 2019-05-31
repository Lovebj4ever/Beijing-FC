package com.example.dell.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.myapplication.database.database1;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;


public class LoginActivity extends AppCompatActivity {
    //private TextView v_back;
    private Button login_button;
    private TextView register_now;
    private EditText l_account;
    private EditText l_password;
    private CheckBox checkBoxView;
    private String account;
    private String password;
    private String sharedPass;
    ArrayList<String> account1 = new ArrayList<>();
    ArrayList<String> password1 = new ArrayList<>();
    database1 dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        l_account=findViewById(R.id.l_account);
        l_password=findViewById(R.id.l_password);
        login_button=findViewById(R.id.login_button);
        register_now=findViewById(R.id.register_now);
        dbHelper = new database1(this,"SpecialDay.db",null,1);
        SQLiteDatabase d = dbHelper.getWritableDatabase();
        register_now.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
                //finish();
            }
        });
        checkBoxView=(CheckBox) findViewById(R.id.checkbox);
        checkBoxView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    l_password.setTransformationMethod(null);
                }
                else{
                    l_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        login_button.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                //get account and password from the user
                account=l_account.getText().toString().trim();
                password=l_password.getText().toString().trim();
                Cursor cursor = d.query("user",null,null,null,null,null,null);
                if(cursor!=null){
                    while(cursor.moveToNext()){
                        String temp1 = cursor.getString(cursor.getColumnIndex("id"));
                        account1.add(cursor.getString(cursor.getColumnIndex("id")));
                        password1.add(cursor.getString(cursor.getColumnIndex("password")));
                        System.out.println(temp1);
                    }
                }
                cursor.close();
                for(int i=0;i<account1.size();i++){
                    if(account.equals(account1.get(i))&&password.equals(password1.get(i))){
                        LoginActivity.this.finish();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }else if(!account.equals(account1.get(i))){
                        Toast.makeText(LoginActivity.this, "Account does not exist!", Toast.LENGTH_SHORT).show();
                    }else if(account.equals(account1.get(i))&&!password.equals(password1.get(i))){
                        Toast.makeText(LoginActivity.this,"Account and password do not match", Toast.LENGTH_SHORT).show();
                    }
                }

                sharedPass=readPass(account);
                if(TextUtils.isEmpty(account)){
                    Toast.makeText(LoginActivity.this, "Please enter your account",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Please enter your password",Toast.LENGTH_SHORT).show();
                    return;
                 }

                if(password.equals(sharedPass)) {
                    Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    keepLogin(true, account);
                    Intent value = new Intent();
                    value.putExtra("isLogin", true);
                    setResult(RESULT_OK, value);
                    LoginActivity.this.finish();
                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    return;
                }
                if(sharedPass!=null&&!TextUtils.isEmpty(sharedPass)&&!password.equals(sharedPass)){
                    //Toast.makeText(LoginActivity.this,"Account and password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                }

        });

    }
    private String readPass(String account){
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(account,"");
    }
    private void keepLogin(boolean keep, String account){
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin",keep);
        editor.putString("loginUserName",account);
        editor.commit();
    }
    protected void  result(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(data!=null){
            String account=data.getStringExtra("account");
            if(!TextUtils.isEmpty(account)){
                l_account.setText(account);
                l_account.setSelection(account.length());
            }
        }
    }


}

