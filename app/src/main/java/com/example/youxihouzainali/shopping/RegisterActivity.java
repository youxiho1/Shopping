package com.example.youxihouzainali.shopping;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {
    int flag = 0;
    private MyDatabaseHelper dbHelper;
    public void alert(String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
    //检验是否有重复的用户名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new MyDatabaseHelper(this, "Shopping.db", null, 2);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null)
            actionbar.hide();
        CheckBox check_Box = (CheckBox)findViewById(R.id.checkbox) ;
        check_Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {               //判断checkbox是不是被挑了钩
                if(flag == 0)
                    flag = 1;
                else
                    flag = 0;
            }
        });
        Button buttonRegister = (Button) findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password, repassword, telephone;
                EditText edittext_username = (EditText) findViewById(R.id.edittext_username);
                EditText edittext_pass = (EditText) findViewById(R.id.edittext_pass);
                EditText edittext_repass = (EditText) findViewById(R.id.edittext_repass);
                EditText edittext_tel = (EditText) findViewById(R.id.edittext_tel);
                username = edittext_username.getText().toString();
                password = edittext_pass.getText().toString();
                repassword = edittext_repass.getText().toString();
                telephone = edittext_tel.getText().toString();
                if (username.length() > 18) {
                    alert("提示", "用户名长度不能超过18位");
                    return;
                }
                char[] c = new char[20];
                c = username.toCharArray();
                int length = username.length();
                for (int i = 0; i < length; i++) {
                    if (!((c[i] >= 48 && c[i] <= 57) || (c[i] >= 65 && c[i] <= 90) || (c[i] >= 97 && c[i] <= 122) || c[i] == ' ')) {
                        alert("警告", "用户名中含有非数字、字母、空格的字符，请重新输入");
                        return;
                    }
                }
                c = password.toCharArray();
                length = password.length();
                for (int i = 0; i < length; i++) {
                    if (!((c[i] >= 48 && c[i] <= 57) || (c[i] >= 65 && c[i] <= 90) || (c[i] >= 97 && c[i] <= 122) || c[i] == ' ')) {
                        alert("警告", "密码中含有非数字、字母、空格的字符，请重新输入");
                        return;
                    }
                }
                if (password.length() > 18) {
                    alert("提示", "密码长度不能超过18位");
                    return;
                }
                if (!(password.equals(repassword))) {
                    alert("提示", "两次输入的密码不一致");
                    return;
                }
                //还差checkbox和手机验证码（验证码）
                if (password.equals(repassword)) {
                    int id = -1;
                    if (username.length() == 0 || password.length() == 0) {
                        alert("提示", "您的信息未填写完整");
                        return;
                    }
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor cursor = db.query("User", null, "username=?", new String[] {username}, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        do {
                            id = cursor.getInt(cursor.getColumnIndex("id"));
                        } while (cursor.moveToNext());
                    }
                    if(id != -1) {
                        alert("提示", "该用户名已被注册");
                        return;
                    }
                    cursor.close();
                    ContentValues values = new ContentValues();
                    //开始组装第一条数据
                    values.put("username", username);
                    values.put("password", password);
                    values.put("ownerflag", flag);
                    values.put("shopflag", 0);
                    db.insert("User", null, values);    //插入第一条数据
                    values.clear();
                    Toast.makeText(RegisterActivity.this, "successful", Toast.LENGTH_SHORT).show();
                    if(flag == 1) {
                        Intent intent = new Intent(RegisterActivity.this, NewShopActivity.class);
                        intent.putExtra("extra_data", username);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "YOU ARE CUSTOMER", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, ShoppingActivity.class);
                        intent.putExtra("extra_data", username);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
