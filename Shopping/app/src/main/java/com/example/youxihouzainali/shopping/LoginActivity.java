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
    private EditText edittext_username;
    private  EditText edittext_password;
    private  EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edittext_username = (EditText)findViewById(R.id.edittext_username);
        edittext_password = (EditText)findViewById(R.id.edittext_pass);
        editText = (EditText)findViewById(R.id.edit_text2);
        //final MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "User.db", null, 1);
        //dbHelper.getWritableDatabase();
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null)
            actionbar.hide();
        //用户名和密码都输入过才enabled
        ImageButton button_eye = (ImageButton) findViewById(R.id.button_eye);
        button_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    Toast.makeText(LoginActivity.this, "PASSWORD->TEXT", Toast.LENGTH_SHORT).show();
                }
                else {
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Toast.makeText(LoginActivity.this, "TEXT->PASSWORD", Toast.LENGTH_SHORT).show();
                    //这个setInputType为什么没作用，明明toast都能成功？！！
                }
            }
        });
        Button button_forget_password = (Button) findViewById(R.id.button_forget_password);
        button_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = null, password = null;
                //username = edittext_username.getText().toString();
                //password = edittext_password.getText().toString();
                /*SQLiteDatabase db = dbHelper.getWritableDatabase();
                //String rightpassword = db.rawQuery("select password from user where username=?", new String[]{username});
                Cursor cursor = db.query("user", null, "where username = ?", new String[]{username}, null, null, null);
                if(cursor.moveToFirst()) {
                    do {
                        String rightPassword = cursor.getString(cursor.getColumnIndex("password"));
                        if(password.equals(rightPassword)) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, ShoppingActivity.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(LoginActivity.this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                    }while(cursor.moveToNext());

                }

            }

        });
        */
            }
        });
    }
}
