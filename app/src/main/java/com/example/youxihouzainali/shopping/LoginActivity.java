package com.example.youxihouzainali.shopping;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new MyDatabaseHelper(this, "Shopping.db", null, 2);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null)
            actionbar.hide();
        //用户名和密码都输入过才enabled
        ImageButton button_eye = (ImageButton) findViewById(R.id.button_eye);
        button_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText2 = (EditText)findViewById(R.id.edit_text2);
                if(editText2.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    editText2.setInputType(InputType.TYPE_CLASS_TEXT);
                    Toast.makeText(LoginActivity.this, "PASSWORD->TEXT", Toast.LENGTH_SHORT).show();
                }
                else {
                    editText2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Toast.makeText(LoginActivity.this, "TEXT->PASSWORD", Toast.LENGTH_SHORT).show();
                    //这个setInputType为什么没作用，明明toast都能成功？！！
                }
            }
        });
        Button button_forget_password = (Button) findViewById(R.id.button_forget_password);
        button_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加入忘记密码功能
            }
        });
        Button button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                EditText editText1 = (EditText) findViewById(R.id.edit_text1);
                EditText editText2 = (EditText) findViewById(R.id.edit_text2);
                String username = null, password = null, rightPassword = "ddd";
                username = editText1.getText().toString();
                password = editText2.getText().toString();
                Cursor cursor = db.query("User", null, "username=?", new String[] {username}, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        rightPassword = cursor.getString(cursor.getColumnIndex("password"));
                    } while (cursor.moveToNext());
                }
                cursor.close();
               if(password.equals(rightPassword)) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(LoginActivity.this, ShoppingActivity.class);
                    //startActivity(intent);
                }
                else
                    Toast.makeText(LoginActivity.this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
